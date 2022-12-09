package units;

import GameLogic.*;

import java.util.ArrayList;
import java.util.List;

public class Retract {//悔棋

    public static Game traceBack(Game game) throws Exception{//返回至上一步
        Game g = game;
        g.setBack();
        if(game.getMoves().size() == 0){
            throw new Exception("It's already the first step");
        }
        g.removeLast();
        g = trace(g);
        if(g instanceof aiMode){
            g.setBack();
            if(game.getMoves().size() == 0){
                throw new Exception("It's already the first step");
            }
            g.removeLast();
            g = trace(g);
        }
        return g;
    }

    public static Game trace(Game game){//从头走一遍
        Game g = game;
        List<int[][]> moves = new ArrayList<>();
        for(int j = 0; j <game.getMoves().size(); j++){
            if(game.getMoves().get(j).length == 1){
                int[][] temp = new int[1][2];
                temp[0][0] = game.getMoves().get(j)[0][0];
                temp[0][1] = game.getMoves().get(j)[0][1];
                moves.add(temp);
            }else if(game.getMoves().get(j).length == 2) {
                int[][] temp = new int[2][2];
                temp[0][0] = game.getMoves().get(j)[0][0];
                temp[0][1] = game.getMoves().get(j)[0][1];
                temp[1][0] = game.getMoves().get(j)[1][0];
                temp[1][1] = game.getMoves().get(j)[1][1];
                moves.add(temp);
            }
        }

        g.clearMoves();
        for (int j = 0; j <moves.size(); j++) {
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