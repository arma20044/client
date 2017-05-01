package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.genero.GeneroDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class GeneroValidator {

	public Boolean ValidarCodigo(String nro, String evento)  {

	    GeneroDAO generoDAO = new GeneroDAO();
	    if (generoDAO.buscarGeneroByCodigoYEvento(nro, evento)){
		return true;
	    }
	    else{
		return false;
	    }

		 

	}
}
