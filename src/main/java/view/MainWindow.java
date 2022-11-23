package view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import database.Local;
import database.Online;
import model.DialogType;

import javax.swing.*;
import java.io.IOException;

public class MainWindow extends JFrame {

    public static Dialog dialog = new Dialog();
    public Local local;
    public Online online;

    public MainWindow() {
        try {
            initComponents();
        } catch (UnsupportedLookAndFeelException e) {
            MainWindow.dialog.showDialog("Megjelenési hiba", e.toString(), DialogType.ERROR);
        } catch (IOException e) {
            MainWindow.dialog.showDialog("Fájlkezelési hiba", "Az alkalmazás konfigurációs fájljai megsérülhettek.\n" +
                    "Kérjük, telepítsd újra az alkalmazást!\n" + e.toString(), DialogType.ERROR);
        }
        setVisible(true);
    }

    private void initComponents() throws UnsupportedLookAndFeelException, IOException {
        setTitle("Conj\u00FAgaMe!");
        setSize(640, 640);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Choose theme
        //UIManager.setLookAndFeel(new FlatDarkLaf());
        UIManager.setLookAndFeel(new FlatLightLaf());

        // Connect to databases
        local = new Local("config/local.db");
        online = new Online("conjugame.cxpxjtc5b29j.eu-central-1.rds.amazonaws.com", "3306", "Dictionary");

        JPanel dashboard = new Dashboard(this);
        add(dashboard);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }

    public void switchPanels(JPanel prev, JPanel next) {
        SwingUtilities.invokeLater(() -> {
            remove(prev);
            add(next);
            repaint();
            revalidate();
        });
    }

}
