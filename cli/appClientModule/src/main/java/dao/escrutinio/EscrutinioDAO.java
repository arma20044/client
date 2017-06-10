package src.main.java.dao.escrutinio;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsVotos;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;




public class EscrutinioDAO {
  
  


  /*public List<UcsawsCandidatos> obtenerCandidatosByIdEvento(Integer idEvento) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(101);
    query.setQueryGenerico(idEvento.toString());

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    List<UcsawsCandidatos> candidatos = new ArrayList<UcsawsCandidatos>();
    try {
      candidatos = mapper.readValue(jsonInString, new TypeReference<List<UcsawsCandidatos>>() {});

    } catch (Exception e) {
      System.out.println(e);
    }
    return candidatos;
  }


  public Boolean modificarCandidato(UcsawsCandidatos candidato) {

    boolean guardado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(candidato);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(103);
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



  public Boolean guardarCandidato(UcsawsCandidatos candidato) {

    boolean guardado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(candidato);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(104);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsCandidatos n = new UcsawsCandidatos();
    try {
      n = mapper.readValue(jsonInString, UcsawsCandidatos.class);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    guardado = true;

    return guardado;

  }



  public Boolean eliminarCandidato(UcsawsCandidatos candidato) {
    boolean eliminado = false;

    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(candidato);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(105);
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
      JOptionPane.showMessageDialog(null, "Error al intentar eliminar al Candidato", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
    return eliminado;

  }



  public UcsawsCandidatos obtenerCandidatoById(String idCandidato) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();


 

    query.setTipoQueryGenerico(106);
    query.setQueryGenerico(idCandidato);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

    UcsawsCandidatos candidato = new UcsawsCandidatos();
    try {
      candidato = mapper.readValue(jsonInString, UcsawsCandidatos.class);
    } catch (Exception e) {
      System.out.println(e);
    }
    return candidato;
  }
*/
  
  public List<UcsawsVotos> obtenerVotosByIdEvento(Integer idEvento, String tipoLista) {

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();
    
    List<String> paraEnviar = new ArrayList<String>();
    paraEnviar.add(idEvento.toString());
    paraEnviar.add(tipoLista);
    List<UcsawsVotos> voto = new ArrayList<UcsawsVotos>();
    try {
    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
     
        // get Employee object as a json string
        jsonStr = mapperObj.writeValueAsString(paraEnviar);

    query.setTipoQueryGenerico(124);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

   
   
      voto = mapper.readValue(jsonInString, new TypeReference<List<UcsawsVotos>>() {});

    } catch (Exception e) {
      System.out.println(e);
    }
    return voto;
  
  }

}
