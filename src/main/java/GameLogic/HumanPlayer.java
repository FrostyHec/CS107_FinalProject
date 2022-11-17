package GameLogic;

public class HumanPlayer extends Player{
    private PlayerState playerState = PlayerState.choosing;
    public boolean isColorMatch(Color color){
        return false;
    }
}
enum PlayerState{
    choosing,moving
}