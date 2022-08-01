# Mine Sweeper

## Table Of Contents

1. [Requirements](#req)
2. [How to play](#play)
3. [Rules](#rules)
4. [Create shortcut](#shortcut)
   1. [Change icon](#change-icon)
   2. [Set minimized as default](#minimize)
5. [Updates to come](#updates)
   1. [Coming updates](#coming-updates)
   2. [Implemented updates](#implemented-updates)
6. [Copy right](#copy-right)

## <a name="req" href="#req">Requirements</a>
You need atleast Java 1.8.0 installed on your machine to run this game.
Also, you need git for easy updating, but it can also be downloaded via a zip file.

## <a name="play" href="#play">How to play</a>
First you need to clone this repository.
```
git clone https://github.com/TomasDahlander/minesweeper.git
```
Or download the zip file form link below by clicking on Code and Download Zip
```
https://github.com/TomasDahlander/minesweeper
```
Then simply run the MineSweeper.bat file to run the game.<br>
A terminal will open that can be minimized during play<br>
and will go away once the game is closed down.

You can choose between 3 difficulties in the settings menu.<br>
Easy, Normal and Hard.

You can reveal a cell by clicking on it with the left mouse button.<br>
If it's a number it will simply be revealed.<br>
If it's a blank space the surrounding blank cells will open up<br>
along with the surrounding numbers.

If you click on a mine the game will stop, and you have to reset the game<br>
by clicking on the smiley face or change the difficulty setting which will trigger a reset.

You can mark a cell with a mine by clicking on it with the right mouse button.<br>
This will mark it with a flag. Right click again to remove it.

## <a name="rules" href="#rules">Rules</a>
A number indicates how many mines surround it, can be 1-8.

If you click on a cell marked with a mine the game will stop and<br>
the smiley face will go black to indicate that you have lost.

To win simply reveal all cells except the mines which can be marked with a flag.<br>
When you win the smiley face will be exchanged for a coffee mug.(Java)

When the game is over, either by win or loss the remaining mines will be shown<br>
without the red background.

## <a name="shortcut" href="#shortcut">Create shortcut</a>
To create a shortcut simply right-click on the Minesweeper.bat file<br>
and choose Create shortcut(Skapa genväg).

### <a name="change-icon" href="#change-icon">Change icon</a>
To change the icon for the shortcut simply right-click on the created shortcut<br>
and choose properties(egenskaper) and click on the tab Shortcut(Genväg).<br>
Then click on Change icon...(Byt ikon...) and choose which ever you want.

### <a name="minimize" href="#minimize">Set minimized as default</a>
To start with a minimized terminal window simply choose Minimized(Minimerat)<br>
in the field Run in(Kör i) and the terminal will by default be minimized.

## <a name="updates" href="#updates">Updates to come</a>
To check for updates imply run the Check-for-updates.bat file.
### <a name="coming-updates" href="#coming-updates">Coming updates</a>
* Functionality to save and view online highscore.

### <a name="implemented-updates" href="#implemented-updates">Implemented updates</a>
* Show remaining mines without red background when game is over by win or loss.
* HighScore board.
* Functionality to export highscore to word document.

## <a name="copy-right" href="#copy-right">Copy right</a>
Tomas Dahlander