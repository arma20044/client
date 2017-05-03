package src.main.java.dao.nacionalidades;

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

import entity.UcsawsGenero;
import entity.UcsawsNacionalidad;
import entity.UcsawsPais;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class NacionalidadesDAO {
	
	
    public Boolean guardarNacionalidad(UcsawsNacionalidad nacionalidad) {

   	boolean guardado = false;

   	ObjectMapper mapperObj = new ObjectMapper();
   	String jsonStr = "";
   	try {
   	    // get Employee object as a json string
   	    jsonStr = mapperObj.writeValueAsString(nacionalidad);
   	    System.out.println(jsonStr);
   	} catch (IOException e) {
   	    // TODO Auto-generated catch block
   	    e.printStackTrace();
   	}

   	ApplicationContext ctx = SpringApplication
   		.run(WeatherConfiguration.class);

   	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
   	QueryGenericoRequest query = new QueryGenericoRequest();

   	query.setTipoQueryGenerico(51);
   	query.setQueryGenerico(jsonStr);

   	QueryGenericoResponse response = weatherClient
   		.getQueryGenericoResponse(query);
   	weatherClient.printQueryGenericoResponse(response);

   	ObjectMapper mapper = new ObjectMapper();
   	String jsonInString = response.getQueryGenericoResponse();

   	UcsawsNacionalidad n = new UcsawsNacionalidad();
   	try {
   	    n = mapper.readValue(jsonInString, UcsawsNacionalidad.class);
   	} catch (Exception ex) {
   	    System.out.println(ex);
   	}
   	guardado = true;

   	return guardado;

       }

	public JSONArray buscarNacionalidad(String codigo) throws ParseException, org.json.simple.parser.ParseException 
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
			
			query.setQueryGenerico("Select id_nacionalidad, cod_nacionalidad, desc_nacionalidad"
					+ " from ucsaws_nacionalidad nac join ucsaws_pais p on (nac.id_pais = p.id_pais)"
					+ " where upper(cod_nacionalidad) like upper('%" + codigo + "%')");
			
			
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("[]")==0){
					JOptionPane.showMessageDialog(null, "La Nacionalidad no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
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

//	public void modificarEvento(String codigoASetear, String codigoWhere) {
//		
//		
//		try{
//			
//		
//		ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);
//
//		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
//		QueryGenericoRequest query = new QueryGenericoRequest();
//		
//		
//		query.setTipoQueryGenerico(3);
//		
//		query.setQueryGenerico("update ucsaws_evento "
//				+ "set descripcion = upper('" +codigoASetear+"') , fch_upd = now() , usuario_upd = '" +  Login.userLogeado
//				+ "' where id_evento = "
//				
//				+ codigoWhere
//				+ "");
//		
//		
//		
//		QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
//		weatherClient.printQueryGenericoResponse(response);
//		
//		String res = response.getQueryGenericoResponse();
//		
//	} catch (Exception ex) {
//		JOptionPane.showMessageDialog(null,"Error al intentar modificar","Error",JOptionPane.ERROR_MESSAGE);
//	}
//	//JOptionPane.showMessageDialog(null,"Excelente, se ha modificado el genero.");
//	
//
////			if(res.compareTo("ERRORRRRRRR")==0){
////				JOptionPane.showMessageDialog(null, "El Genero: "+ codigo +" no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
////				return gen;
////			}
//
//	}

	public Boolean eliminarNacionalidad(String codigo)
	{
		boolean eliminado = false;
		
		try{
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			
			query.setTipoQueryGenerico(4);
			
			query.setQueryGenerico("DELETE FROM ucsaws_nacionalidad WHERE"
					+ " id_nacionalidad = "
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
			JOptionPane.showMessageDialog(null,"Error al intentar eliminar la Nacionalidad","Error",JOptionPane.ERROR_MESSAGE);
		}
		return eliminado;
		
	}
	
	    public UcsawsNacionalidad obtenerNacionalidadByCodigoYNombre(String codigoNacionalidad,
		    String descNacionalidad) {

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		List<String> lista = new ArrayList<String>();
		lista.add(codigoNacionalidad);
		lista.add(descNacionalidad);
		lista.add(VentanaBuscarEvento.evento.toString());

		// parseo json
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";

		// get Employee object as a json string
		try {
		    jsonStr = mapperObj.writeValueAsString(lista);
		} catch (Exception e) {
		    System.out.println(e);
		}

		query.setTipoQueryGenerico(49);
		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();

		UcsawsNacionalidad nacionalidad = new UcsawsNacionalidad();
		try {
		    nacionalidad = mapper.readValue(jsonInString, UcsawsNacionalidad.class);
		} catch (Exception e) {
		    System.out.println(e);
		}
		return nacionalidad;
	    }
}
