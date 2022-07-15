/**
 * created by: darja
 * created at: 2022-07-12
 * using: IntelliJ IDEA
 */
public class GameOptions {

    public static final String EASY = "Easy";
    public static final String NORMAL = "Normal";
    public static final String HARD = "Hard";

    private static final int MINES_EASY = 10;
    private static final int MINES_NORMAL = 40;
    private static final int MINES_HARD = 99;

    private int gridWidth; // Screen width
    private int gridHeight; // Screen height
    private int gridRows; // Rows to click on
    private int gridCols; // Column to click on
    private int amountOfMinesLeft; // Gets starting value as constant and keeps track of remaining mines
    private String difficulty;

    public GameOptions(String difficulty) {
        reCreateInstance(difficulty);
    }

    public void reCreateInstance(String difficulty){
        switch (difficulty) {
            case EASY : {this.setGridWidth(265).setGridHeight(335).setGridCols(9).setGridRows(9).setAmountOfMinesLeft(MINES_EASY).setDifficulty(EASY); break;}
            case NORMAL : {this.setGridWidth(445).setGridHeight(485).setGridCols(15).setGridRows(15).setAmountOfMinesLeft(MINES_NORMAL).setDifficulty(NORMAL); break;}
            case HARD : {this.setGridWidth(850).setGridHeight(515).setGridCols(30).setGridRows(16).setAmountOfMinesLeft(MINES_HARD).setDifficulty(HARD); break;}
        }
    }

    public int updateMineCount(int change){
        amountOfMinesLeft+=change;
        return amountOfMinesLeft;
    }

    public int getGridWidth() {
        return gridWidth;
    }
    public GameOptions setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
        return this;
    }
    public int getGridHeight() {
        return gridHeight;
    }
    public GameOptions setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
        return this;
    }
    public int getGridRows() {
        return gridRows;
    }
    public GameOptions setGridRows(int gridRows) {
        this.gridRows = gridRows;
        return this;
    }
    public int getGridCols() {
        return gridCols;
    }
    public GameOptions setGridCols(int gridCols) {
        this.gridCols = gridCols;
        return this;
    }
    public int getAmountOfMinesLeft() {
        return amountOfMinesLeft;
    }
    public GameOptions setAmountOfMinesLeft(int amountOfMinesLeft) {
        this.amountOfMinesLeft = amountOfMinesLeft;
        return this;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public GameOptions setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    @Override
    public String toString() {
        return "GameOptions{" +
                "gridWidth=" + gridWidth +
                ", gridHeight=" + gridHeight +
                ", gridRows=" + gridRows +
                ", gridCols=" + gridCols +
                ", amountOfMines=" + amountOfMinesLeft +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}
