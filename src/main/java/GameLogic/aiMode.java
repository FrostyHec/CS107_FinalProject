package GameLogic;

import AI.*;

public class aiMode extends Game{
    private int difficulty;
    private boolean isHunanPlayerFirst = true;

    public aiMode(){
        this.difficulty = 1;
        p1 = new Player();
        p2 = new AI(difficulty);
        super.init();
    }
    public aiMode(int difficulty){
        this.difficulty = difficulty;
        p1 = new Player();
        p2 = new AI(difficulty);
        super.init();
    }
    public aiMode(int difficulty,boolean isHumanPlayerFirst){
        this.difficulty = difficulty;
        if(isHumanPlayerFirst){
            p1 = new Player();
            p2 = new AI(difficulty);
        }else{
            p1 = new AI(difficulty);
            p2 = new Player();
        }
        this.isHunanPlayerFirst = isHumanPlayerFirst;
        super.init();
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
            Click(getAIPlayer(), move[0][0], move[0][1]);
            return;
        }

        it:try {

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
        if(isHunanPlayerFirst)
            return p2;
        else
            return p1;
    }

    @Override
    public Player getHumanPlayer(){
        if(isHunanPlayerFirst)
            return p1;
        else
            return p2;
    }

    @Override
    public int getDifficulty(){
        return difficulty;
    }

}