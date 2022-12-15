package controller;

import view.MainWindow;

public class DialogCommands {
    public static class ExitCommand implements Runnable {
        @Override
        public void run() {
            System.exit(0);
        }
    }

    public static class DoNothingCommand implements Runnable {
        @Override
        public void run() {}
    }

    public static class ResetScoresCommand implements Runnable {
        @Override
        public void run() {
            MainWindow.local.resetScores();
            ConfigIO.resetFile("config/badges.cfg");
        }
    }

    public static class ResetVerbLevelsCommand implements Runnable {
        @Override
        public void run() {
            MainWindow.local.resetLevels("Verbo");
        }
    }

    public static class ResetNounLevelsCommand implements Runnable {
        @Override
        public void run() {
            MainWindow.local.resetLevels("Palabra");
        }
    }
}