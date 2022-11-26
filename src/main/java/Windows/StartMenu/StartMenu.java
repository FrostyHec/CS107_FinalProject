package Windows.StartMenu;

import Windows.GameArea.MainGame;
import Windows.SetUp.NormalSettings;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class StartMenu {
    public Pane paneStartMenu;

    private NormalSettings normalSettings;
    public StartMenu(){
        readSettings();
    }
    private void readSettings(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/Settings/Settings.ser")))
        {
            normalSettings = (NormalSettings) in.readObject();
        }catch(IOException i)
        {
            i.printStackTrace();
        }catch(ClassNotFoundException c)
        {
            System.out.println("Settings not found!");
            c.printStackTrace();
        }
    }

    private void startGame() throws IOException {

        MainGame game=new MainGame();
        game.start(new Stage());
        ((Stage)paneStartMenu.getScene().getWindow()).close();
    }


    public void startNextPage() throws IOException {
        if(normalSettings.startMenu.isAlwaysPvP()){
            startGame();
        }
        if(normalSettings.startMenu.isAlwaysPvE()){
            //doSomeThing
        }
        Application.setUserAgentStylesheet(getClass().getResource("default.css").toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartNextPage.fxml"));
        ((Stage) paneStartMenu.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));
    }
}
