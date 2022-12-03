package controller;

import model.ResultImage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class WordSection extends JPanel {
    public String femeninoSolution;
    public String masculinoSolution;
    public final ResultImage resultImage;

    public JTextField input;
    public JLabel checkLabel;

    private boolean isFirst;

    public WordSection(ResultImage resultImage) {
        this.femeninoSolution = "undefined";
        this.masculinoSolution = "undefined";
        this.resultImage = resultImage;

        this.checkLabel = new JLabel();
        this.isFirst = false;

        setLayout(new MigLayout("al center center"));
        initComponents();
    }

    private void initComponents() {
        input = new JTextField(15);
        input.setText("");
        input.setBorder(new LineBorder(Color.GRAY));
        checkLabel.setIcon(resultImage.blankImage());

        add(input, "align center");
        add(checkLabel, "align left, wrap");

        if (isFirst) input.requestFocusInWindow();
    }

    public boolean evaluate() {
        String inputSolution = input.getText().trim();
        if (femeninoSolution.equals(inputSolution) || masculinoSolution.equals(inputSolution)) {
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

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
