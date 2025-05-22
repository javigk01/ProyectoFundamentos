package org.example.bancoadc.bd;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.bancoadc.modelo.Usuario;

public class UsuarioDAO {

    private MongoCollection<Document> collection;

        public UsuarioDAO() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017"); // O tu cadena de conexión
        MongoDatabase database = mongoClient.getDatabase("bancoadc");         // <-- cámbialo si es otro nombre
        collection = database.getCollection("usuarios");                             // <-- cámbialo si es otro nombre
    }

    public boolean guardarUsuario(Usuario usuario) {
        // Primero validar si existe usuario
        Document filtro = new Document("usuario", usuario.getUsuario());
        Document existe = collection.find(filtro).first();
    
        if (existe != null) {
            // Ya existe un usuario con ese nombre, no guardamos y retornamos false
            return false;
        }
    
        Document doc = new Document()
                .append("nombreCompleto", usuario.getNombreCompleto())
                .append("tipoDoc", usuario.getTipoDoc())
                .append("doc", usuario.getDoc())
                .append("fechaNacimiento", usuario.getFechaNacimiento())
                .append("pais", usuario.getPais())
                .append("genero", usuario.getGenero())
                .append("direccion", usuario.getDireccion())
                .append("telefono", usuario.getTelefono())
                .append("correo", usuario.getCorreo())
                .append("estrato", usuario.getEstrato())
                .append("ciudad", usuario.getCiudad())
                .append("usuario", usuario.getUsuario())
                .append("contrasena", usuario.getContrasena())
                .append("pregunta", usuario.getPregunta())
                .append("respuesta", usuario.getRespuesta())
                .append("pin", usuario.getPin());
    
        collection.insertOne(doc);
        return true;
    }
    

    public boolean validarUsuario(String usuario, String contrasena) {
        Document filtro = new Document("usuario", usuario).append("contrasena", contrasena);
        Document encontrado = collection.find(filtro).first();
        return encontrado != null;
    }
    
    public String obtenerNombreCompleto(String usuario) {
        Document filtro = new Document("usuario", usuario);
        Document encontrado = collection.find(filtro).first();
        return encontrado != null ? encontrado.getString("nombreCompleto") : "";
    }
    
         
}
