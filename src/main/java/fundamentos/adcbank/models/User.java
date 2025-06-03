package fundamentos.adcbank.models;

/**
 * @brief Model class representing a user in the ADCBank application.
 */
public class User {

    /** @brief The unique identifier of the user. */
    private String id;

    /** @brief The username of the user. */
    private String username;

    /** @brief The password of the user. */
    private String password;

    /** @brief The email address of the user. */
    private String email;

    /**
     * @brief Gets the user ID.
     * @return The user ID.
     */
    public String getId() {
        return id;
    }

    /**
     * @brief Sets the user ID.
     * @param id The user ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @brief Gets the username.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @brief Sets the username.
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @brief Gets the user password.
     * @return The user password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @brief Sets the user password.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @brief Gets the user email.
     * @return The user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @brief Sets the user email.
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}