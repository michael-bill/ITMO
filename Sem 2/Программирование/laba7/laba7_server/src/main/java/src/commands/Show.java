package src.commands;

import src.DataBase;
import src.models.User;
import src.udp.Element;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.Utils;

import java.util.ArrayList;
import java.util.Comparator;

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
    public ServerCommand execute(byte[] args, User caller) {
        int size = dataBase.getCollection().size();
        if (size == 0)
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Коллекция пуста."));
        ArrayList<Element> result = new ArrayList<>(
                dataBase.getCollection()
                        .entrySet()
                        .stream()
                        .sorted(Comparator.comparing(o -> o.getValue().getName()))
                        .map(x -> {
                                    Element e = new Element();
                                    e.key = x.getKey();
                                    e.element = x.getValue();
                                    return e;
                                }
                        ).toList());
        return new ServerCommand(ServerCommandType.SHOW, Utils.serializeObject(result));
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
