package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jfree.date.DateUtilities;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class VigenciaValidator {

	public Boolean ValidarCodigo(Integer idPais)
			throws ParseException, org.json.simple.parser.ParseException {

		boolean existe = false;

		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(2);

		query.setQueryGenerico("SELECT id_vigencia, id_pais "
				+ "from ucsaws_vigencia_horario_x_pais " + "where id_pais =" + idPais
				+ " and id_evento = " + VentanaBuscarEvento.evento );

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
	
	
	public Boolean ValidarRango(String fechaDesde, String fechaHasta)
			throws ParseException, org.json.simple.parser.ParseException {

		boolean existe = false;
		
		//fechaDede ini
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern   ("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(fechaDesde, formatter);
		System.out.println(dateTime);
		
		//fechaDesde fin
		
		//fechaHasta ini
		
		DateTimeFormatter formatter2 = DateTimeFormat.forPattern   ("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime2 = LocalDateTime.parse(fechaHasta, formatter2);
		System.out.println(dateTime2);
		
		//fechaHasta fin
		
		
		//convert Dato to String  luego a localdatetim ini VentanaBuscarEvento.fechaDesdeDate
		Date inicioEvento =  VentanaBuscarEvento.fechaDesdeDate;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		// Get the date today using Calendar object.
		Date inicio = inicioEvento;       
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		String reportDate = df.format(inicio);

		// Print what date is today!
		System.out.println("inicioEvento Date: " + reportDate);
		
		DateTimeFormatter formatter3 = DateTimeFormat.forPattern   ("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime3 = LocalDateTime.parse(reportDate, formatter3);
		System.out.println(dateTime3);
		
		//convert Dato to String  luego a localdatetim fin
		
		
		//convert Dato to String  luego a localdatetim ini VentanaBuscarEvento.fechaHastaDate
		
		
		
		Date finEvento =  VentanaBuscarEvento.fechaHastaDate;
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		// Get the date today using Calendar object.
		Date fin = finEvento;       
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		String reportDate2 = df2.format(fin);

		// Print what date is today!
		System.out.println("finEvento Date: " + reportDate2);
		
		DateTimeFormatter formatter4 = DateTimeFormat.forPattern   ("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime4 = LocalDateTime.parse(reportDate2, formatter4);
		System.out.println(dateTime4);
		
		//convert Dato to String  luego a localdatetim fin VentanaBuscarEvento.fechaHastaDate
		
		
		
		/* 
		 * if (dateTime.isAfter(dateTime3)){
			System.out.println(dateTime.getHourOfDay()+":"+ dateTime.getMinuteOfHour()+ ">");
			System.out.println(dateTime3.getHourOfDay()+":"+ dateTime3.getMinuteOfHour());
		}
		else
			if(dateTime.isBefore(dateTime3)){
				System.out.println(dateTime.getHourOfDay()+":"+ dateTime.getMinuteOfHour()+ "<");
				System.out.println(dateTime3.getHourOfDay()+":"+ dateTime3.getMinuteOfHour());

			}
		*/
		
		LocalDateTime diaSiguiente = (dateTime.plusDays(1));
		LocalDateTime diaAnterior = (dateTime.minusDays(1));
		
		LocalDateTime first = dateTime;
		LocalDateTime second = dateTime2;

		LocalDate firstDate = first.toLocalDate();
		LocalDate secondDate = second.toLocalDate();

//		firstDate.compareTo(diaAnterior);
		
		
		if(dateTime.isBefore(dateTime3) || dateTime2.isAfter(dateTime4) )
		{
			//System.out.println("Fuera de rango");
			//existe = true;
		}
		
		


		

			return existe;

		

	}
}
