package model;

import view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Badge extends JLabel {
    private String name;
    public Badge(String name) {
        super();
        this.name = name;

        try {
            ImageIcon icon = new ImageIcon(new URL(new URL("file:"), "img/badges/" + name + ".png"));
            this.setIcon(icon);
        } catch (IOException e) {
            MainWindow.dialog.showExceptionDialog("B\u00E9lyeg beolvas\u00E1si hiba", "Az alkalmaz\u00E1s konfigur\u00E1ci\u00F3s f\u00E1jljai megs\u00E9r\u00FClhettek.\n" +
                    "K\u00E9rj\u00FCk, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
            throw new RuntimeException();
        }
    }

    public String getName() {
        return name;
    }
}
