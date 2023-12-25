package src.commands;

import src.DataBase;
import src.Utils;
import src.udp.ServerCommand;
import src.udp.ServerCommandType;
import src.models.*;
/**
 * The type Min by name.
 */
public class MinByName implements Command {

    private final DataBase dataBase;

    /**
     * Instantiates a new Min by name.
     *
     * @param dataBase the data base
     */
    public MinByName(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ServerCommand execute(byte[] args, User caller) {
        Product minNameProduct = null;
        int minLenName = Integer.MAX_VALUE;
        for (Product product : dataBase.getCollection().values()) {
            if (minLenName > product.getName().length()) {
                minNameProduct = product;
                minLenName = product.getName().length();
            }
        }
        if (minNameProduct != null)
            return new ServerCommand(ServerCommandType.MIN_BY_NAME, Utils.serializeObject(minNameProduct));
        else
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Коллекция пуста."));
    }

    @Override
    public String getDescription() {
        return "вывести любой объект из коллекции, значение поля name которого является минимальным (сравнение по длине строки)";
    }
}
