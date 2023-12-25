package src.product;

import java.time.ZonedDateTime;
import static src.product.ExceptionMessages.*;

/**
 * The type Product.
 */
public class Product {

    private Long id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private int price;
    private Integer manufactureCost;
    private UnitOfMeasure unitOfMeasure;
    private Person owner;

    /**
     * Instantiates a new Product.
     *
     * @param id          the id (can't be null, id > 0, id is unique)
     * @param name        the name (can't be null)
     * @param coordinates the coordinates (can't be null)
     * @param price       the price (price > 0)
     * @param owner       the owner (can't be null)
     */
    public Product(Long id, String name, Coordinates coordinates, int price, Person owner) {
        this.setId(id);
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setPrice(price);
        this.setOwner(owner);
        this.setCreationDate(ZonedDateTime.now());
    }

    /**
     * Instantiates a new Product.
     *
     * @param id              the id (can't be null, id > 0, id is unique)
     * @param name            the name (can't be null)
     * @param coordinates     the coordinates (can't be null)
     * @param price           the price (price > 0)
     * @param owner           the owner (can't be null)
     * @param manufactureCost the manufacture cost
     * @param unitOfMeasure   the unit of measure
     */
    public Product(Long id, String name, Coordinates coordinates, int price, Person owner, Integer manufactureCost, UnitOfMeasure unitOfMeasure) {
        this(id, name, coordinates, price, owner);
        this.setManufactureCost(manufactureCost);
        this.setUnitOfMeasure(unitOfMeasure);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id (can't be null, id > 0, id is unique).
     *
     * @param id the id
     */
    public void setId(Long id) {
        if (id != null && id > 0) this.id = id;
        else throw new IllegalArgumentException(ILLEGAL_PRODUCT_ID_FIELD.toString());
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
     * Sets name (can't be null).
     *
     * @param name the name
     */
    public void setName(String name) {
        if (name != null && !name.equals("")) this.name = name;
        else throw new IllegalArgumentException(ILLEGAL_PRODUCT_NAME_FIELD.toString());
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates (can't be null).
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates != null) this.coordinates = coordinates;
        else throw new IllegalArgumentException(ILLEGAL_PRODUCT_COORDINATES_FIELD.toString());
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creation date (can't be null, generates automatically in class constructor).
     *
     * @param creationDate the creation date
     */
    private void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets price (price > 0).
     *
     * @param price the price
     */
    public void setPrice(int price) {
        if (price > 0) this.price = price;
        else throw new IllegalArgumentException(ILLEGAL_PRODUCT_PRICE_FIELD.toString());
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * Sets owner (can't be null).
     *
     * @param owner the owner
     */
    public void setOwner(Person owner) {
        if (owner != null) this.owner = owner;
        else throw new IllegalArgumentException(ILLEGAL_PRODUCT_OWNER_FIELD.toString());
    }

    /**
     * Gets manufacture cost.
     *
     * @return the manufacture cost
     */
    public Integer getManufactureCost() {
        return manufactureCost;
    }

    /**
     * Sets manufacture cost.
     *
     * @param manufactureCost the manufacture cost
     */
    public void setManufactureCost(Integer manufactureCost) {
        this.manufactureCost = manufactureCost;
    }

    /**
     * Gets unit of measure.
     *
     * @return the unit of measure
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets unit of measure.
     *
     * @param unitOfMeasure the unit of measure
     */
    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }


    /**
     * Just toString() function.
     *
     */
    @Override
    public String toString() {
        return "Product:" +
                "\nid = " + id +
                "\nname = '" + name + '\'' +
                "\ncoordinates = " + coordinates +
                "\ncreationDate = " + creationDate +
                "\nprice = " + price +
                "\nmanufactureCost = " + manufactureCost +
                "\nunitOfMeasure = " + unitOfMeasure +
                "\nowner = " + String.join("\n\t", owner.toString().split("\n")) +
                '}';
    }
}
