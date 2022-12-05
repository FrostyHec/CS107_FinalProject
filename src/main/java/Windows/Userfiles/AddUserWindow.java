package Windows.Userfiles;

import UserFiles.UserManager;
import Windows.Transmitter;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUserWindow {
    public TextField textField;

    public void commit() throws Exception {
        UserManager.read().generateUser(textField.getText());
        exit();
    }

    public void exit() {
        ((Stage) textField.getScene().getWindow()).close();
    }
}
