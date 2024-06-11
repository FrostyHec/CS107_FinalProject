package UserFiles;

import Windows.GameArea.Difficulty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ScoreList implements Serializable {
    private Map<Difficulty, AIScore> score;
    private int playTimes;
    private InternetScore internetScore;

    public ScoreList() {
        score = new HashMap<>();
        for (Difficulty d : Difficulty.values()) {
            score.put(d, new AIScore());
        }
        internetScore=new InternetScore();
    }

    public void addScore(Difficulty d, boolean isWin) {
        addAPlayTime();
        AIScore s = score.get(d);
        s.addAPlayTimes();
        if (isWin) {
            s.addAWinTimes();
        } else {
            s.breakContinueWin();
        }
    }

    public Map<Difficulty, AIScore> getScore() {
        return score;
    }

    private void addAPlayTime() {
        playTimes++;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public InternetScore getInternetScore() {
        return internetScore;
    }

    public void addInternetScore(boolean isWin) {
        internetScore.addAPlayTimes();
        if (isWin) {
            internetScore.addAWinTimes();
        } else {
            internetScore.breakContinueWin();
        }
    }

    public int getTotalPlayTimes() {
        int total = 0;
        for (AIScore a : score.values()) {
            total += a.getPlayTimes();
        }
        total += internetScore.getPlayTimes();
        return total;
    }
}

class AIScore extends Score {
//没区别
}

class InternetScore extends Score {
    //其实没啥区别，名字上做个标记预留而已
}
