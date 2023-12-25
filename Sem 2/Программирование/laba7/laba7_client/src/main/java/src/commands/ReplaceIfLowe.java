package src.commands;

import src.CommandManager;
import src.ConnectionManager;
import src.models.Product;
import src.udp.Element;

/**
 * The type Replace if lowe command.
 */
public class ReplaceIfLowe implements Command, Insertable {

    private final CommandManager commandManager;
    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Replace if lowe command.
     *
     * @param commandManager the command manager
     */
    public ReplaceIfLowe(CommandManager commandManager) {
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
        if (connectionManager.getByKey(key) != null) {
            try {
                Product product = getProductFromStdout(commandManager.getScanner(), connectionManager.getUniqueID());
                product.setUser(connectionManager.getCurrentUser());
                Element element = new Element();
                element.key = key;
                element.element = product;
                if (connectionManager.replaceIfLowe(element)) {
                    System.out.println("Замена произведена успешно.");
                }
                System.out.println("Команда выполнена успешно.");
            } catch (Exception e) {
                System.out.println("Некорректный формат ввода данных.");
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String getDescription() {
        return "заменить значение по ключу, если новое значение меньше старого (сравнение по длине name)";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[] {"key"};
    }
}
