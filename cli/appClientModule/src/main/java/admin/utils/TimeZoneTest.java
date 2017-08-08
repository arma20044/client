package src.main.java.admin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneTest {
  
  
  public static void main(String args[]) {
  
//  String[] tzIds = TimeZone.getAvailableIDs();
//  List<String> al = new ArrayList<String>();
//  for (String timeZoneId : tzIds) {
//    if (timeZoneId.toUpperCase().contains("asunci".toUpperCase())) {
//      al.add(timeZoneId);
//    }
//  }
//  System.out.println(al);
    
    System.out.println(System.getProperty("user.country"));
    System.out.println(Locale.getDefault().getCountry());
    System.getProperty("user.country"); 
    System.getProperty("user.language");
  }
}
