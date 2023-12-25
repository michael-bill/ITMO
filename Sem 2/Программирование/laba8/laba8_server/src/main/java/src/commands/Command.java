package src.commands;

import src.models.User;
import src.udp.ServerCommand;

/**
 * The interface Command.
 */
public interface Command {
    /**
     * Execute server command.
     *
     * @param args the args
     * @return the server command
     */
    ServerCommand execute(byte[] args, User caller);

    /**
     * Gets description.
     *
     * @return the description
     */
    String getDescription();
}
