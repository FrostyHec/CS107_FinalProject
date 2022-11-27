package UserFiles;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private int uid;
    private String avatarUrl;
    private List<ScoreList> localScoreList=new ArrayList<>();
    private List<TimeList> localTimeList=new ArrayList<>();
    public User(){

    }
    public User(String name,int uid){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScoreList> getLocalScoreList() {
        return localScoreList;
    }

    public List<TimeList> getLocalTimeList() {
        return localTimeList;
    }
}
class ScoreList {
    public int score;
    public String filesUrl;
}
class TimeList {
}