package Windows.Userfiles;

import GameLogic.Game;
import GameLogic.aiMode;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowingSave {
    private SimpleStringProperty saveKind;

    private SimpleStringProperty saveName;

    private SimpleStringProperty latestPlayTime;

    private SimpleStringProperty saveScore;

    public ShowingSave(Game game, String name) {
        if (game instanceof aiMode) {
            saveKind = new SimpleStringProperty("人机");
        }else {
            saveKind=new SimpleStringProperty("双人");
        }
        saveName = new SimpleStringProperty(name);
        latestPlayTime = new SimpleStringProperty(generateLatestPlayTime(game));
        saveScore = new SimpleStringProperty(
                game.getPlayer1().getScore() + "/" +
                        game.getPlayer2().getScore()
        );
    }

    private String generateLatestPlayTime(Game game){
        LocalDateTime d=game.getLatestTime();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return d.format(df);
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
