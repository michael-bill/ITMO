package src.commands;

import src.Interpreter;
import src.product.Product;

public class Update implements Command, Insertable {

    private final Interpreter interpreter;

    public Update(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public void execute(String[] args) {
        Long id = correctInputLong(interpreter.getScanner(), "Введите `id` элемента типа данных `Long`: ");
        if (interpreter.getDataBase().isIdExist(id)) {
            try {
                Product product = getProductFromStdout(interpreter.getScanner(), interpreter.getDataBase());
                interpreter.getDataBase().update(id, product);
                System.out.println("Элемент обновлен по его id успешно.");
            } catch (Exception e) {
                System.out.println("Некорректный формат ввода данных.");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Элемент с таким id не существует.");
        }
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[] {"id"};
    }
}
