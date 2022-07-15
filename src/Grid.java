import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * created by: darja
 * created at: 2022-07-12
 * using: IntelliJ IDEA
 */
public class Grid extends JLabel {

    private static final Color BLANK_AREA = new Color(246, 235, 133);
    private static final Color COLOR_ONE = new Color(45, 79, 245);
    private static final Color COLOR_TWO = new Color(11, 129, 4);
    private static final Color COLOR_THREE = new Color(248, 41, 41);
    private static final Color COLOR_FOUR = new Color(2, 28, 148);
    private static final Color COLOR_FIVE = new Color(155, 27, 32);
    private static final Color COLOR_SIX = new Color(68, 154, 109);
    private static final Color COLOR_SEVEN = new Color(162, 100, 93);
    private static final Color COLOR_EIGHT = new Color(0, 0, 0);

    private static final String MINE_ICON = "\u2620";
    private static final String FLAG_ICON = "\u2691";
    private static final String BLANK_ICON = "";

    public static final String MINE = "m";
    public static final String NUMBER = "n";
    public static final String BLANK = "b";

    private final GridPanel gridPanel;
    private final int row;
    private final int col;

    private boolean revealed;
    private boolean showFlag;
    private String symbolType;
    private String value;

    public Grid(int row, int col,String symbolType, GridPanel gridPanel){
        this.gridPanel = gridPanel;
        this.row = row;
        this.col = col;
        this.symbolType = symbolType;
        this.setOpaque(true);
        this.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.BLACK,null));
        this.addDefaultMouseListener();
    }

    private void addDefaultMouseListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(gridPanel.isGameStopped()) return;
                if(!gridPanel.isStarted()) {
                    gridPanel.startTime();
                    gridPanel.setStarted(true);
                }
                if(e.getButton() == 1 && !showFlag) handleLeftClick();
                if(e.getButton() == 3 && !revealed) handleRightClick();
            }
        });
    }

    private void handleRightClick(){
        if(showFlag){
            showFlag = false;
            setText(BLANK_ICON);
            gridPanel.updateMineCount(1);
        }else{
            showFlag = true;
            setText(FLAG_ICON);
            gridPanel.updateMineCount(-1);
        }
    }

    private void handleLeftClick(){
        if(revealed) return;
        switch (symbolType){
            case NUMBER : {showValue(); break;}
            case MINE : {showMine(); break;}
            case BLANK : {
                showBlank();
                gridPanel.openConnectedBlankArea(this);
            }
        }
    }

    private Color getColorByValue(){
        switch (value) {
            case "1" : return COLOR_ONE;
            case "2" : return COLOR_TWO;
            case "3" : return COLOR_THREE;
            case "4" : return COLOR_FOUR;
            case "5" : return COLOR_FIVE;
            case "6" : return COLOR_SIX;
            case "7" : return COLOR_SEVEN;
            case "8" : return COLOR_EIGHT;
        }
        return null;
    }

    public String getSymbolType() {
        return symbolType;
    }
    public void setSymbolType(String symbolType) {
        this.symbolType = symbolType;
    }
    public void setValue(String value) {
        this.value = value;
        this.symbolType = NUMBER;
    }

    public void showBlank(){
        gridPanel.increaseRevealed();
        this.revealed = true;
        this.setBackground(BLANK_AREA);
    }
    public void showValue(){
        gridPanel.increaseRevealed();
        this.revealed = true;
        this.setText(value);
        this.setForeground(getColorByValue());
    }
    public void showMine(){
        this.revealed = true;
        this.setText(MINE_ICON);
        this.setBackground(Color.RED);
        gridPanel.setGameStopped(true);
        gridPanel.changeSmiley(TopPanel.BLACK_SMILEY_ICON);
    }

    public boolean hasFlag() {
        return showFlag;
    }
    public boolean isRevealed() {
        return revealed;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
}
