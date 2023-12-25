package src.commands;

import src.ConnectionManager;

import java.util.ArrayList;

/**
 * The type Info command.
 */
public class Info implements Command {

    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Info command.
     *
     * @param connectionManager the connection manager
     */
    public Info(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void execute(String[] args) {
        ArrayList<String> result = connectionManager.info();
        result.forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции";
    }
}
