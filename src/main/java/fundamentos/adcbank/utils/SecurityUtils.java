package fundamentos.adcbank.utils;

/**
 * @brief Utility class for security-related operations in the ADCBank application.
 */
public class SecurityUtils {

    /**
     * @brief Verifies if the input password matches the stored password.
     * @param inputPassword The password provided by the user.
     * @param storedPassword The password stored in the system.
     * @return True if the passwords match, false otherwise.
     */
    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }
}