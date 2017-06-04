package src.main.java.dao.user;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsUsers;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class UserDAO {

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

}
