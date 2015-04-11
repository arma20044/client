package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Date;

import org.json.simple.JSONArray;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class TipoCandidatoValidator {
	
	public Boolean ValidarTipoCandidato(String codigo) throws ParseException, org.json.simple.parser.ParseException 
	{
		JSONArray filas = new JSONArray();
		Genero gen = new Genero();
		Date date = null;
		
		boolean existe=false;
		
			
			//Statement estatuto = conex.getConnection().createStatement();
		
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			
			query.setTipoQueryGenerico(2);
			
			query.setQueryGenerico("SELECT id_tipo_candidato, descripcion "
					+ "from ucsaws_tipo_candidato "
					+ "where  upper(codigo) like "
					
					+ "upper('%"
					+ codigo
					+ "%')");
			
			
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("[]")!=0){
					
					return existe=true;
				}
				
				else
			{
				existe=false;
				
				
				
				
				
				
				return existe;
				
			}
			
			

	
		
				
			 
						
	}

}
