package Windows.StartMenu;

import Windows.SetUp.NormalSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private NormalSettings settings;

    @Override
    public void start(Stage stage) throws Exception {
        //窗体基本属性
        stage.setTitle("DarkChess");
        stage.setResizable(false);
        //加载fxml
        settings = NormalSettings.readSettings(NormalSettings.url);
        Application.setUserAgentStylesheet(getClass().getResource(settings.startMenu.getSkin()).toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartMenu.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
