module com.example.darkchess {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires com.sun.jna.platform;
    requires com.sun.jna;

    opens Windows.GameArea to javafx.fxml;
    exports Windows.GameArea;
    exports GameLogic;
    opens GameLogic to javafx.fxml;
    exports Windows.StartMenu;
    opens Windows.StartMenu to javafx.fxml;
    exports Windows.SetUp;
    opens Windows.SetUp to javafx.fxml;
    exports InternetGaming.GameArea;
    opens InternetGaming.GameArea to javafx.fxml;
    exports InternetGaming.Internet;
    opens InternetGaming.Internet to javafx.fxml;
    exports Windows.Userfiles;
    opens Windows.Userfiles to javafx.fxml;
    exports Windows.GameArea.Extract;
    opens Windows.GameArea.Extract to javafx.fxml;
    exports Windows.GameArea.Extract.Music;
    opens Windows.GameArea.Extract.Music to javafx.fxml;
    exports Windows.GameArea.Extract.Music.SoundEffect;
    opens Windows.GameArea.Extract.Music.SoundEffect to javafx.fxml;
    exports Windows.GameArea.Extract.Music.Music;
    opens Windows.GameArea.Extract.Music.Music to javafx.fxml;
    exports Windows.RankingList;
    opens Windows.RankingList to javafx.fxml;

}