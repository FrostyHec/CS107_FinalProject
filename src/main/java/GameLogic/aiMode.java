package GameLogic;

import AI.*;

public class aiMode extends Game{
    int difficulty;

    @Override
    public int getDifficulty(){
        return difficulty;
    }
    Player p1,p2;
    public aiMode(int difficulty){
        this.difficulty = difficulty;
        p1 = new Player();
        p2 = new Player();
    }
    public aiMode(){
        this.difficulty = 1;
        p1 = new Player();
        p2 = new Player();
    }

    @Override
    public void aiMove(){
        int[][] move;
        try {
            move = Stupid.move(p2, chess);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(move.length ==1) {
            Click(p2,move[0][0],move[0][1]);
        }else{
            Click(p2,move[0][0],move[0][1]);
            Click(p2,move[1][0],move[1][1]);
        }
    }



}
