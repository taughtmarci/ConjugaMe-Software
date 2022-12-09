package view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
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
        JPanel appSettingsPanel = new JPanel(new MigLayout("al center center"));

        // app settings title
        JLabel appSettingsTitle = new JLabel("Alkalmaz\u00E1s");
        appSettingsPanel.add(appSettingsTitle, "al center, span");

        // appearance
        JLabel appearanceTitle = new JLabel("Megjelen\u00E9s:");
        appSettingsPanel.add(appearanceTitle, "span");

        lightModeRadio = new JRadioButton("Vil\u00E1gos", !config.isDarkMode());
        darkModeRadio = new JRadioButton("S\u00F6t\u00E9t", config.isDarkMode());

        darkModeGroup = new ButtonGroup();
        darkModeGroup.add(lightModeRadio);
        darkModeGroup.add(darkModeRadio);

        appSettingsPanel.add(lightModeRadio, "span");
        appSettingsPanel.add(darkModeRadio, "span");

        appSettingsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return appSettingsPanel;
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

        add(initAppSettings(), "al center, span");
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

    public String getErrorLabel() {
        return errorLabel.getText();
    }

    public void setErrorLabel(String text) {
        this.errorLabel.setText(text);
    }

    public JRadioButton getDarkModeRadio() {
        return darkModeRadio;
    }
}
