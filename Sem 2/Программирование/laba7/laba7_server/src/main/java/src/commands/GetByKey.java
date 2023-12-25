package src.commands;

import src.DataBase;
import src.models.Product;
import src.models.User;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.Utils;

/**
 * The type Get by key.
 */
public class GetByKey implements Command {

    private final DataBase dataBase;

    /**
     * Instantiates a new Get by key.
     *
     * @param dataBase the data base
     */
    public GetByKey(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        Product existed;
        try {
            Long key = (Long) Utils.deserializeObject(args);
            existed = dataBase.getCollection().get(key);
            if (existed == null)
                throw new Exception();
            return new ServerCommand(ServerCommandType.GET_BY_KEY, Utils.serializeObject(existed));
        } catch (Exception ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Объект с указанным key не найден."));
        }
    }

    @Override
    public String getDescription() {
        return "получить элемент коллекции по key.";
    }
}
