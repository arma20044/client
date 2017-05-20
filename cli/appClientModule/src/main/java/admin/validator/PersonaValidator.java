package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsNacionalidad;
import entity.UcsawsPersona;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.nacionalidades.NacionalidadesDAO;
import src.main.java.dao.persona.PersonaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class PersonaValidator {

    public Boolean ValidarCedula(String cedula, Integer evento) throws ParseException, org.json.simple.parser.ParseException {

	PersonaDAO personaDAO = new PersonaDAO();

	List<UcsawsPersona> persona = personaDAO.obtenerListaPersonaByCedula(cedula);
		 
	if (persona.isEmpty()) {
	    return false;
	} else {
	    //recorrer la lista
	    Iterator itr = persona.iterator();
	    
	     while(itr.hasNext()) {
		 UcsawsPersona element = (UcsawsPersona) itr.next();
	         if(element.getIdEvento().getIdEvento() == evento){
	             return true;
	             }
	      }
	    
	   return false;
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

}
