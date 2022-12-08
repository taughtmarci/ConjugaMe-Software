package controller;

import model.ResultImage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public abstract class Section extends JPanel {
    public String solution;
    protected final ResultImage resultImage;
    protected Timer resultIconTimer;

    public JTextField input;
    public JLabel checkLabel = new JLabel();

    public Section(ResultImage resultImage) {
        this.solution = "undefined";
        this.resultImage = resultImage;

        setLayout(new MigLayout("al center center"));
    }

    public abstract boolean evaluate();

    public void refreshSection() {
        input.setText("");
        input.requestFocusInWindow();
    }

    public String getInput() {
        return input.getText();
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
