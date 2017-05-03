package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsNacionalidad;
import entity.UcsawsPais;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.nacionalidades.NacionalidadesDAO;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class NacionalidadValidator {

	public Boolean ValidarCodigo(String codigo, String desc)
			throws ParseException, org.json.simple.parser.ParseException {

		 

		NacionalidadesDAO nacionalidadesDAO = new NacionalidadesDAO();
		
		UcsawsNacionalidad nacionalidad = nacionalidadesDAO.obtenerNacionalidadByCodigoYNombre(codigo, desc);
		
		
		if (nacionalidad.getIdNacionalidad()== null){
			return false;
		}
		else{
			return true;
		}


	}

	public Boolean ValidarPais(Integer idPais , String idEvento) throws ParseException,
			org.json.simple.parser.ParseException {


		PaisDAO paisDAO = new PaisDAO();
		
		UcsawsPais pais = new UcsawsPais();
		pais = paisDAO.obtenerPaisByIdeIdEvento(idPais, idEvento);
		
		
		if (pais == null){
			return false;
		}
		else{
			return true;
		}

	}

}
