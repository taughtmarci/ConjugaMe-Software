package view;

import controller.QuizComponents;
import controller.VerbCollection;
import controller.QuizResults;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class EndQuiz extends JPanel {
    private final QuizResults results;
    private final VerbCollection collection;
    private final QuizComponents components;

    private JLabel resultLabel;
    private JLabel percentLabel;
    private JButton restartButton;
    private JButton newQuizButton;

    private JPanel current;

    public EndQuiz(QuizResults results, VerbCollection collection) {
        this.results = results;
        this.collection = collection;
        this.components = collection.getComponents();

        setLayout(new MigLayout("align center center"));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Result/Max
        resultLabel = new JLabel("Pontsz\u00E1m: " + results.getScore() + "/" + components.getTotalNumberOfVerbs());
        add(resultLabel, "span");

        // Result in percent
        float percentResult = (float) results.getScore() / (float) components.getTotalNumberOfVerbs();
        percentLabel = new JLabel(QuizResults.df.format(percentResult * 100) + "%");
        add(percentLabel, "span");

        // Restart button
        restartButton = new JButton("\u00DAjraind\u00EDt\u00E1s");
        add(restartButton);

        restartButton.addActionListener(e -> {
            setVisible(false);
            collection.randomizeVerbList();
            current = new Quiz(collection);
            collection.getMain().switchPanels(this, current);
        });

        newQuizButton = new JButton("\u00DAj kv\u00EDz ind\u00EDt\u00E1sa");
        add(newQuizButton);

        newQuizButton.addActionListener(e -> {
            setVisible(false);
            current = new StartQuiz(collection.getMain());
            collection.getMain().switchPanels(this, current);
        });
    }
}
