package UserFiles;

import java.util.*;

public class UserManager {
    private List<User> users = new ArrayList<>();


    public void generateUser(String name) {
        users.add(new User(name, users.size()));
    }

    public User getUser(int uid) {
        return users.get(uid);
    }
}
