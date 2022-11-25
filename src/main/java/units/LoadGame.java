package units;

import GameLogic.*;
import java.io.*;

public class LoadGame {
    public static void main(String[] args) {
        Game e = null;
        try {
            FileInputStream fileIn = new FileInputStream("D:\\files\\game.ser");
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
        System.out.println(e.getMoves());
        for(Chess[] c : e.getChess()){
            for(Chess cc : c){
                System.out.print(cc.getColor());
                System.out.println(cc.getRank());
            }
        }
    }
}
