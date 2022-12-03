package controller;

import model.Group;

import java.io.IOException;
import java.util.ArrayList;

public class GroupHandler {
    private final ArrayList<Group> groups;
    private ArrayList<Group> defaultVerified;

    public GroupHandler() throws IOException {
        this.groups = ConfigIO.readVerifiedGroups("config/groups.cfg");
        this.defaultVerified = new ArrayList<>();
        setupDefaultVerified();

        if (!inputVerified())
            throw new IOException("Invalid group storage file");
    }

    public void setupDefaultVerified() {
        this.defaultVerified.add(new Group(0, "Conejito"));
        this.defaultVerified.add(new Group(1, "Principiante"));
        this.defaultVerified.add(new Group(2, "Intermedio"));
        this.defaultVerified.add(new Group(3, "Avanzado"));
        this.defaultVerified.add(new Group(4, "Experto"));
    }

    public boolean groupValidated(Group group) {
        return groups.contains(group);
    }

    private boolean inputVerified() {
        for (int i = 0; i < 5; i++)
            if (!groups.get(i).equals(defaultVerified.get(i))) return false;
        return true;
    }

    public ArrayList<String> getGroupNames() {
        ArrayList<String> result = new ArrayList<>();
        for (Group group : groups)
            result.add(group.name());
        return result;
    }

    public Group getGroupByName(String name) {
        Group result = new Group(-1, "undefined");
        for (Group group : groups)
            if (group.name().equals(name)) result = group;
        return result;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public ArrayList<Group> getDefaultVerified() {
        return defaultVerified;
    }

}
