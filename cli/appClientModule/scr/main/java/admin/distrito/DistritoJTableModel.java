package scr.main.java.admin.distrito;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class DistritoJTableModel extends AbstractTableModel {

	List<Object[]> distrito = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return distrito.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = distrito.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID", "Item", "Nro. Distrito", "Desc. Distrito","Nro. Departamento", "Desc. Departamento"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
