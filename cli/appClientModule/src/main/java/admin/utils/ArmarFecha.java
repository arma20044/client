package src.main.java.admin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ArmarFecha {

	public Date armarFecha(String fecha, String hora){
	SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
	//String fechaIn = fecha;
	Date fechaOut = null;
	try {

		fechaOut = formatoDelTexto.parse(fecha);

	} catch (ParseException ex) {

	ex.printStackTrace();

	}

	System.out.println(fecha.toString());
	
	
	return fechaOut;}
	
	public Date armarFechaSinHora(String fecha){
	SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
	//String fechaIn = fecha;
	Date fechaOut = null;
	try {

		fechaOut = formatoDelTexto.parse(fecha);

	} catch (ParseException ex) {

	ex.printStackTrace();

	}

	System.out.println(fecha.toString());
	
	
	return fechaOut;}
	
	public Date armarFecha(String fecha){
	SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	//String fechaIn = fecha;
	Date fechaOut = null;
	try {

		fechaOut = formatoDelTexto.parse(fecha);

	} catch (ParseException ex) {

	ex.printStackTrace();

	}

	System.out.println(fecha.toString());
	
	
	return fechaOut;}
	
	public Date juntarFecha(String fecha, String hora, String minuto){
	SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	//String fechaIn = fecha;
	Date fechaOut = null;
	try {

		fechaOut = formatoDelTexto.parse(fecha + " " + hora + ":" + minuto);

	} catch (ParseException ex) {

	ex.printStackTrace();

	}

	System.out.println(fecha.toString());
	
	
	return fechaOut;}
	
	public Date juntarFecha(Date fecha, Integer hora, Integer minuto){
	    
	      //Date d1 = new Date();
	      Calendar cl = Calendar. getInstance();
	      cl.setTime(fecha);
	      System.out.println("today is " + fecha.toString());
	      //cl. add(Calendar.MONTH, 1);
	    //  System.out.println("date after a month will be " + cl.getTime().toString() );
	      cl. add(Calendar.HOUR, hora);
	      System.out.println("date after 7 hrs will be " + cl.getTime().toString() );
	      cl. add(Calendar.MINUTE, minuto);
	      System.out.println("date after 3 years will be " + cl.getTime().toString() );    
	    
	    return cl.getTime();
	    
//	/*SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//	//String fechaIn = fecha;
//	Date fechaOut = null;
//	try {
//
//		fechaOut = formatoDelTexto.parse(fecha + " " + hora + ":" + minuto);
//
//	} catch (ParseException ex) {
//
//	ex.printStackTrace();
//
//	}
//
//	System.out.println(fecha.toString());
//	
//	
//	return fechaOut;}*/
	}
	
	public Date juntarFecha(Date fecha, String horaMinuto){
	    
	    String string = horaMinuto;
	    String[] parts = string.split(":");
	    String hora = parts[0]; //hora
	    String minuto = parts[1]; //minuto
	    
	      //Date d1 = new Date();
	      Calendar cl = Calendar. getInstance();
	      cl.setTime(fecha);
	      System.out.println("today is " + fecha.toString());
	      //cl. add(Calendar.MONTH, 1);
	    //  System.out.println("date after a month will be " + cl.getTime().toString() );
	      //cl. add(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
	      cl.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hora));
	      System.out.println("date after 7 hrs will be " + cl.getTime().toString() );
	      cl.set(Calendar.MINUTE, Integer.parseInt(minuto));
	      System.out.println("fecha con hora con minuto  " + cl.getTime().toString() ); 
	      cl.set(Calendar.SECOND, 0);
	    
	    return cl.getTime();
	    
//	/*SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//	//String fechaIn = fecha;
//	Date fechaOut = null;
//	try {
//
//		fechaOut = formatoDelTexto.parse(fecha + " " + hora + ":" + minuto);
//
//	} catch (ParseException ex) {
//
//	ex.printStackTrace();
//
//	}
//
//	System.out.println(fecha.toString());
//	
//	
//	return fechaOut;}*/
	}

}
