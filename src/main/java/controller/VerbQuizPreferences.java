package controller;

import model.Verb;
import model.VerbQuizComponents;
import view.VerbQuizSetup;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class VerbQuizPreferences {
    private final VerbQuizSetup setup;

    private VerbQuizComponents comps;
    private final ConfigIO config;

    private final GroupHandler handler;
    private final GroupSelector selector;

    public VerbQuizPreferences(VerbQuizSetup setup) throws IOException {
        this.setup = setup;
        this.comps = new VerbQuizComponents();
        this.config = new ConfigIO();

        this.handler = new GroupHandler();
        this.selector = new GroupSelector(handler);
    }

    public void setupComps() {
        comps = new VerbQuizComponents();

        // pronouns
        for (JCheckBox cb : setup.getPronounCheckBoxes())
            if (cb.isSelected()) comps.addPronoun(cb.getText());

        // forms
        for (JCheckBox cb : setup.getFormCheckBoxes()) {
            if (cb.isSelected()) {
                switch (cb.getText()) {
                    case "Participio Presento" -> comps.setParticipioPresentoSelected(true);
                    case "Participio Pasado" -> comps.setParticipioPasadoSelected(true);
                    default -> comps.addForm(cb.getText());
                }
            }
        }

        // groups
        comps.setSelectedGroups(selector.getSelectedRows());

        // number of verbs
        comps.setNumberOfVerbs((int) setup.getVerbNumberChooser().getValue());

        // duration min and sec
        comps.setDurationMin((int) setup.getMinutesChooser().getValue());
        comps.setDurationSec((int) setup.getSecondsChooser().getValue());
    }

    public String validateForm() {
        String error = "";

        if (!comps.isParticipioPasadoSelected() && !comps.isParticipioPresentoSelected()
                && comps.getSelectedForms().size() == 0)
            error = "Legal\u00E1bb egy igeid\u0151/m\u00F3d kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";
        else if (comps.getSelectedForms().size() > 0 && comps.getSelectedPronouns().size() == 0)
            error = "Legal\u00E1bb egy n\u00E9vm\u00E1s kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";
        else if (comps.getSelectedGroups().size() == 0)
            error = "Legal\u00E1bb egy igecsoport kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";
        else if (comps.getNumberOfVerbs() < 0 || comps.getNumberOfVerbs() > 250)
            error = "Az ig\u00E9k sz\u00E1ma 0 \u00E9s 250 k\u00F6z\u00F6tt kell, hogy legyen!";
        else if (comps.getDurationMin() < 1 || comps.getDurationMin() > 30)
            error = "A megadott perc 1 \u00E9s 30 k\u00F6z\u00F6tt kell, hogy legyen!";
        else if (comps.getDurationMin() < 0 || comps.getDurationMin() > 59)
            error = "A megadott m\u00E1sodperc 1 \u00E9s 30 k\u00F6z\u00F6tt kell, hogy legyen!";

        return error;
    }

    public ConfigIO getConfig() {
        return config;
    }

    public VerbQuizSetup getSetup() {
        return setup;
    }

    public VerbQuizComponents getComps() {
        return comps;
    }

    public GroupHandler getHandler() {
        return handler;
    }

    public GroupSelector getSelector() {
        return selector;
    }
}
