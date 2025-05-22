package org.example.bancoadc;

import java.io.IOException;

import org.example.bancoadc.bd.UsuarioDAO;
import org.example.bancoadc.modelo.Sesion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class IniciarSesionController {
    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtContrasena;

    @FXML
    private Label lblMensajeError;


    @FXML
    private void initialize() {
        lblMensajeError.setVisible(false);
    }

    @FXML
    private void pressBtnIniciarSesion() {


        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();
    
        // Verifica si se dejaron campos vacíos
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            System.out.println("Por favor, ingrese usuario y contraseña.");
            return;
        }
    
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean existe = usuarioDAO.validarUsuario(usuario, contrasena);
    
        if (existe) {
            try {
                Sesion.setUsuario(usuario); // Guarda el nombre del usuario en sesión

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
                AnchorPane menuPrincipalView = loader.load();

                MenuPrincipalController controller = loader.getController();
                controller.setNombreUsuario(Sesion.getUsuario());

                AnchorPane root = (AnchorPane) btnIniciarSesion.getScene().getRoot();
                root.getChildren().clear();
                root.getChildren().add(menuPrincipalView);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
                 
    }


    @FXML
    private void pressBtnRegistrarse() {

        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarDatosPersonales.fxml"));
            AnchorPane registrarPersonalesView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnIniciarSesion.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(registrarPersonalesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void writeTxtUsuario() {

    }

    @FXML
    private void writeTxtContrasena() {

    }
    
}

