package src.udp;

import java.io.Serializable;
import src.models.*;

/**
 * The type Server command.
 */
public class ServerCommand implements Serializable {
    /**
     * The Type of server command.
     */
    public ServerCommandType type;
    /**
     * The Data of command.
     */
    public byte[] data;

    /**
     * The User credentials.
     */
    public UserCredentials userCredentials;

    /**
     * Instantiates a new Server command.
     *
     * @param type            the type
     * @param data            the data
     * @param userCredentials the user credentials
     */
    public ServerCommand(ServerCommandType type, byte[] data, UserCredentials userCredentials) {
        this.type = type;
        this.data = data;
        this.userCredentials = userCredentials;
    }
    public ServerCommand(ServerCommandType type, byte[] data) {
        this.type = type;
        this.data = data;
    }
}
