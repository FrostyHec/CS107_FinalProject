package AI;

import GameLogic.*;

import java.util.ArrayList;
import java.util.Collections;

public class Stupid {

    public static int[] random(ArrayList<int[]> canClick) throws Exception{
        if(canClick.size() == 0){
            throw new Exception("can't click");
        }
        Collections.shuffle(canClick);
        return canClick.get(0);
    }

    public void canClick(Player player,Chess[][] chess){
        
    }
}
