package view;

import controller.Section;
import controller.VerbCollection;
import model.Form;
import model.Pronoun;
import controller.QuizComponents;
import model.ResultImage;
import model.Verb;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Quiz extends JPanel {
    private final VerbCollection collection;
    private final QuizComponents components;

    public int score;
    private JLabel scoreLabel;

    public int iteration;
    private JLabel outOfLabel;

    public Verb currentVerb;
    public Form currentForm;
    private final JLabel currentVerbLabel;
    private final JLabel currentFormLabel;
    private final JButton sendResultsButton;

    private final ResultImage resultImage = new ResultImage();
    private final ArrayList<Section> sections = new ArrayList<>();
    private Section presentoSection;
    private Section pasadoSection;

    public Quiz(VerbCollection collection) {
        this.collection = collection;
        this.components = collection.getComponents();

        this.currentVerbLabel = new JLabel("");
        this.currentFormLabel = new JLabel("");
        this.sendResultsButton = new JButton("K\u00FCld\u00E9s");

        setLayout(new MigLayout("al center center"));
        initComponents();

        score = 0;
        iteration = 0;
        nextRound();

        setVisible(true);
    }

    private void nextRound() {
        currentVerb = collection.getVerb(iteration);
        currentVerbLabel.setText(currentVerb.getBasic().getInfinitivo());

        if (!components.onlyParticipio()) {
            // TODO: ezt okosabban
            currentForm = components.getSelectedForms().get((int)
                    (Math.random() * components.getSelectedForms().size()));
            currentFormLabel.setText(currentForm.toString());

            int i = 0;
            for (Pronoun p : components.getSelectedPronouns()) {
                String currentSolution = currentVerb.getSolution(currentForm, p);
                sections.get(i).setSolution(currentSolution);
                i++;
            }
        }

        if (components.isParticipioPresentoSelected())
            presentoSection.setSolution(currentVerb.getBasic().getPresento());

        if (components.isParticipioPasadoSelected())
            pasadoSection.setSolution(currentVerb.getBasic().getPasado());

        this.updateUI();
    }

    private void initComponents() {
        // score label
        scoreLabel = new JLabel(Integer.toString(score) + " pont");
        add(scoreLabel, "align left");

        // end quiz button
        JButton endQuizButton = new JButton("Kv\u00EDz befejez\u00E9se");
        add(endQuizButton, "align right, wrap");
        endQuizButton.addActionListener(e -> {
            collection.getMain().switchPanels(this, new StartQuiz(collection.getMain()));
        });

        // set up current verb and form labels
        currentVerbLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        currentFormLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        add(currentVerbLabel, "span, align center");

        // add sections panel
        if (components.isParticipioPresentoSelected()) {
            presentoSection = new Section("Participio presento", resultImage);
            add(presentoSection, "span, align center");
        }

        if (components.isParticipioPasadoSelected()) {
            pasadoSection = new Section("Participio pasado", resultImage);
            add(pasadoSection, "span, align center");
        }

        // pronoun sections
        if (!components.onlyParticipio()) {
            add(currentFormLabel, "span, align center");
            for (Pronoun p : components.getSelectedPronouns()) {
                sections.add(new Section(p.toString(), resultImage));
                add(sections.get(sections.size() - 1), "span, align center");
            }
        }

        // out of label
        outOfLabel = new JLabel(Integer.toString(iteration + 1) + "/" + components.getNumberOfVerbs());
        add(outOfLabel, "align left");

        // send results button
        add(sendResultsButton, "align right");
        sendResultsButton.addActionListener(e -> {
            if (components.isParticipioPresentoSelected())
                if (presentoSection.evaluate()) score++;

            if (components.isParticipioPasadoSelected())
                if (pasadoSection.evaluate()) score++;

            for (Section section : sections)
                if (section.evaluate()) score++;

            // update score and iteration
            iteration++;
            scoreLabel.setText((Integer.toString(score) + " pont"));
            outOfLabel.setText(Integer.toString(iteration + 1) + "/" + components.getNumberOfVerbs());

            this.updateUI();
            Timer cooldown = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    refreshAllSections();
                    nextRound();
                }
            });

            cooldown.setRepeats(false);
            cooldown.start();
        });
        collection.getMain().getRootPane().setDefaultButton(sendResultsButton);
    }

    private void refreshAllSections() {
        if (components.isParticipioPresentoSelected())
            presentoSection.refreshSection();

        if (components.isParticipioPasadoSelected())
            pasadoSection.refreshSection();

        for (Section section: sections)
            section.refreshSection();
    }
}
