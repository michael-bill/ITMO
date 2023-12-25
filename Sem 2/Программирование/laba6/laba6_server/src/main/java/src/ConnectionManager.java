package src;

import src.commands.*;
import src.udp.ChunksData;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.udp.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

/**
 * The type Connection manager.
 */
public class ConnectionManager {

    /**
     * The constant PACKAGE_SIZE - single chunk size.
     */
    public static final int PACKAGE_SIZE = 5 * 1024 - 8;
    private DatagramChannel channel;
    private Selector selector;
    private SocketAddress clientAddress;
    private Map<String, ChunksData> clientChunks = new HashMap<>();
    private Map<ServerCommandType, Command> commands = new LinkedHashMap<>();

    private final DataBase dataBase;
    private final String jsonFilePath;

    /**
     * Instantiates a new Connection manager.
     *
     * @param dataBase     the database
     * @param jsonFilePath the json file path
     */
    public ConnectionManager(DataBase dataBase, String jsonFilePath) {
        this.dataBase = dataBase;
        this.jsonFilePath = jsonFilePath;

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
    }

    /**
     * Run.
     *
     * @throws IOException the io exception
     */
    public void run() throws IOException {
        // Создаем канал и настраиваем его на неблокирующий режим
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        int bufferSize = PACKAGE_SIZE + 100;
        channel.setOption(StandardSocketOptions.SO_RCVBUF, bufferSize);
        channel.setOption(StandardSocketOptions.SO_SNDBUF, bufferSize);

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        InputStream inputStream = System.in;
        int port;
        while (true) {
            System.out.print("Введите порт: ");
            try {
                port = Integer.parseInt(scanner.nextLine());
                if (available(port)) {
                    channel.bind(new InetSocketAddress(port));
                    break;
                }
            } catch (Exception e) {
                System.out.println("Порт не подходит.");
            }
        }
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
        Main.logger.info("Сервер проснулся.");

        // Ожидаем сообщения от клиента
        while (selector.isOpen()) {
            selector.selectNow();

            if (inputStream.available() > 0) {
                String line = scanner.nextLine();
                if (line.trim().equals("exit")) {
                    Main.logger.info("Сервер ушел спать.");
                    System.exit(0);
                } else if (line.trim().equals("save")) {
                    try {
                        dataBase.save(jsonFilePath);
                    } catch (Exception e) {
                        Main.logger.error("Не удалось сохранить изменения в файл.");
                    }
                    Main.logger.info("Файл сохранен успешно.");
                }
            }

            if (selector.selectedKeys().size() == 0)
                continue;
            // Обрабатываем готовые ключи
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                // Если ключ готов к чтению
                if (key.isReadable()) {
                    ByteBuffer bufferHelp = ByteBuffer.allocate(PACKAGE_SIZE + 8);
                    clientAddress = channel.receive(bufferHelp);
                    byte[] received = bufferHelp.array();
                    Main.logger.info("Получен чанк длины " + received.length + " байт от клиента " + clientAddress.toString());
                    byte validation = Utils.checkFirst4Bytes(received);
                    if (validation == 1) {
                        // Инициализационный чанк
                        byte[] size = Arrays.copyOfRange(received, 4, 8);
                        ChunksData chunksData = new ChunksData(Utils.getIntFromByteArray(size));
                        clientChunks.put(clientAddress.toString(), chunksData);
                    } else if (validation == 0) {
                        // Чанк с данными
                        if (!clientChunks.containsKey(clientAddress.toString()))
                            continue;
                        int index = Utils.getIntFromByteArray(Arrays.copyOfRange(received, 4, 8));
                        byte[] data = Arrays.copyOfRange(received, 8, received.length);
                        clientChunks.get(clientAddress.toString()).addChunk(index, data);
                        if (clientChunks.get(clientAddress.toString()).isReady()) {
                            byte[] request = clientChunks.get(clientAddress.toString()).getFullResponse();
                            Main.logger.info("Получен полный запрос клиента длиной в " + request.length + " байт.");
                            ServerCommand message = executeInput((ServerCommand)Utils.deserializeObject(request));
                            Main.logger.info("Отправляем ответ " + message.type + " клиенту " + clientAddress.toString());
                            List<byte[]> chunks = splitByteArray(Utils.serializeObject(message));
                            List<byte[]> firstInfo = new ArrayList<>();
                            firstInfo.add(new byte[] {1, 1, 1, 1});
                            firstInfo.add(Utils.intToBytes(chunks.size()));
                            ByteBuffer firstInfoSize = ByteBuffer.wrap(Utils.joinByteArrays(firstInfo));
                            // Инициализационный чанк (ответ)
                            channel.send(firstInfoSize, clientAddress);
                            Main.logger.info("Отправлен чанк длиной " + firstInfoSize.array().length + " байт клиенту " + clientAddress.toString());
                            // Отправка чанков с данными (ответ)
                            for (int i = 0; i < chunks.size(); i++) {
                                ArrayList<byte[]> chunkInfo = new ArrayList<>();
                                chunkInfo.add(new byte[] {0, 0, 0, 0});
                                chunkInfo.add(Utils.intToBytes(i));
                                chunkInfo.add(chunks.get(i));
                                ByteBuffer responseBuffer = ByteBuffer.wrap(Utils.joinByteArrays(chunkInfo));
                                channel.send(responseBuffer, clientAddress);
                                Main.logger.info("Отправлен чанк длиной " + responseBuffer.array().length + " байт клиенту " + clientAddress.toString());
                            }
                            clientChunks.remove(clientAddress.toString());
                        }
                    }
                }
            }
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
    public final ServerCommand executeInput(ServerCommand command) {
        if(!commands.containsKey(command.type))
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Неизвестная команда для сервера."));
        return commands.get(command.type).execute(command.data);
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
        } catch (IOException e) {
            return false;
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException ignored) {}
            }
        }
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

    /**
     * Gets json file path.
     *
     * @return the json file path
     */
    public String getJsonFilePath() {
        return jsonFilePath;
    }
}
