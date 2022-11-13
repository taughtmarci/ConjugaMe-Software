package view;

import controller.VerbCollection;
import model.Pronoun;
import controller.QuizComponents;
import model.Verb;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Quiz extends JPanel {
    private final VerbCollection collection;
    private final QuizComponents components;

    public int iteration;
    public Verb currentVerb;
    private JLabel currentVerbLabel;
    private JLabel currentFormLabel;
    private JButton sendResultsButton;

    private JLabel presentoLabel;
    private JTextField presentoInput;
    private JLabel pasadoLabel;
    private JTextField pasadoInput;
    private int inputNumber;

    ArrayList<JLabel> labels = new ArrayList<>();
    ArrayList<JTextField> inputs = new ArrayList<>();

    private BufferedImage checkImg = null;
    private BufferedImage crossImg = null;
    private BufferedImage blankImg = null;

    public Quiz(VerbCollection collection) {
        this.collection = collection;
        this.components = collection.getComponents();

        this.currentVerbLabel = new JLabel("");
        this.currentFormLabel = new JLabel("");
        this.sendResultsButton = new JButton("K\u00FCld\u00E9s");

        setLayout(new MigLayout("al center center"));
        inputNumber = components.getSelectedPronouns().size();
        if (components.isParticipioPresentoSelected()) inputNumber++;
        if (components.isParticipioPasadoSelected()) inputNumber++;
        initComponents();
        setVisible(true);

        iteration = 0;
        nextRound();
    }

    private void nextRound() {
        currentVerb = collection.getVerb(iteration);
        currentVerbLabel.setText(currentVerb.getBasic().getInfinitivo());

        if (!components.onlyParticipio()) {
            // TODO: ezt okosabban
            currentFormLabel.setText(components.getSelectedForms().get((int)
                    (Math.random() * components.getSelectedForms().size())).toString());
        }
        this.updateUI();
    }

    private void initComponents() {
        // images
        try {
            checkImg = ImageIO.read(new File("check.png"));
            crossImg = ImageIO.read(new File("cross.png"));
            blankImg = ImageIO.read(new File("blank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // end quiz button
        JButton endQuizButton = new JButton("Kv\u00EDz befejez\u00E9se");
        add(endQuizButton, "span, align right");
        endQuizButton.addActionListener(e -> {
            collection.getMain().switchPanels(this, new StartQuiz(collection.getMain()));
        });

        // set up current verb and form labels
        currentVerbLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        currentFormLabel.setFont(new Font("Verdana", Font.BOLD, 12));

        // presento, pasado labels & textfield
        if (components.isParticipioPresentoSelected()) {
            presentoLabel = new JLabel("Participio presento");
            presentoInput = new JTextField(20);
        }

        if (components.isParticipioPasadoSelected()) {
            pasadoLabel = new JLabel("Participio pasado");
            pasadoInput = new JTextField(20);
        }

        // check / cross label
        ArrayList<JLabel> markLabel = new ArrayList<>();
        for (int i = 0; i < inputNumber; i++) {
            markLabel.add(new JLabel());
            markLabel.get(i).setIcon(new ImageIcon(blankImg));
        }

        // adding elements to the panel
        add(currentVerbLabel, "span, align center");

        if (components.isParticipioPresentoSelected()) {
            add(presentoLabel, "align right");
            add(presentoInput);
            add(markLabel.get(markLabel.size() - 1), "wrap");
        }

        if (components.isParticipioPasadoSelected()) {
            add(pasadoLabel, "align right");
            add(pasadoInput);
            if (components.isParticipioPresentoSelected())
                add(markLabel.get(markLabel.size() - 2), "wrap");
            else add(markLabel.get(markLabel.size() - 1), "wrap");
        }

        // other labels
        if (!components.onlyParticipio()) {
            labels = new ArrayList<JLabel>() {{
                for (Pronoun p : components.getSelectedPronouns()) add(new JLabel(p.toString()));
            }};

            // other textfields
            inputs = new ArrayList<JTextField>() {{
                for (int i = 0; i < labels.size(); i++) add(new JTextField(20));
            }};

            add(currentFormLabel, "span, align center");

            for (int i = 0; i < labels.size(); i++) {
                add(labels.get(i), "align right");
                add(inputs.get(i));
                add(markLabel.get(i), "wrap");
            }
        }

        // send results button
        add(sendResultsButton, "span, align right");
        sendResultsButton.addActionListener(e -> {
            if (components.isParticipioPresentoSelected()) {
                int tempSize = markLabel.size() - 1;
                if (presentoInput.getText().trim().equals(currentVerb.getBasic().getPresento())) {
                    markLabel.get(tempSize).setIcon(new ImageIcon(checkImg));
                } else markLabel.get(tempSize).setIcon(new ImageIcon(crossImg));
            }
            if (components.isParticipioPasadoSelected()) {
                int tempSize = markLabel.size() - 1;
                if (components.isParticipioPresentoSelected()) tempSize--;
                if (pasadoInput.getText().trim().equals(currentVerb.getBasic().getPasado())) {
                    markLabel.get(tempSize).setIcon(new ImageIcon(checkImg));
                } else markLabel.get(tempSize).setIcon(new ImageIcon(crossImg));
            }

            for (int i = 0; i < inputs.size(); i++) {
                String currentInput = inputs.get(i).getText().trim();
                String currentSolution = currentVerb.getForm(currentFormLabel.getText()).get(i);
                if (currentInput.equals(currentSolution)) {
                    markLabel.get(i).setIcon(new ImageIcon(checkImg));
                } else markLabel.get(i).setIcon(new ImageIcon(crossImg));
            }

            iteration++;
            nextRound();
        });
        collection.getMain().getRootPane().setDefaultButton(sendResultsButton);
    }
}
