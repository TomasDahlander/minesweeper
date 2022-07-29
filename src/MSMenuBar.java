import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * created by: darja
 * created at: 2022-07-11
 * using: IntelliJ IDEA
 */
public class MSMenuBar extends JMenuBar {

    private final Controller controller;
    private boolean highScoreIsUp;

    public MSMenuBar(Controller controller) {
        this.controller = controller;

        // Create menu button
        JMenu settingsMenu = new JMenu("Settings");
        JMenu highScoreMenu = new JMenu("Highscore");

        // Creating menu items
        JMenuItem easy = new JMenuItem(GameOptions.EASY);
        JMenuItem normal = new JMenuItem(GameOptions.NORMAL);
        JMenuItem hard = new JMenuItem(GameOptions.HARD);
        JMenuItem localHighScore = new JMenuItem("View local");
        JMenuItem onlineHighScore = new JMenuItem("View online");

        // Add menu items to menu
        settingsMenu.add(easy);
        settingsMenu.add(normal);
        settingsMenu.add(hard);

        highScoreMenu.add(localHighScore);
        highScoreMenu.add(onlineHighScore);

        // Add settings menu to menubar
        add(settingsMenu);
        add(highScoreMenu);

        // Add action listeners to settings menu items
        easy.addActionListener(getDifficultySettingListener());
        normal.addActionListener(getDifficultySettingListener());
        hard.addActionListener(getDifficultySettingListener());

        // Add action listeners to highscore menu items
        localHighScore.addActionListener(getHighScoreListener());
    }

    private ActionListener getDifficultySettingListener(){
        return e -> {
            switch (e.getActionCommand()) {
                case GameOptions.EASY : {controller.resetGameTo(GameOptions.EASY); break;}
                case GameOptions.NORMAL : {controller.resetGameTo(GameOptions.NORMAL); break;}
                case GameOptions.HARD : {controller.resetGameTo(GameOptions.HARD); break;}
            }
        };
    }

    private ActionListener getHighScoreListener(){
        return e -> {
            if(!highScoreIsUp){
                highScoreIsUp = true;
                HighScoreWindow highScoreWindow = new HighScoreWindow();
                highScoreWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        highScoreIsUp = false;
                        e.getWindow().dispose();
                    }
                });
            }
        };
    }
}
