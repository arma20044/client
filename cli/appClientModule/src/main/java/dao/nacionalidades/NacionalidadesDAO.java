package src.main.java.dao.nacionalidades;

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

import entity.UcsawsGenero;
import entity.UcsawsMesa;
import entity.UcsawsNacionalidad;
import entity.UcsawsPais;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class NacionalidadesDAO {
    
    
  public List<UcsawsNacionalidad> obtenerNacionalidadByIdEvento(Integer idEvento) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(91);
    query.setQueryGenerico(idEvento.toString());

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    List<UcsawsNacionalidad> nacionalidad = new ArrayList<UcsawsNacionalidad>();
    try {
      nacionalidad =
          mapper.readValue(jsonInString, new TypeReference<List<UcsawsNacionalidad>>() {});

    } catch (Exception e) {
      System.out.println(e);
    }
    return nacionalidad;
  }
    
    
    public Boolean modificarNacionalidad(UcsawsNacionalidad nacionalidad) {

 	boolean guardado = false;

   	ObjectMapper mapperObj = new ObjectMapper();
   	String jsonStr = "";
   	try {
   	    // get Employee object as a json string
   	    jsonStr = mapperObj.writeValueAsString(nacionalidad);
   	    System.out.println(jsonStr);
   	} catch (IOException e) {
   	    // TODO Auto-generated catch block
   	    e.printStackTrace();
   	}

   	ApplicationContext ctx = SpringApplication
   		.run(WeatherConfiguration.class);

   	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
   	QueryGenericoRequest query = new QueryGenericoRequest();

   	query.setTipoQueryGenerico(55);
   	query.setQueryGenerico(jsonStr);

   	QueryGenericoResponse response = weatherClient
   		.getQueryGenericoResponse(query);
   	weatherClient.printQueryGenericoResponse(response);

   	ObjectMapper mapper = new ObjectMapper();
   	String jsonInString = response.getQueryGenericoResponse();

   	if(jsonInString.compareTo("SI")==0){
   	 guardado = true;
   	}
   	else{
   	    guardado = false;
   	}
   	

   	return guardado;
    }
    
    
    
	
	
    public Boolean guardarNacionalidad(UcsawsNacionalidad nacionalidad) {

   	boolean guardado = false;

   	ObjectMapper mapperObj = new ObjectMapper();
   	String jsonStr = "";
   	try {
   	    // get Employee object as a json string
   	    jsonStr = mapperObj.writeValueAsString(nacionalidad);
   	    System.out.println(jsonStr);
   	} catch (IOException e) {
   	    // TODO Auto-generated catch block
   	    e.printStackTrace();
   	}

   	ApplicationContext ctx = SpringApplication
   		.run(WeatherConfiguration.class);

   	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
   	QueryGenericoRequest query = new QueryGenericoRequest();

   	query.setTipoQueryGenerico(51);
   	query.setQueryGenerico(jsonStr);

   	QueryGenericoResponse response = weatherClient
   		.getQueryGenericoResponse(query);
   	weatherClient.printQueryGenericoResponse(response);

   	ObjectMapper mapper = new ObjectMapper();
   	String jsonInString = response.getQueryGenericoResponse();

   	UcsawsNacionalidad n = new UcsawsNacionalidad();
   	try {
   	    n = mapper.readValue(jsonInString, UcsawsNacionalidad.class);
   	} catch (Exception ex) {
   	    System.out.println(ex);
   	}
   	guardado = true;

   	return guardado;

       }

 



	public Boolean eliminarNacionalidad(UcsawsNacionalidad nacionalidad)
	{
		boolean eliminado = false;
		
		ObjectMapper mapperObj = new ObjectMapper();
	   	String jsonStr = "";
	   	try {
	   	    // get Employee object as a json string
	   	    jsonStr = mapperObj.writeValueAsString(nacionalidad);
	   	    System.out.println(jsonStr);
	   	} catch (IOException e) {
	   	    // TODO Auto-generated catch block
	   	    e.printStackTrace();
	   	}

	   	ApplicationContext ctx = SpringApplication
	   		.run(WeatherConfiguration.class);

	   	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	   	QueryGenericoRequest query = new QueryGenericoRequest();

	   	query.setTipoQueryGenerico(53);
	   	query.setQueryGenerico(jsonStr);

	   	QueryGenericoResponse response = weatherClient
	   		.getQueryGenericoResponse(query);
	   	weatherClient.printQueryGenericoResponse(response);

	   	ObjectMapper mapper = new ObjectMapper();
	   	String resp = response.getQueryGenericoResponse();

	   	String n = new String();
	   	try {
	   	    //n = mapper.readValue(jsonInString, String.class);
	   	    if (resp.compareTo("SI")==0){
	   		eliminado = true;
	   	    }
			
		} catch (Exception ex) {
		    	eliminado = false;
			JOptionPane.showMessageDialog(null,"Error al intentar eliminar la Nacionalidad","Error",JOptionPane.ERROR_MESSAGE);
		}
		return eliminado;
		
	}
	
	    public UcsawsNacionalidad obtenerNacionalidadByCodigoYNombre(String codigoNacionalidad,
		    String descNacionalidad) {

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		List<String> lista = new ArrayList<String>();
		lista.add(codigoNacionalidad);
		lista.add(descNacionalidad);
		lista.add(VentanaBuscarEvento.evento.toString());

		// parseo json
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";

		// get Employee object as a json string
		try {
		    jsonStr = mapperObj.writeValueAsString(lista);
		} catch (Exception e) {
		    System.out.println(e);
		}

		query.setTipoQueryGenerico(49);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		UcsawsNacionalidad nacionalidad = new UcsawsNacionalidad();
		try {
		    nacionalidad = mapper.readValue(jsonInString, UcsawsNacionalidad.class);
		} catch (Exception e) {
		    System.out.println(e);
		}
		return nacionalidad;
	    }
	    
	    public UcsawsNacionalidad obtenerNacionalidadById(String idNacionalidad) {

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

 
 

		// parseo json
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";

		// get Employee object as a json string
		try {
		    jsonStr = mapperObj.writeValueAsString(idNacionalidad);
		} catch (Exception e) {
		    System.out.println(e);
		}

		query.setTipoQueryGenerico(54);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		  UcsawsNacionalidad nacionalidad = new UcsawsNacionalidad();
		try {
		    nacionalidad = mapper.readValue(jsonInString,  UcsawsNacionalidad.class);
		} catch (Exception e) {
		    System.out.println(e);
		}
		return nacionalidad;
	    }
	    
	    public UcsawsNacionalidad obtenerNacionalidadByIdPais(String idPais) {

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

 
 

		// parseo json
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";

		// get Employee object as a json string
		try {
		    jsonStr = mapperObj.writeValueAsString(idPais);
		} catch (Exception e) {
		    System.out.println(e);
		}

		query.setTipoQueryGenerico(56);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		  UcsawsNacionalidad nacionalidad = new UcsawsNacionalidad();
		try {
		    nacionalidad = mapper.readValue(jsonInString,  UcsawsNacionalidad.class);
		} catch (Exception e) {
		    System.out.println(e);
		}
		return nacionalidad;
	    }
}
