/**
 * created by: darja
 * created at: 2022-09-01
 * using: IntelliJ IDEA
 */
public class OnlineHighScoreSender extends Thread{

    private HighScore highScore;

    public OnlineHighScoreSender(HighScore highScore){
        this.highScore = highScore;
    }

    @Override
    public void run(){
        HighScoreHandler.getInstance().saveToOnlineServer(highScore);
    }
}
