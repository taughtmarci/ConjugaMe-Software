package view;

import model.DialogOption;
import model.DialogType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialog extends JFrame implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        String[] part = action.split(";");

        JDialog dialog = new JDialog(this, part[0]);
        JLabel label = new JLabel(part[1]);
        dialog.add(label);

        dialog.setVisible(true);
    }

    public void showDialog(String title, String message, DialogType type) {
        int paneType = switch (type) {
            case PLAIN -> -1;
            case ERROR -> 0;
            case INFORMATION -> 1;
            case WARNING -> 2;
            case QUESTION -> 3;
        };
        /*
        int paneOption = switch (option) {
            case DEFAULT -> -1;
            case YESNO -> 0;
            case YESNOCANCEL -> 1;
            case OKCANCEL -> 2;
        };
        */
        JOptionPane.showMessageDialog(this, message, title, paneType);
    }
}
