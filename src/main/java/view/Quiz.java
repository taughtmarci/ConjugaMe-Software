package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class Quiz extends JPanel {

    public Quiz() {
        setLayout(new MigLayout("al center center"));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        ArrayList<JLabel> label = new ArrayList<JLabel>() {{
            add(new JLabel("Yo"));
            add(new JLabel("Tu"));
            add(new JLabel("Usted"));
            add(new JLabel("Nosotros"));
            add(new JLabel("Vosotros"));
            add(new JLabel("Ustedes"));
        }};

        ArrayList<JTextField> input = new ArrayList<JTextField>() {{
            add(new JTextField(20));
            add(new JTextField(20));
            add(new JTextField(20));
            add(new JTextField(20));
            add(new JTextField(20));
            add(new JTextField(20));
        }};

        JButton sendResultsButton = new JButton("Send");

        for (int i = 0; i < label.size(); i++) {
            add(label.get(i), "align right");
            add(input.get(i), "wrap");
        }
        add(sendResultsButton, "span 2, align center");
    }
}
