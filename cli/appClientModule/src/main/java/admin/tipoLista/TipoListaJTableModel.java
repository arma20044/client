package src.main.java.admin.tipoLista;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class TipoListaJTableModel extends AbstractTableModel {

	List<Object[]> tipoLista = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return tipoLista.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = tipoLista.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID","Item", "Codigo", "Descripcion"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
