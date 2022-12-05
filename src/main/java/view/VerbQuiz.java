package view;

import controller.VerbSection;
import controller.VerbQuizController;
import model.*;
import model.VerbQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class VerbQuiz extends JPanel {
    private final MainWindow main;
    private final VerbQuizController controller;
    private final VerbQuizComponents comps;

    private JLabel scoreLabel;

    public int currentTime;
    private Timer countBack;

    public final boolean isNormal;
    private JLabel outOfLabel;

    private final JLabel currentVerbLabel;
    private final JLabel currentFormLabel;
    private final JButton sendResultsButton;

    private VerbSection presentoVerbSection;
    private VerbSection pasadoVerbSection;
    private final ArrayList<VerbSection> verbSections;
    private final ResultImage resultImage;

    public VerbQuiz(MainWindow main) throws IOException {
        this.main = main;
        this.comps = MainWindow.verbComps;
        this.isNormal = comps.isNormal();

        this.currentVerbLabel = new JLabel("");
        this.currentFormLabel = new JLabel("");
        this.sendResultsButton = new JButton("K\u00FCld\u00E9s");

        this.verbSections = new ArrayList<>();
        this.resultImage = new ResultImage();

        this.controller = new VerbQuizController(this);
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
                    controller.evaluateSections();
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
        currentVerbLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        currentFormLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        add(currentVerbLabel, "span, align center");

        // add sections panel
        if (comps.isParticipioPresentoSelected()) {
            presentoVerbSection = new VerbSection("Participio presento", resultImage, true);
            add(presentoVerbSection, "span, align center");
        }

        if (comps.isParticipioPasadoSelected()) {
            pasadoVerbSection = new VerbSection("Participio pasado", resultImage, !comps.isParticipioPresentoSelected());
            add(pasadoVerbSection, "span, align center");
        }

        // pronoun sections
        if (!comps.onlyParticipio()) {
            add(currentFormLabel, "span, align center");
            for (Pronoun p : comps.getSelectedPronouns()) {
                verbSections.add(new VerbSection(p.toString(), resultImage));
                add(verbSections.get(verbSections.size() - 1), "span, align center");
            }
        }

        if (!comps.isParticipioPresentoSelected() && !comps.isParticipioPasadoSelected())
            verbSections.get(0).setFirst(true);

        // send results button
        add(sendResultsButton, "align right");

        sendResultsButton.addActionListener(e -> {
            if (sendResultsButton.getText().equals("K\u00FCld\u00E9s")) {
                // evaluate sections
                controller.evaluateSections();

                // update score and iteration
                controller.setIteration(controller.getIteration() + 1);
                scoreLabel.setText((controller.getScore() + " pont"));
                if (comps.isNormal()) outOfLabel.setText(controller.getIteration() + 1 + "/" + comps.getWordAmount());

                // refresh the sections and next round
                refreshAllSections();
                controller.nextRound();
            }
            this.updateUI();
        });

        main.getRootPane().setDefaultButton(sendResultsButton);
    }

    public void stopCountBack() {
        controller.evaluateSections();
        countBack.setRepeats(false);
        countBack.stop();
    }

    private void refreshAllSections() {
        if (comps.isParticipioPresentoSelected())
            presentoVerbSection.refreshSection();

        if (comps.isParticipioPasadoSelected())
            pasadoVerbSection.refreshSection();

        for (VerbSection verbSection : verbSections)
            verbSection.refreshSection();
    }

    public VerbQuizComponents getComps() {
        return comps;
    }

    public VerbSection getPresentoSection() {
        return presentoVerbSection;
    }

    public void setPresentoSectionSolution(String solution) {
        presentoVerbSection.setSolution(solution);
    }

    public VerbSection getPasadoSection() {
        return pasadoVerbSection;
    }

    public void setPasadoSectionSolution(String solution) {
        pasadoVerbSection.setSolution(solution);
    }

    public ArrayList<VerbSection> getSections() {
        return verbSections;
    }

    public void setSectionSolution(int index, String solution) {
        verbSections.get(index).setSolution(solution);
    }

    public void setCurrentVerbLabel(String text) {
        currentVerbLabel.setText(text);
    }

    public void setCurrentFormLabel(String text) {
        currentFormLabel.setText(text);
    }

    public MainWindow getMain() {
        return main;
    }
}
