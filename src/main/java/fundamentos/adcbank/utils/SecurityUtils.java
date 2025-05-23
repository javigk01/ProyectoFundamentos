package fundamentos.adcbank.utils;

public class SecurityUtils {
    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }
}