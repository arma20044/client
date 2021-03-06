package src.main.java.admin.buscadores;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.TableRowSorter;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.PersonaJTableModel;
import src.main.java.admin.persona.VentanaRegistroPersona;
import src.main.java.admin.votantesHabilitados.VentanaRegistroVotantesHabilitados;
import src.main.java.dao.persona.PersonaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaBuscarPersona extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo;
	private JTextField txtBuscar;
	private JLabel lblBuscar;
	private JButton botonCancelar;

	JSONArray miPersona = null;
	DefaultTableModel modelo;
	private JTable table_1;
	private PersonaJTableModel model = new PersonaJTableModel();
	private JScrollPane scrollPane;

	private String codTemporal = "";
	

	private JLabel lblMensaje;
	
	private DefaultTableModel dm;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de busqueda
	 */
	public VentanaBuscarPersona() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		botonCancelar = new JButton();
		botonCancelar.setIcon(new ImageIcon(VentanaBuscarPersona.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setToolTipText("Atrás");
		botonCancelar.setBounds(1101, 422, 45, 25);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg));
		Image newimg4 = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);

		labelTitulo = new JLabel();
		labelTitulo.setText("BUSCADOR DE PERSONA");
		labelTitulo.setBounds(248, 11, 270, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblBuscar = new JLabel();
		lblBuscar.setText("Buscar:");
		lblBuscar.setBounds(20, 52, 64, 25);
		getContentPane().add(lblBuscar);

		txtBuscar = new JTextField();
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				String query =txtBuscar.getText().toUpperCase(); 
				filter(query);
			}
		});
		txtBuscar.setBounds(86, 52, 319, 26);
		getContentPane().add(txtBuscar);
		botonCancelar.addActionListener(this);

		getContentPane().add(botonCancelar);
		getContentPane().add(labelTitulo);
		limpiar();

		setSize(1152, 476);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("Lista de Personas");
		scrollPane.setBounds(0, 158, 1146, 265);
		getContentPane().add(scrollPane);

		table_1 = new JTable() {  
		      public boolean isCellEditable(int row, int column){  
			        return false;  
			      }  
			};
		table_1.setToolTipText("Listado de Personas.");
		table_1.setAutoCreateRowSorter(true);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table_1);
		// String[] columnNames = {"Picture", "Description"};
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				 
	
				
				List<String> selectedData = new ArrayList<String>();

				//int selectedRow = table_1.rowAtPoint(arg0.getPoint());
				
				
				//Object a = table_1.getModel().getValueAt(table_1.convertRowIndexToView(selectedRow[0]), 0);
				// int[] selectedColumns = table_1.getSelectedColumns();
				//System.out.println(a);

				//if (selectedRow >= 0) {
				int selectedRow = table_1.rowAtPoint(arg0.getPoint());
					System.out.println(selectedRow);
					int col = 0;
					while (col < table_1.getColumnCount()+1) {
						//System.out.println(table_1.getValueAt(selectedRow,
						//		col));
						try {
							int row = table_1.rowAtPoint(arg0.getPoint());
							 String table_click0 = table_1.getModel().getValueAt(table_1.
			                          convertRowIndexToModel(row), col).toString();
			                //System.out.println(table_click0);
			                
							selectedData.add(table_click0);
							System.out.println(selectedData);
							
							
							 
							 //comentar despues
							// int row1 = table_1.rowAtPoint(arg0.getPoint());
							//String table_click01 = table_1.getModel().getValueAt(table_1.
			                  //        convertRowIndexToModel(row1), 0).toString();
			                //System.out.println(table_click01);
			                //comentar despues
			                
						} catch (Exception e) {
							System.out.println(e.getMessage());
							
							
						}

						col++;
					}
					
					if (arg0.getClickCount() == 2) {
					    System.out.println("double clicked");
					    
					   VentanaRegistroVotantesHabilitados.personaSeleccionada = selectedData.get(3) + " " + selectedData.get(4);
					   VentanaRegistroVotantesHabilitados.codTemporal = (selectedData.get(0));
					    
					    VentanaRegistroVotantesHabilitados.lblNombrePersona.setText( VentanaRegistroVotantesHabilitados.personaSeleccionada);
					    
					    
					    dispose();
				 }
				 
				 
				 else{
					// selectedData.ad table_1.getValueAt(selectedRow[i],
					// selectedColumns[0]);
					// txtId.setText(selectedData.get(0));
					txtBuscar.setText(selectedData.get(3)+ " " +selectedData.get(4) );

					// textFecha.setText(selectedData.get(2));
					// textUsu.setText(selectedData.get(4));
					// codTemporal.setText(selectedData.get(1));
					codTemporal = (selectedData.get(0));

				
				System.out.println("Selected: " + selectedData);

			}}
		});
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		recuperarDatos();
		table_1.setModel(dm);
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		JLabel lblListaDeGeneros = new JLabel();
		lblListaDeGeneros.setText("LISTA DE PERSONAS");
		lblListaDeGeneros.setFont(new Font("Verdana", Font.BOLD, 18));
		lblListaDeGeneros.setBounds(147, 117, 325, 30);
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
		btnHome.setIcon(new ImageIcon(VentanaBuscarPersona.class.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img5 = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg5 = img5.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg5));
		getContentPane().add(btnHome);
		

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(57, 88, 432, 14);
		getContentPane().add(lblMensaje);

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
		txtBuscar.setText((String) a.get(1));
		// textFecha.setText((String) a.get(2));
		// textUsu.setText((String) a.get(4));
		codTemporal = a.get(0).toString();

		habilita(true, false, false, false, false, true, false, true, true);
	}

	/**
	 * Permite limpiar los componentes
	 */
	public void limpiar() {
		txtBuscar.setText("");

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
		txtBuscar.setEditable(codigo);
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

		query.setQueryGenerico("select id_persona,ci, per.nombre, per.apellido,  to_char(fecha_nacimiento, 'DD/MM/YYYY'), ori.nombre as PaisOrigen, act.nombre as PaisActual, gen.descripcion,  tel_linea_baja, tel_celular, n.desc_nacionalidad"

				+ " from ucsaws_persona per join ucsaws_pais ori on (per.id_pais_origen = ori.id_pais) join ucsaws_pais act on (per.id_pais_actual = act.id_pais) "
				+ "join ucsaws_genero gen on (per.id_genero = gen.id_genero) join ucsaws_nacionalidad n on (n.id_nacionalidad = per.id_nacionalidad)"
				+ "where per.id_evento = " + VentanaBuscarEvento.evento
				+ " order by per.apellido , per.nombre");

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

		int ite = 0;
		String campo4, campo5 = "";
		int contador = 0;
		while (filas.size() > ite) {
			fil = (JSONArray) filas.get(ite);
			
			contador =  contador + 1 ;

			String[] fin = { fil.get(0).toString(), String.valueOf(contador),fil.get(1).toString(),
					fil.get(2).toString(),fil.get(3).toString(),fil.get(4).toString()
					,fil.get(5).toString(),fil.get(6).toString(),fil.get(7).toString()
					,fil.get(8).toString(),fil.get(9).toString(),fil.get(10).toString()};

			//model.ciudades.add(fin);
			int pos = 0;
			 Vector<Object> vector = new Vector<Object>();
			while(pos < fin.length){
			vector.add(fin[pos]);
			pos++;
			}
			ite++;
			data.add(vector);
		}
		
		  // names of columns
		
		String[] colNames = new String[] {"ID","Item", "CI.", "Nombre", "Apellido","Fch. Nac.", "Pais Origen", "Pais Actual","Genero","Linea Baja","Celular","Nacionalidad"};
				
			    Vector<String> columnNames = new Vector<String>();
			    int columnCount = colNames.length;
			    for (int column = 0; column < columnCount; column++) {
			        columnNames.add(colNames[column]);
			    }
			    
			    dm = new DefaultTableModel(data, columnNames);

	}

	void LimpiarCampos() {
		txtBuscar.setText("");
		// textFecha.setText("");
		// textUsu.setText("");
		codTemporal = "";
		// txtId.setText("");

	}
	
	public void filter(String query){
		
		
		
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
		
		
		
		table_1.setRowSorter(tr);
		
	tr.setRowFilter(RowFilter.regexFilter(query));
		
		
	}
}
