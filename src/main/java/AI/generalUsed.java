package AI;

import GameLogic.*;

import java.io.Serializable;
import java.util.*;

public class generalUsed{//这个类是一些静态方法的集合，因为基本上AI都要用到，所以拿出来放一起

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
        ArrayList<int[][]> remove = new ArrayList<>();

        it:for(int[][] move : eCanClick){
            if(move.length == 1){
                for(int[]xy : surround(move[0])){//不要翻大棋周围的棋
                    if(chess[xy[0]][xy[1]] != null && chess[xy[0]][xy[1]].isTurnOver() && chess[xy[0]][xy[1]].getRank()>4){
                        remove.add(move);
                        continue it;
                    }
                }
                for(int[]xy : pow(move[0],chess)){
                    if(chess[xy[0]][xy[1]] != null && chess[xy[0]][xy[1]].isTurnOver()
                            && (chess[xy[0]][xy[1]].getRank()>5 || chess[xy[0]][xy[1]].getRank() == 2)){
                        remove.add(move);
                        continue it;
                    }
                }
            }
        }

        for(int[][]i : remove){
            eCanClick.remove(i);
        }

        if(eCanClick.size() == 0){
            eCanClick = canClick(color,chess);
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

        if (xy[0] < 7) {
            int[] temp1 = new int[2];
            if (xy[1] < 3) {
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
            if (xy[1] < 3) {
                int[] temp2 = new int[2];
                temp2[0] = xy[0];
                temp2[1] = xy[1] + 1;
                ans.add(temp2);
            }
        }

        return ans;
    }

    public static boolean mayBeEat(Chess[][] chess,int X,int Y){//在XY处是否可能被吃，注意场上棋子的变化

        if(!chess[X][Y].isTurnOver()){//防止出现一些奇怪的情况
            return false;
        }

        for(int i=0;i<chess.length;i++){
            for(int j=0;j<chess[i].length;j++){
                Chess x = chess[i][j];
                if(x == null || x.getColor() == chess[X][Y].getColor() || !x.isTurnOver()){
                    continue;
                }
                for(int[] coordinate : x.possibleMove(chess,i,j)){
                    if(coordinate[0]==X && coordinate[1]==Y){return true;}
                }
            }
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
                if(x.getColor()==Color.RED) {
                    redScore += x.getRank();
                }
                allScore += x.getRank();
            }
        }
        if(number == 0)
            return 0;
        return (double)(2*redScore - allScore)/number;
    }


    public static boolean isGonnaDie(Chess[][] chess, Color color){
        int s = 95;
        for(Chess[] c : chess){
            for(Chess x : c){
                if(x != null && x.getColor() == color){
                    s -= x.getScore();
                }
            }
        }


        for(int i=0;i<chess.length;i++){
            for(int j=0;j<chess[i].length;j++){
                Chess x = chess[i][j];
                if(x != null && x.isTurnOver() && x.getColor() == color){
                    if(mayBeEat(chess,i,j) && x.getScore() + s >= 60 ){
                        return true;
                    }
                }
            }
        }

        return false;
    }


    public static int[][] dyingMove(Chess[][] chess, Color color){
        ArrayList<int[]> dc = new ArrayList<>();

        int s = 95;
        for(Chess[] c : chess){
            for(Chess x : c){
                if(x != null && x.getColor() == color){
                    s -= x.getScore();
                }
            }
        }

        for(int i=0;i<chess.length;i++){
            for(int j=0;j<chess[i].length;j++){
                Chess x = chess[i][j];
                if(x != null && x.isTurnOver() && x.getColor() == color){
                    if(mayBeEat(chess,i,j) && x.getScore() + s >= 60 ){
                        int[] temp = new int[2];
                        temp[0] = i;
                        temp[1] = j;
                        dc.add(temp);
                    }
                }
            }
        }

        int[][] def = new int[1][2];
        def[0][0] = def[0][1] = -1;

        if(dc.size() >1){
            return Selection.highestOnce(chess,color);
        }else if(dc.size() == 0){
            return def;
        }else {
            Chess[][] virtual = virtualChessBoard(chess);
            if(canRun(virtual,dc.get(0)[0],dc.get(0)[1])){
                return Run(virtual,dc.get(0)[0],dc.get(0)[1]);
            }else{
                return Selection.highestOnce(chess,color);
            }
        }
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

        double Score,max = 0;
        int[][] ans = new int[0][0];

        for(int[][] move : moves){
            Score = 0d;
            Chess[][] virtualChessboard_1 = generalUsed.virtualChessBoard(virtualChessboard);
            if(move.length == 2) {
                if(virtualChessboard_1[move[1][0]][move[1][1]] != null) {
                    if(virtualChessboard_1[move[1][0]][move[1][1]].isTurnOver()) {
                            Score += (virtualChessboard_1[move[1][0]][move[1][1]].getScore()
                                    + virtualChessboard_1[move[1][0]][move[1][1]].getRank());
                    }else{

                        if(color == Color.RED){
                            Score +=( averageScore(virtualChessboard_1) + 2);
                        }else {
                            Score -= averageScore(virtualChessboard_1);
                            Score += 2;
                        }
                    }
                }
                move(virtualChessboard_1,move);
            }else if(move.length == 1){
                move(virtualChessboard_1,move);
            }

            if(Score < 0){
                continue;
            }

            int min = 0;
            for(int i=0;i<virtualChessboard_1.length;i++){
                for(int j=0;j<virtualChessboard_1[i].length;j++){
                    if(virtualChessboard_1[i][j] == null || !virtualChessboard_1[i][j].isTurnOver()) {
                        continue;
                    }
                    if(virtualChessboard_1[i][j].getColor() == color && mayBeEat(virtualChessboard_1,i,j)){
                        if(virtualChessboard_1[i][j].getScore() + virtualChessboard_1[i][j].getRank() > min) {
                            Score += min;
                            min = virtualChessboard_1[i][j].getScore() + virtualChessboard_1[i][j].getRank();
                            Score -= min;
                        }
                    }
                }
            }

            if(Score >= max){
                max = Score;
                ans = move;
            }

        }

        if(max == 0){
            return Selection.highest(virtualChessboard,color);
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


    public static int[][] bestMove2(Chess[][] virtualChessboard,Color color,ArrayList<int[][]> moves){//两步最优
        //比较各个行棋方法最低得分中的最高分，濒死状态另说

        double Score,max = 0;
        int[][] ans = new int[0][0];

        for(int[][] move : moves){
            Score = 0d;
            Chess[][] virtualChessboard_1 = generalUsed.virtualChessBoard(virtualChessboard);
            if(move.length == 2) {
                if(virtualChessboard_1[move[1][0]][move[1][1]] != null) {
                    if(virtualChessboard_1[move[1][0]][move[1][1]].isTurnOver()) {
                        Score += (virtualChessboard_1[move[1][0]][move[1][1]].getScore()
                                + virtualChessboard_1[move[1][0]][move[1][1]].getRank());
                    }else{
                        if (virtualChessboard_1[move[1][0]][move[1][1]].getColor() == oppositeColor(color)) {
                            Score += (virtualChessboard_1[move[1][0]][move[1][1]].getScore()
                                    + virtualChessboard_1[move[1][0]][move[1][1]].getRank());
                        }else{
                            Score -= (virtualChessboard_1[move[1][0]][move[1][1]].getScore()
                                    + virtualChessboard_1[move[1][0]][move[1][1]].getRank());
                        }

                    }
                }
                move(virtualChessboard_1,move);
            }else if(move.length == 1){
                move(virtualChessboard_1,move);
            }

            if(Score < 0){
                continue;
            }

            int min = 0;
            for(int i=0;i<virtualChessboard_1.length;i++){
                for(int j=0;j<virtualChessboard_1[i].length;j++){
                    if(virtualChessboard_1[i][j] == null || !virtualChessboard_1[i][j].isTurnOver()) {
                        continue;
                    }
                    if(virtualChessboard_1[i][j].getColor() == color && mayBeEat(virtualChessboard_1,i,j)){
                        if(virtualChessboard_1[i][j].getScore() + virtualChessboard_1[i][j].getRank() > min) {
                            Score += min;
                            min = virtualChessboard_1[i][j].getScore() + virtualChessboard_1[i][j].getRank();
                            Score -= min;
                        }
                    }
                }
            }

            if(Score >= max){
                max = Score;
                ans = move;
            }

        }

        return ans;
    }


    public static boolean canRun(Chess[][] virtualChessboard,int x,int y){
        int[][] move = new int[2][2];
        move[0][0] = x;
        move[0][1] = y;

        for(int[] xy : virtualChessboard[x][y].possibleMove(virtualChessboard,x,y)){
            Chess[][] v = virtualChessBoard(virtualChessboard);

            if(xy[0] != -1){
                move[1][0] = xy[0];
                move[1][1] = xy[1];
                move(v,move);
            }

            if(xy[0] != -1 && v[xy[0]][xy[1]].isTurnOver() && mayBeEat(v,xy[0],xy[1])){
                return false;
            }
        }

        return true;
    }


    public static int[][] Run(Chess[][] virtualChessboard,int x,int y){
        int[][] ans = new int[2][2];
        ans[0][0] = x;
        ans[0][1] = y;
        ans[1][0] = -1;
        ans[1][1] = -1;

        int[][] t = virtualChessboard[x][y].possibleMove(virtualChessboard,x,y);

        Random r = new Random();
        for(int i=0;i<5;i++) {
            int a = r.nextInt(3);
            int[] temp = new int[2];
            temp[0] = t[0][0];
            temp[1] = t[0][1];
            t[0][0] = t[a + 1][0];
            t[0][1] = t[a + 1][1];
            t[a + 1][0] = temp[0];
            t[a + 1][1] = temp[1];
        }


        for(int[] xy : t){
            Chess[][] v = virtualChessBoard(virtualChessboard);

            if(xy[0] != -1){
                ans[1][0] = xy[0];
                ans[1][1] = xy[1];
                move(v,ans);
            }else{
                continue;
            }

            if(! mayBeEat(v,xy[0],xy[1])){
                return ans;
            }
        }


        return ans;
    }

    public static int[] getRemainingScore(Chess[][] chess,Color color){
        int[] remainingScore = new int[2];

        remainingScore[0] = 95;
        remainingScore[1] = 95;

        for(Chess[] c : chess){
            for(Chess x : c){
                if(x != null){
                    if(x.getColor() == color)
                        remainingScore[0] -= x.getScore();
                    else
                        remainingScore[1] -= x.getScore();
                }
            }
        }

        return remainingScore;
    }

}
