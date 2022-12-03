package controller;

import model.ResultImage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class VerbSection extends JPanel {
    public final String pronoun;
    public String solution;
    public final ResultImage resultImage;

    public JLabel pronounLabel;
    public JTextField input;
    public JLabel checkLabel = new JLabel();

    private boolean isFirst = false;

    public VerbSection(String pronoun, ResultImage resultImage) {
        this.pronoun = pronoun;
        this.solution = "undefined";
        this.resultImage = resultImage;

        setLayout(new MigLayout("al center center"));
        initComponents();
    }

    public VerbSection(String pronoun, ResultImage resultImage, boolean isFirst) {
        this.pronoun = pronoun;
        this.solution = "undefined";
        this.resultImage = resultImage;
        this.isFirst = isFirst;

        setLayout(new MigLayout("al center center"));
        initComponents();
    }

    public VerbSection(String pronoun, String solution, ResultImage resultImage) {
        this.pronoun = pronoun;
        this.solution = solution;
        this.resultImage = resultImage;

        setLayout(new MigLayout("al center center"));
        initComponents();
    }

    private void initComponents() {
        pronounLabel = new JLabel(pronoun, SwingConstants.RIGHT);
        input = new JTextField(15);
        input.setText("");
        input.setBorder(new LineBorder(Color.GRAY));
        checkLabel.setIcon(resultImage.blankImage());

        add(pronounLabel, "width 100!, align right");
        add(input, "align center");
        add(checkLabel, "align left, wrap");

        if (isFirst) input.requestFocusInWindow();
    }

    public boolean evaluate() {
        String inputSolution = input.getText().trim();
        if (this.solution.equals(inputSolution)) {
            checkLabel.setIcon(resultImage.checkImage());
            input.setBorder(new LineBorder(Color.GREEN));
            return true;
        }
        else {
            checkLabel.setIcon(resultImage.crossImage());
            input.setBorder(new LineBorder(Color.RED));
            return false;
        }
    }

    public void refreshSection() {
        checkLabel.setIcon(resultImage.blankImage());
        input.setBorder(new LineBorder(Color.GRAY));
        input.setText("");

        if (isFirst) input.requestFocusInWindow();
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

}
