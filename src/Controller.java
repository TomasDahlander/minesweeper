import javax.swing.*;
import java.awt.*;

/**
 * created by: darja
 * created at: 2022-07-11
 * using: IntelliJ IDEA
 */
public class Controller extends JFrame {

    private final HighScoreHandler highScoreHandler;
    private final GameOptions gameOptions;
    private final JPanel basePanel;
    private final TopPanel topPanel;
    private GridPanel gridPanel;

    public Controller(boolean startedByBatFile){
        startup();
        highScoreHandler = HighScoreHandler.getInstance();
        highScoreHandler.setFolderPath(startedByBatFile);
        gameOptions = new GameOptions(GameOptions.EASY);

        GameMenuBar mSMenuBar = new GameMenuBar(this);
        basePanel = new JPanel(new BorderLayout());
        topPanel = new TopPanel(this);
        gridPanel = new GridPanel(this);

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

    private void startup(){
        new Pinger().start();
        UIManager.put("OptionPane.cancelButtonText","Cancel");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.okButtonText", "Ok");
        UIManager.put("OptionPane.yesButtonText", "Yes");
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

    public HighScore saveToLocalHighscore(String name){
        HighScore h = new HighScore(topPanel.getSeconds(), gameOptions.getDifficulty(), name);
        highScoreHandler.addScore(h);
        highScoreHandler.saveData();
        return h;
    }

    public void saveToOnlineScore(HighScore highScore) {
        System.out.println("Sending highscore to server");
        highScoreHandler.saveToOnlineServer(highScore);
    }

    public void updateMineCount(int change){
        int newMineCount = gameOptions.updateMineCount(change);
        topPanel.updateMineCount(newMineCount);
    }

    public void resetGameAsIs(){
        this.resetGameTo(gameOptions.getDifficulty());
    }

    public int getAmountOfMinesLeft(){
        return gameOptions.getAmountOfMinesLeft();
    }

    public int getGridRows(){
        return gameOptions.getGridRows();
    }

    public int getGridCols(){
        return gameOptions.getGridCols();
    }

    public void resetGameTo(String difficulty){
        gameOptions.reCreateInstance(difficulty);
        topPanel.resetTime();
        topPanel.resetMineCount();
        basePanel.removeAll();
        resizeFrameSize();
        topPanel.changeSmiley(TopPanel.WHITE_SMILEY_ICON);
        gridPanel = new GridPanel(this);
        basePanel.add(topPanel,BorderLayout.NORTH);
        basePanel.add(gridPanel,BorderLayout.CENTER);
        basePanel.revalidate();
        basePanel.repaint();
    }

    private void resizeFrameSize(){
        setSize(gameOptions.getGridWidth(),gameOptions.getGridHeight());
    }
}
