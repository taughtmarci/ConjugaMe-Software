package controller;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import model.AppConfigurations;
import view.Dashboard;
import view.MainWindow;
import view.Settings;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class SettingsPreferences {
    private final Settings settings;
    private AppConfigurations config;

    public SettingsPreferences(Settings settings) {
        this.settings = settings;
        this.config = new AppConfigurations();

        configureKeyPresses();
    }

    public void setupConfig() throws UnsupportedLookAndFeelException {
        float tempAppVersion = MainWindow.config.getAppVersion();
        float tempDatabaseVersion = MainWindow.config.getDatabaseVersion();
        this.config = new AppConfigurations();

        // version number
        config.setAppVersion(tempAppVersion);
        config.setDatabaseVersion(tempDatabaseVersion);

        // appearance
        config.setDarkMode(settings.getDarkModeRadio().isSelected());
        if (this.config.isDarkMode()) {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            for (MenuButton button : settings.getButtons())
                button.refreshDarkMode(settings.getDarkModeRadio().isSelected());
        }
        else {
            UIManager.setLookAndFeel(new FlatLightLaf());
            for (MenuButton button : settings.getButtons())
                button.refreshDarkMode(settings.getDarkModeRadio().isSelected());
        }
        SwingUtilities.updateComponentTreeUI(settings.getMain());

        // offline mode
        config.setOfflineMode(settings.getOfflineModeCheckBox().isSelected());

        // instant feedback
        config.setInstantFeedback(settings.getInstantFeedbackCheckBox().isSelected());

        // enye enabled
        config.setEnyeEnabled(settings.getEnyeCheckBox().isSelected());

        // enter as tab
        config.setEnterAsTab(!settings.getEnterRadio().isSelected());
    }

    public void savePrefs() throws UnsupportedLookAndFeelException, IOException {
        setupConfig();
        String error = "";
        try {
            ConfigIO.writeAppConfigurations("config/appconfigurations.cfg", getConfig());
            MainWindow.config = getConfig();
            error = "Sikeres ment\u00E9s!";
        } catch (IOException e) {
            error = "Nem sikerült menteni a beállításokat.";
        }
        settings.writeOutErrors(error);
    }

    private void configureKeyPresses() {
        this.settings.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_ENTER) {
                    try {
                        savePrefs();
                    } catch (UnsupportedLookAndFeelException | IOException ex) {
                        // todo dialogize
                        throw new RuntimeException(ex);
                    }
                }

                if (key == KeyEvent.VK_BACK_SPACE) {
                    try {
                        JPanel next = new Dashboard(settings.getMain());
                        settings.getMain().switchPanels(settings, next);
                    } catch (IOException ex) {
                        // todo dialogize
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    public AppConfigurations getConfig() {
        return config;
    }
}
