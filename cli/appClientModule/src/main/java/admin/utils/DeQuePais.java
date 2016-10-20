package src.main.java.admin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.BreakIterator;

import net.firefang.ip2c.Country;
import net.firefang.ip2c.IP2Country;

public class DeQuePais {
	
	public String main() {

		//String iptest = "180.149.247.26";
		
		//obtener ip
		String result = "";
		try {
			
		
		
		
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));

		String ip = in.readLine(); //you get the IP as a String
		System.out.println(ip);
		
		
		//obtener ip
		
		String iptest = ip;
		
		//para testear comentar despues huso usa
		iptest = //"168.63.24.174"; ip usa
		"193.110.128.109"; // ip españa
		//para testear comentar despues
		
		int caching1 = IP2Country.MEMORY_CACHE;
		IP2Country ip2c = new IP2Country(caching1);
		
		Country c = ip2c.getCountry(iptest);
		if(iptest.compareTo("181.122.212.64")==0){
			result = "Paraguay";
			
		}
		else{
		if (c == null)	{
		        System.out.println("UNKNOWN");                          
		}
		else{
		       result = c.getName();      
		}
		}} catch (Exception e) {
			// TODO: handle exception
		}
		
		//File file = new File("ip-to-country.bin");
		//System.out.println(file.getAbsolutePath());    
		
	/*	String ip = "85.64.225.159";
		int caching1 = IP2Country.NO_CACHE;  // Straight on file, Fastest startup, slowest queries
		int caching2 = IP2Country.MEMORY_MAPPED; // Memory mapped file, fast startup, fast queries.
		int caching3 = IP2Country.MEMORY_CACHE; // load file into memory, slowerst startup, fastest queries
		IP2Country ip2c = new IP2Country(caching1);
		Country c = ip2c.getCountry(ip);
		if (c == null)
		{
		        System.out.println("UNKNOWN");                          
		}
		else
		{
		        // will output IL ISR ISRAEL
		        System.out.println(c.get2cStr() + " " + c.get3cStr() + " " + c.getName());      
		}*/
		
		return result;
		}

}
