module com.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;

    opens application to javafx.fxml;
    exports application;

    opens controller to javafx.fxml;
    exports controller;

    opens model to javafx.fxml;
    exports model;

    opens view to javafx.fxml;
    exports view;

    opens utils to javafx.fxml;
    exports utils;
}
