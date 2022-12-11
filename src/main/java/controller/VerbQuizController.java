package controller;

import model.*;
import view.EndVerbQuiz;
import view.MainWindow;
import view.VerbQuiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class VerbQuizController {
    private final VerbQuiz quiz;
    private ArrayList<Verb> verbs;
    private final VerbQuizComponents comps;

    public int score;
    public int outOf;
    public int iteration;
    public int time;

    public Verb currentVerb;
    public Form currentForm;

    private ArrayList<String> mistakes;
    private ArrayList<Conjugation> correctConjugations;
    private ArrayList<Conjugation> incorrectConjugations;
    private EndVerbQuiz next;

    public VerbQuizController(VerbQuiz quiz) throws IOException {
        this.quiz = quiz;
        this.comps = quiz.getComps();

        if (!comps.isNormal()) comps.setWordAmount(comps.getDuration());
        this.verbs = MainWindow.local.processVerbQueries(comps);

        this.mistakes = new ArrayList<>();
        this.correctConjugations = new ArrayList<>();
        this.incorrectConjugations = new ArrayList<>();

        randomizeVerbList();
        //printVerbs();

        score = 0;
        outOf = 0;
        iteration = 0;
    }

    public void nextRound() {
        if (iteration == comps.getWordAmount()) {
            finishQuiz();
        }
        else {
            currentVerb = verbs.get(iteration);
            quiz.setCurrentVerbLabel(currentVerb.getBasic().getInfinitivo());

            // definitions
            quiz.setCurrentDefinitionsLabel(currentVerb.getBasic().getDefinitions());

            if (!comps.onlyParticipio()) {
                currentForm = comps.getSelectedForms().get((int)
                        (Math.random() * comps.getSelectedForms().size()));
                quiz.setCurrentFormLabel(currentForm.toString());

                int i = 0;
                for (Pronoun p : comps.getSelectedPronouns()) {
                    String currentSolution = currentVerb.getSolution(currentForm, p);
                    quiz.setSectionSolution(i, currentSolution);
                    i++;
                }
            }

            if (comps.isParticipioPresentoSelected())
                quiz.setPresentoSectionSolution(currentVerb.getBasic().getPresento());

            if (comps.isParticipioPasadoSelected())
                quiz.setPasadoSectionSolution(currentVerb.getBasic().getPasado());

            quiz.updateUI();
        }
    }

    public void evaluateSections() {
        if (comps.isParticipioPresentoSelected()) {
            Conjugation conj = new Conjugation(
                    currentVerb.getID(),
                    currentVerb.getBasic().getInfinitivo(),
                    currentVerb.getBasic().getPresento(),
                    "Participio Presento",
                    ""
            );

            if (quiz.getPresentoSection().evaluate()) {
                score++;
                correctConjugations.add(conj);
            }
            else {
                mistakes.add(quiz.getPresentoSection().getInputText());
                incorrectConjugations.add(conj);
            }
            outOf++;
        }

        if (comps.isParticipioPasadoSelected()) {
            Conjugation conj = new Conjugation(
                    currentVerb.getID(),
                    currentVerb.getBasic().getInfinitivo(),
                    currentVerb.getBasic().getPasado(),
                    "Participio Pasado",
                    ""
            );

            if (quiz.getPasadoSection().evaluate()) {
                correctConjugations.add(conj);
                score++;
            }
            else {
                mistakes.add(quiz.getPasadoSection().getInputText());
                incorrectConjugations.add(conj);
            }
            outOf++;
        }

        for (VerbSection verbSection : quiz.getSections()) {
            Conjugation conj = new Conjugation(
                    currentVerb.getID(),
                    currentVerb.getBasic().getInfinitivo(),
                    currentVerb.getVerbForm(currentForm, Pronoun.fromString(verbSection.getPronoun())),
                    currentForm.toString(),
                    verbSection.getPronoun()
            );
            if (verbSection.evaluate()) {
                correctConjugations.add(conj);
                score++;
            }
            else {
                mistakes.add(verbSection.getInputText());
                incorrectConjugations.add(conj);
            }
            outOf++;
        }
    }

    public void finishQuiz() {
        if (!comps.isNormal()) {
            quiz.stopCountBack();
            setTime(comps.getDuration() - quiz.currentTime);
        }

        VerbQuizResults results = new VerbQuizResults(this);
        quiz.setVisible(false);

        next = new EndVerbQuiz(quiz.getMain(), results);
        quiz.getMain().switchPanels(quiz, next);
    }

    public void printVerbs() {
        for (Verb v : verbs) {
            v.printVerb();
            System.out.println("\n");
        }
    }

    public void randomizeVerbList() {
        Collections.shuffle(this.verbs);
    }

    public VerbQuizComponents getComps() {
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

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList<String> getMistakes() {
        return mistakes;
    }

    public ArrayList<Conjugation> getCorrectConjugations() {
        return correctConjugations;
    }

    public ArrayList<Conjugation> getIncorrectConjugations() {
        return incorrectConjugations;
    }
}
