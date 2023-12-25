package src.udp;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * The type Chunks data.
 */
public class ChunksData {
    private final int size;

    private SocketAddress clientAddress;

    private DatagramPacket port;

    private Map<Integer, byte[]> chunks = new ConcurrentSkipListMap<>();
    public ChunksData(int size) {
        this.size = size;
    }
    public void addChunk(int index, byte[] chunk) {
        if(chunks.size() > size) return;
        chunks.put(index, chunk);
    }
    public boolean isReady() {
        return chunks.size() == size;
    }
    public byte[] getFullResponse() {
        if(!isReady()) return null;
        List<byte[]> result = new ArrayList<>();
        for(Integer key : chunks.keySet())
            result.add(chunks.get(key));
        return joinByteArrays(result);
    }
    public static byte[] joinByteArrays(List<byte[]> chunks) {
        int totalLength = 0;
        for (byte[] chunk : chunks) {
            totalLength += chunk.length;
        }
        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] chunk : chunks) {
            System.arraycopy(chunk, 0, result, offset, chunk.length);
            offset += chunk.length;
        }
        return result;
    }
    public SocketAddress getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(SocketAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public DatagramPacket getPacket() {
        return port;
    }

    public void setPacket(DatagramPacket port) {
        this.port = port;
    }
    public int getSize() {
        return size;
    }
    public Map<Integer, byte[]> getChunks() {
        return chunks;
    }
}
