package src;

import src.product.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static src.product.ExceptionMessages.*;

/**
 * The type Interpreter.
 */
public class Interpreter {

    private final String jsonFilePath;
    private final Stack<String> stackFilePaths;

    /**
     * The Data base.
     * What the interpreter works with.
     */
    DataBase dataBase;
    /**
     * The Active.
     * The variable tells you whether the interpreter expects input or not.
     */
    boolean active;

    /**
     * Instantiates a new Interpreter.
     *
     * @param dataBase the database
     */
    public Interpreter(DataBase dataBase, String jsonFilePath) {
        this.dataBase = dataBase;
        this.jsonFilePath = jsonFilePath;
        this.active = true;
        this.stackFilePaths = new Stack<>();
    }

    /**
     * Parse command.
     *
     * @param command the command
     * @param in      the in
     */
    public void parseCommand(String command, Scanner in) {
        String[] commandSplit = command.split(" ");
        String head = commandSplit[0];
        switch (head) {
            case "help" -> help();
            case "info" -> dataBase.info();
            case "show" -> dataBase.show();
            case "insert" -> {
                Long key = correctInputLong(in, "Введите ключ типа данных `Long`: ");
                if (!dataBase.isKeyExist(key)) {
                    try {
                        Product product = getProductFromStdout(in);
                        dataBase.insert(key, product);
                        System.out.println("Элемент добавлен успешно.");
                    } catch (Exception e) {
                        System.out.println("Некорректный формат ввода данных.");
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Элемент с таким ключом уже существует.");
                }
            }
            case "update" -> {
                Long id = correctInputLong(in, "Введите `id` элемента типа данных `Long`: ");
                if (dataBase.isIdExist(id)) {
                    try {
                        Product product = getProductFromStdout(in);
                        dataBase.update(id, product);
                        System.out.println("Элемент обновлен по его id успешно.");
                    } catch (Exception e) {
                        System.out.println("Некорректный формат ввода данных.");
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Элемент с таким id не существует.");
                }
            }
            case "remove_key" -> {
                try {
                    Long key = Long.parseLong(commandSplit[1]);
                    if (dataBase.isKeyExist(key)) {
                        dataBase.removeKey(key);
                    } else {
                        System.out.println("Элемента с таким ключом не существует.");
                    }
                } catch (Exception e) {
                    System.out.println("Ключ команды введен некорректно, попробуйте еще раз.");
                }
            }
            case "clear" -> {
                dataBase.clear();
                System.out.println("Коллекция очищена успешно.");
            }
            case "save" -> {
                try {
                    dataBase.save(jsonFilePath);
                } catch (IOException e) {
                    if (Files.exists(Path.of(jsonFilePath)))
                        System.out.println(FILE_INPUT_ERROR);
                    else
                        System.out.println("Файл не найден. Попробуйте создать файл c именем " + jsonFilePath);
                }
            }
            case "execute_script" -> {
                try {
                    String absPath = "";
                    if (commandSplit.length > 2)
                        for (int i = 1; i < commandSplit.length; i++)
                            absPath += " " + commandSplit[i];
                    else absPath = commandSplit[1];
                    absPath = absPath.trim();
                    absPath = (new File(absPath)).getAbsolutePath();
                    Scanner scanner = new Scanner(new FileInputStream(absPath)).useDelimiter("\n");
                    while (scanner.hasNext()) {
                        String c = scanner.next();
                        if (c.contains("execute_script") && stackFilePaths.contains(absPath)) {
                            System.out.println("execute_script команда недопсутима в исполнении из скрипта и была пропущена во избежании рекурсии.");
                            continue;
                        } else if (!stackFilePaths.contains(absPath)) {
                            stackFilePaths.add(absPath);
                        }
                        parseCommand(c, scanner);
                    }
                    stackFilePaths.remove(absPath);
                    scanner.close();
                    System.out.println("Файл выполнен успешно.");
                } catch (IOException e) {
                    if (Files.exists(Path.of(jsonFilePath)))
                        System.out.println(FILE_INPUT_ERROR);
                    else
                        System.out.println(FILE_NOT_FOUND.toString().replaceAll("программу", "команду."));
                }
            }
            case "exit" -> {
                active = false;
                System.exit(0);
            }
            case "remove_lower" -> {
                try {
                    Long id = Long.parseLong(commandSplit[1]);
                    dataBase.removeLower(id);
                    System.out.println("Соответствующие элементы удалены успешно.");
                } catch (Exception e) {
                    System.out.println("Id введен некорректно, попробуйте еще раз.");
                }
            }
            case "replace_if_lowe" -> {
                Long key = correctInputLong(in, "Введите ключ типа данных `Long`: ");
                if (dataBase.isKeyExist(key)) {
                    try {
                        Product product = getProductFromStdout(in);
                        dataBase.replaceIfLowe(key, product);
                        System.out.println("Команда выполнена успешно.");
                    } catch (Exception e) {
                        System.out.println("Некорректный формат ввода данных.");
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Элемента с таким ключом не существует.");
                }
            }
            case "remove_lower_key" -> {
                try {
                    Long key = Long.parseLong(commandSplit[1]);
                    dataBase.removeLowerKey(key);
                    System.out.println("Соответствующие элементы удалены успешно.");
                } catch (Exception e) {
                    System.out.println("Ключ команды введен некорректно, попробуйте еще раз.");
                }
            }
            case "min_by_name" -> dataBase.minByName();
            case "group_counting_by_price" -> dataBase.groupCountingByPrice();
            case "print_unique_manufacture_cost" -> dataBase.printUniqueManufactureCost();
            default -> {
                System.out.println("Команда " + command + " мне неизвестна.");
            }
        }
    }

    private Product getProductFromStdout(Scanner in) {
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
        return new Product(dataBase.getUniqueId(), name, coordinates, price, owner, manufactureCost, unitOfMeasure);
    }


    /**
     * Correct input for String.
     * Allows you to correctly enter data from the console, even if the wrong user can not do it the first time.
     *
     * @param in Scanner
     * @param message Message to input
     * @return the String value
     */
    private String correctInput(Scanner in, String message) {
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
     * Correct input for Double.
     * Allows you to correctly enter data from the console, even if the wrong user can not do it the first time.
     *
     * @param in Scanner
     * @param message Message to input
     * @return the Double value
     */
    private Double correctInputDouble(Scanner in, String message) {
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
     * Correct input for Float.
     * Allows you to correctly enter data from the console, even if the wrong user can not do it the first time.
     *
     * @param in Scanner
     * @param message Message to input
     * @return the Float value
     */
    private Float correctInputFloat(Scanner in, String message) {
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
     * Correct input for Integer.
     * Allows you to correctly enter data from the console, even if the wrong user can not do it the first time.
     *
     * @param in Scanner
     * @param message Message to input
     * @param necessarily If false, the function will return null from the empty string.
     * @return the Integer value.
     */
    private Integer correctInputInteger(Scanner in, String message, boolean necessarily, boolean moreThanZero) {
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
     * Correct input for Long.
     * Allows you to correctly enter data from the console, even if the wrong user can not do it the first time.
     *
     * @param in Scanner
     * @param message Message to input
     * @return the Long value.
     */
    private Long correctInputLong(Scanner in, String message) {
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
     * Correct input for Enum.
     * Allows you to correctly enter data from the console, even if the wrong user can not do it the first time.
     *
     * @param in Scanner
     * @param values Enum's String values
     * @param message Message to input
     * @param necessarily If false, the function will return null from the empty string.
     * @return the String value of Enum name.
     */
    private String correctInputEnum(Scanner in, List<String> values, String message, boolean necessarily) {
        String result = "";
        while (true) {
            System.out.print(message);
            result = in.next();
            if (!necessarily && result.equals("")) return null;
            if (values.contains(result)) break;
            System.out.println(INCORRECT_INPUT);
        }
        return result;
    }

    /**
     * Help.
     */
    public void help() {
        System.out.println(
            """
            Доступные команды интепретатора:
            
            help : вывести справку по доступным командам
            info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
            show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
            insert {key} {element} : добавить новый элемент с заданным ключом (ключ и значение вводятся с новой строки)
            update {id} {element} : обновить значение элемента коллекции, id которого равен заданному
            remove_key {key} : удалить элемент из коллекции по его ключу
            clear : очистить коллекцию
            save : сохранить коллекцию в файл
            execute_script {file_name} : считать и исполнить скрипт из указанного файла. В скрипте должны содержатся команды в таком же виде, в котором вы их вводите в интерактивном режиме.
            exit : завершить программу (без сохранения в файл)
            remove_lower {id} : удалить из коллекции все элементы, меньшие, чем заданный id
            replace_if_lowe {key} {element} : заменить значение по ключу, если новое значение меньше старого (сравнение по id) (ключ и значение вводятся с новой строки)
            remove_lower_key {key} : удалить из коллекции все элементы, ключ которых меньше, чем заданный
            min_by_name : вывести любой объект из коллекции, значение поля name которого является минимальным (сравнение по длине строки)
            group_counting_by_price : сгруппировать элементы коллекции по значению поля price, вывести количество элементов в каждой группе
            print_unique_manufacture_cost : вывести уникальные значения поля manufactureCost всех элементов в коллекции
            """
        );
    }
}
