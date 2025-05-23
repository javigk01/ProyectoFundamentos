package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Observable;

/**
 * @brief Service class for handling user authentication in the ADCBank application.
 */
public class AuthenticationService extends Observable {

    /** @brief Singleton instance of the AuthenticationService. */
    private static AuthenticationService instance;

    /** @brief The currently logged-in user. */
    private Document currentUser;

    /** @brief The current authentication token. */
    private String currentToken;

    /**
     * @brief Private constructor to enforce singleton pattern.
     */
    private AuthenticationService() {}

    /**
     * @brief Gets the singleton instance of the AuthenticationService.
     * @return The AuthenticationService instance.
     */
    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }

    /**
     * @brief Authenticates a user with the provided credentials.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if login is successful, false otherwise.
     */
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

    /**
     * @brief Registers a new user with the provided credentials and creates a default account.
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return True if registration and account creation are successful, false if the username already exists.
     */
    public boolean register(String username, String password) {
        MongoCollection<Document> users = DatabaseService.getInstance().getDatabase().getCollection("users");
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");

        // Check if username already exists
        Document query = new Document("username", username);
        if (users.find(query).first() != null) {
            return false;
        }

        // Create new user
        String userId = "USER" + (users.countDocuments() + 1);
        Document newUser = new Document("username", username)
                .append("password", password)
                .append("_id", userId);

        // Create default account for the user
        String accountId = "ACCOUNT" + (accounts.countDocuments() + 1);
        Document newAccount = new Document("_id", accountId)
                .append("userId", userId)
                .append("type", "SAVINGS")
                .append("balance", 0);

        try {
            // Insert user and account as a single transaction
            users.insertOne(newUser);
            accounts.insertOne(newAccount);
            return true;
        } catch (Exception e) {
            // Log error and rollback if necessary (MongoDB transactions can be used for atomicity)
            System.err.println("Error during registration: " + e.getMessage());
            return false;
        }
    }

    /**
     * @brief Logs out the current user.
     */
    public void logout() {
        currentUser = null;
        currentToken = null;
        setChanged();
        notifyObservers(currentToken);
    }

    /**
     * @brief Gets the currently logged-in user.
     * @return The current user Document, or null if no user is logged in.
     */
    public Document getCurrentUser() {
        return currentUser;
    }

    /**
     * @brief Gets the current authentication token.
     * @return The current token, or null if no user is logged in.
     */
    public String getCurrentToken() {
        return currentToken;
    }

    /**
     * @brief Generates a new authentication token.
     * @return The generated token.
     */
    private String generateToken() {
        return "token-" + System.currentTimeMillis();
    }

    /**
     * @brief Validates the provided authentication token.
     * @param token The token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        if (token == null || currentToken == null) {
            return false;
        }
        return token.equals(currentToken) && currentUser != null;
    }

    /**
     * @brief Notifies observers of a balance update.
     */
    public void notifyBalanceUpdate() {
        setChanged();
        notifyObservers("balance_update");
    }
}