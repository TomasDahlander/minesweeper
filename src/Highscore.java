import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2022-07-17 <br>
 * Time: 12:57 <br>
 * Project: minesweeper <br>
 */
public class Highscore implements Serializable {
    private static final long serialVersionUID = 1234567L;

    private final int time;
    private final LocalDate date;
    private final String difficulty;

    public Highscore(int time, String difficulty) {
        this.time = time;
        this.difficulty = difficulty;
        this.date = LocalDate.now();
    }

    @Override
    public String toString() {
        return String.format(" %4d  %11s", time,date.toString());
    }

    public int getTime() {
        return time;
    }
    public String getDifficulty() {
        return difficulty;
    }

}


