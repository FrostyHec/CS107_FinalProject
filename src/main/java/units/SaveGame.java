package units;

import GameLogic.*;
import java.io.*;

public class SaveGame {
    public static void save(Game game,String path) {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(path);//需要建立一个输出文件的文件夹(绝对路径/从src开始的相对路径)
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(game);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in "+path);
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }
}
