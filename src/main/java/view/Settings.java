package view;

import controller.MenuButton;
import controller.SettingsPreferences;
import model.AppConfigurations;
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

    private JPanel initMainSettings() {
        // app settings panel and title
        JPanel appSettingsPanel = new JPanel(new MigLayout("al left center"));
        JLabel appSettingsTitle = new JLabel("Alkalmaz\u00E1s");
        appSettingsPanel.add(appSettingsTitle, "al center, span");

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

        // instant feedback
        instantFeedbackCheckBox = new JCheckBox("Visszajelz\u00E9s azonnal", config.isInstantFeedback());
        appSettingsPanel.add(instantFeedbackCheckBox, "al left, span");
        JTextArea instantFeedbackHint = new JTextArea("""
                Kv\u00EDzkit\u00F6lt\u00E9s k\u00F6zben kijelzi
                a helyes \u00E9s hib\u00E1s v\u00E1laszokat.""");
        instantFeedbackHint.setEnabled(false);
        appSettingsPanel.add(instantFeedbackHint, "al left, span");

        // quiz settings panel
        JPanel quizSettingsPanel = new JPanel(new MigLayout("al center center"));
        JLabel quizSettingsTitle = new JLabel("Kv\u00EDz");
        quizSettingsPanel.add(quizSettingsTitle, "al center center, span");

        // enye enabled
        enyeCheckBox = new JCheckBox("ny cser\u00E9je \u00F1-re", config.isEnyeEnabled());
        quizSettingsPanel.add(enyeCheckBox, "al left, span");
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

        appSettingsPanel.setPreferredSize(new Dimension(185, getHeight()));
        appSettingsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        quizSettingsPanel.setPreferredSize(new Dimension(185, getHeight()));
        quizSettingsPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel mainSettingsPanel = new JPanel(new MigLayout("al center center"));
        mainSettingsPanel.add(appSettingsPanel, "al center center, span");
        mainSettingsPanel.add(quizSettingsPanel, "al center center, span");
        return mainSettingsPanel;
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

        add(initMainSettings(), "al center, span");
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
