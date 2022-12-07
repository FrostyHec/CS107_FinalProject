package Windows.Userfiles;

import GameLogic.Game;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ShowingSave {
    private SimpleStringProperty saveKind;

    private SimpleStringProperty saveName;

    private SimpleStringProperty latestPlayTime;

    private SimpleStringProperty saveScore;

    public ShowingSave(Game game, String name) {
        //TODO SaveKind还没写
        saveKind=null;
        saveName = new SimpleStringProperty(name);
        //TODO 最近游戏时间也没写
        latestPlayTime=null;
        saveScore = new SimpleStringProperty(
                game.getPlayer1().getScore() + "/" +
                        game.getPlayer2().getScore()
        );
    }

    public String getSaveKind() {
        return saveKind.get();
    }

    public SimpleStringProperty saveKindProperty() {
        return saveKind;
    }

    public String getSaveName() {
        return saveName.get();
    }

    public SimpleStringProperty saveNameProperty() {
        return saveName;
    }

    public String getLatestPlayTime() {
        return latestPlayTime.get();
    }

    public SimpleStringProperty latestPlayTimeProperty() {
        return latestPlayTime;
    }

    public String getSaveScore() {
        return saveScore.get();
    }

    public SimpleStringProperty saveScoreProperty() {
        return saveScore;
    }
}
