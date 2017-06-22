package src.main.java.admin.utils;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTable;

import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import org.jdesktop.swingx.JXFindPanel;
import org.jdesktop.swingx.JXFindBar;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;

public class XTabla extends JFrame {

	static int c = 0;
	private static String[] data = { "This is the 1st String", "String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,
		"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,
		"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,
		"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,"String "+ c++,
			"The Final String" };

	private static String[] columns = { "Name", "Length", "Upper-case" };

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					XTabla frame = new XTabla();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public XTabla() {
		
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 671, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
 
		//contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(117, 48, 421, 275);
		//contentPane.add(scrollPane);
		
		
		JXTable table = new JXTable(new SampleTableModel());
		scrollPane.setViewportView(table);
		
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		/*
		 * Agregamos el control de columnas a nuestra tabla:
		 */
		table.setColumnControlVisible(true); // Así es, esto es todo!
		contentPane.setLayout(null);
		//contentPane.setLayout(null);
		
		JXFindBar findBar = new JXFindBar(table.getSearchable());
		findBar.setBounds(117, 10, 421, 33);
		//contentPane.add(findBar);
		
		 
		 
		contentPane.add(findBar);
		contentPane.add(scrollPane);
		
		TableRowFilterSupport.forTable(table).searchable(true).apply();
	}
	
	private static class SampleTableModel extends AbstractTableModel {

		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public String getColumnName(int column) {
			return columns[column];
		}

		public int getRowCount() {
			return data.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			String theData = data[rowIndex];
			Object result = null;
			switch (columnIndex) {
			case 1:
				result = theData.length(); // auto-boxing.
				break;
			case 2:
				result = theData.toUpperCase();
				break;
			default:
				result = theData;
			}

			return result;
		}
	}
}
