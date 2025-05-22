package org.example.bancoadc;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AsignarMontoLlaveController {

    @FXML
    private Button btnX;

    @FXML
    private Button btnEnviar;

    @FXML
    private void pressBtnX() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmarCuentaLlave.fxml"));
            AnchorPane confirmarCuentaView = loader.load();
    
            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnX.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(confirmarCuentaView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void pressBtnEnviar() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmarEnvioLlave.fxml"));
            AnchorPane confirmarEnvioView = loader.load();
    
            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnEnviar.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(confirmarEnvioView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
