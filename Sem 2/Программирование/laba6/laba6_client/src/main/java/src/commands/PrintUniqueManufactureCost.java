package src.commands;

import src.ConnectionManager;

import java.util.ArrayList;

/**
 * The type Print unique manufacture cost command.
 */
public class PrintUniqueManufactureCost implements Command {

    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Print unique manufacture cost command.
     *
     * @param connectionManager the connection manager
     */
    public PrintUniqueManufactureCost(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void execute(String[] args) {
        ArrayList<String> result = connectionManager.printUniqueManufactureCost();
        result.forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "вывести уникальные значения поля manufactureCost всех элементов в коллекции";
    }
}
