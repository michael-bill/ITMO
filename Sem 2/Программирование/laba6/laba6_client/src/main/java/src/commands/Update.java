package src.commands;

import src.CommandManager;
import src.ConnectionManager;
import src.product.Product;
import src.udp.Element;

/**
 * The type Update command.
 */
public class Update implements Command, Insertable {

    private final CommandManager commandManager;
    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Update command.
     *
     * @param commandManager the command manager
     */
    public Update(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.connectionManager = commandManager.getConnectionManager();
    }

    @Override
    public void execute(String[] args) {
        Long id = null;
        try {
            id = Long.parseLong(args[0]);
        } catch (Exception e) {
            System.out.println("Данные введены некорректно, попробуйте еще раз.");
        }
        Product existed;
        try {
            existed = connectionManager.get(id);
            if (existed == null) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            // Ошибка напечатается ответом от сервера
            return;
        }
        Product product = getProductFromStdout(commandManager.getScanner(), id);
        Element element = new Element();
        element.key = 0L;
        element.element = product;
        if (connectionManager.update(element)) {
            System.out.println("Product обновлен успешно.");
        }
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[] {"id"};
    }
}
