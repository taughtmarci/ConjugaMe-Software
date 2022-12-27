package view;

import controller.WordQuizController;
import controller.WordSection;
import model.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WordQuiz extends Quiz {
    private final WordQuizController controller;
    private final WordQuizComponents comps;

    public int currentTime;
    private Timer countBack;

    private JLabel scoreLabel;
    private JLabel outOfLabel;

    private final JLabel currentWordLabel;
    private final JLabel currentDefinitionsLabel;
    private WordSection wordSection;

    private final boolean isInstantFeedback;

    public WordQuiz(MainWindow main) throws IOException {
        super(main);

        this.currentWordLabel = new JLabel("");
        this.currentDefinitionsLabel = new JLabel("");

        this.comps = MainWindow.wordComps;
        this.controller = new WordQuizController(this);

        this.isInstantFeedback = MainWindow.config.isInstantFeedback();

        setLayout(new MigLayout("al center center"));
        initComponents();

        controller.nextRound();
        setVisible(true);
    }

    private void initComponents() {
        // score label
        if (this.isInstantFeedback) {
            scoreLabel = new JLabel(controller.getScore() + " pont");
            add(scoreLabel, "align center, span");
        }

        // out of or timer label
        if (comps.isNormal()) {
            outOfLabel = new JLabel(controller.getIteration() + 1 + "/" + comps.getWordAmount());
            add(outOfLabel, "align center, span 1");
        } else {
            currentTime = comps.getDuration();
            JLabel timeLabel = new JLabel((currentTime / 60) + ":" + (currentTime % 60));
            timeLabel.setFont(new Font("Verdana", Font.BOLD, 12));
            timeLabel.setForeground(timeLabel.getForeground());
            add(timeLabel, "align center, span 1");

            countBack = new Timer(1000, event -> {
                currentTime--;
                timeLabel.setText((currentTime / 60) + ":" + (currentTime % 60));
                if (currentTime == 15) timeLabel.setForeground(MainWindow.config.isDarkMode() ?
                        Color.RED.brighter() : Color.RED.darker());
                if (currentTime == 0) {
                    controller.finishQuiz();
                }
            });
            countBack.setRepeats(true);
            countBack.start();
        }

        // end quiz button
        JButton endQuizButton = new JButton("Befejez\u00E9s");
        add(endQuizButton, "align right, span");
        endQuizButton.addActionListener(e -> controller.finishQuiz());

        // set up current verb and form labels
        currentWordLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        currentDefinitionsLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        add(currentWordLabel, "span, align center");
        add(currentDefinitionsLabel, "span, align center");

        // add sections panel
        wordSection = new WordSection(resultImage, comps.isArticlesNeeded());
        add(wordSection, "span, align center");

        // send results button
        add(sendButton, "span, align center");

        sendButton.addActionListener(e -> {
            // evaluate sections
            controller.evaluateSection(false);

            // update score and iteration
            controller.setIteration(controller.getIteration() + 1);
            if (this.isInstantFeedback) scoreLabel.setText((controller.getScore() + " pont"));
            if (comps.isNormal()) outOfLabel.setText(controller.getIteration() + 1 + "/" + comps.getWordAmount());

            // refresh the section and next round
            wordSection.refreshSection();
            controller.nextRound();
            this.updateUI();
        });

        main.getRootPane().setDefaultButton(sendButton);
    }

    public void stopCountBack() {
        controller.evaluateSection(true);
        countBack.setRepeats(false);
        countBack.stop();
    }

    public WordQuizController getController() {
        return controller;
    }

    public WordQuizComponents getComps() {
        return comps;
    }

    public WordSection getWordSection() {
        return wordSection;
    }

    public void setSectionSolutions(String femenino, String masculino, boolean isNoun) {
        wordSection.setFemeninoSolution(femenino);
        wordSection.setMasculinoSolution(masculino);
        wordSection.setNoun(isNoun);
    }

    public JLabel getCurrentWordLabel() {
        return currentWordLabel;
    }

    public void setCurrentWordLabel(String text) {
        currentWordLabel.setText(text);
    }

    public JLabel getCurrentDefinitionsLabel() {
        return currentDefinitionsLabel;
    }

    public void setCurrentDefinitionsLabel(String text) {
        currentDefinitionsLabel.setText(text);
    }
}
