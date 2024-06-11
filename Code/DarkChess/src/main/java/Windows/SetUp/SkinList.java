package Windows.SetUp;

import javafx.application.Application;

public enum SkinList {
    Default("default.css"),
    Black("black.css"),
    White("white.css")
    ;
    SkinList(String name){
        this.name=name;
    }
    private final String name;

    public String getName() {
        return name;
    }
}
