package src.models;

import java.io.Serializable;

import static src.models.ExceptionMessages.*;

/**
 * The type Person.
 */
public class Person implements Serializable {

    private String name;
    private Long weight;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;
    private Location location;

    /**
     * Instantiates a new Person.
     *
     * @param name        the name (can't be null, and equals "")
     * @param weight      the weight (can't be null)
     * @param eyeColor    the eye color (can't be null)
     * @param hairColor   the hair color (can't be null)
     * @param nationality the nationality
     * @param location    the location
     */
    public Person(String name, Long weight, Color eyeColor, Color hairColor, Country nationality, Location location) {
        this(name, weight, eyeColor, hairColor);
        this.setNationality(nationality);
        this.setLocation(location);
    }

    /**
     * Instantiates a new Person.
     *
     * @param name      the name (can't be null, and equals "")
     * @param weight    the weight (can't be null)
     * @param eyeColor  the eye color (can't be null)
     * @param hairColor the hair color (can't be null)
     */
    public Person(String name, Long weight, Color eyeColor, Color hairColor) {
        this.setName(name);
        this.setWeight(weight);
        this.setEyeColor(eyeColor);
        this.setHairColor(hairColor);
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
        if (name != null && !name.equals("")) this.name = name;
        else throw new IllegalArgumentException(ILLEGAL_PERSON_NAME_FIELD.toString());
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public Long getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(Long weight) {
        if (weight != null && weight > 0) this.weight = weight;
        else throw new IllegalArgumentException(ILLEGAL_PERSON_WEIGHT_FIELD.toString());
    }

    /**
     * Gets eye color.
     *
     * @return the eye color
     */
    public Color getEyeColor() {
        return eyeColor;
    }

    /**
     * Sets eye color.
     *
     * @param eyeColor the eye color
     */
    public void setEyeColor(Color eyeColor) {
        if (eyeColor != null) this.eyeColor = eyeColor;
        else throw new IllegalArgumentException(ILLEGAL_PRODUCT_EYE_COLOR_FIELD.toString());
    }

    /**
     * Gets hair color.
     *
     * @return the hair color
     */
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * Sets hair color.
     *
     * @param hairColor the hair color
     */
    public void setHairColor(Color hairColor) {
        if (hairColor != null) this.hairColor = hairColor;
        else throw new IllegalArgumentException(ILLEGAL_PRODUCT_HAIR_COLOR_FIELD.toString());
    }

    /**
     * Gets nationality.
     *
     * @return the nationality
     */
    public Country getNationality() {
        return nationality;
    }

    /**
     * Sets nationality.
     *
     * @param nationality the nationality
     */
    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "{" +
                "\nname = '" + name + '\'' +
                "\nweight = " + weight +
                "\neyeColor = " + eyeColor +
                "\nhairColor = "  + hairColor +
                "\nnationality = " + nationality +
                "\nlocation = " + location +
                '}';
    }
}
