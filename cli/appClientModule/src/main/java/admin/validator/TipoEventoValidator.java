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

import entity.UcsawsEvento;
import entity.UcsawsTipoEvento;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.tipoEvento.TipoEventoDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class TipoEventoValidator {


	public Boolean ValidarCodigo(String idEvento, String codigo) throws ParseException,
			org.json.simple.parser.ParseException {

		boolean existe = false;

		TipoEventoDAO tipoEventoDAO = new TipoEventoDAO();
		
		List<UcsawsTipoEvento> Tipoevento = tipoEventoDAO.obtenerTipoEventoByIdEvento(Integer.parseInt(idEvento));
		
		Iterator<UcsawsTipoEvento> ite = Tipoevento.iterator();
		
		UcsawsTipoEvento aux;
		while (ite.hasNext()) {
			aux = ite.next();
			if(aux.getDescripcion().compareToIgnoreCase(codigo)==0){
			    existe = true;
			}
		}
		
		return existe;

		

		

	}
	

	


}
