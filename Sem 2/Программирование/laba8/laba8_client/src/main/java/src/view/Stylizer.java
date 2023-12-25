package src.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Stylizer {

    public static Locale currentLocale;
    public static HashMap<String, Locale> availableLocales;
    public static ResourceBundle strings;
    static  {
        availableLocales = new HashMap<>();
        availableLocales.put("ru_RU", new Locale("ru", "RU"));
        availableLocales.put("en_CA", new Locale("en", "CA"));
        availableLocales.put("pt_PT", new Locale("pt", "PT"));
        availableLocales.put("da_DK", new Locale("da", "DK"));
        currentLocale = availableLocales.get("ru_RU");
        strings = ResourceBundle.getBundle("strings", currentLocale);
    }

    public static void stylize(JFrame container) {
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLocationRelativeTo(null);
        container.setVisible(true);
        try {
            URI fontPath = Objects.requireNonNull(container.getClass().getClassLoader().getResource("UbuntuMono-Regular.ttf")).toURI();
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            setStylize(container, font);
        } catch (FontFormatException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setStylize(Container parent, Font font) {
        for (Component c : parent.getComponents()) {
            c.setFont(font.deriveFont(c.getFont().getStyle(), c.getFont().getSize()));
            if (c instanceof Container) {
                setStylize((Container) c, font);
            }
            if (c instanceof JPanel) {
                ((JPanel)c).setBackground(new Color(255, 255, 255));
            }
            else if (c instanceof JButton) {
                ((JButton)c).setBackground(new Color(37, 150, 190));
            }
            else if (c instanceof JTable) {
                ((JTable)c).getTableHeader().setFont(font.deriveFont(Font.BOLD, c.getFont().getSize()));
            }
        }
    }
}
