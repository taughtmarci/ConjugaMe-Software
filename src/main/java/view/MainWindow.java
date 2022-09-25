package view;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("ConjugaMe!");
        setSize(640, 640);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
