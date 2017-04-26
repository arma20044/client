package src.main.java.admin.creacionClases;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import src.main.java.login.TestRolDao;
import entity.UcsawsEvento;


public class EventoRules {
	
	public void setearEvento(String fechaHusoHora) {

	
	}
	
	private boolean consultar(String fechaHusoHora){
		
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {
			// get Employee object as a json string
			jsonStr = mapperObj.writeValueAsString(fechaHusoHora);
			System.out.println(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		
		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		// para registrar se inserta el codigo es 1
		query.setTipoQueryGenerico(1);
		System.out.println(Login.userLogeado);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);
		
		return false;
		
	}


}
