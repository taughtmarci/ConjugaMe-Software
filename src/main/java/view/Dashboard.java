package view;

import controller.ConfigIO;
import controller.GroupHandler;
import model.VerbQuizComponents;
import model.MenuButton;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Dashboard extends JPanel {
    private final int BUTTON_NUMBER = 6;
    private final String FILE_PATH = "config/preferences.cfg";
    private final MainWindow main;
    private JPanel current;
    private final ConfigIO config;
    private final GroupHandler groupHandler;
    public final VerbQuizComponents comps;

    private JLabel logo;
    private final ImageIcon logoIcon;

    private ArrayList<MenuButton> buttons;

    public Dashboard(MainWindow main) throws IOException {
        this.main = main;
        this.config = new ConfigIO();
        this.groupHandler = new GroupHandler();
        this.buttons = new ArrayList<>();

        // get components from file
        this.comps = config.readComponents(FILE_PATH);
        setLayout(new MigLayout("align center center"));

        // load buttons
        logoIcon = new ImageIcon(ImageIO.read(new File("img/title/title.png")));
        for (int i = 0; i < BUTTON_NUMBER; i++) {
            buttons.add(new MenuButton("dashboard", i + 1));
            buttons.get(i).setActionDashboard(main, this);
        }

        SwingUtilities.invokeLater(this::initComponents);
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
