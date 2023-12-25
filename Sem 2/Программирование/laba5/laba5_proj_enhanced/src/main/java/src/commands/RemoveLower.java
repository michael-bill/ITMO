package src.commands;

import src.DataBase;

import java.util.ArrayList;

public class RemoveLower implements Command {

    private final DataBase dataBase;

    public RemoveLower(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute(String[] args) {
        try {
            Long id = Long.parseLong(args[0]);
            ArrayList<Long> keys = new ArrayList<>();
            for (Long key : dataBase.getCollection().keySet())
                if (dataBase.getCollection().get(key).getId() < id)
                    keys.add(key);
            for (Long k : keys)
                dataBase.getCollection().remove(k);
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
