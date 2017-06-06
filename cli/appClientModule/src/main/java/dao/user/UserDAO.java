package src.main.java.dao.user;

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

import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import entity.UcsawsUsers;

public class UserDAO {

  public Boolean guardarUser(UcsawsUsers user) {

    boolean guardado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(user);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(111);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsUsers n = new UcsawsUsers();
    try {
      n = mapper.readValue(jsonInString, UcsawsUsers.class);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    guardado = true;

    return guardado;

  }

  public List<UcsawsUsers> obtenerUserByIdEvento(Integer idEvento) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(108);
    query.setQueryGenerico(idEvento.toString());

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    List<UcsawsUsers> user = new ArrayList<UcsawsUsers>();
    try {
      user = mapper.readValue(jsonInString, new TypeReference<List<UcsawsUsers>>() {});

    } catch (Exception e) {
      System.out.println(e);
    }
    return user;
  }


  public Boolean eliminarUser(UcsawsUsers users) {
    boolean eliminado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(users);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(109);
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
      JOptionPane.showMessageDialog(null, "Error al intentar eliminar el Usuario.", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
    return eliminado;

  }

  public UcsawsUsers obtenerUserById(String idUser) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();



    query.setTipoQueryGenerico(110);
    query.setQueryGenerico(idUser);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsUsers users = new UcsawsUsers();
    try {
      users = mapper.readValue(jsonInString, UcsawsUsers.class);
    } catch (Exception e) {
      System.out.println(e);
    }
    return users;
  }

}
