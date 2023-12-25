package src;

import src.commands.*;
import src.models.User;
import src.models.UserCredentials;
import src.udp.ServerRuntimeException;

import java.util.*;

/**
 * The type Command manager.
 */
public class CommandManager {

    private final Map<String, Command> commands = new LinkedHashMap<>();
    private Scanner scanner;
    private final Stack<String> stackFilePaths;
    private final ConnectionManager connectionManager;

    /**
     * Instantiates a new Command manager.
     * With the registration of all the commands that will work in the client application.
     *
     * @param connectionManager the connection manager
     */
    public CommandManager(ConnectionManager connectionManager) {
        this.stackFilePaths = new Stack<>();
        this.connectionManager = connectionManager;

        registerCommand("help", new Help(this));
        registerCommand("info", new Info(this.connectionManager));
        registerCommand("show", new Show(this.connectionManager));
        registerCommand("insert", new Insert(this));
        registerCommand("update", new Update(this));
        registerCommand("remove_key", new RemoveKey(this.connectionManager));
        registerCommand("clear", new Clear(this.connectionManager));
        registerCommand("execute_script", new ExecuteScript(this));
        registerCommand("exit", new Exit());
        registerCommand("remove_lower", new RemoveLower(this.connectionManager));
        registerCommand("replace_if_lowe", new ReplaceIfLowe(this));
        registerCommand("remove_lower_key", new RemoveLowerKey(this.connectionManager));
        registerCommand("min_by_name", new MinByName(this.connectionManager));
        registerCommand("group_counting_by_price", new GroupCountingByPrice(this.connectionManager));
        registerCommand("print_unique_manufacture_cost", new PrintUniqueManufactureCost(this.connectionManager));
        registerCommand("actualize", new Actualize(this.connectionManager));
    }

    /**
     * Run command manager (interpreter).
     */
    public void run() {
        auth();
        System.out.println("Для вывода доступных команд напишите help");
        while (true) {
            try {
                System.out.print("> ");
                executeInput(scanner.nextLine());
            } catch (NoSuchElementException e) {
                System.out.println("Ввод завершен, завершаю программу.");
                System.exit(0);
            } catch (ServerRuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void auth() {
        if (connectionManager.getUserCredentials() != null)
            return;
        while (true) {
            try {
                scanner = new Scanner(System.in).useDelimiter("\n");
                System.out.println("Вы хотите авторизоваться или зарегестрироваться?");
                System.out.println("Напишите 1, если хотите авторизоваться.");
                System.out.println("Напишите 2, если хотите зарегестрироваться.");
                System.out.print("> ");
                String request = scanner.nextLine().strip();
                if("1".equals(request)) { // авторизация
                    System.out.print("Введите логин: ");
                    String login = scanner.nextLine().strip();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    UserCredentials credentials = new UserCredentials(login, password);
                    User auth = connectionManager.auth(credentials);
                    System.out.println("Успешная авторизация. Пользователь - " + auth.getUsername());
                    connectionManager.setUserCredentials(credentials);
                    connectionManager.setCurrentUser(auth);
                    break;
                } else if("2".equals(request)) { // регистрация
                    System.out.print("Введите логин: ");
                    String login = scanner.nextLine().strip();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    UserCredentials credentials = new UserCredentials(login, password);
                    connectionManager.register(credentials);
                    System.out.println("Пользователь " + login + " зарегестрирован! Для продолжения авторизуйтесь.");
                }
            } catch (ServerRuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Execute input.
     *
     * @param input the input
     */
    public void executeInput(String input) {
        String[] command = input.split(" ", 2);
        if (!commands.containsKey(command[0])) {
            if (!command[0].equals(""))
                System.out.println("Неизвестная команда: '" + command[0] + "'");
        }
        else
            commands.get(command[0]).execute(command.length > 1 ? command[1].split(" ") : new String[0]);
    }

    /**
     * Register command.
     *
     * @param name    the name
     * @param command the command
     */
    public void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    /**
     * Gets commands.
     *
     * @return the commands
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Gets connection manager.
     *
     * @return the connection manager
     */
    public ConnectionManager getConnectionManager() {
        return this.connectionManager;
    }

    /**
     * Gets scanner.
     *
     * @return the scanner
     */
    public Scanner getScanner() {
        return this.scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Gets stack file paths.
     *
     * @return the stack file paths
     */
    public Stack<String> getStackFilePaths() {
        return this.stackFilePaths;
    }
}
