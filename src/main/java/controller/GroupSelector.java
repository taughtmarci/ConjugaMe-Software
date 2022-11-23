package controller;

import model.Group;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("unchecked")
public class GroupSelector extends JPanel{
    private final int PIXEL_SIZE = 150;
    private final GroupHandler handler;
    private final JScrollPane scrollPane;
    private final JList list;

    public GroupSelector(GroupHandler handler) {
        this.handler = handler;
        setLayout(new MigLayout("al center center"));

        // creation of the list
        list = new JList(handler.getGroupNames().toArray());
        configureList();
        scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(changeWidth(PIXEL_SIZE));

        add(scrollPane);
        setVisible(true);
    }

    private Dimension changeWidth(int pixels) {
        Dimension d = list.getPreferredScrollableViewportSize();
        d.width = pixels;
        return d;
    }

    private void configureList() {
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(5);

        list.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                }
                else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
    }

    public void setSelectedRows(ArrayList<Group> groups) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (Group group : groups) {
            indices.add(group.id());
        }
        list.setSelectedIndices(indices.stream().mapToInt(i -> i).toArray());
    }

    public ArrayList<Group> getSelectedRows() {
        ArrayList<Group> result = new ArrayList<>();
        for (int i = 0; i < list.getSelectedValuesList().size(); i++) {
            if (!Arrays.equals(list.getSelectedIndices(), new int[]{-1})) {
                result.add(new Group(list.getSelectedIndices()[i], (String) list.getSelectedValuesList().get(i)));
            }
        }
        return result;
    }

}