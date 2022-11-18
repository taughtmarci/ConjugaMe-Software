package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResultImage {
    private final BufferedImage checkImg;
    private final BufferedImage crossImg;
    private final BufferedImage blankImg;

    private final ImageIcon checkIcon;
    private final ImageIcon crossIcon;
    private final ImageIcon blankIcon;

    public ResultImage() {
        try {
            checkImg = ImageIO.read(new File("check.png"));
            crossImg = ImageIO.read(new File("cross.png"));
            blankImg = ImageIO.read(new File("blank.png"));

            checkIcon = new ImageIcon(checkImg);
            crossIcon = new ImageIcon(crossImg);
            blankIcon = new ImageIcon(blankImg);
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
