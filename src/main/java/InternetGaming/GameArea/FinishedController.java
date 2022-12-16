package InternetGaming.GameArea;
import InternetGaming.Internet.ClientData;
import InternetGaming.Internet.Transmitter;
import UserFiles.User;
import UserFiles.UserManager;
import Windows.GameArea.GameArea;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class FinishedController extends Windows.GameArea.FinishedController {
    public FinishedController() {
        Transmitter.finishedController=this;
        gameArea=Transmitter.gameArea;
    }

    public void show(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(InternetGaming.GameArea.FinishedController.class.getResource("FinishedWindow.fxml"));
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle(t.getString("GameOver"));
        stage.show();
        stage.setOnCloseRequest(event -> {
            Transmitter.gameArea.mainExit();
        });
    }

    public void setWinner(ClientData cd){
        try {
            lbWinner.setText(cd.getName() + t.getString("GameOver.win"));
            try {
                imgAvatar.setImage(new Image(new FileInputStream(cd.getAvatar())));
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
        setDetailedInfo();
    }
}
