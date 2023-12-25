package src.commands;

import src.Utils;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.models.*;

public class Auth implements Command {
    @Override
    public ServerCommand execute(byte[] args, User caller) {
        if (caller == null)
            return new ServerCommand(ServerCommandType.ERROR, null);
        return new ServerCommand(ServerCommandType.AUTH, Utils.serializeObject(caller));
    }

    @Override
    public String getDescription() {
        return "авторизация";
    }
}
