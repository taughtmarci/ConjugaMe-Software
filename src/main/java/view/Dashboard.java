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
    private final int BUTTON_NUMBER = 6;
    private final MainWindow main;

    private JLabel logo;
    private final ImageIcon logoIcon;

    private ArrayList<JLabel> buttons;
    private ArrayList<ImageIcon> normalIcons;
    private ArrayList<ImageIcon> hoverIcons;
    private ArrayList<ImageIcon> clickedIcons;

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

    private void initComponents() {
        logo = new JLabel();
        logo.setIcon(logoIcon);
        add(logo, "align center, span");

        initButtonPanel();
    }

    private ImageIcon loadIcon(int buttonNumber, IconVariation variation) throws IOException {
        String pathTemp = buttonNumber + variation.toString();
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

                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (il) {
                        case 5 -> System.exit(0);
                        default -> System.out.println("it works!");
                    }
                }
            });
            String constraint = ((i + 1) % 3 == 0) ? "wrap" : "";
            buttonPanel.add(buttons.get(i), constraint);
        }

        this.add(buttonPanel);
    }
}
