package fundamentos.adcbank.utils;

import java.util.UUID;

/**
 * @brief Utility class for generating authentication tokens in the ADCBank application.
 */
public class TokenGenerator {

    /**
     * @brief Generates a unique token using UUID.
     * @return A randomly generated token as a String.
     */
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}