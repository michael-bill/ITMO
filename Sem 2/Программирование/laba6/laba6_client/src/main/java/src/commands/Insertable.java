package src.commands;

import src.product.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static src.product.ExceptionMessages.ILLEGAL_COORDINATES_X_FIELD;
import static src.product.ExceptionMessages.INCORRECT_INPUT;

/**
 * The interface Insertable.
 */
public interface Insertable {

    /**
     * Gets product element from stdout with all inspections.
     *
     * @param in       the in
     * @param uniqueId the unique id
     * @return the product from stdout
     */
    default Product getProductFromStdout(Scanner in, Long uniqueId) {
        System.out.println("Заполните значения полей класса Product. (если поле помечено 'не обязательно', то для пропуска нажмите enter)");
        String name = correctInput(in, "Введите значение поля `name` типа данных `String`: ");
        System.out.println("Ввод значений поля `coordinates` класса `Coordinates`.");
        double xCoordinates = 1000;
        while (xCoordinates > 511) {
            xCoordinates = correctInputDouble(in, "Введите значение поля `x` типа данных `double`: ");
            if (xCoordinates > 511) System.out.println(ILLEGAL_COORDINATES_X_FIELD);
        }
        float yCoordinates = correctInputFloat(in, "Введите значение поля `y` типа данных `float`: ");
        Coordinates coordinates = new Coordinates(xCoordinates, yCoordinates);
        System.out.println("Конец ввода значений поля `coordinates` типа данных `Coordinates`");
        Integer priceT = correctInputInteger(in, "Введите значение поля `price` типа данных `int` (должно быть больше 0): ", true, true);
        int price = priceT == null ? 0 : priceT;
        System.out.println("Ввод значений поля `owner` типа данных `Person`.");
        String namePerson = correctInput(in, "Введите значение поля `name` типа данных `String`: ");
        Long weightPerson = correctInputLong(in, "Введите значение поля `weight` типа данных `Long`: ");
        Color eyeColorPerson = Color.valueOf(correctInputEnum(in,
                Arrays.stream(Color.values()).map(Enum::name).toList(),
                "Введите значение поля `eyeColor` типа данных `Color`\n" +
                        Arrays.toString(Color.values()) + ": ", true));
        Color hairColorPerson = Color.valueOf(correctInputEnum(in,
                Arrays.stream(Color.values()).map(Enum::name).toList(),
                "Введите значение поля `hairColor` типа данных `Color`\n" +
                        Arrays.toString(Color.values()) + ": ", true));
        String temp = correctInputEnum(in,
                Arrays.stream(Country.values()).map(Enum::name).toList(),
                "Введите значение поля `nationality` типа данных `Country` (не обязательно)\n" +
                        Arrays.toString(Country.values()) + ": ", false);
        Country nationalityPerson = temp == null ? null : Country.valueOf(temp);
        System.out.println("Ввод значений поля `location` типа данных `Location` (не обязательно).");
        System.out.print("Введите значение поля `x` типа данных `Integer`: ");
        temp = in.next();
        Location locationPerson = null;
        if (!temp.equals("")) {
            Integer xLocationPerson = null;
            try {
                xLocationPerson = Integer.parseInt(temp);
            } catch (Exception e) {
                System.out.println(INCORRECT_INPUT);
                boolean correct = false;
                while (!correct) {
                    try {
                        System.out.print("Введите значение поля `x` типа данных `Integer`: ");
                        xLocationPerson = in.nextInt();
                        correct = true;
                    } catch (Exception exception) {
                        System.out.println(INCORRECT_INPUT);
                    }
                }
            }
            Double yLocationPerson = correctInputDouble(in, "Введите значение поля `y` типа данных `Double`: ");
            String nameLocationPerson = correctInput(in, "Введите значение поля `name` типа данных `String`: ");
            locationPerson = new Location(xLocationPerson, yLocationPerson, nameLocationPerson);
        }
        System.out.println("Конец ввода значений поля `location` типа данных `Location`.");
        Person owner = new Person(namePerson, weightPerson, eyeColorPerson, hairColorPerson, nationalityPerson, locationPerson);
        System.out.println("Конец ввода значений поля `owner` типа данных `Person`.");
        Integer manufactureCost = correctInputInteger(in, "Введите значение поля `manufactureCost` типа данных `Integer` (не обязательно): ", false, false);
        temp = correctInputEnum(in,
                Arrays.stream(UnitOfMeasure.values()).map(Enum::name).toList(),
                "Введите значение поля `unitOfMeasure` типа данных `UnitOfMeasure` (не обязательно)\n" +
                        Arrays.toString(UnitOfMeasure.values()) + ": ", false);
        UnitOfMeasure unitOfMeasure = temp == null ? null : UnitOfMeasure.valueOf(temp);
        return new Product(uniqueId, name, coordinates, price, owner, manufactureCost, unitOfMeasure);
    }

    /**
     * Correct input string.
     *
     * @param in      the in
     * @param message the message
     * @return the string
     */
    default String correctInput(Scanner in, String message) {
        boolean correct = false;
        String result = "";
        while (!correct) {
            try {
                System.out.print(message);
                result = in.next().trim();
                if (result.equals("")) throw new Exception();
                correct = true;
            }
            catch (NoSuchElementException e) {
                System.out.println("Ввод завершен, завершаю программу.");
                System.exit(0);
            }
            catch (Exception e) {
                System.out.println(INCORRECT_INPUT);
            }
        }
        return result;
    }

    /**
     * Correct input double double.
     *
     * @param in      the in
     * @param message the message
     * @return the double
     */
    default Double correctInputDouble(Scanner in, String message) {
        boolean correct = false;
        Double result = null;
        while (!correct) {
            try {
                System.out.print(message);
                String input = in.next();
                result = Double.parseDouble(input);
                correct = true;
            }
            catch (NoSuchElementException e) {
                System.out.println("Ввод завершен, завершаю программу.");
                System.exit(0);
            }
            catch (Exception e) {
                System.out.println(INCORRECT_INPUT);
            }
        }
        return result;
    }

    /**
     * Correct input float float.
     *
     * @param in      the in
     * @param message the message
     * @return the float
     */
    default Float correctInputFloat(Scanner in, String message) {
        boolean correct = false;
        Float result = null;
        while (!correct) {
            try {
                System.out.print(message);
                String input = in.next();
                result = Float.parseFloat(input);
                correct = true;
            }
            catch (NoSuchElementException e) {
                System.out.println("Ввод завершен, завершаю программу.");
                System.exit(0);
            }
            catch (Exception e) {
                System.out.println(INCORRECT_INPUT);
            }
        }
        return result;
    }

    /**
     * Correct input integer integer.
     *
     * @param in           the in
     * @param message      the message
     * @param necessarily  the necessarily
     * @param moreThanZero the more than zero
     * @return the integer
     */
    default Integer correctInputInteger(Scanner in, String message, boolean necessarily, boolean moreThanZero) {
        boolean correct = false;
        Integer result = null;
        while (!correct) {
            try {
                System.out.print(message);
                String input = in.next();
                if (!necessarily && input.equals("")) return null;
                result = Integer.parseInt(input);
                if (moreThanZero && result <= 0) throw new Exception();
                correct = true;
            }
            catch (NoSuchElementException e) {
                System.out.println("Ввод завершен, завершаю программу.");
                System.exit(0);
            }
            catch (Exception e) {
                System.out.println(INCORRECT_INPUT);
            }
        }
        return result;
    }

    /**
     * Correct input long.
     *
     * @param in      the in
     * @param message the message
     * @return the long
     */
    default Long correctInputLong(Scanner in, String message) {
        boolean correct = false;
        Long result = null;
        while (!correct) {
            try {
                System.out.print(message);
                String input = in.next();
                result = Long.parseLong(input);
                correct = true;
            }
            catch (NoSuchElementException e) {
                System.out.println("Ввод завершен, завершаю программу.");
                System.exit(0);
            }
            catch (Exception e) {
                System.out.println(INCORRECT_INPUT);
            }
        }
        return result;
    }

    /**
     * Correct input enum string.
     *
     * @param in          the in
     * @param values      the values
     * @param message     the message
     * @param necessarily the necessarily
     * @return the string
     */
    default String correctInputEnum(Scanner in, List<String> values, String message, boolean necessarily) {
        String result = "";
        while (true) {
            System.out.print(message);
            try {
                result = in.next();
            } catch (NoSuchElementException e) {
                System.out.println("Ввод завершен, завершаю программу.");
                System.exit(0);
            }
            if (!necessarily && result.equals("")) return null;
            if (values.contains(result)) break;
            System.out.println(INCORRECT_INPUT);
        }
        return result;
    }
}
