package units;

import GameLogic.*;

public class unitTest {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.init();
        Serialize.save(game,"D:\\祝超\\各种资源\\#JAVA\\FinalProject\\Deserialization测试样例\\105.ser");
        try {
            game = Deserialize.load("D:\\祝超\\各种资源\\#JAVA\\FinalProject\\Deserialization测试样例\\105.ser");
            System.out.println(game);
        }catch( Exception e ){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
