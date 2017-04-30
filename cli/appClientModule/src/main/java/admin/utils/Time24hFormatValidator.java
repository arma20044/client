package src.main.java.admin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Time24hFormatValidator {

	private Pattern pattern;
	  private Matcher matcher;

	  private static final String TIME12HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

	  public Time24hFormatValidator(){
		  pattern = Pattern.compile(TIME12HOURS_PATTERN);
	  }

	 
	  public boolean validate(final String time){		  
		  matcher = pattern.matcher(time);
		  System.out.println(matcher.matches());
		  return matcher.matches();	    	    
	  }
}
