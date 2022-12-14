package Windows.SetUp;

import Windows.StartMenu.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainSetUp extends Application {
    private Settings settings;


    @Override
    public void start(Stage stage) throws Exception {
        //窗体基本属性
        stage.setTitle("SetUp");
        stage.setResizable(false);
        //加载fxml
        settings = Settings.read(Settings.url);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetUp.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(),385,460));
        stage.show();
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        stage.setOnCloseRequest((e)->{
            try {
                new Main().start(new Stage());
            } catch (Exception r) {
                throw new RuntimeException(r);
            }
        });
    }
}
