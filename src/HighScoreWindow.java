import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2022-07-18 <br>
 * Time: 00:36 <br>
 * Project: minesweeper <br>
 */
public class HighScoreWindow extends JFrame {

    private final JTextArea scoreArea = new JTextArea();
    private final List<HighScore> list;

    public HighScoreWindow(boolean isLocal){
        HighScoreMenuBar highScoreMenuBar = new HighScoreMenuBar(this);
        this.list = isLocal ? getListOfLocalHighScores() : getListOfOnlineHighScores();
        this.setLayout(new BorderLayout());
        setUpNorthLayout();
        setUpCenterLayout();
        setUpExportButton(isLocal);

        setJMenuBar(highScoreMenuBar);

        printToScoreboard(GameOptions.EASY);

        setUpJFrame();
    }

    private List<HighScore> getListOfLocalHighScores(){
        return HighScoreHandler.getInstance().getHighScores();
    }

    private List<HighScore> getListOfOnlineHighScores(){
        // TODO: 2022-08-02 Implement settings object with url and secret to check for online scores.
        // TODO: 2022-08-02 Add a table of highscore to the gaming backend.
        return new ArrayList<>();
    }

    private void setUpNorthLayout(){
        JLabel header = new JLabel("Highscore");
        header.setFont(new Font(Font.MONOSPACED, Font.BOLD,24));
        JPanel topPanel = new JPanel();
        topPanel.add(header);
        add(topPanel,BorderLayout.NORTH);
    }

    private void setUpCenterLayout(){
        scoreArea.setEditable(false);
        scoreArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN,14));
        JScrollPane jsp = new JScrollPane(scoreArea);
        jsp.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        add(jsp,BorderLayout.CENTER);
    }

    private void setUpExportButton(boolean isLocal){
        JPanel bottomPanel = new JPanel();
        JButton exportButton = new JButton("Export to word document");
        exportButton.addActionListener(l -> HighScoreHandler.getInstance().exportToWord(isLocal));
        bottomPanel.add(exportButton, CENTER_ALIGNMENT);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void printToScoreboard(String difficulty){
        clearScoreBoard();
        scoreArea.append(HighScoreHandler.HIGHSCORE_HEADER + " - " + difficulty +"\n");
        for(HighScore h : list){
            if(h.getName() == null || h.getName().equals("")) h.setName("John Doe");
            if(h.getDifficulty().equalsIgnoreCase(difficulty)){
                String line = h + "\n";
                scoreArea.append(line);
            }
        }
        scoreArea.append("\n");
    }

    private void clearScoreBoard(){
        scoreArea.setText("");
    }

    private void setUpJFrame(){
        this.setLocation(1000,150);
        this.setSize(300,600);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

}

