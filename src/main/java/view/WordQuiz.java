package view;

import controller.VerbSection;
import controller.WordQuizController;
import controller.WordSection;
import model.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class WordQuiz extends JPanel {
    private final MainWindow main;
    private final WordQuizController controller;
    private final WordQuizComponents comps;

    private JLabel scoreLabel;

    public int currentTime;
    private Timer countBack;

    public final boolean isNormal;
    private JLabel outOfLabel;

    private final JLabel currentWordLabel;
    private final JLabel currentDefinitionsLabel;
    private final JButton sendResultsButton;


    private WordSection wordSection;
    private final ResultImage resultImage;

    private boolean pressedNext;

    public WordQuiz(MainWindow main) throws IOException {
        this.main = main;
        this.comps = MainWindow.wordComps;
        this.isNormal = comps.isNormal();
        this.controller = new WordQuizController(this);

        this.currentWordLabel = new JLabel("");
        this.currentDefinitionsLabel = new JLabel("");
        this.sendResultsButton = new JButton("K\u00FCld\u00E9s");

        this.resultImage = new ResultImage();
        this.pressedNext = false;

        setLayout(new MigLayout("al center center"));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // score label
        scoreLabel = new JLabel(controller.getScore() + " pont");
        add(scoreLabel, "align left");

        // out of or timer label
        if (comps.isNormal()) {
            outOfLabel = new JLabel(controller.getIteration() + 1 + "/" + comps.getWordAmount());
            add(outOfLabel, "align center");
        } else {
            currentTime = comps.getDuration();
            JLabel timeLabel = new JLabel((currentTime / 60) + ":" + (currentTime % 60));
            add(timeLabel, "align center");

            countBack = new Timer(1000, event -> {
                currentTime--;
                timeLabel.setText((currentTime / 60) + ":" + (currentTime % 60));
                if (currentTime == 0) controller.finishQuiz();
            });
            countBack.setRepeats(true);
            countBack.start();
        }

        // end quiz button
        JButton endQuizButton = new JButton("Kv\u00EDz befejez\u00E9se");
        add(endQuizButton, "align right, wrap");
        endQuizButton.addActionListener(e -> controller.finishQuiz());

        // set up current verb and form labels
        currentWordLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        currentDefinitionsLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        add(currentWordLabel, "span, align center");

        // add sections panel
        wordSection = new WordSection(resultImage);
        add(wordSection, "span, align center");

        // send results button
        add(sendResultsButton, "align right");

        sendResultsButton.addActionListener(e -> {
            if (sendResultsButton.getText().equals("K\u00FCld\u00E9s")) {
                // evaluate sections
                controller.evaluateSection();

                // update score and iteration
                controller.setIteration(controller.getIteration() + 1);
                scoreLabel.setText((controller.getScore() + " pont"));
                if (comps.isNormal()) outOfLabel.setText(controller.getIteration() + 1 + "/" + comps.getWordAmount());

                // button swap
                sendResultsButton.setText("Tov\u00E1bb");

                Timer cooldown = new Timer(3000, event -> {
                    if (!pressedNext) {
                        wordSection.refreshSection();
                        controller.nextRound();
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
                wordSection.refreshSection();
                controller.nextRound();

                // button swap
                sendResultsButton.setText("K\u00FCld\u00E9s");
            }

            this.updateUI();
        });

        main.getRootPane().setDefaultButton(sendResultsButton);
    }

    public void stopCountBack() {
        controller.evaluateSection();
        countBack.setRepeats(false);
        countBack.stop();
    }

    public WordQuizComponents getComps() {
        return comps;
    }

    public WordSection getWordSection() {
        return wordSection;
    }

    public void setSectionSolutions(String femenino, String masculino) {
        wordSection.setFemeninoSolution(femenino);
        wordSection.setMasculinoSolution(masculino);
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

    public MainWindow getMain() {
        return main;
    }
}
