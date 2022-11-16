module travelu.client {

    requires java.net.http;
    requires com.google.gson;

    requires transitive travelu.core;

    exports travelu.client;
}
