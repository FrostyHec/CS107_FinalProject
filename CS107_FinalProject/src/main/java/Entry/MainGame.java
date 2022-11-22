package Entry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGame extends Application {
    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //窗体基本属性
        stage.setTitle("DarkChess");
        stage.setResizable(false);

        //加载fxml
        Application.setUserAgentStylesheet(getClass().getResource("surface.css").toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(MainGame.class.getResource("GameArea.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 600, 800));
        stage.show();
    }


    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }

}
