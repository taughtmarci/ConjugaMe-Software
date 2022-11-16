package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResultImage {
    private final BufferedImage checkImg;
    private final BufferedImage crossImg;
    private final BufferedImage blankImg;

    public ResultImage() {
        try {
            checkImg = ImageIO.read(new File("check.png"));
            crossImg = ImageIO.read(new File("cross.png"));
            blankImg = ImageIO.read(new File("blank.png"));
        } catch (IOException e) {
            // TODO dialog
            throw new RuntimeException(e);
        }
    }

    public ImageIcon blankImage() {
        return new ImageIcon(blankImg);
    }

    public ImageIcon crossImage() {
        return new ImageIcon(crossImg);
    }

    public ImageIcon checkImage() {
        return new ImageIcon(checkImg);
    }
}
