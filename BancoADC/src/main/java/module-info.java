module org.example.bancoadc {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.bancoadc to javafx.fxml;
    exports org.example.bancoadc;
}