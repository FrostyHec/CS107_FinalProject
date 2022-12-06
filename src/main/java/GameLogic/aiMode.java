package GameLogic;

import AI.*;

public class aiMode extends Game{
    private int difficulty;
    private boolean isFirst = true;

    Player p1,p2;
    public aiMode(){
        this.difficulty = 1;
        p1 = new Player();
        p2 = new AI();
    }
    public aiMode(int difficulty){
        this.difficulty = difficulty;
        p1 = new Player();
        p2 = new AI();
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
        isFirst = isHumanPlayerFirst;
    }

    @Override
    public int Click(int x,int y){
        return Click(getHumanPlayer(),x,y);
    }

    @Override
    public void aiMove() throws Exception {
        int[][] move;
        if(!Chess.isClick()){//如果是第一次，随便翻
            move = Stupid.move(getAIPlayer().getColor(), chess);
        }

        it:try {//move需要根据AI的等级变化，在这里改进

            if(generalUsed.canWin(getAIPlayer(),chess)){//如果能赢，直接赢
                move = Selection.highest(chess,getAIPlayer().getColor());
                break it;
            }

            move = getAIPlayer().move(chess);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        if(move[0][0] != -1 ) {
            if (move.length == 1) {
                Click(getAIPlayer(), move[0][0], move[0][1]);
                return;
            } else {
                Click(getAIPlayer(), move[0][0], move[0][1]);
                Click(getAIPlayer(), move[1][0], move[1][1]);
                return;
            }
        }
        aiMove();
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