package Windows;


import GameLogic.Game;
import Windows.GameArea.GameArea;
import Windows.Userfiles.RenameUserWindow;
import units.Deserialize;

import java.io.File;

public class Transmitter {
    private static GameArea gameArea;

    private static RenameUserWindow renameUserWindow;

    public static void loadGame(Game game) {
        gameArea.loadGame(game);
    }

    public static void setGameArea(GameArea g) {
        gameArea = g;
    }

    public static void setGameSaveName(String name){
        gameArea.setSaveName(name);
    }
    public static void setPvEDifficulty(int difficulty,boolean isHumanFirst){
        gameArea.setPvE(difficulty,isHumanFirst);
    }

    public static RenameUserWindow getRenameUserWindow() {
        return renameUserWindow;
    }

    public static void setRenameUserWindow(RenameUserWindow renameUserWindow) {
        Transmitter.renameUserWindow = renameUserWindow;
    }
}
