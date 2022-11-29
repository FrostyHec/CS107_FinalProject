package AI;

import GameLogic.*;

import java.util.ArrayList;
import java.util.Collections;

public class generalUsed {

    public static int[][] random(ArrayList<int[][]> canClick) throws Exception{
        if(canClick.size() == 0){
            throw new Exception("can't click");
        }
        Collections.shuffle(canClick);
        return canClick.get(0);
    }

    public static ArrayList<int[][]> canClick(Player player,Chess[][] chess){
        ArrayList<int[][]> canClick = new ArrayList<>();
        for(int i=0;i<chess.length;i++){
            for(int j=0;j<chess[0].length;j++){
                if(chess[i][j] == null)
                    continue;

                if(!chess[i][j].isTurnOver()){
                    int[][] xy = new int[2][2];
                    xy[0][0] = i;
                    xy[0][1] = j;
                    xy[1][0] = (xy[1][1] = -1);
                    canClick.add(xy);
                }else{
                    if(chess[i][j].getColor() == player.getColor() && chess[i][j].possibleMove(chess,i,j) != null){
                        for(int[] a : chess[i][j].possibleMove(chess,i,j)) {
                            if(a[0] != -1) {
                                int[][] xy = new int[2][2];
                                xy[0][0] = i;
                                xy[0][1] = j;
                                xy[1][0] = a[0];
                                xy[1][1] = a[1];
                                canClick.add(xy);
                            }
                        }
                    }
                }
            }
        }
        return canClick;
    }
}
