package src.commands;

import src.ConnectionManager;

/**
 * The type Remove lower command.
 */
public class RemoveLower implements Command {

    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Remove lower command.
     *
     * @param connectionManager the connection manager
     */
    public RemoveLower(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            Long id = Long.parseLong(args[0]);
            if (connectionManager.removeLower(id))
                System.out.println("Соответствующие элементы удалены успешно.");
        } catch (Exception e) {
            System.out.println("Id введен некорректно, попробуйте еще раз.");
        }
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие, чем заданный id";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[] {"id"};
    }
}
