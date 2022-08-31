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
    private List<HighScore> localHighScores;

    private HighScoreHandler() {
        this.localHighScores = new ArrayList<>();
    }

    public void setFolderPath(boolean startedByBatFile){
        if(startedByBatFile) folderPath = "../";
        this.createFile();
        this.loadData();
    }

    public static HighScoreHandler getInstance() {
        return SINGLE_INSTANCE;
    }

    public List<HighScore> getLocalHighScores() {
        return localHighScores;
    }

    public List<HighScore> getOnlineHighScores(){
        HighScoreClient hsc = new HighScoreClient(Properties.BASE_URL,Properties.GET_ENDPOINT);
        String s = hsc.fetchDataString();
        return HighScoreJsonParser.parseJsonListToHighScoreList(s);
    }

    public void pingOnlineService(){
        HighScoreClient hsc = new HighScoreClient(Properties.BASE_URL,Properties.PING_ENDPOINT);
        String s = hsc.fetchDataString();
        if(s.equalsIgnoreCase("OK")){
            System.out.println("Service is " + s);
        }else{
            System.out.println("Service is not responding or your internet is not working");
        }
    }

    public void saveToOnlineServer(HighScore h) {
        StringBuilder params = new StringBuilder("?");
        params.append(Properties.TIME_PARAM_KEY).append("=").append(h.getTime()).append("&");
        params.append(Properties.DATE_PARAM_KEY).append("=").append(h.getDate()).append("&");
        params.append(Properties.DIFFICULTY_PARAM_KEY).append("=").append(h.getDifficulty()).append("&");
        params.append(Properties.NAME_PARAM_KEY).append("=").append(h.getName());
        System.out.println("Params: " + params);
        HighScoreClient hsc = new HighScoreClient(Properties.BASE_URL, Properties.ADD_ENDPOINT+params);
        String result = hsc.fetchDataString();
        System.out.println(result);
    }

    public void addScore(HighScore highscore) {
        localHighScores.add(highscore);
        sortHighscoreList(this.localHighScores);
        removeExcessiveScores(highscore.getDifficulty());
    }

    private void removeExcessiveScores(String difficulty){
        int difficultyCount = 0;
        for(int i = 0; i < localHighScores.size(); i++){
            if(localHighScores.get(i).getDifficulty().equalsIgnoreCase(difficulty)){
                difficultyCount++;
                if(difficultyCount > 100) localHighScores.remove(localHighScores.get(i));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(folderPath+ FILEPATH)))) {
            this.localHighScores = (List<HighScore>) in.readObject();
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
            out.writeObject(this.localHighScores);
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
        List<HighScore> highScoresToExport = isLocal ? this.localHighScores : new ArrayList<>();
        printer.println(HighScoreHandler.HIGHSCORE_HEADER + " - " + difficulty);
        for (HighScore h : highScoresToExport){
            if(h.getDifficulty().equalsIgnoreCase(difficulty)){
                printer.println(h);
            }
        }
        printer.println();
    }
}