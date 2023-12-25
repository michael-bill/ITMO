package src.commands;

import src.DataBase;

public class Info implements Command {

    private final DataBase dataBase;

    public Info(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Тип коллекции: HashMap<Long, Person>");
        System.out.println("Дата инициализации: " + dataBase.getInitializationTime());
        System.out.println("Количество элементов: " + dataBase.getCollection().size());
        System.out.println("Автор: Студент группы P3116, Билошицкий Михаил Владимирович, ИСУ 367101");
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
