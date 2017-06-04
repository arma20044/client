package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsRoles;
import src.main.java.dao.rol.RolDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class RolValidator {



  public Boolean ValidarCodigo(String codigo, String descripcion, String idEvento)
      throws ParseException, org.json.simple.parser.ParseException {

    boolean existe = false;

    RolDAO rolDAO = new RolDAO();
    
    List<UcsawsRoles> rol = rolDAO.obtenerRolByIdEvento(Integer.parseInt(idEvento));

    Iterator<UcsawsRoles> ite = rol.iterator();

    UcsawsRoles aux;
    while (ite.hasNext()) {
      aux = ite.next();
      if ((aux.getCodigo().compareToIgnoreCase(codigo.toUpperCase()) == 0 || aux.getDescripcion().compareTo(descripcion.toUpperCase()) == 0)
        ) {
        existe = true;
      }
    }

    return existe;

  }

  public Boolean ValidarCodigo(String codigo) throws ParseException,
      org.json.simple.parser.ParseException {

    boolean existe = false;

    Calendar calendar = new GregorianCalendar();
    int year = calendar.get(Calendar.YEAR);

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(2);

    query.setQueryGenerico("SELECT id_candidatos, codigo " + "from ucsaws_candidatos "
        + "where  upper(codigo) = "

        + "upper('" + year + "/" + codigo + "')");

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    String res = response.getQueryGenericoResponse();

    if (res.compareTo("[]") != 0) {

      return existe = true;
    }

    else {
      existe = false;

      return existe;

    }

  }

  public Boolean ValidarPersona(Integer persona) throws ParseException,
      org.json.simple.parser.ParseException {

    boolean existe = false;

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(2);

    query.setQueryGenerico("SELECT id_candidatos, codigo " + "from ucsaws_candidatos "
        + "where  id_persona = "

        + persona + "");

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    String res = response.getQueryGenericoResponse();

    if (res.compareTo("[]") != 0) {

      return existe = true;
    }

    else {
      existe = false;

      return existe;

    }

  }

}
