package Windows.SetUp;

public class StartMenu implements java.io.Serializable {
    private boolean alwaysPvE = false;
    private boolean alwaysPvP = false;

    private String skin="default.css";
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

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }
}
