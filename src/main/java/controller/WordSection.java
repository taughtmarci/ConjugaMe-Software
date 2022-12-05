package controller;

import model.ResultImage;

import javax.swing.*;

public class WordSection extends Section {
    public String femeninoSolution;
    public String masculinoSolution;

    public WordSection(ResultImage resultImage) {
        super(resultImage);
        this.femeninoSolution = "undefined";
        this.masculinoSolution = "undefined";

        initComponents();
    }

    private void initComponents() {
        input = new JTextField(15);
        input.setText("");
        checkLabel.setIcon(resultImage.blankImage());

        resultIconTimer = new Timer(2000, e -> checkLabel.setIcon(resultImage.blankImage()));
        resultIconTimer.setRepeats(false);

        add(input, "align center");
        add(checkLabel, "align left, wrap");

        input.requestFocusInWindow();
    }

    @Override
    public boolean evaluate() {
        boolean result;

        String inputSolution = input.getText().trim();
        if (femeninoSolution.equalsIgnoreCase(inputSolution) || masculinoSolution.equalsIgnoreCase(inputSolution)) {
            checkLabel.setIcon(resultImage.checkImage());
            result = true;
        } else if (("la " + femeninoSolution).equalsIgnoreCase(inputSolution) || ("el " + masculinoSolution).equalsIgnoreCase(inputSolution)) {
            checkLabel.setIcon(resultImage.checkImage());
            result = true;
        } else {
            checkLabel.setIcon(resultImage.crossImage());
            result = false;
        }
        resultIconTimer.start();

        return result;
    }

    public String getFemeninoSolution() {
        return femeninoSolution;
    }

    public void setFemeninoSolution(String femeninoSolution) {
        this.femeninoSolution = femeninoSolution;
    }

    public String getMasculinoSolution() {
        return masculinoSolution;
    }

    public void setMasculinoSolution(String masculinoSolution) {
        this.masculinoSolution = masculinoSolution;
    }

}
