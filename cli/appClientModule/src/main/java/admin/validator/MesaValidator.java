package src.main.java.admin.validator;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import entity.UcsawsMesa;
import src.main.java.dao.mesa.MesaDAO;



public class MesaValidator {

    public Boolean ValidarCodigo(String codigo, String descripcion,
	    String idEvento, String departamentoSeleccionado)
	    throws ParseException, org.json.simple.parser.ParseException {

	boolean existe = false;

	MesaDAO mesaDAO = new MesaDAO();

	List<UcsawsMesa> mesa = mesaDAO
		.obtenerMesaByIdEvento(Integer.parseInt(idEvento));

	Iterator<UcsawsMesa> ite = mesa.iterator();

	UcsawsMesa aux;
	while (ite.hasNext()) {
	    aux = ite.next();
	    if (aux.getDescMesa().compareToIgnoreCase(descripcion) == 0
		    || aux.getNroMesa() == Integer.parseInt(codigo)) {
		existe = true;
	    }
	}

	return existe;

    }
}
