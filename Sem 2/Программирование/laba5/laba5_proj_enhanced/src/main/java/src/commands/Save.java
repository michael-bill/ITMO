package src.commands;

import src.Interpreter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static src.product.ExceptionMessages.FILE_INPUT_ERROR;

public class Save implements Command {

    private final Interpreter interpreter;

    public Save(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public void execute(String[] args) {
        try {
            interpreter.getDataBase().save(interpreter.getJsonFilePath());
            System.out.println("Файл сохранён успешно.");
        } catch (IOException e) {
            if (Files.exists(Path.of(interpreter.getJsonFilePath())))
                System.out.println(FILE_INPUT_ERROR);
            else
                System.out.println("Файл не найден. Попробуйте создать файл c именем " + interpreter.getJsonFilePath());
        }
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}
