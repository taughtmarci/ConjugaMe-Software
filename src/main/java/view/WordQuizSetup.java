package view;

import controller.GroupSelector;
import controller.MenuButton;
import controller.WordQuizPreferences;
import model.DialogType;
import model.Difficulty;
import model.WordQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class WordQuizSetup extends JPanel {
    private final int BUTTON_NUMBER = 3;
    private final SetupPane setupPane;

    private WordQuizComponents comps;
    private final WordQuizPreferences prefs;

    private ButtonGroup difficultyMode;
    private ArrayList<JRadioButton> difficultyRadioButtons;
    private JCheckBox articlesCheckBox;

    private JSpinner wordNumberChooser;
    private JSpinner minutesChooser;
    private JSpinner secondsChooser;
    private GroupSelector groupList;

    private ButtonGroup wordMode;
    private JRadioButton normalModeRadio;
    private JRadioButton timedModeRadio;

    private JLabel errorLabel;
    private final ArrayList<MenuButton> buttons;

    public WordQuizSetup(SetupPane setupPane) throws IOException {
        this.setupPane = setupPane;
        this.comps = MainWindow.wordComps;
        this.prefs = new WordQuizPreferences(this);
        this.buttons = new ArrayList<>();

        setLayout(new MigLayout("al center center"));
        SwingUtilities.invokeLater(this::initComponents);
        setVisible(true);
    }

    private JPanel initDifficultyPanel() {
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new MigLayout("al center top"));

        // difficulty title
        JLabel difficultyTitle = new JLabel("Neh\u00E9zs\u00E9gi szint:");
        difficultyPanel.add(difficultyTitle, "al center, span");

        // difficulty radio buttons
        difficultyMode = new ButtonGroup();
        difficultyRadioButtons = new ArrayList<>();
        for (Difficulty d : Difficulty.values()) {
            difficultyRadioButtons.add(new JRadioButton(d.toString(), comps.getDifficulty().equalsName(d.toString())));
            difficultyMode.add(difficultyRadioButtons.get(difficultyRadioButtons.size() - 1));
            difficultyPanel.add(difficultyRadioButtons.get(difficultyRadioButtons.size() - 1), "al left center, wrap");
        }
        JLabel spanTitle = new JLabel(" ");
        difficultyPanel.add(spanTitle, "span");

        // articles needed title
        JLabel articlesTitle = new JLabel("N\u00E9vel\u0151k:");
        difficultyPanel.add(articlesTitle, "al left, span");

        // articles needed checkbox
        articlesCheckBox = new JCheckBox("Sz\u00FCks\u00E9gesek", comps.isArticlesNeeded());
        difficultyPanel.add(articlesCheckBox);

        difficultyPanel.setPreferredSize(new Dimension(getWidth(), 280));
        difficultyPanel.setBorder(BorderFactory.createLineBorder(MainWindow.config.getBorderColor(), 1));
        return difficultyPanel;
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

        // number of words title
        JLabel wordNumberTitle = new JLabel("Szavak sz\u00E1ma:");
        lastPanel.add(wordNumberTitle, "span");

        // number of words spinner
        SpinnerNumberModel wordNumberModel = new SpinnerNumberModel(comps.getWordAmount(), 1, 250, 5);
        wordNumberChooser = new JSpinner(wordNumberModel);
        JFormattedTextField wordNumberField = (JFormattedTextField) wordNumberChooser.getEditor().getComponent(0);
        DefaultFormatter wordNumberFormatter = (DefaultFormatter) wordNumberField.getFormatter();
        wordNumberFormatter.setCommitsOnValidEdit(true);
        lastPanel.add(wordNumberChooser, "span");

        // timed mode radio and title
        timedModeRadio = new JRadioButton("Id\u0151z\u00EDtett");
        lastPanel.add(timedModeRadio, "align left, span");

        // timed duration title
        JLabel timedDurationTitle = new JLabel("Id\u0151tartam:");
        lastPanel.add(timedDurationTitle, "span");

        // minutes spinner and label
        SpinnerNumberModel minutesModel = new SpinnerNumberModel(comps.getDurationMin(), 1, 5, 1);
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
        wordMode = new ButtonGroup();
        wordMode.add(normalModeRadio);
        wordMode.add(timedModeRadio);

        // action listener to radios
        normalModeRadio.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            // own
            wordNumberTitle.setForeground(MainWindow.config.getTextColor());
            ((JSpinner.DefaultEditor) wordNumberChooser.getEditor()).getTextField().setEnabled(true);
            ((JSpinner.DefaultEditor) wordNumberChooser.getEditor()).getTextField().setEditable(true);

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
            wordNumberTitle.setForeground(Color.GRAY);
            ((JSpinner.DefaultEditor) wordNumberChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) wordNumberChooser.getEditor()).getTextField().setEditable(false);
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
            wordNumberTitle.setForeground(Color.GRAY);
            ((JSpinner.DefaultEditor) wordNumberChooser.getEditor()).getTextField().setEnabled(false);
            ((JSpinner.DefaultEditor) wordNumberChooser.getEditor()).getTextField().setEditable(false);
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
        prefPanels.add(initDifficultyPanel());
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
        JPanel buttonPanel = new JPanel(new MigLayout());

        try {
            for (int i = 0; i < BUTTON_NUMBER; i++) {
                buttons.add(new MenuButton("wordprefs", i + 1));
                buttons.get(i).setActionSetup(setupPane.getMain(), this.getSetupPane(), comps);
                buttonPanel.add(buttons.get(i));
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

    public WordQuizComponents getComps() {
        return comps;
    }

    public WordQuizPreferences getPrefs() {
        return prefs;
    }

    public ArrayList<JRadioButton> getDifficultyRadioButtons() {
        return difficultyRadioButtons;
    }

    public JCheckBox getArticlesCheckBox() {
        return articlesCheckBox;
    }

    public JSpinner getWordNumberChooser() {
        return wordNumberChooser;
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

    public ButtonGroup getWordMode() {
        return wordMode;
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

    public ArrayList<MenuButton> getButtons() {
        return buttons;
    }
}
