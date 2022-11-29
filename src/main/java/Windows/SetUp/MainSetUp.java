package Windows.SetUp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainSetUp extends Application {
    private NormalSettings settings;


    @Override
    public void start(Stage stage) throws Exception {
        //窗体基本属性
        stage.setTitle("SetUp");
        stage.setResizable(false);
        //加载fxml
        settings = NormalSettings.readSettings(NormalSettings.url);
        Application.setUserAgentStylesheet(getClass().getResource(settings.startMenu.getSkin()).toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetUp.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(),385,460));
        stage.show();
    }
}
