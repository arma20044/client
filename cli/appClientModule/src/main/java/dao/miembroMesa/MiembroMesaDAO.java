package src.main.java.dao.miembroMesa;

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

 

 

 

import entity.UcsawsMiembroMesa;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;


public class MiembroMesaDAO {

    public Boolean eliminarMiembroMesa(String codigo) {
	boolean eliminado = false;

	//try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(140);

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
          JOptionPane.showMessageDialog(null,"Error al intentar eliminar el Tipo Miembro Mesa.","Error",JOptionPane.ERROR_MESSAGE);
      }
      return eliminado;

    }

    public UcsawsMiembroMesa obtenerMiembroMesaById(Integer idMiembroMesa) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(141);
	query.setQueryGenerico(idMiembroMesa.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsMiembroMesa miembroMesa = new UcsawsMiembroMesa();
	try {
	  miembroMesa = mapper.readValue(jsonInString, UcsawsMiembroMesa.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return miembroMesa;
    }

    public List<UcsawsMiembroMesa> obtenerMiembroMesaByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(142);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsMiembroMesa> miembroMesa = new ArrayList<UcsawsMiembroMesa>();
	try {
	  miembroMesa = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsMiembroMesa>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return miembroMesa;
    }

    public boolean guardarTipoActa(UcsawsMiembroMesa tipoMiembroMesa) {
	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(tipoMiembroMesa);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(143);
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
