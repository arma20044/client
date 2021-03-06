package src.main.java.admin.evento;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class EventoJTableModel extends AbstractTableModel {

	List<Object[]> eventos = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return eventos.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = eventos.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID","Item","Nro.", "Desc. Evento","Inicio","Fin","Desc. Tipo Evento"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
