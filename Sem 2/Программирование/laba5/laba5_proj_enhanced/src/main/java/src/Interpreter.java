package src;

import src.commands.*;
import src.product.*;

import javax.security.sasl.SaslClient;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static src.product.ExceptionMessages.*;

public class Interpreter {

    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final Scanner scanner;
    private final DataBase dataBase;
    private final String jsonFilePath;
    private final Stack<String> stackFilePaths;

    public Interpreter(DataBase dataBase, Scanner scanner, String jsonFilePath) {
        this.dataBase = dataBase;
        this.scanner = scanner;
        this.jsonFilePath = jsonFilePath;
        this.stackFilePaths = new Stack<>();

        registerCommand("help", new Help(this));
        registerCommand("info", new Info(this.dataBase));
        registerCommand("show", new Show(this.dataBase));
        registerCommand("insert", new Insert(this));
        registerCommand("update", new Update(this));
        registerCommand("remove_key", new RemoveKey(this.dataBase));
        registerCommand("clear", new Clear(this.dataBase));
        registerCommand("save", new Save(this));
        registerCommand("execute_script", new ExecuteScript(this));
        registerCommand("exit", new Exit());
        registerCommand("remove_lower", new RemoveLower(this.dataBase));
        registerCommand("replace_if_lowe", new ReplaceIfLowe(this));
        registerCommand("remove_lower_key", new RemoveLowerKey(this.dataBase));
        registerCommand("min_by_name", new MinByName(this.dataBase));
        registerCommand("group_counting_by_price", new GroupCountingByPrice(this.dataBase));
        registerCommand("print_unique_manufacture_cost", new PrintUniqueManufactureCost(this.dataBase));
    }

    public void run() {
        System.out.println("Для вывода доступных команд напишите help");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            try {
                executeInput(scanner.nextLine());
            } catch (NoSuchElementException e) {
                System.out.println("Ввод завершен, завершаю программу.");
                System.exit(0);
            }
        }
    }

    public void executeInput(String input) {
        String[] command = input.split(" ", 2);
        if (!commands.containsKey(command[0]))
            System.out.println("Неизвестная команда: '" + command[0] + "'");
        else
            commands.get(command[0]).execute(command.length > 1 ? command[1].split(" ") : new String[0]);
    }

    public void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public String getJsonFilePath() {
        return jsonFilePath;
    }

    public Stack<String> getStackFilePaths() {
        return stackFilePaths;
    }
}
