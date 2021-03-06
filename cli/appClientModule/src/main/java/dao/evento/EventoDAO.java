package src.main.java.dao.evento;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import entity.Generic;
import entity.UcsawsEvento;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.ArmarFecha;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

@Component
public class EventoDAO {

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

    public JSONArray buscarEvento(String codigo) throws ParseException,
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

	query.setQueryGenerico("select id_evento, nro_evento,ev.descripcion, fch_desde, fch_hasta , tev.descripcion "

		+ " from ucsaws_evento ev join ucsaws_tipo_evento tev on (ev.id_tipo_evento = tev.id_tipo_evento)"

		+ "where upper(ev.descripcion) like upper('%"
		+ codigo
		+ "%') or upper(tev.descripcion) like upper('%"
		+ codigo
		+ "%') or upper(nro_evento) like upper('%" + codigo + "%')");

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	String res = response.getQueryGenericoResponse();

	if (res.compareTo("[]") == 0) {
	    JOptionPane.showMessageDialog(null, "El Evento no Existe",
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

    public Boolean eliminarEvento(Integer idEvento) {
	boolean eliminado = false;

	try {

	    ApplicationContext ctx = SpringApplication
		    .run(WeatherConfiguration.class);

	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	    QueryGenericoRequest query = new QueryGenericoRequest();

	    query.setTipoQueryGenerico(3);

	    query.setQueryGenerico(idEvento.toString());

	    QueryGenericoResponse response = weatherClient
		    .getQueryGenericoResponse(query);
	    weatherClient.printQueryGenericoResponse(response);

	    String res = response.getQueryGenericoResponse();

	    if (res.compareTo("NO") == 0 || res.compareTo("ERRORRRRRRR")==0) {

		eliminado = false;
	    } else {
		eliminado = true;
	    }

	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(null,
		    "Error al intentar eliminar el Evento", "Error",
		    JOptionPane.ERROR_MESSAGE);
	}
	return eliminado;

    }

    public Boolean actualizarEvento(UcsawsEvento evento) {

	boolean actualizado = false;
	
	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
		// get Employee object as a json string
		jsonStr = mapperObj.writeValueAsString(evento);
		System.out.println(jsonStr);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
}


	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(36);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsEvento e = new UcsawsEvento();
	try {
	    evento = mapper.readValue(jsonInString, UcsawsEvento.class);
	} catch (Exception ex) {
	    System.out.println(ex);
	}
	actualizado = true;
	
	return actualizado;

    }

    public UcsawsEvento obtenerEventoByCodigo(String codigoEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(33);
	query.setQueryGenerico(codigoEvento);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsEvento evento = new UcsawsEvento();
	try {
	    evento = mapper.readValue(jsonInString, UcsawsEvento.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return evento;
    }

    public UcsawsEvento obtenerEventoByRangoFechaTipoEvento(Generic g) {

	// parseo json
	ObjectMapper mapperObj = new ObjectMapper();
	String jsonStr = "";
	try {
	    // get Employee object as a json string
	    jsonStr = mapperObj.writeValueAsString(g);
	    System.out.println(jsonStr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(34);
	query.setQueryGenerico(jsonStr);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsEvento evento = new UcsawsEvento();
	try {
	    evento = mapper.readValue(jsonInString, UcsawsEvento.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return evento;
    }

    public UcsawsEvento obtenerEventoById(String idEvento) {

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(37);
	query.setQueryGenerico(idEvento);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();

	UcsawsEvento evento = new UcsawsEvento();
	try {
	    evento = mapper.readValue(jsonInString, UcsawsEvento.class);
	} catch (Exception e) {
	    System.out.println(e);
	}
	return evento;
    }
    
    public UcsawsEvento obtenerEventoVigente() {
      UcsawsEvento evento = new UcsawsEvento();
      try {
        
        
        ObjectMapper mapperObj = new ObjectMapper();
        String jsonStr = "";
        jsonStr = mapperObj.writeValueAsString(new Date());
        
    ApplicationContext ctx = SpringApplication
        .run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(1);
    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient
        .getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();

   
   
        evento = mapper.readValue(jsonInString, UcsawsEvento.class);
    } catch (Exception e) {
        System.out.println(e);
    }
    return evento;
    }
}
