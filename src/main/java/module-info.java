module com.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;

    opens application to javafx.fxml;
    exports application;

    opens chart to javafx.fxml;
    exports chart;

    opens search to javafx.fxml;
    exports search;
}
