package src.commands;

import src.DataBase;

public class RemoveKey implements Command {

    private final DataBase dataBase;

    public RemoveKey(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute(String[] args) {
        try {
            Long key = Long.parseLong(args[0]);
            if (dataBase.isKeyExist(key)) {
                dataBase.removeKey(key);
            } else {
                System.out.println("Элемента с таким ключом не существует.");
            }
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
