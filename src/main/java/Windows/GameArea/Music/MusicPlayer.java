package Windows.GameArea.Music;

import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.List;

public class MusicPlayer extends Thread {
    ObservableList<Media> musicList;


    public void generateMediaPlayList(){
//TODO 画出基本草图&实现
    }

    private void playMediaTracks(ObservableList<Media> mediaList) {
        if (mediaList.size() == 0) {
            return;
        }
        MediaPlayer mediaplayer = new MediaPlayer(mediaList.remove(0));
        mediaplayer.play();
        mediaplayer.setOnEndOfMedia(() -> playMediaTracks(mediaList));
    }
}
