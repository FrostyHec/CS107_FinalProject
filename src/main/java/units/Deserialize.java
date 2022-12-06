package units;

import GameLogic.*;
import java.io.*;
import java.util.Objects;

public class Deserialize {//未经测试
    public static Game load(String path) throws Exception  {

        Game game;

        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
            game = (Game) in.readObject();
        }catch (Exception c ){
            throw new Exception("101") ;
        }
        in.close();
        fileIn.close();

        if(game.getChess().length != 8 || game.getChess()[0].length != 4){
            throw new Exception("102");
        }

        boolean a = false;
        for(Chess[] cc : game.getChess()){
            for(Chess c : cc){
                if(a)
                    break;
                if( c == null )
                    continue;
                a = c.getColor()==Color.UNKNOWN || c.getRank()<1 || c.getRank()>7 ;
            }
        }
        if(a){
            throw new Exception("103");
        }

        boolean b  = false;
        a = game.getPlayer1() == null || game.getPlayer2() == null;
        if(!a){
            b = game.getPlayer1().getColor() == game.getPlayer2().getColor()
                    && game.getPlayer1().getColor() != Color.UNKNOWN;
        }
        a = a || game.getPlayer1().getStatus() == game.getPlayer2().getStatus();
        a = a || b;
        if(a){
            throw new Exception("104");
        }

        String s = game.chessToString();
        game.setBack();
        if(!s.equals(Retract.trace(game).chessToString())){
            throw new Exception("105");
        }

        /* 添加异常处理
         *
         * 1. 文件格式错误
         * 描述： 比如支持存储文件是txt，导入的是json
         * 错误编码： 101
         * done
         *
         * 2. 棋盘错误
         * 描述：棋盘并非8*4
         * 错误编码： 102
         * done
         *
         * 3. 棋子错误
         * 描述：棋子并非红黑7种棋子之一
         * 错误编码：103
         * done
         *
         * 4. 缺少行棋方
         * 描述：导入数据只有棋盘，没有下一步行棋的方的提示
         * 错误编码：104
         * done
         *
         * 5. 行棋步骤错误
         * 描述：需要附上从初始棋盘-当前棋盘的行棋步骤，如果过程中有一处有误，则需要报出错误
         * 错误编码：105
         * done
         *
         */

        System.out.println("successfully deserialized");
        return game;
    }
}
