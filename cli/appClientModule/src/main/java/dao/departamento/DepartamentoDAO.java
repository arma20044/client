package src.main.java.dao.departamento;

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
import entity.UcsawsPersona;
import entity.UcsawsTipoEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class DepartamentoDAO {

    // public void registrarPersona(PersonaVo miPersona)
    // {
    // Conexion conex= new Conexion();
    //
    // try {
    // Statement estatuto = conex.getConnection().createStatement();
    // estatuto.executeUpdate("INSERT INTO persona VALUES ('"+miPersona.getIdPersona()+"', '"
    // +miPersona.getNombrePersona()+"', '"+miPersona.getEdadPersona()+"', '"
    // +miPersona.getProfesionPersona()+"', '"+miPersona.getTelefonoPersona()+"')");
    // JOptionPane.showMessageDialog(null,
    // "Se ha registrado Exitosamente","Informaci�n",JOptionPane.INFORMATION_MESSAGE);
    // estatuto.close();
    // conex.desconectar();
    //
    // } catch (SQLException e) {
    // System.out.println(e.getMessage());
    // JOptionPane.showMessageDialog(null, "No se Registro");
    // }
    // }

    public JSONArray buscarDepartamento(String codigo) throws ParseException,
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

	query.setQueryGenerico("SELECT id_departamento, desc_departamento "
		+ " from ucsaws_departamento "
		+ "where upper(desc_departamento) like upper('%" + codigo
		+ "%')  ");

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	String res = response.getQueryGenericoResponse();

	if (res.compareTo("[]") == 0) {
	    JOptionPane.showMessageDialog(null, "El Departamento no Existe",
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

    public Boolean eliminarDepartamento(String idDepartamento) {
	boolean eliminado = false;

	try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(69);

	    query.setQueryGenerico(idDepartamento);

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
		    "Error al intentar eliminar el Departamento.", "Error",
		    JOptionPane.ERROR_MESSAGE);
	}
	return eliminado;

    }

    public List<UcsawsDepartamento> obtenerDepartamentoByIdEvento(
	    Integer idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(67);
	query.setQueryGenerico(idEvento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	List<UcsawsDepartamento> departamento = new ArrayList<UcsawsDepartamento>();
	try {
	    departamento = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsDepartamento>>() {
		    });

	} catch (Exception e) {
	    System.out.println(e);
	}
	return departamento;
    }

    public boolean guardarDepartamento(UcsawsDepartamento departamento) {
	boolean guardado = false;

	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(departamento);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(68);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String string = response.getQueryGenericoResponse();

	UcsawsDepartamento n = new UcsawsDepartamento();
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

    public UcsawsDepartamento obtenerDepartamentoByID(Integer idDepartamento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(71);
	query.setQueryGenerico(idDepartamento.toString());

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsDepartamento departamento = new UcsawsDepartamento();
	try {
	    departamento = mapper.readValue(jsonInString,UcsawsDepartamento.class);

	} catch (Exception e) {
	    System.out.println(e);
	}
	return departamento;
    }
}
