package src.main.java.admin.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class FechaDeOtroPais {
	
	public static void main(String[] args) {
		// get current moment in default time zone
	    DateTime dt = new DateTime();
	    // translate to New York local time
	    // DateTime dtNewYork = dt.withZone(DateTimeZone.forID("EST5EDT")); // huso new jersey
	    
	    DateTime dtNewYork = dt.withZone(DateTimeZone.forID("America/New_York")); // huso eeuu
	    // DateTime dtNewYork = dt.withZone(DateTimeZone.forID("America/Argentina/Buenos_Aires")); // huso argetina
	  //  DateTime dtNewYork = dt.withZone(DateTimeZone.forID("Europe/Madrid")); // huso españa
	    
	    
	    System.out.println(dtNewYork.getZone());
	    System.out.println(dtNewYork.getDayOfMonth() + "/" + dtNewYork.getMonthOfYear() + "/" + dtNewYork.getYear());

	    System.out.println("***");
	    System.out.println(dtNewYork.getHourOfDay() + ":" + dtNewYork.getMinuteOfHour() + ":" + dtNewYork.getSecondOfMinute());
	    
	    
	}
	
	

}
