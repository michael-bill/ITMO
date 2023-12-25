package src.commands;

import src.ConnectionManager;
import src.udp.Element;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Show command.
 */

public class Show implements Command {

    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Show command.
     *
     * @param connectionManager the connection manager
     */
    public Show(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void execute(String[] args) {
        ArrayList<Element> result = connectionManager.show();
        result.forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
