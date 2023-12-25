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

        for (int i = 0; i < chunks.size(); i++) {
            List<byte[]> chunkInfo = new ArrayList<>();
            chunkInfo.add(Utils.intToBytes(chunks.size()));
            chunkInfo.add(Utils.intToBytes(i));
            chunkInfo.add(chunks.get(i));
            ByteBuffer buffer = ByteBuffer.wrap(Utils.joinByteArrays(chunkInfo));
            channel.write(buffer);
        }
        ChunksData currentWorker = new ChunksData(Integer.MAX_VALUE);

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
                    byte[] recieved = helpBuffer.array();
                    int index = Utils.getIntFromByteArray(Arrays.copyOfRange(recieved, 4, 8));
                    byte[] data = Arrays.copyOfRange(recieved, 8, recieved.length);
                    if (currentWorker.getSize() != Integer.MAX_VALUE) {
                        currentWorker.addChunk(index, data);
                    } else {
                        int size = Utils.getIntFromByteArray(Arrays.copyOfRange(recieved, 0, 4));
                        currentWorker = new ChunksData(size);
                        currentWorker.addChunk(index, data);
                    }
                    if (currentWorker.isReady()) {
                        return currentWorker.getFullResponse();
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
}