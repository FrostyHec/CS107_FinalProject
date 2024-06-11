package Windows.StartMenu;

import UserFiles.UserManager;
import Windows.SetUp.Settings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main4 extends Application {
    private Settings settings;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            UserManager.read();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //是新玩家哦
        }

        //窗体基本属性
        stage.setTitle("DarkChess");
        stage.setResizable(false);
        //加载fxml
        settings = Settings.read(Settings.url);
        Application.setUserAgentStylesheet(getClass().getResource(settings.visualSettings.getSkin()).toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartMenu.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
