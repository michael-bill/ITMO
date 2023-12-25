package src.models;

import java.io.Serializable;

/**
 * The enum Exception messages.
 */
public enum ExceptionMessages implements Serializable {

    /**
     * The Illegal product id field.
     */
    ILLEGAL_PRODUCT_ID_FIELD("Поле `Long id` класса `Product` не может быть null, Значение поля должно быть больше 0."),
    /**
     * The Illegal product name field.
     */
    ILLEGAL_PRODUCT_NAME_FIELD("Поле `String name` класса `Product` не может быть null, Строка не может быть пустой."),
    /**
     * The Illegal product coordinates field.
     */
    ILLEGAL_PRODUCT_COORDINATES_FIELD("Поле `Coordinates coordinates` класса `Product` не может быть null."),
    /**
     * The Illegal product price field.
     */
    ILLEGAL_PRODUCT_PRICE_FIELD("Значение поля `int price` класса `Product` должно быть больше 0."),
    /**
     * The Illegal product owner field.
     */
    ILLEGAL_PRODUCT_OWNER_FIELD("Поле `Person owner` класса `Product` не может быть null"),
    /**
     * Illegal coordinates x field exception messages.
     */
    ILLEGAL_COORDINATES_X_FIELD("Максимальное значение поля `X` класса `Coordinates`: 511."),
    /**
     * The Illegal person name field.
     */
    ILLEGAL_PERSON_NAME_FIELD("Поле `String name` класса `Person` не может быть null, Строка не может быть пустой."),
    /**
     * The Illegal person weight field.
     */
    ILLEGAL_PERSON_WEIGHT_FIELD("Поле `Long weight` класса `Person` не может быть null, Значение поля должно быть больше 0."),
    /**
     * The Illegal product eye color field.
     */
    ILLEGAL_PRODUCT_EYE_COLOR_FIELD("Поле `Color eyeColor` класса `Person` не может быть null"),
    /**
     * The Illegal product hair color field.
     */
    ILLEGAL_PRODUCT_HAIR_COLOR_FIELD("Поле `Color hairColor` класса `Person` не может быть null"),
    /**
     * The Illegal location x field.
     */
    ILLEGAL_LOCATION_X_FIELD("Поле `Integer x` класса `Location` не может быть null"),
    /**
     * The Illegal location y field.
     */
    ILLEGAL_LOCATION_Y_FIELD("Поле `Double y` класса `Location` не может быть null"),
    /**
     * Incorrect input exception messages.
     */
    INCORRECT_INPUT("Некорректный ввод. Попробуйте ввести поле еще раз."),
    /**
     * File not found exception messages.
     */
    FILE_NOT_FOUND("Файл не найден. Попробуйте создать файл и перезупустить программу."),
    /**
     * File input error exception messages.
     */
    FILE_INPUT_ERROR("Ошибка при записи данных в файл. Попробуйте исправить файл."),
    /**
     * File read error exception messages.
     */
    FILE_READ_ERROR("Ошибка при чтении данных из файла. Попробуйте исправить файл."),
    /**
     * Incorrect file path exception messages.
     */
    INCORRECT_FILE_PATH("Вы неверно указали файл для загрузки данных при запуске приложения."),
    /**
     * Env not found exception messages.
     */
    ENV_NOT_FOUND("Переменная окружения MY_JSON не была задана или была задана некорректно");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
