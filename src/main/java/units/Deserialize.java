package units;

import GameLogic.*;
import java.io.*;

public class Deserialize {
    public static Game deserialize(String path) {//看能否做成选择的

        Game game;

        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            game = (Game) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Game class not found");
            c.printStackTrace();
            return null;
        }
        //如果路径不对或者不是Game类的，直接return null，这里可能需要前端加以判断

        System.out.println("successfully deserialized");
        return game;
    }
}
