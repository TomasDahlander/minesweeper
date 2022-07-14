import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * created by: darja
 * created at: 2022-07-11
 * using: IntelliJ IDEA
 */
public class MSMenuBar extends JMenuBar {

    private final Controller controller;

    public MSMenuBar(Controller controller) {
        this.controller = controller;

        // Create meny button
        JMenu menu = new JMenu("Settings");

        // Creating menu items
        JMenuItem easy = new JMenuItem(GameOptions.EASY);
        JMenuItem normal = new JMenuItem(GameOptions.NORMAL);
        JMenuItem hard = new JMenuItem(GameOptions.HARD);

        // Add menu items to menu
        menu.add(easy);
        menu.add(normal);
        menu.add(hard);

        // Add settings menu to menubar
        add(menu);

        // Add action listeners to menu items
        easy.addActionListener(getDifficultySettingListener());
        normal.addActionListener(getDifficultySettingListener());
        hard.addActionListener(getDifficultySettingListener());
    }

    private ActionListener getDifficultySettingListener(){
        return e -> {
            switch (e.getActionCommand()) {
                case GameOptions.EASY -> controller.resetGameTo(GameOptions.EASY);
                case GameOptions.NORMAL -> controller.resetGameTo(GameOptions.NORMAL);
                case GameOptions.HARD -> controller.resetGameTo(GameOptions.HARD);
            }
        };
    }
}
