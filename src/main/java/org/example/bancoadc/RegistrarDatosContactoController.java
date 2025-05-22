package org.example.bancoadc;

import java.io.IOException;

import org.example.bancoadc.modelo.UsuarioRegistroTemp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class RegistrarDatosContactoController {

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnContinuar;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreo;

    @FXML
    private ChoiceBox<String> chcEstrato;

    @FXML
    private TextField txtCiudad;

    @FXML
    private Label lblError;

    @FXML
    private void initialize() {
        // Inicializar los ChoiceBox con opciones
        chcEstrato.getItems().addAll("1", "2", "3", "4", "5", "6");
        lblError.setVisible(false);
    }

    @FXML
    private void pressBtnVolver() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarDatosPersonales.fxml"));
            AnchorPane registrarPersonalesView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnVolver.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(registrarPersonalesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressBtnContinuar() {
        // Validación de campos vacíos
        if (txtDireccion.getText().isEmpty() ||
            txtTelefono.getText().isEmpty() ||
            txtCorreo.getText().isEmpty() ||
            chcEstrato.getValue() == null ||
            txtCiudad.getText().isEmpty()) {

            lblError.setVisible(true);
            return;
        }

        lblError.setVisible(false);

        UsuarioRegistroTemp.direccion = txtDireccion.getText();
        UsuarioRegistroTemp.telefono = txtTelefono.getText();
        UsuarioRegistroTemp.correo = txtCorreo.getText();
        UsuarioRegistroTemp.estrato = chcEstrato.getValue();
        UsuarioRegistroTemp.ciudad = txtCiudad.getText();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarDatosSeguridad.fxml"));
            AnchorPane registrarSeguridadView = loader.load();
            AnchorPane root = (AnchorPane) btnVolver.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(registrarSeguridadView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void writeTxtDireccion() {
        // Lógica para manejar el evento de escritura en el campo de dirección
        // Aquí puedes agregar la lógica para manejar la entrada del usuario
    }

    @FXML
    private void writeTxtTelefono() {
        // Lógica para manejar el evento de escritura en el campo de teléfono
        // Aquí puedes agregar la lógica para manejar la entrada del usuario
    }

    @FXML
    private void writeTxtCorreo() {
        // Lógica para manejar el evento de escritura en el campo de correo
        // Aquí puedes agregar la lógica para manejar la entrada del usuario
    }

    @FXML
    private void selectChcEstrato() {
        // Lógica para manejar la selección del ChoiceBox de estrato
        // Aquí puedes agregar la lógica para manejar la selección del usuario
    }

    @FXML
    private void writeTxtCiudad() {
        // Lógica para manejar el evento de escritura en el campo de ciudad
        // Aquí puedes agregar la lógica para manejar la entrada del usuario
    }
    
}
