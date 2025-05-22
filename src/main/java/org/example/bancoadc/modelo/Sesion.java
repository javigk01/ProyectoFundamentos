package org.example.bancoadc.modelo;

public class Sesion {

    private static String usuario;

    public static void setUsuario(String usuario) {
        Sesion.usuario = usuario;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void cerrarSesion() {
        usuario = null;
    }
}
