package src.main.java.admin.vigencia;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class VigenciaJTableModel extends AbstractTableModel {

	List<Object[]> vigencia = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return vigencia.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = vigencia.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID","Item", "Cod. Pais", "Nombre Pais","Vigencia Desde", "Vigencia Hasta"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
