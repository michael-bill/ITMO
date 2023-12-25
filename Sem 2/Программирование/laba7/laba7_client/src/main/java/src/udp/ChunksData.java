package src.udp;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * The type Chunks data.
 * Any request from the server in the format of a certain sequence of chunks
 */
public class ChunksData {
    private DatagramPacket info;

    private final int size;
    private Map<Integer, byte[]> chunks = new ConcurrentSkipListMap<>();
    private long initializationTime = (new Date()).getTime();
    public ChunksData(int size) {
        this.size = size;
    }
    public synchronized void addChunk(int index, byte[] chunk) {
        if(chunks.size() > size) return;
        chunks.put(index, chunk);
    }
    public boolean isReady() {
        return chunks.size() >= size;
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
    public boolean isActual() {
        return initializationTime + 5000 > (new Date()).getTime();
    }
    public DatagramPacket getInfo() {
        return info;
    }

    public void setInfo(DatagramPacket info) {
        this.info = info;
    }

    public int getSize() {
        return size;
    }
}
