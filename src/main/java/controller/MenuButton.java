package controller;

import model.DialogType;
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
    private final int number;

    private final ImageIcon normalIcon;
    private final ImageIcon hoverIcon;
    private final ImageIcon clickedIcon;

    private boolean isDarkMode;
    private ImageIcon greyIcon;

    public MenuButton(String panel, int number) throws IOException {
        super();
        this.number = number;
        this.isDarkMode = MainWindow.config.isDarkMode();

        String pathTemp = "img/buttons/" + panel + "/button_0" + number;
        BufferedImage normalImage = ImageIO.read(new File(pathTemp + IconVariation.NORMAL + ".png"));
        BufferedImage hoverImage = ImageIO.read(new File(pathTemp + IconVariation.HOVER + ".png"));
        BufferedImage clickedImage = ImageIO.read(new File(pathTemp + IconVariation.CLICKED + ".png"));
        BufferedImage greyImage = ImageIO.read(new File(pathTemp + IconVariation.GREY + ".png"));

        this.normalIcon = new ImageIcon(normalImage);
        this.hoverIcon = new ImageIcon(hoverImage);
        this.clickedIcon = new ImageIcon(clickedImage);
        this.greyIcon = new ImageIcon(greyImage);

        if (isDarkMode) setIcon(greyIcon);
        else setIcon(normalIcon);

        setLookActions();
    }

    public void refreshDarkMode(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;

        if (isDarkMode) setIcon(greyIcon);
        else setIcon(normalIcon);
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
                if (isDarkMode) setIcon(greyIcon);
                else setIcon(normalIcon);
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
                        MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                                "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra, vagy ha a probl\u00E9ma kor\u00E1bbr\u00F3l is fenn\u00E1ll, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
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
                        MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                                "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra, vagy ha a probl\u00E9ma kor\u00E1bbr\u00F3l is fenn\u00E1ll, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
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
                        MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                                "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra, vagy ha a probl\u00E9ma kor\u00E1bbr\u00F3l is fenn\u00E1ll, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
                    }
                }
            });
            case 4 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        JPanel next = new Achievements(main);
                        main.switchPanels(current, next);
                    } catch (IOException ex) {
                        MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                                "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra, vagy ha a probl\u00E9ma kor\u00E1bbr\u00F3l is fenn\u00E1ll, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
                    }
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
            default -> MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                    "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra az alkalmaz\u00E1st!", DialogType.ERROR);
        }
    }

    public void setActionSettings(MainWindow main, Settings current) {
        switch (number) {
            case 1 -> this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        current.getPrefs().savePrefs();
                    } catch (UnsupportedLookAndFeelException | IOException ex) {
                        MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                                "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra, vagy ha a probl\u00E9ma kor\u00E1bbr\u00F3l is fenn\u00E1ll, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
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
                        MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                                "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra, vagy ha a probl\u00E9ma kor\u00E1bbr\u00F3l is fenn\u00E1ll, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
                    }
                }
            });
            default -> MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                    "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra az alkalmaz\u00E1st!", DialogType.ERROR);
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
                            MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                                    "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra, vagy ha a probl\u00E9ma kor\u00E1bbr\u00F3l is fenn\u00E1ll, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
                        }
                    }
                });
                default -> MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                        "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra az alkalmaz\u00E1st!", DialogType.ERROR);
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
                            MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                                    "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra, vagy ha a probl\u00E9ma kor\u00E1bbr\u00F3l is fenn\u00E1ll, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
                        }
                    }
                });
                default -> MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                        "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra az alkalmaz\u00E1st!", DialogType.ERROR);
            }
        }
    }

}
