package controller;

import model.IconVariation;
import model.QuizComponents;
import view.*;

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
            case 1 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        JPanel next = new VerbQuiz(main);
                        main.switchPanels(current, next);
                    } catch (IOException ex) {
                        // todo dialogize
                        throw new RuntimeException(ex);
                    }
                }
            });
            case 2 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        JPanel next = new WordQuiz(main);
                        main.switchPanels(current, next);
                    } catch (IOException ex) {
                        // todo dialogize
                        throw new RuntimeException(ex);
                    }
                }
            });
            case 3 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        JPanel next = new SetupPane(main);
                        main.switchPanels(current, next);
                    } catch (IOException ex) {
                        // todo dialogize
                        throw new RuntimeException(ex);
                    }
                }
            });
            case 4 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println(":D");
                }
            });
            case 5 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JPanel next = new Settings(main);
                    main.switchPanels(current, next);
                }
            });
            case 6 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.exit(0);
                }
            });
            default -> throw new RuntimeException();
            // TODO dialog
        }
    }

    public void setActionSettings(MainWindow main, Settings current) {
        switch (number) {
            case 1 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        current.getPrefs().savePrefs();
                    } catch (UnsupportedLookAndFeelException ex) {
                        // todo dialogize
                        throw new RuntimeException(ex);
                    }
                }
            });
            case 2 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        JPanel next = new Dashboard(main);
                        main.switchPanels(current, next);
                    } catch (IOException ex) {
                        // todo dialogize
                        throw new RuntimeException(ex);
                    }
                }
            });
            default -> throw new RuntimeException();
            // TODO dialog
        }
    }

    public void setActionSetup(MainWindow main, SetupPane current, QuizComponents outputComponents) {
        if (outputComponents.isVerb()) {
            switch (number) {
                case 1 -> this.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        current.getVerbQuizSetup().getPrefs().savePrefs(true);
                    }
                });
                case 2 -> this.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        current.getVerbQuizSetup().getPrefs().savePrefs(false);
                    }
                });
                case 3 -> this.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        try {
                            JPanel next = new Dashboard(main);
                            main.switchPanels(current, next);
                        } catch (IOException ex) {
                            // todo dialogize
                            throw new RuntimeException(ex);
                        }
                    }
                });
                default -> throw new RuntimeException();
                // TODO dialog
            }
        } else {
            switch (number) {
                case 1 -> this.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        current.getWordQuizSetup().getPrefs().savePrefs(true);
                    }
                });
                case 2 -> this.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        current.getWordQuizSetup().getPrefs().savePrefs(false);
                    }
                });
                case 3 -> this.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        try {
                            JPanel next = new Dashboard(main);
                            main.switchPanels(current, next);
                        } catch (IOException ex) {
                            // todo dialogize
                            throw new RuntimeException(ex);
                        }
                    }
                });
                default -> throw new RuntimeException();
                // TODO dialog
            }
        }
    }

}
