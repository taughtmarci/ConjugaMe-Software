package view;

import controller.VerbQuizResults;
import controller.Section;
import controller.VerbQuizController;
import model.*;
import model.VerbQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VerbQuiz extends JPanel {
    private final MainWindow main;
    private final VerbQuizController controller;
    private final ArrayList<Verb> verbs;
    private final VerbQuizComponents comps;

    public int score;
    private JLabel scoreLabel;
    private Timer countBack;

    public final boolean isNormal;
    public int currentTime;
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

    public VerbQuiz(MainWindow main, VerbQuizController controller) {
        this.main = main;
        this.controller = controller;
        this.verbs = controller.getVerbs();
        this.comps = controller.getComps();
        this.isNormal = comps.isNormal();

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
        if (iteration == comps.getNumberOfVerbs() - 1) {
            finishQuiz();
        }
        else {
            currentVerb = verbs.get(iteration);
            currentVerbLabel.setText(currentVerb.getBasic().getInfinitivo());

            if (!comps.onlyParticipio()) {
                currentForm = comps.getSelectedForms().get((int)
                        (Math.random() * comps.getSelectedForms().size()));
                currentFormLabel.setText(currentForm.toString());

                int i = 0;
                for (Pronoun p : comps.getSelectedPronouns()) {
                    String currentSolution = currentVerb.getSolution(currentForm, p);
                    sections.get(i).setSolution(currentSolution);
                    i++;
                }
            }

            if (comps.isParticipioPresentoSelected())
                presentoSection.setSolution(currentVerb.getBasic().getPresento());

            if (comps.isParticipioPasadoSelected())
                pasadoSection.setSolution(currentVerb.getBasic().getPasado());

            this.updateUI();
            main.getRootPane().setDefaultButton(sendResultsButton);
        }
    }

    private void initComponents() {
        // score label
        scoreLabel = new JLabel(Integer.toString(score) + " pont");
        add(scoreLabel, "align left");

        // out of or timer label
        if (comps.isNormal()) {
            outOfLabel = new JLabel(iteration + 1 + "/" + comps.getNumberOfVerbs());
            add(outOfLabel, "align center");
        } else {
            currentTime = comps.getDuration();
            JLabel timeLabel = new JLabel((currentTime / 60) + ":" + (currentTime % 60));
            add(timeLabel, "align center");

            countBack = new Timer(1000, event -> {
                currentTime--;
                timeLabel.setText((currentTime / 60) + ":" + (currentTime % 60));
                if (currentTime == 0) finishQuiz();
            });
            countBack.setRepeats(true);
            countBack.start();
        }

        // end quiz button
        JButton endQuizButton = new JButton("Kv\u00EDz befejez\u00E9se");
        add(endQuizButton, "align right, wrap");
        endQuizButton.addActionListener(e -> finishQuiz());

        // set up current verb and form labels
        currentVerbLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        currentFormLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        add(currentVerbLabel, "span, align center");

        // add sections panel
        if (comps.isParticipioPresentoSelected()) {
            presentoSection = new Section("Participio presento", resultImage, true);
            add(presentoSection, "span, align center");
        }

        if (comps.isParticipioPasadoSelected()) {
            pasadoSection = new Section("Participio pasado", resultImage, !comps.isParticipioPresentoSelected());
            add(pasadoSection, "span, align center");
        }

        // pronoun sections
        if (!comps.onlyParticipio()) {
            add(currentFormLabel, "span, align center");
            for (Pronoun p : comps.getSelectedPronouns()) {
                sections.add(new Section(p.toString(), resultImage));
                add(sections.get(sections.size() - 1), "span, align center");
            }
        }

        if (!comps.isParticipioPresentoSelected() && !comps.isParticipioPasadoSelected())
            sections.get(0).setFirst(true);

        // send results button
        add(sendResultsButton, "align right");

        sendResultsButton.addActionListener(e -> {
            if (sendResultsButton.getText().equals("K\u00FCld\u00E9s")) {
                // evaluate sections
                evaluateSections();

                // update score and iteration
                iteration++;
                scoreLabel.setText((score + " pont"));
                if (comps.isNormal()) outOfLabel.setText(iteration + 1 + "/" + comps.getNumberOfVerbs());

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

    private void stopCountBack() {
        evaluateSections();
        countBack.setRepeats(false);
        countBack.stop();
    }

    private void evaluateSections() {
        if (comps.isParticipioPresentoSelected()) {
            if (presentoSection.evaluate()) score++;
            else incorrectVerbs.add(currentVerb);
        }

        if (comps.isParticipioPasadoSelected()) {
            if (pasadoSection.evaluate()) score++;
            else incorrectVerbs.add(currentVerb);
        }

        for (Section section : sections) {
            if (section.evaluate()) score++;
            else incorrectVerbs.add(currentVerb);
        }
    }

    private void refreshAllSections() {
        if (comps.isParticipioPresentoSelected())
            presentoSection.refreshSection();

        if (comps.isParticipioPasadoSelected())
            pasadoSection.refreshSection();

        for (Section section: sections)
            section.refreshSection();
    }

    private void finishQuiz() {
        if (!comps.isNormal()) stopCountBack();
        VerbQuizResults results = new VerbQuizResults(score, controller, incorrectVerbs);
        setVisible(false);
        current = new EndQuiz(results);
        main.switchPanels(this, current);
    }
}
