package src.main.java.dao.tipoEvento;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsTipoEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class TipoEventoDAO {
	
	
	public UcsawsTipoEvento buscarTipoEventoById(Integer idTipoEvento){
		
		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx
				.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		
		query.setTipoQueryGenerico(32);
		query.setQueryGenerico(idTipoEvento.toString());
		
		
		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);
		
		
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString  =response.getQueryGenericoResponse();

		UcsawsTipoEvento tipoEvento = new UcsawsTipoEvento();
		try{
		tipoEvento = mapper.readValue(jsonInString, UcsawsTipoEvento.class);
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		if (tipoEvento.getIdEvento()!= null){
			return tipoEvento;
		}
		else{
			return tipoEvento;
		}
		
		//return null;
		
	}
	
	
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

	public JSONArray buscarTipoEvento(String codigo) throws ParseException, org.json.simple.parser.ParseException 
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
			
			query.setQueryGenerico("SELECT id_tipo_evento, descripcion "
					+ "from ucsaws_tipo_evento "
				  + "where upper(descripcion) like upper('%"+codigo+"%')");
			
			
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("[]")==0){
					JOptionPane.showMessageDialog(null, "El Tipo Evento no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
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

	public void modificarEvento(String codigoASetear, String codigoWhere) {
		
		
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

	public Boolean eliminarTipoEvento(String codigo)
	{
		boolean eliminado = false;
		
		try{
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			
			query.setTipoQueryGenerico(4);
			
			query.setQueryGenerico("DELETE FROM ucsaws_tipo_evento WHERE"
					+ " id_tipo_evento = "
					+ codigo 
					 );
			
			
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
			
			if (res.compareTo("ERRORRRRRRR")== 0){
				
				
				eliminado = false;
			}
			else{
				eliminado = true;
			}
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error al intentar eliminar el Tipo Evento","Error",JOptionPane.ERROR_MESSAGE);
		}
		return eliminado;
		
	}
	
	    public UcsawsTipoEvento obtenerTipoEventoById(Integer idTipoEvento) {

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(32);
		query.setQueryGenerico(idTipoEvento.toString());

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		UcsawsTipoEvento tipoEvento = new UcsawsTipoEvento();
		try {
		    tipoEvento = mapper.readValue(jsonInString, UcsawsTipoEvento.class);
		} catch (Exception e) {
		    System.out.println(e);
		}
		return tipoEvento;
	    }
}
