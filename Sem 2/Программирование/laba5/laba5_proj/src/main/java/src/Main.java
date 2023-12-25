package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static src.product.ExceptionMessages.*;

/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(env.get("MY_JSON"));
        } catch (IOException e) {
            if (Files.exists(Path.of(args[0])))
                System.out.println(FILE_NOT_FOUND);
            else
                System.out.println(FILE_READ_ERROR);
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(INCORRECT_FILE_PATH);
            return;
        } catch (NullPointerException e) {
            System.out.println(ENV_NOT_FOUND);
            return;
        }
        Interpreter interpreter = new Interpreter(dataBase, env.get("MY_JSON"));
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (interpreter.active) {
            System.out.print("> ");
            String command = "";
            try {
                command = in.next();
            } catch (NoSuchElementException e) {
                System.out.println("Ввод завершен, завершаю программу.");
                System.exit(0);
            }
            interpreter.parseCommand(command, in);
        }
        in.close();
    }
}
