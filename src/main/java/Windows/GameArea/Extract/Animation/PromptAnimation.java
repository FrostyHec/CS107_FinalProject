package Windows.GameArea.Extract.Animation;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


public class PromptAnimation {
    public static void mainPrompt(Pane mainPain, String message) {
        Label lb=new Label();
        lb.setText(message);
        lb.setStyle("-fx-text-fill: Red\n" +
                "" +
                "" +
                "");
    }
}
