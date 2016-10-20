package scr.main.java.admin.mail;



import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;


	public class SendEmailGenerico {
	

	        
	        public void enviarNotificacion(String email) {
/*	            Properties props = new Properties();
	            props.put("mail.smtp.host", "smtp.gmail.com");
	            props.put("mail.transport.protocol", "smtp");
	            props.put("mail.smtp.starttls.enable","true");

	            Session session = Session.getDefaultInstance(props);

	            try {
	                InternetAddress fromAddress = new InternetAddress("votoucsa@gmail.com");
	                InternetAddress toAddress = new InternetAddress(email);

	                Message message = new MimeMessage(session);
	                message.setFrom(fromAddress);
	                message.setRecipient(Message.RecipientType.TO, toAddress);
	                message.setSubject("Notificación de Voto");
	                message.setText("Muchas gracias por haber participado con su voto.");
	                
	                Transport.send(message
	                		, "votoucsa", 
	                        "votoucsa2016"
	                		);
	            } catch (MessagingException ex) {
	                ex.printStackTrace();
	            }*/
	        	
	        	try {
	        	//public static void main(String[] args) throws Exception{
	        	      Properties props = new Properties();
	        	      props.setProperty("mail.transport.protocol", "smtp");
	        	      props.setProperty("mail.host", "smtp.gmail.com");
	        	     // props.setProperty("mail.user", "votoucsa");
	        	    //  props.setProperty("mail.password", "votoucsa2016");
	        	      props.setProperty("mail.smtp.starttls.enable", "true");
	        	      props.setProperty("mail.smtp.auth", "true"); 
	        	      props.setProperty("mail.smtp.user", "votoucsa");
	        	      props.setProperty("mail.smtp.password", "votoucsa2016");

	        	      Session mailSession = Session.getDefaultInstance(props, 

	        	    		  new javax.mail.Authenticator(){
	        	          protected PasswordAuthentication getPasswordAuthentication() {
	        	              return new PasswordAuthentication(
	        	                  "votoucsa@gmail.com", "votoucsa2016");// Specify the Username and the PassWord
	        	          }});
	        	      Transport transport = mailSession.getTransport();

	        	      MimeMessage message = new MimeMessage(mailSession);
	        	      
	        	      message.setSubject("Testing javamail plain");
	        	      message.setContent("This is a test", "text/plain");
	        	      message.addRecipient(Message.RecipientType.TO,
	        	           new InternetAddress(email));

	        	      transport.connect();
	        	      
	        	      transport.sendMessage(message,
	        	          message.getRecipients(Message.RecipientType.TO));
	        	      transport.close();
	        	    
	        	 } catch (Exception ex) {
		                ex.printStackTrace();
		            }
	        }
	        
	        
	        
	    	public static void NewEnviar(String email){
	    		
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
	         
	             addr[0] = new InternetAddress(email);
	         
	         
	             MimeMessage message = new MimeMessage(session);
	             message.setSubject("Testing javamail plain");
	    	      message.setContent("This is a test", "text/plain");
	         
	        
	         transport.sendMessage(message, addr);
	    		} catch (Exception e) {
	    			// TODO: handle exception
	    		}
	    	}
	
}
