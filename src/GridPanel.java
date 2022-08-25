import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * created by: darja
 * created at: 2022-07-12
 * using: IntelliJ IDEA
 */
public class GridPanel extends JPanel {

    private final Controller controller;

    private Cell[][] grid;
    private final List<Cell> mineLocations;

    private boolean gameStopped;
    private boolean started;
    private int revealed;
    private int revealToFinish;

    public GridPanel(Controller controller){
        this.controller = controller;
        this.mineLocations = new ArrayList<>();

        setLayout(new GridLayout(this.controller.getGridRows(),this.controller.getGridCols()));
        setBorder(new EtchedBorder(EtchedBorder.RAISED,Color.BLACK,null));

        calculateFinishParameters();
        setUpGrid();
        addMines();
        addNumber();
    }

    // Setup methods
    private void calculateFinishParameters(){
        revealed = 0;
        revealToFinish = (controller.getGridRows() * controller.getGridCols()) - controller.getAmountOfMinesLeft();
    }

    private void setUpGrid(){
        grid = new Cell[controller.getGridRows()][controller.getGridCols()];

        for(int r = 0; r < controller.getGridRows(); r++){
            for(int c = 0; c < controller.getGridCols(); c++){
                grid[r][c] = new Cell(r,c, Cell.BLANK,this);
                this.add(grid[r][c]);
            }
        }
    }

    private void addMines(){
        Random r = new Random();
        int count = 0;
        while(count < controller.getAmountOfMinesLeft()){
            int row = r.nextInt(controller.getGridRows());
            int col = r.nextInt(controller.getGridCols());

            if(!grid[row][col].getSymbolType().equals(Cell.MINE)){
                grid[row][col].setSymbolType(Cell.MINE);
                mineLocations.add(grid[row][col]);
                count++;
            }
        }
    }

    private void addNumber(){
        for(int r = 0; r < controller.getGridRows(); r++){
            for(int c = 0; c < controller.getGridCols(); c++){
                if(!grid[r][c].getSymbolType().equals(Cell.MINE)) addCorrectValueToGrid(r,c);
            }
        }
    }

    private void addCorrectValueToGrid(int row, int col){
        int mines = 0;
        for(int r = row-1; r < row+2; r++){
            for(int c = col-1; c < col+2; c++){
                try{
                    if(grid[r][c].getSymbolType().equals(Cell.MINE)) mines++;
                }catch(IndexOutOfBoundsException ignored){
                    // When indexing outside of board, simply continue iteration
                }
            }
        }
        if(mines > 0) grid[row][col].setValue(String.valueOf(mines));
    }

    // Controller methods
    public void startTime(){
        controller.startTime();
    }
    public void changeSmiley(String smileyUnicode){
        controller.changeSmiley(smileyUnicode);
    }
    public void updateMineCount(int change){
        controller.updateMineCount(change);
    }

    // GridPanel public methods
    public void openConnectedBlankArea(Cell initialCell){
        Queue<Cell> cells = new LinkedList<>();
        cells.add(initialCell);
        while(!cells.isEmpty()){
            Cell polled = cells.poll();
            for(int r = polled.getRow()-1; r < polled.getRow()+2; r++){
                for(int c = polled.getCol()-1; c < polled.getCol()+2; c++){
                    try{
                        Cell cell = this.grid[r][c];
                        if(!cell.isRevealed() && !cell.hasFlag()){
                            if(cell.getSymbolType().equals(Cell.BLANK)){
                                cells.add(cell);
                                cell.showBlank();
                            }else if(cell.getSymbolType().equals(Cell.NUMBER)){
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

    public void increaseRevealed(){
        revealed++;
        if(revealed == revealToFinish){
            gameStopped = true;
            controller.stopTime();
            controller.changeSmiley(TopPanel.COFFEE_ICON);
            revealLeftOverMines();
            controller.saveToHighscore();
            // TODO: 2022-08-25 Implement taking name and to send to online highscore
        }
    }

    public void revealLeftOverMines(){
        for(Cell mine : mineLocations){
            if(!mine.hasFlag() && !mine.isRevealed()){
                mine.showMineForInfo();
            }
        }
    }

    // Getter and setters
    public boolean isGameStopped(){
        return gameStopped;
    }
    public void setGameStopped(boolean gameStopped){
        this.gameStopped = gameStopped;
        controller.stopTime();
    }
    public boolean isStarted() {
        return started;
    }
    public void setStarted(boolean started){
        this.started = started;
    }
}
