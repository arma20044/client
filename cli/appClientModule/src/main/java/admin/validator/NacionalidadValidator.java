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

import entity.UcsawsMesa;
import entity.UcsawsNacionalidad;
import entity.UcsawsPais;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.mesa.MesaDAO;
import src.main.java.dao.nacionalidades.NacionalidadesDAO;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class NacionalidadValidator {

    /*public Boolean ValidarCodigo(String codigo, String desc)
	    throws ParseException, org.json.simple.parser.ParseException {

	NacionalidadesDAO nacionalidadesDAO = new NacionalidadesDAO();

	UcsawsNacionalidad nacionalidad = nacionalidadesDAO
		.obtenerNacionalidadByCodigoYNombre(codigo, desc);

	if (nacionalidad.getIdNacionalidad() == null) {
	    return false;
	} else {
	    return true;
	}

    }*/

    public Boolean ValidarPais(Integer idPais, String idEvento)
	    throws ParseException, org.json.simple.parser.ParseException {

	PaisDAO paisDAO = new PaisDAO();

	UcsawsPais pais = new UcsawsPais();
	pais = paisDAO.obtenerPaisByIdeIdEvento(idPais, idEvento);

	if (pais == null) {
	    return false;
	} else {
	    return true;
	}

    }

    public Boolean ValidarPais2(Integer idPais )
	    throws ParseException, org.json.simple.parser.ParseException {

	UcsawsNacionalidad nacionalidad = new UcsawsNacionalidad();
	NacionalidadesDAO nacionalidadDAO = new NacionalidadesDAO();
	
	
	nacionalidad = nacionalidadDAO.obtenerNacionalidadByIdPais(idPais.toString());

	 


	if (nacionalidad.getIdNacionalidad() == null) {
	    return false;
	} else {
	    return true;
	}

    }
    
    public Boolean ValidarCodigo(String codigo, String descripcion,
	    String idEvento)
	    throws ParseException, org.json.simple.parser.ParseException {

	boolean existe = false;

	NacionalidadesDAO nacionalidadesDAO = new NacionalidadesDAO();

	List<UcsawsNacionalidad> nacionalidades = nacionalidadesDAO
		.obtenerNacionalidadByIdEvento(Integer.parseInt(idEvento));

	Iterator<UcsawsNacionalidad> ite = nacionalidades.iterator();

	UcsawsNacionalidad aux;
	while (ite.hasNext()) {
	    aux = ite.next();
	    if (aux.getDescNacionalidad().compareToIgnoreCase(descripcion) == 0
		    || aux.getCodNacionalidad() == codigo) {
		existe = true;
	    }
	}

	return existe;

    }

}
