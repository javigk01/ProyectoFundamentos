package org.example.bancoadc;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MenuPrincipalController {

    @FXML
    private Button btnLlaves;

    @FXML
    private Button btnMovimientos;

    @FXML
    private Button btnEnviar;

    @FXML
    private void pressBtnLlaves() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuLlaves.fxml"));
            AnchorPane llavesView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnLlaves.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(llavesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressBtnMovimientos() {
        try {
            // Cargar el archivo FXML de la vista de movimientos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Movimientos.fxml"));
            AnchorPane movimientosView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnMovimientos.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(movimientosView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressBtnEnviar() {
        try {
            // Cargar el archivo FXML de la vista de enviar
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuEnviar.fxml"));
            AnchorPane enviarView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnEnviar.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(enviarView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}