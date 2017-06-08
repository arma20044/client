package src.main.java.admin.votantesHabilitados;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class VotantesHabilitadosJTableModel extends AbstractTableModel {

	List<Object[]> votantes = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 9;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return votantes.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = votantes.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID","Item", "CI","Nombre", "Apellido","Habilitado?", "Sufrago?","Lugar de Votación","Mesa N°"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
