package org.example.bancoadc.modelo;

public class Usuario {
    private String nombreCompleto;
    private String tipoDoc;
    private String doc;
    private String fechaNacimiento;
    private String pais;
    private String genero;
    private String direccion;
    private String telefono;
    private String correo; 
    private String estrato;
    private String ciudad;
    private String usuario;
    private String contrasena;
    private String pregunta;
    private String respuesta;
    private String pin;

    public Usuario(String nombreCompleto, String tipoDoc, String doc, String fechaNacimiento, String pais,
                   String genero, String direccion, String telefono, String correo, String estrato,
                   String ciudad, String usuario, String contrasena, String pregunta, String respuesta, String pin) {
        this.nombreCompleto = nombreCompleto;
        this.tipoDoc = tipoDoc;
        this.doc = doc;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
        this.genero = genero;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.estrato = estrato;
        this.ciudad = ciudad;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.pin = pin;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getDoc() {
        return doc;
    }
    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstrato() {
        return estrato;
    }
    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getPregunta() {
        return pregunta;
    }
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }

}
