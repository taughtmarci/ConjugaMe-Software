package view;

import model.VerbQuizComponents;
import controller.MenuButton;
import model.WordQuizComponents;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Dashboard extends JPanel {
    private final int BUTTON_NUMBER = 6;

    public final VerbQuizComponents verbComps;
    public final WordQuizComponents wordComps;

    private JLabel logo;
    private final ImageIcon logoIcon;

    private final ArrayList<MenuButton> buttons;

    public Dashboard(MainWindow main) throws IOException {
        this.buttons = new ArrayList<>();

        // get components from main class
        this.verbComps = MainWindow.verbComps;
        this.wordComps = MainWindow.wordComps;
        setLayout(new MigLayout("align center center"));

        // load logo and buttons
        String logoVersion = MainWindow.config.isDarkMode() ? "title_grey.png" : "title.png";
        logoIcon = new ImageIcon(ImageIO.read(new File("img/title/" + logoVersion)));
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

        add(initButtonPanel());
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
