package src.main.java.admin.nacionalidad;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class NacionalidadJTableModel extends AbstractTableModel {

	List<Object[]> nacionalidad = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return nacionalidad.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = nacionalidad.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID", "Item", "Cod. Nacionalidad", "Desc. Nacionalidad","Pais"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
