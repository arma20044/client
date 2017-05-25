package src.main.java.admin.validator;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import entity.UcsawsDistrito;
import src.main.java.dao.distrito.DistritoDAO;


 

public class DistritoValidator {

    public Boolean ValidarCodigo(String codigo, String descripcion,
	    String idEvento) throws ParseException,
	    org.json.simple.parser.ParseException {

	boolean existe = false;

	DistritoDAO distritoDAO = new DistritoDAO();

	List<UcsawsDistrito> distrito = distritoDAO
		.obtenerDistritoByIdEvento(Integer.parseInt(idEvento));

	Iterator<UcsawsDistrito> ite = distrito.iterator();

	UcsawsDistrito aux;
	while (ite.hasNext()) {
	    aux = ite.next();
	    if (aux.getDescDistrito().compareTo(descripcion) == 0
		    || aux.getNroDistrito().compareTo(codigo) == 0) {
		existe = true;
	    }
	}

	return existe;

    }
}
