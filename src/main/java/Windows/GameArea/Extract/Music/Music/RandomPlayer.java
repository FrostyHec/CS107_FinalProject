package Windows.GameArea.Extract.Music.Music;

import Windows.GameArea.Extract.Music.MusicPlayer;
import Windows.GameArea.Extract.Pursuance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPlayer extends MusicPlayer {

    public RandomPlayer(Pursuance pursuance, String filePath) {
        super(pursuance, filePath);
    }

    @Override
    public void generateMusicList(Pursuance pursuance) {
        List<Media> temp = new ArrayList<>(totalMusic);
        List<Media> result = new ArrayList<>();
        while (temp.size() > 0) {
            int i = new Random().nextInt(temp.size());
            result.add(temp.get(i));
            temp.remove(i);
        }
        playingMusicList = result;
    }
}
