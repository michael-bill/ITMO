package src.commands;

import src.ConnectionManager;

public class Actualize implements Command {

    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Info command.
     *
     * @param connectionManager the connection manager
     */
    public Actualize(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void execute(String[] args) {
        connectionManager.actualize();
        System.out.println("Данные обновлены.");
    }

    @Override
    public String getDescription() {
        return "актуализировать данные коллекции";
    }
}
