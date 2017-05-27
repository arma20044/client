package src.main.java.dao.zona;

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





import entity.UcsawsDistrito;
import entity.UcsawsLocal;
import entity.UcsawsZona;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class ZonaDAO {
	
 
    public UcsawsZona obtenerZonaByID(Integer idZona) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(82);
	query.setQueryGenerico(idZona.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsZona zona = new UcsawsZona();
	try {
	    zona = mapper.readValue(jsonInString, UcsawsZona.class);

	} catch (Exception e) {
	    System.out.println(e);
	}
	return zona;
    }

	
	
	    public List<UcsawsZona> obtenerZonaByIdEvento(Integer idEvento) {

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(74);
		query.setQueryGenerico(idEvento.toString());

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		List<UcsawsZona> zona = new ArrayList<UcsawsZona>();
		try {
		    zona = mapper.readValue(jsonInString, new TypeReference<List<UcsawsZona>>(){});
		    
		    
		} catch (Exception e) {
		    System.out.println(e);
		}
		return zona;
	    }
	    
	    
	    
	    public Boolean eliminarZona(String idZona) {
		boolean eliminado = false;

		try {

		    ApplicationContext ctx = SpringApplication
			    .run(WeatherConfiguration.class);

		    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		    QueryGenericoRequest query = new QueryGenericoRequest();

		    query.setTipoQueryGenerico(76);

		    query.setQueryGenerico(idZona);

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
			    "Error al intentar eliminar la Zona.", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
		return eliminado;

	    }
	
	public boolean guardarZona(UcsawsZona zona) {
		boolean guardado = false;

		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {
		    // get Employee object as a json string
		    jsonStr = mapperObj.writeValueAsString(zona);
		    System.out.println(jsonStr);
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(75);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String string = response.getQueryGenericoResponse();

		UcsawsZona n = new UcsawsZona();
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
	
	
	    public List<UcsawsZona> obtenerZonaByIdDistrito(Integer idDistrito) {

	   	ApplicationContext ctx = SpringApplication
	   		.run(WeatherConfiguration.class);

	   	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	   	QueryGenericoRequest query = new QueryGenericoRequest();

	   	query.setTipoQueryGenerico(84);
	   	query.setQueryGenerico(idDistrito.toString());

	   	QueryGenericoResponse response = weatherClient
	   		.getQueryGenericoResponse(query);
	   	weatherClient.printQueryGenericoResponse(response);

	   	ObjectMapper mapper = new ObjectMapper();
	   	String jsonInString = response.getQueryGenericoResponse();

	   	List<UcsawsZona> zona = new ArrayList<UcsawsZona>();
	   	try {
	   	    zona = mapper.readValue(jsonInString,
	   		    new TypeReference<List<UcsawsZona>>() {
	   		    });

	   	} catch (Exception e) {
	   	    System.out.println(e);
	   	}
	   	return zona;
	       }
}
