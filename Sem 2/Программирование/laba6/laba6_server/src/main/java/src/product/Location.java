package src.product;

import java.io.Serializable;

import static src.product.ExceptionMessages.*;

/**
 * The type Location.
 */
public class Location implements Serializable {

    private Integer x;
    private Double y;
    private String name;

    /**
     * Instantiates a new Location.
     *
     * @param x    the x (can't be null)
     * @param y    the y (can't be null)
     * @param name the name
     */
    public Location(Integer x, Double y, String name) {
        this(x, y);
        this.setName(name);
    }

    /**
     * Instantiates a new Location.
     *
     * @param x the x (can't be null)
     * @param y the y (can't be null)
     */
    public Location(Integer x, Double y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public Integer getX() {
        return x;
    }

    /**
     * Sets x (can't be null).
     *
     * @param x the x
     */
    public void setX(Integer x) {
        if (x != null) this.x = x;
        else throw new IllegalArgumentException(ILLEGAL_LOCATION_X_FIELD.toString());
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public Double getY() {
        return y;
    }

    /**
     * Sets y (can't be null).
     *
     * @param y the y
     */
    public void setY(Double y) {
        if (y != null) this.y = y;
        else throw new IllegalArgumentException(ILLEGAL_LOCATION_Y_FIELD.toString());
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{x = " + x + ", y = " + y + ", name = '" + name + "'}";
    }
}
