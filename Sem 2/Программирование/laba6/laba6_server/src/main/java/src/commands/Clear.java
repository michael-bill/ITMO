package src.commands;

import src.ConnectionManager;
import src.DataBase;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.udp.Utils;

import java.io.IOException;

/**
 * The type Clear.
 */
public class Clear implements Command {

    private final ConnectionManager connectionManager;
    private final DataBase dataBase;

    /**
     * Instantiates a new Clear.
     *
     * @param connectionManager the connection manager
     */
    public Clear(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.dataBase = connectionManager.getDataBase();
    }

    @Override
    public ServerCommand execute(byte[] args) {
        dataBase.clear();
        return new ServerCommand(ServerCommandType.CLEAR, new byte[] {1});
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
