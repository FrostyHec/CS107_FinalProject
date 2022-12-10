package Windows.StartMenu;

import Windows.GameArea.MainGame;
import Windows.Transmitter;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartNextPage {
    public AnchorPane pane;


    public void localPvP() throws IOException {
        startGame();
    }

    private void startGame() throws IOException {
        MainGame game = new MainGame();
        game.start(new Stage());
        ((Stage) pane.getScene().getWindow()).close();
    }

    public void localPvE() throws IOException {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);//这句有bug，但我懒得改
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("setDifficulty.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));
    }
}


