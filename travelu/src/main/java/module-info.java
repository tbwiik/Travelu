module gr2219.gr2219 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens gr2219 to javafx.fxml, com.google.gson;
    opens gr2219.backend to com.google.gson;

    exports gr2219;
}
