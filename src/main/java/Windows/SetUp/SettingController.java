package Windows.SetUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SettingController {

    public AnchorPane paneStart;
    public AnchorPane paneGame;
    public AnchorPane paneSound;
    public AnchorPane paneView;

    @FXML
    public void initialize(){
        paneGame.setVisible(false);
        paneSound.setVisible(false);
        paneView.setVisible(false);
        paneStart.setVisible(true);//默认为开始节目

    }
    public void startMenu(ActionEvent event) {
        //设置界面
        paneGame.setVisible(false);
        paneSound.setVisible(false);
        paneView.setVisible(false);
        paneStart.setVisible(true);
    }

    public void gameSetting(ActionEvent event) {
        //设置界面
        paneGame.setVisible(true);
        paneSound.setVisible(false);
        paneView.setVisible(false);
        paneStart.setVisible(false);
    }

    public void soundSetting(ActionEvent event) {
        //设置界面
        paneGame.setVisible(false);
        paneSound.setVisible(true);
        paneView.setVisible(false);
        paneStart.setVisible(false);
    }

    public void viewSetting(ActionEvent event) {
        //设置界面
        paneGame.setVisible(false);
        paneSound.setVisible(false);
        paneView.setVisible(true);
        paneStart.setVisible(false);
    }
}
