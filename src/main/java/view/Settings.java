package view;

import controller.DialogCommands;
import controller.MenuButton;
import controller.SettingsPreferences;
import model.AppConfigurations;
import model.DialogType;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Settings extends JPanel {
    private final int BUTTON_NUMBER = 2;

    private final MainWindow main;
    private AppConfigurations config;
    private SettingsPreferences prefs;

    private ButtonGroup darkModeGroup;
    private JRadioButton lightModeRadio;
    private JRadioButton darkModeRadio;
    private JCheckBox offlineModeCheckBox;
    private JCheckBox instantFeedbackCheckBox;

    private ButtonGroup enterAsTabGroup;
    private JRadioButton enterRadio;
    private JRadioButton tabRadio;
    private JCheckBox enyeCheckBox;

    private JLabel errorLabel;
    private final ArrayList<MenuButton> buttons;

    public Settings(MainWindow main) {
        this.main = main;
        this.config = MainWindow.config;
        this.prefs = new SettingsPreferences(this);
        this.buttons = new ArrayList<>();

        setLayout(new MigLayout("al center center"));
        SwingUtilities.invokeLater(this::initComponents);
        setVisible(true);
    }

    private JPanel initAppSettings() {
        // app settings panel and title
        JPanel appSettingsPanel = new JPanel(new MigLayout("al left"));
        JLabel appSettingsTitle = new JLabel("Alkalmaz\u00E1s");
        appSettingsPanel.add(appSettingsTitle, "al center top, span");

        // appearance
        JLabel appearanceTitle = new JLabel("Megjelen\u00E9s:");
        appSettingsPanel.add(appearanceTitle, "al left, span");

        lightModeRadio = new JRadioButton("Vil\u00E1gos m\u00F3d", !config.isDarkMode());
        darkModeRadio = new JRadioButton("S\u00F6t\u00E9t m\u00F3d", config.isDarkMode());

        darkModeGroup = new ButtonGroup();
        darkModeGroup.add(lightModeRadio);
        darkModeGroup.add(darkModeRadio);

        appSettingsPanel.add(lightModeRadio, "al left, span");
        appSettingsPanel.add(darkModeRadio, "al left, span");
        appSettingsPanel.add(new JLabel(" "), "span");

        // offline mode
        offlineModeCheckBox = new JCheckBox("Offline m\u00F3d", config.isOfflineMode());
        appSettingsPanel.add(offlineModeCheckBox, "al left, span");

        JTextArea offlineModeHint = new JTextArea("""
                Bekapcsolva nem fog \u00FAjabb
                sz\u00F3t\u00E1rakat keresni \u00E9s let\u00F6lteni.""");
        offlineModeHint.setEnabled(false);
        appSettingsPanel.add(offlineModeHint, "al left, span");
        appSettingsPanel.add(new JLabel(" "), "span");

        // reset buttons
        JLabel resetLabel = new JLabel("Alaphelyzetbe \u00E1ll\u00EDt\u00E1s:");
        appSettingsPanel.add(resetLabel, "al left, span");

        JButton scoresReset = new JButton("Eredm\u00E9nyek");
        scoresReset.addActionListener(e-> {
            String errorMessage = """
                        Az eredm\u00E9nyek vissza\u00E1ll\u00EDt\u00E1sa eset\u00E9n az eddigi \u00F6sszes eredm\u00E9nyed elv\u00E9sz.
                        Biztos vagy benne?
                        """;
            String errorTitle = "Meger\u0151s\u00Edt\u00E9s";
            MainWindow.dialog.showYesNoDialog(errorTitle, errorMessage, DialogType.WARNING, new DialogCommands.ResetScoresCommand(), new DialogCommands.DoNothingCommand());
        });
        appSettingsPanel.add(scoresReset, "al center, span");

        JButton verbLevelReset = new JButton("Hib\u00E1s ig\u00E9k");
        verbLevelReset.addActionListener(e-> {
            String errorMessage = """
                        Ezzel a teljes \u00E1tn\u00E9z\u00E9sre v\u00E1r\u00F3 ig\u00E9id list\u00E1ja elv\u00E9sz.
                        Biztos vagy benne?
                        """;
            String errorTitle = "Meger\u0151s\u00Edt\u00E9s";
            MainWindow.dialog.showYesNoDialog(errorTitle, errorMessage, DialogType.WARNING, new DialogCommands.ResetVerbLevelsCommand(), new DialogCommands.DoNothingCommand());
        });
        appSettingsPanel.add(verbLevelReset, "al center, span");

        JButton nounLevelReset = new JButton("Hib\u00E1s ford\u00EDt\u00E1sok");
        nounLevelReset.addActionListener(e-> {
            String errorMessage = """
                        Ezzel a teljes \u00E1tn\u00E9z\u00E9sre v\u00E1r\u00F3 ford\u00EDt\u00E1said list\u00E1ja elv\u00E9sz.
                        Biztos vagy benne?
                        """;
            String errorTitle = "Meger\u0151s\u00Edt\u00E9s";
            MainWindow.dialog.showYesNoDialog(errorTitle, errorMessage, DialogType.WARNING, new DialogCommands.ResetNounLevelsCommand(), new DialogCommands.DoNothingCommand());
        });
        appSettingsPanel.add(nounLevelReset, "al center, span");

        appSettingsPanel.setPreferredSize(new Dimension(185, 320));
        appSettingsPanel.setBorder(BorderFactory.createLineBorder(MainWindow.config.getBorderColor()));
        return appSettingsPanel;
    }

    private JPanel initQuizSettings() {
        // quiz settings panel and title
        JPanel quizSettingsPanel = new JPanel(new MigLayout("al left"));
        JLabel quizSettingsTitle = new JLabel("Kv\u00EDz");
        quizSettingsPanel.add(quizSettingsTitle, "al center top, span");

        // enye enabled
        enyeCheckBox = new JCheckBox("ny cser\u00E9je \u00F1-re", config.isEnyeEnabled());
        quizSettingsPanel.add(enyeCheckBox, "al left, span");

        JTextArea enyeHint = new JTextArea("""
                Bekapcsolt \u00E1llapotban az
                \u00F6sszes beg\u00E9pelt "ny" bet\u0171p\u00E1r
                automatikusan \u00E1talakul \u00F1-re.""");
        enyeHint.setEnabled(false);
        quizSettingsPanel.add(enyeHint, "al left, span");
        quizSettingsPanel.add(new JLabel(" "), "span");

        // instant feedback
        instantFeedbackCheckBox = new JCheckBox("Visszajelz\u00E9s azonnal", config.isInstantFeedback());
        quizSettingsPanel.add(instantFeedbackCheckBox, "al left, span");

        JTextArea instantFeedbackHint = new JTextArea("""
                Kv\u00EDzkit\u00F6lt\u00E9s k\u00F6zben kijelzi
                a helyes \u00E9s hib\u00E1s v\u00E1laszokat.""");
        instantFeedbackHint.setEnabled(false);
        quizSettingsPanel.add(instantFeedbackHint, "al left, span");
        quizSettingsPanel.add(new JLabel(" "), "span");

        // enter as tab
        JLabel enterAsTabTitle = new JLabel("Enter billenty\u0171 megnyom\u00E1sakor:");
        quizSettingsPanel.add(enterAsTabTitle, "al left, span");

        tabRadio = new JRadioButton("K\u00F6vetkez\u0151 mez\u0151 (tab)", config.isEnterAsTab());
        enterRadio = new JRadioButton("K\u00FCld\u00E9s gomb (enter)", !config.isEnterAsTab());

        enterAsTabGroup = new ButtonGroup();
        enterAsTabGroup.add(tabRadio);
        enterAsTabGroup.add(enterRadio);

        quizSettingsPanel.add(tabRadio, "al left, span");
        quizSettingsPanel.add(enterRadio, "al left, span");

        JTextArea enterAsTabHint = new JTextArea("""
                Fel\u00FCl\u00EDrja az enter m\u0171k\u00F6d\u00E9s\u00E9t.""");
        enterAsTabHint.setEnabled(false);
        quizSettingsPanel.add(enterAsTabHint, "al left, span");

        quizSettingsPanel.setPreferredSize(new Dimension(185, 320));
        quizSettingsPanel.setBorder(BorderFactory.createLineBorder(MainWindow.config.getBorderColor()));
        return quizSettingsPanel;
    }

    private void initComponents() {
        // set up main title
        JLabel settingsTitle = new JLabel("Be\u00E1ll\u00EDt\u00E1sok");
        settingsTitle.setFont(new Font("Verdana", Font.BOLD, 24));
        add(settingsTitle, "al center, span");

        // set up error label
        errorLabel = new JLabel("\n");
        errorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        add(errorLabel, "span");

        add(initAppSettings(), "al center center");
        add(initQuizSettings(), "al center center, span");
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
                buttons.add(new MenuButton("settings", i + 1));
                buttons.get(i).setActionSettings(main, this);
                buttonPanel.add(buttons.get(i), "align center");
            }
        } catch (IOException e) {
            // todo dialog
            throw new RuntimeException(e);
        }

        return buttonPanel;
    }

    public MainWindow getMain() {
        return main;
    }

    public AppConfigurations getConfig() {
        return config;
    }

    public SettingsPreferences getPrefs() {
        return prefs;
    }

    public ArrayList<MenuButton> getButtons() {
        return buttons;
    }

    public String getErrorLabel() {
        return errorLabel.getText();
    }

    public void setErrorLabel(String text) {
        this.errorLabel.setText(text);
    }

    public JRadioButton getDarkModeRadio() {
        return darkModeRadio;
    }

    public JCheckBox getOfflineModeCheckBox() {
        return offlineModeCheckBox;
    }

    public JCheckBox getInstantFeedbackCheckBox() {
        return instantFeedbackCheckBox;
    }

    public JCheckBox getEnyeCheckBox() {
        return enyeCheckBox;
    }

    public JRadioButton getEnterRadio() {
        return enterRadio;
    }
}
