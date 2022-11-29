package Windows.StartMenu;

import Windows.GameArea.MainGame;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartNextPage {
    public AnchorPane pane;

    public void localPvP() throws IOException {
        startGame();
    }
    private void startGame() throws IOException {
        MainGame game=new MainGame();
        game.start(new Stage());
        ((Stage)pane.getScene().getWindow()).close();
    }
}
