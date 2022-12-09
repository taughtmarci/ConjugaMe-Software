package controller;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import model.AppConfigurations;
import view.MainWindow;
import view.Settings;
import view.WordQuiz;

import javax.swing.*;
import java.io.IOException;

public class SettingsPreferences {
    private Settings settings;
    private AppConfigurations config;

    public SettingsPreferences(Settings settings) {
        this.settings = settings;
        this.config = new AppConfigurations();
    }

    public void setupConfig() throws UnsupportedLookAndFeelException {
        this.config = new AppConfigurations();

        // appearance
        config.setDarkMode(settings.getDarkModeRadio().isSelected());
        if (this.config.isDarkMode() != settings.getConfig().isDarkMode()) {
            if (this.config.isDarkMode()) UIManager.setLookAndFeel(new FlatDarkLaf());
            else UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.updateComponentTreeUI(settings.getMain());
        }
    }

    public void savePrefs() throws UnsupportedLookAndFeelException {
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

    public AppConfigurations getConfig() {
        return config;
    }
}
