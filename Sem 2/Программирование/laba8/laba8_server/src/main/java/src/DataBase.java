package src;

import src.models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * The type Data base.
 */
public class DataBase {

    private final HashMap<Long, Product> collection;
    private ZonedDateTime initializationTime;
    private SQLManager sqlManager;

    public DataBase() {
        this.sqlManager = new SQLManager();
        collection = new HashMap<>();
        updateCollection();
    }

    public void updateCollection() {
        ResultSet resultSet = sqlManager.getRaw("SELECT product.*, users.username FROM product JOIN users ON product.user_id = users.id ORDER BY name ASC;");
        if (resultSet == null) return;
        collection.clear();
        try {
            while (resultSet.next()) {
                String owner_nationality = resultSet.getString("owner_nationality");
                String owner_location_name = resultSet.getString("owner_location_name");
                int manufacture_cost = resultSet.getInt("manufacture_cost");
                String unit_of_measure = resultSet.getString("unit_of_measure");
                Long key = resultSet.getLong("key");
                Product product = new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        new Coordinates(
                                resultSet.getDouble("coordinates_x"),
                                resultSet.getFloat("coordinates_y")),
                        resultSet.getInt("price"),
                        new Person(
                                resultSet.getString("owner_name"),
                                resultSet.getLong("owner_weight"),
                                Color.valueOf(resultSet.getString("owner_eyecolor")),
                                Color.valueOf(resultSet.getString("owner_haircolor")),
                                owner_nationality == null ? null : Country.valueOf(owner_nationality),
                                owner_location_name == null ? null :
                                        new Location(
                                                resultSet.getInt("owner_location_x"),
                                                resultSet.getDouble("owner_location_y"),
                                                owner_location_name
                                        )
                        ),
                        manufacture_cost == 0 ? null : manufacture_cost,
                        unit_of_measure == null ? null : UnitOfMeasure.valueOf(unit_of_measure),
                        new User(resultSet.getLong("user_id"), resultSet.getString("username"))
                );
                product.setCreationDate(ZonedDateTime.parse(resultSet.getString("creation_date")));
                if (!isIdExist(product.getId())) {
                    collection.put(key, product);
                }
                else {
                    System.out.println("Элемент с id = " + product.getId() + " уже существует и не был добавлен.");
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("При добавлении элемента произошла ошибка.");
            System.out.println(e.getMessage());
        }
        this.initializationTime = ZonedDateTime.now();
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
     *
     * @param key     the key
     * @param element the element
     */
    public void insert(Long key, Product element) {
        boolean result = false;
        try {
            PreparedStatement sql = sqlManager.getConnection().prepareStatement("INSERT INTO product "
                    + "(key, name, coordinates_x, coordinates_y, price, owner_name, owner_weight, owner_eyecolor, owner_haircolor, owner_nationality, owner_location_x, owner_location_y, owner_location_name, manufacture_cost, unit_of_measure, user_id, creation_date) VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            sql.setLong(1, key);
            sql.setString(2, element.getName());
            sql.setDouble(3, element.getCoordinates().getX());
            sql.setFloat(4, element.getCoordinates().getY());
            sql.setInt(5, element.getPrice());
            sql.setString(6, element.getOwner().getName());
            sql.setLong(7, element.getOwner().getWeight());
            sql.setString(8, element.getOwner().getEyeColor().toString());
            sql.setString(9, element.getOwner().getHairColor().toString());
            if (element.getOwner().getNationality() != null) {
                sql.setString(10, element.getOwner().getNationality().toString());
            } else {
                sql.setNull(10, Types.VARCHAR);
            }
            if (element.getOwner().getLocation() != null) {
                sql.setInt(11, element.getOwner().getLocation().getX());
                sql.setDouble(12, element.getOwner().getLocation().getY());
                sql.setString(13, element.getOwner().getLocation().getName());
            } else {
                sql.setNull(11, Types.INTEGER);
                sql.setNull(12, Types.DOUBLE);
                sql.setNull(13, Types.VARCHAR);
            }
            if (element.getManufactureCost() != null) {
                sql.setInt(14, element.getManufactureCost());
            } else {
                sql.setNull(14, Types.INTEGER);
            }
            if (element.getUnitOfMeasure() != null) {
                sql.setString(15, element.getUnitOfMeasure().toString());
            } else {
                sql.setNull(15,Types.VARCHAR);
            }
            sql.setLong(16, element.getUser().getId());
            sql.setString(17, element.getCreationDate().toString());
            result = sqlManager.send(sql);
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        if(!result) {
            throw new IllegalArgumentException("Не удалось добавить элемент в коллекцию");
        }
        updateCollection();
    }

    /**
     * Update.
     *
     * @param id      the id
     * @param element the element
     */
    public void update(Long id, Product element) {
        for (Long key : collection.keySet()) {
            if (collection.get(key).getId().equals(id)) {
                boolean result = false;
                try {
                    PreparedStatement sql = sqlManager.getConnection().prepareStatement("UPDATE product SET "
                            + "key = ?, name = ?, coordinates_x = ?, coordinates_y = ?, price = ?, owner_name = ?, owner_weight = ?, owner_eyecolor = ?, owner_haircolor = ?, owner_nationality = ?, owner_location_x = ?, owner_location_y = ?, owner_location_name = ?, manufacture_cost = ?, unit_of_measure = ?, user_id = ? WHERE id = ?;");
                    sql.setLong(1, key);
                    sql.setString(2, element.getName());
                    sql.setDouble(3, element.getCoordinates().getX());
                    sql.setFloat(4, element.getCoordinates().getY());
                    sql.setInt(5, element.getPrice());
                    sql.setString(6, element.getOwner().getName());
                    sql.setLong(7, element.getOwner().getWeight());
                    sql.setString(8, element.getOwner().getEyeColor().toString());
                    sql.setString(9, element.getOwner().getHairColor().toString());
                    if (element.getOwner().getNationality() != null) {
                        sql.setString(10, element.getOwner().getNationality().toString());
                    } else {
                        sql.setNull(10, Types.VARCHAR);
                    }
                    if (element.getOwner().getLocation() != null) {
                        sql.setInt(11, element.getOwner().getLocation().getX());
                        sql.setDouble(12, element.getOwner().getLocation().getY());
                        sql.setString(13, element.getOwner().getLocation().getName());
                    } else {
                        sql.setNull(11, Types.INTEGER);
                        sql.setNull(12, Types.DOUBLE);
                        sql.setNull(13, Types.VARCHAR);
                    }
                    if (element.getManufactureCost() != null) {
                        sql.setInt(14, element.getManufactureCost());
                    } else {
                        sql.setNull(14, Types.INTEGER);
                    }
                    if (element.getUnitOfMeasure() != null) {
                        sql.setString(15, element.getUnitOfMeasure().toString());
                    } else {
                        sql.setNull(15,Types.VARCHAR);
                    }
                    sql.setLong(16, element.getUser().getId());
                    sql.setLong(17, element.getId());
                    result = sqlManager.send(sql);
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
                if(!result)
                    throw new IllegalArgumentException("Не удалось добавить элемент в коллекцию");
                updateCollection();
                return;
            }
        }
    }

    /**
     * Remove key.
     *
     * @param key the key
     */
    public void removeKey(Long key) {
        boolean result = sqlManager.sendRaw("DELETE FROM product WHERE key IN (" + key + ");");
        if(!result)
            throw new IllegalArgumentException("Не удалось удалить элемент коллекции.");
        updateCollection();
    }

    /**
     * Clear.
     */
    public void clear(long userId) {
        boolean result = sqlManager.sendRaw("DELETE FROM product WHERE user_id IN (" + userId + ");");
        if(!result)
            throw new IllegalArgumentException("Не удалось очистить коллекцию.");
        updateCollection();
    }

    public void save() {
        sqlManager.sendRaw("DELETE FROM product;");
        for (Long key : collection.keySet()) {
            insert(key, collection.get(key));
        }
    }

    /**
     * Get product.
     *
     * @param id the id
     * @return the product
     */
    public Product get(Long id) {
        for (Long key : collection.keySet()) {
            if (collection.get(key).getId().equals(id)) {
                return collection.get(key);
            }
        }
        return null;
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

    public User authorizeUser(UserCredentials credentials) {
        if(credentials == null || credentials.getUsername() == null || credentials.getPassword() == null)
            return null;
        ResultSet resultSet = null;
        try {
            PreparedStatement sql = sqlManager.getConnection().prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            sql.setString(1, credentials.getUsername());
            sql.setString(2, Utils.hashPassword(credentials.getPassword()));
            resultSet = sqlManager.get(sql);
        }
        catch (SQLException ignored) {}
        if(resultSet == null)
            throw new IllegalArgumentException("Не удалось получить данные о пользователях.");
        try {
            while (resultSet.next())
                return new User(resultSet.getInt("id"), resultSet.getString("username"));
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean createUser(UserCredentials credentials) {
        try {
            PreparedStatement sql = sqlManager.getConnection().prepareStatement("INSERT INTO users (username, password) VALUES (?, ?);");
            sql.setString(1, credentials.getUsername());
            sql.setString(2, Utils.hashPassword(credentials.getPassword()));
            return sqlManager.send(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
