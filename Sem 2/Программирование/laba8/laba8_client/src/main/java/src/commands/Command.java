package src.commands;

public interface Command {
    /**
    * Method called when a command executed
    * @param args Given arguments
    */
    void execute(String[] args);

    /**
    * Getting a description of command for command list
    * @return Description in command list
    */
    String getDescription();

    /**
    * Getting a required arguments for command
    * @return Argument names
    */
    default String[] getArgumentNames() {
        return new String[0];
    }
}
