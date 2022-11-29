package AI;

import GameLogic.*;

import java.util.ArrayList;

public class Stupid extends aiMode {

    Stupid(){
        super();
    }

    public int[][] move() throws Exception {
        ArrayList<int[][]> canClick = generalUsed.canClick(super.nowPlay(), super.getChess());
        return generalUsed.random(canClick);
    }
}
