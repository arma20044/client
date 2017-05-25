package src.main.java.admin.zona;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class ZonaJTableModel extends AbstractTableModel {

	List<Object[]> zona = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return zona.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = zona.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID","Item", "Nro. Zona", "Desc. Zona","Nro. Distrito", "Desc. Distrito"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
