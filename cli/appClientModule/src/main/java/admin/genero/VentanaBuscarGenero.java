package src.main.java.admin.genero;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.Encabezado;
import src.main.java.admin.utils.Header;
import src.main.java.admin.utils.JPanelOne;
import src.main.java.dao.genero.GeneroDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JComboBox;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaBuscarGenero extends JFrame implements ActionListener {
	
	private JPanelOne panel;
	private JPanel panelMain;

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo;
	private JTextField txtFiltro;
	private JLabel lblBuscar;
	private JButton botonCancelar, botonBuscar, botonEliminar, btnNewButton;
	private JComboBox comboFiltro;

	JSONArray miPersona = null;
	DefaultTableModel modelo;
	private JTable table_1;
	private GeneroJTableModel model = new GeneroJTableModel();
	private JScrollPane scrollPane;

	private String codTemporal = "";

	private JLabel lblMensaje;
	
	private DefaultTableModel dm;
	
	
	
	private TableRowSorter<TableModel> trsFiltro = new TableRowSorter<TableModel>(model);
	
	

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de busqueda
	 */
	public VentanaBuscarGenero() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		botonCancelar = new JButton();
		botonCancelar.setIcon(new ImageIcon(VentanaBuscarGenero.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setToolTipText("Atrás");
		botonCancelar.setBounds(589, 501, 45, 25);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg));

		botonBuscar = new JButton();
		botonBuscar.setToolTipText("Buscar");
		botonBuscar.setIcon(new ImageIcon(VentanaBuscarGenero.class
				.getResource("/imgs/search.png")));
		botonBuscar.setBounds(415, 131, 32, 32);
		botonBuscar.setOpaque(false);
		botonBuscar.setContentAreaFilled(false);
		botonBuscar.setBorderPainted(false);
		Image img3 = ((ImageIcon) botonBuscar.getIcon()).getImage();
		Image newimg3 = img3.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonBuscar.setIcon(new ImageIcon(newimg3));

		botonEliminar = new JButton();
		botonEliminar.setToolTipText("Eliminar");
		botonEliminar.setIcon(new ImageIcon(VentanaBuscarGenero.class
				.getResource("/imgs/borrar.png")));
		botonEliminar.setBounds(499, 131, 32, 32);
		botonEliminar.setOpaque(false);
		botonEliminar.setContentAreaFilled(false);
		botonEliminar.setBorderPainted(false);
		Image img4 = ((ImageIcon) botonEliminar.getIcon()).getImage();
		Image newimg4 = img4.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonEliminar.setIcon(new ImageIcon(newimg4));
		
		/*
		 * 
		 * nuevo
		 */
		
		  // creating main JPanel (white)
        panelMain = new JPanel();
        panelMain.setBackground(Color.WHITE);
        panelMain.setBounds(34, 0, 619, 85);
        //panelMain.setPreferredSize(new Dimension(619, 85));
        getContentPane().add(panelMain);

		   // creating new JPanelOne object from JPanelOne class containing black JPanel
        panel = new JPanelOne();

        // adding black JPanel to main JPanel (white)
        panelMain.add(panel);
        
			
        
			
		
		/*
		 * nuevo
		 */
		
		labelTitulo = new JLabel();
		labelTitulo.setText("ABM DE GENERO");
		labelTitulo.setBounds(175, 96, 270, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblBuscar = new JLabel();
		lblBuscar.setText("Buscar:");
		lblBuscar.setBounds(20, 131, 64, 25);
		getContentPane().add(lblBuscar);

		txtFiltro = new JTextField();
		txtFiltro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				String query =txtFiltro.getText().toUpperCase(); 
				filter(query);
			}
		});
		
		
		txtFiltro.setBounds(86, 131, 319, 26);
		getContentPane().add(txtFiltro);
		botonEliminar.addActionListener(this);
		botonBuscar.addActionListener(this);
		botonCancelar.addActionListener(this);

		getContentPane().add(botonCancelar);
		getContentPane().add(botonBuscar);
		getContentPane().add(botonEliminar);
		getContentPane().add(labelTitulo);
		limpiar();

		setSize(640, 554);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("Lista de Candidatos");
		scrollPane.setBounds(0, 237, 634, 265);
		getContentPane().add(scrollPane);

		table_1 = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				Component component = super.prepareRenderer(renderer, row,
						column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(Math.max(rendererWidth
						+ getIntercellSpacing().width,
						tableColumn.getPreferredWidth()));
				return component;
			}
		};
		table_1.setToolTipText("Listado de Generos.");
		table_1.setAutoCreateRowSorter(true);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table_1);
		// String[] columnNames = {"Picture", "Description"};
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				List<String> selectedData = new ArrayList<String>();

				int[] selectedRow = table_1.getSelectedRows();
				// int[] selectedColumns = table_1.getSelectedColumns();

				for (int i = 0; i < selectedRow.length; i++) {
					int col = 0;
					while (table_1.getColumnCount() > col) {
						System.out.println(table_1.getValueAt(selectedRow[i],
								col));
						try {
							selectedData.add((String) table_1.getValueAt(
									selectedRow[i], col));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}

						col++;
					}
					// selectedData.ad table_1.getValueAt(selectedRow[i],
					// selectedColumns[0]);
					// txtId.setText(selectedData.get(0));
					txtFiltro.setText(selectedData.get(1));

					// textFecha.setText(selectedData.get(2));
					// textUsu.setText(selectedData.get(4));
					// codTemporal.setText(selectedData.get(1));
					codTemporal = (String) (table_1.getModel().getValueAt(
							selectedRow[i], 0));

				}
				System.out.println("Selected: " + selectedData);

			}
		});
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		recuperarDatos();
		table_1.setModel(dm);
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		JLabel lblListaDeGeneros = new JLabel();
		lblListaDeGeneros.setText("LISTA DE GENEROS");
		lblListaDeGeneros.setFont(new Font("Verdana", Font.BOLD, 18));
		lblListaDeGeneros.setBounds(147, 196, 325, 30);
		getContentPane().add(lblListaDeGeneros);

		JButton btnHome = new JButton("");
		btnHome.setToolTipText("Inicio");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuPrincipal menuprincipal = new MenuPrincipal();
				menuprincipal.setVisible(true);
				dispose();
			}
		});
		btnHome.setIcon(new ImageIcon(VentanaBuscarGenero.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img5 = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg5 = img5.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg5));
		getContentPane().add(btnHome);

		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaRegistroGenero registro = new VentanaRegistroGenero();
				registro.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setToolTipText("Nuevo");
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(new ImageIcon(VentanaBuscarGenero.class
				.getResource("/imgs/add.png")));
		btnNewButton.setBounds(457, 131, 32, 32);
		Image img2 = ((ImageIcon) btnNewButton.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnNewButton.setIcon(new ImageIcon(newimg2));
		getContentPane().add(btnNewButton);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(79, 188, 432, 14);
		getContentPane().add(lblMensaje);
		
		String[] petStrings = { "Codigo", "Descripcion" };
		comboFiltro = new JComboBox(petStrings);
		comboFiltro.setBounds(133, 168, 110, 20);
		getContentPane().add(comboFiltro);
		

        

		// table_1.getColumnModel().getColumn(0).setHeaderValue("Descripcion");

		ListSelectionModel cellSelectionModel = table_1.getSelectionModel();
		recuperarDatos();
		cellSelectionModel
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		// cellSelectionModel.addListSelectionListener(new
		// ListSelectionListener() {
		// public void valueChanged(ListSelectionEvent e) {
		// String selectedData = null;
		//
		// int[] selectedRow = table_1.getSelectedRows();
		// int[] selectedColumns = table_1.getSelectedColumns();
		//
		// for (int i = 0; i < selectedRow.length; i++) {
		// for (int j = 0; j < selectedColumns.length; j++) {
		// selectedData = (String) table_1.getValueAt(selectedRow[i],
		// selectedColumns[j]);
		// }
		// }
		// System.out.println("Selected: " + selectedData);
		// }
		//
		// });

		DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm:ss");

	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == botonBuscar) {
			String ge = txtFiltro.getText();

			GeneroDAO generoDAO = new GeneroDAO();

			if (!(txtFiltro.getText().length() == 0)) {

				try {
					miPersona = generoDAO.buscarGenero(txtFiltro.getText());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (org.json.simple.parser.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (miPersona.size() > 0) {

					muestraPersona(miPersona);

					try {
						// VentanaVer ver = new VentanaVer(miPersona);
						// ver.setVisible(true);
					} catch (Exception e2) {
						// TODO: handle exception
						System.out.print(e2);
					}

					modelo = new DefaultTableModel();
					JSONArray a = (JSONArray) miPersona.get(0);

					model = new GeneroJTableModel();
					recuperarDatos();
					table_1.setModel(dm);
					table_1.removeColumn(table_1.getColumnModel().getColumn(0));
					model.fireTableDataChanged();
				}
				//

			} else {
				lblMensaje
						.setText("Por favor complete los campos para realizar la busqueda");
				codTemporal = "";
				txtFiltro.setText("");

				Timer t = new Timer(Login.timer, new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						lblMensaje.setText(null);
					}
				});
				t.setRepeats(false);
				t.start();
			}
		}

		if (e.getSource() == botonEliminar) {
			if (!codTemporal.equals("")) {
				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar el Genero?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION) {
					GeneroDAO generoDAO = new GeneroDAO();

					try {
						generoDAO.eliminarGenero(codTemporal);

					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "sfdsfsfsdfs",
								"Información", JOptionPane.WARNING_MESSAGE);
					}

					lblMensaje.setText("Excelente, se ha eliminado el Genero");
					codTemporal = "";
					txtFiltro.setText("");

					Timer t = new Timer(Login.timer, new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							lblMensaje.setText(null);
						}
					});
					t.setRepeats(false);
					t.start();
					// modificarGenero(textCod.getText(),
					// codTemporal.getText());
					codTemporal = "";
					limpiar();

					model = new GeneroJTableModel();

					recuperarDatos();
					table_1.setModel(dm);
					table_1.removeColumn(table_1.getColumnModel().getColumn(0));
					// model.fireTableDataChanged();
					// table_1.repaint();
				}
			} else {
				lblMensaje
						.setText("Por favor seleccione que Genero desea Eliminar");

				Timer t = new Timer(Login.timer, new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						lblMensaje.setText(null);
					}
				});
				t.setRepeats(false);
				t.start();

				// JOptionPane.showMessageDialog(null,
				// "Por favor seleccione que Genero desea Eliminar",
				// "Información",JOptionPane.WARNING_MESSAGE);
			}

		}
		if (e.getSource() == botonCancelar) {
			DefinicionesGenerales definiciones = new DefinicionesGenerales();
			definiciones.setVisible(true);
			this.dispose();
		}

	}

	/**
	 * permite cargar los datos de la persona consultada
	 * 
	 * @param miPersona
	 */
	private void muestraPersona(JSONArray genero) {
		JSONArray a = (JSONArray) genero.get(0);
		// txtId.setText(Long.toString( (Long) a.get(0)) );
		txtFiltro.setText((String) a.get(1));
		// textFecha.setText((String) a.get(2));
		// textUsu.setText((String) a.get(4));
		codTemporal = a.get(0).toString();

		habilita(true, false, false, false, false, true, false, true, true);
	}

	/**
	 * Permite limpiar los componentes
	 */
	public void limpiar() {
		txtFiltro.setText("");

		// codTemporal.setText("");
		habilita(true, false, false, false, false, true, false, true, true);
	}

	/**
	 * Permite habilitar los componentes para establecer una modificacion
	 * 
	 * @param codigo
	 * @param nombre
	 * @param edad
	 * @param tel
	 * @param profesion
	 * @param cargo
	 * @param bBuscar
	 * @param bGuardar
	 * @param bModificar
	 * @param bEliminar
	 */
	public void habilita(boolean codigo, boolean nombre, boolean edad,
			boolean tel, boolean profesion, boolean bBuscar, boolean bGuardar,
			boolean bModificar, boolean bEliminar) {
		txtFiltro.setEditable(codigo);
		botonBuscar.setEnabled(bBuscar);
		// botonModificar.setEnabled(true);
		botonEliminar.setEnabled(bEliminar);
	}



	private void recuperarDatos() {
		JSONArray filas = new JSONArray();
		JSONArray fil = new JSONArray();

		boolean existe = false;

		// Statement estatuto = conex.getConnection().createStatement();

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		// para registrar se inserta el codigo es 1
		query.setTipoQueryGenerico(2);

		query.setQueryGenerico("SELECT  id_genero, codigo, descripcion "
				+ "from  ucsaws_genero where id_evento = " + VentanaBuscarEvento.evento 
				+ "order by codigo" + "");

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();

		if (res.compareTo("ERRORRRRRRR") == 0) {
			JOptionPane.showMessageDialog(null, "algo salio mal",
					"Advertencia", JOptionPane.WARNING_MESSAGE);

		}

		else {
			existe = true;

			String generoAntesPartir = response.getQueryGenericoResponse();

			JSONParser j = new JSONParser();
			Object ob = null;
			String part1, part2, part3;

			try {
				ob = j.parse(generoAntesPartir);
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			filas = (JSONArray) ob;

		}
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		//Vector<Object> vector = new Vector<Object>();
		

		int ite = 0;
		String campo4, campo5 = "";
		int contador = 0;
		while (filas.size() > ite) {
			contador = contador + 1;
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), String.valueOf(contador),fil.get(1).toString(),
					fil.get(2).toString()};

			//model.ciudades.add(fin);
			int pos = 0;
			 Vector<Object> vector = new Vector<Object>();
			while(pos < 4){
			vector.add(fin[pos]);
			pos++;
			}
			ite++;
			data.add(vector);
		}
		 
		
		
		
		  // names of columns
		
		String[] colNames = new String[] {"ID", "Item",  "Codigo", "Descripcion"};
		
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = colNames.length;
	    for (int column = 0; column < columnCount; column++) {
	        columnNames.add(colNames[column]);
	    }
	    
	    dm = new DefaultTableModel(data, columnNames);

	}

	void LimpiarCampos() {
		txtFiltro.setText("");
		// textFecha.setText("");
		// textUsu.setText("");
		codTemporal = "";
		// txtId.setText("");

	}
	
	public void filtro() {
        int columnaABuscar = 0;
        if (comboFiltro.getSelectedItem() == "Codigo") {
            columnaABuscar = 0;
        }
        if (comboFiltro.getSelectedItem().toString() == "Descripcion") {
            columnaABuscar = 1;
        }
       try{
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtFiltro.getText(), columnaABuscar));
       }
       catch(Exception e){
    	   System.out.println(e);
       }
       
        
        //table_1.set   setRowSorter(RowFilter.regexFilter(txtFiltro.getText(), columnaABuscar));
    }
	
	
	public void filter(String query){
		
		
		
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
		
		
		
		table_1.setRowSorter(tr);
		
	tr.setRowFilter(RowFilter.regexFilter(query));
		
		
	}
//	
//	private void createColumns(){
//	dm=(DefaultTableModel) table_1.getModel();
//	
//	
//	}
	
	
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}
	
	
	
	
}
