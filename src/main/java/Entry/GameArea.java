package Entry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameArea extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameArea.class.getResource("hello-view.fxml"));
        stage.setTitle("DarkChess");
        stage.setScene(new Scene(fxmlLoader.load(), 320, 240));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
