package view;

import model.Pronoun;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        // pronouns checkboxes
        ArrayList<JCheckBox> pronounCheckBoxes = new ArrayList<JCheckBox>();
        for (Pronoun p : Pronoun.values()) {
            pronounCheckBoxes.add(new JCheckBox(p.toString()));
            add(pronounCheckBoxes.get(pronounCheckBoxes.size() - 1), "wrap");
        }

        // new quiz button
        JButton newQuizButton = new JButton("Create a new quiz");
        add(newQuizButton);
        newQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < pronounCheckBoxes.size(); i++) {
                    if (pronounCheckBoxes.get(i).isChecked()) {

                    }
                }
                setVisible(false);
                current = new Quiz();
                main.switchPanels(current);
            }
        });
    }



}
