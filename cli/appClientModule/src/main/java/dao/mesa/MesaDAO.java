package src.main.java.dao.mesa;

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

import entity.UcsawsMesa;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;


public class MesaDAO {

  public UcsawsMesa obtenerMesaByID(Integer idMesa) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(122);
    query.setQueryGenerico(idMesa.toString());

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsMesa mesa = new UcsawsMesa();
    try {
      mesa = mapper.readValue(jsonInString, UcsawsMesa.class);

    } catch (Exception e) {
      System.out.println(e);
    }
    return mesa;
  }

  public List<UcsawsMesa> obtenerMesaByIdEvento(Integer idEvento) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(87);
    query.setQueryGenerico(idEvento.toString());

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    List<UcsawsMesa> mesa = new ArrayList<UcsawsMesa>();
    try {
      mesa = mapper.readValue(jsonInString, new TypeReference<List<UcsawsMesa>>() {});

    } catch (Exception e) {
      System.out.println(e);
    }
    return mesa;
  }

  public boolean guardarMesa(UcsawsMesa mesa) {
    boolean guardado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(mesa);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(88);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String string = response.getQueryGenericoResponse();

    UcsawsMesa n = new UcsawsMesa();
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

  public Boolean eliminarMesa(String idMesa) {
    boolean eliminado = false;

    try {

      ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

      WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
      QueryGenericoRequest query = new QueryGenericoRequest();

      query.setTipoQueryGenerico(89);

      query.setQueryGenerico(idMesa);

      QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
      weatherClient.printQueryGenericoResponse(response);

      String res = response.getQueryGenericoResponse();

      if (res.compareTo("NO") == 0 || res.compareTo("ERRORRRRRRR") == 0) {

        eliminado = false;
      } else {
        eliminado = true;
      }

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error al intentar eliminar la Mesa.", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
    return eliminado;

  }



  public List<UcsawsMesa> obtenerMesaByIdLocal(Integer idMesa) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(90);
    query.setQueryGenerico(idMesa.toString());

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    List<UcsawsMesa> mesa = new ArrayList<UcsawsMesa>();
    try {
      mesa = mapper.readValue(jsonInString, new TypeReference<List<UcsawsMesa>>() {});

    } catch (Exception e) {
      System.out.println(e);
    }
    return mesa;
  }

}
