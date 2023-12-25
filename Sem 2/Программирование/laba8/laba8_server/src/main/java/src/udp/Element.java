package src.udp;

import src.models.Product;

import java.io.Serializable;

/**
 * The type Element.
 */
public class Element implements Serializable {
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
