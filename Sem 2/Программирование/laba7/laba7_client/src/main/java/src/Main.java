package src;

import java.io.IOException;

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
        while (true) {
            ConnectionManager connectionManager = null;
            try {
                connectionManager = new ConnectionManager();
            } catch (IOException e) {
                System.out.println("Клиент умер, хотя не должен. Надо дебажить.");
            }
            CommandManager commandManager = new CommandManager(connectionManager);
            try {
                commandManager.run();
            } catch (Exception e) {
                System.out.println("Клиент уснул, бужу его снова...\n" + e);
            }
        }
    }
}
