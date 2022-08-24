import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2022-07-17 <br>
 * Time: 12:57 <br>
 * Project: minesweeper <br>
 */
public class HighScore implements Serializable {
    private static final long serialVersionUID = 1234567L;

    private final int time;
    private final LocalDate date;
    private final String difficulty;
    private String name;

    public HighScore(int time, LocalDate date, String difficulty, String name){
        this.time = time;
        this.date = date;
        this.difficulty = difficulty;
        this.name = name;
    }

    public HighScore(int time, String date, String difficulty, String name){
        this.time = time;
        this.date = LocalDate.parse(date);
        this.difficulty = difficulty;
        this.name = name;
    }

    public HighScore(int time, String difficulty) {
        this.time = time;
        this.difficulty = difficulty;
        this.date = LocalDate.now();
    }

    @Override
    public String toString() {
        return String.format(" %-10s %4d  %11s", name, time,date.toString());
    }

    public int getTime() {
        return time;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public LocalDate getDate(){
        return this.date;
    }
}


