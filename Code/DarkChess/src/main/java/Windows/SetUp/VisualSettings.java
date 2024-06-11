package Windows.SetUp;

import javafx.application.Application;

import java.io.Serializable;
import java.util.Locale;

public class VisualSettings implements Serializable {
    private SkinList skin;
    private Locale language;

    private boolean visualEffect;
    private boolean visualAlarm;

    public String getSkin() {
        return skin.getName();
    }
    public SkinList getSkinInList(){
        return skin;
    }

    public void setSkinName(SkinList skin) {
        this.skin = skin;
    }

    public boolean isVisualEffect() {
        return visualEffect;
    }

    public void setVisualEffect(boolean visualEffect) {
        this.visualEffect = visualEffect;
    }


    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public void setLanguage(Language language) {
        setLanguage(language.getLocale());
    }

    public boolean isVisualAlarm() {
        return visualAlarm;
    }

    public void setVisualAlarm(boolean visualAlarm) {
        this.visualAlarm = visualAlarm;
    }
}
