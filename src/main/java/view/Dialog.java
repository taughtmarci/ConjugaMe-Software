package view;

import model.DialogType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialog extends JFrame implements ActionListener {

    private Runnable noCommand;

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        String[] part = action.split(";");

        JDialog dialog = new JDialog(this, part[0]);
        JLabel label = new JLabel(part[1]);
        dialog.add(label);

        dialog.setVisible(true);
    }

    public int setPaneType(DialogType type) {
        int paneType = switch (type) {
            case PLAIN -> -1;
            case ERROR -> 0;
            case INFORMATION -> 1;
            case WARNING -> 2;
            case QUESTION -> 3;
        };
        return paneType;
    }

    public void showDialog(String title, String message, DialogType type) {
        int paneType = setPaneType(type);
        JOptionPane.showMessageDialog(this, message, title, paneType);
    }

    public void showYesNoDialog(String title, String message, DialogType type, Runnable yesCommand, Runnable noCommand) {
        int paneType = setPaneType(type);
        int result = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, paneType);

        if (result == JOptionPane.YES_OPTION)
            yesCommand.run();
        else noCommand.run();
    }
}
