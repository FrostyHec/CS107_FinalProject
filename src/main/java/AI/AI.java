package AI;

import GameLogic.Chess;
import GameLogic.Color;
import GameLogic.Player;

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
        int[][] move;//不一定是[2][2],如果是翻祺的话就是[1][2]

        if(difficulty==0){
            move = Stupid.move(getColor(), chess);
            return move;
        }
        Chess[][] virtualChessBoard = generalUsed.virtualChessBoard(chess);//此处新开了一个棋盘，以后模拟都用这个棋盘

        int score = 0;//期望得分
        ArrayList<int[][]> moves = generalUsed.canClick(super.getColor(),chess);
        int[] r = new int[moves.size()];
        move = Stupid.move(getColor(), chess);

        return move;
    }
}