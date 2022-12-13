package view;

import controller.*;
import model.DialogType;
import model.Form;
import controller.MenuButton;
import model.Pronoun;
import model.VerbQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class VerbQuizSetup extends JPanel {
    private final int BUTTON_NUMBER = 3;
    private final SetupPane setupPane;

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

    private JLabel errorLabel;
    private final ArrayList<MenuButton> buttons;

    public VerbQuizSetup(SetupPane setupPane) throws IOException {
        this.setupPane = setupPane;
        this.comps = MainWindow.verbComps;
        this.prefs = new VerbQuizPreferences(this);
        this.buttons = new ArrayList<>();

        setLayout(new MigLayout("al center center"));
        SwingUtilities.invokeLater(this::initComponents);
        setVisible(true);
    }

    private JPanel initPronounPanel() {
        JPanel pronounPanel = new JPanel();
        pronounPanel.setLayout(new MigLayout("al center top"));

        // pronouns title
        JLabel pronounTitle = new JLabel("Szem\u00E9ly(ek):");
        pronounPanel.add(pronounTitle, "al center, span");

        // pronouns checkboxes
        pronounCheckBoxes = new ArrayList<>();
        for (Pronoun p : Pronoun.values()) {
            boolean inputFlag = comps.getSelectedPronouns().contains(p);
            pronounCheckBoxes.add(new JCheckBox(p.toString(), inputFlag));
            pronounPanel.add(pronounCheckBoxes.get(pronounCheckBoxes.size() - 1), "al left center, wrap");
        }

        pronounPanel.setPreferredSize(new Dimension(getWidth(), 280));
        pronounPanel.setBorder(BorderFactory.createLineBorder(MainWindow.config.getBorderColor(), 1));
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

        formCheckBoxes.add(new JCheckBox("Participio Presente", comps.isParticipioPresenteSelected()));
        formCheckBoxes.add(new JCheckBox("Participio Pasado", comps.isParticipioPasadoSelected()));
        participioPanel.add(formCheckBoxes.get(0), "wrap");
        participioPanel.add(formCheckBoxes.get(1), "wrap");

        for (Form f : Form.values()) {
            boolean inputFlag = comps.getSelectedForms().contains(f);
            formCheckBoxes.add(new JCheckBox(f.toString(), inputFlag));
            if (f == Form.ImperativoAffirmativo || f == Form.ImperativoNegativo)
                imperativoPanel.add(formCheckBoxes.get(formCheckBoxes.size() - 1), "wrap");
            else if (f == Form.SubjuntivoPresente || f == Form.SubjuntivoImperfecto || f == Form.SubjuntivoFuturo)
                subjuntivoPanel.add(formCheckBoxes.get(formCheckBoxes.size() - 1), "wrap");
            else indicativoPanel.add(formCheckBoxes.get(formCheckBoxes.size() - 1), "wrap");
        }

        // border, add panels to formPanel
        formPanel.add(participioPanel, "align left, cell 0 1");
        formPanel.add(indicativoPanel, "align left, cell 0 2");
        formPanel.add(imperativoPanel, "align left, cell 1 1");
        formPanel.add(subjuntivoPanel, "align left, cell 1 2");

        formPanel.setPreferredSize(new Dimension(getWidth(), 280));
        formPanel.setBorder(BorderFactory.createLineBorder(MainWindow.config.getBorderColor()));
        return formPanel;
    }

    private JPanel initModePanel() {
        JPanel lastPanel = new JPanel();
        lastPanel.setLayout(new MigLayout("al center top"));

        // group selector with title
        JLabel groupTitle = new JLabel("Csoport(ok):");
        lastPanel.add(groupTitle, "align center, span");
        groupList = prefs.getSelector();
        groupList.setSelectedRows(comps.getSelectedGroups());
        lastPanel.add(groupList, "align center, span");

        // normal mode radio and title
        normalModeRadio = new JRadioButton("Norm\u00E1l");
        lastPanel.add(normalModeRadio, "align left, span");

        // number of verbs title
        JLabel verbNumberTitle = new JLabel("Ig\u00E9k sz\u00E1ma:");
        lastPanel.add(verbNumberTitle, "span");

        // number of verbs spinner
        SpinnerNumberModel verbNumberModel = new SpinnerNumberModel(comps.getWordAmount(), 5, 500, 5);
        verbNumberChooser = new JSpinner(verbNumberModel);
        JFormattedTextField verbNumberField = (JFormattedTextField) verbNumberChooser.getEditor().getComponent(0);
        DefaultFormatter verbNumberFormatter = (DefaultFormatter) verbNumberField.getFormatter();
        verbNumberFormatter.setCommitsOnValidEdit(true);
        lastPanel.add(verbNumberChooser, "span");

        // timed mode radio and title
        timedModeRadio = new JRadioButton("Id\u0151z\u00EDtett");
        lastPanel.add(timedModeRadio, "align left, span");

        // timed duration title
        JLabel timedDurationTitle = new JLabel("Id\u0151tartam:");
        lastPanel.add(timedDurationTitle, "span");

        // minutes spinner and label
        SpinnerNumberModel minutesModel = new SpinnerNumberModel(comps.getDurationMin(), 1, 30, 1);
        minutesChooser = new JSpinner(minutesModel);
        minutesChooser.setUI(new BasicSpinnerUI() {
            protected Component createNextButton() {
                return null;
            }

            protected Component createPreviousButton() {
                return null;
            }
        });
        ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setColumns(3);
        JFormattedTextField minutesField = (JFormattedTextField) minutesChooser.getEditor().getComponent(0);
        DefaultFormatter minutesFormatter = (DefaultFormatter) minutesField.getFormatter();
        minutesFormatter.setCommitsOnValidEdit(true);
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
        JFormattedTextField secondsField = (JFormattedTextField) secondsChooser.getEditor().getComponent(0);
        DefaultFormatter secondsFormatter = (DefaultFormatter) secondsField.getFormatter();
        secondsFormatter.setCommitsOnValidEdit(true);
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
            verbNumberTitle.setForeground(MainWindow.config.getTextColor());
            ((JSpinner.DefaultEditor) verbNumberChooser.getEditor()).getTextField().setEnabled(true);
            ((JSpinner.DefaultEditor) verbNumberChooser.getEditor()).getTextField().setEditable(true);

            // other
            timedDurationTitle.setForeground(Color.GRAY);
            minutesLabel.setForeground(Color.GRAY);
            secondsLabel.setForeground(Color.GRAY);
            ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setEditable(false);
            ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setEditable(false);
        }));

        timedModeRadio.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            // own
            timedDurationTitle.setForeground(MainWindow.config.getTextColor());
            minutesLabel.setForeground(MainWindow.config.getTextColor());
            secondsLabel.setForeground(MainWindow.config.getTextColor());
            ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setEnabled(true);
            ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setEditable(true);
            ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setEnabled(true);
            ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setEditable(true);

            // other
            verbNumberTitle.setForeground(Color.GRAY);
            ((JSpinner.DefaultEditor) verbNumberChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) verbNumberChooser.getEditor()).getTextField().setEditable(false);
        }));

        // select one of the radio buttons
        if (comps.isNormal()) {
            normalModeRadio.setSelected(true);
            timedDurationTitle.setForeground(Color.GRAY);
            minutesLabel.setForeground(Color.GRAY);
            secondsLabel.setForeground(Color.GRAY);
            ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) minutesChooser.getEditor()).getTextField().setEditable(false);
            ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) secondsChooser.getEditor()).getTextField().setEditable(false);
        }
        else {
            timedModeRadio.setSelected(true);
            verbNumberTitle.setForeground(Color.GRAY);
            ((JSpinner.DefaultEditor) verbNumberChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) verbNumberChooser.getEditor()).getTextField().setEditable(false);
        }

        lastPanel.setPreferredSize(new Dimension(getWidth(), 280));
        lastPanel.setBorder(BorderFactory.createLineBorder(MainWindow.config.getBorderColor()));
        return lastPanel;
    }

    private void initComponents() {
        // set up error label
        errorLabel = new JLabel("\n");
        errorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        add(errorLabel, "span");

        // add panels
        JPanel prefPanels = new JPanel(new MigLayout());
        prefPanels.add(initPronounPanel());
        prefPanels.add(initFormPanel());
        prefPanels.add(initModePanel());
        add(prefPanels, "al center center, wrap");
        add(initButtonPanel(), "al center center, span");
    }

    public void writeOutErrors(String error) {
        if (error.equals("Sikeres ment\u00E9s!")) errorLabel.setForeground(Color.GREEN.darker());
        else errorLabel.setForeground(Color.RED.darker());
        setErrorLabel(error);
    }

    private JPanel initButtonPanel() {
        JPanel buttonPanel = new JPanel(new MigLayout("align center center"));

        try {
            for (int i = 0; i < BUTTON_NUMBER; i++) {
                buttons.add(new MenuButton("verbprefs", i + 1));
                buttons.get(i).setActionSetup(setupPane.getMain(), this.getSetupPane(), comps);
                buttonPanel.add(buttons.get(i), "align center");
            }
        } catch (IOException e) {
            MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                    "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra, vagy ha a probl\u00E9ma kor\u00E1bbr\u00F3l is fenn\u00E1ll, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
        }

        return buttonPanel;
    }

    private void setErrorLabel(String text) {
        errorLabel.setText(text);
    }

    public SetupPane getSetupPane() {
        return setupPane;
    }

    public VerbQuizPreferences getPrefs() {
        return prefs;
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

    public ButtonGroup getVerbMode() {
        return verbMode;
    }

    public JRadioButton getNormalModeRadio() {
        return normalModeRadio;
    }

    public JRadioButton getTimedModeRadio() {
        return timedModeRadio;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

}