package src.main.java.admin.validator;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.jdesktop.swingx.JXErrorPane;
import org.jfree.date.DateUtilities;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsVigenciaHorarioXPais;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.vigencia.VigenciaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class VigenciaValidator {

  public Boolean ValidarCodigo(Integer idPais) {


    boolean existe = false;
    
    try{

    VigenciaDAO vigenciaDAO = new VigenciaDAO();

    List<UcsawsVigenciaHorarioXPais> listas = vigenciaDAO.obtenerVigenciaByEvento(VentanaBuscarEvento.eventoClase);

    Iterator<UcsawsVigenciaHorarioXPais> ite = listas.iterator();

    UcsawsVigenciaHorarioXPais aux;
    while (ite.hasNext()) {
      aux = ite.next();
      if (aux.getIdPais().getIdPais() == idPais) {
        existe = true;
      }
    }
    }
    catch(Exception e){
      JXErrorPane.showDialog(e);
    }

    return existe;

  }


  public Boolean ValidarRango(String fechaDesde, String fechaHasta) throws ParseException,
      org.json.simple.parser.ParseException, NameNotFoundException {

    boolean existe = false;

    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
    DateTime desde = formatter.parseDateTime(fechaDesde);
    
    
    DateTime hasta = formatter.parseDateTime(fechaHasta);
    
    
    


    if (hasta.isBefore(desde)) {
      throw new NameNotFoundException("La fecha Hasta no puede ser inferior a la fecha Desde.");
      
    }

    else if (desde.isAfter(hasta)) {
      // System.out.println("Fuera de rango");
      // existe = true;
    }



    return existe;



  }
}
