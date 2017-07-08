package src.main.java.dao.vigencia;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsEvento;
import entity.UcsawsVigenciaHorarioXPais;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;


public class VigenciaDAO {


  public UcsawsVigenciaHorarioXPais obtenerVigenciaByIdVigencia(Integer idVigencia) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(127);
    query.setQueryGenerico(idVigencia.toString());

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsVigenciaHorarioXPais vigencia = new UcsawsVigenciaHorarioXPais();
    try {
      vigencia = mapper.readValue(jsonInString, UcsawsVigenciaHorarioXPais.class);
    } catch (Exception e) {
      System.out.println(e);
    }
    return vigencia;
  }

  public List<UcsawsVigenciaHorarioXPais> obtenerVigenciaByEvento(UcsawsEvento evento) throws JsonGenerationException, JsonMappingException, IOException {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();
    
    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    jsonStr = mapperObj.writeValueAsString(evento);

    query.setTipoQueryGenerico(128);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    List<UcsawsVigenciaHorarioXPais> vigencia = new ArrayList<UcsawsVigenciaHorarioXPais>();
    try {
      vigencia = mapper.readValue(jsonInString, new TypeReference<List<UcsawsVigenciaHorarioXPais>>() {});

    } catch (Exception e) {
      System.out.println(e);
    }
    return vigencia;
  }
  
  
  public Boolean guardarVigencia(UcsawsVigenciaHorarioXPais vigencia) {

    boolean guardado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(vigencia);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(129);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsVigenciaHorarioXPais n = new UcsawsVigenciaHorarioXPais();
    try {
      n = mapper.readValue(jsonInString, UcsawsVigenciaHorarioXPais.class);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    guardado = true;

    return guardado;

  }
  
  
  public Boolean eliminarVigencia(UcsawsVigenciaHorarioXPais vigencia) {
    boolean eliminado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(vigencia);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(130);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String resp = response.getQueryGenericoResponse();

    String n = new String();
    try {
      // n = mapper.readValue(jsonInString, String.class);
      if (resp.compareTo("SI") == 0) {
        eliminado = true;
      }

    } catch (Exception ex) {
      eliminado = false;
      JOptionPane.showMessageDialog(null, "Error al intentar eliminar la Vigencia", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
    return eliminado;

  }

  /*public Boolean modificarLista(UcsawsListas lista) {

    boolean guardado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(lista);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(98);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    if (jsonInString.compareTo("SI") == 0) {
      guardado = true;
    } else {
      guardado = false;
    }

    return guardado;
  }



*/

}
