package src.commands;

import src.ConnectionManager;
import src.DataBase;
import src.models.User;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.Utils;

import java.util.ArrayList;

/**
 * The type Remove lower key.
 */
public class RemoveLowerKey implements Command {

    private final ConnectionManager connectionManager;
    private final DataBase dataBase;

    /**
     * Instantiates a new Remove lower key.
     *
     * @param connectionManager the connection manager
     */
    public RemoveLowerKey(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.dataBase = connectionManager.getDataBase();
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        try {
            Long key = (Long) Utils.deserializeObject(args);
            ArrayList<Long> keys = new ArrayList<>();
            for (Long k : dataBase.getCollection().keySet())
                if (k < key)
                    keys.add(k);
            for (Long k : keys) {
                if (dataBase.getCollection().get(k).getUser().getId() == caller.getId())
                    dataBase.removeKey(k);
            }
            return new ServerCommand(ServerCommandType.REMOVE_LOWER_KEY, new byte[] {1});
        } catch (Exception e) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Ключ команды введен некорректно, попробуйте еще раз."));
        }
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, ключ которых меньше, чем заданный";
    }
}
