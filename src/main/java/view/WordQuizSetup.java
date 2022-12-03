package view;

import controller.GroupSelector;
import controller.MenuButton;
import controller.WordQuizPreferences;
import model.Difficulty;
import model.WordQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSpinnerUI;
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

        difficultyPanel.setPreferredSize(new Dimension(getWidth(), 280));
        difficultyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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

        // number of verbs title
        JLabel wordNumberTitle = new JLabel("Szavak sz\u00E1ma:");
        lastPanel.add(wordNumberTitle, "span");

        // number of verbs spinner
        SpinnerNumberModel verbNumberModel = new SpinnerNumberModel(comps.getWordAmount(), 5, 500, 5);
        wordNumberChooser = new JSpinner(verbNumberModel);
        lastPanel.add(wordNumberChooser, "span");

        // timed mode radio and title
        timedModeRadio = new JRadioButton("Id\u0151z\u00EDtett");
        lastPanel.add(timedModeRadio, "align left, span");

        // timed duration title
        JLabel timedDurationTitle = new JLabel("Id\u0151tartam:");
        lastPanel.add(timedDurationTitle, "span");

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
        wordMode = new ButtonGroup();
        wordMode.add(normalModeRadio);
        wordMode.add(timedModeRadio);

        // action listener to radios
        normalModeRadio.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            // own
            wordNumberTitle.setForeground(Color.BLACK);
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
            timedDurationTitle.setForeground(Color.BLACK);
            minutesLabel.setForeground(Color.BLACK);
            secondsLabel.setForeground(Color.BLACK);
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
        lastPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return lastPanel;
    }

    private void initComponents() {
        // set up error label
        errorLabel = new JLabel("\n");
        errorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        add(errorLabel, "span");

        // add panels
        add(initDifficultyPanel(), "span 1");
        add(initModePanel(), "span");
        add(initButtonPanel(), "align center, span");
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
                buttons.add(new MenuButton("preferences", i + 1));
                buttons.get(i).setActionSetup(setupPane.getMain(), this.getSetupPane(), comps);
                buttonPanel.add(buttons.get(i), "align center");
            }
        } catch (IOException e) {
            // todo dialog
            throw new RuntimeException(e);
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

    public ArrayList<JRadioButton> getDifficultyRadioButtons() {
        return difficultyRadioButtons;
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
