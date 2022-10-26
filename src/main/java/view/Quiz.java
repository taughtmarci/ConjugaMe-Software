package view;

import controller.VerbCollection;
import model.Pronoun;
import model.QuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Quiz extends JPanel {
    VerbCollection collection;
    QuizComponents components;

    public Quiz(VerbCollection collection) {
        this.collection = collection;
        this.components = collection.getComponents();
        setLayout(new MigLayout("al center center"));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JLabel currentVerbLabel = new JLabel(collection.getVerb(0).getInfinitivo());
        currentVerbLabel.setFont(new Font("Verdana", Font.PLAIN, 24));
        add(currentVerbLabel, "span, align center");

        ArrayList<JLabel> label = new ArrayList<JLabel>() {{
            for (Pronoun p : components.getSelectedPronouns()) add(new JLabel(p.toString()));
        }};

        ArrayList<JTextField> input = new ArrayList<JTextField>() {{
            for (int i = 0; i < label.size(); i++) add(new JTextField(20));
        }};

        JButton sendResultsButton = new JButton("K\u00F6vetkez\u0151");

        for (int i = 0; i < label.size(); i++) {
            add(label.get(i), "align right");
            add(input.get(i), "wrap");
        }
        add(sendResultsButton, "span 2, align center");
    }
}
