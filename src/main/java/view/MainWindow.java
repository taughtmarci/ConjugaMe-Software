package view;

import com.formdev.flatlaf.FlatDarkLaf;
import database.LocalDB;
import database.OnlineDB;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Conj\u00FAgaMe!");
        setSize(640, 640);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Connect to local DB
        LocalDB localDB = new LocalDB("src/main/resources/local.db");
        localDB.selectStatement("test");

        // Connect to online DB
        OnlineDB onlineDB = new OnlineDB("conjugame.cxpxjtc5b29j.eu-central-1.rds.amazonaws.com", "3306");
        onlineDB.selectStatement("Test");

        // Dark theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        JPanel sq = new StartQuiz(this);
        add(sq);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }

    public void debugDialog(String text) {
        JOptionPane.showMessageDialog(this, text);
    }

    public void switchPanels(JPanel panel) {
        repaint();
        revalidate();
        add(panel);
    }
}
