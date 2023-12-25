package src;

import src.view.AuthView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class Main {
    public static CommandManager commandManager;

    static {
        try {
            commandManager = new CommandManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        new AuthView();
    }
}
