package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsVotante;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.votantesHabilitados.VotantesHabilitadosDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

 

public class VotantesHabilitadosValidator {

	public Boolean ValidarCedula(BigDecimal cedula)
			throws ParseException, org.json.simple.parser.ParseException {

	    boolean existe = false;

	    VotantesHabilitadosDAO votantesHabilitadosDAO = new VotantesHabilitadosDAO();

	    List<UcsawsVotante> votante = votantesHabilitadosDAO.obtenerVotanteByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

	    Iterator<UcsawsVotante> ite = votante.iterator();

	    UcsawsVotante aux;
	    while (ite.hasNext()) {
	        aux = ite.next();
	        if (aux.getIdPersona().getCi().compareTo(cedula)==0) {
	        existe = true;
	        }
	    }

	    return existe;

	    }
	
	
	   public boolean validarUnSoloHabilitadoPorMesaALaVez(UcsawsVotante v){
	        
	       boolean existe = false;

	        VotantesHabilitadosDAO votantesHabilitadosDAO = new VotantesHabilitadosDAO();

	        List<UcsawsVotante> votante = votantesHabilitadosDAO.obtenerVotanteByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

	        Iterator<UcsawsVotante> ite = votante.iterator();

	        UcsawsVotante aux;
	        while (ite.hasNext()) {
	            aux = ite.next();
	            if (aux.getSufrago().compareTo(0)==0  
	                && aux.getHabilitado().compareTo(1)==0 
	                && aux.getUcsawsMesa().getIdMesa() == (v.getUcsawsMesa().getIdMesa()) ) {
	            existe = true;
	            }
	        }

	        return existe;
	        
	     //   query.setQueryGenerico("select id_votante, id_persona from ucsaws_votante where sufrago = 0 and habilitado = 1 and id_mesa = " +
	       // idMesa1);
	        
	   }           
	                
	             
	
}
