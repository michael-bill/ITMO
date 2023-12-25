package src.commands;

import src.ConnectionManager;

/**
 * The type Remove lower key command.
 */
public class RemoveLowerKey implements Command {

    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Remove lower key command.
     *
     * @param connectionManager the connection manager
     */
    public RemoveLowerKey(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            Long key = Long.parseLong(args[0]);
            if (connectionManager.removeLowerKey(key))
                System.out.println("Соответствующие элементы удалены успешно.");
        } catch (Exception e) {
            System.out.println("Ключ команды введен некорректно, попробуйте еще раз.");
        }
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, ключ которых меньше, чем заданный";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[] {"key"};
    }
}
