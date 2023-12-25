package src.commands;

import src.DataBase;
import src.models.User;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.Utils;

import java.util.ArrayList;

/**
 * The type Info.
 */
public class Info implements Command {

    private final DataBase dataBase;

    /**
     * Instantiates a new Info.
     *
     * @param dataBase the data base
     */
    public Info(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        ArrayList<String> result = new ArrayList<>();
        result.add("Тип коллекции: HashMap<Long, Person>");
        result.add("Дата инициализации: " + dataBase.getInitializationTime());
        result.add("Количество элементов: " + dataBase.getCollection().size());
        result.add("Автор: Студент группы P3116, Билошицкий Михаил Владимирович, ИСУ 367101");
        return new ServerCommand(ServerCommandType.INFO, Utils.serializeObject(result));
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции";
    }
}
