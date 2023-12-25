package src.commands;

import src.CommandManager;
import src.ConnectionManager;
import src.models.*;
import src.udp.Element;
import src.udp.ServerRuntimeException;

/**
 * The type Insert.
 */
public class Insert implements Command, Insertable {

    private final CommandManager commandManager;
    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Insert command.
     *
     * @param commandManager the command manager
     */
    public Insert(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.connectionManager = commandManager.getConnectionManager();
    }

    @Override
    public void execute(String[] args) {
        Long key = null;
        try {
            key = Long.parseLong(args[0]);
        } catch (Exception e) {
            System.out.println("Данные введены некорректно, попробуйте еще раз.");
            return;
        }
        Product existed;
        try {
            existed = connectionManager.getByKey(key);
            if (existed != null) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Элемент с таким ключом уже существует.");
            return;
        } catch (ServerRuntimeException ignored) {}
        Product product = getProductFromStdout(commandManager.getScanner(), connectionManager.getUniqueID());
        product.setUser(connectionManager.getCurrentUser());
        Element element = new Element();
        element.key = key;
        element.element = product;
        if (connectionManager.insert(element)) {
            System.out.println("Product добавлен успешно.");
        }
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент с заданным ключом";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[] {"key"};
    }
}
