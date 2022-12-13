package Windows.GameArea;

import GameLogic.Player;

public class Transmitter{
    private static FinishedController finishedWindow;

    public static void setFinishedWindow(FinishedController finishedWindow) {
        Transmitter.finishedWindow = finishedWindow;
    }
    public static void setWinUser(boolean isHuman){
        Transmitter.finishedWindow.setWinUser(isHuman);
    }
    public static void setWinUser(String name){
        Transmitter.finishedWindow.setWinUser(name);
    }
}
