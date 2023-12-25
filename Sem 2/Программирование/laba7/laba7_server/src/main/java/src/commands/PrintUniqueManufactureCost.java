package src.commands;

import src.DataBase;
import src.models.Product;
import src.models.User;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.Utils;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The type Print unique manufacture cost.
 */
public class PrintUniqueManufactureCost implements Command {

    private final DataBase dataBase;

    /**
     * Instantiates a new Print unique manufacture cost.
     *
     * @param dataBase the data base
     */
    public PrintUniqueManufactureCost(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        HashSet<Integer> elements = new HashSet<Integer>(dataBase.getCollection()
                .values()
                .stream()
                .map(Product::getManufactureCost)
                .toList());
        ArrayList<String> result = new ArrayList<>();
        result.add("Уникальные значения поля manufactureCost всех элементов в коллекции:");
        result.add(elements.toString());
        return new ServerCommand(ServerCommandType.PRINT_UNIQUE_MANUFACTURE_COST, Utils.serializeObject(result));
    }

    @Override
    public String getDescription() {
        return "вывести уникальные значения поля manufactureCost всех элементов в коллекции";
    }
}
