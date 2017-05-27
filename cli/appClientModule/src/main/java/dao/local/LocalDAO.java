package src.main.java.dao.local;

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

import entity.UcsawsLocal;
import entity.UcsawsZona;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class LocalDAO {
	
    public UcsawsLocal obtenerLocalByID(Integer idLocal) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(78);
	query.setQueryGenerico(idLocal.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsLocal local = new UcsawsLocal();
	try {
	    local = mapper.readValue(jsonInString, UcsawsLocal.class);

	} catch (Exception e) {
	    System.out.println(e);
	}
	return local;
    }

    public List<UcsawsLocal> obtenerLocalByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(79);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsLocal> local = new ArrayList<UcsawsLocal>();
	try {
	    local = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsLocal>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return local;
    }

    public Boolean eliminarLocal(String idLocal) {
	boolean eliminado = false;

	try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(81);

	    query.setQueryGenerico(idLocal);

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
		    "Error al intentar eliminar el Local.", "Error",
		    JOptionPane.ERROR_MESSAGE);
	}
	return eliminado;

    }

    public boolean guardarLocal(UcsawsLocal local) {
	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(local);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(80);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String string = response.getQueryGenericoResponse();

	UcsawsLocal n = new UcsawsLocal();
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
    
    public List<UcsawsLocal> obtenerLocalByIdZona(Integer idZona) {

   	ApplicationContext ctx = SpringApplication
   		.run(WeatherConfiguration.class);

   	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
   	QueryGenericoRequest query = new QueryGenericoRequest();

   	query.setTipoQueryGenerico(85);
   	query.setQueryGenerico(idZona.toString());

   	QueryGenericoResponse response = weatherClient
   		.getQueryGenericoResponse(query);
   	weatherClient.printQueryGenericoResponse(response);

   	ObjectMapper mapper = new ObjectMapper();
   	String jsonInString = response.getQueryGenericoResponse();

   	List<UcsawsLocal> local = new ArrayList<UcsawsLocal>();
   	try {
   	    local = mapper.readValue(jsonInString,
   		    new TypeReference<List<UcsawsLocal>>() {
   		    });

   	} catch (Exception e) {
   	    System.out.println(e);
   	}
   	return local;
       }

}
