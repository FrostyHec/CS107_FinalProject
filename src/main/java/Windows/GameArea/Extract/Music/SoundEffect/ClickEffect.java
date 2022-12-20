package Windows.GameArea.Extract.Music.SoundEffect;

import Windows.GameArea.Extract.Pursuance;
import javafx.scene.media.Media;

import java.nio.file.Path;
import java.util.ArrayList;

public class ClickEffect extends SoundEffect{
    public ClickEffect(Pursuance pursuance, String filePath) {
        super(pursuance, filePath);
    }

    @Override
    public void next() {

    }
    @Override
    public void generateMusicList(Pursuance pursuance){
        playingMusicList=new ArrayList<>();
        Click type=(Click)pursuance;
        String path="src/main/resources/Windows/Music/Effect/Click/";
        switch (type){
            case Finished -> {
                Media toPlay = new Media(Path.of(path+"finished.mp3").toUri().toString());
                playingMusicList.add(toPlay);
            }
            case Continue -> {
                Media toPlay = new Media(Path.of(path+"filp.mp3").toUri().toString());
                playingMusicList.add(toPlay);
            }
        }
    }

    @Override
    public void addMusic(Pursuance pursuance) {

    }
}
