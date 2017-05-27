package src.main.java.admin.local;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class LocalJTableModel extends AbstractTableModel {

	List<Object[]> local = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return local.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = local.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID", "Item", "Nro. Local", "Desc. Local","Nro. Zona", "Desc. Zona"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
