package src.commands;

import src.DataBase;
import src.Interpreter;
import src.product.*;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static src.product.ExceptionMessages.ILLEGAL_COORDINATES_X_FIELD;
import static src.product.ExceptionMessages.INCORRECT_INPUT;

public class Insert implements Command, Insertable {

    private final Interpreter interpreter;
    private final DataBase dataBase;

    public Insert(Interpreter interpreter) {
        this.interpreter = interpreter;
        this.dataBase = interpreter.getDataBase();
    }

    @Override
    public void execute(String[] args) {
        Long key = correctInputLong(interpreter.getScanner(), "Введите ключ типа данных `Long`: ");
        if (!dataBase.isKeyExist(key)) {
            try {
                Product product = getProductFromStdout(interpreter.getScanner(), interpreter.getDataBase());
                dataBase.insert(key, product);
                System.out.println("Элемент добавлен успешно.");
            } catch (Exception e) {
                System.out.println("Некорректный формат ввода данных.");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Элемент с таким ключом уже существует.");
        }
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент с заданным ключом";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[] {"key"};
    }
}
