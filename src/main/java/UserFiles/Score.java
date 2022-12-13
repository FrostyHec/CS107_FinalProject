package UserFiles;

import java.io.Serializable;

public abstract class Score implements Serializable {
    private int playTimes;
    private int winTimes;
    private int nowContinueWin;

    private int longestContinueWin;

    public int getPlayTimes() {
        return playTimes;
    }

    public void addAPlayTimes() {
        playTimes++;
    }

    public int getWinTimes() {
        return winTimes;
    }

    public void addAWinTimes() {
        winTimes++;
        addANContinueWin();
    }

    public double getWinRates() {
        return (double)  winTimes/playTimes;
    }

    private void addANContinueWin() {
        nowContinueWin++;
        if (nowContinueWin > longestContinueWin) {
            longestContinueWin = nowContinueWin;
        }
    }

    public void breakContinueWin() {
        nowContinueWin = 0;
    }

    public int getLongestContinueWin() {
        return longestContinueWin;
    }
}
