package src.main.java.dao.pais;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

 



import entity.UcsawsNacionalidad;
import entity.UcsawsPais;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class PaisDAO {

    public static Long idPais;

    public boolean guardarPais(UcsawsPais pais) {

	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(pais);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(48);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsPais p = new UcsawsPais();
	try {
	    p = mapper.readValue(jsonInString, UcsawsPais.class);
	} catch (Exception ex) {
	    System.out.println(ex);
	}
	guardado = true;

	return guardado;
    }

    public JSONArray buscarPais(String codigo) throws ParseException,
	    org.json.simple.parser.ParseException {
	JSONArray filas = new JSONArray();

	Date date = null;

	boolean existe = false;

	// Statement estatuto = conex.getConnection().createStatement();

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	// para registrar se inserta el codigo es 1
	query.setTipoQueryGenerico(2);

	query.setQueryGenerico("SELECT id_pais, nombre " + " from ucsaws_pais "
		+ "where  upper(nombre) like upper('%" + codigo + "%')  ");

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	String res = response.getQueryGenericoResponse();

	if (res.compareTo("[]") == 0) {
	    JOptionPane.showMessageDialog(null, "El Pais no Existe",
		    "Advertencia", JOptionPane.WARNING_MESSAGE);
	    return filas;
	}

	else {
	    existe = true;

	    String generoAntesPartir = response.getQueryGenericoResponse();

	    JSONParser j = new JSONParser();
	    Object ob;
	    String part1, part2, part3;

	    ob = j.parse(generoAntesPartir);
	    filas = (JSONArray) ob;

	    JSONArray fila = (JSONArray) filas.get(0);
	    // JSONArray fila1 = (JSONArray) filas.get(1);
	    // idPais = fila1;
	    // System.out.print(filas);
	    // System.out.print("\\n");
	    // // System.out.print(fila);
	    // System.out.print("\\n");
	    // System.out.print(fila1);

	    idPais = (Long) fila.get(0);
	    // part2 = (String) array1.get(1);
	    // part3 = (String) array1.get(2);

	    // gen.setDescripcion(part1);
	    // gen.setFecha(part2);
	    // gen.setUsuario(part3);

	    // String[] parts = generoAntesPartir.split(",");
	    //

	    // DateTimeFormatter formatter =
	    // DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
	    // DateTime dt = formatter.parseDateTime(part2);

	    // DateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
	    //
	    //
	    // date = formatter.parse(part2);
	    //
	    // GregorianCalendar newCalendar = (GregorianCalendar)
	    // GregorianCalendar.getInstance();
	    // newCalendar.setTime(date);
	    // GregorianCalendar fecha = date.tog

	    // fecha.setTime(date);

	    // gen.setFecha(part2);
	    //
	    // gen.setUsuario(part3);

	    return filas;

	}

    }

    public Boolean eliminarPais(String codigo) {
	boolean eliminado = false;

	//try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(63);

	    query.setQueryGenerico(codigo);

	    QueryGenericoResponse response = weatherClient
		    .getQueryGenericoResponse(query);
	    weatherClient.printQueryGenericoResponse(response);

	    String res = response.getQueryGenericoResponse();

        try {
          //n = mapper.readValue(jsonInString, String.class);
          if (res.compareTo("SI")==0){
          eliminado = true;
          }
          
      } catch (Exception ex) {
              eliminado = false;
          JOptionPane.showMessageDialog(null,"Error al intentar eliminar el Pais.","Error",JOptionPane.ERROR_MESSAGE);
      }
      return eliminado;

    }

    public UcsawsPais obtenerEventoByCodigoYNombre(String codigoPais,
	    String nombre) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	List<String> lista = new ArrayList<String>();
	lista.add(codigoPais);
	lista.add(nombre);
	lista.add(VentanaBuscarEvento.evento.toString());

	// parseo json
	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";

	// get Employee object as a json string
	try {
	    jsonStr = mapperObj.writeValueAsString(lista);
	} catch (Exception e) {
	    System.out.println(e);
	}

	query.setTipoQueryGenerico(47);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsPais pais = new UcsawsPais();
	try {
	    pais = mapper.readValue(jsonInString, UcsawsPais.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return pais;
    }
    
    
    
    public UcsawsPais obtenerPaisById(Integer idPais) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();
 	// parseo json
	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";

	// get Employee object as a json string
	try {
	    jsonStr = mapperObj.writeValueAsString(idPais);
	} catch (Exception e) {
	    System.out.println(e);
	}

	query.setTipoQueryGenerico(50);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsPais pais = new UcsawsPais();
	try {
	    pais = mapper.readValue(jsonInString, UcsawsPais.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return pais;
    }
    
    public UcsawsPais obtenerPaisByIdeIdEvento(Integer idPais, String idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);
	
	List<String> lista = new ArrayList<String>();
	lista.add(idPais.toString());
	lista.add(idEvento);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();
 	// parseo json
	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";

	// get Employee object as a json string
	try {
	    jsonStr = mapperObj.writeValueAsString(lista);
	} catch (Exception e) {
	    System.out.println(e);
	}

	query.setTipoQueryGenerico(52);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsNacionalidad n = new UcsawsNacionalidad();
	try {
	    n = mapper.readValue(jsonInString, UcsawsNacionalidad.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return n.getUcsawsPais();
    }
    
    public List<UcsawsPais> obtenerPaisByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(92);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsPais> pais = new ArrayList<UcsawsPais>();
	try {
	    pais = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsPais>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return pais;
    }
}
