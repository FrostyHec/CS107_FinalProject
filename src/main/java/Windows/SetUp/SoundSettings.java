package Windows.SetUp;

import java.io.Serializable;

public class SoundSettings implements Serializable {
    private static final long serialVersionUID = 11451419191919L;

    private boolean isMusicPlay;
    private SoundType bagType;


    public boolean isMusicPlay() {
        return isMusicPlay;
    }

    public void setMusicPlay(boolean musicPlay) {
        isMusicPlay = musicPlay;
    }


    public SoundType getBagType() {
        return bagType;
    }

    public void setBagType(SoundType bagType) {
        this.bagType = bagType;
    }
}
