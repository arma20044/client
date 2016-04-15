package src.main.java.hello;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;

import sun.misc.BASE64Encoder;

public class WebServiceMessageSenderWithAuth extends HttpUrlConnectionMessageSender{
	
	@Override
	protected void prepareConnection(HttpURLConnection connection)
			throws IOException {
		
		BASE64Encoder enc = new sun.misc.BASE64Encoder();
		String userpassword = "votouser:votopass";
		String encodedAuthorization = enc.encode( userpassword.getBytes() );
		connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

		super.prepareConnection(connection);
	}

}
