package src.udp;

import java.io.Serializable;

/**
 * The type Server command.
 */
public class ServerCommand implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L-10;
    /**
     * The Type of server command.
     */
    public ServerCommandType type;
    /**
     * The Data of command.
     */
    public byte[] data;

    /**
     * Instantiates a new Server command.
     *
     * @param type the type
     * @param data the data
     */
    public ServerCommand(ServerCommandType type, byte[] data) {
        this.type = type;
        this.data = data;
    }
}
