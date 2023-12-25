package src.commands;

import src.Interpreter;
import src.product.Product;

public class ReplaceIfLowe implements Command, Insertable {

    private final Interpreter interpreter;

    public ReplaceIfLowe(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public void execute(String[] args) {
        Long key = correctInputLong(interpreter.getScanner(), "Введите ключ типа данных `Long`: ");
        if (interpreter.getDataBase().isKeyExist(key)) {
            try {
                Product product = getProductFromStdout(interpreter.getScanner(), interpreter.getDataBase());
                if (product.getId() < interpreter.getDataBase().getCollection().get(key).getId()) {
                    interpreter.getDataBase().getCollection().put(key, product);
                }
                System.out.println("Команда выполнена успешно.");
            } catch (Exception e) {
                System.out.println("Некорректный формат ввода данных.");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Элемента с таким ключом не существует.");
        }
    }

    @Override
    public String getDescription() {
        return "заменить значение по ключу, если новое значение меньше старого (сравнение по id)";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[] {"key"};
    }
}
