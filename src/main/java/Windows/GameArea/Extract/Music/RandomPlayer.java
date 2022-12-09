package Windows.GameArea.Extract.Music;

import Windows.GameArea.Extract.Pursuance;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
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
