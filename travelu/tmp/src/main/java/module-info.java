module travelu {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens app to javafx.fxml, com.google.gson;
    opens app.core to com.google.gson;

    exports app;
}
