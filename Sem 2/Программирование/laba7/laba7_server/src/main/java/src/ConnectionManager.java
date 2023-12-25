package src;

import src.commands.*;
import src.models.User;
import src.udp.ChunksData;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type Connection manager.
 */
public class ConnectionManager {

    /**
     * The constant PACKAGE_SIZE - single chunk size.
     */
    public static final int PACKAGE_SIZE = 5 * 1024 - 8;
    private Map<String, ChunksData> clientChunks = new ConcurrentHashMap<>();
    private final DataBase dataBase;
    private Map<ServerCommandType, Command> commands = new HashMap<>();
    private Lock lock = new ReentrantLock();
    private ForkJoinPool requestForkJoinPool;
    private ForkJoinPool responseForkJoinPool;
    private DatagramSocket socket;
    private static final int FORK_JOIN_SIZE = 20;

    /**
     * Instantiates a new Connection manager.
     *
     * @param dataBase     the database
     */
    public ConnectionManager(DataBase dataBase) {
        this.dataBase = dataBase;

        registerCommand(ServerCommandType.INFO, new Info(this.dataBase));
        registerCommand(ServerCommandType.SHOW, new Show(this.dataBase));
        registerCommand(ServerCommandType.INSERT, new Insert(this));
        registerCommand(ServerCommandType.UPDATE, new Update(this));
        registerCommand(ServerCommandType.REMOVE_KEY, new RemoveKey(this));
        registerCommand(ServerCommandType.CLEAR, new Clear(this));
        registerCommand(ServerCommandType.REMOVE_LOWER, new RemoveLower(this));
        registerCommand(ServerCommandType.REPLACE_IF_LOWE, new ReplaceIfLowe(this));
        registerCommand(ServerCommandType.REMOVE_LOWER_KEY, new RemoveLowerKey(this));
        registerCommand(ServerCommandType.MIN_BY_NAME, new MinByName(this.dataBase));
        registerCommand(ServerCommandType.GROUP_COUNTING_BY_PRICE, new GroupCountingByPrice(this.dataBase));
        registerCommand(ServerCommandType.PRINT_UNIQUE_MANUFACTURE_COST, new PrintUniqueManufactureCost(this.dataBase));
        registerCommand(ServerCommandType.GET, new Get(this.dataBase));
        registerCommand(ServerCommandType.GET_UNIQUE_ID, new GetUniqueID(this.dataBase));
        registerCommand(ServerCommandType.GET_BY_KEY, new GetByKey(this.dataBase));
        registerCommand(ServerCommandType.AUTH, new Auth());
        registerCommand(ServerCommandType.REGISTER, new Register(this.dataBase));
        registerCommand(ServerCommandType.ACTUALIZE, new Actualize(this.dataBase));
    }

    /**
     * Run.
     *
     * @throws IOException the io exception
     */
    public void run() throws IOException {
        responseForkJoinPool = new ForkJoinPool(FORK_JOIN_SIZE);
        requestForkJoinPool = new ForkJoinPool(FORK_JOIN_SIZE);

        Scanner scanner = new Scanner(System.in);
        int port;
        while (true){
            System.out.print("Введите порт: ");
            try{
                port = Integer.parseInt(scanner.nextLine());
                if(available(port)){
                    socket = new DatagramSocket(port);;
                    break;
                }
            }catch (Exception e){
                System.out.println("Порт не подходит");
            }
        }
        Main.logger.info("Сервер проснулся");


        Runnable check = () -> {
            while (true) {
                for (String key : clientChunks.keySet()) {
                    if (clientChunks.get(key).isReady()) {
                        byte[] requestFull = clientChunks.get(key).getFullResponse();
                        Main.logger.info("(Thread Processing) Получен полный запрос клиента длиной в " + requestFull.length + " байт.");
                        ServerCommand toExecute = (ServerCommand)Utils.deserializeObject(requestFull);
                        User caller = dataBase.authorizeUser(toExecute.userCredentials);
                        ServerCommand tempMessage = new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Нельзя выполнять команды неавторизованным пользователем"));
                        if (caller != null || toExecute.type == ServerCommandType.AUTH || toExecute.type == ServerCommandType.REGISTER)
                            tempMessage = executeInput(toExecute, caller);
                        final ServerCommand message = tempMessage;
                        // Отправка ответа на клиентскую сторону в пуле потоков
                        List<byte[]> chunks = splitByteArray(Utils.serializeObject(message));
                        for (int i = 0; i < chunks.size(); i++) {
                            int index = i;
                            // Отправка ответа на клиентскую сторону в пуле потоков
                            responseForkJoinPool.execute(() -> {
                                List<byte[]> chunkInfo = new ArrayList<>();
                                chunkInfo.add(Utils.intToBytes(chunks.size()));
                                chunkInfo.add(Utils.intToBytes(index));
                                chunkInfo.add(chunks.get(index));
                                ByteBuffer responseBuffer = ByteBuffer.wrap(Utils.joinByteArrays(chunkInfo));
                                DatagramPacket responseInf = new DatagramPacket(
                                        responseBuffer.array(),
                                        responseBuffer.array().length,
                                        clientChunks.get(key).getPacket().getSocketAddress()
                                );
                                // Отправка ответа
                                try {
                                    socket.send(responseInf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Main.logger.info("(Thread Sending) Отправлен чанк длиной " + responseBuffer.array().length + " байт клиенту " + clientChunks.get(key).getClientAddress().toString());
                            });
                        }
                        while (responseForkJoinPool.getActiveThreadCount() != 0) {}
                        clientChunks.remove(key);
                    }
                }
            }
        };
        Thread checkClientChunks = new Thread(check);
        checkClientChunks.start();

        // Ожидаем сообщения от клиента
        while (true) {
            byte[] bufferHelp = new byte[PACKAGE_SIZE + 8];
            DatagramPacket request = new DatagramPacket(bufferHelp, bufferHelp.length);
            socket.receive(request);

            // читаем запросы при помощи Fixed thread pool
            requestForkJoinPool.execute(() -> {
                Main.logger.info("(Thread Reading) Поток приступил к работе");
                SocketAddress clientAddress = request.getSocketAddress();
                byte[] recieved = request.getData();
                Main.logger.info("(Thread Reading) Получен чанк длины "+recieved.length+" байт от клиента "+clientAddress.toString());

                int index = Utils.getIntFromByteArray(Arrays.copyOfRange(recieved, 4, 8));
                byte[] data = Arrays.copyOfRange(recieved, 8, recieved.length);

                if (clientChunks.containsKey(clientAddress.toString())) {
                    clientChunks.get(clientAddress.toString()).addChunk(index, data);
                    Main.logger.info("(Thread Reading) Чанк добавлен к имеющимся и готовность: " + clientChunks.get(clientAddress.toString()).isReady());

                } else {
                    int size = Utils.getIntFromByteArray(Arrays.copyOfRange(recieved, 0, 4));
                    ChunksData chunksData = new ChunksData(size);
                    clientChunks.put(clientAddress.toString(), chunksData);
                    clientChunks.get(clientAddress.toString()).setClientAddress(clientAddress);
                    clientChunks.get(clientAddress.toString()).setPacket(request);
                    clientChunks.get(clientAddress.toString()).addChunk(index, data);
                    Main.logger.info("(Thread Reading) Чанк добавлен как новый и готовность: "+clientChunks.get(clientAddress.toString()).isReady()+" так как "+clientChunks.get(clientAddress.toString()).getSize()+", а всего "+clientChunks.get(clientAddress.toString()).getChunks().size());
                }
            });
        }
    }

    /**
     * Split byte array list.
     *
     * @param source the source
     * @return the list
     */
    public static List<byte[]> splitByteArray(byte[] source) {
        int maxChunkSize = PACKAGE_SIZE;
        if (source.length <= maxChunkSize) {
            return Collections.singletonList(source);
        }
        int numChunks = (int) Math.ceil((double) source.length / maxChunkSize);
        List<byte[]> chunks = new ArrayList<>(numChunks);

        int offset = 0;
        for (int i = 0; i < numChunks; i++) {
            int chunkSize = Math.min(maxChunkSize, source.length - offset);
            byte[] chunk = Arrays.copyOfRange(source, offset, offset + chunkSize);
            chunks.add(chunk);
            offset += chunkSize;
        }
        return chunks;
    }

    /**
     * Execute input server command.
     *
     * @param command the command
     * @return the server command
     */
    public final ServerCommand executeInput(ServerCommand command, User caller) {
        lock.lock();
        if(!commands.containsKey(command.type))
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Неизвестная команда для сервера."));
        try {
            return commands.get(command.type).execute(command.data, caller);
        } catch (IllegalArgumentException e) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject(e.getMessage()));
        } finally {
            lock.unlock();
        }
    }

    /**
     * Available boolean.
     *
     * @param port the port
     * @return the boolean
     */
    public static boolean available(int port) {
        if (port < 1023 || port > 65534) {
            return false;
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException ignored) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }

    /**
     * Register command.
     *
     * @param type    the type
     * @param command the command
     */
    public void registerCommand(ServerCommandType type, Command command) {
        commands.put(type, command);
    }

    /**
     * Gets commands.
     *
     * @return the commands
     */
    public Map<ServerCommandType, Command> getCommands() {
        return commands;
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public DataBase getDataBase() {
        return dataBase;
    }
}
