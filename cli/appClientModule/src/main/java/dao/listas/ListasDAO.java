package src.main.java.dao.listas;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.Lista;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;


public class ListasDAO {
	
	
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

	public Lista buscarLista(String codigo) throws ParseException 
	{
		Lista gen = new Lista();
		Date date = null;
		
		boolean existe=false;
		
			
			//Statement estatuto = conex.getConnection().createStatement();
		
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			//para registrar se inserta el codigo es 1
			query.setTipoQueryGenerico(2);
			
			query.setQueryGenerico("SELECT nro_lista, nombre_lista,descripcion, anho, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS'), usuario_ins  from ucsaws_listas "
					+ "where nro_lista = "
					
					+ codigo + "");
			
			
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("ERRORRRRRRR")==0){
					JOptionPane.showMessageDialog(null, "La Lista: "+ codigo +" no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
					return gen;
				}
				
				else
			{
				existe=true;
				
				
				
				String generoAntesPartir = response.getQueryGenericoResponse();
				String[] parts = generoAntesPartir.split(",");
				String part1 = parts[0]; //nro lista
				String part2 = parts[1]; // nombre lista
				String part3 = parts[2]; // descripcion
				String part4 = parts[3]; // año
				String part5 = parts[4]; // fecha insert
				String part6 = parts[5]; // usuario insert
				
				
				//seteo de la entidad
				gen.setAnhoLista(part4);
				gen.setDescripcion(part3);
				gen.setNombreLista(part2);
				gen.setNroLista(part1);
				gen.setUsuIns(part6);
				gen.setFechaIns(part5);
				
				
			
				
				
				
				
				
				
				return gen;
				
			}
			
			

	
		
				
			 
						
	}

	public void modificarLista(String codigoASetear, String codigoWhere , String descripcion, String fecha, String nombrelista) {
		
		
		try{
			
		
		ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();
		
		
		query.setTipoQueryGenerico(3);
		
		query.setQueryGenerico("update ucsaws_listas "
				+ "set nro_lista = '" +codigoASetear+"',  descripcion = upper('" +descripcion+"') , fch_upd = now() , usuario_upd = 'ucsa2014' " 
				+ ",  nombre_lista =upper('" + nombrelista + "'),  anho ='" + fecha 
				+ "' where nro_lista = "
				+ "'"
				+ codigoWhere
				+ "' ");
		
		
		
		QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);
		
		String res = response.getQueryGenericoResponse();
		
	} catch (Exception ex) {
		JOptionPane.showMessageDialog(null,"Error al intentar modificar","Error",JOptionPane.ERROR_MESSAGE);
	}
	JOptionPane.showMessageDialog(null,"Excelente, se ha modificado la Lista " + codigoWhere +".");
	

//			if(res.compareTo("ERRORRRRRRR")==0){
//				JOptionPane.showMessageDialog(null, "El Genero: "+ codigo +" no Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
//				return gen;
//			}

	}

	public boolean eliminarLista(String codigo)
	{
		boolean eliminado = false;
		
		try{
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			
			query.setTipoQueryGenerico(4);
			
			query.setQueryGenerico("DELETE FROM ucsaws_listas WHERE"
					+ " id_lista = "
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
			JOptionPane.showMessageDialog(null,"Error al intentar eliminar la lista","Error",JOptionPane.ERROR_MESSAGE);
		}
		return eliminado;
		
	}
}
