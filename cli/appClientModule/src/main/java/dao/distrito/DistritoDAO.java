package src.main.java.dao.distrito;

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

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import entity.UcsawsDistrito;

public class DistritoDAO {

    public UcsawsDistrito obtenerDistritoByID(Integer idDistrito) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(77);
	query.setQueryGenerico(idDistrito.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsDistrito distrito = new UcsawsDistrito();
	try {
	    distrito = mapper.readValue(jsonInString, UcsawsDistrito.class);

	} catch (Exception e) {
	    System.out.println(e);
	}
	return distrito;
    }

    public List<UcsawsDistrito> obtenerDistritoByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(70);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsDistrito> distrito = new ArrayList<UcsawsDistrito>();
	try {
	    distrito = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsDistrito>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return distrito;
    }

    public Boolean eliminarDistrito(String idDistrito) {
	boolean eliminado = false;

	try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(73);

	    query.setQueryGenerico(idDistrito);

	    QueryGenericoResponse response = weatherClient
		    .getQueryGenericoResponse(query);
	    weatherClient.printQueryGenericoResponse(response);

	    String res = response.getQueryGenericoResponse();

	    if (res.compareTo("NO") == 0 || res.compareTo("ERRORRRRRRR")==0) {

		eliminado = false;
	    } else {
		eliminado = true;
	    }

	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(null,
		    "Error al intentar eliminar el Distrito.", "Error",
		    JOptionPane.ERROR_MESSAGE);
	}
	return eliminado;

    }

    public boolean guardarDistrito(UcsawsDistrito distrito) {
	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(distrito);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(72);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String string = response.getQueryGenericoResponse();

	UcsawsDistrito n = new UcsawsDistrito();
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
    
    
    public List<UcsawsDistrito> obtenerDistritoByIdDepartamento(Integer idDepartamento) {

   	ApplicationContext ctx = SpringApplication
   		.run(WeatherConfiguration.class);

   	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
   	QueryGenericoRequest query = new QueryGenericoRequest();

   	query.setTipoQueryGenerico(83);
   	query.setQueryGenerico(idDepartamento.toString());

   	QueryGenericoResponse response = weatherClient
   		.getQueryGenericoResponse(query);
   	weatherClient.printQueryGenericoResponse(response);

   	ObjectMapper mapper = new ObjectMapper();
   	String jsonInString = response.getQueryGenericoResponse();

   	List<UcsawsDistrito> distrito = new ArrayList<UcsawsDistrito>();
   	try {
   	    distrito = mapper.readValue(jsonInString,
   		    new TypeReference<List<UcsawsDistrito>>() {
   		    });

   	} catch (Exception e) {
   	    System.out.println(e);
   	}
   	return distrito;
       }
}
