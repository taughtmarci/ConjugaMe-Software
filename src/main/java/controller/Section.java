package controller;

import model.ResultImage;
import net.miginfocom.swing.MigLayout;
import view.MainWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public abstract class Section extends JPanel {
    public String solution;
    protected final ResultImage resultImage;
    protected Timer resultIconTimer;

    public JTextField input = new JTextField(15);
    public JLabel checkLabel = new JLabel();

    public Section(ResultImage resultImage) {
        this.solution = "undefined";
        this.resultImage = resultImage;

        if (MainWindow.config.isEnyeEnabled()) {
            input.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    changeToEnye();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changeToEnye();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    changeToEnye();
                }

                public void changeToEnye() {
                    Runnable doChangeToEnye = () -> {
                        if (input.getText().length() > 1) {
                            String enye = "\u00F1";
                            if (input.getText().charAt(input.getText().length() - 2) == 'n' &&
                                    input.getText().charAt(input.getText().length() - 1) == 'y') {
                                try {
                                    Document doc = input.getDocument();
                                    doc.remove(input.getText().length() - 2, 2);
                                    input.getDocument().insertString(input.getText().length(), enye, null);
                                } catch (BadLocationException e) {
                                    // todo dialogize
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    };
                    SwingUtilities.invokeLater(doChangeToEnye);
                }
            });
        }

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
