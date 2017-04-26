package src.main.java.login;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import entity.UcsawsEvento;
import entity.UcsawsRoles;

public class TestGuardarRol {

	
	public void guardar(String codigo, String descripcion, UcsawsEvento evento) {

		TestRolDao dao = new TestRolDao();

		// Add new user
		UcsawsRoles rol = new UcsawsRoles();
		rol.setCodigo(codigo);
		rol.setDescripcion(descripcion);
		rol.setUcsawsEvento(evento);
		//rol.setIdRol(88);
	//UcsawsRoles a = new UcsawsRoles();
	//a = rol;
		// enviar por soap
		// Java object to JSON ini
		// public class ObjectToJsonEx {

		// Employee emp = new Employee();
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {
			// get Employee object as a json string
			jsonStr = mapperObj.writeValueAsString(rol);
			System.out.println(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		// ***************************************///

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		// para registrar se inserta el codigo es 1
		query.setTipoQueryGenerico(1);
		System.out.println(Login.userLogeado);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		//
		/*
		 * try { Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
		 * user.setDob(dob); } catch (ParseException e) { e.printStackTrace(); }
		 */

		//dao.addRol(rol);

		// Update user
		// user.setEmail("daniel@updated.com");
		// dao.updateUser(user);

		// Delete user
		// dao.deleteUser(1);

		// Get all users
		for (UcsawsRoles iter : dao.getAllRol()) {
			System.out.println(iter);
		}

		// Get user by id
		System.out.println(dao.getRolById(2));
	}

}
