package Windows.GameArea.Extract.Music.SoundEffect;

import Windows.GameArea.Extract.Music.MusicPlayer;
import Windows.GameArea.Extract.Pursuance;

public abstract class SoundEffect extends MusicPlayer {
    public SoundEffect(Pursuance pursuance, String filePath) {
        super(pursuance, filePath);
    }

    @Override
    public void run(){
        if(playingMusicList.size()==0){
            return;
        }
        generateMusicList(pursuance);
        playMediaTracks(playingMusicList);
    }
    @Override
    public void generateMusicList(Pursuance pursuance) {

    }
}
