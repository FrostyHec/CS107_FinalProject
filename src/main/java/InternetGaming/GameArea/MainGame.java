package InternetGaming.GameArea;


import Windows.StartMenu.Main;
import Windows.StartMenu.StartMenu;
import Windows.Transmitter;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
        Application.setUserAgentStylesheet(Windows.GameArea.GameArea.class.getResource("default.css").toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(Windows.GameArea.GameArea.class.getResource("GameArea.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();//close request在controller里
        stage.setOnCloseRequest(event -> {
            try {
                new Main().start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

}
