package src.commands;

import src.DataBase;

public class Clear implements Command {

    private final DataBase dataBase;

    public Clear(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute(String[] args) {
        dataBase.clear();
        System.out.println("Коллекция очищена успешно.");
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
