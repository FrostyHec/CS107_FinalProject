package InternetGaming.Internet;

import InternetGaming.Internet.Message.MessageType;
import Windows.GameArea.MainGame;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class PreparingWindow {
    public Pane preparingPane;
    Client c;

    public PreparingWindow() {
        Transmitter.preparingWindow = this;
        c = Transmitter.client;
        start();
    }

    private void start() {
        c.start();
        c.m.send(MessageType.PlayerSetting, "");//等做完用户再加入进来
    }

    public void refresh(ClientData[] cd) {
        //刷新用户界面捏
    }

    public void ableToStartGame() {

    }

    public void startGame() {
        MainGame game = new MainGame();
        try {
            game.start(new Stage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((Stage) preparingPane.getScene().getWindow()).close();
    }
}
