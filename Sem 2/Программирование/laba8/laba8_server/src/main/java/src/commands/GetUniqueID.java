package src.commands;

import src.DataBase;
import src.Utils;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.models.*;

/**
 * The type Get unique id.
 */
public class GetUniqueID implements Command {

    private final DataBase dataBase;

    /**
     * Instantiates a new Get unique id.
     *
     * @param dataBase the data base
     */
    public GetUniqueID(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        return new ServerCommand(ServerCommandType.GET_UNIQUE_ID, Utils.serializeObject(dataBase.getUniqueId()));
    }

    @Override
    public String getDescription() {
        return "возвращает уникальный id для создания нового элемента.";
    }
}
