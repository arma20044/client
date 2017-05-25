package src.main.java.dao.distrito;

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

import entity.UcsawsDepartamento;
import entity.UcsawsDistrito;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class DistritoDAO {

    public List<UcsawsDistrito> obtenerDistritoByIdEvento(Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(70);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsDistrito> distrito = new ArrayList<UcsawsDistrito>();
	try {
	    distrito = mapper.readValue(jsonInString, new TypeReference<List<UcsawsDistrito>>(){});
	    
	    
	} catch (Exception e) {
	    System.out.println(e);
	}
	return distrito;
    }

	public JSONArray buscarDistrito(String codigo)
			throws ParseException, org.json.simple.parser.ParseException {
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

		query.setQueryGenerico("SELECT id_distrito, desc_distrito "
				+ " from ucsaws_distrito di join ucsaws_departamento de on (di.id_departamento = de.id_departamento)"
				+ "where (upper(desc_distrito) like upper('%" + codigo + "%') "
				+ ") and di.id_evento = " + VentanaBuscarEvento.evento);

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();

		if (res.compareTo("[]") == 0) {
			JOptionPane.showMessageDialog(null, "El Distrito no Existe",
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

	    public Boolean eliminarDistrito(String idDistrito) {
		boolean eliminado = false;

		try {

		    ApplicationContext ctx = SpringApplication
			    .run(WeatherConfiguration.class);

		    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		    QueryGenericoRequest query = new QueryGenericoRequest();

		    query.setTipoQueryGenerico(73);

		    query.setQueryGenerico(idDistrito);

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
			    "Error al intentar eliminar el Distrito.", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
		return eliminado;

	    }
	
	public boolean guardarDistrito(UcsawsDistrito distrito) {
		boolean guardado = false;

		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {
		    // get Employee object as a json string
		    jsonStr = mapperObj.writeValueAsString(distrito);
		    System.out.println(jsonStr);
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(72);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String string = response.getQueryGenericoResponse();

		UcsawsDistrito n = new UcsawsDistrito();
		try {
		    if (string.compareTo("SI") == 0) {
			guardado = true;
		    }

		} catch (Exception ex) {
		    System.out.println(ex);
		}
		// guardado = true;

		return guardado;
	    }
}
