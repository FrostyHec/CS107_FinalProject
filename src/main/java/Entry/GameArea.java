package Entry;

import GameLogic.Square;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class GameArea {
   // Square












    public void chessMove(MouseEvent event) {
        final int bound=5,size=80;
        int x=(int) (event.getX()-bound)/size;
        int y=(int) (event.getX()-bound)/size;


    }
}
