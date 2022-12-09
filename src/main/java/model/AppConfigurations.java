package model;

public class AppConfigurations {
    public boolean isDarkMode;
    public boolean isOfflineMode;
    public boolean isInstantFeedback;
    public boolean isEnterAsTab;
    public boolean isEnyeEnabled;

    public AppConfigurations() {
        this.isDarkMode = false;
        this.isOfflineMode = false;
        this.isInstantFeedback = false;
        this.isEnterAsTab = false;
        this.isEnyeEnabled = false;
    }

    public boolean isDarkMode() {
        return isDarkMode;
    }

    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
    }

    public boolean isOfflineMode() {
        return isOfflineMode;
    }

    public void setOfflineMode(boolean offlineMode) {
        isOfflineMode = offlineMode;
    }

    public boolean isInstantFeedback() {
        return isInstantFeedback;
    }

    public void setInstantFeedback(boolean instantFeedback) {
        isInstantFeedback = instantFeedback;
    }

    public boolean isEnterAsTab() {
        return isEnterAsTab;
    }

    public void setEnterAsTab(boolean enterAsTab) {
        isEnterAsTab = enterAsTab;
    }

    public boolean isEnyeEnabled() {
        return isEnyeEnabled;
    }

    public void setEnyeEnabled(boolean enyeEnabled) {
        isEnyeEnabled = enyeEnabled;
    }
}
