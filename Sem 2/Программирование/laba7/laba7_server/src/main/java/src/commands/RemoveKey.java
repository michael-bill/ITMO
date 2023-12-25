package src.commands;

import src.ConnectionManager;
import src.DataBase;
import src.models.User;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.Utils;

/**
 * The type Remove key.
 */
public class RemoveKey implements Command {

    private final ConnectionManager connectionManager;
    private final DataBase dataBase;

    /**
     * Instantiates a new Remove key.
     *
     * @param connectionManager the connection manager
     */
    public RemoveKey(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.dataBase = connectionManager.getDataBase();
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        try {
            Long key = (Long) Utils.deserializeObject(args);
            if (dataBase.isKeyExist(key)) {
                if (dataBase.getCollection().get(key).getUser().getId() == caller.getId())
                    dataBase.removeKey(key);
                else
                    return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Элемент коллекции вам не принадлежит и вы не можете его удалить."));
            } else {
                return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Элемента с таким ключом не существует."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Ключ команды введен некорректно, попробуйте еще раз."));
        }
        return new ServerCommand(ServerCommandType.REMOVE_KEY, new byte[] {1});
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его ключу";
    }
}
