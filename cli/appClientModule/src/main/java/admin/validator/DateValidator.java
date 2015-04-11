package src.main.java.admin.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.joda.time.DateTime;
import org.postgresql.core.Utils;

public class DateValidator {

	public boolean isThisDateValid(String dateToValidate, String dateFromat){
		 
		if(dateToValidate == null || dateToValidate.length() == 0){
			return false;
		}
 
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
 
		try {
 
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);
 
		} catch (ParseException e) {
 
			e.printStackTrace();
			return false;
		}
 
		return true;
	}
	
	public boolean fechaHastaEsMenor(String fechaDesde, String fechaHasta){
		boolean resultado = false;
		
		Date desde, hasta;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			desde = sdf.parse(fechaDesde);
			System.out.println(desde);
			
			hasta =  sdf.parse(fechaHasta);
			System.out.println(hasta);
			
			
			if (desde.compareTo(hasta) > 0){
				JOptionPane.showMessageDialog(null,"La fecha hasta no puede ser inferior a la fecha desde.");
				resultado = true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		
		return resultado;
		
	}
 
}
