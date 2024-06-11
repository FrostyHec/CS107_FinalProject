package units;

import GameLogic.*;
import java.util.ArrayList;
import java.util.List;

public class Play {
    List<int[][]> moves = new ArrayList<>();
    Game game;
    int step;
    final int step1;

    public Play(Game game){//构造方法
        this.game = game;

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
        step1 = game.getMoves().size();
        step = 0;

        game.setBack();
        game.clearMoves();
    }

    public boolean move(){//具体方法
        if (step == step1){
            return false;
        }
        if(moves.get(step).length == 1){
            game.Click(game.nowPlay(),moves.get(step)[0][0],moves.get(step)[0][1]);
        }
        else if(moves.get(step).length == 2){
            game.Click(game.nowPlay(),moves.get(step)[0][0],moves.get(step)[0][1]);
            game.Click(game.nowPlay(),moves.get(step)[1][0],moves.get(step)[1][1]);
        }
        step++;
        return true;
    }
}
