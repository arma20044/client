package src.main.java.admin.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class FechaDeOtroPaisParametrizado {
	
	public DateTime main(String Pais) {
		// get current moment in default time zone
	    DateTime dt = new DateTime();
	    // translate to New York local time
	    // DateTime dtNewYork = dt.withZone(DateTimeZone.forID("EST5EDT")); // huso new jersey
	    DateTime DateResult= null;
	    //DateTime DateResult = dt.withZone(DateTimeZone.forID("America/New_York")); // huso eeuu
	    // DateTime dtNewYork = dt.withZone(DateTimeZone.forID("America/Argentina/Buenos_Aires")); // huso argetina
	  //  DateTime dtNewYork = dt.withZone(DateTimeZone.forID("Europe/Madrid")); // huso españa
	    if (Pais.compareTo("SPAIN")==0){
	    	DateResult = dt.withZone(DateTimeZone.forID("Europe/Madrid")); 
	    }
	    
	   // if (Pais.compareTo(""))
	    
	    System.out.println(DateResult.getZone());
	    System.out.println(DateResult.getDayOfMonth() + "/" + DateResult.getMonthOfYear() + "/" + DateResult.getYear());

	    System.out.println("***");
	    System.out.println(DateResult.getHourOfDay() + ":" + DateResult.getMinuteOfHour() + ":" + DateResult.getSecondOfMinute());
	    
	    
	    
		return DateResult;
	    
	    
	}
	
	

}
