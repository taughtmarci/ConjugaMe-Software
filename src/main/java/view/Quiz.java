package view;

import controller.VerbCollection;
import model.Form;
import model.Pronoun;
import controller.QuizComponents;
import model.Verb;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Quiz extends JPanel {
    VerbCollection collection;
    QuizComponents components;

    Verb currentVerb;
    JLabel currentVerbLabel;

    public Quiz(VerbCollection collection) {
        this.collection = collection;
        this.components = collection.getComponents();

        this.currentVerbLabel = new JLabel();

        setLayout(new MigLayout("al center center"));
        initComponents();
        setVisible(true);

        for (int i = 0; i < components.getNumberOfVerbs(); i++)
            nextRound(i);
    }

    private void nextRound(int iteration) {
        currentVerb = collection.getVerb(iteration);
        currentVerbLabel.setText(currentVerb.getBasic().getInfitivo());

        this.updateUI();
    }

    private void initComponents() {
        currentVerbLabel.setFont(new Font("Verdana", Font.PLAIN, 24));
        add(currentVerbLabel, "span, align center");

        ArrayList<JLabel> label = new ArrayList<JLabel>() {{
            if (components.hasGerundio())
                add(new JLabel("Gerundio"));
            if (components.hasParticipio())
                add(new JLabel("Participio"));
            for (Pronoun p : components.getSelectedPronouns()) add(new JLabel(p.toString()));
        }};

        ArrayList<JTextField> input = new ArrayList<JTextField>() {{
            if (components.hasGerundio())
                add(new JTextField(20));
            if (components.hasParticipio())
                add(new JTextField(20));
            for (int i = 0; i < label.size(); i++) add(new JTextField(20));
        }};

        JButton sendResultsButton = new JButton("K\u00FCld\u00E9s");

        for (int i = 0; i < label.size(); i++) {
            add(label.get(i), "align right");
            add(input.get(i), "wrap");
        }
        add(sendResultsButton, "span 2, align center");
    }
}
