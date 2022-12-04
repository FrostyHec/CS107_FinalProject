package AI;

import GameLogic.*;

public class Selection {

    public static double Score(Chess[][] virtualChessboard,int X,int Y){//一步最高得分
        Chess[][] chessboard = virtualChessboard;
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
        for(int[][] xy : generalUsed.canClick(p,virtualChessboard)){
            if(virtualChessboard[xy[1][0]][xy[1][1]] != null
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
            move[0][0] = -1;
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
        if(xy[0][0] == -1);
        else{
            Score -= virtualChessboard[xy[1][0]][xy[1][1]].getScore();
        }

        return Score;
    }
}
