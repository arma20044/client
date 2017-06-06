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

import entity.UcsawsPersona;
import entity.UcsawsRoles;
import entity.UcsawsUsers;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.nacionalidades.NacionalidadesDAO;
import src.main.java.dao.user.UserDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class UsuariosValidator {
  


  public Boolean ValidarCodigo(UcsawsPersona persona,UcsawsRoles rol, String user,  String idEvento)
      throws ParseException, org.json.simple.parser.ParseException {

    boolean existe = false;

    UserDAO userDAO = new UserDAO();

    List<UcsawsUsers> usuarios = userDAO.obtenerUserByIdEvento(Integer.parseInt(idEvento));

    Iterator<UcsawsUsers> ite = usuarios.iterator();

    UcsawsUsers aux;
    while (ite.hasNext()) {
      aux = ite.next();
      if (
         ( (aux.getUcsawsPersona().getIdPersona().toString().compareTo(persona.getIdPersona().toString())==0)   
              && (aux.getIdRol().getIdRol().toString().compareTo(rol.getIdRol().toString())  == 0 ) )
          || aux.getUsuario().compareTo(user)==0
          )  {
        existe = true;
      }
    }

    return existe;

  }

  public Boolean ValidarUser(String usuario) throws ParseException,
      org.json.simple.parser.ParseException {

    boolean existe = false;

    Calendar calendar = new GregorianCalendar();
    int year = calendar.get(Calendar.YEAR);

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(2);

    query.setQueryGenerico("select id_user, usuario  from ucsaws_users "

    + "where id_evento = " + VentanaBuscarEvento.evento + " " + "and usuario = '" + usuario + "'");

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

  public Boolean ValidarPersona(Integer idPersona) throws ParseException,
      org.json.simple.parser.ParseException {

    boolean existe = false;

    Calendar calendar = new GregorianCalendar();
    int year = calendar.get(Calendar.YEAR);

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(2);

    query.setQueryGenerico("select id_user, usuario  from ucsaws_users "

    + "where id_evento = " + VentanaBuscarEvento.evento + " " + "and id_persona = '" + idPersona
        + "'");

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
