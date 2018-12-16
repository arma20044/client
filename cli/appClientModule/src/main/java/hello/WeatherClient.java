package src.main.java.hello;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import hello.wsdl.AutenticarRequest;
import hello.wsdl.AutenticarResponse;
import hello.wsdl.ConsultarRequest;
import hello.wsdl.ConsultarResponse;
import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;
import hello.wsdl.VotarRequest;
import hello.wsdl.VotarResponse;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;



public class WeatherClient extends WebServiceGatewaySupport {
	
	//local
	//public final static String URL = "http://voto.ws:8080/";
	
//	apunta a ubuntu maquina virtual
//	public final static String URL = "http://192.168.1.103:8080/";
	
//3 enero 2017	public final static String URL = "http://169.168.0.151:8080/";
	
	///test tcpmon
	//public final static String URL = "https://localhost/";
	///test tcpmon
	
	//public final static String URL = "https://ubuntu/";  //con maquina virtual https
	//public final static String URL = "http://localhost:8080/";  //sin maquina virtual http
    public final static String URL = "http://localhost:8080/";

	public ConsultarResponse getConsultarResponse(ConsultarRequest consulta) {
		ConsultarRequest request = new ConsultarRequest();
		request.setCedula(consulta.getCedula());
		request.setOrigenPeticion(1);
		

		System.out.println();
		System.out.println("Requesting forecast for " + request.getCedula());

		ConsultarResponse response = (ConsultarResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						URL + "VotoWS/votoService.wsdl?ConsultarRequest"));

		return response;
	}
	
	public VotarResponse getVotarResponse(VotarRequest votar) {
		VotarRequest request = new VotarRequest();
		request.setCedula(votar.getCedula());
		request.setOrigenPeticion(1);
		request.setCandidato(votar.getCandidato());
		

		System.out.println();
		System.out.println("Requesting forecast for " + request.getCedula());

		VotarResponse response = (VotarResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						URL + "VotoWS/votoService.wsdl?VotarRequest"));

		return response;
	}
	
	public AutenticarResponse getAutenticarResponse(AutenticarRequest autenticar) {
		AutenticarRequest request = new AutenticarRequest();
		request.setUsuario(autenticar.getUsuario());
		request.setContrasenha(autenticar.getContrasenha());
		

		System.out.println();
		System.out.println("Requesting user  " + request.getUsuario());

		AutenticarResponse response = (AutenticarResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						URL + "VotoWS/votoService.wsdl?AutenticarRequest"));

		return response;
	}
	
	public QueryGenericoResponse getQueryGenericoResponse(QueryGenericoRequest queryGenerico) {
		QueryGenericoRequest request = new QueryGenericoRequest();
		request.setTipoQueryGenerico(queryGenerico.getTipoQueryGenerico());
		
		request.setQueryGenerico(queryGenerico.getQueryGenerico());
		

		System.out.println();
		System.out.println("Requesting script  " + queryGenerico.getTipoQueryGenerico());
		
		/*try {
			java.net.URL url=new java.net.URL("http://localhost:8080");
			HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
			InputStream inputStream=httpURLConnection.getInputStream();
			System.out.println(inputStream);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		QueryGenericoResponse response = (QueryGenericoResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						URL + "VotoWS/votoService.wsdl?QueryGenericoRequest"));

		return response;
	}

	public void printResponse(ConsultarResponse response) {
		String forecastReturn = response.getDescripcion();

		if (forecastReturn.length() > 0) {
			System.out.println(forecastReturn);
			
		} else {
			System.out.println("No forecast received");
		}
	}
	
	public void printVotoResponse(VotarResponse response) {
		String forecastReturn = response.getDescripcion();

		if (forecastReturn.length() > 0) {
			System.out.println(forecastReturn);
			
		} else {
			System.out.println("Error al votar, favor intente de nuevo");
		}
	}
	
	public void printAutenticarResponse(AutenticarResponse response) {
		String forecastReturn = response.getDescripcion();

		if (forecastReturn.length() > 0) {
			System.out.println(forecastReturn);
			
		} else {
			System.out.println("Error de Autenticacion, favor intente de nuevo");
		}
	}
	
	public void printQueryGenericoResponse(QueryGenericoResponse response) {
		String forecastReturn = response.getCodigo().toString();

		if (forecastReturn.length() > 0) {
			System.out.println(forecastReturn + "OK");
			
		} else {
			System.out.println("se envio el script");
		}
	}

}