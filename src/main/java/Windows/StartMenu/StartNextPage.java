package Windows.StartMenu;

import Windows.GameArea.MainGame;
import Windows.Transmitter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartNextPage {
    public AnchorPane pane;
    public RadioButton humanFirst;
    public RadioButton aiFirst;
    public ChoiceBox choiceButton;

    public void localPvP() throws IOException {
        startGame();
    }

    private void startGame() throws IOException {
        MainGame game = new MainGame();
        game.start(new Stage());
        try {
            ((Stage) pane.getScene().getWindow()).close();

        }catch (NullPointerException e){
            ((Stage) humanFirst.getScene().getWindow()).close();

        }
    }

    public void localPvE() throws IOException {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);//这句有bug，但我懒得改
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("setDifficulty.fxml"));
        ((Stage) pane.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));
    }

    public void startAIGame(ActionEvent event) throws IOException {
        //TODO 搓出选择难度界面
        startGame();
        //Transmitter.setPvEDifficulty(0,false);
        Transmitter.setPvEDifficulty(0,true);
    }
}
