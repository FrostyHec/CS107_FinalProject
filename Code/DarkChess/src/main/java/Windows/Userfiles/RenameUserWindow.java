package Windows.Userfiles;

import UserFiles.UserManager;
import Windows.Transmitter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RenameUserWindow {
    public TextField textField;
    private int uid;
    public RenameUserWindow(){
        Transmitter.setRenameUserWindow(this);
    }
    public void commit() throws Exception {
        UserManager.read().renameUser(uid, textField.getText());
        exit();
    }

    public void exit() {
        ((Stage) textField.getScene().getWindow()).close();
    }
    public void setUid(int uid){
        this.uid = uid;
        Transmitter.setRenameUserWindow(null);//及时清空
    }
}
