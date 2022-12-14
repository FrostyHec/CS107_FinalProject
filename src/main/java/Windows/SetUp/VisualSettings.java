package Windows.SetUp;

import javafx.application.Application;

import java.io.Serializable;
import java.util.Locale;

public class VisualSettings implements Serializable {
    private SkinList skin;
    private Locale language;

    private boolean visualEffect;

    public String getSkin() {
        return skin.getName();
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
}
