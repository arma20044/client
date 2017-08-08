package src.main.java.admin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TestDate4 {

  
  public static void main(String[] args) throws ParseException {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date date1 = sdf.parse("2017-07-11 07:00");
    Date date2 = sdf.parse("2017-07-11 07:00");

    System.out.println("date1 : " + sdf.format(date1));
    System.out.println("date2 : " + sdf.format(date2));

    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date1);
    cal2.setTime(date2);

    if (cal1.after(cal2)) {
        System.out.println("Date1 is mayor Date2");
    }

    if (cal1.before(cal2)) {
        System.out.println("Date1 is menor Date2");
    }

    if (cal1.equals(cal2)) {
        System.out.println("Date1 es igual Date2");
    }
}


}
