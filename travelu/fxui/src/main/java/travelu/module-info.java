module travelu.fxui {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    requires travelu.core;
    requires travelu.localpersistence;

    opens travelu.fxui to javafx.fxml, javafx.graphics, org.testfx;
}
