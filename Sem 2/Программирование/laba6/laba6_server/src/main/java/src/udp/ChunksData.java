package src.udp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Chunks data.
 */
public class ChunksData {

    private final int size;
    private Map<Integer, byte[]> chunks = new TreeMap<>();

    /**
     * Instantiates a new Chunks data.
     *
     * @param size the size
     */
    public ChunksData(int size) {
        this.size = size;
    }

    /**
     * Add chunk.
     *
     * @param index the index
     * @param chunk the chunk
     */
    public void addChunk(int index, byte[] chunk) {
        if (chunks.size() > size) return;
        chunks.put(index, chunk);
    }

    /**
     * Is ready boolean.
     *
     * @return the boolean
     */
    public boolean isReady() {
        return chunks.size() == size;
    }

    /**
     * Get full response byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getFullResponse() {
        if (!isReady()) return null;
        List<byte[]> result = new ArrayList<>();
        for(Integer key : chunks.keySet())
            result.add(chunks.get(key));
        return joinByteArrays(result);
    }

    /**
     * Join byte arrays byte [ ].
     *
     * @param chunks the chunks
     * @return the byte [ ]
     */
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
}
