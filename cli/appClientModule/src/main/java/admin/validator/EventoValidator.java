package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.Generic;
import entity.UcsawsEvento;
import entity.UcsawsTipoEvento;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.DeQuePais;
import src.main.java.admin.utils.FechaDeOtroPaisParametrizado;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class EventoValidator {

	public Boolean ValidarCodigo(String codigo) throws ParseException,
			org.json.simple.parser.ParseException {

		boolean existe = false;

		EventoDAO eventoDAO = new EventoDAO();
		
		UcsawsEvento evento = eventoDAO.obtenerEventoByCodigo(codigo);
		
		
		if (evento.getIdEvento()== null){
			return false;
		}
		else{
			return true;
		}

		

		

	}

//	public Boolean ValidarPersona(Integer persona) throws ParseException,
//			org.json.simple.parser.ParseException {
//
//		boolean existe = false;
//
//		ApplicationContext ctx = SpringApplication
//				.run(WeatherConfiguration.class);
//
//		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
//		QueryGenericoRequest query = new QueryGenericoRequest();
//
//		query.setTipoQueryGenerico(2);
//
//		query.setQueryGenerico("SELECT id_candidatos, codigo "
//				+ "from ucsaws_candidatos " + "where  id_persona = "
//
//				+ persona + "");
//
//		QueryGenericoResponse response = weatherClient
//				.getQueryGenericoResponse(query);
//		weatherClient.printQueryGenericoResponse(response);
//
//		String res = response.getQueryGenericoResponse();
//
//		if (res.compareTo("[]") != 0) {
//
//			return existe = true;
//		}
//
//		else {
//			existe = false;
//
//			return existe;
//
//		}
//
//	}
	
	public static Boolean eventoVigente(Generic g)  {
		
		EventoDAO eventoDAO = new EventoDAO();
		
		UcsawsEvento evento = eventoDAO.obtenerEventoByRangoFechaTipoEvento(g);
		
		
		if (evento.getIdEvento() == null){
			return false;
		}
		
		else{
			
			
		

		

		return true;
		
		}

	}

}
