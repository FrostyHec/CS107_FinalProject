package Windows.StartMenu;

import GameLogic.Game;
import UserFiles.User;
import UserFiles.UserManager;
import Windows.GameArea.MainGame;
import Windows.SetUp.NormalSettings;
import Windows.Transmitter;
import Windows.Userfiles.SaveList;
import Windows.Userfiles.ShowingSave;
import Windows.Userfiles.ShowingUser;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import units.Deserialize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class ContinueGame {
    public Button btnContinue;
    public TableView dataTable;
    public TableColumn saveKind;
    public TableColumn saveName;
    public TableColumn latestPlayTime;
    public TableColumn saveScore;
    public Button btnDeleteFile;
    private String selectedSaveName;
    private SaveList saveList;


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
        automaticSave();
        //存档选择界面
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        btnContinueVisible(false);
        saveList = new SaveList();
        refreshData();
        //单选与监听
        dataTable.getSelectionModel().setCellSelectionEnabled(true);
        dataTable.getSelectionModel().getSelectedCells().addListener((InvalidationListener) observable -> {
            ObservableList<TablePosition> observableList = (ObservableList<TablePosition>) observable;
            for (int i = 0; i < observableList.size(); i++) {
                TablePosition tablePosition = observableList.get(i);
                Object cellData = saveName.getCellData(tablePosition.getRow());
                selectedSaveName = cellData.toString();
                btnContinueVisible(true);
            }
        });
    }

    private Path generateSavePath(User nowPlay,String path) {
        return Path.of(nowPlay.getSavePath() + "/" + path);
    }

    public void refreshData() {
        ObservableList<ShowingSave> data = FXCollections.observableArrayList();
        saveKind.setCellValueFactory(new PropertyValueFactory<>("saveKind"));
        saveName.setCellValueFactory(new PropertyValueFactory<>("saveName"));
        latestPlayTime.setCellValueFactory(new PropertyValueFactory<>("latestPlayTime"));
        saveScore.setCellValueFactory(new PropertyValueFactory<>("saveScore"));
        for (String key : saveList.getSaveList().keySet()) {
            ShowingSave sv=new ShowingSave(saveList.getSaveList().get(key), key);
            if(sv.getSaveKind().equals("人机")){
                System.out.println("屏蔽了");
                continue;
            }//因为有bug，所以把AI的存档全都屏蔽掉了
            data.add(sv);
        }
        dataTable.setItems(data);
    }


    private void btnContinueVisible(boolean b) {
        btnContinue.setVisible(b);
        btnContinue.setDisable(!b);
        btnDeleteFile.setVisible(b);
        btnDeleteFile.setDisable(!b);
    }

    private void loadGame(File file) {
        Game g;
        try {
            if (file == null) {
                return;
            }
            g = Deserialize.load(file.getPath());
        } catch (Exception e) {
            switch (e.getMessage()) {
                //貌似乱开文件不会抛出101，只有错误的ser会报101
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
        try {
            new MainGame().start(new Stage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Transmitter.loadGame(g);
        exit();
    }

    private void exit() {
        ((Stage) btnContinue.getScene().getWindow()).close();
    }

    private void loadGame(File file, String name) {
        loadGame(file);
        Transmitter.setGameSaveName(name);
    }

    @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
    public void loadFromLocal(ActionEvent event) throws IOException {
        Stage s2 = new Stage();
        s2.initOwner(btnContinue.getScene().getWindow());
        s2.initModality(Modality.WINDOW_MODAL);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("SaveFiles", "*.ser"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
        fileChooser.setTitle("从本地载入存档");
        File file = fileChooser.showOpenDialog(s2);
        if (file == null) {
            return;
        }
        loadGame(file, file.getName());

    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    public void backToMain(ActionEvent event) {
        StartMenu.show((Stage) btnContinue.getScene().getWindow());
    }

    public void continueGame(ActionEvent event) {
        loadGame(saveList.generateSavePath(selectedSaveName).toFile());
    }

    public void deleteSave() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("警告！");
        alert.setContentText("删除存档操作不可逆，请确认您的操作！");
        alert.setHeaderText("删除存档确认");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;//取消删除
        }
        //删除
        saveList.deleteSave(selectedSaveName);
        refreshData();
    }

    public void automaticSave() {
        //自动加载存档//TODO 测试这一模块
        if (NormalSettings.read(NormalSettings.url).StartSettings.isAlwaysLatestSave()) {
            String saveName=null;
            User u = null;
            try {
                u=UserManager.read().nowPlay();
                saveName = u.getLatestSaveName();
            }catch (Exception e){
                e.printStackTrace();
            }
            if (saveName!=null){
                loadGame(generateSavePath(u,selectedSaveName).toFile());
                exit();
            }
        }

    }
}
