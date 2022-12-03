package view;

import controller.VerbQuizResults;
import model.VerbQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.io.IOException;

public class EndQuiz extends JPanel {
    private final MainWindow main;

    private final VerbQuizResults results;
    private final VerbQuizComponents comps;

    private JLabel resultLabel;
    private JLabel percentLabel;
    private JButton restartButton;
    private JButton preferencesButton;

    private JPanel next;

    public EndQuiz(MainWindow main, VerbQuizResults results) {
        this.main = main;
        this.results = results;
        this.comps = results.getComps();

        setLayout(new MigLayout("align center center"));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Result/Max
        resultLabel = new JLabel("Pontsz\u00E1m: " + results.getScore() + "/" + comps.getTotalNumberOfVerbs());
        add(resultLabel, "span");

        // Result in percent
        float percentResult = (float) results.getScore() / (float) comps.getTotalNumberOfVerbs();
        percentLabel = new JLabel(VerbQuizResults.df.format(percentResult * 100) + "%");
        add(percentLabel, "span");

        /*
        3 opció:
        kvíz újraindítása
        preferences menü
        dashboard
         */

        // Restart button
        restartButton = new JButton("\u00DAjraind\u00EDt\u00E1s");
        add(restartButton);

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
        preferencesButton = new JButton("Dashboard");
        add(preferencesButton);

        preferencesButton.addActionListener(e -> {
            setVisible(false);
            try {
                next = new Dashboard(main);
                main.switchPanels(this, next);
            } catch (IOException ex) {
                // todo dialog
                throw new RuntimeException(ex);
            }
        });
    }
}
