package src.commands;

import src.ConnectionManager;

import java.util.ArrayList;

/**
 * The type Group counting by price command.
 */
public class GroupCountingByPrice implements Command {

    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Group counting by price command.
     *
     * @param connectionManager the connection manager
     */
    public GroupCountingByPrice(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void execute(String[] args) {
        ArrayList<String> result = connectionManager.groupCountingByPrice();
        result.forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "сгруппировать элементы коллекции по значению поля price, вывести количество элементов в каждой группе";
    }
}
