package Windows.Userfiles;

import UserFiles.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ShowingUser {
    private SimpleLongProperty uid;

    private SimpleStringProperty name;

    private SimpleStringProperty playTime;

    private SimpleIntegerProperty longestWin;

    private SimpleStringProperty createTime;

    public ShowingUser(User user) {
        uid = new SimpleLongProperty(user.getUid());
        name = new SimpleStringProperty(user.getName());
        playTime = new SimpleStringProperty(generatePlayTime(user));
        longestWin = new SimpleIntegerProperty();//还没写
        createTime = new SimpleStringProperty(generateStartTime(user));
    }

    private String generatePlayTime(User user) {
        long playSec;
        try {
            playSec = user.getTimeList().getPlaySec();
        } catch (NullPointerException n) {
            return "";
        }
        int hours = (int) (playSec / 3600);
        int minutes = (int) ((playSec % 3600) / 60);
        return hours + " 小时" + minutes + " 分钟";
    }

    private String generateStartTime(User user) {
        return "";//还没写
    }

    public long getUid() {
        return uid.get();
    }

    public SimpleLongProperty uidProperty() {
        return uid;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getPlayTime() {
        return playTime.get();
    }

    public SimpleStringProperty playTimeProperty() {
        return playTime;
    }

    public int getLongestWin() {
        return longestWin.get();
    }

    public SimpleIntegerProperty longestWinProperty() {
        return longestWin;
    }

    public String getCreateTime() {
        return createTime.get();
    }

    public SimpleStringProperty createTimeProperty() {
        return createTime;
    }
}
