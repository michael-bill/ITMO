package src.commands;

import src.ConnectionManager;
import src.DataBase;
import src.models.Coordinates;
import src.models.Person;
import src.models.Product;
import src.models.User;
import src.udp.Element;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.Utils;

/**
 * The type Replace if lowe.
 */
public class ReplaceIfLowe implements Command {

    private final ConnectionManager connectionManager;
    private final DataBase dataBase;

    /**
     * Instantiates a new Replace if lowe.
     *
     * @param connectionManager the connection manager
     */
    public ReplaceIfLowe(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.dataBase = connectionManager.getDataBase();
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        Element parsed = null;
        try {
            parsed = (Element) Utils.deserializeObject(args);
        } catch (Exception e) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Получены некорректные данные об объекте."));
        }
        if (dataBase.isKeyExist(parsed.key)) {
            Product product = null;
            try {
                product = new Product(
                        parsed.element.getId(),
                        parsed.element.getName(),
                        new Coordinates(
                                parsed.element.getCoordinates().getX(),
                                parsed.element.getCoordinates().getY()),
                        parsed.element.getPrice(),
                        new Person(
                                parsed.element.getOwner().getName(),
                                parsed.element.getOwner().getWeight(),
                                parsed.element.getOwner().getEyeColor(),
                                parsed.element.getOwner().getHairColor(),
                                parsed.element.getOwner().getNationality(),
                                parsed.element.getOwner().getLocation()),
                        parsed.element.getManufactureCost(),
                        parsed.element.getUnitOfMeasure(),
                        parsed.element.getUser());
            } catch (Exception e) {
                return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Некорректный формат ввода данных.\n" + e.getMessage()));
            }
            if (product.getName().length() < dataBase.getCollection().get(parsed.key).getName().length()) {
                if (dataBase.getCollection().get(parsed.key).getUser().getId() == caller.getId())
                    dataBase.update(parsed.key, product);
                else
                    return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Элемент коллекции вам не принадлежит и вы не можете его модифицировать."));
            } else {
                return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Замена не была произведена, имя у нового объекта длина name не меньше."));
            }
            return new ServerCommand(ServerCommandType.INSERT, new byte[] {1});
        } else {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Элемента с таким ключом не существует."));
        }
    }

    @Override
    public String getDescription() {
        return "заменить значение по ключу, если новое значение меньше старого (сравнение по длине name)";
    }
}
