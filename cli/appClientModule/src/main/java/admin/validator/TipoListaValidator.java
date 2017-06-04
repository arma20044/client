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

import entity.UcsawsTipoEvento;
import entity.UcsawsTipoLista;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.tipoEvento.TipoEventoDAO;
import src.main.java.dao.tipoLista.TipoListaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.proceso.voto.VentanaSenadores;

public class TipoListaValidator {

    public Boolean Validar(String nro) throws ParseException,
	    org.json.simple.parser.ParseException {

	boolean existe = false;

	Calendar calendar = new GregorianCalendar();
	int year = calendar.get(Calendar.YEAR);

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(2);

	query.setQueryGenerico("SELECT id_tipo_lista, codigo "
		+ "from ucsaws_tipo_lista " + "where id_evento = "
		+ VentanaBuscarEvento.evento + " and upper(codigo) = upper('"
		+ nro + "') ");

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

    public Boolean ValidarCodigo( String codigo, String descripcion, String idEvento)
	    throws ParseException, org.json.simple.parser.ParseException {

	boolean existe = false;

	TipoListaDAO tipoListaDAO = new TipoListaDAO();

	List<UcsawsTipoLista> tipoLista = tipoListaDAO
		.obtenerTipoListaByIdEvento(Integer.parseInt(idEvento));

	Iterator<UcsawsTipoLista> ite = tipoLista.iterator();

	UcsawsTipoLista aux;
	while (ite.hasNext()) {
	    aux = ite.next();
	    if (aux.getDescripcion().compareToIgnoreCase(descripcion) == 0
		    || aux.getCodigo().compareToIgnoreCase(codigo)==0) {
		existe = true;
	    }
	}

	return existe;

    }
}
