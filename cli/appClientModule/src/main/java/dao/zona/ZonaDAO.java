package src.main.java.dao.zona;

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



import entity.UcsawsZona;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class ZonaDAO {
	
	
//	public void registrarPersona(PersonaVo miPersona)
//	{
//		Conexion conex= new Conexion();
//		
//		try {
//			Statement estatuto = conex.getConnection().createStatement();
//			estatuto.executeUpdate("INSERT INTO persona VALUES ('"+miPersona.getIdPersona()+"', '"
//					+miPersona.getNombrePersona()+"', '"+miPersona.getEdadPersona()+"', '"
//					+miPersona.getProfesionPersona()+"', '"+miPersona.getTelefonoPersona()+"')");
//			JOptionPane.showMessageDialog(null, "Se ha registrado Exitosamente","Informaci�n",JOptionPane.INFORMATION_MESSAGE);
//			estatuto.close();
//			conex.desconectar();
//			
//		} catch (SQLException e) {
//            System.out.println(e.getMessage());
//			JOptionPane.showMessageDialog(null, "No se Registro");
//		}
//	}

	public JSONArray buscarZona(String codigo) throws ParseException, org.json.simple.parser.ParseException 
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
			
			query.setQueryGenerico("SELECT id_zona, desc_zona "
					+ " from ucsaws_zona zona join ucsaws_distrito dis on (zona.id_distrito = dis.id_distrito)"
				  + "where upper(desc_zona) like upper('%"+codigo+"%') and zona.id_evento = " + VentanaBuscarEvento.evento);
			
			
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("[]")==0){
					JOptionPane.showMessageDialog(null, "La Zona no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
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

	public void modificarZona(String codigoASetear, String codigoWhere) {
		
		
		try{
			
		
		ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();
		
		
		query.setTipoQueryGenerico(3);
		
		query.setQueryGenerico("update ucsaws_evento "
				+ "set descripcion = upper('" +codigoASetear+"') , fch_upd = now() , usuario_upd = '" +  Login.userLogeado
				+ "' where id_evento = "
				
				+ codigoWhere
				+ "");
		
		
		
		QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);
		
		String res = response.getQueryGenericoResponse();
		
	} catch (Exception ex) {
		JOptionPane.showMessageDialog(null,"Error al intentar modificar","Error",JOptionPane.ERROR_MESSAGE);
	}
	//JOptionPane.showMessageDialog(null,"Excelente, se ha modificado el genero.");
	

//			if(res.compareTo("ERRORRRRRRR")==0){
//				JOptionPane.showMessageDialog(null, "El Genero: "+ codigo +" no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
//				return gen;
//			}

	}


	
	
	    public List<UcsawsZona> obtenerZonaByIdEvento(Integer idEvento) {

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(74);
		query.setQueryGenerico(idEvento.toString());

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		List<UcsawsZona> zona = new ArrayList<UcsawsZona>();
		try {
		    zona = mapper.readValue(jsonInString, new TypeReference<List<UcsawsZona>>(){});
		    
		    
		} catch (Exception e) {
		    System.out.println(e);
		}
		return zona;
	    }
	    
	    
	    
	    public Boolean eliminarZona(String idZona) {
		boolean eliminado = false;

		try {

		    ApplicationContext ctx = SpringApplication
			    .run(WeatherConfiguration.class);

		    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		    QueryGenericoRequest query = new QueryGenericoRequest();

		    query.setTipoQueryGenerico(76);

		    query.setQueryGenerico(idZona);

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
			    "Error al intentar eliminar la Zona.", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
		return eliminado;

	    }
	
	public boolean guardarZona(UcsawsZona zona) {
		boolean guardado = false;

		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {
		    // get Employee object as a json string
		    jsonStr = mapperObj.writeValueAsString(zona);
		    System.out.println(jsonStr);
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(75);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String string = response.getQueryGenericoResponse();

		UcsawsZona n = new UcsawsZona();
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
