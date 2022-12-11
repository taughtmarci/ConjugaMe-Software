package controller;

import model.*;
import view.MainWindow;
import view.Scores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;

public class ScoresController {
    private final Scores scores;
    private final GroupHandler handler;

    private String[] groupNames;

    private final JTable emptyListDefault;

    public ScoresController(Scores scores) throws IOException {
        this.scores = scores;
        this.handler = new GroupHandler();

        this.groupNames = new String[handler.getGroupNames().size()];
        this.groupNames = handler.getGroupNames().toArray(groupNames);

        DefaultTableModel emptyListModel = new DefaultTableModel(new String[]{"J\u00F3 h\u00EDr!"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        emptyListModel.addRow(new String[]{"Hurr\u00E1, ebben a csoportban egyel\u0151re nincs mit \u00E1tn\u00E9zned!"});
        this.emptyListDefault = new JTable(emptyListModel);
    }

    private ArrayList<Word> getWordRevisionList(String groupName) {
        ArrayList<Word> result = new ArrayList<>();

        Group group = handler.getGroupByName(groupName);
        if (handler.groupValidated(group))
            result = MainWindow.local.processWordRevisionQuery(group);
        else {
            // todo dialogize
            throw new RuntimeException();
        }

        return result;
    }

    public JTable updateWordRevisionList(String groupName) {
        ArrayList<Word> words = getWordRevisionList(groupName);

        if (words.size() > 0) {
            CorrectWords wordsRevision = new CorrectWords(words);
            JTable wordsRevisionList = new JTable(wordsRevision.getData(), wordsRevision.getColumnNames());

            // set model
            DefaultTableModel revisionModel = new DefaultTableModel(wordsRevision.getData(), wordsRevision.getColumnNames()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            wordsRevisionList.setModel(revisionModel);

            return wordsRevisionList;
        } else return emptyListDefault;
    }

    private ArrayList<VerbBasic> getVerbRevisionList(String groupName) {
        ArrayList<VerbBasic> result = new ArrayList<>();

        Group group = handler.getGroupByName(groupName);
        if (handler.groupValidated(group))
            result = MainWindow.local.processVerbRevisionQuery(group);
        else {
            // todo dialogize
            throw new RuntimeException();
        }

        return result;
    }

    public JTable updateVerbRevisionList(String groupName) {
        ArrayList<VerbBasic> verbs = getVerbRevisionList(groupName);

        if (verbs.size() > 0) {
            CorrectVerbs verbsRevision = new CorrectVerbs(verbs);
            JTable verbsRevisionList = new JTable(verbsRevision.getData(), verbsRevision.getColumnNames());

            // set model
            DefaultTableModel revisionModel = new DefaultTableModel(verbsRevision.getData(), verbsRevision.getColumnNames()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            verbsRevisionList.setModel(revisionModel);

            return verbsRevisionList;
        } else return emptyListDefault;
    }

    public String[] getGroupNames() {
        return groupNames;
    }
}
