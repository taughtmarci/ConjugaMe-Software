package controller;

import model.Form;
import model.Pronoun;
import model.Verb;
import model.VerbQuizComponents;
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

    public Verb currentVerb;
    public Form currentForm;

    private ArrayList<Verb> incorrectVerbs;
    private ArrayList<Verb> correctVerbs;
    private EndVerbQuiz next;

    public VerbQuizController(VerbQuiz quiz) throws IOException {
        this.quiz = quiz;
        this.comps = quiz.getComps();

        if (!comps.isNormal()) comps.setWordAmount(comps.getDuration());
        this.verbs = MainWindow.local.processVerbQueries(comps);
        this.incorrectVerbs = new ArrayList<>();
        this.correctVerbs = new ArrayList<>();

        randomizeVerbList();
        //printVerbs();

        score = 0;
        outOf = 0;
        iteration = 0;
    }

    public void nextRound() {
        if (iteration == comps.getWordAmount() - 1) {
            finishQuiz();
        }
        else {
            currentVerb = verbs.get(iteration);
            quiz.setCurrentVerbLabel(currentVerb.getBasic().getInfinitivo());

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
            if (quiz.getPresentoSection().evaluate()) {
                score++;
            }
            else {
                incorrectVerbs.add(currentVerb);
            }
            outOf++;
        }

        if (comps.isParticipioPasadoSelected()) {
            if (quiz.getPasadoSection().evaluate()) {
                score++;
            }
            else {
                incorrectVerbs.add(currentVerb);
            }
            outOf++;
        }

        for (VerbSection verbSection : quiz.getSections()) {
            if (verbSection.evaluate()) {
                score++;
            }
            else {
                incorrectVerbs.add(currentVerb);
            }
            outOf++;
        }
    }

    public void finishQuiz() {
        if (!comps.isNormal()) quiz.stopCountBack();
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

    public ArrayList<Verb> getIncorrectVerbs() {
        return incorrectVerbs;
    }
}
