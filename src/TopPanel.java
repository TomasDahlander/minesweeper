import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * created by: darja
 * created at: 2022-07-11
 * using: IntelliJ IDEA
 */
public class TopPanel extends JPanel {

    public static final String WHITE_SMILEY = "\u263A";
    public static final String BLACK_SMILEY = "\u263B";

    private final Controller controller;
    private final GameOptions gameOptions;

    private final JLabel mineCount;
    private final JLabel resetFace;
    private final JLabel timeCount;

    private final javax.swing.Timer timer;
    private int seconds;

    public TopPanel(Controller controller, GameOptions gameOptions){
        this.controller = controller;
        this.gameOptions = gameOptions;
        setLayout(new GridLayout(1,3));
        setBorder(new EtchedBorder(EtchedBorder.RAISED,Color.BLACK,null));
        seconds = 0;

        mineCount = new JLabel(String.format("%03d",gameOptions.getAmountOfMinesLeft()),SwingConstants.LEFT);
        resetFace = new JLabel(WHITE_SMILEY,SwingConstants.CENTER);
        timeCount = new JLabel("000",SwingConstants.RIGHT);

        add(mineCount);
        add(resetFace);
        add(timeCount);

        mineCount.setBorder(new EmptyBorder(0,5,2,5));
        resetFace.setBorder(new EmptyBorder(0,0,2,0));
        timeCount.setBorder(new EmptyBorder(0,5,2,5));

        mineCount.setFont(new Font(Font.DIALOG,Font.PLAIN,16));
        resetFace.setFont(new Font(Font.MONOSPACED,Font.BOLD,16));
        timeCount.setFont(new Font(Font.DIALOG,Font.PLAIN,16));

        timeCount.setSize(30,20);

        resetFace.addMouseListener(getResetFaceListener());

        timer = new javax.swing.Timer(1000,l -> {
            seconds++;
            timeCount.setText(String.format("%03d",seconds));
        });
    }

    public void updateMineCount(int newMineCount){
        mineCount.setText(String.format("%03d",newMineCount));
    }

    public void startTime(){
        timer.start();
    }

    public void stopTime(){
        timer.stop();
    }

    public void resetTime(){
        seconds = 0;
        timeCount.setText(String.format("%03d",seconds));
    }

    public void resetMineCount(){
        this.mineCount.setText(String.format("%03d",gameOptions.getAmountOfMinesLeft()));
    }

    public void changeSmiley(String smileyUnicode){
        resetFace.setText(smileyUnicode);
    }

    private MouseAdapter getResetFaceListener(){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.resetGameTo(gameOptions.getDifficulty());
            }
        };
    }

}
