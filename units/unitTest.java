package units;

import GameLogic.*;

public class unitTest {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.init();
        Serialize.save(game,"src\\101.ser");
        try {
            game = Deserialize.load("src\\101.ser");
            System.out.println(game);
        }catch( Exception e ){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}