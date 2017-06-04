package src.main.java.dao.rol;

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

import entity.UcsawsRoles;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;


public class RolDAO {



  public List<UcsawsRoles> obtenerRolByIdEvento(Integer idEvento) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(112);
    query.setQueryGenerico(idEvento.toString());

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    List<UcsawsRoles> rol = new ArrayList<UcsawsRoles>();
    try {
      rol = mapper.readValue(jsonInString, new TypeReference<List<UcsawsRoles>>() {});

    } catch (Exception e) {
      System.out.println(e);
    }
    return rol;
  }


  /*public Boolean modificarNacionalidad(UcsawsNacionalidad nacionalidad) {

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

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(55);
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
  }*/



  public Boolean guardarRol(UcsawsRoles rol) {

    boolean guardado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(rol);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(114);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsRoles n = new UcsawsRoles();
    try {
      n = mapper.readValue(jsonInString, UcsawsRoles.class);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    guardado = true;

    return guardado;

  }



  public Boolean eliminarRol(UcsawsRoles rol) {
    boolean eliminado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(rol);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(115);
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
      JOptionPane.showMessageDialog(null, "Error al intentar eliminar el Rol.", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
    return eliminado;

  }



  public UcsawsRoles obtenerRolById(String idRol) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();





    query.setTipoQueryGenerico(113);
    query.setQueryGenerico(idRol);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsRoles rol = new UcsawsRoles();
    try {
      rol = mapper.readValue(jsonInString, UcsawsRoles.class);
    } catch (Exception e) {
      System.out.println(e);
    }
    return rol;
  }



}
