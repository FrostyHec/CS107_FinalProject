package GameLogic;

import java.util.Scanner;

public class test {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Game Game = new Game();

        Game.init();

        int code = 0;
        while(code == 0){
            for(int m=0;m<Game.chess.length;m++){
                for(int n=0;n<Game.chess[m].length;n++){
                    if(Game.chess[m][n] == null) System.out.println("null");
                    else {
                        if (Game.chess[m][n].isTurnOver()) {
                            System.out.print(Game.chess[m][n].getColor());
                            System.out.print("  ");
                            System.out.println(Game.chess[m][n].getRank());
                        } else {
                            System.out.println(Color.UNKNOWN);
                        }
                    }
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("It's "+Game.nowPlay().getColor()+"'s turn");
            int x = in.nextInt(),y = in.nextInt();
            int[][] mmm = Game.chess[x][y].possibleMove(Game.chess, x,y);
            for(int[] i : mmm){
                for(int in : i){
                    System.out.print(in+" ");
                }
                System.out.println();
            }

            code = Game.Click(Game.nowPlay(), x, y);

            if(code == -1) {
                int m = in.nextInt(),n = in.nextInt();
                code = Game.Click(Game.nowPlay(), m, n);
                //switch code
                switch (code) {
                    case 2 :
                        System.out.println("妄图覆盖自己的棋子");
                        break;
                    case 3 :
                        System.out.println("不能吃未翻开的棋子");
                        break;
                    case 4 :
                        System.out.println("妄图蛇吞象");
                        break;
                    case 5 :
                        System.out.println("将不能吃兵");
                        break;
                    case 404 :
                        System.out.println("其他类型错误,注意检查");
                        break;
                    default:
                        break;
                }
                code = 0;
            }else if(code == 1){
                code = 0;
                System.out.println("DO NOT MOVE OTHER'S CHESS");
            }

        }

    }
}
