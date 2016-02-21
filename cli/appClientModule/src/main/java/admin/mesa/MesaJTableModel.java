package src.main.java.admin.mesa;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class MesaJTableModel extends AbstractTableModel {

	List<Object[]> ciudades = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return ciudades.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = ciudades.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID", "Item",  "Nro. Mesa", "Desc. Mesa","Nro. Local", "Desc. Local"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
