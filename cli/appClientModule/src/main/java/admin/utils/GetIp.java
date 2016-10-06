package src.main.java.admin.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetIp {
	
	 public static void main(String[] args) throws UnknownHostException {
	
	InetAddress IP=InetAddress.getLocalHost();
	System.out.println(IP.toString());
	
	 }

}
