package src.main.java.admin.validator;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import entity.UcsawsDepartamento;
import src.main.java.dao.departamento.DepartamentoDAO;



public class DepartamentoValidator {


	public Boolean ValidarCodigo(String codigo, String descripcion, String idEvento) throws ParseException,
			org.json.simple.parser.ParseException {

		boolean existe = false;

		DepartamentoDAO departamentoDAO = new DepartamentoDAO();
		
		List<UcsawsDepartamento> departamento = departamentoDAO.obtenerDepartamentoByIdEvento(Integer.parseInt(idEvento));
		
		Iterator<UcsawsDepartamento> ite = departamento.iterator();
		
		UcsawsDepartamento aux;
		while (ite.hasNext()) {
			aux = ite.next();
			if(aux.getDescDepartamento().compareToIgnoreCase(descripcion)==0 || aux.getNroDepartamento().compareToIgnoreCase(codigo)==0){
			    existe = true;
			}
		}
		
		return existe;

		

		

	}
}
