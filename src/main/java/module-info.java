module org.example.bancoadc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;

    // Si usas clases de junit en el módulo principal (poco común), agrega:
    // requires org.junit.jupiter.api;

    opens org.example.bancoadc to javafx.fxml;
    exports org.example.bancoadc;
}
