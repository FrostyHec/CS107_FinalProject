package Windows.GameArea;

public class Transmitter {
    private static GameArea gameArea;

    public static GameArea getGameArea() {
        return gameArea;
    }

    public static void setGameArea(GameArea gA) {
        gameArea = gA;
    }
}
