package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartQuiz extends JPanel {
    private MainWindow main;
    private Quiz current;

    public StartQuiz(MainWindow main) {
        this.main = main;

        setLayout(new MigLayout("al center center"));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // new quiz button
        JButton newQuizButton = new JButton("Create a new quiz");
        add(newQuizButton);
        newQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                current = new Quiz();
                main.switchPanels(current);
            }
        });
    }

}
