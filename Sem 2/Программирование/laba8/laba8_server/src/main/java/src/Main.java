package src;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

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
            ConnectionManager connectionManager = null;
            try {
                dataBase = new DataBase();
                connectionManager = new ConnectionManager(dataBase);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            try {
                connectionManager.run();
            } catch (Exception e) {
                System.out.println("Сервер уснул, бужу его снова...\n" + e);
            } finally {
                dataBase.save();
            }
        }
    }
}
