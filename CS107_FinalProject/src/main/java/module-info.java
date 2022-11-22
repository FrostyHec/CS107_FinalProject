module com.example.darkchess {
    requires javafx.controls;
    requires javafx.fxml;


    opens Entry to javafx.fxml;
    exports Entry;
    exports GameLogic;
    opens GameLogic to javafx.fxml;
}