package GameLogic;

public class Square {
    private int positionX, positionY;

    public Square(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    private Chess chess;

    public Chess getChess() {
        return chess;
    }

    public void setChess(Chess chess) {
        this.chess = chess;
    }
}
