package controller;

import model.*;
import view.EndQuiz;
import view.MainWindow;
import view.WordQuiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WordQuizController {
    private final WordQuiz quiz;
    private final ArrayList<Word> words;
    private final WordQuizComponents comps;

    public int score;
    public int iteration;
    public Word currentWord;

    private ArrayList<Word> incorrectWords;
    private ArrayList<Word> correctWords;
    private EndQuiz next;

    public WordQuizController(WordQuiz quiz) throws IOException {
        this.quiz = quiz;
        this.comps = quiz.getComps();

        if (!comps.isNormal()) comps.setWordAmount(comps.getDuration());
        this.words = MainWindow.local.processWordQueries(comps);
        this.incorrectWords = new ArrayList<>();
        this.correctWords = new ArrayList<>();

        randomizeWordList();
        //printWords();
        score = 0;
        iteration = 0;
    }

    public void nextRound() {
        if (iteration == comps.getWordAmount() - 1) {
            finishQuiz();
        }
        else {
            currentWord = words.get(iteration);

            // word hint label
            String currentHint = "";
            if (comps.getDifficulty() != Difficulty.WithoutHint) {
                if (currentWord.getMasculino().equals(""))
                    currentHint = currentWord.getFemenino();
                else currentHint = currentWord.getMasculino();

                switch (comps.getDifficulty()) {
                    case Easy -> currentHint = createHint(currentHint, 0.33);
                    case Medium -> currentHint = createHint(currentHint, 0.5);
                    case Hard -> currentHint = createHint(currentHint, 0.75);
                }
            }
            quiz.setCurrentWordLabel(currentHint);

            // definitions
            StringBuilder currentDefinitions = new StringBuilder();
            for (String def : currentWord.definitions)
                if (!def.equals("")) currentDefinitions.append(def).append(", ");
            currentDefinitions = new StringBuilder((currentDefinitions.substring(0, currentDefinitions.length() - 2)));
            quiz.setCurrentDefinitionsLabel(currentDefinitions.toString());

            // solutions
            quiz.setSectionSolutions(currentWord.getFemenino(), currentWord.getMasculino());
            quiz.updateUI();
        }
    }

    String createHint(String s, double diff) {
        char[] characters = s.toCharArray();

        double diffMax = diff + (diff * 0.2);
        double diffMin = diff - (diff * 0.05);
        double smallChange = Math.random() * (diffMax - diffMin);

        Random rd = new Random();
        if (rd.nextBoolean()) diff += smallChange;
        else diff -= smallChange;

        int changeAmount = (int) (s.length() * diff);
        System.out.println(changeAmount);
        for (int i = 0; i < changeAmount; i++) {
            int rand = (int)(Math.random() * s.length());
            characters[rand] = '_';
        }
        return new String(characters);
    }

    public void evaluateSection() {
        if (quiz.getWordSection().evaluate()) score++;
        else incorrectWords.add(currentWord);
    }

    public void finishQuiz() {
        if (!comps.isNormal()) quiz.stopCountBack();
        WordQuizResults results = new WordQuizResults(score, this, incorrectWords);
        quiz.setVisible(false);
        //next = new EndQuiz(quiz.getMain(), results);
        quiz.getMain().switchPanels(quiz, next);
    }

    public void printWords() {
        for (Word w : words) {
            w.printWord();
            System.out.println("");
        }
    }

    public void randomizeWordList() {
        Collections.shuffle(this.words);
    }

    public WordQuizComponents getComps() {
        return comps;
    }

    public int getScore() {
        return score;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }
}
