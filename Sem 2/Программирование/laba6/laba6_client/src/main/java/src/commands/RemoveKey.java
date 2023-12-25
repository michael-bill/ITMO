package src.commands;

import src.ConnectionManager;

/**
 * The type Remove key commmand.
 */
public class RemoveKey implements Command {

    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Remove key command.
     *
     * @param connectionManager the connection manager
     */
    public RemoveKey(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            Long key = Long.parseLong(args[0]);
            if (connectionManager.removeKey(key))
                System.out.println("Элемент удален по его ключу успешно.");
        } catch (Exception e) {
            System.out.println("Ключ команды введен некорректно, попробуйте еще раз.");
        }
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его ключу";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[] {"key"};
    }
}
