package src.commands;

import src.DataBase;
import src.product.Product;

public class MinByName implements Command {

    private final DataBase dataBase;

    public MinByName(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute(String[] args) {
        Product minNameProduct = null;
        int minLenName = Integer.MAX_VALUE;
        for (Product product : dataBase.getCollection().values()) {
            if (minLenName > product.getName().length()) {
                minNameProduct = product;
                minLenName = product.getName().length();
            }
        }
        System.out.println(minNameProduct);
    }

    @Override
    public String getDescription() {
        return "вывести любой объект из коллекции, значение поля name которого является минимальным (сравнение по длине строки)";
    }
}
