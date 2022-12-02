package controller;

import model.Verb;
import model.Word;
import model.WordQuizComponents;
import view.MainWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class WordQuizController {
    private final MainWindow main;
    private ArrayList<Word> words;
    private final WordQuizComponents comps;

    public WordQuizController(MainWindow main, WordQuizComponents comps) throws IOException {
        this.main = main;
        this.comps = comps;

        if (!comps.isNormal()) comps.setWordAmount(comps.getDuration());
        this.words = main.local.processWordQueries(comps);

        randomizeWordList();
        printWords();
    }

    public void printWords() {
        for (Word w : words) {
            w.printWord();
            System.out.println("\n");
        }
    }

    public void randomizeWordList() {
        Collections.shuffle(this.words);
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public WordQuizComponents getComps() {
        return comps;
    }

    public MainWindow getMain() {
        return main;
    }

}
