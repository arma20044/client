package scr.main.java.admin.mail;

import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HtmlEmailSenderTemplate {

  public void sendHtmlEmail(String host, String port,
      final String userName, final String password, String toAddress,
      String subject, String message) throws AddressException,
      MessagingException {

  // sets SMTP server properties
  Properties properties = new Properties();
  properties.put("mail.smtp.host", host);
  properties.put("mail.smtp.port", port);
  properties.put("mail.smtp.auth", "true");
  properties.put("mail.smtp.starttls.enable", "true");

  // creates a new session with an authenticator
  Authenticator auth = new Authenticator() {
      public PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(userName, password);
      }
  };

  Session session = Session.getInstance(properties, auth);

  // creates a new e-mail message
  Message msg = new MimeMessage(session);

  msg.setFrom(new InternetAddress(userName));
  InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
  msg.setRecipients(Message.RecipientType.TO, toAddresses);
  msg.setSubject(subject);
  msg.setSentDate(new Date());
  // set plain text message
  msg.setContent(message, "text/html");

  // sends the e-mail
  Transport.send(msg);

}

/**
* Test the send html e-mail method
*
*/
public static void main(String[] args) {
  // SMTP server information
  String host = "smtp.gmail.com";
  String port = "587";
  String mailFrom = "votoucsa@gmail.com";
  String password = "votoucsa2016";

  // outgoing message information
  String mailTo = "arma20044@gmail.com";
  String subject = "Hello my friend";

  // message contains HTML markups
  String message = "<i>Greetings!</i><br>";
  message += "<b>Wish you a nice day!</b><br>";
  message += "<font color=red>Duke</font>";

  HtmlEmailSenderTemplate mailer = new HtmlEmailSenderTemplate();

  try {
      mailer.sendHtmlEmail(host, port, mailFrom, password, mailTo,
              subject, message);
      System.out.println("Email sent.");
  } catch (Exception ex) {
      System.out.println("Failed to sent email.");
      ex.printStackTrace();
  }
}
}
