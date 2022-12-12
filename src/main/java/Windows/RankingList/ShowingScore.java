package Windows.RankingList;

import UserFiles.Score;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.NumberFormat;

public class ShowingScore {
    private SimpleStringProperty playKind;
    private SimpleStringProperty winRates;
    private SimpleStringProperty longestWin;
    private SimpleStringProperty playTimes;
    private SimpleStringProperty nowLongestWin;

    public ShowingScore(Score score, String playKind) {
        this.playKind = new SimpleStringProperty(playKind);
        this.winRates = new SimpleStringProperty(generateWinRate(score.getWinRates()));
        this.longestWin = new SimpleStringProperty(String.valueOf(score.getLongestContinueWin()));
        this.playTimes = new SimpleStringProperty(String.valueOf(score.getPlayTimes()));
        this.nowLongestWin = new SimpleStringProperty(String.valueOf(score.getLongestContinueWin()));
    }

    private String generateWinRate(double winRates) {
        NumberFormat num = NumberFormat.getPercentInstance();
        String ans = num.format(winRates);
        if (ans.equals("NaN")) {
            ans = "";
        }
        return ans;
    }

    public String getPlayKind() {
        return playKind.get();
    }

    public SimpleStringProperty playKindProperty() {
        return playKind;
    }

    public String getWinRates() {
        return winRates.get();
    }

    public SimpleStringProperty winRatesProperty() {
        return winRates;
    }

    public String getLongestWin() {
        return longestWin.get();
    }

    public SimpleStringProperty longestWinProperty() {
        return longestWin;
    }

    public String getPlayTimes() {
        return playTimes.get();
    }

    public SimpleStringProperty playTimesProperty() {
        return playTimes;
    }

    public String getNowLongestWin() {
        return nowLongestWin.get();
    }

    public SimpleStringProperty nowLongestWinProperty() {
        return nowLongestWin;
    }
}
