import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Mail {
    Session newSession = null;
    MimeMessage mimeMessage = null;
    public static void main(String[] args) {
        Mail mail = new Mail();
        mail.setupServerProperties();
        try {
            mail.sendEmail();
            mail.draftEmail();
        } catch (MessagingException me){
            me.printStackTrace();
        }
    }

    private MimeMessage draftEmail() throws MessagingException {
        String[] emailRecipients = {"abc@gmail.com"};
        String emailSubject = "Java Email Sender";
        String emailBody = "Hello World!";
        mimeMessage = new MimeMessage(newSession);

        for(int i = 0 ; i < emailRecipients.length ; i++){
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipients[i]));
        }

        mimeMessage.setSubject(emailSubject);

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(emailBody, "html/text");
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);
        mimeMessage.setContent(multipart);
        return mimeMessage;
    }

    private void sendEmail() throws MessagingException {
        String fromUser = "xyz@gmail.com";
        String fromUserPassword = "******";
        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserPassword);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully");
    }

    private void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties, null);
    }
}