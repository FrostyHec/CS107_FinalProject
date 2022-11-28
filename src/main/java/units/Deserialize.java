package units;

import GameLogic.*;
import java.io.*;

public class Deserialize {
    public static Game load(String path) throws Exception  {//看能否做成选择的

        Game game;

        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
            game = (Game) in.readObject();
        }catch (ClassNotFoundException c ){
            throw new Exception("101") ;
        }
        in.close();
        fileIn.close();

        /* 添加异常处理
         *
         * 1. 文件格式错误
         * 描述： 比如支持存储文件是txt，导入的是json
         * 错误编码： 101
         *
         * 2. 棋盘错误
         * 描述：棋盘并非8*4
         * 错误编码： 102
         *
         * 3. 棋子错误
         * 描述：棋子并非红黑7种棋子之一
         * 错误编码：103
         *
         * 4. 缺少行棋方
         * 描述：导入数据只有棋盘，没有下一步行棋的方的提示
         * 错误编码：104
         *
         * 5. 行棋步骤错误
         * 描述：需要附上从初始棋盘-当前棋盘的行棋步骤，如果过程中有一处有误，则需要报出错误
         * 错误编码：105
         *
         */


        //如果路径不对或者不是Game类的，直接return null，这里可能需要前端加以判断

        System.out.println("successfully deserialized");
        return game;
    }
}
