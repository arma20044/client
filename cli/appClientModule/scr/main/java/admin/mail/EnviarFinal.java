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

import entity.UcsawsVotante;

public class EnviarFinal {

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
public void main(String email,  UcsawsVotante votante ) {
  // SMTP server information
  String host = "smtp.gmail.com";
  String port = "587";
  String mailFrom = "votoucsa@gmail.com";
  String password = "votoucsa2016";

  // outgoing message information
  String mailTo = "arma20044@gmail.com";
  String subject = "CERTIFICADO DE VOTACIÓN" + " - "+ votante.getIdEvento().getDescripcion().toUpperCase();

  // message contains HTML markups
  String message = "<i>El Sistema E-vote certifica la votación de:</i><br>";
  message += "<b>"+ votante.getIdPersona().getNombre() + " " + votante.getIdPersona().getApellido() +"</b><br>";
  message += "<b>Con C.I. N° : "+ votante.getIdPersona().getCi() +"</b><br>";
  message += "<b>Mesa: "+ votante.getUcsawsMesa().getDescMesa()  +"</b><br>";
  message += "<b>Departamento: "+ votante.getUcsawsMesa().getUcsawsLocal().getUcsawsZona().getUcsawsDistrito().getUcsawsDepartamento().getDescDepartamento() +"</b><br>";
  message += "<b>Distrito: "+  votante.getUcsawsMesa().getUcsawsLocal().getUcsawsZona().getUcsawsDistrito().getDescDistrito()  +"</b><br>";
  message += "<b>Zona: "+ votante.getUcsawsMesa().getUcsawsLocal().getUcsawsZona().getDescZona()  +"</b><br>";
  message += "<b>Local de Votación: "+ votante.getUcsawsMesa().getUcsawsLocal().getDescLocal()  +"</b><br>";
  
  
  
  
   
  

  EnviarFinal mailer = new EnviarFinal();

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
