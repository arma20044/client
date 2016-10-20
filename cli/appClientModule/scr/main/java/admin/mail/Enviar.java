package scr.main.java.admin.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Enviar {
	
	public static void main(String[] args) {
		
		NewEnviar();
	      
	}
	
	public static void NewEnviar(){
		
		try {
			
	
		
	      Properties _properties = new Properties();
	      _properties.setProperty("mail.transport.protocol", "smtp");
	      _properties.setProperty("mail.host", "smtp.gmail.com");
	     // props.setProperty("mail.user", "votoucsa");
	    //  props.setProperty("mail.password", "votoucsa2016");
	      _properties.setProperty("mail.smtp.starttls.enable", "true");
	      _properties.setProperty("mail.smtp.auth", "true"); 
	      _properties.setProperty("mail.smtp.user", "votoucsa");
	      _properties.setProperty("mail.smtp.password", "votoucsa2016");
	      _properties.setProperty("mail.port", "587");
	      

	      Session session = Session.getDefaultInstance(_properties);
	      
	 Transport transport = session.getTransport("smtp");
     transport.connect(_properties.getProperty("mail.host"), 
             Integer.parseInt(_properties.getProperty("mail.port")),
             _properties.getProperty("mail.smtp.user"),
             _properties.getProperty("mail.smtp.password")); 
     
     
     
     String _addresses = "arma20044@gmail.com";
     
      
     
     Address[] addr = new Address[1];
     
         addr[0] = new InternetAddress(_addresses);
     
     
         MimeMessage message = new MimeMessage(session);
         message.setSubject("Testing javamail plain");
	      message.setContent("This is a test", "text/plain");
     
    
     transport.sendMessage(message, addr);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

