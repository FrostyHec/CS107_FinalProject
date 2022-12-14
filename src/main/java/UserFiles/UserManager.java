package UserFiles;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class UserManager implements Serializable {
    public static final String url = "Userfile/UserManager.ser";
    private List<User> users = new ArrayList<>();
    private int nowPlayUid;

    public UserManager() {

    }

    public void generateUser(String name) {
        users.add(new User(name, IndexToUid(users.size())));
        save();
    }

    public void renameUser(int uid, String newName) {
        for (User u : users) {
            if (u.getUid() == uid) {
                u.renameUser(newName);
                save();
                return;
            }
        }
    }

    public User getUser(int uid) {
        return users.get(UidToIndex(uid));
    }

    public List<User> getUserList() {
        return users;
    }

    public void save() {
        try {
            Files.deleteIfExists(Paths.get(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(url))) {
            out.writeObject(this);
            System.out.println("UserManager is saved.");
        } catch (IOException i) {
            i.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static UserManager read() throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(url))) {
            return (UserManager) in.readObject();
        } catch (IOException i) {
            throw new Exception("Manager not found! May be a new player");
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            throw new Exception("Unexpected ERROR while loading UserManager");
            // c.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        UserManager a = new UserManager();
        a.generateUser("默认用户");
        a.setNowPlayUid(1);
        a.save();
        System.out.println("测试结束");
    }

    public User nowPlay() {
        return getUser(nowPlayUid);
    }

    public void setNowPlayUid(int nowPlayUid) {
        this.nowPlayUid = nowPlayUid;
    }

    public void deleteUser(int uid) {
        int index = UidToIndex(uid);
        try {
            users.get(index).delete();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        users.remove(index);
        refreshUID();
        save();
    }

    private void refreshUID() {
        for (User u : users) {
            u.renameFile(IndexToUid(users.indexOf(u)));
        }
    }

    private int IndexToUid(int index) {
        return index + 1;
    }

    private int UidToIndex(int uid) {
        return uid - 1;
    }

    public void switchUser(int uid) {
        setNowPlayUid(uid);
    }

    public static void deleteAll() {
        try {
            Files.walkFileTree(Paths.get("Userfile"),
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
            Files.createFile(Paths.get("Userfile"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
