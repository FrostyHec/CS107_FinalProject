package Windows.StartMenu;

import InternetGaming.Internet.LinkingWindow;
import Windows.GameArea.MainGame;
import Windows.SetUp.MainSetUp;
import Windows.SetUp.NormalSettings;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartMenu {
    public Pane paneStartMenu;

    private NormalSettings settings;

    public StartMenu() {
        settings = NormalSettings.readSettings(NormalSettings.url);
    }

    private void startGame() throws IOException {

        MainGame game = new MainGame();
        game.start(new Stage());
        ((Stage) paneStartMenu.getScene().getWindow()).close();
    }

    public void startNextPage() throws IOException {
        if (settings.startMenu.isAlwaysPvP()) {
            startGame();
        }
        if (settings.startMenu.isAlwaysPvE()) {
            //doSomeThing
        }
        Application.setUserAgentStylesheet(getClass().getResource(settings.startMenu.getSkin()).toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartNextPage.fxml"));
        ((Stage) paneStartMenu.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));

    }

    public void openSetUp() throws Exception {
        Stage s2 = new Stage();
        s2.initOwner(paneStartMenu.getScene().getWindow());
        s2.initModality(Modality.WINDOW_MODAL);
        new MainSetUp().start(s2);
    }

    public void exitGame() {
        Platform.exit();
    }

    public void startLinkingPage() throws IOException {
        Application.setUserAgentStylesheet(getClass().getResource(settings.startMenu.getSkin()).toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(LinkingWindow.class.getResource("LinkingWindow.fxml"));
        ((Stage) paneStartMenu.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));
    }
}
