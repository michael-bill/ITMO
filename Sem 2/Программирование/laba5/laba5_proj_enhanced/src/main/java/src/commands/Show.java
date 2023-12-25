package src.commands;

import src.DataBase;

public class Show implements Command {

    private final DataBase dataBase;

    public Show(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute(String[] args) {
        for (Long key : dataBase.getCollection().keySet()) {
            System.out.println("Key = " + key);
            System.out.println(dataBase.getCollection().get(key) + "\n");
        }
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
