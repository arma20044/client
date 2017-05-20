package src.main.java.dao.persona;

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

import entity.UcsawsEvento;
import entity.UcsawsNacionalidad;
import entity.UcsawsPersona;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class PersonaDAO {
	
	
	public boolean guardarPersona(UcsawsPersona persona)
	{
	  	boolean guardado = false;

	   	ObjectMapper mapperObj = new ObjectMapper();
	   	String jsonStr = "";
	   	try {
	   	    // get Employee object as a json string
	   	    jsonStr = mapperObj.writeValueAsString(persona);
	   	    System.out.println(jsonStr);
	   	} catch (IOException e) {
	   	    // TODO Auto-generated catch block
	   	    e.printStackTrace();
	   	}

	   	ApplicationContext ctx = SpringApplication
	   		.run(WeatherConfiguration.class);

	   	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	   	QueryGenericoRequest query = new QueryGenericoRequest();

	   	query.setTipoQueryGenerico(58);
	   	query.setQueryGenerico(jsonStr);

	   	QueryGenericoResponse response = weatherClient
	   		.getQueryGenericoResponse(query);
	   	weatherClient.printQueryGenericoResponse(response);

	   	ObjectMapper mapper = new ObjectMapper();
	   	String jsonInString = response.getQueryGenericoResponse();

	   	UcsawsPersona n = new UcsawsPersona();
	   	try {
	   	    n = mapper.readValue(jsonInString, UcsawsPersona.class);
	   	} catch (Exception ex) {
	   	    System.out.println(ex);
	   	}
	   	guardado = true;

	   	return guardado;
	}

	public JSONArray buscarPersona(String codigo) throws ParseException, org.json.simple.parser.ParseException 
	{
		JSONArray filas = new JSONArray();
		
		Date date = null;
		
		boolean existe=false;
		
			
			//Statement estatuto = conex.getConnection().createStatement();
		
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			//para registrar se inserta el codigo es 1
			query.setTipoQueryGenerico(2);
			
			query.setQueryGenerico("select id_persona, per.nombre, per.apellido, fecha_nacimiento, ori.nombre as PaisOrigen, act.nombre as PaisActual, gen.descripcion, ci, tel_linea_baja, tel_celular"

				+ " from ucsaws_persona per join ucsaws_pais ori on (per.id_pais_origen = ori.id_pais) join ucsaws_pais act on (per.id_pais_actual = act.id_pais) "
				+ "join ucsaws_genero gen on (per.id_genero = gen.id_genero)"
				
				  + "where upper(per.nombre) like upper('%"+codigo+"%') or upper(per.apellido) like upper('%"+codigo+"%')  or"
				  + " upper(gen.descripcion) like upper('%"+codigo+"%')");
			
			
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("[]")==0){
					JOptionPane.showMessageDialog(null, "La Persona no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
					return filas;
				}
				
				else
			{
				existe=true;
				
				
				
				String generoAntesPartir = response.getQueryGenericoResponse();
				
				JSONParser j = new JSONParser();
				Object ob;
				String part1,part2,part3;
				
					ob = j.parse(generoAntesPartir);
					filas = (JSONArray) ob;
					
					
					
				//	JSONArray fila		= (JSONArray) filas.get(0);
					//JSONArray fila1		= (JSONArray) filas.get(1);
							
//					System.out.print(filas);
//					System.out.print("\\n");
//				//	System.out.print(fila);
//					System.out.print("\\n");
//					System.out.print(fila1);
					
					
					
//					 part1 = (String) array1.get(0);
//					 part2 = (String) array1.get(1);
//					 part3 = (String) array1.get(2);
					 
//					 gen.setDescripcion(part1);
//					 gen.setFecha(part2);
//					 gen.setUsuario(part3);
					
				
				
				
				
				
				

				
				
				
				
//				String[] parts = generoAntesPartir.split(",");
//				
				
				
				
//				DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
//				DateTime dt = formatter.parseDateTime(part2);
				
//				DateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
//				
//				
//					date = formatter.parse(part2);
//					
//				GregorianCalendar newCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
//				newCalendar.setTime(date);
				//GregorianCalendar fecha = date.tog
				
				//fecha.setTime(date);
				
//				gen.setFecha(part2);
//				
//				gen.setUsuario(part3);
				
				
				return filas;
				
			}
			
			

	
		
				
			 
						
	}

	
	public Boolean eliminarPersona(String codigo)
	{
		boolean eliminado = false;

		try {
		    ObjectMapper mapperObj = new ObjectMapper();
		  //  String jsonStr = "";

		    // get Employee object as a json string
		   // jsonStr = mapperObj.writeValueAsString(codigo);

		    ApplicationContext ctx = SpringApplication
			    .run(WeatherConfiguration.class);

		    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		    QueryGenericoRequest query = new QueryGenericoRequest();

		    query.setTipoQueryGenerico(60);

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
			    "Error al intentar eliminar la Persona", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
		return eliminado;
		
	}
	
	
	    public UcsawsPersona obtenerPersonaByCedula(String cedula) {

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();




		// parseo json
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";

		// get Employee object as a json string
		try {
		    jsonStr = mapperObj.writeValueAsString(cedula);
		} catch (Exception e) {
		    System.out.println(e);
		}

		query.setTipoQueryGenerico(59);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		List<UcsawsPersona> persona = new ArrayList<UcsawsPersona>();
		try {
		    persona = mapper.readValue(jsonInString,  new TypeReference<List<UcsawsPersona>>(){});
		} catch (Exception e) {
		    System.out.println(e);
		}
		if(persona.isEmpty()){
		    return new UcsawsPersona();
		}
		else{
		return persona.get(0);
		}
	    }
	    
	    public List<UcsawsPersona> obtenerListaPersonaByCedula(String cedula) {

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();




		// parseo json
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";

		// get Employee object as a json string
		try {
		    jsonStr = mapperObj.writeValueAsString(cedula);
		} catch (Exception e) {
		    System.out.println(e);
		}

		query.setTipoQueryGenerico(59);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		List<UcsawsPersona> persona = new ArrayList<UcsawsPersona>();
		try {
		    persona = mapper.readValue(jsonInString,  new TypeReference<List<UcsawsPersona>>(){});
		} catch (Exception e) {
		    System.out.println(e);
		}
		if(persona.isEmpty()){
		    return  new ArrayList<UcsawsPersona>();
		}
		else{
		return persona;
		}
	    }
	    	
	    
	    public UcsawsPersona obtenerPersonaByIdPersona(String idPersona) {

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();




	 

		query.setTipoQueryGenerico(61);
		query.setQueryGenerico(idPersona);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		UcsawsPersona  persona = new UcsawsPersona();
		try {
		    persona = mapper.readValue(jsonInString,  UcsawsPersona.class);
		} catch (Exception e) {
		    System.out.println(e);
		}
		if(persona.getIdPersona() == null){
		    return new UcsawsPersona();
		}
		else{
		return persona;
		}
	    }
	    
	    	
	    public Boolean actualizarPersona(UcsawsPersona persona) {

		boolean actualizado = false;
		
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {
			// get Employee object as a json string
			jsonStr = mapperObj.writeValueAsString(persona);
			System.out.println(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}


		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(62);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		 
		try {
		    UcsawsPersona p = mapper.readValue(jsonInString, UcsawsPersona.class);
		} catch (Exception ex) {
		    System.out.println(ex);
		}
		actualizado = true;
		
		return actualizado;

	    }

	    
	    
	    
}
