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

        int[][] move;

        if(generalUsed.isGonnaDie(virtualChessboard,color)){//会走一些臭不要脸的棋出来
            move = generalUsed.dyingMove(virtualChessboard,color);
            if(move.length == 2){
                if(move[1][0] != -1){
                    return move;
                }
            }else if(move.length == 1){
                return highest(virtualChessboard,color);
            }
        }

        Random random = new Random();
        int a = random.nextInt(2);

        if(a > 0) {
            move = generalUsed.bestMove(virtualChessboard, color, moves);
        }else {
            move = generalUsed.bestMove2(virtualChessboard, color, moves);
        }
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

    private static int move(Chess[][] chessboard,int[][]move){
        if(move.length==1 && !chessboard[move[0][0]][move[0][1]].isTurnOver()){
            chessboard[move[0][0]][move[0][1]].TurnOver();
            return 0;
        }else if(move.length == 2 && chessboard[move[1][0]][move[1][1]] != null){
            int score = chessboard[move[1][0]][move[1][1]].getScore();
            chessboard[move[1][0]][move[1][1]] = chessboard[move[0][0]][move[0][1]];
            chessboard[move[0][0]][move[0][1]] = null;
            return score;
        }
        return 0;
    }


    public static int[][] highLevelAI(Chess[][] chessboard,Color color,int step){
        int[] rs = generalUsed.getRemainingScore(chessboard,color);
        ArrayList<int[][]> canClick = generalUsed.enhancedCanClick(color,chessboard);
        int max = -100;
        int[][] move = highest(chessboard,color);

        for(int[][] xy : canClick) {
            Chess[][] virtualChessboard = generalUsed.virtualChessBoard(chessboard);
            int Score = 0;
            Score += move(virtualChessboard,xy);
            int temp;
            if((temp = minMax(virtualChessboard,color,1,Score,rs[0],rs[1],step))!=-100){
                Score += temp;
            }

            if(Score > max){
                max = Score;
                move = xy;
            }
        }

        return move;
    }

    //剪枝算法(minMax)
    public static int minMax(Chess[][] chessboard,Color color,int step,int Score,
                                 int remainingScore1,int remainingScore2,int quitStep){

        //这玩意前面还要再套一个返回int[][]的
        //remainingScore的第一个指的是己方，第二个指的是对方
        //color始终指的是ai的颜色
        //每次递归前都要step++

        int[][] move = new int[0][0];
        int ans = -100;

        if(step <= quitStep ) {
            if (step % 2 == 0) {

                ArrayList<int[][]> canClick = generalUsed.enhancedCanClick(color,chessboard);
                for(int[][] xy : canClick){
                    Chess[][] virtualChessboard = generalUsed.virtualChessBoard(chessboard);
                    int virtualScore = 0;
//                    virtualScore = Score;
                    virtualScore += move(virtualChessboard,xy);
                    int temp;
                    if((temp = minMax(virtualChessboard,color,++step,virtualScore + Score,remainingScore1,remainingScore2,quitStep))!=-100){
                        virtualScore += temp;
                    }

                    if(virtualScore > ans){
                        ans = virtualScore;
                    }

                }



            } else {
                ans = 100;

                ArrayList<int[][]> canClick = generalUsed.enhancedCanClick(generalUsed.oppositeColor(color),chessboard);
                for(int[][] xy : canClick){
                    Chess[][] virtualChessboard = generalUsed.virtualChessBoard(chessboard);
                    int virtualScore = 0;
//                    virtualScore = Score;
                    virtualScore -= move(virtualChessboard,xy);
                    int temp;
                    if((temp = minMax(virtualChessboard,color,++step,virtualScore + Score,remainingScore1,remainingScore2,quitStep))!=-100){
                        virtualScore += temp;
                    }

                    if(virtualScore < ans){
                        ans = virtualScore;
                    }

                }

            }
        }

        return ans;
    }
}
