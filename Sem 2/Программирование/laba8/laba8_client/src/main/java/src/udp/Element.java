package src.udp;

import src.models.Product;

import java.io.Serializable;

/**
 * The type Element.
 * Auxiliary class for sending object and its
 * key at the same time for commands like Update, insert.
 */
public class Element implements Serializable  {
    /**
     * The Key.
     */
    public Long key;
    /**
     * The Element.
     */
    public Product element;

    @Override
    public String toString() {
        return "Key = " + key + "\n" + element;
    }
}
