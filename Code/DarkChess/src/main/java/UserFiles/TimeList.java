package UserFiles;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class TimeList implements Serializable {
    private long playSec;
    private LocalDate createDate;

    public TimeList() {
        initialize();
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public long getPlaySec() {
        return playSec;
    }

    public void initialize() {
        createDate = LocalDate.now();
        playSec = 0;
    }

    public void addSec(long sec) {
        playSec += sec;
    }

    public Period getPlayingDays() {
        return Period.between(createDate, LocalDate.now());
    }
}
