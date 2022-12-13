package Windows.GameArea;

import GameLogic.Player;
import UserFiles.User;
import UserFiles.UserManager;
import Windows.StartMenu.StartMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Period;
import java.util.Locale;
import java.util.ResourceBundle;

public class FinishedController {
    public ImageView imgAvatar;
    public Label lbGameOver;
    public Label lbWinner;
    public Label lbScoreDif;
    public Label lbUsedTime;
    Locale locale = Locale.getDefault();//TODO 语言接口
    private GameArea gameArea;
    ResourceBundle t = ResourceBundle.getBundle("Language/GameAreaLanguage", locale);

    public static void show(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("FinishedWindow.fxml"));
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public FinishedController() {
        Transmitter.setFinishedWindow(this);
        gameArea = Windows.Transmitter.getGameArea();
    }

    public void setWinUser(boolean isHuman) {//TODO 完成游戏结束部分
        if (isHuman) {
            try {
                //XXX赢了
                User u = UserManager.read().nowPlay();
                lbWinner.setText(u.getName() + t.getString("GameOver.win"));

                //分差： xx
                int dif=Math.abs(gameArea.game.getPlayer1().getScore()-gameArea.game.getPlayer2().getScore());
                lbScoreDif.setText(t.getString("GameOver.scoreDifference")+": "+dif);

                //用时
                Period p=gameArea.game.getTotalTime();
                lbUsedTime.setText(t.getString("GameOver.usedTime"));
            } catch (Exception e) {

            }
        }
    }

    public void setWinUser(String name) {

    }

    public void gotoMain(ActionEvent event) {
        Windows.Transmitter.getGameArea().mainExit();
        ((Stage) imgAvatar.getScene().getWindow()).close();
    }

    public void playAgain(ActionEvent event) {
        Windows.Transmitter.getGameArea().reGameExit();
        ((Stage) imgAvatar.getScene().getWindow()).close();
    }
}
