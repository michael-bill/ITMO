package src.models;

import java.io.Serializable;

import static src.models.ExceptionMessages.ILLEGAL_COORDINATES_X_FIELD;

/**
 * The type Coordinates.
 */
public class Coordinates implements Serializable {

    private double x;
    private float y;

    /**
     * Instantiates a new Coordinates.
     *
     * @param x the x (max value is 511)
     * @param y the y
     */
    public Coordinates(double x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Sets x (max value is 511).
     *
     * @param x the x
     */
    public void setX(double x) {
        if (x <= 511) this.x = x;
        else throw new IllegalArgumentException(ILLEGAL_COORDINATES_X_FIELD.toString());
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{x = " + x + ", y = " + y + "}";
    }
}
