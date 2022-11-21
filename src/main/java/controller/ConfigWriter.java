package controller;

import model.Form;
import model.Pronoun;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigWriter {
    private String FILE_NAME = "config/preferences.cfg";
    private File file;
    private FileWriter writer;

    private QuizComponents outputComponents;

    public ConfigWriter(QuizComponents outputComponents) throws IOException {
        this.outputComponents = outputComponents;
        outputComponents.printStats();
        File file = new File(FILE_NAME);
        FileWriter writer = new FileWriter(FILE_NAME);

        // clear file data
        if (file.exists() && file.isFile()) {
            file.delete();
            file.createNewFile();
        }

        // add pronouns
        writer.write("Pronoun\n");
        for (Pronoun p : outputComponents.getSelectedPronouns())
            writer.write(p.toString() + "\n");
        writer.write("END\n\n");

        // add forms
        writer.write("Form\n");
        if (outputComponents.isParticipioPresentoSelected())
            writer.write("Participio Presento\n");
        if (outputComponents.isParticipioPasadoSelected())
            writer.write("Participio Pasado\n");
        for (Form f : outputComponents.getSelectedForms())
            writer.write(f.toString() + "\n");
        writer.write("END\n\n");

        // TODO add groups
        writer.write("Group\n");
        writer.write("END\n\n");

        // add number, feedback flag, duration mins and sec
        writer.write("Number\n" + outputComponents.getNumberOfVerbs() + "\n\n");
        writer.write("Feedback\n" + (outputComponents.isFeedbackEnabled() ? "True" : "False") + "\n\n");
        writer.write("Duration\n" + outputComponents.getDurationMin() + "\n" + outputComponents.getDurationSec());

        writer.close();
    }

}
