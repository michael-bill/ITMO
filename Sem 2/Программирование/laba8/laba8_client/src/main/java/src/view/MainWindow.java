package src.view;

import src.Main;
import src.udp.Element;
import src.udp.ServerRuntimeException;
import src.udp.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static src.view.Stylizer.strings;

public class MainWindow extends JFrame {
    private JPanel panelMain;
    private JScrollPane paneMain;
    private JButton buttonCommandMode;
    private JButton buttonVisualMode;
    private JTextField textFieldFilter;
    private JButton buttonFilter;
    private JButton buttonReset;
    private JLabel labelYou;
    private JLabel mainWindowTitle;
    private JComboBox comboBoxLanguage;
    JTable table;
    ArrayList<Element> elements = new ArrayList<>();
    Lock lock = new ReentrantLock();
    private int sortedColumn;
    private String filter = "";
    public MainWindow() {
        this.sortedColumn = -1;
        this.setContentPane(this.panelMain);
        this.setTitle(strings.getString("mainWindowTitle"));
        this.setSize(1000, 500);
        labelYou.setText(strings.getString("you") + " " + Main.commandManager.getConnectionManager().getUserCredentials().getUsername());
        table = new JTable();
        paneMain.setViewportView(table);
        Stylizer.stylize(this);
        buttonCommandMode.addActionListener(this::buttonCommandModeActionEvent);
        buttonVisualMode.addActionListener(this::buttonVisualModeActionEvent);
        buttonFilter.addActionListener(this::buttonFilterActionEvent);
        buttonReset.addActionListener(this::buttonResetFilterActionEvent);
        table.setFont(table.getFont().deriveFont(15.f));
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(15.f));
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableMouseClicked(e);
            }
        });
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableHeaderMouseClicked(e);
            }
        });
        table.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        updateTable();
        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        timer.start();
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
                updateTable();
            }
        });
        this.updateLocale();
    }

    private void buttonFilterActionEvent(ActionEvent e) {
        filter = textFieldFilter.getText();
        updateTable();
    }

    private void buttonResetFilterActionEvent(ActionEvent e) {
        filter = "";
        textFieldFilter.setText("");
        updateTable();
    }

    private void buttonCommandModeActionEvent(ActionEvent e) {
        new CommandsView(this).setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void buttonVisualModeActionEvent(ActionEvent e) {
        new ViewElementView(elements, this).setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void tableMouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            int row = table.getSelectedRow();
            Long key = Long.parseLong(table.getValueAt(row, 0) + "");
            Element element = elements.stream().filter(x -> x.key.equals(key)).findFirst().orElse(null);
            if (element != null) {
                if (element.element.getUser().getUsername().equals(Main.commandManager.getConnectionManager().getUserCredentials().getUsername())) {
                    new EditElementView(element, this).setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                } else {
                    JOptionPane.showMessageDialog(null, strings.getString("cantUpdateElement"));
                }
            }
        }
    }

    private void tableHeaderMouseClicked(MouseEvent e) {
        sortedColumn = table.getTableHeader().columnAtPoint(e.getPoint());
        tableSort(sortedColumn);
    }

    private void tableSort(int column) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        if (model.getDataVector().size() > 0) {
            model.getDataVector().sort(new Comparator<Vector>() {
                @Override
                public int compare(Vector o1, Vector o2) {
                    if (o1.get(column) != null && o2.get(column) != null &&
                            Utils.tryParseDouble(o1.get(column).toString()) &&
                            Utils.tryParseDouble(o2.get(column).toString())) {
                        return ((Double.parseDouble(o1.get(column).toString()) -
                                Double.parseDouble(o2.get(column).toString()))) > 0 ? 1 : -1;
                    } else {
                        return (o1.get(column) == null ? "" : o1.get(column).toString())
                                .compareTo(o2.get(column) == null ? "" : o2.get(column).toString());
                    }
                }
            });
            table.updateUI();
        }
    }


    public void updateTable() {
        try {
            ArrayList<Element> newElements = Main.commandManager.getConnectionManager().show();
            int selectedRow = -1;
            if (newElements.size() == elements.size()) {
                selectedRow = table.getSelectedRow();
            }
            elements.clear();
            elements = newElements;
            String[] columns = {
                    strings.getString("table_key"),
                    strings.getString("table_id"),
                    strings.getString("table_name"),
                    strings.getString("table_coordinates_x"),
                    strings.getString("table_coordinates_y"),
                    strings.getString("table_price"),
                    strings.getString("table_owner_name"),
                    strings.getString("table_owner_weight"),
                    strings.getString("table_owner_eyecolor"),
                    strings.getString("table_owner_haircolor"),
                    strings.getString("table_owner_nationality"),
                    strings.getString("table_owner_location_x"),
                    strings.getString("table_owner_location_y"),
                    strings.getString("table_owner_location_name"),
                    strings.getString("table_manufacture_cost"),
                    strings.getString("table_unit_of_measure"),
                    strings.getString("table_user"),
                    strings.getString("table_creation_date")
            };
            Object[][] rows = new Object[elements.size()][];
            if (filter.length() > 0) {
                elements = (ArrayList<Element>) elements.stream().filter(new Predicate<Element>() {
                    @Override
                    public boolean test(Element element) {
                        return element.toString().toLowerCase().contains(filter.toLowerCase().trim());
                    }
                }).collect(Collectors.toList());
            }
            for (int i = 0; i < elements.size(); i++) {
                rows[i] = new Object[] {
                        elements.get(i).key.toString(),
                        elements.get(i).element.getId(),
                        elements.get(i).element.getName(),
                        elements.get(i).element.getCoordinates().getX(),
                        elements.get(i).element.getCoordinates().getY(),
                        elements.get(i).element.getPrice(),
                        elements.get(i).element.getOwner().getName(),
                        elements.get(i).element.getOwner().getWeight(),
                        elements.get(i).element.getOwner().getEyeColor(),
                        elements.get(i).element.getOwner().getHairColor(),
                        elements.get(i).element.getOwner().getNationality(),
                        elements.get(i).element.getOwner().getLocation() != null ? elements.get(i).element.getOwner().getLocation().getX() : null,
                        elements.get(i).element.getOwner().getLocation() != null ? elements.get(i).element.getOwner().getLocation().getY() : null,
                        elements.get(i).element.getOwner().getLocation() != null ? elements.get(i).element.getOwner().getLocation().getName() : null,
                        elements.get(i).element.getManufactureCost(),
                        elements.get(i).element.getUnitOfMeasure(),
                        elements.get(i).element.getUser().getUsername(),
                        elements.get(i).element.getCreationDate().toLocalDateTime()
                };
            }
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setDataVector(rows, columns);
            if (sortedColumn >= 0)
                tableSort(sortedColumn);
            if (selectedRow >= 0)
                table.setRowSelectionInterval(selectedRow, selectedRow);
        } catch (ServerRuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void updateLocale() {
        this.setTitle(strings.getString("mainWindowTitle"));
        mainWindowTitle.setText(strings.getString("mainWindowTitle"));
        buttonFilter.setText(strings.getString("filter"));
        buttonReset.setText(strings.getString("reset"));
        labelYou.setText(strings.getString("you") + Main.commandManager.getConnectionManager().getCurrentUser().getUsername());
        buttonCommandMode.setText(strings.getString("buttonCommandModeText"));
        buttonVisualMode.setText(strings.getString("buttonVisualModeText"));
    }
}
