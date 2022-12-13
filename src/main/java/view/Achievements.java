package view;

import controller.AchievementsController;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.IOException;

public class Achievements extends JPanel {
    private final MainWindow main;
    private AchievementsController controller;
    private JTabbedPane tabbedPane;


    private JPanel wordRevisionPanel;
    private JComboBox<String> wordRevisionComboBox;
    private JScrollPane wordRevisionScroll;

    private JPanel verbRevisionPanel;
    private JComboBox<String> verbRevisionComboBox;
    private JScrollPane verbRevisionScroll;

    private JPanel scoresPanel;
    private JComboBox<String> quizTypeComboBox;
    private JComboBox<String> quizModeComboBox;
    private JScrollPane scoresScroll;

    private boolean isVerb = true;
    private boolean isNormal = true;

    private JButton dashboardButton;
    private JPanel next;

    public Achievements(MainWindow main) throws IOException {
        this.main = main;
        this.controller = new AchievementsController(this);
        this.tabbedPane = new JTabbedPane();

        setLayout(new MigLayout("al center center"));
        SwingUtilities.invokeLater(this::initComponents);
        setVisible(true);
    }

    private JPanel initVerbRevisionPanel() {
        verbRevisionPanel = new JPanel(new MigLayout("al center center"));
        JLabel verbRevisionTitle = new JLabel("\u00C1tn\u00E9z\u00E9sre v\u00E1r\u00F3 ig\u00E9k");
        verbRevisionTitle.setFont(new Font("Verdana", Font.BOLD, 24));
        verbRevisionPanel.add(verbRevisionTitle, "al center center, span");

        // init verb revision list
        verbRevisionScroll = new JScrollPane(controller.updateVerbRevisionList(controller.getGroupNames()[0]));

        // init verb revision combo box
        verbRevisionComboBox = new JComboBox<>(controller.getGroupNames());
        verbRevisionComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String groupName = String.valueOf(e.getItem());
                verbRevisionPanel.remove(verbRevisionScroll);
                verbRevisionScroll = new JScrollPane(controller.updateVerbRevisionList(groupName));
                verbRevisionPanel.add(verbRevisionScroll, "al center center, span");
                this.revalidate();
                this.repaint();
            }
        });

        verbRevisionPanel.add(new JLabel("Csoport: "));
        verbRevisionPanel.add(verbRevisionComboBox, "span");
        verbRevisionPanel.add(verbRevisionScroll, "al center center, span");

        return verbRevisionPanel;
    }

    private JPanel initWordRevisionPanel() {
        wordRevisionPanel = new JPanel(new MigLayout("al center center"));
        JLabel wordRevisionTitle = new JLabel("\u00C1tn\u00E9z\u00E9sre v\u00E1r\u00F3 ford\u00EDt\u00E1sok");
        wordRevisionTitle.setFont(new Font("Verdana", Font.BOLD, 24));
        wordRevisionPanel.add(wordRevisionTitle, "al center center, span");

        // init word revision list
        wordRevisionScroll = new JScrollPane(controller.updateWordRevisionList(controller.getGroupNames()[0]));

        // init word revision combo box
        wordRevisionComboBox = new JComboBox<>(controller.getGroupNames());
        wordRevisionComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String groupName = String.valueOf(e.getItem());
                wordRevisionPanel.remove(wordRevisionScroll);
                wordRevisionScroll = new JScrollPane(controller.updateWordRevisionList(groupName));
                wordRevisionPanel.add(wordRevisionScroll, "al center center, span");
                this.revalidate();
                this.repaint();
            }
        });

        wordRevisionPanel.add(new JLabel("Csoport: "));
        wordRevisionPanel.add(wordRevisionComboBox, "span");
        wordRevisionPanel.add(wordRevisionScroll, "al center center, span");

        return wordRevisionPanel;
    }

    private JPanel initScorePanel() {
        scoresPanel = new JPanel(new MigLayout("al center center"));

        // scores label
        JLabel scoreLabel = new JLabel("Eredm\u00E9nyek");
        scoreLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        scoresPanel.add(scoreLabel, "al center center, span");

        // init scores list
        scoresScroll = new JScrollPane(controller.updateScoresList(isVerb, isNormal));

        // init combo boxes
        quizTypeComboBox = new JComboBox<>(new String[]{"Igeragoz\u00E1s", "Sz\u00F3ford\u00EDt\u00E1s"});
        quizTypeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                isVerb = String.valueOf(e.getItem()).equals("Igeragoz\u00E1s");
                scoresPanel.remove(scoresScroll);
                scoresScroll = new JScrollPane(controller.updateScoresList(isVerb, isNormal));
                scoresPanel.add(scoresScroll, "al center center, span");
                this.revalidate();
                this.repaint();
            }
        });

        quizModeComboBox = new JComboBox<>(new String[]{"Norm\u00E1l", "Id\u0151z\u00EDtett"});
        quizModeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                isNormal = String.valueOf(e.getItem()).equals("Norm\u00E1l");
                scoresPanel.remove(scoresScroll);
                scoresScroll = new JScrollPane(controller.updateScoresList(isVerb, isNormal));
                scoresPanel.add(scoresScroll, "al center center, span");
                this.revalidate();
                this.repaint();
            }
        });

        // add components to JPanel
        scoresPanel.add(new JLabel("Kv\u00EDz t\u00EDpusa: "), "span 1");
        scoresPanel.add(quizTypeComboBox, "span 1");

        scoresPanel.add(new JLabel("J\u00E1t\u00E9km\u00F3d: "), "span 1");
        scoresPanel.add(quizModeComboBox, "span");

        scoresPanel.add(scoresScroll, "al center center, span");
        return scoresPanel;
    }


    private void initComponents() {
        tabbedPane.add("Eredm\u00E9nyek", initScorePanel());
        tabbedPane.add("Ig\u00E9k \u00E1tn\u00E9z\u00E9sre", initVerbRevisionPanel());
        tabbedPane.add("Ford\u00EDt\u00E1sok \u00E1tn\u00E9z\u00E9sre", initWordRevisionPanel());
        add(tabbedPane, "al center center, span");

        // Back to dashboard button
        dashboardButton = new JButton("F\u0151men\u00FC");
        add(dashboardButton, "align right, span");

        dashboardButton.addActionListener(e -> {
            setVisible(false);
            try {
                next = new Dashboard(main);
                main.switchPanels(this, next);
            } catch (IOException ex) {
                // todo dialogize
                throw new RuntimeException(ex);
            }
        });

        main.getRootPane().setDefaultButton(dashboardButton);
    }
}
