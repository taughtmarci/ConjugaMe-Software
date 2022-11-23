package controller;

import model.Group;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class GroupHandler {
    private ArrayList<Group> verifiedGroups;
    private ConfigIO config;

    public GroupHandler() throws IOException {
        this.config = new ConfigIO();
        this.verifiedGroups = config.readVerifiedGroups("config/groups.cfg");
        if (!inputVerified())
            throw new IOException("Invalid group storage file");
    }

    private boolean inputVerified() {
        return true;
    }

    public ArrayList<String> getGroupNames() {
        ArrayList<String> result = new ArrayList<>();
        for (Group group : verifiedGroups)
            result.add(group.name());
        return result;
    }

    public Group getGroupByName(String name) {
        Group result = new Group(-1, "undefined");
        for (Group group : verifiedGroups)
            if (group.name().equals(name)) result = group;
        return result;
    }
}
