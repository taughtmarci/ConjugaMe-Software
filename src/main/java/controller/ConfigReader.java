package controller;

import java.io.*;
import java.util.ArrayList;

public class ConfigReader {
    private String FILE_NAME = "config/preferences.cfg";
    private BufferedReader br;

    private ArrayList<String> lines;
    public QuizComponents inputComponents;

    public ConfigReader() {
        inputComponents = new QuizComponents();
        File file = new File(FILE_NAME);

        InputStream is;
        try {
            is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            this.br = new BufferedReader(isr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO normálisan dialogban
            System.out.println("hiba: " + e);
        }

        lines = new ArrayList<>();
        String line;

        try {
            if (br != null) {
                line = br.readLine();
                while (line != null) {
                    if (!line.trim().equals("\n")) lines.add(line);
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            // TODO normálisan dialogban
            System.out.println("hiba: " + e);
        }

        processInputComponents();
    }

    private void processInputComponents() {
        for (int i = 0 ; i < lines.size(); i++) {
            switch (lines.get(i)) {
                case "Pronoun":
                    i++;
                    while (!lines.get(i).trim().equals("END")) {
                        inputComponents.addPronoun(lines.get(i).trim());
                        i++;
                    }
                    break;
                case "Form":
                    i++;
                    while (!lines.get(i).trim().equals("END")) {
                        if (lines.get(i).trim().equals("Participio Presento"))
                            inputComponents.setParticipioPresentoSelected(true);
                        else if (lines.get(i).trim().equals("Participio Pasado"))
                            inputComponents.setParticipioPasadoSelected(true);
                        else inputComponents.addForm(lines.get(i).trim());
                        i++;
                    }
                    break;
                case "Group":
                    i++;
                    while (!lines.get(i).trim().equals("END")) {
                        inputComponents.addGroup(lines.get(i).trim());
                        i++;
                    }
                    break;
                case "Number":
                    i++;
                    inputComponents.setNumberOfVerbs(Integer.parseInt(lines.get(i).trim()));
                    break;
                case "Errors":
                    i++;
                    inputComponents.setShowErrors(Boolean.parseBoolean(lines.get(i).trim()));
                    break;
                case "Duration":
                    i++;
                    inputComponents.setDurationMin(Integer.parseInt(lines.get(i).trim()));
                    i++;
                    inputComponents.setDurationSec(Integer.parseInt(lines.get(i).trim()));
                    break;
                default:
                    System.out.println(lines.get(i));
            }
        }
        inputComponents.printStats();
    }

}
