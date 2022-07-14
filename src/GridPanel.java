import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * created by: darja
 * created at: 2022-07-12
 * using: IntelliJ IDEA
 */
public class GridPanel extends JPanel {

    private final Controller controller;
    private final GameOptions gameOptions;

    private Grid[][] grid;

    private boolean dead;
    private boolean started;

    public GridPanel(Controller controller,GameOptions gameOptions){
        this.controller = controller;
        this.gameOptions = gameOptions;

        setLayout(new GridLayout(this.gameOptions.getGridRows(),this.gameOptions.getGridCols()));
        setBorder(new EtchedBorder(EtchedBorder.RAISED,Color.BLACK,null));

        setUpGrid();
        addMines();
        addNumber();
    }

    public boolean isDead(){
        return dead;
    }
    public void setDead(boolean dead){
        this.dead = dead;
        controller.stopTime();
    }
    public boolean isStarted() {
        return started;
    }
    public void setStarted(boolean started){
        this.started = started;
    }
    public void startTime(){
        controller.startTime();
    }
    public void changeSmiley(String smileyUnicode){
        controller.changeSmiley(smileyUnicode);
    }

    public void updateMineCount(int change){
        controller.updateMineCount(change);
    }

    public void openConnectedBlankArea(Grid initialCell){
        Queue<Grid> grids = new LinkedList<>();
        grids.add(initialCell);
        while(!grids.isEmpty()){
            Grid polled = grids.poll();
            for(int r = polled.getRow()-1; r < polled.getRow()+2; r++){
                for(int c = polled.getCol()-1; c < polled.getCol()+2; c++){
                    try{
                        Grid cell = grid[r][c];
                        if(!cell.isPressed() && !cell.hasFlag()){
                            if(cell.getSymbolType().equals(Grid.BLANK)){
                                grids.add(cell);
                                cell.showBlank();
                            }else if(cell.getSymbolType().equals(Grid.NUMBER)){
                                cell.showValue();
                            }
                        }
                    }catch(IndexOutOfBoundsException ignored){
                        // When indexing outside of board, simply continue iteration
                    }
                }
            }
        }
    }

    private void setUpGrid(){
        grid = new Grid[gameOptions.getGridRows()][gameOptions.getGridCols()];

        for(int r = 0; r < gameOptions.getGridRows(); r++){
            for(int c = 0; c < gameOptions.getGridCols(); c++){
                grid[r][c] = new Grid(r,c,Grid.BLANK,this);
                this.add(grid[r][c]);
            }
        }
    }

    private void addMines(){
        Random r = new Random();
        int count = 0;
        while(count < gameOptions.getAmountOfMinesLeft()){
            int row = r.nextInt(gameOptions.getGridRows());
            int col = r.nextInt(gameOptions.getGridCols());

            if(!grid[row][col].getSymbolType().equals(Grid.MINE)){
                grid[row][col].setSymbolType(Grid.MINE);
                count++;
            }
        }
    }

    private void addNumber(){
        for(int r = 0; r < gameOptions.getGridRows(); r++){
            for(int c = 0; c < gameOptions.getGridCols(); c++){
                if(!grid[r][c].getSymbolType().equals(Grid.MINE)) addCorrectValueToGrid(r,c);
            }
        }
    }

    private void addCorrectValueToGrid(int row, int col){
        int mines = 0;
        for(int r = row-1; r < row+2; r++){
            for(int c = col-1; c < col+2; c++){
                try{
                    if(grid[r][c].getSymbolType().equals(Grid.MINE)) mines++;
                }catch(IndexOutOfBoundsException ignored){
                    // When indexing outside of board, simply continue iteration
                }
            }
        }
        if(mines > 0) grid[row][col].setValue(String.valueOf(mines));
    }
}
