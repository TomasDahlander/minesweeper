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
public class HighScoreHandler {
    public static final String HIGHSCORE_HEADER = " Name        Sec   Date";

    private static final HighScoreHandler SINGLE_INSTANCE = new HighScoreHandler();
    private static final String FILEPATH = "highscore.ser";
    private String folderPath = "";
    private List<HighScore> highScores;

    private HighScoreHandler() {
        this.highScores = new ArrayList<>();
    }

    public void setFolderPath(boolean startedByBatFile){
        if(startedByBatFile) folderPath = "../";
        this.createFile();
        this.loadData();
    }

    public static HighScoreHandler getInstance() {
        return SINGLE_INSTANCE;
    }

    public List<HighScore> getHighScores() {
        return highScores;
    }

    public void addScore(HighScore highscore) {
        highScores.add(highscore);
        sortHighscoreList(this.highScores);
        removeExcessiveScores(highscore.getDifficulty());
    }

    private void removeExcessiveScores(String difficulty){
        int difficultyCount = 0;
        for(int i = 0; i < highScores.size(); i++){
            if(highScores.get(i).getDifficulty().equalsIgnoreCase(difficulty)){
                difficultyCount++;
                if(difficultyCount > 50) highScores.remove(highScores.get(i));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(folderPath+ FILEPATH)))) {
            this.highScores = (List<HighScore>) in.readObject();
        } catch (EOFException e) {
            System.out.println("End of file reached.");
        } catch (Exception e) {
            System.out.println("Could not load from file.");
            e.printStackTrace();
        } catch (NoClassDefFoundError e){
            System.out.println("Writing over existing local save file since the object model HighScore has changed");
            saveData();
        }
    }

    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(folderPath + FILEPATH)))) {
            out.writeObject(this.highScores);
            System.out.println("Saved scores to file " + FILEPATH);
        } catch (Exception e) {
            System.out.println("Could not write to file " + FILEPATH);
            e.printStackTrace();
        }
    }

    private void createFile() {
        File file = new File(folderPath + FILEPATH);
        try {
            if(file.createNewFile()) System.out.println("File created with the name: " + FILEPATH);
            else System.out.println("File already exists with name: " + FILEPATH);
        } catch (IOException e) {
            System.out.println("Could not create file name: " + FILEPATH);
            e.printStackTrace();
        }
    }

    public static void sortHighscoreList(List<HighScore> highScores){
        highScores.sort((a,b) -> {
            if(a.getTime() == b.getTime()){
                if(a.getDate().isEqual(b.getDate())) return 0;
                else return a.getDate().isBefore(b.getDate()) ? -1 : 1;
            }
            else return a.getTime()-b.getTime();
        });
    }

    public void exportToWord(boolean isLocal){
        String today = LocalDate.now().toString();
        try(PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(folderPath+"Minesweeper " + today + ".doc")))){
            printHighScoresFor(printer,GameOptions.EASY,isLocal);
            printHighScoresFor(printer,GameOptions.NORMAL,isLocal);
            printHighScoresFor(printer,GameOptions.HARD,isLocal);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void printHighScoresFor(PrintWriter printer, String difficulty, boolean isLocal){
        List<HighScore> highScoresToExport = isLocal ? this.highScores : new ArrayList<>();
        printer.println(HighScoreHandler.HIGHSCORE_HEADER + " - " + difficulty);
        for (HighScore h : highScoresToExport){
            if(h.getDifficulty().equalsIgnoreCase(difficulty)){
                printer.println(h);
            }
        }
        printer.println();
    }

}