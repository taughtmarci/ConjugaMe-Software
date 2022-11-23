package controller;

import model.Group;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class GroupSelector extends JPanel{
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
        scrollPane.setPreferredSize(changeWidth(150));

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

    public void setSelectedElems(ArrayList<String> elems) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (String elem : elems) {
            Group current = handler.getGroupByName(elem);
            indices.add(current.id());
        }
        list.setSelectedIndices(indices.stream().mapToInt(i -> i).toArray());
    }

}