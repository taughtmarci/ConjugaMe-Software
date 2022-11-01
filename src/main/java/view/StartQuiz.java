package view;

import controller.VerbCollection;
import model.Form;
import model.Pronoun;
import controller.QuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class StartQuiz extends JPanel {
    private final MainWindow main;
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
            boolean defaultFlag = (p.toString() != "Vos") && (p.toString() != "Vosotros");
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
            boolean defaultFlag = (f.toString() == "Indicativo Presento");
            formCheckBoxes.add(new JCheckBox(f.toString(), defaultFlag));
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

        // set up error label
        JLabel errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        errorLabel.setForeground(Color.RED);
        add(errorLabel, "span");

        // add panels
        add(initPronounPanel());
        add(initFormPanel());
        add(initLastPanel(), "wrap");

        // new quiz button
        JButton newQuizButton = new JButton("\u00DAj kv\u00EDz ind\u00EDt\u00E1sa");
        add(newQuizButton, "span");

        newQuizButton.addActionListener(e -> {

            for (JCheckBox cb : pronounCheckBoxes)
                if (cb.isSelected()) components.addPronoun(cb.getText());

            for (JCheckBox cb : formCheckBoxes) {
                if (cb.isSelected()) {
                    if (cb.getText() == "Gerundio") components.setGerundioFlag(true);
                    if (cb.getText() == "Participio") components.setParticipioFlag(true);
                    components.addForm(cb.getText());
                }
            }

            String error = "";
            // TODO: kétoldalú érme
            components.setNumberOfVerbs((int) verbNumberChooser.getValue());
            //components.printStats();
            if (!components.hasGerundio() && !components.hasParticipio()) {
                if (components.getSelectedForms().size() < 1) {
                    error = "Legal\u00E1bb egy igeid\u0151/m\u00F3d kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";}
                else if (components.getSelectedPronouns().size() < 1)
                    error = "Legal\u00E1bb egy n\u00E9vm\u00E1s kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";
                else if (components.getNumberOfVerbs() < 5 || components.getNumberOfVerbs() > 500)
                    error = "Az ig\u00E9k sz\u00E1ma 5 és 500 k\u00F6z\u00F6tt kell, hogy legyen!";
            }

            if (error.equals("")) {
                VerbCollection vc = new VerbCollection(main, components);
                setVisible(false);
                current = new Quiz(vc);
                main.switchPanels(current);
            } else {
                errorLabel.setText(error);
                this.components = new QuizComponents();
                this.updateUI();
            }
        });
    }

}