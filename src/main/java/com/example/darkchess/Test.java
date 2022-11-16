package com.example.darkchess;

public class Test {
    public static void main(String[] args) {
        Square[][] board = new Square[8][4];
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                board[i][j]=new Square(i,j);
            }
        }
    }

}
interface Chess{
    boolean turnOver();
    moveKind move(Square square) throws Exception;
    void occupied();
}
abstract class AbstractChess implements Chess{
    private Color color;
    private boolean usable;
    private boolean turnOver;
    private int positionX,positionY;
    private int rank;
    @Override
    public boolean turnOver() {
        return false;
    }

    @Override
    public moveKind move(Square square) throws Exception{
        return moveKind.Move;
    }
    @Override
    public void occupied(){

    }
}
class Square{
    private int positionX,positionY;
    public Square(int positionX,int positionY){
        this.positionX=positionX;
        this.positionY=positionY;
    }
    private Chess chess;
    public Chess getChess(){
        return chess;
    }
    public void setChess(Chess chess){
        this.chess=chess;
    }
}
enum Color{
    RED,BLACK
}
enum moveKind{
    Capture, Move
}


