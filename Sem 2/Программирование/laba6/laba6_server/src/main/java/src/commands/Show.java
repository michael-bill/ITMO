package src.commands;

import src.DataBase;
import src.product.Product;
import src.udp.Utils;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * The type Show.
 */
public class Show implements Command {

    private final DataBase dataBase;

    /**
     * Instantiates a new Show.
     *
     * @param dataBase the data base
     */
    public Show(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ServerCommand execute(byte[] args) {
        int size = dataBase.getCollection().size();
        if (size == 0)
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Коллекция пуста."));
        ArrayList<String> result = (ArrayList<String>) dataBase.getCollection().entrySet().stream().sorted(Comparator.comparing(o -> o.getValue().getName())).map(x -> {
            String line = "";
            line += ("Key = " + x.getKey() + "\n");
            line += (x.getValue() + "\n");
            return line;
        }).toList();
        return new ServerCommand(ServerCommandType.SHOW, Utils.serializeObject(result));
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
