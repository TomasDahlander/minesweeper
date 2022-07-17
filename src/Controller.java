import javax.swing.*;
import java.awt.*;

/**
 * created by: darja
 * created at: 2022-07-11
 * using: IntelliJ IDEA
 */
public class Controller extends JFrame {

    private final TopPanel topPanel;
    private final GameOptions gameOptions;
    private GridPanel gridPanel;
    private final JPanel basePanel;
    private final HighscoreHandler highscoreHandler = HighscoreHandler.getInstance();

    public Controller(){
        gameOptions = new GameOptions(GameOptions.EASY);

        MSMenuBar mSMenuBar = new MSMenuBar(this);
        basePanel = new JPanel(new BorderLayout());
        topPanel = new TopPanel(this,gameOptions);
        gridPanel = new GridPanel(this,this.gameOptions);

        setJMenuBar(mSMenuBar);

        basePanel.add(topPanel,BorderLayout.NORTH);
        basePanel.add(gridPanel,BorderLayout.CENTER);

        add(basePanel);

        setSize(gameOptions.getGridWidth(), gameOptions.getGridHeight());
        setLocation(500,200);
        setTitle("Mine Sweeper");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void stopTime(){
        topPanel.stopTime();
    }

    public void startTime(){
        topPanel.startTime();
    }

    public void changeSmiley(String smileyUnicode){
        topPanel.changeSmiley(smileyUnicode);
    }

    public void saveToHighscore(){
        Highscore h = new Highscore(topPanel.getSeconds(), gameOptions.getDifficulty());
        highscoreHandler.addScore(h);
        highscoreHandler.saveData();
    }

    public void updateMineCount(int change){
        int newMineCount = gameOptions.updateMineCount(change);
        topPanel.updateMineCount(newMineCount);
    }

    public void resetGameTo(String difficulty){
        gameOptions.reCreateInstance(difficulty);
        topPanel.resetTime();
        topPanel.resetMineCount();
        basePanel.removeAll();
        resizeFrameSize();
        topPanel.changeSmiley(TopPanel.WHITE_SMILEY_ICON);
        gridPanel = new GridPanel(this,gameOptions);
        basePanel.add(topPanel,BorderLayout.NORTH);
        basePanel.add(gridPanel,BorderLayout.CENTER);
        basePanel.revalidate();
        basePanel.repaint();
    }

    private void resizeFrameSize(){
        setSize(gameOptions.getGridWidth(),gameOptions.getGridHeight());
    }
}
