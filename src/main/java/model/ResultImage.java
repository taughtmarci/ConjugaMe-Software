package model;

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
            // TODO dialog
            throw new RuntimeException(e);
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
