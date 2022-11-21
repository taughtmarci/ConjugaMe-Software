package controller;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class GroupSelector extends JPanel{
    private final String FILE_NAME = "config/groups.cfg";
    private BufferedReader br;

    private final JList<Object> list;
    private final JScrollPane scrollPane;

    public GroupSelector() {
        setLayout(new MigLayout("al center center"));
        File file = new File(FILE_NAME);

        InputStream is;
        try {
            is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            this.br = new BufferedReader(isr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO normálisan dialogban
            System.out.println("hiba: " + e);
        }

        ArrayList<String> lines = new ArrayList<>();
        String line;

        try {
            if (br != null) {
                line = br.readLine();
                while (line != null) {
                    lines.add(line);
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            // TODO normálisan dialogban
            System.out.println("hiba: " + e);
        }

        // creation of the list
        list = new JList<>(lines.toArray());
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

        int[] select = {0, 1};
        list.setSelectedIndices(select);
    }

    public void setSelectedElems(ArrayList<String> elems) {

    }

}