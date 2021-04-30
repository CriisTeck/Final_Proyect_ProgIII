package utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static utils.EncrypterString.desencrypt;
import static utils.JSONUtils.userEmailFromJSONFile;
import static utils.JSONUtils.userPassEmailFromJSONFile;


public class EmailSender {

    public static void sendEmail(String recipientEmail, String subjectEmail, String textEmail) throws MessagingException, IOException {
        String senderEmail = desencrypt(userEmailFromJSONFile());

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", senderEmail);
        props.put("mail.smtp.clave", desencrypt(userPassEmailFromJSONFile()));    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(senderEmail));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject(subjectEmail);
        message.setText(textEmail);
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", senderEmail, desencrypt(userPassEmailFromJSONFile()));
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}