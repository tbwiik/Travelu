module travelu.localpersistence {

    requires com.google.gson;

    requires transitive travelu.core;

    opens travelu.localpersistence to javafx.fxml, com.google.gson;

    exports travelu.localpersistence;
}
