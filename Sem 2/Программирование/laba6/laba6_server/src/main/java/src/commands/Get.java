package src.commands;

import src.DataBase;
import src.product.Product;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.udp.Utils;

/**
 * The type Get.
 */
public class Get implements Command {

    private final DataBase dataBase;

    /**
     * Instantiates a new Get.
     *
     * @param dataBase the data base
     */
    public Get(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ServerCommand execute(byte[] args) {
        Product existed;
        try {
            existed = dataBase.get((Long)Utils.deserializeObject(args));
            if (existed == null)
                throw new IllegalArgumentException();
        } catch(Exception ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Объект с указанным id не найден."));
        }
        return new ServerCommand(ServerCommandType.GET, Utils.serializeObject(existed));
    }

    @Override
    public String getDescription() {
        return "получить элемент коллекции по id.";
    }
}
