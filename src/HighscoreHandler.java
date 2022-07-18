import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2022-07-17 <br>
 * Time: 12:57 <br>
 * Project: minesweeper <br>
 */
public class HighscoreHandler {
    public static final String HIGHSCORE_HEADER = " Time   Date";

    private static final HighscoreHandler SINGLE_INSTANCE = new HighscoreHandler();
    private static final String FILEPATH = "highscore.ser";
    private String folderPath = "";
    private List<Highscore> highScores;

    private HighscoreHandler() {
        this.highScores = new ArrayList<>();
    }

    public void setFolderPath(boolean startedByBatFile){
        if(startedByBatFile) folderPath = "../";
        this.createFile();
        this.loadData();
    }

    public static HighscoreHandler getInstance() {
        return SINGLE_INSTANCE;
    }

    public List<Highscore> getHighScores() {
        return highScores;
    }

    public void addScore(Highscore highscore) {
        if(highScores.isEmpty()) {
            highScores.add(highscore);
        }
        else {
            for (int i = 0; i < this.highScores.size(); i++) {
                if (highscore.getTime() < this.highScores.get(i).getTime()) {
                    this.highScores.add(i, highscore);
                    break;
                }
                else if(i == this.highScores.size()-1){
                    this.highScores.add(highscore);
                    break;
                }
            }
        }
        sortHighscoreList();
        removeExcessiveScores(highscore.getDifficulty());
    }

    private void removeExcessiveScores(String difficulty){
        int difficultyCount = 0;
        for(int i = 0; i < highScores.size(); i++){
            if(highScores.get(i).getDifficulty().equalsIgnoreCase(difficulty)){
                difficultyCount++;
                if(difficultyCount > 5) highScores.remove(highScores.get(i));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(folderPath+ FILEPATH)))) {
            this.highScores = (List<Highscore>) in.readObject();
        } catch (EOFException e) {
            System.out.println("End of file reached.");
        } catch (Exception e) {
            System.out.println("Could not load from file.");
            e.printStackTrace();
        }
    }

    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(folderPath+ FILEPATH))) {
            out.writeObject(this.highScores);
            System.out.println("Saved scores to file.");
        } catch (Exception e) {
            System.out.println("Could not write to file.");
            e.printStackTrace();
        }
    }

    private void createFile() {
        File file = new File(folderPath+ FILEPATH);
        try {
            if(file.createNewFile()) System.out.println("File created with the name: " + FILEPATH);
            else System.out.println("File already exists with name: " + FILEPATH);
        } catch (IOException e) {
            System.out.println("Could not create file name: " + FILEPATH);
            e.printStackTrace();
        }
    }

    private void sortHighscoreList(){
        highScores.sort((a,b) -> {
            if(a.getDifficulty().equalsIgnoreCase(b.getDifficulty())){
                return a.getTime()-b.getTime();
            }
            else if(a.getDifficulty().equalsIgnoreCase(GameOptions.EASY)) return -1;
            else if(b.getDifficulty().equalsIgnoreCase(GameOptions.EASY)) return 1;
            else if(a.getDifficulty().equalsIgnoreCase(GameOptions.NORMAL)) return -1;
            else if(b.getDifficulty().equalsIgnoreCase(GameOptions.NORMAL)) return 1;
            else return 0;
        });
    }

    public void exportToWord(){
        String today = LocalDate.now().toString();
        try(PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(folderPath+"Minesweeper " + today + ".doc")))){
            printHighScoresFor(printer,GameOptions.EASY);
            printHighScoresFor(printer,GameOptions.NORMAL);
            printHighScoresFor(printer,GameOptions.HARD);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void printHighScoresFor(PrintWriter printer, String difficulty){
        printer.println(HighscoreHandler.HIGHSCORE_HEADER + " - " + difficulty);
        for (Highscore h : highScores){
            if(h.getDifficulty().equalsIgnoreCase(difficulty)){
                printer.println(h);
            }
        }
        printer.println();
    }

}