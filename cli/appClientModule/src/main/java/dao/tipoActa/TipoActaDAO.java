package src.main.java.dao.tipoActa;

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

 

import entity.UcsawsTipoActa;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;


public class TipoActaDAO {

    public Boolean eliminarTipoActa(String codigo) {
	boolean eliminado = false;

	//try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(132);

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
          JOptionPane.showMessageDialog(null,"Error al intentar eliminar el Tipo Acta.","Error",JOptionPane.ERROR_MESSAGE);
      }
      return eliminado;

    }

    public UcsawsTipoActa obtenerTipoActaById(Integer idTipoActa) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(133);
	query.setQueryGenerico(idTipoActa.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsTipoActa tipoActa = new UcsawsTipoActa();
	try {
	  tipoActa = mapper.readValue(jsonInString, UcsawsTipoActa.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return tipoActa;
    }

    public List<UcsawsTipoActa> obtenerTipoActaByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(134);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsTipoActa> tipoActa = new ArrayList<UcsawsTipoActa>();
	try {
	  tipoActa = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsTipoActa>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return tipoActa;
    }

    public boolean guardarTipoActa(UcsawsTipoActa tipoActa) {
	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(tipoActa);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(135);
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
