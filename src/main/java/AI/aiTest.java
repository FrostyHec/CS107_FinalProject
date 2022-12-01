package AI;

import GameLogic.*;

public class aiTest {
    public static void main(String[] args) {
        Game game = new aiMode(5);
        game.init();
        int x = game.getDifficulty();
        game.Click(game.nowPlay(),1,1);
        System.out.println(game.getMoves());
        System.out.println(x);
    }
}
