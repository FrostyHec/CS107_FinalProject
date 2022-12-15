package AI;

import GameLogic.*;

import java.util.ArrayList;

public class Stupid  {


//    public int[][] move() throws Exception {
//        ArrayList<int[][]> canClick = generalUsed.canClick(super.nowPlay(), super.getChess());
//        return generalUsed.random(canClick);
//    }

    public static int[][] move(Color color,Chess[][] chess) throws Exception {
        ArrayList<int[][]> canClick = generalUsed.enhancedCanClick(color, chess);
        return generalUsed.randomClick(canClick);
    }
}
