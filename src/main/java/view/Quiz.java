package view;

import controller.QuizResults;
import controller.Section;
import controller.VerbCollection;
import model.*;
import controller.QuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Quiz extends JPanel {
    private final VerbCollection collection;
    private final QuizComponents components;

    public int score;
    private JLabel scoreLabel;

    public int iteration;
    private JLabel outOfLabel;

    public Verb currentVerb;
    public Form currentForm;
    private final JLabel currentVerbLabel;
    private final JLabel currentFormLabel;
    private final JButton sendResultsButton;

    private final ResultImage resultImage = new ResultImage();
    private final ArrayList<Section> sections = new ArrayList<>();
    private Section presentoSection;
    private Section pasadoSection;

    private boolean pressedNext;
    private ArrayList<Verb> incorrectVerbs;
    private EndQuiz current;

    public Quiz(VerbCollection collection) {
        this.collection = collection;
        this.components = collection.getComponents();

        this.currentVerbLabel = new JLabel("");
        this.currentFormLabel = new JLabel("");
        this.sendResultsButton = new JButton("K\u00FCld\u00E9s");

        this.pressedNext = false;
        this.incorrectVerbs = new ArrayList<>();

        setLayout(new MigLayout("al center center"));
        initComponents();

        score = 0;
        iteration = 0;
        nextRound();

        setVisible(true);
    }

    private void nextRound() {
        if (iteration == components.getNumberOfVerbs() - 1) {
            finishQuiz();
        }
        else {
            currentVerb = collection.getVerb(iteration);
            currentVerbLabel.setText(currentVerb.getBasic().getInfinitivo());

            if (!components.onlyParticipio()) {
                // TODO: ezt okosabban
                currentForm = components.getSelectedForms().get((int)
                        (Math.random() * components.getSelectedForms().size()));
                currentFormLabel.setText(currentForm.toString());

                int i = 0;
                for (Pronoun p : components.getSelectedPronouns()) {
                    String currentSolution = currentVerb.getSolution(currentForm, p);
                    sections.get(i).setSolution(currentSolution);
                    i++;
                }
            }

            if (components.isParticipioPresentoSelected())
                presentoSection.setSolution(currentVerb.getBasic().getPresento());

            if (components.isParticipioPasadoSelected())
                pasadoSection.setSolution(currentVerb.getBasic().getPasado());

            this.updateUI();
            collection.getMain().getRootPane().setDefaultButton(sendResultsButton);
        }
    }

    private void initComponents() {
        // score label
        scoreLabel = new JLabel(Integer.toString(score) + " pont");
        add(scoreLabel, "align left");

        // end quiz button
        JButton endQuizButton = new JButton("Kv\u00EDz befejez\u00E9se");
        add(endQuizButton, "align right, wrap");
        endQuizButton.addActionListener(e -> {
            finishQuiz();
            //collection.getMain().switchPanels(this, new StartQuiz(collection.getMain()));
        });

        // set up current verb and form labels
        currentVerbLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        currentFormLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        add(currentVerbLabel, "span, align center");

        // add sections panel
        if (components.isParticipioPresentoSelected()) {
            presentoSection = new Section("Participio presento", resultImage, true);
            add(presentoSection, "span, align center");
        }

        if (components.isParticipioPasadoSelected()) {
            pasadoSection = new Section("Participio pasado", resultImage, !components.isParticipioPresentoSelected());
            add(pasadoSection, "span, align center");
        }

        // pronoun sections
        if (!components.onlyParticipio()) {
            add(currentFormLabel, "span, align center");
            for (Pronoun p : components.getSelectedPronouns()) {
                sections.add(new Section(p.toString(), resultImage));
                add(sections.get(sections.size() - 1), "span, align center");
            }
        }

        if (!components.isParticipioPresentoSelected() && !components.isParticipioPasadoSelected())
            sections.get(0).setFirst(true);

        // out of label
        outOfLabel = new JLabel(iteration + 1 + "/" + components.getNumberOfVerbs());
        add(outOfLabel, "align left");
        add(sendResultsButton, "align right");

        sendResultsButton.addActionListener(e -> {
            if (sendResultsButton.getText().equals("K\u00FCld\u00E9s")) {
                // evaluate sections
                if (components.isParticipioPresentoSelected()) {
                    if (presentoSection.evaluate()) score++;
                    else incorrectVerbs.add(currentVerb);
                }

                if (components.isParticipioPasadoSelected()) {
                    if (pasadoSection.evaluate()) score++;
                    else incorrectVerbs.add(currentVerb);
                }

                for (Section section : sections) {
                    if (section.evaluate()) score++;
                    else incorrectVerbs.add(currentVerb);
                }

                // update score and iteration
                iteration++;
                scoreLabel.setText((Integer.toString(score) + " pont"));
                outOfLabel.setText(Integer.toString(iteration + 1) + "/" + components.getNumberOfVerbs());

                // button swap
                sendResultsButton.setText("Tov\u00E1bb");

                Timer cooldown = new Timer(3000, event -> {
                    if (!pressedNext) {
                        refreshAllSections();
                        nextRound();
                        System.out.println("debug");
                    }
                    pressedNext = false;
                    // button swap
                    sendResultsButton.setText("K\u00FCld\u00E9s");
                });

                cooldown.setRepeats(false);
                cooldown.start();
            }
            else if (sendResultsButton.getText().equals("Tov\u00E1bb")) {
                pressedNext = true;
                refreshAllSections();
                nextRound();

                // button swap
                sendResultsButton.setText("K\u00FCld\u00E9s");
            }

            this.updateUI();
        });
    }

    private void refreshAllSections() {
        if (components.isParticipioPresentoSelected())
            presentoSection.refreshSection();

        if (components.isParticipioPasadoSelected())
            pasadoSection.refreshSection();

        for (Section section: sections)
            section.refreshSection();
    }

    private void finishQuiz() {
        QuizResults results = new QuizResults(score, incorrectVerbs);
        setVisible(false);
        current = new EndQuiz(results, collection);
        collection.getMain().switchPanels(this, current);
    }
}
