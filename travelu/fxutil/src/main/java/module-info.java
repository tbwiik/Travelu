module travelu.fxutil {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens fxutil to javafx.fxml, com.google.gson;

    exports fxutil;
}
