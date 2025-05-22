package org.example.bancoadc;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import org.example.bancoadc.modelo.Sesion;


public class MenuLlavesController {
    @FXML
    private Button btnX;

    @FXML
    private Button btnAgregar;

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
    private void pressBtnAgregar() {
        try {
            // Cargar el archivo FXML de la vista de crear o eliminar llave
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CrearLlave.fxml"));
            AnchorPane crearEliminarLlaveView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnAgregar.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(crearEliminarLlaveView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressBtnEliminar() {
        try {
            // Cargar el archivo FXML de la vista de crear o eliminar llave
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarLlave.fxml"));
            AnchorPane crearEliminarLlaveView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnAgregar.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(crearEliminarLlaveView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}