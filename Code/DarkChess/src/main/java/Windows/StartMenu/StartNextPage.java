package Windows.StartMenu;

import Windows.GameArea.MainGame;
import Windows.SetUp.Settings;
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
    private Settings settings = Settings.read(Settings.url);

    @FXML
    public void initialize() {
        Transmitter.startNextPage = this;
    }

    public void jump() {
        if (settings.StartSettings.isAlwaysPvP()) {
            try {
                localPvP();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (settings.StartSettings.isAlwaysPvE()) {
            try {
                localPvE();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

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


