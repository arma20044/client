package src.main.java.admin.utils;

import java.util.Calendar;
import java.util.Date;

public class FechaUtils {
  
  
    public static Integer obtenerAnhoDeDate (Date fecha){
      
      
   // create a calendar
      Calendar cal = Calendar.getInstance();
      cal.setTime(fecha);  //use java.util.Date object as arguement
      // get the value of all the calendar date fields.
      System.out.println("Calendar's Year: " + cal.get(Calendar.YEAR));
      System.out.println("Calendar's Month: " + cal.get(Calendar.MONTH));
      System.out.println("Calendar's Day: " + cal.get(Calendar.DATE));
      
      
      return cal.get(Calendar.YEAR);
    }

}
