package Windows.StartMenu;

import GameLogic.Game;
import Windows.GameArea.MainGame;
import Windows.Transmitter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import units.Deserialize;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ContinueGame {
    public Button btnContinue;

    public static void show(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("ContinueGame.fxml"));
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        btnContinueVisible(false);
    }

    private void btnContinueVisible(boolean b) {
        btnContinue.setVisible(b);
        btnContinue.setDisable(!b);
    }

    @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
    public void loadFromLocal(ActionEvent event) throws IOException {
        Stage s2 = new Stage();
        s2.initOwner(btnContinue.getScene().getWindow());
        s2.initModality(Modality.WINDOW_MODAL);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All", "*.*"),
                new FileChooser.ExtensionFilter("SaveFiles", "*.ser")
        );
        fileChooser.setTitle("从本地载入存档");
        File file = fileChooser.showOpenDialog(s2);
        MainGame game = new MainGame();
        try {
            if (file == null) {
                return;
            }
            Game g = Deserialize.load(file.getPath());
            Transmitter.loadGame(g);
        } catch (Exception e) {
            switch (e.getMessage()) {
                //TODO 101有bug，貌似乱开文件不会抛出101，只有错误的ser会报101，，，
                case "101" -> {
                    showAlert("ERROR!",
                            "ERROR CODE: 101",
                            "文件格式错误,请检查载入文件是否正确");
                }
                case "102" -> {
                    showAlert("ERROR!",
                            "ERROR CODE: 102",
                            "棋盘并非8*4");
                }
                case "103" -> {
                    showAlert("ERROR!",
                            "ERROR CODE: 103",
                            "棋子并非红黑7种棋子之一");
                }
                case "104" -> {
                    showAlert("ERROR!",
                            "ERROR CODE: 104",
                            "导入数据只有棋盘，没有下一步行棋的方的提示");
                }
                case "105" -> {
                    showAlert("ERROR!",
                            "ERROR CODE: 105",
                            "需要附上从初始棋盘-当前棋盘的行棋步骤，如果过程中有一处有误，则需要报出错误");
                }
                default -> {
                    showAlert("ERROR!",
                            "ERROR CODE: 101",
                            "文件格式错误,请检查载入文件是否正确");
                }
            }
            return;
        }
        game.start(new Stage());
        ((Stage) btnContinue.getScene().getWindow()).close();

    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }//晚点再美化这个界面

    public void backToMain(ActionEvent event) {
        StartMenu.show((Stage) btnContinue.getScene().getWindow());
    }

    public void continueGame(ActionEvent event) {
        //TODO
    }
    //TODO:自动打开上一次存档还没做
}
