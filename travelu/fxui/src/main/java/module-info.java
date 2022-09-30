module travelu.fxui {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    requires travelu.core;
    requires travelu.fxutil;

    opens travelu.fxui to javafx.fxml;

}
