module com.example.darkchess {
    requires javafx.controls;
    requires javafx.fxml;


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

}