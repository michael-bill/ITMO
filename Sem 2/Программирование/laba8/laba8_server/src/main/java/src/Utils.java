package src;

import src.models.Country;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * The type Utils.
 */
public class Utils {

    /**
     * Serialize object byte [ ].
     *
     * @param o the o
     * @return the byte [ ]
     */
    public static byte[] serializeObject(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        byte[] result = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.flush();
            result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Deserialize object object.
     *
     * @param b the b
     * @return the object
     */
    public static Object deserializeObject(byte[] b) {
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        ObjectInput in = null;
        Object result = null;
        try {
            in = new ObjectInputStream(bis);
            result = in.readObject();
        } catch (IOException ignored) {}
        catch (ClassNotFoundException e) {e.printStackTrace();}
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {}
        }
        return result;
    }

    /**
     * Check first 4 bytes byte.
     *
     * @param arr the arr
     * @return the byte
     */
    public static byte checkFirst4Bytes(byte[] arr) {
        if (arr.length < 4) return (byte)255;
        if (arr[0] == arr[1] && arr[1] == arr[2] && arr[2] == arr[3])
            return arr[0];
        return (byte)255;
    }

    /**
     * Int to bytes byte [ ].
     *
     * @param i the
     * @return the byte [ ]
     */
    public static byte[] intToBytes(int i) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
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

    /**
     * Gets int from byte array.
     *
     * @param bytes the bytes
     * @return the int from byte array
     */
    public static int getIntFromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    /**
     * Gets long from byte array.
     *
     * @param bytes the bytes
     * @return the long from byte array
     */
    public static long getLongFromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
