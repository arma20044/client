package src.main.java.dao.tipoEvento;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsPersona;
import entity.UcsawsTipoEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class TipoEventoDAO {

    public UcsawsTipoEvento buscarTipoEventoById(Integer idTipoEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(32);
	query.setQueryGenerico(idTipoEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsTipoEvento tipoEvento = new UcsawsTipoEvento();
	try {
	    tipoEvento = mapper.readValue(jsonInString, UcsawsTipoEvento.class);
	} catch (Exception e) {
	    System.out.println(e);
	}

	if (tipoEvento.getIdEvento() != null) {
	    return tipoEvento;
	} else {
	    return tipoEvento;
	}

	// return null;

    }

    public Boolean eliminarTipoEvento(String codigo) {
	boolean eliminado = false;

	try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(66);

	    query.setQueryGenerico(codigo);

	    QueryGenericoResponse response = weatherClient
		    .getQueryGenericoResponse(query);
	    weatherClient.printQueryGenericoResponse(response);

	    String res = response.getQueryGenericoResponse();

	    if (res.compareTo("NO") == 0) {

		eliminado = false;
	    } else {
		eliminado = true;
	    }

	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(null,
		    "Error al intentar eliminar el Evento", "Error",
		    JOptionPane.ERROR_MESSAGE);
	}
	return eliminado;

    }

    public UcsawsTipoEvento obtenerTipoEventoById(Integer idTipoEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(32);
	query.setQueryGenerico(idTipoEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsTipoEvento tipoEvento = new UcsawsTipoEvento();
	try {
	    tipoEvento = mapper.readValue(jsonInString, UcsawsTipoEvento.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return tipoEvento;
    }

    public List<UcsawsTipoEvento> obtenerTipoEventoByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(64);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsTipoEvento> tipoEvento = new ArrayList<UcsawsTipoEvento>();
	try {
	    tipoEvento = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsTipoEvento>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return tipoEvento;
    }

    public boolean guardarTipoEvento(UcsawsTipoEvento tipoEvento) {
	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(tipoEvento);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(65);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String string = response.getQueryGenericoResponse();

	UcsawsPersona n = new UcsawsPersona();
	try {
	    if (string.compareTo("SI") == 0) {
		guardado = true;
	    }

	} catch (Exception ex) {
	    System.out.println(ex);
	}
	// guardado = true;

	return guardado;
    }
}
