package AI;

import GameLogic.Chess;
import GameLogic.Color;
import GameLogic.Player;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;

public class AI extends Player {
    private int difficulty;
    public AI(int difficulty){
        this.difficulty = difficulty;
    }
    /*
     * 需要注意的地方：
     * 需要考虑优先级的问题
     * 使用递归，要新建一个模拟棋盘，不然会搞乱原来那个
     */


    @Override
    public int[][] move(Chess[][] chess) throws Exception {//move需要根据AI的等级变化，在这里改进
        int[][] move;//不一定是[2][2],如果是翻棋的话就是[1][2]

        if(difficulty==0){
            move = Stupid.move(getColor(), chess);
            return move;
        }else if(difficulty == 1){//不会逃跑，二级要实现会逃跑
            move = Selection.highest(chess,getColor());
            return move;
        }else if(difficulty == 2){
            //to do
        }

        Chess[][] virtualChessBoard = generalUsed.virtualChessBoard(chess);//此处新开了一个棋盘，以后模拟都用这个棋盘
        ArrayList<int[][]> moves = generalUsed.canClick(super.getColor(),chess);//所有的moves必须从这里来找
        ArrayList<int[][]> ans = new ArrayList<>();

        //据何俞均说，枚举六步不会太慢，但是要合理表达估价函数
        //但因为我的代码比较冗长，不一定能枚举六步
        if(difficulty > 2 && difficulty <= 6){
            ans = enumerationAlgorithm(virtualChessBoard,moves);
            move = generalUsed.randomClick(ans);
            return move;
        }

        int max = -100;
        int[] r = new int[moves.size()];
        for(int i=0;i<r.length;i++){
            r[i] = score(chess,moves.get(i));
        }
        for(int i=0;i<r.length;i++){
            if(r[i] == max){
                ans.add(moves.get(i));
            }else if(r[i] > max){
                ans.clear();
                ans.add(moves.get(i));
            }
        }
        move = generalUsed.randomClick(ans);

        return move;
    }

    private int score(Chess[][] chess,int[][] move){
        int score = 0;//期望得分
        return score;
    }

    private ArrayList<int[][]> enumerationAlgorithm(Chess[][] virtualChessBoard ,ArrayList<int[][]> moves){//枚举算法
        ArrayList<int[][]> ans = new ArrayList<>();
        ArrayList<ArrayList<Integer>> enumResult = new ArrayList<>();
        Chess[][] virtualChessBoard_1 = generalUsed.virtualChessBoard(virtualChessBoard);
        for(int[][] move : moves) {
            for (int i = 0; i < difficulty; i++) {//枚举的步数,这里应该是不能用for写的，得用递归写
            }
        }
        return ans;
    }

    private int virtualClick(Chess[][] virtualChessBoard,int[][] move){
        int Score = 0;
        if(move.length == 1){
            virtualChessBoard[move[0][0]][move[0][1]].TurnOver();
        }else{
            if(virtualChessBoard[move[1][0]][move[1][1]] == null){
                virtualChessBoard[move[1][0]][move[1][1]] = virtualChessBoard[move[0][0]][move[0][1]];
                virtualChessBoard[move[0][0]][move[0][1]] = null;
            } else if(virtualChessBoard[move[1][0]][move[1][1]].isTurnOver()){
                Score += virtualChessBoard[move[1][0]][move[1][1]].getScore();
                virtualChessBoard[move[1][0]][move[1][1]] = virtualChessBoard[move[0][0]][move[0][1]];
                virtualChessBoard[move[0][0]][move[0][1]] = null;
            } else if(!virtualChessBoard[move[1][0]][move[1][1]].isTurnOver()){
                Score += 3;
                virtualChessBoard[move[1][0]][move[1][1]] = virtualChessBoard[move[0][0]][move[0][1]];
                virtualChessBoard[move[0][0]][move[0][1]] = null;
            }
        }
        return Score;
    }

    private int recursionClick(Chess[][] virtualChessBoard , int[][] move , int step , ArrayList<Integer> ans , boolean x , Color color , int score){
        if(step == difficulty){
            return 0;
        }
        Chess[][] virtualChessBoard_1 = generalUsed.virtualChessBoard(virtualChessBoard);
        score = virtualClick(virtualChessBoard_1,move);
        x = !x ;
        Color co = generalUsed.oppositeColor(color);
        for(int[][] mo : generalUsed.canClick(co,virtualChessBoard_1)){

        }
        return score;
    }
}