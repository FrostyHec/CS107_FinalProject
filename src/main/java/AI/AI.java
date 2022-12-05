package AI;

import GameLogic.Chess;
import GameLogic.Player;

public class AI extends Player {
    /*
     * 需要注意的地方：
     * 需要考虑优先级的问题
     * 使用递归，要新建一个模拟棋盘，不然会搞乱原来那个
     */

    @Override
    public int[][] move(Chess[][] chess){
        int[][] move = new int[2][2];//不一定是[2][2],如果是翻祺的话就是[1][2]
        return move;
    }
}