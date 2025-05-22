package org.example.bancoadc;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class EliminarLlaveController {
    @FXML
    private Button btnX;

    @FXML
    private void pressBtnX() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuLlaves.fxml"));
            AnchorPane llavesView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnX.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(llavesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}