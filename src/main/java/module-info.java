module com.example.darkchess {
    requires javafx.controls;
    requires javafx.fxml;


    opens GameArea to javafx.fxml;
    exports GameArea;
    exports GameLogic;
    opens GameLogic to javafx.fxml;
}