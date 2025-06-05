package fundamentos.adcbank.services;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @brief Service class for handling email verification in the ADCBank application.
 */
public class EmailVerificationService {

    /** @brief Singleton instance of the EmailVerificationService. */
    private static EmailVerificationService instance;

    /** @brief Map to store verification codes with their expiration times. */
    private final ConcurrentHashMap<String, VerificationData> verificationCodes;

    /** @brief Scheduler for cleaning up expired codes. */
    private final ScheduledExecutorService scheduler;

    /** @brief Code expiration time in minutes. */
    private static final int CODE_EXPIRATION_MINUTES = 10;

    /**
     * @brief Private constructor to enforce singleton pattern.
     */
    private EmailVerificationService() {
        this.verificationCodes = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);

        // Clean up expired codes every 5 minutes
        scheduler.scheduleAtFixedRate(this::cleanupExpiredCodes, 5, 5, TimeUnit.MINUTES);
    }

    /**
     * @brief Gets the singleton instance of the EmailVerificationService.
     * @return The EmailVerificationService instance.
     */
    public static EmailVerificationService getInstance() {
        if (instance == null) {
            instance = new EmailVerificationService();
        }
        return instance;
    }

    /**
     * @brief Generates and sends a verification code to the specified email.
     * @param email The email address to send the verification code to.
     * @param username The username associated with the email.
     * @return True if the code was sent successfully, false otherwise.
     */
    public boolean sendVerificationCode(String email, String username) {
        try {
            // Generate a 6-digit verification code
            String code = generateVerificationCode();
            long expirationTime = System.currentTimeMillis() + (CODE_EXPIRATION_MINUTES * 60 * 1000);

            // Store the verification data
            verificationCodes.put(email, new VerificationData(code, expirationTime, username));

            // Send the email
            EmailService emailService = EmailService.getInstance();
            return emailService.sendVerificationEmail(email, username, code);

        } catch (Exception e) {
            System.err.println("Error sending verification code: " + e.getMessage());
            return false;
        }
    }

    /**
     * @brief Verifies the provided code against the stored code for the email.
     * @param email The email address to verify.
     * @param providedCode The code provided by the user.
     * @return True if the code is valid and not expired, false otherwise.
     */
    public boolean verifyCode(String email, String providedCode) {
        VerificationData data = verificationCodes.get(email);

        if (data == null) {
            return false; // No code found for this email
        }

        if (System.currentTimeMillis() > data.expirationTime) {
            verificationCodes.remove(email); // Remove expired code
            return false; // Code expired
        }

        boolean isValid = data.code.equals(providedCode);
        if (isValid) {
            verificationCodes.remove(email); // Remove used code
        }

        return isValid;
    }

    /**
     * @brief Removes expired verification codes from the map.
     */
    private void cleanupExpiredCodes() {
        long currentTime = System.currentTimeMillis();
        verificationCodes.entrySet().removeIf(entry ->
                currentTime > entry.getValue().expirationTime
        );
    }

    /**
     * @brief Generates a random 6-digit verification code.
     * @return A 6-digit verification code as a string.
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(code);
    }

    /**
     * @brief Checks if a verification code exists for the given email.
     * @param email The email to check.
     * @return True if a code exists and is not expired, false otherwise.
     */
    public boolean hasValidCode(String email) {
        VerificationData data = verificationCodes.get(email);
        if (data == null) {
            return false;
        }

        if (System.currentTimeMillis() > data.expirationTime) {
            verificationCodes.remove(email);
            return false;
        }

        return true;
    }

    /**
     * @brief Gets the username associated with a verification code.
     * @param email The email address.
     * @return The username, or null if not found.
     */
    public String getUsername(String email) {
        VerificationData data = verificationCodes.get(email);
        return data != null ? data.username : null;
    }

    /**
     * @brief Shuts down the cleanup scheduler.
     */
    public void shutdown() {
        scheduler.shutdown();
    }

    /**
     * @brief Inner class to hold verification data.
     */
    private static class VerificationData {
        final String code;
        final long expirationTime;
        final String username;

        VerificationData(String code, long expirationTime, String username) {
            this.code = code;
            this.expirationTime = expirationTime;
            this.username = username;
        }
    }
}