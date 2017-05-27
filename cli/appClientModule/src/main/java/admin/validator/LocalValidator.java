package src.main.java.admin.validator;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import entity.UcsawsLocal;
import src.main.java.dao.local.LocalDAO;

 
 

public class LocalValidator {

    public Boolean ValidarCodigo(String codigo, String descripcion,
	    String idEvento, String departamentoSeleccionado)
	    throws ParseException, org.json.simple.parser.ParseException {

	boolean existe = false;

	LocalDAO localDAO = new LocalDAO();

	List<UcsawsLocal> local = localDAO
		.obtenerLocalByIdEvento(Integer.parseInt(idEvento));

	Iterator<UcsawsLocal> ite = local.iterator();

	UcsawsLocal aux;
	while (ite.hasNext()) {
	    aux = ite.next();
	    if (aux.getDescLocal().compareToIgnoreCase(descripcion) == 0
		    || aux.getNroLocal().compareToIgnoreCase(codigo) == 0) {
		existe = true;
	    }
	}

	return existe;

    }
}
