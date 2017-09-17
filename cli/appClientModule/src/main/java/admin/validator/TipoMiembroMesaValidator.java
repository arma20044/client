package src.main.java.admin.validator;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import entity.UcsawsTipoMiembroMesa;
import src.main.java.dao.tipoMiembroMesa.TipoMiembroMesaDAO;



 

public class TipoMiembroMesaValidator {



    public Boolean ValidarCodigo( String codigo, String descripcion, String idEvento)
	    throws ParseException, org.json.simple.parser.ParseException {

	boolean existe = false;

	TipoMiembroMesaDAO tipoMiembroMesaDAO = new TipoMiembroMesaDAO();

	List<UcsawsTipoMiembroMesa> tipoMiembroMesa = tipoMiembroMesaDAO
		.obtenerTipoMiembroMesaByIdEvento(Integer.parseInt(idEvento));

	Iterator<UcsawsTipoMiembroMesa> ite = tipoMiembroMesa.iterator();

	UcsawsTipoMiembroMesa aux;
	while (ite.hasNext()) {
	    aux = ite.next();
	    if (aux.getDescripcion().compareToIgnoreCase(descripcion) == 0
		    || aux.getCodigo().compareToIgnoreCase(codigo)==0) {
		existe = true;
	    }
	}

	return existe;

    }
}
