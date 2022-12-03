package view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import controller.ConfigIO;
import database.Local;
import database.Online;
import model.DialogType;
import model.VerbQuizComponents;
import model.WordQuizComponents;

import javax.swing.*;
import java.io.IOException;

public class MainWindow extends JFrame {

    // Quiz preferences and their paths
    private static final String VERB_FILE_PATH = "config/verbpreferences.cfg";
    private static final String WORD_FILE_PATH = "config/wordpreferences.cfg";
    public static VerbQuizComponents verbComps;
    public static WordQuizComponents wordComps;

    // Databases
    public static Local local;
    public static Online online;

    // Dialog handler
    public static Dialog dialog = new Dialog();

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

        // Choose theme
        //UIManager.setLookAndFeel(new FlatDarkLaf());
        UIManager.setLookAndFeel(new FlatLightLaf());

        // Load quiz preferences
        verbComps = ConfigIO.readVerbComponents(VERB_FILE_PATH);
        wordComps = ConfigIO.readWordComponents(WORD_FILE_PATH);

        // Connect to databases
        local = new Local("database/local.db");
        online = new Online("conjugame.cxpxjtc5b29j.eu-central-1.rds.amazonaws.com", "3306", "Dictionary");

        // Configure JFrame
        setTitle("Conj\u00FAgaMe!");
        setSize(640, 640);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
