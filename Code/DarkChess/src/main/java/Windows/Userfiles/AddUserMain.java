package Windows.Userfiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddUserMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(UserfilesWindow.class.getResource("addUserWindow.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.showAndWait();
    }
}
