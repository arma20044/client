package src.main.java.dao.acta;

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

 

 

import entity.UcsawsActas;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;


public class ActaDAO {

    public Boolean eliminarActa(String codigo) {
	boolean eliminado = false;

	//try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(144);

	    query.setQueryGenerico(codigo);

	    QueryGenericoResponse response = weatherClient
		    .getQueryGenericoResponse(query);
	    weatherClient.printQueryGenericoResponse(response);

	    String res = response.getQueryGenericoResponse();

        try {
          //n = mapper.readValue(jsonInString, String.class);
          if (res.compareTo("SI")==0){
          eliminado = true;
          }
          
      } catch (Exception ex) {
              eliminado = false;
          JOptionPane.showMessageDialog(null,"Error al intentar eliminar el Acta.","Error",JOptionPane.ERROR_MESSAGE);
      }
      return eliminado;

    }

    public UcsawsActas obtenerActaById(Integer idActa) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(145);
	query.setQueryGenerico(idActa.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsActas acta = new UcsawsActas();
	try {
	  acta = mapper.readValue(jsonInString, UcsawsActas.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return acta;
    }

    public List<UcsawsActas> obtenerActaByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(146);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsActas> acta = new ArrayList<UcsawsActas>();
	try {
	  acta = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsActas>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return acta;
    }

    public boolean guardarActa(UcsawsActas acta) {
	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(acta);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(147);
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
    
    
    public Integer obtenerCantidadVotosByMesayEvento( Integer idMesa,Integer idEvento) {

    ApplicationContext ctx = SpringApplication
        .run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();
    
    List<Integer> a = new ArrayList<Integer>();
    
    a.add(idMesa);
    a.add(idEvento);
    
    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    
    try{
    jsonStr = mapperObj.writeValueAsString(a);
    
    }
    catch(Exception e){
      System.out.println(e);
    }

    query.setTipoQueryGenerico(148);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient
        .getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    Integer acta = null ;
    try {
      acta = mapper.readValue(jsonInString, Integer.class);

    } catch (Exception e) {
        System.out.println(e);
    }
    return acta;
    }
    
}
