/**
 * created by: darja
 * created at: 2022-08-26
 * using: IntelliJ IDEA
 */
public class Pinger extends Thread{

    @Override
    public void run(){
        HighScoreHandler.getInstance().pingOnlineService();
    }
}