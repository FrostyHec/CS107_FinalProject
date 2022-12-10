package Windows.GameArea.Extract.Music;

import Windows.GameArea.Extract.Pursuance;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public abstract class MusicPlayer implements Runnable {
    String forwardPath = "src/main/resources/Windows/Music/";
    protected String filePath;//音乐包路径
    protected List<Media> playingMusicList;//在播放列表的音乐

    protected List<Media> totalMusic = new ArrayList<>();//总音乐列表
    protected Pursuance pursuance;
    protected MediaPlayer nowMedia;

    public MusicPlayer(Pursuance pursuance, String filePath) {
        this.pursuance = pursuance;
        this.filePath = filePath;
        read();
    }

    @Override
    public void run() {//默认循环播放
        if (playingMusicList == null || playingMusicList.size() == 0) {
            generateMusicList(pursuance);
        }
        playMediaTracks(playingMusicList);
    }

    public abstract void generateMusicList(Pursuance pursuance);

    protected void playMediaTracks(List<Media> mediaList) {//这个方法将会播放所有的音频直到播完
        nowMedia = new MediaPlayer(mediaList.remove(0));
        nowMedia.play();
        nowMedia.setOnEndOfMedia(this);
    }

    public void continuePlay() {
        nowMedia.play();
    }

    private void read() {
        try {
            Files.walkFileTree(Path.of(forwardPath + filePath),
                    new SimpleFileVisitor<Path>() {
                        // 遍历导入
                        @Override
                        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                            try {
                                totalMusic.add(new Media(path.toUri().toString()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        nowMedia.pause();
    }

    public void insertMusic(int index) {//点歌台，强行插入音乐
        playingMusicList = new ArrayList<>();
        playingMusicList.add(totalMusic.get(index));
        run();
    }
}
