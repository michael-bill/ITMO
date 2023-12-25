package src.commands;

import src.ConnectionManager;

/**
 * The type Clear command.
 */
public class Clear implements Command {

    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Clear command.
     *
     * @param connectionManager the connection manager
     */
    public Clear(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (connectionManager.clear()) {
            System.out.println("Коллекция очищена успешно.");
        }
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
