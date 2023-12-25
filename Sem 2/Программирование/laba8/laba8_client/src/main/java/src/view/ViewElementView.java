package src.view;

import org.w3c.dom.ls.LSOutput;
import src.Main;
import src.models.Product;
import src.models.User;
import src.udp.Element;
import src.udp.ServerRuntimeException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import static src.view.Stylizer.strings;

public class ViewElementView extends JFrame {
    private MyJPanel panelMain;
    private ArrayList<Element> elements;
    public ViewElementView(ArrayList<Element> elements, MainWindow father) {
        father.setEnabled(false);
        this.elements = (ArrayList<Element>) elements.clone();
        this.panelMain = new MyJPanel();
        this.setContentPane(panelMain);
        setTitle(strings.getString("elementViewTitle"));
        setSize(600, 600);
        Stylizer.stylize(this);
        panelMain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                elementClickEvent(e);
            }
        });
        javax.swing.Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePanel();
            }
        });
        timer.start();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                timer.stop();
                father.setEnabled(true);
            }
        });
    }

    private void elementClickEvent(MouseEvent e) {
        for (Element element : elements) {
            Product product = element.element;
            int x = (int)product.getCoordinates().getX();
            int y = (int)product.getCoordinates().getY();
            int size = product.getPrice();
            if ((e.getX() >= x) && (e.getX() <= x + size) && (e.getY() >= y) && (e.getY() <= y + size)) {
                if (element.element.getUser().getUsername().equals(Main.commandManager.getConnectionManager().getUserCredentials().getUsername())) {
                    new EditElementView(element, this).setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                } else {
                    JOptionPane.showMessageDialog(null, strings.getString("cantUpdateElement"));
                }
                break;
            }
        }
    }

    private void drawElements(Graphics g) {
        for (Element element : elements) {
            Product product = element.element;
            int x = (int)product.getCoordinates().getX();
            int y = (int)product.getCoordinates().getY();
            int size = product.getPrice();
            Color color = generateColorFromUser(product.getUser());
            g.setColor(color);
            g.fillRect(x, y, size, size);
            Graphics2D g2 = (Graphics2D) g;
            g.drawString(product.getName(), x, y - 3);
        }
    }

    public void updatePanel() {
        try {
            elements = Main.commandManager.getConnectionManager().show();
            panelMain.updateUI();
        } catch (ServerRuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    class MyJPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawElements(g);
        }
    }

    private Color generateColorFromUser(User user) {
        Random random = new Random(user.getId());
        return new Color(
                random.nextInt(0, 256),
                random.nextInt(0, 256),
                random.nextInt(0, 256)
        );
    }
}