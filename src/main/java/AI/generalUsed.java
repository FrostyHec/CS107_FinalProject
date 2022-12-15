package AI;

import GameLogic.*;

import java.util.ArrayList;
import java.util.Collections;

public class generalUsed {//这个类是一些静态方法的集合，因为基本上AI都要用到，所以拿出来放一起

    public static int[][] randomClick(ArrayList<int[][]> canClick) throws Exception{
        if(canClick.size() == 0){
            throw new Exception("can't click");
        }
        int x = 0;
        do {
            Collections.shuffle(canClick);
            x++;
        }while(canClick.get(0).length==1 && x<2);

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
        Collections.shuffle(canClick);
        return canClick;
    }

    
    public static ArrayList<int[][]> enhancedCanClick(Color color,Chess[][] chess){//舍弃一些非常无聊的子,用于枚举时进行优化
        ArrayList<int[][]> eCanClick = canClick(color,chess);

        it:for(int[][] move : eCanClick){
            if(move.length == 1){
                for(int[]xy : surround(move[0])){//不要翻大棋周围的棋
                    if(chess[xy[0]][xy[1]] != null && chess[xy[0]][xy[1]].isTurnOver() && chess[xy[0]][xy[1]].getRank()>4){
                        eCanClick.remove(move);
                        continue it;
                    }
                }
                for(int[]xy : pow(move[0],chess)){
                    if(chess[xy[0]][xy[1]] != null && chess[xy[0]][xy[1]].isTurnOver()
                            && (chess[xy[0]][xy[1]].getRank()>5 || chess[xy[0]][xy[1]].getRank() == 2)){
                        eCanClick.remove(move);
                        continue it;
                    }
                }
            }
        }

        return eCanClick;
    }

    //返回炮位
    public static ArrayList<int[]> pow(int[] xy,Chess[][] chess){
        ArrayList<int[]> ans = new ArrayList<>();
        int x = xy[0],y = xy[1];
        int[] a = new int[2];

        int i = x+1;
        if(i<7){
            for (; chess[i][y] == null; i++)
                if (i == 7) break;
            if (i != 7){
                i++;
                for (; chess[i][y] == null; i++)
                    if (i == 7) {
                        i++;
                        break;
                    }
                if (i != 8){
                    a[0] = i;
                    a[1] = y;
                    ans.add(a);
                }
            }
        }

        i = x-1;
        if(i>0){
            for (; chess[i][y] == null; i--)
                if (i == 0) break;
            if (i != 0){
                i--;
                for (; chess[i][y] == null; i--)
                    if (i == 0) {
                        i--;
                        break;
                    }
                if (i != -1){
                    a[0] = i;
                    a[1] = y;
                    ans.add(a);
                }
            }
        }

        i = y+1;
        if(i<3){
            for (; chess[x][i] == null; i++)
                if (i == 3) break;
            if (i != 3){
                i++;
                for (; chess[x][i] == null; i++)
                    if (i == 3) {
                        i++;
                        break;
                    }

                if (i != 4){
                    a[0] = x;
                    a[1] = i;
                    ans.add(a);
                }
            }
        }

        i = y-1;
        if(i > 0){
            for (; chess[x][i] == null; i--)
                if (i == 0) break;
            if (i != 0){
                i--;
                for (; chess[x][i] == null; i--)
                    if (i == 0) {
                        i--;
                        break;
                    }
                if (i != -1){
                    a[0] = x;
                    a[1] = i;
                    ans.add(a);
                }
            }
        }

        return ans;
    }


    //返回棋盘上曼哈顿距离为1的点
    public static ArrayList<int[]> surround(int[] xy){
        ArrayList<int[]> ans = new ArrayList<>();

        if(xy[0] > 0){
            int[] temp1 = new int[2];
            if(xy[1] > 0){
                int[] temp2 = new int[2];
                temp1[0] = xy[0] - 1; temp1[1] = xy[1];
                temp2[0] = xy[0]; temp2[1] = xy[1] - 1;
                ans.add(temp1);
                ans.add(temp2);
            }else{
                temp1[0] = xy[0] - 1; temp1[1] = xy[1];
                ans.add(temp1);
            }
        }else {
            if (xy[1] > 0) {
                int[] temp2 = new int[2];
                temp2[0] = xy[0];
                temp2[1] = xy[1] - 1;
                ans.add(temp2);
            }
        }

        if (xy[0] < 8) {
            int[] temp1 = new int[2];
            if (xy[1] < 4) {
                int[] temp2 = new int[2];
                temp1[0] = xy[0] + 1;
                temp1[1] = xy[1];
                temp2[0] = xy[0];
                temp2[1] = xy[1] + 1;
                ans.add(temp1);
                ans.add(temp2);
            } else {
                temp1[0] = xy[0] + 1;
                temp1[1] = xy[1];
                ans.add(temp1);
            }
        } else {
            if (xy[1] < 4) {
                int[] temp2 = new int[2];
                temp2[0] = xy[0];
                temp2[1] = xy[1] + 1;
                ans.add(temp2);
            }
        }

        return ans;
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


    public static Chess[][] virtualChessBoard(Chess[][] originChessBoard){//复制一个棋盘供推演
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

    public static int[][] bestMove(Chess[][] virtualChessboard,Color color,ArrayList<int[][]> moves){//两步最优
        //比较各个行棋方法最低得分中的最高分，濒死状态另说

        double Score = 0,max = 0;
        int[][] ans = new int[0][0];

        for(int[][] move : moves){
            Chess[][] virtualChessboard_1 = generalUsed.virtualChessBoard(virtualChessboard);
            if(move.length == 2) {
                if(virtualChessboard_1[move[1][0]][move[1][1]] != null) {
                    Score += (virtualChessboard_1[move[1][0]][move[1][1]].getScore()
                            + virtualChessboard_1[move[1][0]][move[1][1]].getRank());
                }
                move(virtualChessboard_1,move);
            }else if(move.length == 1){
                move(virtualChessboard_1,move);
            }

            int min1 = 0, min2 = 0;
            for(int i=0;i<virtualChessboard_1.length;i++){
                for(int j=0;j<virtualChessboard_1[i].length;j++){
                    if(virtualChessboard_1[i][j] == null || !virtualChessboard_1[i][j].isTurnOver()) {
                        continue;
                    }
                    if(virtualChessboard_1[i][j].getColor() == color && mayBeEat(virtualChessboard_1,i,j)){
                        if(virtualChessboard_1[i][j].getScore() + virtualChessboard_1[i][j].getRank() > min1) {
                            Score += min2;
                            min2 = min1;
                            min1 = virtualChessboard_1[i][j].getScore() + virtualChessboard_1[i][j].getRank();
                            Score -= min1;
                        }
                    }
                }
            }

            if(Score > max){
                max = Score;
                ans = move;
            }

        }

        return ans;
    }

    public static void move(Chess[][] chessboard,int[][]move){
        if(move.length==1 && !chessboard[move[0][0]][move[0][1]].isTurnOver()){
            chessboard[move[0][0]][move[0][1]].TurnOver();
        }else if(move.length == 2){
            chessboard[move[1][0]][move[1][1]] = chessboard[move[0][0]][move[0][1]];
            chessboard[move[0][0]][move[0][1]] = null;
        }
    }
}
