package src.commands;

import src.DataBase;
import src.product.Product;

import java.util.HashMap;

public class GroupCountingByPrice implements Command {

    private final DataBase dataBase;

    public GroupCountingByPrice(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute(String[] args) {
        HashMap<Integer, Integer> groups = new HashMap<>();
        for (Product product : dataBase.getCollection().values()) {
            if (!groups.containsKey(product.getPrice())) {
                groups.put(product.getPrice(), 1);
            } else {
                groups.put(product.getPrice(), groups.get(product.getPrice()) + 1);
            }
        }
        int i = 1;
        for (Integer price : groups.keySet()) {
            System.out.println(i + ". Группа с ценой " + price + ". Количество: " + groups.get(price));
            i++;
        }
    }

    @Override
    public String getDescription() {
        return "сгруппировать элементы коллекции по значению поля price, вывести количество элементов в каждой группе";
    }
}
