package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class PersonaValidator {

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

		query.setQueryGenerico("SELECT id_persona, ci "
				+ "from ucsaws_persona " + "where  ci = '"

				+ "" + codigo + "' and id_evento = " + VentanaBuscarEvento.evento);

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

}
