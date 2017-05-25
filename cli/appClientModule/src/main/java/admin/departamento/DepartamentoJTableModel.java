package src.main.java.admin.departamento;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class DepartamentoJTableModel extends AbstractTableModel {

	List<Object[]> departamento = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return departamento.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = departamento.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID", "Item", "Nro. Departamento", "Desc. Departamento"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
