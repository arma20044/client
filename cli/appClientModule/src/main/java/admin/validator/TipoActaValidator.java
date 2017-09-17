package src.main.java.admin.validator;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import src.main.java.dao.tipoActa.TipoActaDAO;
import entity.UcsawsTipoActa;
 

public class TipoActaValidator {



    public Boolean ValidarCodigo( String codigo, String descripcion, String idEvento)
	    throws ParseException, org.json.simple.parser.ParseException {

	boolean existe = false;

	TipoActaDAO tipoActaDAO = new TipoActaDAO();

	List<UcsawsTipoActa> tipoActa = tipoActaDAO
		.obtenerTipoActaByIdEvento(Integer.parseInt(idEvento));

	Iterator<UcsawsTipoActa> ite = tipoActa.iterator();

	UcsawsTipoActa aux;
	while (ite.hasNext()) {
	    aux = ite.next();
	    if (aux.getDescripcion().compareToIgnoreCase(descripcion) == 0
		    || aux.getCodigoActa().compareToIgnoreCase(codigo)==0) {
		existe = true;
	    }
	}

	return existe;

    }
}
