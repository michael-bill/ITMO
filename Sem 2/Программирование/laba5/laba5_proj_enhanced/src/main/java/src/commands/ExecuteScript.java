package src.commands;

import src.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static src.product.ExceptionMessages.*;

public class ExecuteScript implements Command {

    private final Interpreter interpreter;

    public ExecuteScript(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public void execute(String[] args) {
        String absPath = "";
        try {
            if (args.length > 1)
                for (int i = 0; i < args.length; i++)
                    absPath += " " + args[i];
            else absPath = args[0];
            absPath = absPath.trim();
            absPath = (new File(absPath)).getAbsolutePath();
            Scanner scanner = new Scanner(new FileInputStream(absPath)).useDelimiter("\n");
            while (scanner.hasNext()) {
                String c = scanner.next();
                if (c.contains("execute_script") && interpreter.getStackFilePaths().contains(absPath)) {
                    System.out.println("execute_script команда недопсутима в исполнении из скрипта и была пропущена во избежании рекурсии.");
                    continue;
                } else if (!interpreter.getStackFilePaths().contains(absPath)) {
                    interpreter.getStackFilePaths().add(absPath);
                }
                interpreter.executeInput(c);
            }
            interpreter.getStackFilePaths().remove(absPath);
            scanner.close();
            System.out.println("Файл выполнен успешно.");
        } catch (IOException e) {
            if (Files.exists(Path.of(absPath)))
                System.out.println(FILE_READ_ERROR);
            else
                System.out.println(FILE_NOT_FOUND.toString().replaceAll("программу", "команду."));
        }
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте должны содержатся команды в таком же виде, в котором вы их вводите в интерактивном режиме.";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[] {"file_name"};
    }
}
