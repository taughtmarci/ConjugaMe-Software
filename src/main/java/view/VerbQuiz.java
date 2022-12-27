package view;

import controller.VerbSection;
import controller.VerbQuizController;
import model.*;
import model.VerbQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

public class VerbQuiz extends Quiz {
    private final VerbQuizController controller;
    private final VerbQuizComponents comps;

    public int currentTime;
    private Timer countBack;

    private JLabel scoreLabel;
    private JLabel outOfLabel;

    private final JLabel currentVerbLabel;
    private final JLabel currentDefinitionsLabel;
    private final JLabel currentFormLabel;

    private VerbSection presentoVerbSection;
    private VerbSection pasadoVerbSection;
    private final ArrayList<VerbSection> verbSections;

    private final boolean isInstantFeedback;

    public VerbQuiz(MainWindow main) throws IOException {
        super(main);

        this.currentVerbLabel = new JLabel("");
        this.currentDefinitionsLabel = new JLabel("");
        this.currentFormLabel = new JLabel("");
        this.verbSections = new ArrayList<>();

        this.comps = MainWindow.verbComps;
        this.controller = new VerbQuizController(this);

        this.isInstantFeedback = MainWindow.config.isInstantFeedback();

        setLayout(new MigLayout("al center center"));
        initComponents();

        controller.nextRound(true);
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
        currentVerbLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        currentDefinitionsLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        currentFormLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        add(currentVerbLabel, "span, align center");
        add(currentDefinitionsLabel, "span, align center");

        // add sections panel
        if (comps.isParticipioPresenteSelected()) {
            presentoVerbSection = new VerbSection(controller.currentForm, "Participio presente", resultImage, true);
            add(presentoVerbSection, "span, align center");
        }

        if (comps.isParticipioPasadoSelected()) {
            pasadoVerbSection = new VerbSection(controller.currentForm, "Participio pasado",
                    resultImage, !comps.isParticipioPresenteSelected());
            add(pasadoVerbSection, "span, align center");
        }

        // pronoun sections
        if (!comps.onlyParticipio()) {
            add(currentFormLabel, "span, align center");
            for (Pronoun p : comps.getSelectedPronouns()) {
                verbSections.add(new VerbSection(controller.currentForm, p.toString(), resultImage));
                add(verbSections.get(verbSections.size() - 1), "span, align center");
            }
        }

        if (!comps.isParticipioPresenteSelected() && !comps.isParticipioPasadoSelected())
            verbSections.get(0).setFirst(true);

        // send results button
        add(sendButton, "align center, span");

        sendButton.addActionListener(e -> {
            // evaluate sections
            controller.evaluateSections(false);

            // update score and iteration
            controller.setIteration(controller.getIteration() + 1);
            if (this.isInstantFeedback) scoreLabel.setText((controller.getScore() + " pont"));
            if (comps.isNormal()) outOfLabel.setText(controller.getIteration() + 1 + "/" + comps.getWordAmount());

            // next round, refresh section
            controller.nextRound(false);
            refreshAllSections();
            this.updateUI();
        });

        if (MainWindow.config.isEnterAsTab())
            Toolkit.getDefaultToolkit().addAWTEventListener(new EnterKeyListener(), AWTEvent.KEY_EVENT_MASK);
        else main.getRootPane().setDefaultButton(sendButton);
    }

    public void stopCountBack() {
        controller.evaluateSections(true);
        countBack.setRepeats(false);
        countBack.stop();
    }

    protected void refreshAllSections() {
        if (comps.isParticipioPresenteSelected())
            presentoVerbSection.refreshSection(controller.currentForm);

        if (comps.isParticipioPasadoSelected())
            pasadoVerbSection.refreshSection(controller.currentForm);

        for (VerbSection verbSection : verbSections)
            verbSection.refreshSection(controller.currentForm);
    }

    private static class EnterKeyListener implements AWTEventListener {
        @Override
        public void eventDispatched(AWTEvent event) {
            if (event instanceof KeyEvent key && key.getKeyCode() == KeyEvent.VK_ENTER  && key.getModifiersEx() == 0 && key.getID() == KeyEvent.KEY_PRESSED) {
                if (key.getComponent() instanceof JButton) {
                    return;
                }
                key.getComponent().transferFocus();
            }
        }

    }

    public VerbQuizController getController() {
        return controller;
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

    public void setCurrentDefinitionsLabel(String text) {
        currentDefinitionsLabel.setText(text);
    }

    public void setCurrentFormLabel(String text) {
        currentFormLabel.setText(text);
    }
}