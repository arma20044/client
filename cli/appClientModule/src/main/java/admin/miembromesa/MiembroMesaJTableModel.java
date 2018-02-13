package src.main.java.admin.miembromesa;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class MiembroMesaJTableModel extends AbstractTableModel {

	List<Object[]> candidato = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return candidato.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = candidato.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID", "Item",  "Persona", "Tipo", "Lista"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
