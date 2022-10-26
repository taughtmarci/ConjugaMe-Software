package view;

import controller.VerbCollection;
import model.Form;
import model.Pronoun;
import model.QuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class StartQuiz extends JPanel {
    private MainWindow main;
    private Quiz current;
    private QuizComponents components;

    private ArrayList<JCheckBox> pronounCheckBoxes;
    private ArrayList<JCheckBox> formCheckBoxes;
    private JSpinner verbNumberChooser;

    public StartQuiz(MainWindow main) {
        this.main = main;

        setLayout(new MigLayout("al center center"));
        initComponents();
        setVisible(true);
    }

    private JPanel initPronounPanel() {
        JPanel pronounPanel = new JPanel();
        pronounPanel.setLayout(new MigLayout("al center center"));

        // pronouns title
        JLabel pronounTitle = new JLabel("Szem\u00E9ly(ek):");
        pronounPanel.add(pronounTitle, "wrap");

        // pronouns checkboxes
        pronounCheckBoxes = new ArrayList<JCheckBox>();
        for (Pronoun p : Pronoun.values()) {
            boolean defaultFlag = ((p.toString() == "Vos") || (p.toString() == "Vosotros")) ? false : true;
            pronounCheckBoxes.add(new JCheckBox(p.toString(), defaultFlag));
            pronounPanel.add(pronounCheckBoxes.get(pronounCheckBoxes.size() - 1), "wrap");
        }

        pronounPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return pronounPanel;
    }

    private JPanel initFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new MigLayout("al center center"));

        // verb forms title
        JLabel formTitle = new JLabel("Igeid\u0151(k) \u00E9s m\u00F3d(ok):");
        formPanel.add(formTitle, "wrap");

        // verb forms checkboxes
        formCheckBoxes = new ArrayList<>();
        for (Form f: Form.values()) {
            formCheckBoxes.add(new JCheckBox(f.toString()));
            formPanel.add(formCheckBoxes.get(formCheckBoxes.size() - 1), "wrap");
        }

        formPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return formPanel;
    }

    private JPanel initLastPanel() {
        JPanel lastPanel = new JPanel();
        lastPanel.setLayout(new MigLayout("al center center"));

        // number of verbs title
        JLabel verbNumberTitle = new JLabel("Ig\u00E9k sz\u00E1ma:");
        lastPanel.add(verbNumberTitle, "wrap");

        // number of verbs spinner
        SpinnerNumberModel verbNumberModel = new SpinnerNumberModel(25, 5, 500, 5);
        verbNumberChooser = new JSpinner(verbNumberModel);
        lastPanel.add(verbNumberChooser, "wrap");

        lastPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return lastPanel;
    }

    private void initComponents() {
        components = new QuizComponents();
        add(initPronounPanel());
        add(initFormPanel());
        add(initLastPanel(), "wrap");

        // new quiz button
        JButton newQuizButton = new JButton("\u00DAj kv\u00EDz ind\u00EDt\u00E1sa");
        add(newQuizButton, "span");
        newQuizButton.addActionListener(e -> {
            for (JCheckBox cb : pronounCheckBoxes) {
                if (cb.isSelected()) {
                    components.addPronoun(cb.getText());
                }
            }

            for (JCheckBox cb : formCheckBoxes) {
                if (cb.isSelected()) {
                    components.addForm(cb.getText());
                }
            }

            // TODO: kétoldalú érme
            components.setNumberOfVerbs((int) verbNumberChooser.getValue());
            VerbCollection vc = new VerbCollection(main, components);

            setVisible(false);
            current = new Quiz(vc);
            main.switchPanels(current);
        });
    }

}