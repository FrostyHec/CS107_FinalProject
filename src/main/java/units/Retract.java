package units;

import GameLogic.*;
import java.util.List;

public class Retract {//悔棋

    public static Game traceBack(Game game){//返回至上一步
        Game g = game;
        g.setBack();
        g.removeLast();
        g = trace(g);
        return g;
    }

    public static Game trace(Game game){//从头走一遍
        Game g = game;
        List<int[][]> moves = g.getMoves();
        g.clearMoves();
        for (int j = 0; j <game.getMoves().size(); j++) {
            if(moves.get(j).length == 1){
                g.Click(g.nowPlay(),moves.get(j)[0][0],moves.get(j)[0][1]);
            }
            else if(moves.get(j).length == 2){
                g.Click(g.nowPlay(),moves.get(j)[0][0],moves.get(j)[0][1]);
                g.Click(g.nowPlay(),moves.get(j)[1][0],moves.get(j)[1][1]);
            }
        }
        return g;
    }

}