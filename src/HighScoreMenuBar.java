import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2022-08-01 <br>
 * Time: 20:27 <br>
 * Project: minesweeper <br>
 */
public class HighScoreMenuBar extends JMenuBar {

    private final HighScoreWindow highScoreWindow;

    public HighScoreMenuBar(HighScoreWindow highScoreWindow){
        this.highScoreWindow = highScoreWindow;

        // Create menu button
        JMenu difficultyMenu = new JMenu("Difficulty");

        // Creating menu items
        JMenuItem easy = new JMenuItem(GameOptions.EASY);
        JMenuItem normal = new JMenuItem(GameOptions.NORMAL);
        JMenuItem hard = new JMenuItem(GameOptions.HARD);

        // Add menu items to menu
        difficultyMenu.add(easy);
        difficultyMenu.add(normal);
        difficultyMenu.add(hard);

        // Add difficulties menu to menubar
        add(difficultyMenu);

        // Add listeners
        easy.addActionListener(getDifficultyViewListener());
        normal.addActionListener(getDifficultyViewListener());
        hard.addActionListener(getDifficultyViewListener());
    }

    private ActionListener getDifficultyViewListener(){
        return e -> {
            switch (e.getActionCommand()) {
                case GameOptions.EASY:{
                    this.highScoreWindow.printToScoreboard(GameOptions.EASY);
                    break;
                }
                case GameOptions.NORMAL:{
                    this.highScoreWindow.printToScoreboard(GameOptions.NORMAL);
                    break;
                }
                case GameOptions.HARD:{
                    this.highScoreWindow.printToScoreboard(GameOptions.HARD);
                    break;
                }
            }
        };
    }
}
