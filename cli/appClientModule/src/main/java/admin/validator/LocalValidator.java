package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class LocalValidator {

	public Boolean ValidarCodigo(String nro, String desc, Integer distrito)
			throws ParseException, org.json.simple.parser.ParseException {

		boolean existe = false;

		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(2);

		query.setQueryGenerico("SELECT id_local, nro_local "
				+ "from ucsaws_local " + "where nro_local ='" + nro
				+ "'  and id_zona =" +distrito+ "" );

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
}
