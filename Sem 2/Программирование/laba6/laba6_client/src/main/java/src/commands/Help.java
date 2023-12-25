package src.commands;

import src.CommandManager;

/**
 * The type Help command.
 */
public class Help implements Command {

    private final CommandManager commandManager;

    /**
     * Instantiates a new Help command.
     *
     * @param commandManager the command manager
     */
    public Help(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {
        for(String commandName : commandManager.getCommands().keySet()) {
            System.out.print(commandName);
            Command command = commandManager.getCommands().get(commandName);
            for (String argument : command.getArgumentNames())
                System.out.print(" [" + argument + "]");
            System.out.println(": " + command.getDescription());
        }
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
