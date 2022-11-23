package view;

import controller.*;
import model.Form;
import model.MenuButton;
import model.Pronoun;
import model.VerbQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class VerbQuizSetup extends JPanel {
    private final int BUTTON_NUMBER = 4;

    private final MainWindow main;
    private VerbQuiz current;

    private VerbQuizComponents comps;
    private final VerbQuizPreferences prefs;

    private ArrayList<JCheckBox> pronounCheckBoxes;
    private ArrayList<JCheckBox> formCheckBoxes;
    private JSpinner verbNumberChooser;
    private JSpinner minutesChooser;
    private JSpinner secondsChooser;
    private GroupSelector groupList;

    private ButtonGroup verbMode;
    private JRadioButton normalModeRadio;
    private JRadioButton timedModeRadio;

    private final ArrayList<MenuButton> buttons;

    public VerbQuizSetup(MainWindow main, VerbQuizComponents comps) throws IOException {
        this.main = main;
        this.comps = comps;
        this.buttons = new ArrayList<>();
        this.prefs = new VerbQuizPreferences(this);

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
        pronounCheckBoxes = new ArrayList<>();
        for (Pronoun p : Pronoun.values()) {
            boolean inputFlag = comps.getSelectedPronouns().contains(p);
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

        formCheckBoxes.add(new JCheckBox("Participio Presento", comps.isParticipioPresentoSelected()));
        formCheckBoxes.add(new JCheckBox("Participio Pasado", comps.isParticipioPasadoSelected()));
        participioPanel.add(formCheckBoxes.get(0), "wrap");
        participioPanel.add(formCheckBoxes.get(1), "wrap");

        for (Form f : Form.values()) {
            boolean inputFlag = comps.getSelectedForms().contains(f);
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

    private JPanel initModePanel() {
        JPanel lastPanel = new JPanel();
        lastPanel.setLayout(new MigLayout("al center center"));

        // group selector with title
        JLabel groupTitle = new JLabel("Igecsoport(ok):");
        lastPanel.add(groupTitle, "align center, span");
        groupList = prefs.getSelector();
        groupList.setSelectedRows(comps.getSelectedGroups());
        lastPanel.add(groupList, "align center, span");

        // normal mode radio and title
        normalModeRadio = new JRadioButton();
        lastPanel.add(normalModeRadio, "align right");
        JLabel normalModeTitle = new JLabel("Norm\u00E1l");
        lastPanel.add(normalModeTitle, "span");

        // number of verbs title
        JLabel verbNumberTitle = new JLabel("Ig\u00E9k sz\u00E1ma:");
        lastPanel.add(verbNumberTitle, "span");

        // number of verbs spinner
        SpinnerNumberModel verbNumberModel = new SpinnerNumberModel(comps.getNumberOfVerbs(), 5, 500, 5);
        verbNumberChooser = new JSpinner(verbNumberModel);
        lastPanel.add(verbNumberChooser, "span");

        // timed mode radio and title
        timedModeRadio = new JRadioButton();
        lastPanel.add(timedModeRadio, "align right");
        JLabel timedModeTitle = new JLabel("Id\u0151z\u00EDtett");
        lastPanel.add(timedModeTitle, "span");

        // timed duration title
        JLabel timedDurationTitle = new JLabel("Id\u0151tartam:");
        lastPanel.add(timedDurationTitle, "span");

        /* placeholder for
        timingDurationSubtitle.setFont(new Font("Verdana", Font.ITALIC, 10));
        timingDurationSubtitle.setForeground(Color.GRAY);
        */

        // minutes spinner and label
        SpinnerNumberModel minutesModel = new SpinnerNumberModel(comps.getDurationMin(), 1, 180, 1);
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
        SpinnerNumberModel secondsModel = new SpinnerNumberModel(comps.getDurationSec(), 0, 59, 1);
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

        // button group for radios
        verbMode = new ButtonGroup();
        verbMode.add(normalModeRadio);
        verbMode.add(timedModeRadio);

        // action listener to radios
        normalModeRadio.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            // own
            normalModeTitle.setForeground(Color.BLACK);
            ((JSpinner.DefaultEditor) verbNumberChooser.getEditor()).getTextField().setEnabled(true);
            ((JSpinner.DefaultEditor) verbNumberChooser.getEditor()).getTextField().setEditable(true);

            // other
            timedModeTitle.setForeground(Color.GRAY);
            minutesLabel.setForeground(Color.GRAY);
            secondsLabel.setForeground(Color.GRAY);
            ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setEditable(false);
            ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setEditable(false);
        }));

        timedModeRadio.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            // own
            timedModeTitle.setForeground(Color.BLACK);
            minutesLabel.setForeground(Color.BLACK);
            secondsLabel.setForeground(Color.BLACK);
            ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setEnabled(true);
            ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setEditable(true);
            ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setEnabled(true);
            ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setEditable(true);

            // other
            normalModeTitle.setForeground(Color.GRAY);
            ((JSpinner.DefaultEditor) verbNumberChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) verbNumberChooser.getEditor()).getTextField().setEditable(false);
        }));

        lastPanel.setPreferredSize(new Dimension(getWidth(), 280));
        lastPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return lastPanel;
    }

    private void initComponents() {
        // set up error label
        JLabel errorLabel = new JLabel("\n");
        errorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        errorLabel.setForeground(Color.RED);
        add(errorLabel, "span");

        // add panels
        add(initPronounPanel(), "span 1");
        add(initFormPanel(), "span 1");
        add(initModePanel(), "wrap");
        add(initButtonPanel(), "span");

        // new quiz button
        JButton newQuizButton = new JButton("\u00DAj kv\u00EDz ind\u00EDt\u00E1sa");
        add(newQuizButton, "span, align center");

        newQuizButton.addActionListener(e -> {
            prefs.setupComps();
            String error = prefs.validateForm();

            if (error.equals("")) {
                try {
                    prefs.getConfig().writeComponents("config/preferences.cfg", prefs.getComps());
                } catch (IOException ex) {
                    // todo panel
                    throw new RuntimeException(ex);
                }
                VerbCollection vc = new VerbCollection(main, comps);
                setVisible(false);
                current = new VerbQuiz(vc);
                main.switchPanels(this, current);
            } else {
                errorLabel.setText(error);
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
                buttons.get(i).setActionSetup(main, this, comps);
                buttonPanel.add(buttons.get(i), "align center");
            }
        } catch (IOException e) {
            // TODO dialog
            throw new RuntimeException(e);
        }

        return buttonPanel;
    }

    public ArrayList<JCheckBox> getPronounCheckBoxes() {
        return pronounCheckBoxes;
    }

    public ArrayList<JCheckBox> getFormCheckBoxes() {
        return formCheckBoxes;
    }

    public JSpinner getVerbNumberChooser() {
        return verbNumberChooser;
    }

    public JSpinner getMinutesChooser() {
        return minutesChooser;
    }

    public JSpinner getSecondsChooser() {
        return secondsChooser;
    }

    public GroupSelector getGroupList() {
        return groupList;
    }
}