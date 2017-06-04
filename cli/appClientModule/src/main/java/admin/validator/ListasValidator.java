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

import com.lowagie.toolbox.plugins.Txt2Pdf;

import entity.UcsawsListas;
import entity.UcsawsNacionalidad;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.evento.VentanaMainEvento;
import src.main.java.dao.listas.ListasDAO;
import src.main.java.dao.nacionalidades.NacionalidadesDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class ListasValidator {
    
    public Boolean ValidarCodigo(Integer nro, String nombre, Integer anho, Integer tipoLista, String idEvento)
	    throws ParseException, org.json.simple.parser.ParseException {

	boolean existe = false;

	ListasDAO listasDAO = new ListasDAO();

	List<UcsawsListas> listas = listasDAO
		.obtenerListaByIdEvento(Integer.parseInt(idEvento));

	Iterator<UcsawsListas> ite = listas.iterator();

	UcsawsListas aux;
	while (ite.hasNext()) {
	    aux = ite.next();
	    if ((aux.getUcsawsTipoLista().getIdTipoLista() == tipoLista) &&
		    (aux.getNroLista() == nro
		    || aux.getNombreLista().compareToIgnoreCase(nombre) == 0
		    || aux.getAnho() == anho
		    || aux.getIdLista() == tipoLista)
		    ){
		existe = true;
	    }
	}

	return existe;

    }

	public Boolean ValidarCodigo(String nro, Integer tipoLista, String nombre, String nroLista)
			throws ParseException, org.json.simple.parser.ParseException {

		boolean existe = false;

		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(2);

		query.setQueryGenerico("SELECT id_lista, nro_lista "
				+ "from ucsaws_listas " + "where "
				+ "   id_tipo_lista = " + tipoLista  + " and id_evento = " + VentanaBuscarEvento.evento
				+ " and (upper(nombre_lista) = upper('" + nombre + "') "
						+ "or nro_lista = " +nroLista+")");

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();

		if (res.compareTo("[]") != 0) {

			return existe = true;
		}

		else {
			existe = false;

			return existe;

		}

	}
}
