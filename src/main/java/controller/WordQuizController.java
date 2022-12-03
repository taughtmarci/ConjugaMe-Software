package controller;

import model.Pronoun;
import model.Verb;
import model.Word;
import model.WordQuizComponents;
import view.EndQuiz;
import view.MainWindow;
import view.WordQuiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class WordQuizController {
    private final WordQuiz quiz;
    private final ArrayList<Word> words;
    private final WordQuizComponents comps;

    public int score;
    public int iteration;
    public Word currentWord;

    private ArrayList<Word> incorrectWords;
    private EndQuiz next;

    public WordQuizController(WordQuiz quiz) throws IOException {
        this.quiz = quiz;
        this.comps = quiz.getComps();

        if (!comps.isNormal()) comps.setWordAmount(comps.getDuration());
        this.words = MainWindow.local.processWordQueries(comps);
        this.incorrectWords = new ArrayList<>();

        randomizeWordList();
        printWords();

        score = 0;
        iteration = 0;
        nextRound();
    }

    public void nextRound() {
        if (iteration == comps.getWordAmount() - 1) {
            finishQuiz();
        }
        else {
            currentWord = words.get(iteration);
            quiz.setCurrentWordLabel(currentWord.getMasculino());
            // todo definitions label
            quiz.setSectionSolutions(currentWord.getFemenino(), currentWord.getMasculino());

            quiz.updateUI();
        }
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
            System.out.println("\n");
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
