package org.example.bancoadc;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class ConfirmarEnvioLlaveController {
    @FXML
    private Button btnX;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnVolver;

    @FXML
    private Label lblExito;

    @FXML
    private void initialize() {
        btnVolver.setVisible(false);
        lblExito.setVisible(false);
    }

    @FXML
    private void pressBtnX() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AsignarMontoLlave.fxml"));
            AnchorPane asignarMontoView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnX.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(asignarMontoView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressBtnConfirmar() {

        lblExito.setVisible(true);
        btnVolver.setVisible(true);
        btnConfirmar.setVisible(false);
        btnX.setVisible(false);
    }

    @FXML
    private void pressBtnVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
            AnchorPane principalView = loader.load();
            
            MenuPrincipalController controller = loader.getController();
            controller.setNombreUsuario(Sesion.getUsuario()); // Recupera el nombre del usuario
            
            AnchorPane root = (AnchorPane) btnVolver.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(principalView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
