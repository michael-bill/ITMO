package src.view;

import src.Main;
import src.models.*;
import src.models.Color;
import src.udp.Element;
import src.udp.ServerRuntimeException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static src.view.Stylizer.strings;

public class InsertElementView extends JFrame {
    private JPanel panelMain;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JTextField textField15;
    private JTextField textField17;
    private JTextField textField18;
    private JButton buttonSave;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;

    public InsertElementView(JFrame father) {
        father.setEnabled(false);
        this.setContentPane(this.panelMain);
        this.setTitle(strings.getString("addElementWindowTitle"));
        this.setSize(420, 600);
        Stylizer.stylize(this);
        comboBox3.addItem(null);
        comboBox4.addItem(null);
        for (src.models.Color c : src.models.Color.values()) {
            comboBox1.addItem(c.toString());
            comboBox2.addItem(c.toString());
        }
        for (src.models.Country c : src.models.Country.values()) {
            comboBox3.addItem(c.toString());
        }
        for (src.models.UnitOfMeasure u : src.models.UnitOfMeasure.values()) {
            comboBox4.addItem(u.toString());
        }
        buttonSave.addActionListener(this::buttonSaveActionListener);
        updateLocale();
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                father.setEnabled(true);
            }
        });
    }

    private void buttonSaveActionListener(ActionEvent e) {
        try {
            Element newElement = new Element();
            Long key = Long.parseLong(textField1.getText());
            Product product = new Product(
                    123L,
                    textField3.getText(),
                    new Coordinates(
                            Double.parseDouble(textField4.getText()),
                            Float.parseFloat(textField5.getText())),
                    Integer.parseInt(textField6.getText()),
                    new Person(
                            textField7.getText(),
                            Long.parseLong(textField8.getText()),
                            src.models.Color.valueOf(comboBox1.getSelectedItem().toString()),
                            src.models.Color.valueOf(comboBox2.getSelectedItem().toString()),
                            comboBox3.getSelectedItem() == null ? null : Country.valueOf(comboBox3.getSelectedItem().toString()),
                            (textField12.getText().length() == 0 && textField13.getText().length() == 0 && textField14.getText().length() == 0) ? null :
                                    new Location(
                                            textField12.getText().length() == 0 ? null : Integer.parseInt(textField12.getText()),
                                            textField13.getText().length() == 0 ? null : Double.parseDouble(textField13.getText()),
                                            textField14.getText().length() == 0 ? null : textField14.getText()
                                    )
                    ),
                    textField15.getText().length() == 0 ? null : Integer.parseInt(textField15.getText()),
                    comboBox4.getSelectedItem() == null ? null : UnitOfMeasure.valueOf(comboBox4.getSelectedItem().toString()),
                    Main.commandManager.getConnectionManager().getCurrentUser()
            );
            newElement.key = key;
            newElement.element = product;
            if (Main.commandManager.getConnectionManager().insert(newElement)) {
                JOptionPane.showMessageDialog(null, strings.getString("addedSuccessfully"));
            }
        } catch (IllegalArgumentException exception) {
            if (exception instanceof NumberFormatException) {
                JOptionPane.showMessageDialog(null, strings.getString("numberFormatException"));
            } else {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        } catch (ServerRuntimeException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    private void updateLocale() {
        String[] s = new String[] {
                "table_key",
                "table_id",
                "table_name",
                "table_coordinates_x",
                "table_coordinates_y",
                "table_price",
                "table_owner_name",
                "table_owner_weight",
                "table_owner_eyecolor",
                "table_owner_haircolor",
                "table_owner_nationality",
                "table_owner_location_x",
                "table_owner_location_y",
                "table_owner_location_name",
                "table_manufacture_cost",
                "table_unit_of_measure",
                "table_user",
                "table_creation_date"
        };
        int i = 0;
        for (Component component : panelMain.getComponents()) {
            if (component instanceof JLabel label) {
                label.setText(strings.getString(s[i]));
                i++;
            }
        }
        buttonSave.setText(strings.getString("save"));
    }
}
