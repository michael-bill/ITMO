package src.commands;

import src.ConnectionManager;
import src.product.Product;

/**
 * The type Min by name command.
 */
public class MinByName implements Command {

    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Min by name command.
     *
     * @param connectionManager the connection manager
     */
    public MinByName(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void execute(String[] args) {
        Product result = connectionManager.minByName();
        if (result != null)
            System.out.println(result);
    }

    @Override
    public String getDescription() {
        return "вывести любой объект из коллекции, значение поля name которого является минимальным (сравнение по длине строки)";
    }
}
