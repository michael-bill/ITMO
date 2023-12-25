package src.commands;

import src.DataBase;
import src.Utils;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.models.*;
public class Register implements Command {

    private final DataBase dataBase;

    public Register(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        if (caller != null)
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Плохие дела делаешь брат..."));
        UserCredentials credentials = (UserCredentials)Utils.deserializeObject(args);
        if (dataBase.createUser(credentials))
            return new ServerCommand(ServerCommandType.REGISTER, null);
        return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Пользователь с данным именем уже существует"));
    }

    @Override
    public String getDescription() {
        return "регистрация";
    }
}
