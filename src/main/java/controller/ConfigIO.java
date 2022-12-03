package controller;

import model.*;
import view.MainWindow;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ConfigIO {

    public String readSQL(String fileName) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        return contentBuilder.toString();
    }

    public static VerbQuizComponents readVerbComponents(String fileName) throws IOException {
        File file = new File(fileName);
        VerbQuizComponents inputComps = new VerbQuizComponents();

        if (file.exists() && file.isFile()) {
            ArrayList<String> lines = readLines(file);

            for (int i = 0; i < lines.size(); i++) {
                switch (lines.get(i)) {
                    case "Pronoun" -> {
                        i++;
                        while (!lines.get(i).trim().equals("END")) {
                            inputComps.addPronoun(lines.get(i).trim());
                            i++;
                        }
                    }
                    case "Form" -> {
                        i++;
                        while (!lines.get(i).trim().equals("END")) {
                            if (lines.get(i).trim().equals("Participio Presento"))
                                inputComps.setParticipioPresentoSelected(true);
                            else if (lines.get(i).trim().equals("Participio Pasado"))
                                inputComps.setParticipioPasadoSelected(true);
                            else inputComps.addForm(lines.get(i).trim());
                            i++;
                        }
                    }
                    case "Group" -> {
                        i++;
                        while (!lines.get(i).trim().equals("END")) {
                            String[] tuple = lines.get(i).trim().split(";", 2);
                            inputComps.addGroup(new Group(Integer.parseInt(tuple[0]), tuple[1]));
                            i++;
                        }
                    }
                    case "IsNormal" -> {
                        i++;
                        inputComps.setNormal(Boolean.parseBoolean(lines.get(i).trim()));
                    }
                    case "Number" -> {
                        i++;
                        inputComps.setWordAmount(Integer.parseInt(lines.get(i).trim()));
                    }
                    case "Duration" -> {
                        i++;
                        inputComps.setDurationMin(Integer.parseInt(lines.get(i).trim()));
                        i++;
                        inputComps.setDurationSec(Integer.parseInt(lines.get(i).trim()));
                    }
                }
            }
        } else {
            // todo dialog?
            file.createNewFile();
            inputComps = getDefaultVerbComps();
        }

        // todo dialog?
        if (!inputComps.isWorkingCorrectly()) {
            inputComps = getDefaultVerbComps();
        }

        return inputComps;
    }

    public void writeVerbComponents(String fileName, VerbQuizComponents outputComps) throws IOException {
        File file = new File(fileName);
        // debug
        //outputComps.printStats();

        if (!outputComps.isWorkingCorrectly()) {
            MainWindow.dialog.showDialog("Preferencia ment\u00E9s hiba", "Nem siker\u00FClt menteni a megadott adatokat," +
                    "a preferenci\u00E1kat tartalmaz\u00F3 f\u00E1jl az alap\u00E9rtelmezett \u00E1llapot\u00E1ba lett helyezve.", DialogType.WARNING);
            outputComps = getDefaultVerbComps();
        }

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
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

        // add groups
        writer.write("Group\n");
        for (Group g : outputComps.getSelectedGroups())
            writer.write(g.id() + ";" + g.name() + "\n");
        writer.write("END\n\n");

        // add isnormal, verb number and duration mins and sec
        writer.write("IsNormal\n" + outputComps.isNormal() + "\n\n");
        writer.write("Number\n" + outputComps.getWordAmount() + "\n\n");
        writer.write("Duration\n" + outputComps.getDurationMin() + "\n" + outputComps.getDurationSec());

        writer.close();
    }

    public static WordQuizComponents readWordComponents(String fileName) throws IOException {
        File file = new File(fileName);
        WordQuizComponents inputComps = new WordQuizComponents();

        if (file.exists() && file.isFile()) {
            ArrayList<String> lines = readLines(file);

            for (int i = 0; i < lines.size(); i++) {
                switch (lines.get(i)) {
                    case "Difficulty" -> {
                        i++;
                        while (!lines.get(i).trim().equals("END")) {
                            inputComps.setDifficulty(Difficulty.fromString(lines.get(i).trim()));
                            i++;
                        }
                    }
                    case "Group" -> {
                        i++;
                        while (!lines.get(i).trim().equals("END")) {
                            String[] tuple = lines.get(i).trim().split(";", 2);
                            inputComps.addGroup(new Group(Integer.parseInt(tuple[0]), tuple[1]));
                            i++;
                        }
                    }
                    case "IsNormal" -> {
                        i++;
                        inputComps.setNormal(Boolean.parseBoolean(lines.get(i).trim()));
                    }
                    case "Number" -> {
                        i++;
                        inputComps.setWordAmount(Integer.parseInt(lines.get(i).trim()));
                    }
                    case "Duration" -> {
                        i++;
                        inputComps.setDurationMin(Integer.parseInt(lines.get(i).trim()));
                        i++;
                        inputComps.setDurationSec(Integer.parseInt(lines.get(i).trim()));
                    }
                }
            }
        } else {
            // todo dialog?
            file.createNewFile();
            inputComps = getDefaultWordComps();
        }

        // todo dialog?
        if (!inputComps.isWorkingCorrectly()) {
            inputComps = getDefaultWordComps();
        }

        return inputComps;
    }

    public void writeWordComponents(String fileName, WordQuizComponents outputComps) throws IOException {
        File file = new File(fileName);
        // debug
        //outputComps.printStats();

        if (!outputComps.isWorkingCorrectly()) {
            MainWindow.dialog.showDialog("Preferencia ment\u00E9s hiba", "Nem siker\u00FClt menteni a megadott adatokat," +
                    "a preferenci\u00E1kat tartalmaz\u00F3 f\u00E1jl az alap\u00E9rtelmezett \u00E1llapot\u00E1ba lett helyezve.", DialogType.WARNING);
            outputComps = getDefaultWordComps();
        }

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        // clear file data
        if (file.exists() && file.isFile()) {
            file.delete();
            file.createNewFile();
        }

        // add difficulty
        writer.write("Difficulty\n");
        writer.write(outputComps.getDifficulty().toString() + "\n");
        writer.write("END\n\n");

        // add groups
        writer.write("Group\n");
        for (Group g : outputComps.getSelectedGroups())
            writer.write(g.id() + ";" + g.name() + "\n");
        writer.write("END\n\n");

        // add isnormal, verb number and duration mins and sec
        writer.write("IsNormal\n" + outputComps.isNormal() + "\n\n");
        writer.write("Number\n" + outputComps.getWordAmount() + "\n\n");
        writer.write("Duration\n" + outputComps.getDurationMin() + "\n" + outputComps.getDurationSec());

        writer.close();
    }

    private static VerbQuizComponents getDefaultVerbComps() {
        VerbQuizComponents defaultComps = new VerbQuizComponents();
        defaultComps.setWordAmount(25);
        defaultComps.setDurationMin(5);
        defaultComps.setDurationSec(0);
        defaultComps.setParticipioPresentoSelected(true);
        defaultComps.setParticipioPasadoSelected(true);

        ArrayList<Group> defaultGroups = new ArrayList<>();
        defaultGroups.add(new Group(0, "Conejito"));
        defaultGroups.add(new Group(1, "Principiante"));
        defaultComps.setSelectedGroups(defaultGroups);

        return defaultComps;
    }

    private static WordQuizComponents getDefaultWordComps() {
        WordQuizComponents defaultComps = new WordQuizComponents();
        defaultComps.setWordAmount(25);
        defaultComps.setDurationMin(5);
        defaultComps.setDurationSec(0);

        ArrayList<Group> defaultGroups = new ArrayList<>();
        defaultGroups.add(new Group(0, "Conejito"));
        defaultGroups.add(new Group(1, "Principiante"));
        defaultComps.setSelectedGroups(defaultGroups);

        return defaultComps;
    }

    public static ArrayList<Group> readVerifiedGroups(String fileName) throws IOException {
        ArrayList<Group> verifiedGroups = new ArrayList<>();
        File file = new File(fileName);

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

    public static ArrayList<String> readLines(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);

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
