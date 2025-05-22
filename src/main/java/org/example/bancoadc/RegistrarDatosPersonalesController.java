package org.example.bancoadc;

import java.io.IOException;

import org.example.bancoadc.modelo.UsuarioRegistroTemp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class RegistrarDatosPersonalesController {

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnContinuar;

    @FXML
    private TextField txtNombreCompleto;

    @FXML
    private TextField txtDoc;

    @FXML
    private ChoiceBox<String> chcDoc;

    @FXML
    private DatePicker dtFechaNacimiento;

    @FXML
    private TextField txtPais;

    @FXML
    private ChoiceBox<String> chcGenero;

    @FXML
    private Label lblError;

    @FXML
    private void initialize() {
        // Inicializar los ChoiceBox con opciones
        chcDoc.getItems().addAll("CC", "TI", "CE", "PP");
        chcGenero.getItems().addAll("Masculino", "Femenino", "No binario", "Prefiero no decirlo");
        lblError.setVisible(false);
    }

    @FXML
    private void pressBtnVolver() {
        try {
            // Cargar el archivo FXML de la vista de llaves
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IniciarSesion.fxml"));
            AnchorPane iniciarSesionView = loader.load();

            // Reemplazar la vista actual con la nueva vista
            AnchorPane root = (AnchorPane) btnVolver.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(iniciarSesionView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pressBtnContinuar() {
        // Validación de campos vacíos
        if (txtNombreCompleto.getText().isEmpty() ||
            chcDoc.getValue() == null ||
            txtDoc.getText().isEmpty() ||
            dtFechaNacimiento.getValue() == null ||
            txtPais.getText().isEmpty() ||
            chcGenero.getValue() == null) {
            
            lblError.setVisible(true);
            return;
        }

        lblError.setVisible(false); // Ocultar si todo está bien

        UsuarioRegistroTemp.nombreCompleto = txtNombreCompleto.getText();
        UsuarioRegistroTemp.tipoDoc = chcDoc.getValue();
        UsuarioRegistroTemp.doc = txtDoc.getText();
        UsuarioRegistroTemp.fechaNacimiento = dtFechaNacimiento.getValue().toString();
        UsuarioRegistroTemp.pais = txtPais.getText();
        UsuarioRegistroTemp.genero = chcGenero.getValue();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarDatosContacto.fxml"));
            AnchorPane registrarContactoView = loader.load();
            AnchorPane root = (AnchorPane) btnVolver.getScene().getRoot();
            root.getChildren().clear();
            root.getChildren().add(registrarContactoView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void writeTxtNombreCompleto() {
        
    }

    @FXML
    private void writeTxtDoc() {
        // Lógica para manejar el evento de escritura en el campo de texto
        // Puedes agregar validaciones o cualquier otra lógica necesaria
    }

    @FXML
    private void selectChcDoc() {
        // Lógica para manejar la selección del ChoiceBox
        // Puedes agregar validaciones o cualquier otra lógica necesaria
    }

    @FXML
    private void selectDtFechaNacimiento() {
        // Lógica para manejar la selección de la fecha
        // Puedes agregar validaciones o cualquier otra lógica necesaria
    }

    @FXML
    private void writeTxtPais() {
        // Lógica para manejar el evento de escritura en el campo de texto
        // Puedes agregar validaciones o cualquier otra lógica necesaria
    }

    @FXML
    private void selectChcGenero() {
        // Lógica para manejar la selección del ChoiceBox
        // Puedes agregar validaciones o cualquier otra lógica necesaria
    }
}
