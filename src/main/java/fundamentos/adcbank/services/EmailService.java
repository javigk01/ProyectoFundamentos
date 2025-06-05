package fundamentos.adcbank.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @brief Service class for sending email notifications in the ADCBank application.
 */
public class EmailService {

    /** @brief Singleton instance of the EmailService. */
    private static EmailService instance;

    /** @brief SMTP server host */
    private static final String SMTP_HOST = "smtp.gmail.com";

    /** @brief SMTP server port */
    private static final String SMTP_PORT = "587";

    /** @brief Bank email address (sender) - CAMBIA ESTE EMAIL */
    private static final String BANK_EMAIL = "bancoadc@gmail.com";

    /** @brief Bank email password - CAMBIA ESTA CONTRASEÑA DE APLICACIÓN */
    private static final String BANK_PASSWORD = "tqsn prle xocs mbkb";

    /**
     * @brief Private constructor to enforce singleton pattern.
     */
    private EmailService() {}

    /**
     * @brief Gets the singleton instance of the EmailService.
     * @return The EmailService instance.
     */
    public static EmailService getInstance() {
        if (instance == null) {
            instance = new EmailService();
        }
        return instance;
    }

    /**
     * @brief Sends a verification email with a code to the user.
     * @param recipientEmail The email address of the recipient.
     * @param username The username of the user.
     * @param verificationCode The verification code to send.
     * @return True if email was sent successfully, false otherwise.
     */
    public boolean sendVerificationEmail(String recipientEmail, String username, String verificationCode) {
        try {
            String subject = "ADC Bank - Email Verification";
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

            String body = String.format(
                    "Dear %s,\n\n" +
                            "Welcome to ADC Bank! To complete your registration, please verify your email address.\n\n" +
                            "Your verification code is: %s\n\n" +
                            "This code will expire in 10 minutes for security reasons.\n\n" +
                            "If you did not request this verification, please ignore this email.\n\n" +
                            "Verification Details:\n" +
                            "- Username: %s\n" +
                            "- Email: %s\n" +
                            "- Generated: %s\n\n" +
                            "Thank you for choosing ADC Bank!\n\n" +
                            "Best regards,\n" +
                            "ADC Bank Security Team\n\n" +
                            "This is an automated message, please do not reply.",
                    username, verificationCode, username, recipientEmail, timestamp
            );

            sendEmail(recipientEmail, subject, body);
            System.out.println("Verification email sent successfully to: " + recipientEmail);
            return true;
        } catch (Exception e) {
            System.err.println("Error sending verification email: " + e.getMessage());
            return false;
        }
    }

    /**
     * @brief Sends a transfer confirmation email to the sender.
     * @param senderEmail The email address of the sender.
     * @param senderName The name of the sender.
     * @param recipientAccountId The account ID of the recipient.
     * @param amount The amount transferred.
     * @param transactionId The transaction ID.
     */
    public void sendTransferConfirmationEmail(String senderEmail, String senderName,
                                              String recipientAccountId, double amount,
                                              String transactionId) {
        try {
            String subject = "ADC Bank - Transfer Confirmation";
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

            String body = String.format(
                    "Dear %s,\n\n" +
                            "Your transfer has been processed successfully.\n\n" +
                            "Transfer Details:\n" +
                            "- Amount: $%.2f\n" +
                            "- Recipient Account: %s\n" +
                            "- Transaction ID: %s\n" +
                            "- Date & Time: %s\n\n" +
                            "Thank you for using ADC Bank services.\n\n" +
                            "Best regards,\n" +
                            "ADC Bank Team\n\n" +
                            "This is an automated message, please do not reply.",
                    senderName, amount, recipientAccountId, transactionId, timestamp
            );

            sendEmail(senderEmail, subject, body);
            System.out.println("Transfer confirmation email sent successfully to: " + senderEmail);
        } catch (Exception e) {
            System.err.println("Error sending transfer confirmation email: " + e.getMessage());
        }
    }

    /**
     * @brief Sends a transfer received notification email to the recipient.
     * @param recipientEmail The email address of the recipient.
     * @param recipientName The name of the recipient.
     * @param senderAccountId The account ID of the sender.
     * @param amount The amount received.
     * @param transactionId The transaction ID.
     */
    public void sendTransferReceivedEmail(String recipientEmail, String recipientName,
                                          String senderAccountId, double amount,
                                          String transactionId) {
        try {
            String subject = "ADC Bank - Money Received";
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

            String body = String.format(
                    "Dear %s,\n\n" +
                            "You have received a transfer to your ADC Bank account.\n\n" +
                            "Transfer Details:\n" +
                            "- Amount Received: $%.2f\n" +
                            "- From Account: %s\n" +
                            "- Transaction ID: %s\n" +
                            "- Date & Time: %s\n\n" +
                            "The funds are now available in your account.\n\n" +
                            "Best regards,\n" +
                            "ADC Bank Team\n\n" +
                            "This is an automated message, please do not reply.",
                    recipientName, amount, senderAccountId, transactionId, timestamp
            );

            sendEmail(recipientEmail, subject, body);
            System.out.println("Transfer received email sent successfully to: " + recipientEmail);
        } catch (Exception e) {
            System.err.println("Error sending transfer received email: " + e.getMessage());
        }
    }

    /**
     * @brief Sends an email with the specified parameters.
     * @param recipientEmail The recipient's email address.
     * @param subject The email subject.
     * @param body The email body.
     * @throws MessagingException If there's an error sending the email.
     */
    private void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.trust", SMTP_HOST);
        props.put("mail.debug", "true"); // Para debug

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(BANK_EMAIL, BANK_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(BANK_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}