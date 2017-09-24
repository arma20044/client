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

import entity.UcsawsCandidatos;
import entity.UcsawsNacionalidad;
import src.main.java.dao.candidato.CandidatoDAO;
import src.main.java.dao.nacionalidades.NacionalidadesDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class ActaValidator {



  public Boolean ValidarCodigo() throws ParseException, org.json.simple.parser.ParseException {

  /*  boolean existe = false;

    CandidatoDAO candidatoDAO = new CandidatoDAO();

    List<UcsawsCandidatos> candidatos =
        candidatoDAO.obtenerCandidatosByIdEvento(Integer.parseInt(idEvento));

    Iterator<UcsawsCandidatos> ite = candidatos.iterator();

    UcsawsCandidatos aux;
    while (ite.hasNext()) {
      aux = ite.next();
      if ((aux.getCodigo().compareToIgnoreCase(codigo) == 0
          || aux.getIdPersona().getIdPersona() == Integer.parseInt(persona) || (aux.getIdLista()
          .getIdLista() == Integer.parseInt(lista) && aux.getDescripcion().compareTo(observacion) == 0))
          && aux.getIdEvento().getIdEvento() == Integer.parseInt(idEvento)) {
        existe = true;
      }
    }

    return existe;
*/
    return true;
  }

  /*
   * public Boolean ValidarCodigo(String codigo) throws ParseException,
   * org.json.simple.parser.ParseException {
   * 
   * boolean existe = false;
   * 
   * Calendar calendar = new GregorianCalendar(); int year = calendar.get(Calendar.YEAR);
   * 
   * ApplicationContext ctx = SpringApplication .run(WeatherConfiguration.class);
   * 
   * WeatherClient weatherClient = ctx.getBean(WeatherClient.class); QueryGenericoRequest query =
   * new QueryGenericoRequest();
   * 
   * query.setTipoQueryGenerico(2);
   * 
   * query.setQueryGenerico("SELECT id_candidatos, codigo " + "from ucsaws_candidatos " +
   * "where  upper(codigo) = "
   * 
   * + "upper('" + year + "/" + codigo + "')");
   * 
   * QueryGenericoResponse response = weatherClient .getQueryGenericoResponse(query);
   * weatherClient.printQueryGenericoResponse(response);
   * 
   * String res = response.getQueryGenericoResponse();
   * 
   * if (res.compareTo("[]") != 0) {
   * 
   * return existe = true; }
   * 
   * else { existe = false;
   * 
   * return existe;
   * 
   * }
   * 
   * }
   * 
   * public Boolean ValidarPersona(Integer persona) throws ParseException,
   * org.json.simple.parser.ParseException {
   * 
   * boolean existe = false;
   * 
   * ApplicationContext ctx = SpringApplication .run(WeatherConfiguration.class);
   * 
   * WeatherClient weatherClient = ctx.getBean(WeatherClient.class); QueryGenericoRequest query =
   * new QueryGenericoRequest();
   * 
   * query.setTipoQueryGenerico(2);
   * 
   * query.setQueryGenerico("SELECT id_candidatos, codigo " + "from ucsaws_candidatos " +
   * "where  id_persona = "
   * 
   * + persona + "");
   * 
   * QueryGenericoResponse response = weatherClient .getQueryGenericoResponse(query);
   * weatherClient.printQueryGenericoResponse(response);
   * 
   * String res = response.getQueryGenericoResponse();
   * 
   * if (res.compareTo("[]") != 0) {
   * 
   * return existe = true; }
   * 
   * else { existe = false;
   * 
   * return existe;
   * 
   * }
   * 
   * }
   */

}
