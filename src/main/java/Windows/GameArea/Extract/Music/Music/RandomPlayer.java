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
    public void run() {//默认循环播放
        super.run();
        middleGenerate();
    }

    @Override
    public void generateMusicList(Pursuance pursuance) {
        playingMusicList = randomMusicList();
    }
    private List<Media> randomMusicList(){
        List<Media> temp = new ArrayList<>(totalMusic);
        List<Media> result = new ArrayList<>();
        while (temp.size() > 0) {
            int i = new Random().nextInt(temp.size());
            result.add(temp.get(i));
            temp.remove(i);
        }
        return result;
    }

    @Override
    public void next() {
        next(playingMusicList);
        middleGenerate();
    }
    private void middleGenerate(){//音乐列表小于一半时续加
        if (playingMusicList.size() <totalMusic.size()/2) {
            addMusic(pursuance);
        }
    }
    @Override
    public void addMusic(Pursuance pursuance){
        playingMusicList.addAll(randomMusicList());
    }
}
