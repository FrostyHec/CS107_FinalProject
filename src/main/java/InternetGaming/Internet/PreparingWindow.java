package InternetGaming.Internet;

import InternetGaming.GameArea.MainGame;
import InternetGaming.Internet.Message.MessageType;
import InternetGaming.Internet.Message.PlayerType;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class PreparingWindow {
    public AnchorPane preparingPane;
    Client c;

    private PlayerType thisPlayerType;

    public PreparingWindow() {
        Transmitter.preparingWindow = this;
        c = Transmitter.client;
        start();
    }

    private void start() {
        c.start();
        c.m.send(MessageType.PlayerSetting, "");//TODO 等做完用户再加入进来
    }

    public void refresh(ClientData[] cd) {
        //TODO 刷新用户界面捏
    }

    public void ableToStartGame() {
//TODO 能够开始游戏
    }

    public void startGame() {
        MainGame game = new MainGame();
        Platform.runLater(() -> {
            try {
                game.start(new Stage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Transmitter.playerType=thisPlayerType;
        Platform.runLater(() -> {
            ((Stage) preparingPane.getScene().getWindow()).close();
        });

    }

    public void setThisPlayerType(PlayerType thisPlayerType) {
        this.thisPlayerType = thisPlayerType;
    }

    public void startGameClick(ActionEvent event) {
        c.getM().send(MessageType.StartGame,"");
    }
}
