package src.main.java.admin.persona;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class PersonaJTableModel extends AbstractTableModel {

	List<Object[]> personas = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 12;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return personas.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = personas.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID","Item", "CI.", "Nombre", "Apellido","Fch. Nac.", "Pais Origen", "Pais Actual","Genero","Linea Baja","Celular","E-Mail"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
