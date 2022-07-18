

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2022-07-18 <br>
 * Time: 00:36 <br>
 * Project: minesweeper <br>
 */
public class HighScoreWindow extends JFrame {

    private final JTextArea scoreArea = new JTextArea();
    private final List<Highscore> list;

    public HighScoreWindow(){
        this.list = HighscoreHandler.getInstance().getHighScores();
        this.setLayout(new BorderLayout());
        setUpNorthLayout();
        setUpCenterLayout();
        setUpExportButton();

        printToScoreboard(GameOptions.EASY);
        printToScoreboard(GameOptions.NORMAL);
        printToScoreboard(GameOptions.HARD);

        setUpJFrame();
    }

    public void setUpNorthLayout(){
        JLabel header = new JLabel("Highscore");
        header.setFont(new Font(Font.MONOSPACED, Font.BOLD,24));
        JPanel topPanel = new JPanel();
        topPanel.add(header);
        add(topPanel,BorderLayout.NORTH);
    }

    public void setUpCenterLayout(){
        scoreArea.setEditable(false);
        scoreArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN,14));
        add(scoreArea,BorderLayout.CENTER);
    }

    public void setUpExportButton(){
        JPanel bottomPanel = new JPanel();
        JButton exportButton = new JButton("Export to word document");
        exportButton.addActionListener(l -> HighscoreHandler.getInstance().exportToWord());
        bottomPanel.add(exportButton, CENTER_ALIGNMENT);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void printToScoreboard(String difficulty){
        scoreArea.append(HighscoreHandler.HIGHSCORE_HEADER + " - " + difficulty +"\n");
        for(Highscore h : list){
            if(h.getDifficulty().equalsIgnoreCase(difficulty)){
                String line = h + "\n";
                scoreArea.append(line);
            }
        }
        scoreArea.append("\n");
    }

    public void setUpJFrame(){
        this.setLocation(1000,150);
        this.setSize(220,550);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

}

