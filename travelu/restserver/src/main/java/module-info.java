open module restserver {

    requires travelu.core;
    requires travelu.localpersistence;

    requires spring.boot;
    requires spring.web;
    requires spring.boot.autoconfigure;

    requires com.google.gson;

    exports travelu.restserver;
}
