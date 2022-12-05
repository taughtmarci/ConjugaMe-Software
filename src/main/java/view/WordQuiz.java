package view;

import controller.WordQuizController;
import controller.WordSection;
import model.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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

    public WordQuiz(MainWindow main) throws IOException {
        this.main = main;
        this.comps = MainWindow.wordComps;
        this.isNormal = comps.isNormal();

        this.currentWordLabel = new JLabel("");
        this.currentDefinitionsLabel = new JLabel("");
        this.sendResultsButton = new JButton("K\u00FCld\u00E9s");

        this.resultImage = new ResultImage();

        this.controller = new WordQuizController(this);
        setLayout(new MigLayout("al center center"));
        initComponents();
        controller.nextRound();
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
            timeLabel.setForeground(Color.BLACK);
            add(timeLabel, "align center");

            countBack = new Timer(1000, event -> {
                currentTime--;
                timeLabel.setText((currentTime / 60) + ":" + (currentTime % 60));
                if (currentTime == 15) timeLabel.setForeground(Color.RED.darker());
                if (currentTime == 0) {
                    controller.evaluateSection();
                    controller.finishQuiz();
                }
            });
            countBack.setRepeats(true);
            countBack.start();
        }

        // end quiz button
        JButton endQuizButton = new JButton("Befejez\u00E9s");
        add(endQuizButton, "align right, wrap");
        endQuizButton.addActionListener(e -> controller.finishQuiz());

        // set up current verb and form labels
        currentWordLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        currentDefinitionsLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        add(currentWordLabel, "span, align center");
        add(currentDefinitionsLabel, "span, align center");

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

                // refresh the section and next round
                wordSection.refreshSection();
                controller.nextRound();
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
