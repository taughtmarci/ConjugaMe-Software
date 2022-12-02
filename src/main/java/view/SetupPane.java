package view;

import model.VerbQuizComponents;

import javax.swing.*;
import java.io.IOException;

public class SetupPane extends JPanel {
    private final MainWindow main;
    private final VerbQuizSetup verbQuizSetup;
    private JPanel wordQuizSetup;

    private JTabbedPane tabbedPane;

    public SetupPane(MainWindow main, VerbQuizComponents verbComps) throws IOException {
        this.main = main;
        this.verbQuizSetup = new VerbQuizSetup(this, verbComps);
        this.tabbedPane = new JTabbedPane();

        initComponents();
    }

    private void initComponents() {
        tabbedPane.add("Ig\u00E9k", verbQuizSetup);

        this.add(tabbedPane);
    }

    public MainWindow getMain() {
        return main;
    }

    public VerbQuizSetup getVerbQuizSetup() {
        return verbQuizSetup;
    }
}
