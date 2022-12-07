package Windows.StartMenu;

import UserFiles.UserManager;
import Windows.SetUp.NormalSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private NormalSettings settings;

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
        settings = NormalSettings.read(NormalSettings.url);
        Application.setUserAgentStylesheet(getClass().getResource(settings.StartSettings.getSkin()).toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartMenu.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
