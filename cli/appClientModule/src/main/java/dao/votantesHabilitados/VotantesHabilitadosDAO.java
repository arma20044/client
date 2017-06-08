package src.main.java.dao.votantesHabilitados;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsVotante;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.votantesHabilitados.VentanaBuscarVotantesHabilitados;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;


public class VotantesHabilitadosDAO {
  
  
  public boolean habilitarVotante(UcsawsVotante votante) {

/* query.setQueryGenerico("UPDATE ucsaws_votante " +
    " set habilitado = 1    where id_votante = " + VentanaBuscarVotantesHabilitados.idVotante + " and  id_evento= " 
    + VentanaBuscarEvento.evento
     );*/

    boolean existe = false;

    // Statement estatuto = conex.getConnection().createStatement();
    
    votante.setHabilitado(1);
    
    try{
    modificarVotante(votante);
    }
    catch(Exception e){
      System.out.println(e);
    }
    
    finally{
      existe = true;
    }
    
    
    return existe;
 
    
    

}


  public List<UcsawsVotante> obtenerVotanteByIdEvento(Integer idEvento) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(118);
    query.setQueryGenerico(idEvento.toString());

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    List<UcsawsVotante> votante = new ArrayList<UcsawsVotante>();
    try {
      votante = mapper.readValue(jsonInString, new TypeReference<List<UcsawsVotante>>() {});

    } catch (Exception e) {
      System.out.println(e);
    }
    return votante;
  }


  public Boolean modificarVotante(UcsawsVotante votante) {

    boolean guardado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(votante);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(123);
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



  public Boolean guardarVotante(UcsawsVotante votante) {

    boolean guardado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(votante);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(119);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsVotante n = new UcsawsVotante();
    try {
      n = mapper.readValue(jsonInString, UcsawsVotante.class);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    guardado = true;

    return guardado;

  }



  public Boolean eliminarVotante(UcsawsVotante votante) {
    boolean eliminado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(votante);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(120);
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
      JOptionPane.showMessageDialog(null, "Error al intentar eliminar rl Votante", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
    return eliminado;

  }



  public UcsawsVotante obtenerVotanteById(String idVotante) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();



    // parseo json
  /*  ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";

    // get Employee object as a json string
    try {
      jsonStr = mapperObj.writeValueAsString(idVotante);
    } catch (Exception e) {
      System.out.println(e);
    }*/

    query.setTipoQueryGenerico(121);
    query.setQueryGenerico(idVotante);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsVotante votante = new UcsawsVotante();
    try {
      votante = mapper.readValue(jsonInString, UcsawsVotante.class);
    } catch (Exception e) {
      System.out.println(e);
    }
    return votante;
  }




}
