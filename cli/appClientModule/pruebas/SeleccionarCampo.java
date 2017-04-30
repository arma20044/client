package pruebas;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class SeleccionarCampo extends JFrame implements Serializable {
	private static final DecimalFormat formatter = new DecimalFormat( "#,##0.00" );
	DefaultTableModel model;

	JTable table;

	public SeleccionarCampo() {
		final List<Employee> listaEmpleados = new ArrayList<Employee>();

		Date fecha = new Date();
		
		
		Employee employee = new Employee("1", "Armando", fecha, 40000.59);
		Employee employee2 = new Employee("2", "Luis", fecha, 60000.49);

		listaEmpleados.add(employee);
		listaEmpleados.add(employee2);

		Iterator<Employee> ite = listaEmpleados.iterator();
		
		Employee employeeSeleccionado = new Employee();

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
//		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//		    @Override
//		    public void valueChanged(ListSelectionEvent event) {
//		        if (table.getSelectedRow() > -1) {
//		            // print first column value from selected row
//		            System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
//		            
//		            Iterator<Employee> ite = listaEmpleados.iterator();
//		            
//		            
//		    		while (ite.hasNext()) {
//		    			employeeSeleccionado = ite.next();
//		    			
//		    		} 
//		            
//		        }
//		    }
//		});
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		pack();

		model = (DefaultTableModel) table.getModel();

		String header[] = new String[] { "ID", "Nombre", "Fecha Nacimiento", "Salario" };
		model.setColumnIdentifiers(header);

		Employee aux;
		while (ite.hasNext()) {
			aux = ite.next();

			Object[] row = { aux.getEmployeeId(), aux.getEmployeeName() , 
					new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())), 
					formatter.format(aux.getSalario())};
			model.addRow(row);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String arg[]) {
		new SeleccionarCampo().setVisible(true);
	}
}