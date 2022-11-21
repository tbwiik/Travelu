module travelu.fxui {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    requires travelu.client;

    opens travelu.fxui to javafx.fxml, javafx.graphics, org.testfx;
}
