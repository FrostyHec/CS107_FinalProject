package AI;

import GameLogic.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Selection {//做剪枝算法的时候可能要用到,也是一堆static方法

    public static double Score(Chess[][] chessboard,int X,int Y){//一步最高得分
        double score = 0;
        for(int[]xy : chessboard[X][Y].possibleMove(chessboard,X,Y)){
            if(xy[0]==-1 || chessboard[xy[0]][xy[1]] == null){
                continue;
            }
            if(chessboard[xy[0]][xy[1]].isTurnOver()){
                score = Math.max(score , chessboard[xy[0]][xy[1]].getScore());
            }else{
                score = Math.max(score,generalUsed.averageScore(chessboard));
            }
        }
        return score;
    }

    public static int[][] highestOnce(Chess[][] virtualChessboard,Color color){
        int[][] move = new int[2][2];
        int score = 0;
        boolean is = false;
        Player p = new Player();
        p.setColor(color);
        for(int[][] xy : generalUsed.canClick(p.getColor(),virtualChessboard)){
            if(xy.length == 2 && virtualChessboard[xy[1][0]][xy[1][1]] != null
                    && virtualChessboard[xy[1][0]][xy[1][1]].getScore() > score
                    && virtualChessboard[xy[1][0]][xy[1][1]].isTurnOver()){
                score = virtualChessboard[xy[1][0]][xy[1][1]].getScore();
                move[0][0] = xy[0][0];
                move[0][1] = xy[0][1];
                move[1][0] = xy[1][0];
                move[1][1] = xy[1][1];
                is = true;
            }
        }
        if(!is)
            move[0][0] = -1;//实际上表示没有能吃的棋
        return move;
    }


    public static double expect(Chess[][] virtualChessboard,int x,int y){
        //两步得分,翻开（x，y）为第一步
        //目前没计算炮盲打的可能
        if(!virtualChessboard[x][y].isTurnOver()){//未翻开的情况，待补充
        }
        double[] firstThree = new double[3];
        return 0;
    }

    public static double expect(Chess[][] virtualChessboard,int X,int Y,int x,int y){
        //两步得分，（X，Y）到（x，y）为第一步
        //目前没计算炮盲打的可能
        double Score = 0;
        if(virtualChessboard[x][y] != null){
            Score = virtualChessboard[x][y].getScore();
        }
        virtualChessboard[x][y] = virtualChessboard[X][Y];
        virtualChessboard[X][Y] = null;

        int[][] xy = highestOnce(virtualChessboard,generalUsed.oppositeColor(virtualChessboard[x][y].getColor()));
        if(xy[0][0] != -1){
            Score -= virtualChessboard[xy[1][0]][xy[1][1]].getScore();
        }

        return Score;
    }

    public static int[][] highest(Chess[][] virtualChessboard,Color color){
        Random random = new Random();
        int a = random.nextInt(5);
        int[][] move = new int[2][2];
        int score = 0;
        boolean is = true;

        ArrayList<int[][]> moves = generalUsed.canClick(color,virtualChessboard);
        Collections.shuffle(moves);
        if(a>0) {
            for (int[][] xy : moves) {
                if (xy.length == 2 &&
                        virtualChessboard[xy[1][0]][xy[1][1]] != null
                        && virtualChessboard[xy[1][0]][xy[1][1]].getScore() > score) {
                    score = virtualChessboard[xy[1][0]][xy[1][1]].getScore();
                    move[0][0] = xy[0][0];
                    move[0][1] = xy[0][1];
                    move[1][0] = xy[1][0];
                    move[1][1] = xy[1][1];
                    is = false;
                }
            }
        }
        if( is ) {//实际上表示没有能吃的棋
            move = find(virtualChessboard,moves).get(0);
        }
        return move;
    }

    public static int[][] bestTwice(Chess[][] virtualChessboard,Color color,ArrayList<int[][]> moves){//还需要优化

        //TODO:濒死状态的玩法
        Random random = new Random();
        int a = random.nextInt(5);

        int[][] move = generalUsed.bestMove(virtualChessboard,color,moves);
        if (move.length == 0){//防止出问题
            return highest(virtualChessboard,color);
        }

        return move;
    }

    public static ArrayList<int[][]> find(Chess[][] virtualChessboard,ArrayList<int[][]> moves){
        ArrayList<int[][]> ans = new ArrayList<>();
        for(int[][] move : moves){
            if(qualified(move,virtualChessboard)){
                ans.add(move);
            }
        }

        if(ans.size() == 0){
            ans = moves;
        }
        return ans;
    }

    private static boolean qualified(int[][] move,Chess[][] virtualChessboard){
        int[] temp = new int[32];
        int x = move[0][0] , y = move[0][1];
        for(int i=0;i<virtualChessboard.length;i++){
            for(int j=0;j<virtualChessboard[i].length;j++){
                if(virtualChessboard[i][j] == null || virtualChessboard[i][j].isTurnOver()){
                    temp[4*i+j] = Math.abs(x-i)+Math.abs(y-j);
                }else{
                    temp[4*i+j] = 0;
                }
            }
        }
        boolean is = false;
        for(int t : temp){
            if(t==1)
                return false;
            else if(t==2)
                is = true;
        }
        return is;
    }
}
