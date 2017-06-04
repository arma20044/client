package src.main.java.admin.users;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

public class UsersJTableModel extends AbstractTableModel {

	List<Object[]> user = new ArrayList<Object[]>();
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return user.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Object resultado = user.get(rowIndex) [columnIndex];
		
		return resultado;
	}

	private String[] colNames = new String[] {"ID","Item", "User","Nombre", "Apellido","Rol"};

	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
}
