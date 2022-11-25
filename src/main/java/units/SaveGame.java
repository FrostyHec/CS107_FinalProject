package units;

import GameLogic.*;
import java.io.*;

public class SaveGame {
    public static void save(Game game) {
        try
        {
            FileOutputStream fileOut = new FileOutputStream("D:\\files\\game.ser");//需要建立一个输出文件的文件夹(绝对路径)
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(game);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in D:\\files\\game.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }
}
