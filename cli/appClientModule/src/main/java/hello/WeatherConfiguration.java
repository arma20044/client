
package src.main.java.hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class WeatherConfiguration {
	//desde win
	//public final static String URL = "http://voto.ws:8080/"; //sin https
	//desdewin
	
	//tcpmon test
	//public final static String URL = "https://localhost/";
	//tcpmon test
	
  //*O ESTE*******************************************************************
	//descomentado 22 de julio del 2017
	//public final static String URL = "https://ubuntu/"; //con maquina virtual https
	//public final static String URL = "http://ubuntu:8080/"; //con maquina virtual http 8080
	//O ESTE
	//comentado 22 de julio del 2017
	public final static String URL = "http://localhost:8080/"; //sin maquina virtual http
	  //****************************************************************************	
	
//	apunta a ubuntu maquina virtual
//	public final static String URL = "http://192.168.1.103:8080/";

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("hello.wsdl");
		return marshaller;
	}

	@Bean
	public WeatherClient votoClient(Jaxb2Marshaller marshaller) {
		WeatherClient client = new WeatherClient();
		client.setDefaultUri(URL + "VotoWS/votoService.wsdl");
//		client.setDefaultUri("http://localhost:8080/VotoWS/votoService.wsdl");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		
		WebServiceTemplate template = client.getWebServiceTemplate();
		template.setMessageSender(new WebServiceMessageSenderWithAuth());
		
		return client;
	}

}