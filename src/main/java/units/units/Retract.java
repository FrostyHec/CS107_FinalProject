package units;

import GameLogic.*;

public class Retract {//悔棋
    Game game;
    int step;

    Retract(Game game){
        this.game = game;
        this.step = game.getMoves().size();
    }

    private Game traceBack(int i){//返回i步
        Game g = new Game();
        for(int j=0;j<i;j++){

        }
        return g;
    }

    public Game traceTo(int i){//返回至第i步
        Game g = new Game();
        Chess[][] chess = game.getChess_init();
        if(i<step && i>0) {
            for (int j = 0; j < i; j++) {
                if(game.getPlayer1().getStatus()){

                }
            }
            return g;
        }
        return null;//i的输入值大于走了的步数或者i<0
    }

}
