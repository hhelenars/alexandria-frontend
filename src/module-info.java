module Front_Alexandria2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics; 
    requires transitive java.logging;
    requires javafx.base;
    requires java.desktop;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires com.google.gson;
    requires okhttp3;
    requires kotlin.stdlib;
    requires javafx.web;
    requires epublib.core;
    requires org.slf4j;

    //  para que Gson pueda acceder a la clase Libro
    exports application to com.google.gson;

    opens application to javafx.graphics, javafx.fxml, com.google.gson;
}