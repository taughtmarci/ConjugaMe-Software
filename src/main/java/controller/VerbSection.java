package controller;

import model.ResultImage;

import javax.swing.*;

public class VerbSection extends Section {
    public final String pronoun;
    public JLabel pronounLabel;

    private boolean isFirst = false;

    public VerbSection(String pronoun, ResultImage resultImage) {
        super(resultImage);
        this.pronoun = pronoun;

        initComponents();
    }

    public VerbSection(String pronoun, ResultImage resultImage, boolean isFirst) {
        super(resultImage);
        this.pronoun = pronoun;
        this.isFirst = isFirst;

        initComponents();
    }

    private void initComponents() {
        pronounLabel = new JLabel(pronoun, SwingConstants.RIGHT);
        input = new JTextField(15);
        input.setText("");
        checkLabel.setIcon(resultImage.blankImage());

        resultIconTimer = new Timer(2000, e -> checkLabel.setIcon(resultImage.blankImage()));
        resultIconTimer.setRepeats(false);

        add(pronounLabel, "width 100!, align right");
        add(input, "align center");
        add(checkLabel, "align left, wrap");

        if (isFirst) input.requestFocusInWindow();
    }

    @Override
    public boolean evaluate() {
        boolean result;

        String inputSolution = input.getText().trim();
        if (this.solution.equalsIgnoreCase(inputSolution)) {
            checkLabel.setIcon(resultImage.checkImage());
            result = true;
        } else {
            checkLabel.setIcon(resultImage.crossImage());
            result = false;
        }
        resultIconTimer.start();

        return result;
    }

    @Override
    public void refreshSection() {
        input.setText("");
        if (isFirst) input.requestFocusInWindow();
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

}
