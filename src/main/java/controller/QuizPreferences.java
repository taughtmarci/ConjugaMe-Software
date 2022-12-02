package controller;

import model.QuizComponents;

import java.io.IOException;

abstract class QuizPreferences {
    protected final ConfigIO config;

    protected final GroupHandler handler;
    protected final GroupSelector selector;

    public QuizPreferences() throws IOException {
        this.config = new ConfigIO();
        this.handler = new GroupHandler();
        this.selector = new GroupSelector(handler);
    }

    public ConfigIO getConfig() {
        return config;
    }

    public GroupHandler getHandler() {
        return handler;
    }

    public GroupSelector getSelector() {
        return selector;
    }
}
