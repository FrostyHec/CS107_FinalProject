package AI;

import GameLogic.*;

import java.util.ArrayList;

public class Stupid  {


    public static int[][] move(Color color,Chess[][] chess) throws Exception {
        ArrayList<int[][]> canClick = generalUsed.enhancedCanClick(color, chess);
        return generalUsed.randomClick(canClick);
    }
}
