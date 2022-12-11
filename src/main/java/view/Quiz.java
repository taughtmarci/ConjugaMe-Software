package view;

import model.ResultImage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public abstract class Quiz extends JPanel {
    protected final MainWindow main;
    protected final JButton sendButton;
    protected final ResultImage resultImage;

    public Quiz(MainWindow main) {
        this.main = main;
        this.resultImage = new ResultImage();

        this.sendButton = new JButton("K\u00FCld\u00E9s");
        setLayout(new MigLayout("al center center"));
    }

    public MainWindow getMain() {
        return main;
    }

}
