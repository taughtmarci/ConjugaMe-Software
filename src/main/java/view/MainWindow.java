package view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import controller.ConfigIO;
import database.Local;
import database.Online;
import model.*;

import javax.swing.*;
import java.io.IOException;

public class MainWindow extends JFrame {
    // App configurations and its path
    private static final String CONFIG_FILE_PATH = "config/appconfigurations.cfg";
    public static AppConfigurations config;

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

    // EXPLICITLY for JUNIT to reference the first JPanel
    public Dashboard dashboard;

    public MainWindow() {
        try {
            initComponents();
        } catch (UnsupportedLookAndFeelException e) {
            MainWindow.dialog.showExceptionDialog("Megjelen\u00E9si hiba", e.toString(), DialogType.ERROR);
            System.exit(2);
        } catch (IOException e) {
            MainWindow.dialog.showExceptionDialog("F\u00E1jlkezel\u00E9si hiba", "Az alkalmaz\u00E1s konfigur\u00E1ci\u00F3s f\u00E1jljai megs\u00E9r\u00FClhettek.\n" +
                    "K\u00E9rj\u00FCk, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
            System.exit(1);
        }
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();
    }

    private void initComponents() throws UnsupportedLookAndFeelException, IOException {
        // Load app configurations
        config = ConfigIO.readAppConfigurations(CONFIG_FILE_PATH);

        // Choose theme
        if (config.isDarkMode()) UIManager.setLookAndFeel(new FlatDarkLaf());
        else UIManager.setLookAndFeel(new FlatLightLaf());
        SwingUtilities.updateComponentTreeUI(this);

        // Load quiz preferences
        verbComps = ConfigIO.readVerbComponents(VERB_FILE_PATH);
        verbComps.printStats();
        wordComps = ConfigIO.readWordComponents(WORD_FILE_PATH);

        // Connect to databases
        local = new Local("database/mainDB.db");
        if (!config.isOfflineMode()) online = new Online("conjugame.cxpxjtc5b29j.eu-central-1.rds.amazonaws.com", "3306", "ConjugaMe");

        // Configure JFrame
        setTitle("Conj\u00FAgaMe!");
        setSize(640, 640);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dashboard = new Dashboard(this);
        add(dashboard);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }

    public void switchPanels(JPanel prev, JPanel next) {
        SwingUtilities.invokeLater(() -> {
            getContentPane().remove(prev);
            getContentPane().add(next);
            getContentPane().repaint();
            getContentPane().revalidate();
            next.setFocusable(true);
            next.requestFocusInWindow();
        });
    }

}
