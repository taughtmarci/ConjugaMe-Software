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
        String[] labels = {"Yo", "Tu", "Usted", "Nosotros", "Vosotros", "Ustedes"};
        ArrayList<JLabel> label = new ArrayList<JLabel>() {{
            for (String s : labels) add(new JLabel(s));
        }};

        ArrayList<JTextField> input = new ArrayList<JTextField>() {{
            for (int i = 0; i < 6; i++) add(new JTextField(20));
        }};

        JButton sendResultsButton = new JButton("Send");

        for (int i = 0; i < label.size(); i++) {
            add(label.get(i), "align right");
            add(input.get(i), "wrap");
        }
        add(sendResultsButton, "span 2, align center");
    }
}
