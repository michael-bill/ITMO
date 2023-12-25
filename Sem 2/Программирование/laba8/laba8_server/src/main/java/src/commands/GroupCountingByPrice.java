package src.commands;

import src.DataBase;
import src.Utils;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.models.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Group counting by price.
 */
public class GroupCountingByPrice implements Command {

    private final DataBase dataBase;

    /**
     * Instantiates a new Group counting by price.
     *
     * @param dataBase the data base
     */
    public GroupCountingByPrice(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        HashMap<Integer, Integer> groups = new HashMap<>();
        for (Product product : dataBase.getCollection().values()) {
            if (!groups.containsKey(product.getPrice())) {
                groups.put(product.getPrice(), 1);
            } else {
                groups.put(product.getPrice(), groups.get(product.getPrice()) + 1);
            }
        }
        ArrayList<String> result = new ArrayList<>();
        int i = 1;
        for (Integer price : groups.keySet()) {
            result.add(i + ". Группа с ценой " + price + ". Количество: " + groups.get(price));
            i++;
        }
        return new ServerCommand(ServerCommandType.GROUP_COUNTING_BY_PRICE, Utils.serializeObject(result));
    }

    @Override
    public String getDescription() {
        return "сгруппировать элементы коллекции по значению поля price, вывести количество элементов в каждой группе";
    }
}
