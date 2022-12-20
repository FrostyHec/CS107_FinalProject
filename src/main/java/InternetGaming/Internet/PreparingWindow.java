package InternetGaming.Internet;

import InternetGaming.GameArea.MainGame;
import InternetGaming.Internet.Message.MessageType;
import InternetGaming.Internet.Message.PlayerType;

import UserFiles.User;
import UserFiles.UserManager;
import Windows.SetUp.Settings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class PreparingWindow {
    public AnchorPane preparingPane;
    public Button btnStartGame;
    public Label lbStatus;
    public Label lbIP;
    public Label lbFirst;
    public Label lbSecond;
    public Label lbView;
    public Label lbFirstName;
    public Label lbSecondName;
    public Label lbViewerName;
    public Label lbPort;
    public Label lbIPField;
    public Label lbPortField;
    Client c;
    private final GraphicHandler graphicHandler = new GraphicHandler();

    private PlayerType thisPlayerType;
    private boolean isAbleToStart;

    public PreparingWindow() {
        Transmitter.preparingWindow = this;
        c = Transmitter.client;
        start();
    }

    @FXML
    public void initialize() {
        graphicHandler.initialize();
    }

    private void start() {
        c.start();
        String name;
        try {
            User u = UserManager.read().nowPlay();
            name = u.getName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        c.m.send(MessageType.PlayerSetting, name);
    }

    public void refresh(ClientData[] cd) {
        graphicHandler.refresh();
    }

    public void ableToStartGame() {
        isAbleToStart = true;
        graphicHandler.ableToStartGame();
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
        Transmitter.playerType = thisPlayerType;
        Platform.runLater(() -> {
            ((Stage) preparingPane.getScene().getWindow()).close();
        });

    }

    public void setThisPlayerType(PlayerType thisPlayerType) {
        this.thisPlayerType = thisPlayerType;
        if (lbStatus != null) {
            graphicHandler.refreshState();
        }
    }

    public void startGameClick(ActionEvent event) {
        c.getM().send(MessageType.StartGame, "");
    }

    public void setIPAndPort(String ip, int port) {
        graphicHandler.setIPAndPort(ip, port);
    }

    class GraphicHandler {
        Settings settings = Settings.read(Settings.url);
        Locale locale = settings.visualSettings.getLanguage();
        ResourceBundle t = ResourceBundle.getBundle("Language/InternetLanguage", locale);

        public void initialize() {
            btnStartGame.setVisible(false);
            lbFirst.setText(t.getString("Player.1"));
            lbSecond.setText(t.getString("Player.2"));
            lbView.setText(t.getString("View"));
            refreshState();
        }

        public void ableToStartGame() {
            btnStartGame.setVisible(true);
            lbStatus.setText(t.getString("Status.waitStart"));//给你写死
        }

        public void refreshState() {
            if (thisPlayerType == PlayerType.FirstHand && !isAbleToStart) {
                lbStatus.setText(t.getString("Status.waitLink"));
            } else {
                lbStatus.setText(t.getString("Status.waitStart"));
            }
        }

        public void refresh() {//TODO 完全写死了，没办法了最后一会了
            ClientData[] cd = c.getTotalClient();
            lbFirstName.setText(cd[0].name);
            if (cd.length > 1) {
                lbSecondName.setText(cd[1].name);
            }
            if (cd.length > 2) {
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < cd.length; i++) {
                    sb.append(cd[i].name).append(" ");
                }
                lbViewerName.setText(sb.toString());
            }
        }

        public void setIPAndPort(String ip, int port) {
            lbIP.setText(t.getString("NowIP"));
            lbPort.setText(t.getString("NowPort"));
            lbIPField.setText(ip);
            lbPortField.setText(String.valueOf(port));
        }
    }
}
