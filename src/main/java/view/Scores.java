package view;

import controller.ScoresController;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.IOException;

public class Scores extends JPanel {
    private final MainWindow main;
    private ScoresController controller;
    private JTabbedPane tabbedPane;


    private JPanel wordRevisionPanel;
    private JComboBox<String> wordRevisionComboBox;
    private JScrollPane wordRevisionScroll;

    private JPanel verbRevisionPanel;
    private JComboBox<String> verbRevisionComboBox;
    private JScrollPane verbRevisionScroll;

    public Scores(MainWindow main) throws IOException {
        this.main = main;
        this.controller = new ScoresController(this);
        this.tabbedPane = new JTabbedPane();

        setLayout(new MigLayout("al center center"));
        SwingUtilities.invokeLater(this::initComponents);
        setVisible(true);
    }

    private JPanel initVerbRevisionPanel() {
        verbRevisionPanel = new JPanel(new MigLayout("al center center"));
        JLabel verbRevisionTitle = new JLabel("\u00C1tn\u00E9z\u00E9sre v\u00E1r\u00F3 ig\u00E9k");
        verbRevisionTitle.setFont(new Font("Verdana", Font.BOLD, 24));

        // init word revision list
        verbRevisionScroll = new JScrollPane(controller.updateVerbRevisionList(controller.getGroupNames()[0]));

        // init word revision combo box
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


    private void initComponents() {
        tabbedPane.add("Ford\u00EDt\u00E1sok \u00E1tn\u00E9z\u00E9sre", initWordRevisionPanel());
        tabbedPane.add("Ig\u00E9k \u00E1tn\u00E9z\u00E9sre", initVerbRevisionPanel());
        add(tabbedPane, "al center center");
    }
}
