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
     * Gets collection.
     *
     * @return the collection
     */
    public HashMap<Long, Product> getCollection() {
        return collection;
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

    /**
     * Gets initialization time.
     *
     * @return the initialization time
     */
    public ZonedDateTime getInitializationTime() {
        return initializationTime;
    }
}
