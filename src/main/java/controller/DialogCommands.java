package controller;

public class DialogCommands {
    public static class ExitCommand implements Runnable {
        @Override
        public void run() {
            System.exit(0);
        }
    }

    public static class DoNothingCommand implements Runnable {
        @Override
        public void run() {
            System.out.println("did nothing :D");
        }
    }
}