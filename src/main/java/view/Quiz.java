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
    private final VerbCollection collection;
    private final QuizComponents components;

    public int iteration;
    public Verb currentVerb;
    private JLabel currentVerbLabel;
    private JLabel currentFormLabel;
    private JButton sendResultsButton;

    private JLabel gerundioLabel;
    private JTextField gerundioInput;
    private JLabel participioLabel;
    private JTextField participioInput;

    public Quiz(VerbCollection collection) {
        this.collection = collection;
        this.components = collection.getComponents();

        this.currentVerbLabel = new JLabel();
        this.currentFormLabel = new JLabel();
        this.sendResultsButton = new JButton("K\u00FCld\u00E9s");

        setLayout(new MigLayout("al center center"));
        initComponents();
        setVisible(true);

        iteration = 0;
        nextRound();
    }

    private void nextRound() {
        currentVerb = collection.getVerb(iteration);
        currentVerbLabel.setText(currentVerb.getBasic().getInfinitivo());

        if (!components.onlyParticipio()) {
            // TODO: ezt okosabban
            currentFormLabel.setText(components.getSelectedForms().get((int)
                    (Math.random() * components.getSelectedForms().size())).toString());
        }
        this.updateUI();
    }

    private void initComponents() {
        // end quiz button
        JButton endQuizButton = new JButton("Kv\u00EDz befejez\u00E9se");
        add(endQuizButton, "span, align right");
        endQuizButton.addActionListener(e -> {
            collection.getMain().switchPanels(this, new StartQuiz(collection.getMain()));
        });

        // set up current verb and form labels
        currentVerbLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        currentFormLabel.setFont(new Font("Verdana", Font.BOLD, 12));

        // gerundio, participio labels & textfield
        if (components.isParticipioPresentoSelected()) {
            gerundioLabel = new JLabel("Participio presento");
            gerundioInput = new JTextField(20);
        }

        if (components.isParticipioPasadoSelected()) {
            participioLabel = new JLabel("Participio pasado");
            participioInput = new JTextField(20);
        }

        // adding elements to the panel
        add(currentVerbLabel, "span, align center");

        if (components.isParticipioPresentoSelected()) {
            add(gerundioLabel, "align right");
            add(gerundioInput, "wrap");
        }

        if (components.isParticipioPasadoSelected()) {
            add(participioLabel, "align right");
            add(participioInput, "wrap");
        }

        // other labels
        if (!components.onlyParticipio()) {
            ArrayList<JLabel> label = new ArrayList<JLabel>() {{
                for (Pronoun p : components.getSelectedPronouns()) add(new JLabel(p.toString()));
            }};

            // other textfields
            ArrayList<JTextField> input = new ArrayList<JTextField>() {{
                for (int i = 0; i < label.size(); i++) add(new JTextField(20));
            }};

            add(currentFormLabel, "span, align center");

            for (int i = 0; i < label.size(); i++) {
                add(label.get(i), "align right");
                add(input.get(i), "wrap");
            }
        }

        // send results button
        add(sendResultsButton, "span, align right");
        sendResultsButton.addActionListener(e -> {
            iteration++;
            System.out.println(iteration);
            nextRound();
        });
    }
}
