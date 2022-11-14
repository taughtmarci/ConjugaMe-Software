package view;

import controller.Section;
import controller.VerbCollection;
import model.Form;
import model.Pronoun;
import controller.QuizComponents;
import model.ResultImage;
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
    public Form currentForm;
    private final JLabel currentVerbLabel;
    private final JLabel currentFormLabel;
    private final JButton sendResultsButton;

    private final ResultImage resultImage = new ResultImage();
    private final ArrayList<Section> sections = new ArrayList<>();

    public Quiz(VerbCollection collection) {
        this.collection = collection;
        this.components = collection.getComponents();

        this.currentVerbLabel = new JLabel("");
        this.currentFormLabel = new JLabel("");
        this.sendResultsButton = new JButton("K\u00FCld\u00E9s");

        iteration = 0;
        nextRound();

        setLayout(new MigLayout("al center center"));
        initComponents();
        setVisible(true);
    }

    private void nextRound() {
        currentVerb = collection.getVerb(iteration);
        currentVerbLabel.setText(currentVerb.getBasic().getInfinitivo());


        if (!components.onlyParticipio()) {
            // TODO: ezt okosabban
            currentForm = components.getSelectedForms().get((int)
                    (Math.random() * components.getSelectedForms().size()));
            currentFormLabel.setText(currentForm.toString());
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
        add(currentVerbLabel, "span, align center");

        if (components.isParticipioPresentoSelected()) {
            sections.add(new Section("Participio presento", currentVerb.getBasic().getPresento(), resultImage));
            add(sections.get(sections.size() - 1), "wrap");
        }

        if (components.isParticipioPasadoSelected()) {
            sections.add(new Section("Participio pasado", currentVerb.getBasic().getPasado(), resultImage));
            add(sections.get(sections.size() - 1), "wrap");
        }

        // pronoun sections
        if (!components.onlyParticipio()) {
            add(currentFormLabel, "span, align center");
            for (Pronoun p : components.getSelectedPronouns()) {
                String currentSolution = currentVerb.getSolution(currentForm, p);
                sections.add(new Section(p.toString(), currentSolution, resultImage));
                add(sections.get(sections.size() - 1), "wrap");
            }
        }

        // send results button
        add(sendResultsButton, "span, align right");
        sendResultsButton.addActionListener(e -> {
            int score = 0;
            for (Section section : sections) {
                if (section.evaluate()) score++;
                section.updateUI();
            }
            System.out.println(score);

            iteration++;
            nextRound();
        });
        collection.getMain().getRootPane().setDefaultButton(sendResultsButton);
    }
}
