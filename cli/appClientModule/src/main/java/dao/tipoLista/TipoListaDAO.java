package src.main.java.dao.tipoLista;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsTipoLista;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;


public class TipoListaDAO {

    public Boolean eliminarTipoLista(String codigo) {
	boolean eliminado = false;

	try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(93);

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
		    "Error al intentar eliminar el Tipo Lista.", "Error",
		    JOptionPane.ERROR_MESSAGE);
	}
	return eliminado;

    }

    public UcsawsTipoLista obtenerTipoListaById(Integer idTipoLista) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(94);
	query.setQueryGenerico(idTipoLista.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsTipoLista tipoLista = new UcsawsTipoLista();
	try {
	    tipoLista = mapper.readValue(jsonInString, UcsawsTipoLista.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return tipoLista;
    }

    public List<UcsawsTipoLista> obtenerTipoListaByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(95);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsTipoLista> tipoLista = new ArrayList<UcsawsTipoLista>();
	try {
	    tipoLista = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsTipoLista>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return tipoLista;
    }

    public boolean guardarTipoLista(UcsawsTipoLista tipoLista) {
	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(tipoLista);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(96);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String string = response.getQueryGenericoResponse();

	//UcsawsPersona n = new UcsawsPersona();
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
