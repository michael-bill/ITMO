package src.commands;

public class Exit implements Command {
    @Override
    public void execute(String[] args) {
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }
}
