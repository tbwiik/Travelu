module gr2219 {
    requires javafx.controls;
    requires javafx.fxml;

    opens gr2219 to javafx.fxml;
    exports gr2219;
}
