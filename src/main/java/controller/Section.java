package controller;

import model.ResultImage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class Section extends JPanel {
    public final String pronoun;
    public String solution;
    public final ResultImage resultImage;

    public JLabel pronounLabel;
    public JTextField input;
    public JLabel checkLabel;

    public Section(String pronoun, String solution, ResultImage resultImage) {
        this.pronoun = pronoun;
        this.solution = solution;
        this.resultImage = resultImage;

        setLayout(new MigLayout("al center center"));
        initComponents();
    }

    private void initComponents() {
        pronounLabel = new JLabel(pronoun);
        input = new JTextField(15);
        checkLabel = resultImage.blankImage();

        add(pronounLabel);
        add(input);
        add(checkLabel);
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean evaluate() {
        String inputSolution = input.getText().trim();
        if (this.solution.equals(inputSolution)) {
            checkLabel = resultImage.checkImage();
            return true;
        }
        else {
            checkLabel = resultImage.crossImage();
            return false;
        }
    }

    public void clearInput() {
        this.input.setText("");
    }
}
