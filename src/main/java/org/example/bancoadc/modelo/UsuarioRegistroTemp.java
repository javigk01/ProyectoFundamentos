package org.example.bancoadc.modelo;

public class UsuarioRegistroTemp {
    public static String nombreCompleto;
    public static String tipoDoc;
    public static String doc;
    public static String fechaNacimiento;
    public static String pais;
    public static String genero;

    public static String direccion;
    public static String telefono;
    public static String correo;
    public static String estrato;
    public static String ciudad;

    public static String usuario;
    public static String contrasena;
    public static String pregunta;
    public static String respuesta;
    public static String pin;

    public static Usuario construirUsuario() {
        return new Usuario(
            nombreCompleto, tipoDoc, doc, fechaNacimiento, pais, genero,
            direccion, telefono, correo, estrato, ciudad,
            usuario, contrasena, pregunta, respuesta, pin
        );
    }

    public static void limpiar() {
        nombreCompleto = tipoDoc = doc = fechaNacimiento = pais = genero =
        direccion = telefono = correo = estrato = ciudad =
        usuario = contrasena = pregunta = respuesta = pin = null;
    }
}
