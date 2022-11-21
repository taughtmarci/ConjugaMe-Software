package model;

import controller.ConfigWriter;
import controller.QuizComponents;
import view.Dashboard;
import view.MainWindow;
import view.SetupQuiz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuButton extends JLabel {
    private final String panel;
    private final int number;

    private final ImageIcon normalIcon;
    private final ImageIcon hoverIcon;
    private final ImageIcon clickedIcon;

    public MenuButton(String panel, int number) throws IOException {
        super();
        this.panel = panel;
        this.number = number;

        String pathTemp = "img/buttons/" + panel + "/button_0" + number;
        BufferedImage normalImage = ImageIO.read(new File(pathTemp + IconVariation.NORMAL + ".png"));
        BufferedImage hoverImage = ImageIO.read(new File(pathTemp + IconVariation.HOVER + ".png"));
        BufferedImage clickedImage = ImageIO.read(new File(pathTemp + IconVariation.CLICKED + ".png"));

        this.normalIcon = new ImageIcon(normalImage);
        this.hoverIcon = new ImageIcon(hoverImage);
        this.clickedIcon = new ImageIcon(clickedImage);

        setIcon(normalIcon);
        setLookActions();
    }

    private void setLookActions() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(clickedIcon);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(normalIcon);
            }
        });
    }

    public void setActionDashboard(MainWindow main, Dashboard current) {
        switch (number) {
            case 0 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("xD");
                }
            });
            case 1 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(":P");
                }
            });
            case 2 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(":O");
                }
            });
            case 3 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JPanel next = new SetupQuiz(main);
                    main.switchPanels(current, next);
                }
            });
            case 4 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(":D");
                }
            });
            case 5 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(":))");
                }
            });
            case 6 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }
            });
            default -> throw new RuntimeException();
            // TODO dialog
        }
    }

    public void setActionSetup(MainWindow main, SetupQuiz current, QuizComponents outputComponents) {
        switch (number) {
            case 0 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("xD");
                }
            });
            case 1 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(":P");
                }
            });
            case 2 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(":)))");
                }
            });
            case 3 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JPanel next = new Dashboard(main);
                    main.switchPanels(current, next);
                }
            });
            default -> throw new RuntimeException();
            // TODO dialog
        }
    }

}
