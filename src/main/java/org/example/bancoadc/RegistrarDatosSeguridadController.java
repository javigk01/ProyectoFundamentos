package org.example.bancoadc;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

import org.example.bancoadc.bd.UsuarioDAO;
import org.example.bancoadc.modelo.Usuario;
import org.example.bancoadc.modelo.UsuarioRegistroTemp;
import javafx.scene.control.Label;

public class RegistrarDatosSeguridadController {

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnFinalizar;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtPregunta;

    @FXML
    private TextField txtRespuesta;

    @FXML
    private TextField txtContrasena;

    @FXML
    private TextField txtPIN;

    @FXML
    private Label lblError;

    @FXML
    private void initialize() {
        lblError.setVisible(false);
    }

    @FXML
    private void pressBtnVolver() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarDatosContacto.fxml"));
            AnchorPane registrarContactoView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnVolver.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(registrarContactoView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressBtnFinalizar() {
        // Validación de campos vacíos
        if (txtUsuario.getText().isEmpty() ||
            txtContrasena.getText().isEmpty() ||
            txtPregunta.getText().isEmpty() ||
            txtRespuesta.getText().isEmpty() ||
            txtPIN.getText().isEmpty()) {

            lblError.setVisible(true);
            return;
        }

        lblError.setVisible(false);

        UsuarioRegistroTemp.usuario = txtUsuario.getText();
        UsuarioRegistroTemp.contrasena = txtContrasena.getText();
        UsuarioRegistroTemp.pregunta = txtPregunta.getText();
        UsuarioRegistroTemp.respuesta = txtRespuesta.getText();
        UsuarioRegistroTemp.pin = txtPIN.getText();

        Usuario nuevoUsuario = new Usuario(
            UsuarioRegistroTemp.nombreCompleto,
            UsuarioRegistroTemp.tipoDoc,
            UsuarioRegistroTemp.doc,
            UsuarioRegistroTemp.fechaNacimiento,
            UsuarioRegistroTemp.pais,
            UsuarioRegistroTemp.genero,
            UsuarioRegistroTemp.direccion,
            UsuarioRegistroTemp.telefono,
            UsuarioRegistroTemp.correo,
            UsuarioRegistroTemp.estrato,
            UsuarioRegistroTemp.ciudad,
            txtUsuario.getText(),
            txtContrasena.getText(),
            txtPregunta.getText(),
            txtRespuesta.getText(),
            txtPIN.getText()
        );

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean registrado = usuarioDAO.guardarUsuario(nuevoUsuario);

        if (registrado) {
            System.out.println("Usuario registrado exitosamente");
        } else {
            System.out.println("Error al registrar el usuario");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IniciarSesion.fxml"));
            AnchorPane iniciarSesionView = loader.load();
            AnchorPane root = (AnchorPane) btnFinalizar.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(iniciarSesionView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void writeTxtUsuario() {
    }

    @FXML
    private void writeTxtPregunta() {
    }

    @FXML
    private void writeTxtRespuesta() {
    }

    @FXML
    private void writeTxtContrasena() {
    }

    @FXML
    private void writeTxtPIN() {
    }
    
}
