module fundamentos.adcbank {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    opens fundamentos.adcbank to javafx.fxml;
    opens fundamentos.adcbank.controllers to javafx.fxml;

    exports fundamentos.adcbank;
    exports fundamentos.adcbank.controllers to javafx.fxml;
}
