package src.main.java.dao.genero;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsEvento;
import entity.UcsawsGenero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class GeneroDAO {

    public Boolean buscarGeneroByCodigoYEvento(String codGenero, String idEvento) {

	List<String> codGeneroIdEvento = new ArrayList<String>();
	codGeneroIdEvento.add(codGenero);
	codGeneroIdEvento.add(idEvento);

	// parseo json
	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(codGeneroIdEvento);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(40);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsGenero> genero = new ArrayList<UcsawsGenero>();
	try {
	    genero = mapper.readValue(jsonInString, List.class);

	} catch (Exception e) {
	    System.out.println(e);
	}
	if (genero.isEmpty()) {
	    return false;
	} else {
	    return true;
	}

    }

    public JSONArray buscarGenero(String codigo) throws ParseException,
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

	query.setQueryGenerico("SELECT id_genero, descripcion "
		+ " from ucsaws_genero "
		+ "where upper(descripcion) like upper('%" + codigo + "%')  ");

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	String res = response.getQueryGenericoResponse();

	if (res.compareTo("[]") == 0) {
	    JOptionPane.showMessageDialog(null, "El Genero no Existe",
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

	    // JSONArray fila = (JSONArray) filas.get(0);
	    // JSONArray fila1 = (JSONArray) filas.get(1);

	    // System.out.print(filas);
	    // System.out.print("\\n");
	    // // System.out.print(fila);
	    // System.out.print("\\n");
	    // System.out.print(fila1);

	    // part1 = (String) array1.get(0);
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

    public Boolean eliminarGenero(UcsawsGenero genero) {
	boolean eliminado = false;


	    ObjectMapper mapperObj = new ObjectMapper();
	    String jsonStr = "";

	    // get Employee object as a json string
	    try{
	    jsonStr = mapperObj.writeValueAsString(genero);
	    }
	    catch(Exception e){
	      System.out.println(e);
	    }

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(42);

	    query.setQueryGenerico(jsonStr);

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
          JOptionPane.showMessageDialog(null,"Error al intentar eliminar el Genero","Error",JOptionPane.ERROR_MESSAGE);
      }
      return eliminado;

    
    
    }

    public Boolean guardarGenero(UcsawsGenero genero) {

	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(genero);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(41);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsGenero g = new UcsawsGenero();
	try {
	    g = mapper.readValue(jsonInString, UcsawsGenero.class);
	} catch (Exception ex) {
	    System.out.println(ex);
	}
	guardado = true;

	return guardado;

    }
    
    public UcsawsGenero buscarGeneroById(String idGenero) {

 	 

 	// parseo json
 	ObjectMapper mapperObj = new ObjectMapper();
 	String jsonStr = "";
 	try {
 	    // get Employee object as a json string
 	    jsonStr = mapperObj.writeValueAsString(idGenero);
 	    System.out.println(jsonStr);
 	} catch (IOException e) {
 	    // TODO Auto-generated catch block
 	    e.printStackTrace();
 	}

 	ApplicationContext ctx = SpringApplication
 		.run(WeatherConfiguration.class);

 	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
 	QueryGenericoRequest query = new QueryGenericoRequest();

 	query.setTipoQueryGenerico(43);
 	query.setQueryGenerico(jsonStr);

 	QueryGenericoResponse response = weatherClient
 		.getQueryGenericoResponse(query);
 	weatherClient.printQueryGenericoResponse(response);

 	ObjectMapper mapper = new ObjectMapper();
 	String jsonInString = response.getQueryGenericoResponse();

 	UcsawsGenero genero = new UcsawsGenero();
 	try {
 	    genero = mapper.readValue(jsonInString, UcsawsGenero.class);

 	} catch (Exception e) {
 	    System.out.println(e);
 	}
 	 
 	    return genero;
 	 
 	    }

}
