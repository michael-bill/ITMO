package src.view;

import src.Main;
import src.models.Product;
import src.udp.Element;
import src.udp.ServerRuntimeException;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import static src.view.Stylizer.strings;

public class CommandsView extends JFrame {
    private JPanel panelMain;
    private JButton infoButton;
    private JButton showButton;
    private JButton min_by_nameButton;
    private JButton group_counting_by_priceButton;
    private JButton print_unique_manufacture_costButton;
    private JButton execute_scriptButton;
    private JButton remove_lowerButton;
    private JButton remove_lower_keyButton;
    private JTextField textField1;
    private JTextField textField3;
    private JButton clearButton;
    private JButton helpButton;
    private JButton insertButton;

    public CommandsView(MainWindow father) {
        father.setEnabled(false);
        this.setContentPane(this.panelMain);
        this.setTitle(strings.getString("commandsViewTitle"));
        this.setSize(700, 300);
        Stylizer.stylize(this);
        CommandsView tempThis = this;

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String helpText = "";
                helpText += "help: " + Main.commandManager.getCommandByName("help").getDescription() + '\n';
                helpText += "info: " + Main.commandManager.getCommandByName("info").getDescription() + '\n';
                helpText += "show: " + Main.commandManager.getCommandByName("show").getDescription() + '\n';
                helpText += "min_by_name" + Main.commandManager.getCommandByName("min_by_name").getDescription() + '\n';
                helpText += "group_counting_by_price: " + Main.commandManager.getCommandByName("group_counting_by_price").getDescription() + '\n';
                helpText += "print_unique_manufacture_cost: " + Main.commandManager.getCommandByName("print_unique_manufacture_cost").getDescription() + '\n';
                helpText += "execute_script: " + Main.commandManager.getCommandByName("execute_script").getDescription() + '\n';
                helpText += "clear: " + Main.commandManager.getCommandByName("clear").getDescription() + '\n';
                helpText += "remove_lower [Long id]: " + Main.commandManager.getCommandByName("remove_lower").getDescription() + '\n';
                helpText += "remove_lower_key [Long key]: " + Main.commandManager.getCommandByName("remove_lower_key").getDescription();
                JOptionPane.showMessageDialog(null, helpText);
            }
        });
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<String> result = Main.commandManager.getConnectionManager().info();
                    String s = "";
                    for (String st : result) s += st + "\n";
                    JOptionPane.showMessageDialog(null, s);
                } catch (ServerRuntimeException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Element> result = Main.commandManager.getConnectionManager().show();
                    String s = "";
                    for (Element st : result) s += st + "\n";
                    JOptionPane.showMessageDialog(null, s);
                } catch (ServerRuntimeException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InsertElementView(tempThis).setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean result = Main.commandManager.getConnectionManager().clear();
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Коллекция очищена успешно.");
                    }
                } catch (ServerRuntimeException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        min_by_nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Product product = Main.commandManager.getConnectionManager().minByName();
                    JOptionPane.showMessageDialog(null, product.toString());
                } catch (ServerRuntimeException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        group_counting_by_priceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<String> result = Main.commandManager.getConnectionManager().groupCountingByPrice();
                    String s = "";
                    for (String st : result) s += st + "\n";
                    JOptionPane.showMessageDialog(null, s);
                } catch (ServerRuntimeException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        print_unique_manufacture_costButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<String> result = Main.commandManager.getConnectionManager().printUniqueManufactureCost();
                    String s = "";
                    for (String st : result) s += st + "\n";
                    JOptionPane.showMessageDialog(null, s);
                } catch (ServerRuntimeException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        remove_lowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean result = Main.commandManager.getConnectionManager().removeLower(Long.parseLong(textField1.getText()));
                    if (result) JOptionPane.showMessageDialog(null, "Соответствующие элементы удалены успешно.");
                } catch (ServerRuntimeException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, strings.getString("numberFormatException"));
                }
            }
        });
        remove_lower_keyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean result = Main.commandManager.getConnectionManager().removeLowerKey(Long.parseLong(textField1.getText()));
                    if (result) JOptionPane.showMessageDialog(null, "Соответствующие элементы удалены успешно.");
                } catch (ServerRuntimeException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, strings.getString("numberFormatException"));
                }
            }
        });
        execute_scriptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        Main.commandManager.getCommandByName("execute_script").execute(new String[] {selectedFile.getAbsolutePath()});
                        JOptionPane.showMessageDialog(null, "Файл выполнен успешно.");
                    }
                } catch (ServerRuntimeException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                father.setEnabled(true);
                father.updateTable();
            }
        });
        updateLocale();
    }

    public void updateLocale() {
        this.setTitle(strings.getString("commandsViewTitle"));
    }
}
