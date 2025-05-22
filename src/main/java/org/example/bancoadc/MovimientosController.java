package org.example.bancoadc;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import org.example.bancoadc.modelo.Sesion;

public class MovimientosController {
    
    @FXML
    private Button btnX;

    @FXML
    private void pressBtnX() {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
                AnchorPane principalView = loader.load();

                MenuPrincipalController controller = loader.getController();
                controller.setNombreUsuario(Sesion.getUsuario()); // Recupera el nombre del usuario

                AnchorPane root = (AnchorPane) btnX.getScene().getRoot();
                root.getChildren().clear();
                root.getChildren().add(principalView);

            } catch (IOException e) {
                e.printStackTrace();
            }
        
    }
}