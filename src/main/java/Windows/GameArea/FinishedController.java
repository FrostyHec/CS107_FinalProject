package Windows.GameArea;

import GameLogic.Player;
import UserFiles.User;
import UserFiles.UserManager;
import Windows.SetUp.Settings;
import Windows.StartMenu.Main;
import Windows.StartMenu.StartMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.util.Locale;
import java.util.ResourceBundle;

public class FinishedController {
    private String defaultAvatar = "src/main/resources/Windows/images/UserImage/tempUser.png";
    private String defaultComputerAvatar = "src/main/resources/Windows/images/UserImage/ComputerUser.png";
    public ImageView imgAvatar;
    public Label lbGameOver;
    public Label lbWinner;
    public Label lbScoreDif;
    public Label lbUsedTime;
    private Settings settings=Settings.read(Settings.url);
    Locale locale=settings.visualSettings.getLanguage();
    private GameArea gameArea;
    private boolean rawClose = true;
    ResourceBundle t = ResourceBundle.getBundle("Language/GameAreaLanguage", locale);

    public void show(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GameArea.class.getResource("FinishedWindow.fxml"));
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle(t.getString("GameOver"));
        stage.show();
        stage.setOnCloseRequest(event -> {
            if (!rawClose) {
                return;
            }
            try {
                Windows.Transmitter.getGameArea().mainExit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public FinishedController() {
        Transmitter.setFinishedWindow(this);
        gameArea = Windows.Transmitter.getGameArea();
    }

    @FXML
    public void initialize() {
        lbGameOver.setText(t.getString("GameOver"));
    }

    public void setWinUser(boolean isHuman) {//TODO 完成游戏结束部分
        if (isHuman) {//XXX赢了
            try {
                User u = UserManager.read().nowPlay();
                lbWinner.setText(u.getName() + t.getString("GameOver.win"));
                try {
                    imgAvatar.setImage(new Image(new FileInputStream(u.getAvatarUrl())));
                } catch (Exception e) {
                    try {
                        imgAvatar.setImage(new Image(new FileInputStream(defaultAvatar)));
                    } catch (Exception n) {
                        System.out.println("图片加载失败！");
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            lbWinner.setText(t.getString("Player.Computer") + t.getString("GameOver.win"));
            try {
                imgAvatar.setImage(new Image(new FileInputStream(defaultComputerAvatar)));
            } catch (Exception n) {
                System.out.println("图片加载失败！");
            }
        }
        setDetailedInfo();
    }

    private void setDetailedInfo() {
        //分差： xx
        int dif = Math.abs(gameArea.game.getPlayer1().getScore() - gameArea.game.getPlayer2().getScore());
        lbScoreDif.setText(t.getString("GameOver.scoreDifference") + ": " + dif);

        //用时
        Duration p = gameArea.game.getTotalTime();
        lbUsedTime.setText(t.getString("GameOver.usedTime") + p.toMinutes() + t.getString("GameOver.minutes"));

    }

    public void setWinUser(String name) {
        if (name.equals("player1")) {//XXX赢了
            lbWinner.setText(t.getString("Player1") + t.getString("GameOver.win"));
        } else {
            lbWinner.setText(t.getString("Player2") + t.getString("GameOver.win"));
        }
        try {
            imgAvatar.setImage(new Image(new FileInputStream(defaultAvatar)));
        } catch (Exception n) {
            System.out.println("图片加载失败！");
        }
        setDetailedInfo();

    }

    public void gotoMain(ActionEvent event) {
        rawClose = false;
        Windows.Transmitter.getGameArea().mainExit();
        ((Stage) imgAvatar.getScene().getWindow()).close();
    }

    public void playAgain(ActionEvent event) {
        rawClose = false;
        Windows.Transmitter.getGameArea().reGameExit();
        ((Stage) imgAvatar.getScene().getWindow()).close();
    }
}
