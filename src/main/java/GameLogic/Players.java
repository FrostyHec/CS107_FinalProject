package GameLogic;

import java.util.List;

public class Players {
    private List<Player> players;
    private int nowPlay;

    public Players(List<Player> players) {
        this.players = players;
    }

    public void next() {

    }

    public Player nowPlay() {
        return players.get(nowPlay);
    }
}
