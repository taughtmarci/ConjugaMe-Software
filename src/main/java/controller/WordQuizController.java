package controller;

import model.*;
import view.EndWordQuiz;
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
    public int outOf;
    public int iteration;
    public int time;
    public Word currentWord;

    private ArrayList<String> mistakes;
    private ArrayList<Word> correctWords;
    private ArrayList<Word> incorrectWords;

    private EndWordQuiz next;

    public WordQuizController(WordQuiz quiz) {
        this.quiz = quiz;
        this.comps = quiz.getComps();

        this.words = MainWindow.local.processWordQueries(comps);

        this.mistakes = new ArrayList<>();
        this.correctWords = new ArrayList<>();
        this.incorrectWords = new ArrayList<>();

        randomizeWordList();
        //printWords();

        score = 0;
        outOf = 0;
        iteration = 0;
    }

    public void nextRound() {
        if (comps.isNormal() && iteration == comps.getWordAmount())
            finishQuiz();
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
            quiz.setCurrentWordLabel(((comps.isArticlesNeeded() && currentWord.isNoun()) ? "__ " : "") + currentHint);

            // definitions
            quiz.setCurrentDefinitionsLabel(currentWord.getDefinitions());

            // solutions
            quiz.setSectionSolutions(currentWord.getFemenino(), currentWord.getMasculino(), currentWord.isNoun());
            quiz.updateUI();
        }
    }

    String createHint(String s, double diff) {
        char[] characters = s.toCharArray();

        double diffMax = diff + (diff * 0.2);
        double smallChange = Math.random() * (diffMax - diff);

        Random rd = new Random();
        if (rd.nextBoolean()) diff += smallChange;
        else diff -= smallChange;

        int changeAmount = (int) (s.length() * diff);
        for (int i = 0; i < changeAmount; i++) {
            int rand;
            do {rand = (int) (Math.random() * s.length());}
            while (characters[rand] == '_');
            characters[rand] = '_';
        }
        return new String(characters);
    }

    public void evaluateSection(boolean last) {
        if (quiz.getWordSection().evaluate()) {
            score++;
            correctWords.add(currentWord);
            outOf++;
        }
        else if (!last) {
            mistakes.add(quiz.getWordSection().getInputText());
            incorrectWords.add(currentWord);
            outOf++;
        }
    }

    public void finishQuiz() {
        if (!comps.isNormal()) {
            quiz.stopCountBack();
            setTime(comps.getDuration() - quiz.currentTime);
        }
        WordQuizResults results = new WordQuizResults(this);
        quiz.setVisible(false);
        next = new EndWordQuiz(quiz.getMain(), results);
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

    public int getOutOf() {
        return outOf;
    }

    public int getIteration() {
        return iteration;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public ArrayList<Word> getCorrectWords() {
        return correctWords;
    }

    public ArrayList<String> getMistakes() {
        return mistakes;
    }

    public ArrayList<Word> getIncorrectWords() {
        return incorrectWords;
    }
}
