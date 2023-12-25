package src;

import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;
import src.product.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * The type Data base.
 */
public class DataBase {

    private final HashMap<Long, Product> collection;
    private ZonedDateTime initializationTime;

    /**
     * Instantiates a new Data base.
     *
     * @param collection the collection
     */
    public DataBase(HashMap<Long,Product> collection) {
        this.collection = collection;
        initializationTime = ZonedDateTime.now();
    }

    /**
     * Instantiates a new Data base.
     *
     * @param jsonFilePath the json file path
     * @throws FileNotFoundException the file not found exception
     */
    public DataBase(String jsonFilePath) throws FileNotFoundException {
        collection = new HashMap<>();
        Scanner scanner = new Scanner(new FileInputStream(jsonFilePath));
        StringBuilder jsonString = new StringBuilder();
        while (scanner.hasNext())
            jsonString.append(scanner.next());
        if (jsonString.toString().trim().length() == 0) {
            scanner.close();
            return;
        }
        JSONObject jsonObject = new JSONObject(jsonString.toString());
        for (var name : jsonObject.names()) {
            try {
                JSONObject productJson = (JSONObject) jsonObject.get(name.toString());
                JSONObject coordinatesJson = (JSONObject) productJson.get("coordinates");
                JSONObject personJson = (JSONObject) productJson.get("owner");
                JSONObject locationJson = null;
                try { locationJson = (JSONObject) personJson.get("location"); } catch (Exception ignored) {}
                Object nationality = null;
                try { nationality = personJson.get("nationality"); } catch (Exception ignored) {}
                Object manufactureCost = null;
                try { manufactureCost = productJson.get("manufactureCost"); } catch (Exception ignored) {}
                Object unitOfMeasure = null;
                try { unitOfMeasure = productJson.get("unitOfMeasure"); } catch (Exception ignored) {}
                Product product = new Product(
                        Long.parseLong(productJson.get("id").toString()),
                        productJson.get("name").toString(),
                        new Coordinates(
                                Double.parseDouble(coordinatesJson.get("x").toString()),
                                Float.parseFloat(coordinatesJson.get("y").toString())),
                        Integer.parseInt(productJson.get("price").toString()),
                        new Person(
                                personJson.get("name").toString(),
                                Long.parseLong(personJson.get("weight").toString()),
                                Color.valueOf(personJson.get("eyeColor").toString()),
                                Color.valueOf(personJson.get("hairColor").toString()),
                                nationality == null ? null : Country.valueOf(nationality.toString()),
                                locationJson == null ? null :
                                        new Location(
                                                Integer.parseInt(locationJson.get("x").toString()),
                                                Double.parseDouble(locationJson.get("y").toString()),
                                                locationJson.get("name").toString()
                                        )
                        ),
                        manufactureCost == null ? null : Integer.parseInt(manufactureCost.toString()),
                        unitOfMeasure == null ? null : UnitOfMeasure.valueOf(unitOfMeasure.toString())
                );
                if (!isIdExist(product.getId()))
                    collection.put(Long.parseLong(name.toString()), product);
                else
                    System.out.println("Элемент с id = " + product.getId() + " уже существует и не был добавлен.");
            } catch (IllegalArgumentException e) {
                System.out.println("При добавлении элемента произошла ошибка.");
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
        initializationTime = ZonedDateTime.now();
    }

    /**
     * Info.
     * Output information about the collection (type, initialization date, number of items, etc.) to the standard output stream.
     */
    public void info() {
        System.out.println("Тип коллекции: HashMap<Long, Person>");
        System.out.println("Дата инициализации: " + initializationTime);
        System.out.println("Количество элементов: " + collection.size());
        System.out.println("Автор: Студент группы P3116, Билошицкий Михаил Владимирович, ИСУ 367101");
    }

    /**
     * Show.
     * Output to the standard output stream all elements of the collection in string representation.
     */
    public void show() {
        for (Long key : collection.keySet()) {
            System.out.println("Key = " + key);
            System.out.println(collection.get(key) + "\n");
        }
    }

    /**
     * Insert.
     * Add a new element with the specified key.
     *
     * @param key     the key
     * @param element the element
     */
    public void insert(Long key, Product element) {
        collection.put(key, element);
    }

    /**
     * Update.
     * Update the value of the collection item whose id is equal to the given one.
     *
     * @param id      the id
     * @param element the element
     */
    public void update(Long id, Product element) {
        for (Long key : collection.keySet()) {
            if (collection.get(key).getId().equals(id)) {
                collection.put(key, element);
            }
        }
    }

    /**
     * Remove key.
     * Remove an item from the collection by its key.
     *
     * @param key the key
     */
    public void removeKey(Long key) {
        collection.remove(key);
    }

    /**
     * Clear.
     * Clear the collection.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Save.
     * Save the collection to a file.
     *
     * @param jsonFilePath the json file path
     * @throws IOException the io exception
     */
    public void save(String jsonFilePath) throws IOException {
        String json = new JSONObject(collection).toString();
        try (FileOutputStream fileOutputStream = new FileOutputStream((jsonFilePath));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
            bufferedOutputStream.write(json.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Remove lower.
     * Remove from the collection all items smaller than the specified id.
     *
     * @param id the id
     */
    public void removeLower(Long id) {
        for (Long key : collection.keySet()) {
            if (collection.get(key).getId() < id) {
                collection.remove(key);
            }
        }
    }

    /**
     * Replace if lowe.
     * Replace the value by the key, if the new value is smaller than the old value (compare by id) (key and value are entered on a new line).
     *
     * @param key     the key
     * @param element the element
     */
    public void replaceIfLowe(Long key, Product element) {
        if (element.getId() < collection.get(key).getId()) {
            collection.put(key, element);
        }
    }

    /**
     * Remove lower key.
     * Remove from the collection all items whose key is less than the specified one.
     *
     * @param key the key
     */
    public void removeLowerKey(Long key) {
        for (Long k : collection.keySet()) {
            if (k < key) {
                collection.remove(k);
            }
        }
    }

    /**
     * Min by name.
     * Output any object from the collection whose name field value is the minimum (comparison by string length).
     */
    public void minByName() {
        Product minNameProduct = null;
        int minLenName = Integer.MAX_VALUE;
        for (Product product : collection.values()) {
            if (minLenName > product.getName().length()) {
                minNameProduct = product;
                minLenName = product.getName().length();
            }
        }
        System.out.println(minNameProduct);
    }

    /**
     * Group counting by price.
     * Group the items of the collection by the value of the price field, print the number of items in each group.
     */
    public void groupCountingByPrice() {
        HashMap<Integer, Integer> groups = new HashMap<>();
        for (Product product : collection.values()) {
            if (!groups.containsKey(product.getPrice())) {
                groups.put(product.getPrice(), 1);
            } else {
                groups.put(product.getPrice(), groups.get(product.getPrice()) + 1);
            }
        }
        int i = 1;
        for (Integer price : groups.keySet()) {
            System.out.println(i + ". Группа с ценой " + price + ". Количество: " + groups.get(price));
            i++;
        }
    }

    /**
     * Print unique manufacture cost.
     * Output the unique values of the manufactureCost field of all elements in the collection.
     */
    public void printUniqueManufactureCost() {
        HashSet<Integer> elements = new HashSet<Integer>(collection
                        .values()
                        .stream()
                        .map(Product::getManufactureCost)
                        .toList());
        System.out.println("Уникальные значения поля manufactureCost всех элементов в коллекции:");
        System.out.println(elements);
    }

    /**
     * Gets unique id.
     *
     * @return the unique id
     */
    public Long getUniqueId() {
        if (collection.size() > 0) {
            List<Long> ids = collection.values().stream().map(Product::getId).sorted().toList();
            return ids.get(ids.size() - 1) + 1;
        } else return 1L;
    }

    /**
     * Is id exist boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean isIdExist(Long id) {
        for (Product product : collection.values()) {
            if (product.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is key exist boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean isKeyExist(Long key) {
        return collection.containsKey(key);
    }
}
