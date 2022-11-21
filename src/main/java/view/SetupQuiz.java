package view;

import controller.*;
import model.Form;
import model.MenuButton;
import model.Pronoun;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;


public class SetupQuiz extends JPanel {
    private final int BUTTON_NUMBER = 4;

    private final MainWindow main;
    private Quiz current;

    private QuizComponents components;
    private final QuizComponents inputComponents;

    private ArrayList<JCheckBox> pronounCheckBoxes;
    private ArrayList<JCheckBox> formCheckBoxes;
    private JSpinner verbNumberChooser;
    private JSpinner minutesChooser;
    private JSpinner secondsChooser;
    private JCheckBox instantFeedbackCheckBox;

    private ArrayList<MenuButton> buttons;

    public SetupQuiz(MainWindow main) {
        this.main = main;
        ConfigReader cf = new ConfigReader();
        this.buttons = new ArrayList<>();
        this.inputComponents = cf.inputComponents;

        setLayout(new MigLayout("al center center"));
        SwingUtilities.invokeLater(this::initComponents);
        setVisible(true);
    }

    public SetupQuiz(MainWindow main, QuizComponents inputComponents) {
        this.main = main;
        this.inputComponents = inputComponents;

        setLayout(new MigLayout("al center center"));
        SwingUtilities.invokeLater(this::initComponents);
        setVisible(true);
    }

    private JPanel initPronounPanel() {
        JPanel pronounPanel = new JPanel();
        pronounPanel.setLayout(new MigLayout("al center center"));

        // pronouns title
        JLabel pronounTitle = new JLabel("Szem\u00E9ly(ek):");
        pronounPanel.add(pronounTitle, "align center, wrap");

        // pronouns checkboxes
        pronounCheckBoxes = new ArrayList<JCheckBox>();
        for (Pronoun p : Pronoun.values()) {
            //boolean defaultFlag = (!p.toString().equals("Vos")) && (!p.toString().equals("Vosotros"));
            boolean inputFlag = inputComponents.getSelectedPronouns().contains(p);
            pronounCheckBoxes.add(new JCheckBox(p.toString(), inputFlag));
            pronounPanel.add(pronounCheckBoxes.get(pronounCheckBoxes.size() - 1), "wrap");
        }

        pronounPanel.setPreferredSize(new Dimension(getWidth(), 280));
        pronounPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        return pronounPanel;
    }

    private JPanel initFormPanel() {
        // main form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new MigLayout("", "[center]", "[top]"));

        JLabel formTitle = new JLabel("Igeid\u0151(k) \u00E9s m\u00F3d(ok):");
        formPanel.add(formTitle, "align center, span");

        // participio panel and title
        JPanel participioPanel = new JPanel();
        participioPanel.setLayout(new MigLayout());
        JLabel participioTitle = new JLabel("Participio");
        participioPanel.add(participioTitle, "wrap");

        // indicativo panel and title
        JPanel indicativoPanel = new JPanel();
        indicativoPanel.setLayout(new MigLayout());
        JLabel indicativoTitle = new JLabel("Indicativo");
        indicativoPanel.add(indicativoTitle, "wrap");

        // imperativo panel and title
        JPanel imperativoPanel = new JPanel();
        imperativoPanel.setLayout(new MigLayout());
        JLabel imperativoTitle = new JLabel("Imperativo");
        imperativoPanel.add(imperativoTitle, "wrap");

        // subjuntivo panel and title
        JPanel subjuntivoPanel = new JPanel();
        subjuntivoPanel.setLayout(new MigLayout());
        JLabel subjuntivoTitle = new JLabel("Subjuntivo");
        subjuntivoPanel.add(subjuntivoTitle, "wrap");

        // verb forms checkboxes
        formCheckBoxes = new ArrayList<>();

        formCheckBoxes.add(new JCheckBox("Participio Presento", inputComponents.isParticipioPresentoSelected()));
        formCheckBoxes.add(new JCheckBox("Participio Pasado", inputComponents.isParticipioPasadoSelected()));
        participioPanel.add(formCheckBoxes.get(0), "wrap");
        participioPanel.add(formCheckBoxes.get(1), "wrap");

        for (Form f : Form.values()) {
            boolean inputFlag = inputComponents.getSelectedForms().contains(f);
            formCheckBoxes.add(new JCheckBox(f.toString(), inputFlag));
            if (f == Form.ImperativoAffirmativo || f == Form.ImperativoNegativo)
                imperativoPanel.add(formCheckBoxes.get(formCheckBoxes.size() - 1), "wrap");
            else if (f == Form.SubjuntivoPresento || f == Form.SubjuntivoImperfecto || f == Form.SubjuntivoFuturo)
                subjuntivoPanel.add(formCheckBoxes.get(formCheckBoxes.size() - 1), "wrap");
            else indicativoPanel.add(formCheckBoxes.get(formCheckBoxes.size() - 1), "wrap");
        }

        // border, add panels to formPanel
        formPanel.add(participioPanel, "align left, cell 0 1");
        formPanel.add(indicativoPanel, "align left, cell 0 2");
        formPanel.add(imperativoPanel, "align left, cell 1 1");
        formPanel.add(subjuntivoPanel, "align left, cell 1 2");

        formPanel.setPreferredSize(new Dimension(getWidth(), 280));
        formPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return formPanel;
    }

    // TODO struktúráltabbá tenni
    private JPanel initLastPanel() {
        JPanel lastPanel = new JPanel();
        lastPanel.setLayout(new MigLayout("al center center"));

        // group selector with title
        JLabel groupTitle = new JLabel("Igecsoport(ok):");
        lastPanel.add(groupTitle, "align center, span");
        GroupSelector gs = new GroupSelector();
        lastPanel.add(gs, "align center, span");

        // number of verbs title
        JLabel verbNumberTitle = new JLabel("Ig\u00E9k sz\u00E1ma:");
        lastPanel.add(verbNumberTitle, "span");

        JLabel verbNumberSubtitle = new JLabel("(hagyom\u00E1nyos)");
        verbNumberSubtitle.setFont(new Font("Verdana", Font.ITALIC, 10));
        verbNumberSubtitle.setForeground(Color.GRAY);
        lastPanel.add(verbNumberSubtitle, "span");

        // number of verbs spinner
        SpinnerNumberModel verbNumberModel = new SpinnerNumberModel(inputComponents.getNumberOfVerbs(), 5, 500, 5);
        verbNumberChooser = new JSpinner(verbNumberModel);
        lastPanel.add(verbNumberChooser, "span");

        // instant feedback checkbox
        instantFeedbackCheckBox = new JCheckBox("Hib\u00E1k mutat\u00E1sa", inputComponents.isFeedbackEnabled());
        lastPanel.add(instantFeedbackCheckBox, "span");

        // timing duration title
        JLabel timingDurationTitle = new JLabel("Id\u0151tartam:");
        lastPanel.add(timingDurationTitle, "span");

        JLabel timingDurationSubtitle = new JLabel("(id\u0151z\u00EDtett)");
        timingDurationSubtitle.setFont(new Font("Verdana", Font.ITALIC, 10));
        timingDurationSubtitle.setForeground(Color.GRAY);
        lastPanel.add(timingDurationSubtitle, "span");

        // minutes spinner and label
        SpinnerNumberModel minutesModel = new SpinnerNumberModel(inputComponents.getDurationMin(), 0, 180, 1);
        minutesChooser = new JSpinner(minutesModel);
        minutesChooser.setUI(new BasicSpinnerUI() {
            protected Component createNextButton() {
                return null;
            }

            protected Component createPreviousButton() {
                return null;
            }
        });
        lastPanel.add(minutesChooser);
        JLabel minutesLabel = new JLabel("p");
        lastPanel.add(minutesLabel);

        // seconds spinner and label
        SpinnerNumberModel secondsModel = new SpinnerNumberModel(inputComponents.getDurationSec(), 0, 59, 1);
        secondsChooser = new JSpinner(secondsModel);
        secondsChooser.setUI(new BasicSpinnerUI() {
            protected Component createNextButton() {
                return null;
            }

            protected Component createPreviousButton() {
                return null;
            }
        });
        ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setColumns(3);
        lastPanel.add(secondsChooser);
        JLabel secondsLabel = new JLabel("mp");
        lastPanel.add(secondsLabel);

        lastPanel.setPreferredSize(new Dimension(getWidth(), 280));
        lastPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return lastPanel;
    }

    private void initComponents() {
        components = new QuizComponents();

        // set up error label
        JLabel errorLabel = new JLabel("\n");
        errorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        errorLabel.setForeground(Color.RED);
        add(errorLabel, "span");

        // add panels
        add(initPronounPanel(), "span 1");
        add(initFormPanel(), "span 1");
        add(initLastPanel(), "wrap");
        add(initButtonPanel(), "span");

        // new quiz button
        JButton newQuizButton = new JButton("\u00DAj kv\u00EDz ind\u00EDt\u00E1sa");
        add(newQuizButton, "span, align center");

        newQuizButton.addActionListener(e -> {
            for (JCheckBox cb : pronounCheckBoxes)
                if (cb.isSelected()) components.addPronoun(cb.getText());

            for (JCheckBox cb : formCheckBoxes) {
                if (cb.isSelected()) {
                    switch (cb.getText()) {
                        case "Participio Presento" -> components.setParticipioPresentoSelected(true);
                        case "Participio Pasado" -> components.setParticipioPasadoSelected(true);
                        default -> components.addForm(cb.getText());
                    }
                }
            }
            components.setNumberOfVerbs((int) verbNumberChooser.getValue());

            // error handling
            // components.printStats();
            String error = "";
            if (!components.isParticipioPasadoSelected() && !components.isParticipioPresentoSelected()
                    && components.getSelectedForms().size() == 0)
                error = "Legal\u00E1bb egy igeid\u0151/m\u00F3d kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";
            else if (components.getSelectedForms().size() > 0 && components.getSelectedPronouns().size() == 0)
                error = "Legal\u00E1bb egy n\u00E9vm\u00E1s kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";

            if (error.equals("")) {
                try {
                    ConfigWriter cw = new ConfigWriter(components);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                VerbCollection vc = new VerbCollection(main, components);
                setVisible(false);
                current = new Quiz(vc);
                main.switchPanels(this, current);
            } else {
                errorLabel.setText(error);
                this.components = new QuizComponents();
                this.updateUI();
            }
        });
        main.getRootPane().setDefaultButton(newQuizButton);
    }

    private JPanel initButtonPanel() {
        JPanel buttonPanel = new JPanel(new MigLayout("align center center"));

        try {
            for (int i = 0; i < BUTTON_NUMBER; i++) {
                buttons.add(new MenuButton("preferences", i));
                buttons.get(i).setActionSetup(main, this, components);
                buttonPanel.add(buttons.get(i), "align center");
            }
        } catch (IOException e) {
            // TODO dialog
            throw new RuntimeException(e);
        }

        return buttonPanel;
    }

}