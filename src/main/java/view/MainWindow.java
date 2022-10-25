package view;

import com.formdev.flatlaf.FlatDarkLaf;
import database.Local;
import database.Online;

import javax.swing.*;
public class MainWindow extends JFrame {

    public static Dialog dialog = new Dialog();

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

        // Connect to databases
        Local local = new Local("src/main/resources/local.db");
        Online online = new Online("conjugame.cxpxjtc5b29j.eu-central-1.rds.amazonaws.com", "3306", "Dictionary");

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

    public void switchPanels(JPanel panel) {
        repaint();
        revalidate();
        add(panel);
    }

}
