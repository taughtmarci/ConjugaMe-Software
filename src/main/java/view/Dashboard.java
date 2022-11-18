package view;

import model.IconVariation;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Dashboard extends JPanel {
    private final int BUTTON_NUMBER = 3;
    private final MainWindow main;

    private JLabel logo;
    private final ImageIcon logoIcon;

    private ArrayList<JLabel> buttons;
    private ArrayList<ImageIcon> normalIcons;
    private ArrayList<ImageIcon> hoverIcons;
    private ArrayList<ImageIcon> clickedIcons;

    private JLabel newNormalQuizLabel;

    private JLabel newTimedQuizLabel;

    public Dashboard(MainWindow main) {
        this.main = main;
        setLayout(new MigLayout("align center center"));

        this.buttons = new ArrayList<>();
        this.normalIcons = new ArrayList<>();
        this.hoverIcons = new ArrayList<>();
        this.clickedIcons = new ArrayList<>();

        try {
            logoIcon = new ImageIcon(ImageIO.read(new File("title.png")));
            for (int i = 0; i < BUTTON_NUMBER; i++) {
                normalIcons.add(loadIcon(i + 1, IconVariation.NORMAL));
                hoverIcons.add(loadIcon(i + 1, IconVariation.HOVER));
                clickedIcons.add(loadIcon(i + 1, IconVariation.CLICKED));
            }
        } catch(IOException e) {
            // TODO dialog
            throw new RuntimeException(e);
        }

        initComponents();
    }

    private ImageIcon loadIcon(int buttonNumber, IconVariation variation) throws IOException {
        String pathTemp = buttonNumber + variation.toString();
        System.out.println("img/buttons/button_0" + pathTemp + ".png");
        BufferedImage imageTemp = ImageIO.read(new File("img/buttons/button_0" + pathTemp + ".png"));
        return new ImageIcon(imageTemp);
    }

    private void initButtonPanel() {
        JPanel buttonPanel = new JPanel(new MigLayout("align center center"));

        for (int i = 0; i < BUTTON_NUMBER; i++) {
            buttons.add(new JLabel());
            buttons.get(i).setIcon(normalIcons.get(i));

            final int il = i;
            buttons.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    buttons.get(il).setIcon(clickedIcons.get(il));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    buttons.get(il).setIcon(hoverIcons.get(il));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    buttons.get(il).setIcon(normalIcons.get(il));
                }
            });
            buttonPanel.add(buttons.get(i));
        }

        this.add(buttonPanel);
    }

    private void initComponents() {
        logo = new JLabel();
        logo.setIcon(logoIcon);
        add(logo, "span");

        initButtonPanel();
    }
}
