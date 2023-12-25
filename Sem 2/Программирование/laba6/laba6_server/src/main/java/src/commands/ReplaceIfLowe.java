package src.commands;

import src.ConnectionManager;
import src.DataBase;
import src.udp.Utils;
import src.product.Coordinates;
import src.product.Location;
import src.product.Person;
import src.product.Product;
import src.udp.Element;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;

import java.io.IOException;

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
    public ServerCommand execute(byte[] args) {
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
                                new Location(
                                        parsed.element.getOwner().getLocation().getX(),
                                        parsed.element.getOwner().getLocation().getY(),
                                        parsed.element.getOwner().getLocation().getName())));
            } catch (Exception e) {
                return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Некорректный формат ввода данных.\n" + e.getMessage()));
            }
            dataBase.insert(parsed.key, product);
            if (product.getId() < dataBase.getCollection().get(parsed.key).getId()) {
                    dataBase.getCollection().put(parsed.key, product);
            } else {
                return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Замена не была произведена, имя у нового объекта короче."));
            }
            return new ServerCommand(ServerCommandType.INSERT, new byte[] {1});
        } else {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Элемента с таким ключом не существует."));
        }
    }

    @Override
    public String getDescription() {
        return "заменить значение по ключу, если новое значение меньше старого (сравнение по name)";
    }
}
