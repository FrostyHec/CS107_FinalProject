package GameLogic;



public class aiMode extends Game{
    int difficulty;
    Player p1,p2;
    aiMode(int difficulty){
        this.difficulty = difficulty;
        p1 = new Player();
        p2 = new Player();
    }
    public aiMode(){}



}
