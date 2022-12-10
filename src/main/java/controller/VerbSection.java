package controller;

import model.ResultImage;
import view.MainWindow;

import javax.swing.*;

public class VerbSection extends Section {
    public final String pronoun;
    public JLabel pronounLabel;

    private boolean isFirst = false;
    private final boolean isInstantFeedback;

    public VerbSection(String pronoun, ResultImage resultImage) {
        super(resultImage);
        this.pronoun = pronoun;
        this.isInstantFeedback = MainWindow.config.isInstantFeedback();

        initComponents();
    }

    public VerbSection(String pronoun, ResultImage resultImage, boolean isFirst) {
        super(resultImage);
        this.pronoun = pronoun;
        this.isFirst = isFirst;
        this.isInstantFeedback = MainWindow.config.isInstantFeedback();

        initComponents();
    }

    private void initComponents() {
        pronounLabel = new JLabel(pronoun, SwingConstants.RIGHT);

        if (this.isInstantFeedback) {
            checkLabel.setIcon(resultImage.blankImage());
            resultIconTimer = new Timer(2000, e -> checkLabel.setIcon(resultImage.blankImage()));
            resultIconTimer.setRepeats(false);
        }

        add(pronounLabel, "width 100!, align right");
        if (this.isInstantFeedback) {
            add(input, "align center");
            add(checkLabel, "align left, wrap");
        } else add(input, "align center, wrap");

        if (isFirst) input.requestFocusInWindow();
    }

    @Override
    public boolean evaluate() {
        boolean result;

        String inputSolution = input.getText().trim();
        if (this.solution.equalsIgnoreCase(inputSolution)) {
            if (this.isInstantFeedback)
                checkLabel.setIcon(resultImage.checkImage());
            result = true;
        } else {
            if (this.isInstantFeedback)
                checkLabel.setIcon(resultImage.crossImage());
            result = false;
        }
        if (this.isInstantFeedback) resultIconTimer.start();

        return result;
    }

    @Override
    public void refreshSection() {
        input.setText("");
        if (isFirst) input.requestFocusInWindow();
    }

    public String getPronoun() {
        return pronoun;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

}
