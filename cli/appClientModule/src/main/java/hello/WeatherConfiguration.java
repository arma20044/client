
package src.main.java.hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WeatherConfiguration {
	//public final static String URL = "http://192.168.0.151:8080/"; sin https
	//public final static String URL = "https://ubuntu/"; //con maquina virtual https
	public final static String URL = "http://localhost:8080/"; //todo desde win
	
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
		return client;
	}

}