package controller;

import model.DialogType;
import model.Group;
import view.MainWindow;

import java.io.IOException;
import java.util.ArrayList;

public class GroupHandler {
    private final ArrayList<Group> groups;
    private final ArrayList<Group> defaultVerified;

    public GroupHandler() throws IOException {
        this.groups = ConfigIO.readVerifiedGroups("config/groups.cfg");
        this.defaultVerified = new ArrayList<>();
        setupDefaultVerified();

        if (!inputVerified())
            MainWindow.dialog.showExceptionDialog("Csoport beolvas\u00E1si hiba", "A csoportok beolvas\u00E1sa sor\u00E1n hiba l\u00E9pett fel.\n" +
                    "K\u00E9rj\u00FCk, ind\u00EDtsd \u00FAjra az alkalmaz\u00E1st!", DialogType.ERROR);
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
        if (groups.size() < 5) return false;
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
