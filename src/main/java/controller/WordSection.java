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

    public Timer resultIconTime;

    public JTextField input;
    public JLabel checkLabel = new JLabel();

    private boolean isFirst = false;

    public WordSection(ResultImage resultImage) {
        this.femeninoSolution = "undefined";
        this.masculinoSolution = "undefined";
        this.resultImage = resultImage;

        setLayout(new MigLayout("al center center"));
        initComponents();
    }

    private void initComponents() {
        input = new JTextField(15);
        input.setText("");
        input.setBorder(new LineBorder(Color.GRAY));
        checkLabel.setIcon(resultImage.blankImage());

        resultIconTime = new Timer(2000, e -> checkLabel.setIcon(resultImage.blankImage()));
        resultIconTime.setRepeats(false);

        add(input, "align center");
        add(checkLabel, "align left, wrap");

        if (isFirst) input.requestFocusInWindow();
    }

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
        resultIconTime.start();

        return result;
    }

    public void refreshSection() {
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
