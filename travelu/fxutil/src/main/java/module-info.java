module travelu.fxutil {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires transitive travelu.core;

    opens travelu.fxutil to javafx.fxml, com.google.gson;

    exports travelu.fxutil;
}
