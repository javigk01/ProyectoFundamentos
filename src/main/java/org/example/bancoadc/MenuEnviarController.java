package org.example.bancoadc;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.example.bancoadc.modelo.Sesion;

public class MenuEnviarController {

    @FXML
    private Button btnX;

    @FXML
    private Button btnEnviarLlave;

    @FXML
    private Button btnEnviarCliente;

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

    @FXML
    private void pressBtnEnviarLlave() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EnviarLlave.fxml"));
            AnchorPane enviarLlaveView = loader.load();
    
            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnEnviarLlave.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(enviarLlaveView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressBtnEnviarCliente() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EnviarCliente.fxml"));
            AnchorPane enviarClienteView = loader.load();
    
            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnEnviarCliente.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(enviarClienteView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
