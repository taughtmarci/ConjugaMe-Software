package model;

import view.MainWindow;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class ResultImage {
    private final ImageIcon checkIcon;
    private final ImageIcon crossIcon;
    private final ImageIcon blankIcon;

    public ResultImage() {
        try {
            checkIcon = new ImageIcon(new URL(new URL("file:"), "img/feedback/check.gif"));
            crossIcon = new ImageIcon(new URL(new URL("file:"),"img/feedback/cross.gif"));
            blankIcon = new ImageIcon(new URL(new URL("file:"), "img/feedback/blank.png"));
        } catch (IOException e) {
            MainWindow.dialog.showExceptionDialog("K\u00E9p beolvas\u00E1si hiba", "Az alkalmaz\u00E1s konfigur\u00E1ci\u00F3s f\u00E1jljai megs\u00E9r\u00FClhettek.\n" +
                    "K\u00E9rj\u00FCk, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
            throw new RuntimeException();
        }
    }

    public ImageIcon blankImage() {
        return blankIcon;
    }

    public ImageIcon crossImage() {
        return crossIcon;
    }

    public ImageIcon checkImage() {
        return checkIcon;
    }
}
