package src.commands;

import src.DataBase;
import src.models.User;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;

public class Actualize implements Command {

    private final DataBase dataBase;

    public Actualize(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        dataBase.updateCollection();
        return new ServerCommand(ServerCommandType.ACTUALIZE, null);
    }

    @Override
    public String getDescription() {
        return "актуализировать данные коллекции";
    }
}
