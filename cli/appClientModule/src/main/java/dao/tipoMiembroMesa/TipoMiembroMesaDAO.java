package src.main.java.dao.tipoMiembroMesa;

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

 

 

import entity.UcsawsTipoMiembroMesa;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;


public class TipoMiembroMesaDAO {

    public Boolean eliminarTipoMiembroMesa(String codigo) {
	boolean eliminado = false;

	//try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(136);

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

    public UcsawsTipoMiembroMesa obtenerTipoMiembroMesaById(Integer idTipoMiembroMesa) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(137);
	query.setQueryGenerico(idTipoMiembroMesa.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsTipoMiembroMesa tipoMiembroMesa = new UcsawsTipoMiembroMesa();
	try {
	  tipoMiembroMesa = mapper.readValue(jsonInString, UcsawsTipoMiembroMesa.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return tipoMiembroMesa;
    }

    public List<UcsawsTipoMiembroMesa> obtenerTipoMiembroMesaByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(138);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsTipoMiembroMesa> tipoMiembroMesa = new ArrayList<UcsawsTipoMiembroMesa>();
	try {
	  tipoMiembroMesa = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsTipoMiembroMesa>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return tipoMiembroMesa;
    }

    public boolean guardarTipoActa(UcsawsTipoMiembroMesa tipoMiembroMesa) {
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

	query.setTipoQueryGenerico(139);
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
