package Windows.GameArea.Extract.Music;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ShowingMusic {
    private SimpleIntegerProperty index;
    private SimpleStringProperty musicName;
    private SimpleStringProperty length;


    public ShowingMusic(SimpleIntegerProperty index, SimpleStringProperty musicName, SimpleStringProperty length) {
        this.index = index;
        this.musicName = musicName;
        this.length = length;
    }

    public ShowingMusic(int index, MusicInfo musicInfo){
        this.index=new SimpleIntegerProperty(index);
        this.musicName =new SimpleStringProperty(musicInfo.musicName);
        this.length=new SimpleStringProperty(generateTime(musicInfo.secs));
    }
    private String generateTime(int secs){
        return "";
    }
    public int getIndex() {
        return index.get();
    }

    public SimpleIntegerProperty indexProperty() {
        return index;
    }

    public String getMusicName() {
        return musicName.get();
    }

    public SimpleStringProperty musicNameProperty() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName.set(musicName);
    }

    public String getLength() {
        return length.get();
    }

    public SimpleStringProperty lengthProperty() {
        return length;
    }

    public void setLength(String length) {
        this.length.set(length);
    }
}
