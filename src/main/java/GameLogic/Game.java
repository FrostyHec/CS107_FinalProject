package GameLogic;

import java.util.*;

public class Game {
    Player p1 = new Player(),p2 = new Player();
    Chess[][] chess = new Chess[8][4];

    static boolean isFirst = true;
    private int X,Y;


    int Click(Player player,int x,int y){//点击
        if(isFirst){//是第一次点击
            if(chess[x][y].TurnOver(p1,p2)){
                p1.changeStatus();p2.changeStatus();
                isFirst = true;
                return 0;
            }//未被翻开
            else{
                if(player.getColor() != chess[x][y].getColor())return 1;//error code 1:妄图移动对方的棋子
                else{
                    X = x;Y = y;
                    isFirst = false;
                    return -1;
                }
            }
        }
        else{//是第二次点击
            isFirst = true;
            for(int[] coordinate : chess[X][Y].possibleMove(chess,X,Y)) {
                if (x == coordinate[0] && y == coordinate[1]) {
                    player.addScore(chess[x][y].getScore());
                    chess[x][y] = chess[X][Y];
                    chess[X][Y] = null;
                    p1.changeStatus();
                    p2.changeStatus();
                    return 0;
                }
            }

            //如果不能走，错误类型判断
            if(chess[x][y].getColor() == chess[X][Y].getColor())
                return 2;//error code 2: 妄图覆盖自己的棋子

            else if(!chess[x][y].isTurnOver())
                return 3;//error code 3: 不能吃未翻开的棋子

            else if(chess[x][y].getRank() > chess[X][Y].getRank())
                return 4;//error code 4: 妄图蛇吞象

            else if(chess[x][y].getRank()==7 && chess[X][Y].getRank()==1)
                return 5;//error code 5: 将不能吃兵

            else return 6;//error code 6: 其他类型错误
        }

    }


    public void init() {//初始化，完成后可以开始游戏//已测试

        //初始化棋盘
        ArrayList<Chess> ch = new ArrayList<>();
        int i=0;
        for(AllChess x : AllChess.values()){
            ch.add(new Chess());
            ch.get(i).setColor(x.getColor());
            ch.get(i).setRank(x.getRank());
            ch.get(i).setScore(x.getScore());
            i++;
        }
        Collections.shuffle(ch);
        i=0;
        for(int m=0;m<chess.length;m++){
            for(int n=0;n<chess[m].length;n++){
                chess[m][n] = ch.get(i);
                i++;
            }
        }
        chess[0][0].initClick();

        //初始化player
        p1.setColor(Color.UNKNOWN);p2.setColor(Color.UNKNOWN);
        p1.setScore(0);p2.setScore(0);

        //p1开始行动
        p1.changeStatus();
    }


    Chess getChess(int x,int y){
        return chess[x][y];
    }


    Player nowPlay(){
        if(p1.getStatus())return p1;
        else return p2;
    }
}
