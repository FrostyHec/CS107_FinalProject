package GameLogic;

public class Chessboard {
    private Square[][] chessboard;
    public Square getSquare(int x,int y){
        return chessboard[x][y];
    }
}
