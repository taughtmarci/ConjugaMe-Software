package view;

import controller.VerbQuizResults;
import model.CorrectConjugations;
import model.IncorrectConjugations;
import model.QuizComponents;
import model.VerbQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class EndVerbQuiz extends JPanel {
    private final MainWindow main;

    private final VerbQuizResults results;
    private final VerbQuizComponents comps;

    private JLabel resultLabel;
    private JLabel percentLabel;
    private JProgressBar percentIndicator;

    private JButton restartButton;
    private JButton dashboardButton;

    private CorrectConjugations correctConjugations;
    private JTable correctsList;

    private IncorrectConjugations incorrectConjugations;
    private JTable incorrectsList;

    private JPanel next;

    public EndVerbQuiz(MainWindow main, VerbQuizResults results) {
        this.main = main;
        this.results = results;
        this.comps = results.getComps();

        this.correctConjugations = results.getCorrectConjugations();
        this.incorrectConjugations = results.getIncorrectConjugations();

        setLayout(new MigLayout("align center center"));
        initComponents();
        setVisible(true);

        configureKeyPresses();
    }

    private JPanel initResultsPanel() {
        JPanel resultsPanel = new JPanel(new MigLayout("al center center"));

        // Runtime measurement
        if (!results.getComps().isNormal()) {
            int resultTime = results.getController().getTime();
            JLabel timeLabel = new JLabel("Id\u0151: " + (resultTime / 60) + ":" + (resultTime % 60));
            resultsPanel.add(timeLabel, "al center center, span");
        }

        // Result/Max
        resultLabel = new JLabel("Pontsz\u00E1m: " + results.getScore() + "/" + results.getOutOf());
        resultsPanel.add(resultLabel, "al center center, span");

        // Result in percent
        percentLabel = new JLabel("Sz\u00E1zal\u00E9k: " + results.getPercentText());
        resultsPanel.add(percentLabel, "al center center, span");

        percentIndicator = new JProgressBar();
        percentIndicator.setPreferredSize(new Dimension(150, 20));
        percentIndicator.setValue((int) results.getPercentage());
        resultsPanel.add(percentIndicator, "al center center, span");

        resultsPanel.setBorder(BorderFactory.createLineBorder(MainWindow.config.getBorderColor()));
        return resultsPanel;
    }

    private JPanel initStatsPanel() {
        JPanel statsPanel = new JPanel(new MigLayout("al center center"));
        JTabbedPane pane = new JTabbedPane();

        JPanel incorrectPanel = new JPanel();
        JPanel correctPanel = new JPanel();

        if (incorrectConjugations.size() > 0) {
            incorrectsList = new JTable(incorrectConjugations.getData(), incorrectConjugations.getColumnNames());
            // set model
            DefaultTableModel incorrectsModel = new DefaultTableModel(incorrectConjugations.getData(), incorrectConjugations.getColumnNames()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            incorrectsList.setModel(incorrectsModel);

            JScrollPane incorrectScroll = new JScrollPane(incorrectsList);
            incorrectPanel.add(incorrectScroll);
        }
        if (correctConjugations.size() > 0) {
            correctsList = new JTable(correctConjugations.getData(), correctConjugations.getColumnNames());
            // set model
            DefaultTableModel correctsModel = new DefaultTableModel(correctConjugations.getData(), correctConjugations.getColumnNames()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            correctsList.setModel(correctsModel);

            JScrollPane correctScroll = new JScrollPane(correctsList);
            correctPanel.add(correctScroll);
        }

        pane.add("Hib\u00E1s v\u00E1laszok", incorrectPanel);
        pane.add("Helyes v\u00E1laszok", correctPanel);

        if (incorrectConjugations.size() == 0) pane.setEnabledAt(0, false);
        if (correctConjugations.size() == 0) pane.setEnabledAt(1, false);

        statsPanel.add(pane);
        statsPanel.setBorder(BorderFactory.createLineBorder(MainWindow.config.getBorderColor()));
        return statsPanel;
    }

    private JPanel initButtonPanel() {
        JPanel buttonPanel = new JPanel(new MigLayout("al center center"));

        // Restart button
        restartButton = new JButton("\u00DAjraind\u00EDt\u00E1s");
        buttonPanel.add(restartButton);

        restartButton.addActionListener(e -> {
            setVisible(false);
            results.getController().randomizeVerbList();
            try {
                next = new VerbQuiz(main);
            } catch (IOException ex) {
                // todo dialogize
                throw new RuntimeException(ex);
            }
            main.switchPanels(this, next);
        });

        // Back to dashboard button
        dashboardButton = new JButton("F\u0151men\u00FC");
        buttonPanel.add(dashboardButton);

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

        return buttonPanel;
    }

    private void initComponents() {
        add(initResultsPanel(), "al center, span");
        add(initStatsPanel(), "al center, span");
        add(initButtonPanel(), "al center, span");

        main.getRootPane().setDefaultButton(restartButton);
    }

    private void configureKeyPresses() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_ENTER)
                    restartButton.doClick();

                if (key == KeyEvent.VK_BACK_SPACE)
                    dashboardButton.doClick();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
