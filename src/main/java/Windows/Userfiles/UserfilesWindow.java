package Windows.Userfiles;

import UserFiles.User;
import UserFiles.UserManager;
import Windows.StartMenu.StartMenu;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class UserfilesWindow {

    public TableView dataTable;
    public TableColumn Uid;
    public TableColumn UserName;
    public TableColumn LongestWin;
    public TableColumn PlayTime;
    public TableColumn CreateTime;
    public UserManager userManager;
    public Button btnDelete;
    public Button btnSwitch;
    public Button btnRename;
    private int selectedUser;

    public void Userfiles() {

    }
    private void newWindowButton(){
        setRenameVisible(false);
        setNextButtonVisible(false);
    }

    @FXML
    public void initialize() {
        newWindowButton();
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        refreshData();
        //单选与监听
        dataTable.getSelectionModel().setCellSelectionEnabled(true);
        dataTable.getSelectionModel().getSelectedCells().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                ObservableList<TablePosition> observableList = (ObservableList<TablePosition>) observable;
                for (int i = 0; i < observableList.size(); i++) {
                    TablePosition tablePosition = observableList.get(i);
                    Object cellData = Uid.getCellData(tablePosition.getRow());
                    selectedUser = Integer.parseInt(cellData.toString());
                    refreshSelectedUserButton();
                }
            }
        });
    }
    private void refreshSelectedUserButton(){
        if(userManager.nowPlay().getUid()!=selectedUser){
            setNextButtonVisible(true);
        }else {
            setNextButtonVisible(false);
        }
        setRenameVisible(true);
    }

    private void setNextButtonVisible(boolean b) {//ture可见，false不可见
        btnDelete.setVisible(b);
        btnDelete.setDisable(!b);
        btnSwitch.setVisible(b);
        btnSwitch.setDisable(!b);
    }
    private void setRenameVisible(boolean b){
        btnRename.setVisible(b);
        btnRename.setDisable(!b);
    }

    public void refreshData() {
        try {
            userManager = UserManager.read();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        ObservableList<ShowingUser> data = FXCollections.observableArrayList();
        Uid.setCellValueFactory(new PropertyValueFactory<>("uid"));
        UserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PlayTime.setCellValueFactory(new PropertyValueFactory<>("playTime"));
        LongestWin.setCellValueFactory(new PropertyValueFactory<>("longestWin"));
        CreateTime.setCellValueFactory(new PropertyValueFactory<>("createTime"));
        for (User u : userManager.getUserList()) {
            data.add(new ShowingUser(u));
        }
        dataTable.setItems(data);
    }

    public void exitWindow(ActionEvent event) throws IOException {
        StartMenu.show((Stage) dataTable.getScene().getWindow());
    }

    public void addUser() throws Exception {
        newWindowButton();
        Stage s2 = new Stage();
        s2.setTitle("新建玩家");
        s2.initOwner(dataTable.getScene().getWindow());
        s2.initModality(Modality.WINDOW_MODAL);
        new AddUserMain().start(s2);
        refreshData();
    }

    public void deleteUser() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("警告！");
        alert.setContentText("删除用户操作不可逆，请确认您的操作！");
        alert.setHeaderText("删除用户确认");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;//取消删除
        }
        //删除
        userManager.deleteUser(selectedUser);
        refreshData();
    }

    public void switchUser() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("询问");
        alert.setContentText("是否确认切换用户？");
        alert.setHeaderText("切换用户确认");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;//取消切换
        }
        userManager.switchUser(selectedUser);
        refreshSelectedUserButton();
    }

    public void renameUser() {
        //没做
    }
}
