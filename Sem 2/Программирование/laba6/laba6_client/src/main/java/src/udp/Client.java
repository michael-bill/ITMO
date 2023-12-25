package src.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

/**
 * The type Client.
 */
public class Client {
    private DatagramChannel channel = null;
    private Selector selector = null;
    /**
     * The constant PACKAGE_SIZE.
     */
    public static final int PACKAGE_SIZE = 5 * 1024 - 8;

    /**
     * Instantiates a new Client.
     *
     * @throws IOException the io exception
     */
    public Client() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        int bufferSize = PACKAGE_SIZE + 100;
        channel.setOption(StandardSocketOptions.SO_RCVBUF, bufferSize);
        channel.setOption(StandardSocketOptions.SO_SNDBUF, bufferSize);

        Scanner scanner = new Scanner(System.in);
        int port;
        while (true) {
            System.out.print("Введите порт сервера: ");
            try {
                port = Integer.parseInt(scanner.nextLine());
                if (available(port)) {
                    channel.connect(new InetSocketAddress("localhost", port));
                    break;
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                System.out.println("Порт не подходит.");
            }
        }
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * Send message byte [ ].
     *
     * @param message the message
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public byte[] sendMsg(byte[] message) throws IOException {
        List<byte[]> chunks = splitByteArray(message);
        List<byte[]> firstInfo = new ArrayList<>();
        firstInfo.add(new byte[] {1, 1, 1, 1});
        firstInfo.add(Utils.intToBytes(chunks.size()));
        ByteBuffer initializationChunk = ByteBuffer.wrap(Utils.joinByteArrays(firstInfo));
        channel.write(initializationChunk);
        for (int i = 0; i < chunks.size(); i++) {
            ArrayList<byte[]> chunkInfo = new ArrayList<>();
            chunkInfo.add(new byte[] {0, 0, 0, 0});
            chunkInfo.add(Utils.intToBytes(i));
            chunkInfo.add(chunks.get(i));
            ByteBuffer dataChunk = ByteBuffer.wrap(Utils.joinByteArrays(chunkInfo));
            channel.write(dataChunk);
        }
        ChunksData currentWorker = new ChunksData(1);
        while (currentWorker.isActual()) {
            selector.selectNow();
            // Обрабатываем готовые ключи
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                if (currentWorker.isReady())
                    continue;
                SelectionKey key = it.next();
                it.remove();
                // Если ключ готов к чтению
                if (key.isReadable()) {
                    ByteBuffer helpBuffer = ByteBuffer.allocate(PACKAGE_SIZE + 8);
                    channel.receive(helpBuffer);
                    byte[] received = helpBuffer.array();
                    byte validation = Utils.checkFirst4Bytes(received);
                    if (validation == 1) {
                        byte[] size = Arrays.copyOfRange(received, 4, 8);
                        currentWorker = new ChunksData(Utils.getIntFromByteArray(size));
                    } else if (validation == 0) {
                        int index = Utils.getIntFromByteArray(Arrays.copyOfRange(received, 4, 8));
                        byte[] data = Arrays.copyOfRange(received, 8, received.length);
                        currentWorker.addChunk(index, data);
                        if (currentWorker.isReady()) {
                            byte[] response = currentWorker.getFullResponse();
                            return response;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Available port boolean.
     *
     * @param port the port
     * @return the boolean
     */
    public static boolean available(int port) {
        return port >= 1023 && port <= 65534;
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
        ArrayList<byte[]> chunks = new ArrayList<>();
        for (int i = 0; i <= (int)Math.ceil((double) source.length / PACKAGE_SIZE); i++) {
            chunks.add(Arrays.copyOfRange(source, i * PACKAGE_SIZE, (i + 1) * PACKAGE_SIZE));
        }
        return chunks;
    }
}