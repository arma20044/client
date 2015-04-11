package src.main.java.admin.persona;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class CiudadesJTableModel extends AbstractTableModel {

	List<Object[]> ciudades = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 14;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return ciudades.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = ciudades.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {
			"id_persona", "nombre", "apellido", "fecha_nacimiento", "id_pais_origen", 
		       "id_pais_actual", "fch_ins", "fch_upd", "usuario_ins", "usuario_upd", "id_genero", 
		       "ci", "tel_linea_baja", "tel_celular"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
