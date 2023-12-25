package src.commands;

import src.DataBase;

import java.util.ArrayList;
import java.util.Arrays;

public class RemoveLowerKey implements Command {

    private final DataBase dataBase;

    public RemoveLowerKey(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute(String[] args) {
        try {
            Long key = Long.parseLong(args[0]);
            ArrayList<Long> keys = new ArrayList<>();
            for (Long k : dataBase.getCollection().keySet())
                if (k < key)
                    keys.add(k);
            for (Long k : keys)
                dataBase.getCollection().remove(k);
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
