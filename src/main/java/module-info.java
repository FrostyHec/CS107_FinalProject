module com.example.darkchess {
    requires javafx.controls;
    requires javafx.fxml;


    opens Windows.GameArea to javafx.fxml;
    exports Windows.GameArea;
    exports GameLogic;
    opens GameLogic to javafx.fxml;
    exports Windows.StartMenu;
    opens Windows.StartMenu to javafx.fxml;
}