package src.main.java.admin.validator;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import entity.UcsawsZona;
import src.main.java.dao.zona.ZonaDAO;

public class ZonaValidator {

    public Boolean ValidarCodigo(String codigo, String descripcion,
	    String idEvento, String distritoSeleccionado) throws ParseException,
	    org.json.simple.parser.ParseException {

	boolean existe = false;

	ZonaDAO zonaDAO = new ZonaDAO();

	List<UcsawsZona> distrito = zonaDAO.obtenerZonaByIdEvento(Integer
		.parseInt(idEvento));

	Iterator<UcsawsZona> ite = distrito.iterator();

	UcsawsZona aux;
	while (ite.hasNext()) {
	    aux = ite.next();
	    if (aux.getDescZona().compareToIgnoreCase(descripcion) == 0
		    || aux.getNroZona().compareToIgnoreCase(codigo) == 0) {
		existe = true;
	    }
	}

	return existe;

    }
}
