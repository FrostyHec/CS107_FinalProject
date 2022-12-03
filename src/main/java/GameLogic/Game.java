package GameLogic;

import java.util.*;

public class Game implements java.io.Serializable{


    Player p1,p2;
    public Game(){
        p1 = new Player();
        p2 = new Player();
    }

    public Player getPlayer1(){
        return p1;
    }

    public Player getPlayer2(){
        return p2;
    }
    public Player getHumanPlayer(){
        return p1;
    }
    public Player getAIPlayer(){return p2;}
    Chess[][] chess = new Chess[8][4];
    Chess[][] chess_init = new Chess[8][4];

    public Chess[][] getChess(){
        return chess;
    }


    private int[][] diedChess = new int[2][7];
    //第一个代表死去棋子的颜色，RED为0
    //第二个代表死去棋子的rank-1
    //比如：diedChess[0][1]的值代表死掉红炮的个数

    public int[][] getDiedChess(){
        return diedChess;
    }

    private List <int[][]> moves = new ArrayList<>();

    public List<int[][]> getMoves() {
        return moves;
    }

    public void removeLast(){
        moves.remove(moves.size()-1);
    }

    public void clearMoves(){
        moves.clear();
    }

    private boolean isFirst = true;
    int X,Y;


    public int Click(Player player,int x,int y){//点击

        if(isFirst){//是第一次点击
            if(chess[x][y] == null) return 401;//error code 401:点击了空格子
            if(chess[x][y].TurnOver(p1,p2)){
                p1.changeStatus();p2.changeStatus();
                isFirst = true;
                int[][] move = new int[1][2];
                move[0][0] = x;move[0][1] = y;
                moves.add(move);
                return 0;
            }//未被翻开
            else{
                if(player.getColor() != chess[x][y].getColor())return 1;//error code 1:妄图移动对方的棋子
                else{
                    X = x;Y = y;
                    isFirst = false;
                    return -1;
                }
            }
        }
        else{//是第二次点击
            isFirst = true;
            for(int[] coordinate : chess[X][Y].possibleMove(chess,X,Y)) {
                if (x == coordinate[0] && y == coordinate[1]) {
                    if(chess[x][y] != null){

                        if(chess[x][y].getColor()==Color.RED)
                            diedChess[0][chess[x][y].getRank()-1]++;
                        else
                            diedChess[1][chess[x][y].getRank()-1]++;

                        if(player.getColor()!=chess[x][y].getColor())
                            player.addScore(chess[x][y].getScore());
                        else
                            other(player.getColor()).addScore(chess[x][y].getScore());
                    }
                    chess[x][y] = chess[X][Y];
                    chess[X][Y] = null;
                    p1.changeStatus();
                    p2.changeStatus();
                    int[][] move = new int[2][2];
                    move[0][0] = X;move[0][1] = Y;
                    move[1][0] = x;move[1][1] = y;
                    moves.add(move);
                    //顺便在这里判断一下有没有哪一方赢了的
                    if(p1.isWin())return 101;//code 101:先手那位赢了
                    if(p2.isWin())return 102;//code 102:后手那位赢了
                    return 0;
                }
            }

            //如果不能走，错误类型判断
            if(chess[x][y] == null)
                return 6;//妄图移动炮

            else if(chess[x][y].getColor() == chess[X][Y].getColor())
                return 2;//error code 2: 妄图覆盖自己的棋子

            else if(!chess[x][y].isTurnOver())
                return 3;//error code 3: 不能吃未翻开的棋子

            else if(chess[x][y].getRank() > chess[X][Y].getRank())
                return 4;//error code 4: 妄图蛇吞象

            else if(chess[x][y].getRank()==7 && chess[X][Y].getRank()==1)
                return 5;//error code 5: 将不能吃兵


            else return 404;//error code 404: 其他类型错误
        }

    }


    public void init() {//初始化，完成后可以开始游戏//已测试

        //初始化棋盘
        moves = new ArrayList<>();
        ArrayList<Chess> ch = new ArrayList<>();
        int i=0;
        for(AllChess x : AllChess.values()){
            ch.add(new Chess());
            ch.get(i).setColor(x.getColor());
            ch.get(i).setRank(x.getRank());
            ch.get(i).setScore(x.getScore());
            i++;
        }
        Collections.shuffle(ch);
        i=0;
        for(int m=0;m<chess.length;m++){
            for(int n=0;n<chess[m].length;n++){
                chess[m][n] = ch.get(i);
                chess_init[m][n] = ch.get(i);
                i++;
            }
        }
        chess[0][0].initClick();

        for(int m=0;m<diedChess.length;m++){
            for(int n=0;n< diedChess[m].length;n++){
                diedChess[m][n] = 0;
            }
        }

        //初始化player
        p1.setColor(Color.UNKNOWN);p2.setColor(Color.UNKNOWN);
        p1.setScore(0);p2.setScore(0);
        isFirst = true;

        //p1开始行动
        p1.changeStatus();
    }

    private void init(Chess[][] chess){
        for(Chess[] cc : chess){
            for(Chess c : cc){
                c.initTurnOver();
            }
        }
    }

    public void setBack(){

        init(chess_init);

        //将棋盘放回去
        for(int m=0;m<chess.length;m++){
            for(int n=0;n<chess[m].length;n++){
                chess[m][n] = chess_init[m][n];
            }
        }
        chess[0][0].initClick();

        for(int m=0;m<diedChess.length;m++){
            for(int n=0;n< diedChess[m].length;n++){
                diedChess[m][n] = 0;
            }
        }

        //初始化player
        p1 = new Player();
        p2 = new Player();
        p1.setColor(Color.UNKNOWN);p2.setColor(Color.UNKNOWN);
        p1.setScore(0);p2.setScore(0);
        isFirst = true;

        //从p1开始行动
        p1.changeStatus();
    }


    public Chess getChess(int x,int y){
        return chess[x][y];
    }


    public Player nowPlay(){
        if(p1.getStatus())return p1;
        else return p2;
    }
    private Player other(Color color){
        if(p1.getColor()==color)return p2;
        else return p1;
    }

    public void aiMove(){}

    public int getDifficulty(){
        return 0;
    }

    public int Click(int x,int y){
        return 0;
    }

}
