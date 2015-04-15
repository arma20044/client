package src.main.java.dao.genero;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.google.gson.JsonArray;

import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class GeneroDAO {
	
	
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

	public JSONArray buscarGenero(String codigo) throws ParseException, org.json.simple.parser.ParseException 
	{
		JSONArray filas = new JSONArray();
		Genero gen = new Genero();
		Date date = null;
		
		boolean existe=false;
		
			
			//Statement estatuto = conex.getConnection().createStatement();
		
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			//para registrar se inserta el codigo es 1
			query.setTipoQueryGenerico(2);
			
			query.setQueryGenerico("SELECT id_candidatos, id_persona, id_lista, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS'), to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS')"
					+ " , usuario_ins, usuario_upd from ucsaws_candidatos "
					+ "where upper(codigo) like "
					+ "upper('%" + codigo + "%') or "
					+ "upper(descripcion) like "
					+ "upper('%"
					+ codigo
					+ "%') order by id_candidatos ");
			
			
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("[]")==0){
					JOptionPane.showMessageDialog(null, "El Candidato: "+ codigo +" no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
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

	public void modificarGenero(String codigoASetear, String codigoWhere) {
		
		
		try{
			
		
		ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();
		
		
		query.setTipoQueryGenerico(3);
		
		query.setQueryGenerico("update ucsaws_genero "
				+ "set descripcion = upper('" +codigoASetear+"') , fch_upd = now() , usuario_upd = '" +  Login.userLogeado
				+ "' where id_genero = "
				
				+ codigoWhere
				+ "");
		
		
		
		QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);
		
		String res = response.getQueryGenericoResponse();
		
	} catch (Exception ex) {
		JOptionPane.showMessageDialog(null,"Error al intentar modificar","Error",JOptionPane.ERROR_MESSAGE);
	}
	JOptionPane.showMessageDialog(null,"Excelente, se ha modificado el genero.");
	

//			if(res.compareTo("ERRORRRRRRR")==0){
//				JOptionPane.showMessageDialog(null, "El Genero: "+ codigo +" no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
//				return gen;
//			}

	}

	public Boolean eliminarGenero(String codigo)
	{
		boolean eliminado = false;
		
		try{
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			
			query.setTipoQueryGenerico(4);
			
			query.setQueryGenerico("DELETE FROM ucsaws_genero WHERE"
					+ " id_genero = "
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
			JOptionPane.showMessageDialog(null,"Error al intentar eliminar","Error",JOptionPane.ERROR_MESSAGE);
		}
		return eliminado;
		
	}
}
