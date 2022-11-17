package GameLogic;

public class HumanPlayer extends Player{
    private PlayerState playerState = PlayerState.choosing;
    public boolean isColorMatch(Color color){
        return false;
    }
    public PlayerState getPlayerState(){
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
}
