package src.main.java.admin.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sp {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String usuario = "arma20044";
		String contrasenha = "1";
		
		
		//PasswordEncoder pe = null;
		
		//String usuarioEncoded = pe.encodePassword(contrasenha, usuario.toLowerCase());
		
		md5(contrasenha);
		
		//System.out.println();
		
		
		
		
		

	}
	
	public static void md5(String password) {
		
	     

	        //single buy
	    //	String password = "4N7.6uq$($7YO,CuO10LK5IjyV$10PbcqTr3+6Gc205.00PYG";
	    	
	    	//Catastro de Tarjeta (Cards_new) 
	    	//md5(private_key + card_id + user_id + "request_new_card”)
	    	//String password = "4N7.6uq$($7YO,CuO10LK5IjyV$10PbcqTr3+6Gc1966389request_new_card";

	        MessageDigest md = null;
			try {
				//md = MessageDigest.getInstance("MD5");
				md = MessageDigest.getInstance("SHA-512");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashInBytes) {
	            sb.append(String.format("%02x", b));
	        }
	        System.out.println(sb.toString());

	    
		
	}
	
	
 
	
	

}
