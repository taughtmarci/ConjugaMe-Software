package view;

import model.VerbQuizComponents;
import model.WordQuizComponents;

import javax.swing.*;
import java.io.IOException;

public class SetupPane extends JPanel {
    private final MainWindow main;
    private final VerbQuizSetup verbQuizSetup;
    private final WordQuizSetup wordQuizSetup;

    private JTabbedPane tabbedPane;

    public SetupPane(MainWindow main) throws IOException {
        this.main = main;
        this.verbQuizSetup = new VerbQuizSetup(this);
        this.wordQuizSetup = new WordQuizSetup(this);
        this.tabbedPane = new JTabbedPane();

        SwingUtilities.invokeLater(this::initComponents);
    }

    private void initComponents() {
        tabbedPane.add("Igeragoz\u00E1s", verbQuizSetup);
        tabbedPane.add("Sz\u00F3ford\u00EDt\u00E1s", wordQuizSetup);
        this.add(tabbedPane);
    }

    public MainWindow getMain() {
        return main;
    }

    public VerbQuizSetup getVerbQuizSetup() {
        return verbQuizSetup;
    }

    public WordQuizSetup getWordQuizSetup() {
        return wordQuizSetup;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
