package view;

import controller.VerbQuizController;
import model.Verb;
import model.WordQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class WordQuiz extends JPanel {
    private final MainWindow main;
    private final WordQuizController controller;
    private final ArrayList<Verb> verbs;
    private final WordQuizComponents comps;

    public WordQuiz(MainWindow main, VerbQuizController controller) {
        this.main = main;
        this.controller = controller;
        this.verbs = controller.getVerbs();
        this.comps = controller.getComps();
        this.isNormal = comps.isNormal();

        this.currentVerbLabel = new JLabel("");
        this.currentFormLabel = new JLabel("");
        this.sendResultsButton = new JButton("K\u00FCld\u00E9s");

        this.pressedNext = false;
        this.incorrectVerbs = new ArrayList<>();

        setLayout(new MigLayout("al center center"));
        initComponents();

        score = 0;
        iteration = 0;
        nextRound();

        setVisible(true);
    }
}
