package units;

import GameLogic.*;

import java.util.ArrayList;
import java.util.List;

public class Retract {//悔棋

    public static Game traceBack(Game game) throws Exception{//返回至上一步
        game.setBack();
        if(game.getMoves().size() == 0){
            throw new Exception("It's already the first step");
        }
        game.removeLast();
        trace(game);
        if(game instanceof aiMode){
            game.setBack();
            if(game.getMoves().size() == 0){
                throw new Exception("It's already the first step");
            }
            game.removeLast();
            trace(game);
        }
        return game;
    }

    public static Game trace(Game game){//从头走一遍
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

        game.clearMoves();
        for (int[][] move : moves) {
            if (move.length == 1) {
                game.Click(game.nowPlay(), move[0][0], move[0][1]);
            } else if (move.length == 2) {
                game.Click(game.nowPlay(), move[0][0], move[0][1]);
                game.Click(game.nowPlay(), move[1][0], move[1][1]);
            }
        }
        return game;
    }

}