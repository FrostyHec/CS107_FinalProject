package Windows.Userfiles;

import GameLogic.Game;
import UserFiles.User;
import UserFiles.UserManager;
import units.Deserialize;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveList {
    private Map<String, Game> saveList;
    private User nowPlay;

    public SaveList() {
        saveList = new HashMap<>();
        try {
            UserManager u = UserManager.read();
            nowPlay = u.nowPlay();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        read();
    }

    public Map<String, Game> getSaveList() {
        return saveList;
    }

    private void read() {
        try {
            Files.walkFileTree(Path.of(nowPlay.getSavePath()),
                    new SimpleFileVisitor<Path>() {
                        // 遍历导入
                        @Override
                        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                            try {
                                if (!path.toFile().getName().contains(".ser")) {
                                    return FileVisitResult.CONTINUE;
                                }
                                System.out.println(path);
                                saveList.put(path.toFile().getName(),Deserialize.load(path.toString()) );
                            } catch (Exception e) {
                                //e.printStackTrace();
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Path generateSavePath(String path) {
        return Path.of(nowPlay.getSavePath() + "/" + path);
    }

    public void deleteSave(String path) {
        try {
            Files.delete(generateSavePath(path));
            saveList.remove(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
