package src.commands;

import src.ConnectionManager;
import src.DataBase;
import src.models.User;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.Utils;

import java.util.ArrayList;

/**
 * The type Remove lower.
 */
public class RemoveLower implements Command {

    private final ConnectionManager connectionManager;
    private final DataBase dataBase;

    /**
     * Instantiates a new Remove lower.
     *
     * @param connectionManager the connection manager
     */
    public RemoveLower(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.dataBase = connectionManager.getDataBase();
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        try {
            Long id = (Long) Utils.deserializeObject(args);
            ArrayList<Long> keys = new ArrayList<>();
            for (Long key : dataBase.getCollection().keySet())
                if (dataBase.getCollection().get(key).getId() < id)
                    keys.add(key);
            for (Long k : keys)
                if (dataBase.getCollection().get(k).getUser().getId() == caller.getId())
                    dataBase.removeKey(k);
            return new ServerCommand(ServerCommandType.REMOVE_LOWER, new byte[] {1});
        } catch (Exception e) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Id введен некорректно, попробуйте еще раз."));
        }
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие, чем заданный id";
    }
}
