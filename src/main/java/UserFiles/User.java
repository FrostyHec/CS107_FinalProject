package UserFiles;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String name;
    private String saveUrl;
    private int uid;
    private String avatarUrl;
    private List<ScoreList> localScoreList = new ArrayList<>();
    private TimeList timeList;

    private String LatestSaveName;

    private User() {

    }

    public User(String name, int uid) {
        this.name = name;
        this.uid = uid;
        createFile();
    }

    public void renameFile(int newUid) {
        File file = new File(saveUrl);
        renameUid(newUid);
        generateUrl();
        System.out.println(file.renameTo(new File(saveUrl)));
    }

    private void renameUid(int uid) {
        this.uid = uid;
    }

    private void createFile() {
        generateUrl();
        Path path = generatePath();
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateUrl() {
        saveUrl = "Userfile/" + uid;
        avatarUrl = "Userfile/" +uid +"/"+"avatar";
    }

    private Path generatePath() {//路径不能序列化，原因未知，所以每次都要生成一次地址；
        return Paths.get("Userfile", Integer.toString(uid));
    }

    public String getName() {
        return name;
    }

    public void setAvatarUrl(String relativeUrl) {
        avatarUrl = relativeUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScoreList> getLocalScoreList() {
        return localScoreList;
    }

    public TimeList getTimeList() {
        return timeList;
    }

    public long getUid() {
        return uid;
    }

    public String getSavePath() {
        return saveUrl;
    }

    public void delete() throws Exception {
        Files.walkFileTree(generatePath(),
                new SimpleFileVisitor<Path>() {
                    // 遍历删除文件
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        System.out.printf("文件被删除 : %s%n", file);
                        return FileVisitResult.CONTINUE;
                    }

                    //删除目录
                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        System.out.printf("文件夹被删除: %s%n", dir);
                        return FileVisitResult.CONTINUE;
                    }

                }
        );
    }

    public String getLatestSaveName() {
        return LatestSaveName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}

class ScoreList implements Serializable {
    private int score;
    private String filesUrl;

    private int nowContinueWin;

    private int longestContinueWin;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFilesUrl() {
        return filesUrl;
    }

    public void setFilesUrl(String filesUrl) {
        this.filesUrl = filesUrl;
    }
}

