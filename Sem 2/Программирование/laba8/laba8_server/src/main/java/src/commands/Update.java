package src.commands;

import src.ConnectionManager;
import src.DataBase;
import src.Utils;
import src.udp.Element;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.models.*;

/**
 * The type Update.
 */
public class Update implements Command {

    private final ConnectionManager connectionManager;
    private final DataBase dataBase;

    /**
     * Instantiates a new Update.
     *
     * @param connectionManager the connection manager
     */
    public Update(ConnectionManager connectionManager) {
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
        if (dataBase.isIdExist(parsed.element.getId())) {
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
            dataBase.update(parsed.element.getId(), product);
            return new ServerCommand(ServerCommandType.INSERT, new byte[] {1});
        } else {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Элемента с таким id не существует."));
        }
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

}
