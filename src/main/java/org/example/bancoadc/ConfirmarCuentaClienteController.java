package org.example.bancoadc;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ConfirmarCuentaClienteController {

    @FXML
    private Button btnX;

    @FXML
    private Button btnConfirmar;

    @FXML
    private void pressBtnX() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EnviarCliente.fxml"));
            AnchorPane enviarClienteView = loader.load();
    
            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnX.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(enviarClienteView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressBtnConfirmar() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AsignarMontoCliente.fxml"));
            AnchorPane asignarMontoView = loader.load();
    
            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnConfirmar.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(asignarMontoView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
