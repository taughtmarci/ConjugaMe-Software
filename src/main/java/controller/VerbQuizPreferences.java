package controller;

import model.DialogType;
import model.VerbQuizComponents;
import view.MainWindow;
import view.VerbQuiz;
import view.VerbQuizSetup;

import javax.swing.*;
import java.io.IOException;

public class VerbQuizPreferences extends QuizPreferences {
    private final String FILE_PATH = "config/verbpreferences.cfg";
    private VerbQuizComponents comps;
    private final VerbQuizSetup setup;
    private VerbQuiz current;

    public VerbQuizPreferences(VerbQuizSetup setup) throws IOException {
        super();
        this.setup = setup;
        this.comps = new VerbQuizComponents();
    }

    public void setupComps() {
        this.comps = new VerbQuizComponents();

        // pronouns
        for (JCheckBox cb : setup.getPronounCheckBoxes())
            if (cb.isSelected()) comps.addPronoun(cb.getText());

        // forms
        for (JCheckBox cb : setup.getFormCheckBoxes()) {
            if (cb.isSelected()) {
                switch (cb.getText()) {
                    case "Participio Presente" -> comps.setParticipioPresenteSelected(true);
                    case "Participio Pasado" -> comps.setParticipioPasadoSelected(true);
                    default -> comps.addForm(cb.getText());
                }
            }
        }

        // groups
        comps.setSelectedGroups(selector.getSelectedRows());

        // normal or timed
        comps.setNormal(setup.getNormalModeRadio().isSelected());

        // number of verbs
        comps.setWordAmount((int) setup.getVerbNumberChooser().getValue());

        // duration min and sec
        comps.setDurationMin((int) setup.getMinutesChooser().getValue());
        comps.setDurationSec((int) setup.getSecondsChooser().getValue());
    }

    public String validateForm() {
        String error = "";
        comps.printStats();;

        if (!comps.isParticipioPasadoSelected() && !comps.isParticipioPresenteSelected()
                && comps.getSelectedForms().size() == 0)
            error = "Legal\u00E1bb egy igeid\u0151/m\u00F3d kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";
        else if (comps.getSelectedForms().size() > 0 && comps.getSelectedPronouns().size() == 0)
            error = "Legal\u00E1bb egy n\u00E9vm\u00E1s kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";
        else if (comps.getSelectedGroups().size() == 0)
            error = "Legal\u00E1bb egy csoport kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";
        else if (comps.getWordAmount() < 1 || comps.getWordAmount() > 100)
            error = "Az ig\u00E9k sz\u00E1ma 1 \u00E9s 100 k\u00F6z\u00F6tt kell, hogy legyen!";
        else if (comps.getDurationMin() < 1 || comps.getDurationMin() > 5)
            error = "A megadott perc 1 \u00E9s 5 k\u00F6z\u00F6tt kell, hogy legyen!";
        else if (comps.getDurationSec() < 0 || comps.getDurationSec() > 59)
            error = "A megadott m\u00E1sodperc 0 \u00E9s 59 k\u00F6z\u00F6tt kell, hogy legyen!";

        return error;
    }

    public void savePrefs(boolean startFlag) {
        setupComps();
        String error = validateForm();
        if (error.equals("")) {
            try {
                getConfig().writeVerbComponents(FILE_PATH, getComps());
                MainWindow.verbComps = getComps();
                error = "Sikeres ment\u00E9s!";
            } catch (IOException e) {
                error = "Nem siker\u00FClt menteni a preferenci\u00E1kat.";
            }
        }
        if (startFlag && error.equals("Sikeres ment\u00E9s!")) {
            try {
                current = new VerbQuiz(setup.getSetupPane().getMain());
            } catch (IOException e) {
                MainWindow.dialog.showExceptionDialog("Ismeretlen hiba", "V\u00E1ratlan fut\u00E1s k\u00F6zbeni hiba l\u00E9pett fel.\n" +
                        "K\u00E9rj\u00FCk, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
            }
            setup.setVisible(false);
            setup.getSetupPane().getMain().switchPanels(setup.getSetupPane(), current);
        } else {
            setup.writeOutErrors(error);
        }
    }

    public VerbQuizComponents getComps() {
        return comps;
    }

}
