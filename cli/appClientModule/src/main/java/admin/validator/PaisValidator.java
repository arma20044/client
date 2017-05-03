package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

 
 


import entity.UcsawsPais;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class PaisValidator {

	public Boolean ValidarCodigo(String nro, String nombre)
			throws ParseException, org.json.simple.parser.ParseException {

		 

		PaisDAO paisDAO = new PaisDAO();
		
		UcsawsPais pais = paisDAO.obtenerEventoByCodigoYNombre(nro, nombre);
		
		
		if (pais.getIdPais()== null){
			return false;
		}
		else{
			return true;
		}

		

	}
}
