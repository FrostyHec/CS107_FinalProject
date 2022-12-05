package UserFiles;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class TimeList implements Serializable {
    private long playSec;
    private LocalDate playDate;

    public TimeList() {
        initialize();
    }

    public LocalDate getPlayDate() {
        return playDate;
    }

    public long getPlaySec() {
        return playSec;
    }

    public void initialize() {
        playDate = LocalDate.now();
        playSec = 0;
    }

    public void addSec(int sec) {
        playSec += sec;
    }

    public Period getPlayingDays() {
        return Period.between(playDate, LocalDate.now());
    }
}
