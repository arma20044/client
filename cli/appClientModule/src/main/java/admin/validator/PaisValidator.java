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
import entity.UcsawsPais;
import src.main.java.dao.nacionalidades.NacionalidadesDAO;
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
	
	public Boolean ValidarCodigo(String codigo, String descripcion,
		    String idEvento)
		    throws ParseException, org.json.simple.parser.ParseException {

		boolean existe = false;

		PaisDAO paisDAO = new PaisDAO();

		List<UcsawsPais> pais = paisDAO.obtenerPaisByIdEvento(Integer.parseInt(idEvento));

		Iterator<UcsawsPais> ite = pais.iterator();

		UcsawsPais aux;
		while (ite.hasNext()) {
		    aux = ite.next();
		    if (aux.getCodigo().compareToIgnoreCase(codigo) == 0
			    || aux.getNombre().compareToIgnoreCase(descripcion)==0) {
			existe = true;
		    }
		}

		return existe;

	    }
}
