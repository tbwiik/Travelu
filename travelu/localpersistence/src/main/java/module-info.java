module travelu.localpersistence {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires transitive travelu.core;

    opens travelu.localpersistence to javafx.fxml, com.google.gson;

    exports travelu.localpersistence;
}
