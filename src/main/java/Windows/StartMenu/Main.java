package Windows.StartMenu;

import Windows.GameArea.MainGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //窗体基本属性
        stage.setTitle("DarkChess");
        stage.setResizable(false);
        //加载fxml
        Application.setUserAgentStylesheet(getClass().getResource("default.css").toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(MainGame.class.getResource("StartMenu.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 600, 800));
        stage.show();
    }
}
