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

        //添加异常处理

        //如果路径不对或者不是Game类的，直接return null，这里可能需要前端加以判断

        System.out.println("successfully deserialized");
        return game;
    }
}
