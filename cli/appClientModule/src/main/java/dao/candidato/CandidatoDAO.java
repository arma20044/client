package src.main.java.dao.candidato;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class CandidatoDAO {
	
	
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

	public JSONArray buscarCandidato(String codigo) throws ParseException, org.json.simple.parser.ParseException 
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
			
			query.setQueryGenerico("SELECT id_candidatos, ca.codigo,  to_char(ca.fch_ins, 'DD/MM/YYYY HH24:MI:SS'), to_char(ca.fch_upd, 'DD/MM/YYYY HH24:MI:SS') , ca.usuario_ins,"
					+ " ca.usuario_upd "
					+ "from ucsaws_candidatos ca join ucsaws_persona per on (ca.id_persona = per.id_persona)"
				  + "join ucsaws_listas li on (ca.id_lista = li.id_lista) join ucsaws_tipo_candidato tcan on (ca.id_tipo_candidato = tcan.id_tipo_candidato) "
				  + "where upper(tcan.descripcion) like upper('%"+codigo+"%') or upper(li.descripcion) like upper('%"+codigo+"%')  or"
				  + " upper(per.nombre) like upper('%"+codigo+"%') or upper(per.apellido) like upper('%"+codigo+"%')");
			
			
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("[]")==0){
					JOptionPane.showMessageDialog(null, "El Candidato no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
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

	public Boolean actualizarCandidato(String codigo, String persona, String lista, String id) {
		boolean actualizado = false;

		try {

			ApplicationContext ctx = SpringApplication
					.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();

			query.setTipoQueryGenerico(3);

			query.setQueryGenerico("UPDATE ucsaws_candidatos    SET codigo= '" + codigo
					+ "', id_persona= " + persona + ", "
					+ "fch_upd= now() , usuario_upd= " + Login.userLogeado + " , "
					+ " id_lista= " + lista 
					+ " WHERE id_candidatos =" + id + " and id_evento = "+ VentanaBuscarEvento.evento);

			QueryGenericoResponse response = weatherClient
					.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);

			String res = response.getQueryGenericoResponse();

			if (res.compareTo("ERRORRRRRRR") == 0) {

				actualizado = false;
			} else {
				actualizado = true;
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Error al intentar actualizar el Candidato.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return actualizado;

	}

	public Boolean eliminarCandidato(String codigo)
	{
		boolean eliminado = false;
		
		try{
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			
			query.setTipoQueryGenerico(4);
			
			query.setQueryGenerico("DELETE FROM ucsaws_candidatos WHERE"
					+ " id_candidatos = "
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
			JOptionPane.showMessageDialog(null,"Error al intentar eliminar el candidato","Error",JOptionPane.ERROR_MESSAGE);
		}
		return eliminado;
		
	}
}
