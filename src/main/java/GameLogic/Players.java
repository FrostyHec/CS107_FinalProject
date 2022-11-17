package GameLogic;

import java.util.List;

public class Players {
    private List<Player> players;
    private int nowPlay;
    public void next(){

    }
    Player nowPlay(){
        return players.get(nowPlay);
    }
}
