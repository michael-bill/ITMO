package src.commands;

import src.udp.ServerCommand;

import java.io.*;

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
    ServerCommand execute(byte[] args);

    /**
     * Gets description.
     *
     * @return the description
     */
    String getDescription();
}
