package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static src.product.ExceptionMessages.*;

/**
 * The type Main.
 */
public class Main {

    /**
     * The constant logger.
     */
    public static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        logger.info("Запуск приложения.");

        Map<String, String> env = System.getenv();
        while (true) {
            DataBase dataBase = null;
            String jsonFilePath = null;
            ConnectionManager connectionManager = null;
            try {
                jsonFilePath = env.get("MY_JSON");
                dataBase = new DataBase(jsonFilePath);
                connectionManager = new ConnectionManager(dataBase, jsonFilePath);
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
            try {
                connectionManager.run();
            } catch (Exception e) {
                System.out.println("Сервер уснул, бужу его снова...\n" + e);
            } finally {
                dataBase.save(jsonFilePath);
            }
        }
    }
}
