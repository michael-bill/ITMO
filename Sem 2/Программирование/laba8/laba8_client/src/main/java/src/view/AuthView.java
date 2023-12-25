package src.view;

import src.udp.ServerRuntimeException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import static src.Main.commandManager;

import static src.view.Stylizer.strings;

public class AuthView extends JFrame {
    public JPanel panelMain;
    private JTextField textFieldLogin;
    private JTextField textFieldPassword;
    private JButton buttonAuth;
    private JButton buttonRegister;
    private JLabel labelTitle;
    private JLabel labelLogin;
    private JLabel labelPassword;
    private JComboBox comboBoxLanguage;

    public AuthView() {
        this.setContentPane(this.panelMain);
        this.setTitle(strings.getString("authWindowTitle"));
        this.setSize(420, 210);
        Stylizer.stylize(this);
        buttonAuth.addActionListener(this::buttonAuthActionListener);
        buttonRegister.addActionListener(this::buttonRegisterActionListener);
        for (String key : Stylizer.availableLocales.keySet()) {
            comboBoxLanguage.addItem(key);
        }
        comboBoxLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strings = ResourceBundle.getBundle(
                        "strings",
                        Stylizer.availableLocales.get(comboBoxLanguage.getSelectedItem().toString())
                );
                updateLocale();
            }
        });
        this.updateLocale();
    }

    private void buttonAuthActionListener(ActionEvent e) {
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();
        if (login.length() == 0 || password.length() == 0) {
            JOptionPane.showMessageDialog(null, strings.getString("fieldsCantBeEmpty"));
            return;
        }
        try {
            commandManager.auth(login, password);
            dispose();
            new MainWindow();
        } catch (ServerRuntimeException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    private void buttonRegisterActionListener(ActionEvent e) {
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();
        if (login.length() == 0 || password.length() == 0) {
            JOptionPane.showMessageDialog(null, strings.getString("fieldsCantBeEmpty"));
            return;
        }
        try {
            commandManager.register(login, password);
            Thread.sleep(10);
            commandManager.auth(login, password);
            JOptionPane.showMessageDialog(null, strings.getString("successfulRegistration"));
            dispose();
            new MainWindow();
        } catch (ServerRuntimeException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateLocale() {
        labelTitle.setText(strings.getString("authWindowTitle"));
        labelLogin.setText(strings.getString("labelLoginText"));
        labelPassword.setText(strings.getString("labelPasswordText"));
        buttonRegister.setText(strings.getString("buttonRegisterText"));
        buttonAuth.setText(strings.getString("buttonAuthText"));
    }
}
