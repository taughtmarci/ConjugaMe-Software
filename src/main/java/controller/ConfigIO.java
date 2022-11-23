package controller;

import model.Form;
import model.Group;
import model.Pronoun;
import model.VerbQuizComponents;

import java.io.*;
import java.util.ArrayList;

public class ConfigIO {
    private File file;
    private BufferedReader br;

    public VerbQuizComponents readComponents(String fileName) throws IOException {
        file = new File(fileName);
        VerbQuizComponents inputComps = new VerbQuizComponents();

        if (file.exists() && file.isFile()) {
            ArrayList<String> lines = readLines(file);

            for (int i = 0; i < lines.size(); i++) {
                switch (lines.get(i)) {
                    case "Pronoun":
                        i++;
                        while (!lines.get(i).trim().equals("END")) {
                            inputComps.addPronoun(lines.get(i).trim());
                            i++;
                        }
                        break;
                    case "Form":
                        i++;
                        while (!lines.get(i).trim().equals("END")) {
                            if (lines.get(i).trim().equals("Participio Presento"))
                                inputComps.setParticipioPresentoSelected(true);
                            else if (lines.get(i).trim().equals("Participio Pasado"))
                                inputComps.setParticipioPasadoSelected(true);
                            else inputComps.addForm(lines.get(i).trim());
                            i++;
                        }
                        break;
                    case "Group":
                        i++;
                        while (!lines.get(i).trim().equals("END")) {
                            i++;
                        }
                        break;
                    case "Number":
                        i++;
                        inputComps.setNumberOfVerbs(Integer.parseInt(lines.get(i).trim()));
                        break;
                    case "Duration":
                        i++;
                        inputComps.setDurationMin(Integer.parseInt(lines.get(i).trim()));
                        i++;
                        inputComps.setDurationSec(Integer.parseInt(lines.get(i).trim()));
                        break;
                    default:
                        System.out.println(lines.get(i));
                }
            }
        } else {
            // TODO dialog?
            file.createNewFile();
            inputComps = getDefaultPreferences();
        }

        // todo dialog too?
        if (!inputComps.isWorkingCorrectly()) {
            inputComps = getDefaultPreferences();
        }

        return inputComps;
    }

    public void writeComponents(String fileName, VerbQuizComponents outputComps) throws IOException {
        file = new File(fileName);
        // debug
        outputComps.printStats();

        if (!outputComps.isWorkingCorrectly()) {
            // TODO dialog?
            outputComps = getDefaultPreferences();
        }

        FileWriter writer = new FileWriter(fileName);
        // clear file data
        if (file.exists() && file.isFile()) {
            file.delete();
            file.createNewFile();
        }

        // add pronouns
        writer.write("Pronoun\n");
        for (Pronoun p : outputComps.getSelectedPronouns())
            writer.write(p.toString() + "\n");
        writer.write("END\n\n");

        // add forms
        writer.write("Form\n");
        if (outputComps.isParticipioPresentoSelected())
            writer.write("Participio Presento\n");
        if (outputComps.isParticipioPasadoSelected())
            writer.write("Participio Pasado\n");
        for (Form f : outputComps.getSelectedForms())
            writer.write(f.toString() + "\n");
        writer.write("END\n\n");

        // TODO add groups
        writer.write("Group\n");
        writer.write("END\n\n");

        // add verb number and duration mins and sec
        writer.write("Number\n" + outputComps.getNumberOfVerbs() + "\n\n");
        writer.write("Duration\n" + outputComps.getDurationMin() + "\n" + outputComps.getDurationSec());

        writer.close();
    }

    private VerbQuizComponents getDefaultPreferences() {
        VerbQuizComponents defaultComps = new VerbQuizComponents();
        defaultComps.setNumberOfVerbs(25);
        defaultComps.setDurationMin(5);
        defaultComps.setDurationSec(0);
        defaultComps.setParticipioPresentoSelected(true);
        defaultComps.setParticipioPasadoSelected(true);
        // TODO add groups
        return defaultComps;
    }

    public ArrayList<Group> readVerifiedGroups(String fileName) throws IOException {
        ArrayList<Group> verifiedGroups = new ArrayList<>();
        file = new File(fileName);

        if (file.exists() && file.isFile()) {
            ArrayList<String> lines = readLines(file);

            String[] tuple;
            for (String line : lines) {
                tuple = line.split(";", 2);
                verifiedGroups.add(new Group(Integer.parseInt(tuple[0]), tuple[1]));
            }
        }

        return verifiedGroups;
    }

    public ArrayList<String> readLines(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        this.br = new BufferedReader(isr);

        ArrayList<String> lines = new ArrayList<>();
        String line;

        line = br.readLine();
        while (line != null) {
            if (!line.trim().equals("\n")) lines.add(line);
            line = br.readLine();
        }

        return lines;
    }

    public void writeVerifiedGroups(String fileName) throws IOException {

    }
}
