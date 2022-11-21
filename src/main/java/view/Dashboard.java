package view;

import model.MenuButton;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Dashboard extends JPanel {
    private final int BUTTON_NUMBER = 6;
    private final MainWindow main;
    private JPanel current;

    private JLabel logo;
    private final ImageIcon logoIcon;

    private ArrayList<MenuButton> buttons;

    public Dashboard(MainWindow main) {
        this.main = main;
        this.buttons = new ArrayList<>();
        setLayout(new MigLayout("align center center"));

        try {
            logoIcon = new ImageIcon(ImageIO.read(new File("img/title/title.png")));
            for (int i = 0; i < BUTTON_NUMBER; i++) {
                buttons.add(new MenuButton("dashboard", i + 1));
                buttons.get(i).setActionDashboard(main, this);
            }
        } catch(IOException e) {
            // TODO dialog
            throw new RuntimeException(e);
        }

        initComponents();
    }

    private void initComponents() {
        logo = new JLabel();
        logo.setIcon(logoIcon);
        add(logo, "align center, span");

        this.add(initButtonPanel());
    }

    private JPanel initButtonPanel() {
        JPanel buttonPanel = new JPanel(new MigLayout("align center center"));

        for (int i = 0; i < BUTTON_NUMBER; i++) {
            String constraint = ((i + 1) % 3 == 0) ? "wrap" : "";
            buttonPanel.add(buttons.get(i), constraint);
        }

        return buttonPanel;
    }
}
