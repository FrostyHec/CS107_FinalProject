package AI;

import GameLogic.*;
import java.util.*;

public class aiTest {
    public static void main(String[] args) throws Exception {//出问题了
        Scanner in = new Scanner(System.in);
        Game game = new aiMode(5);
        game.init();
        int a = 0;

        while(a != 101 && a != 102) {
            for (int m = 0; m < 8; m++) {
                for (int n = 0; n < 4; n++) {
                    if (game.getChess(m, n) == null) System.out.println("null");
                    else {
                        if (game.getChess(m, n).isTurnOver()) {
                            System.out.print(game.getChess(m, n).getColor());
                            System.out.print("  ");
                            System.out.println(game.getChess(m, n).getRank());
                        } else {
                            System.out.println(Color.UNKNOWN);
                        }
                    }
                }
                System.out.println();
            }


            int x = in.nextInt(), y = in.nextInt();
            a = game.Click(x, y);
            game.aiMove();
            System.out.println(game.getMoves());
        }
    }
}
