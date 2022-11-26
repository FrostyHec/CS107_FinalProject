package units;

import GameLogic.*;
import java.io.*;

public class LoadGame {
    Game e = null;
    public void deserialize(String path) {

        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Game) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Game class not found");
            c.printStackTrace();
            return;
        }
        System.out.println("successfully deserialized");
    }

    public Game getGame(){
        return e;
    }
}
