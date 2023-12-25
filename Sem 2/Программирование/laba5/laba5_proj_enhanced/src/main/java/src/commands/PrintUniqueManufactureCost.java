package src.commands;

import src.DataBase;
import src.product.Product;

import java.util.HashSet;

public class PrintUniqueManufactureCost implements Command {

    private final DataBase dataBase;

    public PrintUniqueManufactureCost(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute(String[] args) {
        HashSet<Integer> elements = new HashSet<Integer>(dataBase.getCollection()
                .values()
                .stream()
                .map(Product::getManufactureCost)
                .toList());
        System.out.println("Уникальные значения поля manufactureCost всех элементов в коллекции:");
        System.out.println(elements);
    }

    @Override
    public String getDescription() {
        return "вывести уникальные значения поля manufactureCost всех элементов в коллекции";
    }
}
