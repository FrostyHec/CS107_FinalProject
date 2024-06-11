package Windows.SetUp;

public class StartSettings implements java.io.Serializable {
    private boolean alwaysPvE = false;
    private boolean alwaysPvP = false;
    private boolean alwaysLatestSave=false;

    public boolean isAlwaysPvE() {
        return alwaysPvE;
    }

    public void setAlwaysPvE(boolean alwaysPvE) {
        this.alwaysPvE = alwaysPvE;
    }

    public boolean isAlwaysPvP() {
        return alwaysPvP;
    }

    public void setAlwaysPvP(boolean alwaysPvP) {
        this.alwaysPvP = alwaysPvP;
    }

    public boolean isAlwaysLatestSave() {
        return alwaysLatestSave;
    }

    public void setAlwaysLatestSave(boolean alwaysLatestSave) {
        this.alwaysLatestSave = alwaysLatestSave;
    }
}
