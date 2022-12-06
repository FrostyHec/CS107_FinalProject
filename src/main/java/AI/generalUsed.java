package AI;

import GameLogic.*;

import java.util.ArrayList;
import java.util.Collections;

public class generalUsed {//这个类是一些静态方法的集合，因为基本上AI都要用到，所以拿出来放一起

    public static int[][] randomClick(ArrayList<int[][]> canClick) throws Exception{
        if(canClick.size() == 0){
            throw new Exception("can't click");
        }
        Collections.shuffle(canClick);
        return canClick.get(0);
    }

    public static boolean canWin(Player player,Chess[][] chess){
        for(int[][] x : canClick(player.getColor(),chess)){
            if(x.length==2 && chess[x[1][0]][x[1][1]] != null){
                if(chess[x[1][0]][x[1][1]].getScore() + player.getScore() >= 60){
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<int[][]> canClick(Color color,Chess[][] chess){
        ArrayList<int[][]> canClick = new ArrayList<>();
        for(int i=0;i<chess.length;i++){
            for(int j=0;j<chess[0].length;j++){
                if(chess[i][j] == null)
                    continue;

                if(!chess[i][j].isTurnOver()){
                    int[][] xy = new int[1][2];
                    xy[0][0] = i;
                    xy[0][1] = j;
                    canClick.add(xy);
                }else{
                    if(chess[i][j].getColor() == color && chess[i][j].possibleMove(chess,i,j) != null){
                        for(int[] a : chess[i][j].possibleMove(chess,i,j)) {
                            if(a[0] != -1) {
                                int[][] xy = new int[2][2];
                                xy[0][0] = i;
                                xy[0][1] = j;
                                xy[1][0] = a[0];
                                xy[1][1] = a[1];
                                canClick.add(xy);
                            }
                        }
                    }
                }
            }
        }
        return canClick;
    }

    public static boolean mayBeEat(Chess[][] chess,int X,int Y){//在XY处是否可能被吃，注意场上棋子的变化
        int i = 0;
        if(!chess[X][Y].isTurnOver()){
            return false;
        }//防止出现一些奇怪的情况
        for(Chess[] a :chess){
            int j=0;
            for(Chess x : a){
                if(x == null)continue;
                for(int[] coordinate : x.possibleMove(chess,i,j)){
                    if(coordinate[0]==X && coordinate[1]==Y){return true;}
                }
                j++;
            }
            i++;
        }
        return false;
    }

    public static int[] possibleEat(Chess[][] chess,int X,int Y) throws Exception {//返回可能获得的分数(仅由某个子的行动带来)
        if(chess[X][Y]==null || !chess[X][Y].isTurnOver()){
            throw new Exception("");
        }//防止出现一些奇怪的情况
        int[][] moves = chess[X][Y].possibleMove(chess,X,Y);
        int[] a = new int [4];
        int i = 0;

        for(int[] xy : moves){
            if(xy[0] == -1 || xy[1] == -1){
                a[i] = 0;
            }
            else if(chess[xy[0]][xy[1]]==null)
                a[i] = 0;
            else{
                a[i] = chess[xy[0]][xy[1]].getScore();
            }
            i++;
        }

        return a;
    }


    public static double possibility(Chess[][] chess, Color color,int Rank){//返回大于(不等于)某个子(以Rank形式出现)的可能性
        double po;
        int[] Ch = new int[7];
        int number = 0,need = 0;

        for(Chess[] c : chess){
            for(Chess x : c){
                if( x == null || x.isTurnOver()){
                    continue;
                }
                if(x.getColor()==color)
                    Ch[ x.getRank()-1 ]++;
                number++;
            }
        }
        if(number == 0) return 0d;
        if(Rank == 1 || Rank == 2){
            for(int i=2;i<Ch.length;i++){
                need += Ch[i];
            }
            if(Rank == 1){
                need -= Ch[6];
            }
        }else {
            for (int i = Rank; i < Ch.length; i++) {
                need += Ch[i];
            }
        }

        po = (double) need / number;
        return po;
    }


    public static double averageScore(Chess[][] chess){
        int redScore = 0,allScore = 0,number = 0;
        for(Chess[] c : chess){
            for(Chess x : c){
                if( x == null || x.isTurnOver()){
                    continue;
                }
                number ++;
                if(x.getColor()==Color.RED)
                    redScore += x.getRank();
                allScore += x.getRank();
            }
        }
        if(number == 0)
            return 0;
        return (double)(redScore - allScore)/number;
    }


    public static Chess[][] virtualChessBoard(Chess[][] originChessBoard){//复制一个棋盘供推演//已通过初步测试
        Chess[][] chess = new Chess[8][4];
        for(int i=0;i<originChessBoard.length;i++){
            for(int j=0;j<originChessBoard[i].length;j++){
                if(originChessBoard[i][j] == null)continue;
                chess[i][j] = new Chess(originChessBoard[i][j]);
            }
        }
        return chess;
    }

    public static Color oppositeColor(Color color){
        if(color == Color.RED){
            return Color.BLACK;
        }else{
            return Color.RED;
        }
    }
}
