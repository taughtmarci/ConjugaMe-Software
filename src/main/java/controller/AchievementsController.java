package controller;

import model.*;
import view.MainWindow;
import view.Achievements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;

public class AchievementsController {
    private final Achievements achievements;
    private final GroupHandler handler;

    private String[] groupNames;

    private final JTable emptyVerbListDefault;
    private final JTable emptyWordListDefault;
    private final JTable emptyScoresList;

    public AchievementsController(Achievements achievements) throws IOException {
        this.achievements = achievements;
        this.handler = new GroupHandler();

        this.groupNames = new String[handler.getGroupNames().size()];
        this.groupNames = handler.getGroupNames().toArray(groupNames);

        // default JTable for revision lists
        DefaultTableModel emptyListModel = new DefaultTableModel(new String[]{"J\u00F3 h\u00EDr!"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        emptyListModel.addRow(new String[]{"Hurr\u00E1, ebben a csoportban egyel\u0151re nincs mit \u00E1tn\u00E9zned!"});
        this.emptyVerbListDefault = new JTable(emptyListModel);
        this.emptyWordListDefault = new JTable(emptyListModel);

        // default JTable for scores
        DefaultTableModel emptyScoresModel = new DefaultTableModel(new String[]{"\u00DCres eredm\u00E9nylista!"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        emptyScoresModel.addRow(new String[]{"M\u00E9g nincsenek mentett kv\u00EDz eredm\u00E9nyeid."});
        this.emptyScoresList = new JTable(emptyScoresModel);
    }

    private ArrayList<Word> getWordRevisionList(String groupName) {
        ArrayList<Word> result = new ArrayList<>();

        Group group = handler.getGroupByName(groupName);
        if (handler.groupValidated(group))
            result = MainWindow.local.processWordRevisionQuery(group);
        else {
            MainWindow.dialog.showExceptionDialog("Sz\u00F3t\u00E1r beolvas\u00E1si/valid\u00E1l\u00E1si hiba", "A sz\u00F3t\u00E1rral kapcsolatos konfigur\u00E1ci\u00F3s f\u00E1jlok megs\u00E9r\u00FClhettek.\n" +
                    "Indítsd \u00FAjra az alkalmaz\u00E1st! Ha a probl\u00E9ma tov\u00E1bbra is fenn\u00E1l, k\u00E9rj\u00FCk, pr\u0151b\u00E1ld meg az \u00FAjratelep\u00EDt\u00E9st.", DialogType.ERROR);
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
            wordsRevisionList.setAutoCreateRowSorter(true);
            wordsRevisionList.setModel(revisionModel);

            return wordsRevisionList;
        } else return emptyWordListDefault;
    }

    private ArrayList<VerbBasic> getVerbRevisionList(String groupName) {
        ArrayList<VerbBasic> result = new ArrayList<>();

        Group group = handler.getGroupByName(groupName);
        if (handler.groupValidated(group))
            result = MainWindow.local.processVerbRevisionQuery(group);
        else {
            MainWindow.dialog.showExceptionDialog("Sz\u00F3t\u00E1r beolvas\u00E1si/valid\u00E1l\u00E1si hiba", "A sz\u00F3t\u00E1rral kapcsolatos konfigur\u00E1ci\u00F3s f\u00E1jlok megs\u00E9r\u00FClhettek.\n" +
                    "Indítsd \u00FAjra az alkalmaz\u00E1st! Ha a probl\u00E9ma tov\u00E1bbra is fenn\u00E1l, k\u00E9rj\u00FCk, pr\u0151b\u00E1ld meg az \u00FAjratelep\u00EDt\u00E9st.", DialogType.ERROR);
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
            verbsRevisionList.setAutoCreateRowSorter(true);
            verbsRevisionList.setModel(revisionModel);

            return verbsRevisionList;
        } else return emptyVerbListDefault;
    }

    public JTable updateScoresList(boolean isVerb, boolean isNormal) {
        ArrayList<Score> scores = MainWindow.local.processScoreQuery(isVerb, isNormal);

        if (scores.size() > 0) {
            ScoresList scoresList = new ScoresList(isNormal, isVerb, scores);
            JTable scoresTable = new JTable(scoresList.getData(), scoresList.getColumnNames());

            // set model
            DefaultTableModel scoresModel = new DefaultTableModel(scoresList.getData(), scoresList.getColumnNames()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            scoresTable.setAutoCreateRowSorter(true);
            scoresTable.setModel(scoresModel);

            return scoresTable;
        } else return emptyScoresList;
    }

    public String[] getGroupNames() {
        return groupNames;
    }
}
