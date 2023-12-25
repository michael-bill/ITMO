package src.commands;

import src.Interpreter;

public class Help implements Command {

    private final Interpreter interpreter;

    public Help(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public void execute(String[] args) {
        for(String commandName : interpreter.getCommands().keySet()) {
            System.out.print(commandName);
            Command command = interpreter.getCommands().get(commandName);
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
