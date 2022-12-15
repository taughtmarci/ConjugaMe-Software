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

    public static AppConfigurations readAppConfigurations(String fileName) throws IOException {
        File file = new File(fileName);
        AppConfigurations inputConfig = new AppConfigurations();

        if (file.exists() && file.isFile()) {
            ArrayList<String> lines = readLines(file);

            for (int i = 0; i < lines.size(); i++) {
                switch (lines.get(i)) {
                    case "IsDarkMode" -> {
                        i++;
                        inputConfig.setDarkMode(Boolean.parseBoolean(lines.get(i).trim()));
                    }
                    case "IsOfflineMode" -> {
                        i++;
                        inputConfig.setOfflineMode(Boolean.parseBoolean(lines.get(i).trim()));
                    }
                    case "IsInstantFeedback" -> {
                        i++;
                        inputConfig.setInstantFeedback(Boolean.parseBoolean(lines.get(i).trim()));
                    }
                    case "IsEnterAsTab" -> {
                        i++;
                        inputConfig.setEnterAsTab(Boolean.parseBoolean(lines.get(i).trim()));
                    }
                    case "IsEnyeEnabled" -> {
                        i++;
                        inputConfig.setEnyeEnabled(Boolean.parseBoolean(lines.get(i).trim()));
                    }
                }
            }
        } else {
            MainWindow.dialog.showDialog("Konfigur\u00E1ci\u00F3 beolvas\u00E1si hiba", "A be\u00E1ll\u00EDt\u00E1sokat " +
                    "tartalmaz\u00F3 konfigur\u00E1ci\u00F3s f\u00E1jl megs\u00E9r\u00FClt, vagy t\u00F6r\u00F6lve lett.\n" +
                    "A program megpr\u00F3b\u00E1lja bet\u00F6lteni az alap\u00E9rtelmezett be\u00E1ll\u00EDt\u00E1sokat.", DialogType.WARNING);
            file.createNewFile();
            inputConfig = new AppConfigurations();
        }

        return inputConfig;
    }

    public static void writeAppConfigurations(String fileName, AppConfigurations outputConfig) throws IOException {
        File file = new File(fileName);

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        // clear file data
        if (file.exists() && file.isFile()) {
            file.delete();
            file.createNewFile();
        }

        writer.write("IsDarkMode\n" + outputConfig.isDarkMode() + "\n\n");
        writer.write("IsOfflineMode\n" + outputConfig.isOfflineMode() + "\n\n");
        writer.write("IsInstantFeedback\n" + outputConfig.isInstantFeedback() + "\n\n");
        writer.write("IsEnterAsTab\n" + outputConfig.isEnterAsTab() + "\n\n");
        writer.write("IsEnyeEnabled\n" + outputConfig.isEnyeEnabled());

        writer.close();
    }

    public static String readSQL(String fileName) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            MainWindow.dialog.showExceptionDialog("SQL beolvas\u00E1si hiba", "Az alkalmaz\u00E1s konfigur\u00E1ci\u00F3s f\u00E1jljai megs\u00E9r\u00FClhettek.\n" +
                    "K\u00E9rj\u00FCk, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
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
                            if (lines.get(i).trim().equals("Participio Presente"))
                                inputComps.setParticipioPresenteSelected(true);
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
            MainWindow.dialog.showDialog("Igeragoz\u00E1s kv\u00EDz preferenci\u00E1k beolvas\u00E1si hiba",
                    "Az igeragoz\u00E1si kv\u00EDz preferenci\u00E1it tartalmaz\u00F3 konfigur\u00E1ci\u00F3s f\u00E1jl megs\u00E9r\u00FClt, vagy t\u00F6r\u00F6lve lett.\n" +
                    "A program bet\u00F6lti az alap\u00E9rtelmezett preferenciákat", DialogType.WARNING);
            file.createNewFile();
            inputComps = getDefaultVerbComps();
        }

        if (!inputComps.isWorkingCorrectly()) {
            MainWindow.dialog.showDialog("Igeragoz\u00E1s kv\u00EDz preferenci\u00E1k beolvas\u00E1si hiba",
                    "Az igeragoz\u00E1si kv\u00EDz preferenci\u00E1it tartalmaz\u00F3 konfigur\u00E1ci\u00F3s f\u00E1jl megs\u00E9r\u00FClt, vagy t\u00F6r\u00F6lve lett.\n" +
                            "A program bet\u00F6lti az alap\u00E9rtelmezett preferenciákat", DialogType.WARNING);
            inputComps = getDefaultVerbComps();
        }

        return inputComps;
    }

    public void writeVerbComponents(String fileName, VerbQuizComponents outputComps) throws IOException {
        File file = new File(fileName);
        // debug
        //outputComps.printStats();

        if (!outputComps.isWorkingCorrectly()) {
            MainWindow.dialog.showExceptionDialog("Preferencia ment\u00E9s hiba", "Nem siker\u00FClt menteni a megadott adatokat," +
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
        if (outputComps.isParticipioPresenteSelected())
            writer.write("Participio Presente\n");
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
                        inputComps.setDifficulty(Difficulty.fromString(lines.get(i).trim()));
                    }
                    case "Articles" -> {
                        i++;
                        inputComps.setArticlesNeeded(Boolean.parseBoolean(lines.get(i).trim()));
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
            MainWindow.dialog.showDialog("Sz\u00F3ford\u00EDt\u00E1si kv\u00EDz preferenci\u00E1k beolvas\u00E1si hiba",
                    "Az sz\u00F3ford\u00EDt\u00E1si kv\u00EDz preferenci\u00E1it tartalmaz\u00F3 konfigur\u00E1ci\u00F3s f\u00E1jl megs\u00E9r\u00FClt, vagy t\u00F6r\u00F6lve lett.\n" +
                            "A program bet\u00F6lti és menti az alap\u00E9rtelmezett preferenciákat", DialogType.WARNING);
            file.createNewFile();
            inputComps = getDefaultWordComps();
        }

        if (!inputComps.isWorkingCorrectly()) {
            MainWindow.dialog.showDialog("Sz\u00F3ford\u00EDt\u00E1si kv\u00EDz preferenci\u00E1k beolvas\u00E1si hiba",
                    "Az sz\u00F3ford\u00EDt\u00E1si kv\u00EDz preferenci\u00E1it tartalmaz\u00F3 konfigur\u00E1ci\u00F3s f\u00E1jl megs\u00E9r\u00FClt, vagy t\u00F6r\u00F6lve lett.\n" +
                            "A program bet\u00F6lti és menti az alap\u00E9rtelmezett preferenciákat", DialogType.WARNING);
            inputComps = getDefaultWordComps();
        }

        return inputComps;
    }

    public void writeWordComponents(String fileName, WordQuizComponents outputComps) throws IOException {
        File file = new File(fileName);
        // debug
        //outputComps.printStats();

        if (!outputComps.isWorkingCorrectly()) {
            MainWindow.dialog.showExceptionDialog("Preferencia ment\u00E9s hiba", "Nem siker\u00FClt menteni a megadott adatokat," +
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
        writer.write(outputComps.getDifficulty().toString() + "\n\n");

        // add articles needed
        writer.write("Articles\n");
        writer.write(outputComps.isArticlesNeeded() + "\n\n");

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
        defaultComps.setParticipioPresenteSelected(true);
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

    public static void updateBadgeFile(String fileName, String badgeType) throws IOException {
        File file = new File(fileName);

        if (file.exists() && file.isFile()) {
            ArrayList<String> lines = readLines(file);
            if (!lines.contains(badgeType)) {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8);
                writer.write(badgeType + "\n");
                writer.close();
                MainWindow.dialog.showDialog("Sz\u00E9p munka!", "\u00DAj b\u00E9lyeget szerezt\u00E9l a" +
                        "gy\u0171jtem\u00E9nyedbe!\n" + "Megtekintheted az Eredm\u00E9nyek men\u00FCponton bel\u00FCl.", DialogType.INFORMATION);
            }
        }
    }

    public static void resetFile(String fileName) {
        File file = new File(fileName);

        try {
            if (file.exists() && file.isFile()) {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            MainWindow.dialog.showExceptionDialog("F\u00E1jlkezel\u00E9si hiba", "Az alkalmaz\u00E1s konfigur\u00E1ci\u00F3s f\u00E1jljai megs\u00E9r\u00FClhettek.\n" +
                    "K\u00E9rj\u00FCk, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
            throw new RuntimeException(e);
        }
    }

    public void writeVerifiedGroups(String fileName) throws IOException {

    }
}
