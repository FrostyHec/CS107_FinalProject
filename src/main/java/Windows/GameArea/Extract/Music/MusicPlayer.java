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
    protected List<MusicInfo> musicInfoList = new ArrayList<>();

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

    public List<MusicInfo> getNowMusicInfoList() {
        List<MusicInfo> ml = new ArrayList<>();
        for (Media m : playingMusicList) {
            for (MusicInfo mi : musicInfoList) {
                if (mi.media.equals(m)) {
                    ml.add(mi);
                }
            }
        }
        return ml;
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
                                String sPath = path.toUri().toString();
                                Media toPlay = new Media(sPath);
                                totalMusic.add(toPlay);
                                generateMusicInfo(getName(sPath), toPlay);
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

    private void generateMusicInfo(String name, Media m) {
        MusicInfo mi = new MusicInfo();
        mi.media = m;
        mi.secs = getSeconds(m);
        mi.musicName = name;
        musicInfoList.add(mi);
    }

    private String getName(String path) {//生成文件名
        StringBuilder sb = new StringBuilder(path);
        int f = sb.lastIndexOf(".");
        int t = sb.lastIndexOf("/");
        return sb.substring(t + 1, f);
    }

    private int getSeconds(Media m) {
        return 1;//TODO 生成时间
    }

    public void stop() {
        nowMedia.pause();
    }

    public void insertMusic(int index) {//点歌台，强行插入音乐
        playingMusicList = new ArrayList<>();
        playingMusicList.add(totalMusic.get(index));
        run();
    }
    protected void next(List<Media> mediaList){
        mediaList.remove(0);
    }
    public abstract void next();
    public abstract void addMusic(Pursuance pursuance);
}
