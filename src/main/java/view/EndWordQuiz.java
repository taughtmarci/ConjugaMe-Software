package view;

import controller.WordQuizResults;
import model.QuizComponents;
import model.WordQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.io.IOException;

public class EndWordQuiz extends JPanel {
    private final MainWindow main;

    private final WordQuizResults results;
    private final WordQuizComponents comps;

    private JLabel resultLabel;
    private JLabel percentLabel;
    private JProgressBar percentIndicator;
    private JButton restartButton;
    private JButton preferencesButton;

    private JPanel next;

    public EndWordQuiz(MainWindow main, WordQuizResults results) {
        this.main = main;
        this.results = results;
        this.comps = results.getComps();

        setLayout(new MigLayout("al center center"));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Result/Max
        resultLabel = new JLabel("Pontsz\u00E1m: " + results.getScore() + "/" + results.getOutOf());
        add(resultLabel, "span");

        // Result in percent
        float percentResult = ((float) results.getScore() / (float) results.getOutOf()) * 100;
        percentLabel = new JLabel(QuizComponents.df.format(percentResult) + "%");
        add(percentLabel, "span");

        percentIndicator = new JProgressBar();
        percentIndicator.setValue((int) percentResult);
        add(percentIndicator, "span");

        // Restart button
        restartButton = new JButton("\u00DAjraind\u00EDt\u00E1s");
        add(restartButton);

        restartButton.addActionListener(e -> {
            setVisible(false);
            results.getController().randomizeWordList();
            try {
                next = new WordQuiz(main);
            } catch (IOException ex) {
                // todo dialogize
                throw new RuntimeException(ex);
            }
            main.switchPanels(this, next);
        });

        // Back to dashboard button
        preferencesButton = new JButton("Dashboard");
        add(preferencesButton);

        preferencesButton.addActionListener(e -> {
            setVisible(false);
            try {
                next = new Dashboard(main);
                main.switchPanels(this, next);
            } catch (IOException ex) {
                // todo dialogize
                throw new RuntimeException(ex);
            }
        });
    }
}
