package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Observable;

public class AuthenticationService extends Observable {
    private static AuthenticationService instance;
    private Document currentUser;
    private String currentToken;

    private AuthenticationService() {}

    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }

    public boolean login(String username, String password) {
        MongoCollection<Document> users = DatabaseService.getInstance().getDatabase().getCollection("users");
        Document query = new Document("username", username).append("password", password);
        currentUser = users.find(query).first();
        if (currentUser != null) {
            currentToken = generateToken();
            setChanged();
            notifyObservers(currentToken);
            return true;
        }
        return false;
    }

    public boolean register(String username, String password) {
        MongoCollection<Document> users = DatabaseService.getInstance().getDatabase().getCollection("users");
        // Check if username already exists
        Document query = new Document("username", username);
        if (users.find(query).first() != null) {
            return false; // Username already exists
        }

        // Create new user
        Document newUser = new Document("username", username)
                .append("password", password) // In production, hash the password
                .append("_id", "user" + (users.countDocuments() + 1)); // Simple ID generation
        users.insertOne(newUser);
        return true;
    }

    public void logout() {
        currentUser = null;
        currentToken = null;
        setChanged();
        notifyObservers(currentToken);
    }

    public Document getCurrentUser() {
        return currentUser;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    private String generateToken() {
        return "token-" + System.currentTimeMillis();
    }

    public boolean validateToken(String token) {
        if (token == null || currentToken == null) {
            return false;
        }
        return token.equals(currentToken) && currentUser != null;
    }
}