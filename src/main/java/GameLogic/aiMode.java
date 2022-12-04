package GameLogic;

import AI.*;

public class aiMode extends Game{
    private int difficulty;
    private boolean isFirst = false;

    Player p1,p2;
    public aiMode(){
        this.difficulty = 1;
        p1 = new Player();
        p2 = new Player();
    }
    public aiMode(int difficulty){
        this.difficulty = difficulty;
        p1 = new Player();
        p2 = new Player();
    }
    public aiMode(int difficulty,boolean isHumanPlayerFirst){
        this.difficulty = difficulty;
        if(isHumanPlayerFirst){
            p1 = new Player();
            p2 = new AI();
        }else{
            p1 = new AI();
            p2 = new Player();
        }
    }

    @Override
    public int Click(int x,int y){
        return Click(getHumanPlayer(),x,y);
    }

    @Override
    public void aiMove() throws Exception {
        int[][] move;
        if(!Chess.isClick()){//如果是第一次，随便翻
            move = Stupid.move(getAIPlayer(), chess);
        }

        try {
            move = Stupid.move(getAIPlayer(), chess);//move需要根据AI的等级变化，在这里改进
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(move.length ==1) {
            Click(getAIPlayer(),move[0][0],move[0][1]);
        }else{
            Click(getAIPlayer(),move[0][0],move[0][1]);
            Click(getAIPlayer(),move[1][0],move[1][1]);
        }
    }

    @Override
    public Player getAIPlayer(){
        if(isFirst)
            return p2;
        else
            return p1;
    }

    @Override
    public Player getHumanPlayer(){
        if(isFirst)
            return p1;
        else
            return p2;
    }

    @Override
    public int getDifficulty(){
        return difficulty;
    }

}