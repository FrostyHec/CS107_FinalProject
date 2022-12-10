package Windows.StartMenu;

import Windows.GameArea.MainGame;
import Windows.Transmitter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectDifficulty {
    public AnchorPane pane;
    public RadioButton humanFirst;
    public RadioButton aiFirst;
    public ChoiceBox<String> choiceBox;
    private ToggleGroup tg = new ToggleGroup();

    private RadioButton selectedPlayerFirst;
    @FXML
    public void initialize(){
        //选择难度模式才初始化
        for (Difficulty d : Difficulty.values()) {
            choiceBox.getItems().add(d.toString());
        }
        humanFirst.setToggleGroup(tg);
        aiFirst.setToggleGroup(tg);
        //默认人类优先
        humanFirst.setSelected(true);
        selectedPlayerFirst = humanFirst;
        tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> selectedPlayerFirst = (RadioButton) newValue);
    }
    public void startAIGame(ActionEvent event) throws IOException {
        int difficulty;
        try {
            difficulty = Difficulty.getDifficulty(choiceBox.getValue());
        } catch (Exception e) {
            showAlert("选择错误", "没有选择难度！", "没有选择难度！请选择AI难度等级");
            return;
        }
        //TODO 搓出选择难度界面
        startGame();
        //Transmitter.setPvEDifficulty(0,false);
        Transmitter.setPvEDifficulty(difficulty, isHumanFirst());
    }
    private void startGame() throws IOException {
        MainGame game = new MainGame();
        game.start(new Stage());
        ((Stage) aiFirst.getScene().getWindow()).close();
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private boolean isHumanFirst() {
        if(selectedPlayerFirst.equals(humanFirst)){
            return true;
        }else if(selectedPlayerFirst.equals(aiFirst)){
            return false;
        }
        throw new RuntimeException("Player first missing!");
    }
}
enum Difficulty {
    Zero("难度0", 0),
    One("难度1", 1),
    Two("难度2", 2);
    private String name;
    private int value;

    Difficulty(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }

    public static int getDifficulty(String name) throws Exception {
        for (Difficulty d : Difficulty.values()) {
            if (d.name.equals(name)) {
                return d.value;
            }
        }
        throw new Exception("Difficulty not found");
    }
}
