package src.main.java.dao.listas;

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

import entity.UcsawsListas;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class ListasDAO {

    public UcsawsListas obtenerListaByIdIdLista(Integer idLista) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(102);
	query.setQueryGenerico(idLista.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsListas lista = new UcsawsListas();
	try {
	    lista = mapper.readValue(jsonInString, UcsawsListas.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return lista;
    }

    public List<UcsawsListas> obtenerListaByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(97);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsListas> lista = new ArrayList<UcsawsListas>();
	try {
	    lista = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsListas>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return lista;
    }

    public Boolean modificarLista(UcsawsListas lista) {

	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(lista);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(98);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
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

    public Boolean guardarLista(UcsawsListas lista) {

	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(lista);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(99);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsListas n = new UcsawsListas();
	try {
	    n = mapper.readValue(jsonInString, UcsawsListas.class);
	} catch (Exception ex) {
	    System.out.println(ex);
	}
	guardado = true;

	return guardado;

    }

    public Boolean eliminarLista(UcsawsListas lista) {
	boolean eliminado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(lista);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(100);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
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
	    JOptionPane.showMessageDialog(null,
		    "Error al intentar eliminar la Lista", "Error",
		    JOptionPane.ERROR_MESSAGE);
	}
	return eliminado;

    }

    /*
     * public JSONArray buscarLista(String codigo) throws ParseException,
     * org.json.simple.parser.ParseException { JSONArray filas = new
     * JSONArray();
     * 
     * Date date = null;
     * 
     * boolean existe = false;
     * 
     * // Statement estatuto = conex.getConnection().createStatement();
     * 
     * ApplicationContext ctx = SpringApplication
     * .run(WeatherConfiguration.class);
     * 
     * WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
     * QueryGenericoRequest query = new QueryGenericoRequest();
     * 
     * // para registrar se inserta el codigo es 1
     * query.setTipoQueryGenerico(2);
     * 
     * query.setQueryGenerico("SELECT id_lista, nombre_lista " +
     * " from ucsaws_listas " + "where upper(nombre_lista) like upper('%" +
     * codigo + "%') and id_evento =   " + VentanaBuscarEvento.evento);
     * 
     * QueryGenericoResponse response = weatherClient
     * .getQueryGenericoResponse(query);
     * weatherClient.printQueryGenericoResponse(response);
     * 
     * String res = response.getQueryGenericoResponse();
     * 
     * if (res.compareTo("[]") == 0) { JOptionPane.showMessageDialog(null,
     * "La Lista no Existe", "Advertencia", JOptionPane.WARNING_MESSAGE); return
     * filas; }
     * 
     * else { existe = true;
     * 
     * String generoAntesPartir = response.getQueryGenericoResponse();
     * 
     * JSONParser j = new JSONParser(); Object ob; String part1, part2, part3;
     * 
     * ob = j.parse(generoAntesPartir); filas = (JSONArray) ob;
     * 
     * // JSONArray fila = (JSONArray) filas.get(0); // JSONArray fila1 =
     * (JSONArray) filas.get(1);
     * 
     * // System.out.print(filas); // System.out.print("\\n"); // //
     * System.out.print(fila); // System.out.print("\\n"); //
     * System.out.print(fila1);
     * 
     * // part1 = (String) array1.get(0); // part2 = (String) array1.get(1); //
     * part3 = (String) array1.get(2);
     * 
     * // gen.setDescripcion(part1); // gen.setFecha(part2); //
     * gen.setUsuario(part3);
     * 
     * // String[] parts = generoAntesPartir.split(","); //
     * 
     * // DateTimeFormatter formatter = //
     * DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"); // DateTime dt =
     * formatter.parseDateTime(part2);
     * 
     * // DateFormat formatter = new SimpleDateFormat("dd-mm-yyyy"); // // //
     * date = formatter.parse(part2); // // GregorianCalendar newCalendar =
     * (GregorianCalendar) // GregorianCalendar.getInstance(); //
     * newCalendar.setTime(date); // GregorianCalendar fecha = date.tog
     * 
     * // fecha.setTime(date);
     * 
     * // gen.setFecha(part2); // // gen.setUsuario(part3);
     * 
     * return filas;
     * 
     * }
     * 
     * }
     * 
     * 
     * 
     * public Boolean actualizarLista(String numero, String nombre, String anho,
     * String tipo, String id) { boolean actualizado = false;
     * 
     * try {
     * 
     * ApplicationContext ctx = SpringApplication
     * .run(WeatherConfiguration.class);
     * 
     * WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
     * QueryGenericoRequest query = new QueryGenericoRequest();
     * 
     * query.setTipoQueryGenerico(3);
     * 
     * query.setQueryGenerico("UPDATE ucsaws_listas    SET nombre_lista= '" +
     * nombre + "', anho= " + anho + ", " + "fch_upd= now() , usuario_upd= " +
     * Login.userLogeado + " , " + " nro_lista= " + numero + ", id_tipo_lista= "
     * + tipo + " WHERE id_lista =" + id + " and id_evento = "+
     * VentanaBuscarEvento.evento);
     * 
     * QueryGenericoResponse response = weatherClient
     * .getQueryGenericoResponse(query);
     * weatherClient.printQueryGenericoResponse(response);
     * 
     * String res = response.getQueryGenericoResponse();
     * 
     * if (res.compareTo("ERRORRRRRRR") == 0) {
     * 
     * actualizado = false; } else { actualizado = true; }
     * 
     * } catch (Exception ex) { JOptionPane.showMessageDialog(null,
     * "Error al intentar actualizar la Lista.", "Error",
     * JOptionPane.ERROR_MESSAGE); } return actualizado;
     * 
     * }
     */

    public Boolean eliminarLista(String codigo) {

	boolean eliminado = false;

	try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(100);

	    query.setQueryGenerico(codigo);

	    QueryGenericoResponse response = weatherClient
		    .getQueryGenericoResponse(query);
	    weatherClient.printQueryGenericoResponse(response);

	    String res = response.getQueryGenericoResponse();

	    if (res.compareTo("NO") == 0) {

		eliminado = false;
	    } else {
		eliminado = true;
	    }

	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(null,
		    "Error al intentar eliminar la Lista.", "Error",
		    JOptionPane.ERROR_MESSAGE);
	}
	return eliminado;

    }
}
