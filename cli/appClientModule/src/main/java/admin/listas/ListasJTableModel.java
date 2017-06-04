package src.main.java.admin.listas;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class ListasJTableModel extends AbstractTableModel {

	List<Object[]> listas = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return listas.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = listas.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID","Item", "Nro", "Nombre","Año","Tipo Lista"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
