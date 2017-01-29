package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.DeQuePais;
import src.main.java.admin.utils.FechaDeOtroPaisParametrizado;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class EventoValidator {

	public Boolean ValidarCodigo(String codigo) throws ParseException,
			org.json.simple.parser.ParseException {

		boolean existe = false;

		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(2);

		query.setQueryGenerico("SELECT id_evento, nro_evento "
				+ "from ucsaws_evento " + "where  nro_evento = '"

				+ "" + codigo + "'");

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();

		if (res.compareTo("[]") != 0) {

			return existe = true;
		}

		else {
			existe = false;

			return existe;

		}

	}

//	public Boolean ValidarPersona(Integer persona) throws ParseException,
//			org.json.simple.parser.ParseException {
//
//		boolean existe = false;
//
//		ApplicationContext ctx = SpringApplication
//				.run(WeatherConfiguration.class);
//
//		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
//		QueryGenericoRequest query = new QueryGenericoRequest();
//
//		query.setTipoQueryGenerico(2);
//
//		query.setQueryGenerico("SELECT id_candidatos, codigo "
//				+ "from ucsaws_candidatos " + "where  id_persona = "
//
//				+ persona + "");
//
//		QueryGenericoResponse response = weatherClient
//				.getQueryGenericoResponse(query);
//		weatherClient.printQueryGenericoResponse(response);
//
//		String res = response.getQueryGenericoResponse();
//
//		if (res.compareTo("[]") != 0) {
//
//			return existe = true;
//		}
//
//		else {
//			existe = false;
//
//			return existe;
//
//		}
//
//	}
	
	public static Boolean eventoVigente(String desde, String hasta, Integer tipo)  {
		
		
		//obtener pais desde donde se quiere votar
//		DeQuePais ip = new DeQuePais();
//		String MiPais = ip.main().toUpperCase();
//		System.out.println(MiPais);
//		//
//		String fechaHusoHora= "";
//		
//		if(!(MiPais.compareTo("PARAGUAY")==0)){
//		//obtener huso horario
//		FechaDeOtroPaisParametrizado fechaDeOtroPaisParametrizado = new FechaDeOtroPaisParametrizado();
//		DateTime huso = fechaDeOtroPaisParametrizado.main(MiPais);
//		//obtener huso horario
//		
//		 System.out.println(huso.getZone());
//		 fechaHusoHora =   huso.getDayOfMonth() + "/" + huso.getMonthOfYear() + "/" + huso.getYear() + " " + huso.getHourOfDay() + ":" + huso.getMinuteOfHour() + ":" + huso.getSecondOfMinute();
//		
//		//armar fecha hora
//		 
//		 PaisDAO pais = new PaisDAO();
//		 try {
//			 if(MiPais.compareToIgnoreCase("Brazil")==0){
//				 MiPais="BRASIL";
//			 }
//			pais.buscarPais(MiPais);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		}
//		else{
//			fechaHusoHora = "now()";
//		}
//
//		JSONArray filas = new JSONArray();
//		JSONArray fil = new JSONArray();
//
//		Object ob = null;

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		// para registrar se inserta el codigo es 1
		query.setTipoQueryGenerico(2);
		System.out.println(Login.userLogeado);
		//query.setQueryGenerico("select id_evento, descripcion from ucsaws_evento where cast(fch_hasta as timestamp) <= cast (now() as timestamp)");
		
		
		//if((MiPais.compareTo("PARAGUAY")==0)){
		query.setQueryGenerico("select id_evento, descripcion "
				+ " from ucsaws_evento where id_tipo_evento = " +tipo
				+ " and (to_char(fch_desde,'YYYY') >= '" + desde + "')"
				);

//		}else{
//			query.setQueryGenerico("select id_vigencia, id_pais "
//					+ " from ucsaws_vigencia_horario_x_pais where id_pais = " + PaisDAO.idPais + " to_char(fch_vigencia_desde, 'DD/MM/YYYY HH24:MI')  >= to_char(to_timestamp('"+ desde +"', 'DD/MM/YYYY HH24:MI'),'DD/MM/YYYY HH24:MI')  "
//					+ " and (to_char(fch_vigencia_hasta, 'DD/MM/YYYY HH24:MI')  <= to_char(to_timestamp('"+ hasta +"', 'DD/MM/YYYY HH24:MI'),'DD/MM/YYYY HH24:MI')) "
//					//+ " or (to_char(fch_hasta, 'DD/MM/YYYY HH24:MI')  < to_char(now(), 'DD/MM/YYYY HH24:MI')  )"
//					);
//			
//			
//		}
		
	 

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		JSONParser j = new JSONParser();

		String generoAntesPartir = response.getQueryGenericoResponse();
		
		if (generoAntesPartir.compareTo("[]")==0){
			return false;
		}
		
		else{
			
			
		

		

		return true;
		
		}

	}

}
