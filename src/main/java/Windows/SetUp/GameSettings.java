package Windows.SetUp;

import java.io.Serializable;

public class GameSettings implements Serializable {
    private boolean isPVECanCheat,isPVECanRetract,isPVPCanCheat,isPVPCanRetract;

    public boolean isPVECanCheat() {
        return isPVECanCheat;
    }

    public void setPVECanCheat(boolean PVECanCheat) {
        isPVECanCheat = PVECanCheat;
    }

    public boolean isPVECanRetract() {
        return isPVECanRetract;
    }

    public void setPVECanRetract(boolean PVECanRetract) {
        isPVECanRetract = PVECanRetract;
    }

    public boolean isPVPCanCheat() {
        return isPVPCanCheat;
    }

    public void setPVPCanCheat(boolean PVPCanCheat) {
        isPVPCanCheat = PVPCanCheat;
    }

    public boolean isPVPCanRetract() {
        return isPVPCanRetract;
    }

    public void setPVPCanRetract(boolean PVPCanRetract) {
        isPVPCanRetract = PVPCanRetract;
    }
}
