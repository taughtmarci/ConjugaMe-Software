package controller;

import model.ResultImage;

import javax.swing.*;
import java.util.ArrayList;

public class WordSection extends Section {
    public String femeninoSolution;
    public String masculinoSolution;
    public boolean isNoun;
    public boolean articlesNeeded;

    public WordSection(ResultImage resultImage, boolean articlesNeeded) {
        super(resultImage);
        this.femeninoSolution = "undefined";
        this.masculinoSolution = "undefined";
        this.isNoun = false;
        this.articlesNeeded = articlesNeeded;

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

        ArrayList<String> solutions = new ArrayList<>();
        if (!getFemeninoSolution().equals("")) {
            if (!isNoun || !articlesNeeded) solutions.add(getFemeninoSolution());
            if (isNoun) solutions.add("la " + getFemeninoSolution());
        }
        if (!getMasculinoSolution().equals("")) {
            if (!isNoun || !articlesNeeded) solutions.add(getMasculinoSolution());
            if (isNoun) solutions.add("el " + getMasculinoSolution());
        }

        String inputSolution = input.getText().trim().toLowerCase();
        if (solutions.contains(inputSolution)) {
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

    public boolean isNoun() {
        return isNoun;
    }

    public void setNoun(boolean noun) {
        isNoun = noun;
    }
}
