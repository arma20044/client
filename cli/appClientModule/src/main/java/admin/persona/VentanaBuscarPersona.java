package src.main.java.admin.persona;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.Persona;
import entity.UcsawsEvento;
import entity.UcsawsPersona;
import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.evento.VentanaModificarEvento;
import src.main.java.dao.persona.PersonaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import src.main.java.proceso.voto.VentanaPresidente;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JPanel;

public class VentanaBuscarPersona extends JFrame implements ActionListener {
    
    private static final DecimalFormat formatter = new DecimalFormat( "###,###,###" );
    
    
 

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
	//private JTable table_1;									// coordinador
	private JLabel labelTitulo;
	private JTextField txtBuscar;
	private JLabel lblBuscar;
	private JButton botonCancelar, btnEliminar, btnNuevo;

	JSONArray miPersona = null;
	DefaultTableModel modelo;
	private PersonaJTableModel model = new PersonaJTableModel();

	private String codTemporal = "";

	private JLabel lblMensaje;

	private DefaultTableModel dm;
	private JButton btnModificar;
	
	private Persona persona;
	private JButton btnImportar;
	private VentanaBuscarPersona ventanaBuscarPersona;
	
	private JTable table_1;
	
	private JScrollPane scrollPane;
	
	

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de busqueda
	 */
	public VentanaBuscarPersona() {
//		addWindowFocusListener(new WindowFocusListener() {
//			public void windowGainedFocus(WindowEvent arg0) {
//				//model = new PersonaJTableModel();
//
//				recuperarDatos();
//				table_1.setModel(model);
//				table_1.removeColumn(table_1.getColumnModel()
//						.getColumn(0));
//			}
//			public void windowLostFocus(WindowEvent arg0) {
//			}
//		});
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				table_1.repaint();
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				txtBuscar.requestFocus();
			}
		});

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

		btnEliminar = new JButton();
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setIcon(new ImageIcon(VentanaBuscarPersona.class
				.getResource("/imgs/borrar.png")));
		btnEliminar.setBounds(468, 52, 32, 32);
		btnEliminar.setOpaque(false);
		btnEliminar.setContentAreaFilled(false);
		btnEliminar.setBorderPainted(false);
		Image img4 = ((ImageIcon) btnEliminar.getIcon()).getImage();
		Image newimg4 = img4.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnEliminar.setIcon(new ImageIcon(newimg4));

		labelTitulo = new JLabel();
		labelTitulo.setText("VER PERSONA");
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

				String query = txtBuscar.getText().toUpperCase();
				filter(query);
			}
		});
		txtBuscar.setBounds(86, 52, 319, 26);
		getContentPane().add(txtBuscar);
		btnEliminar.addActionListener(this);
		botonCancelar.addActionListener(this);

		getContentPane().add(botonCancelar);
		getContentPane().add(btnEliminar);
		getContentPane().add(labelTitulo);
		limpiar();

		setSize(1152, 476);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		//resizeColumnWidth(table_1);
		//table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		
		
		JLabel lblListaDeGeneros = new JLabel();
		lblListaDeGeneros.setText("LISTA DE PERSONAS");
		lblListaDeGeneros.setFont(new Font("Verdana", Font.BOLD, 18));
		lblListaDeGeneros.setBounds(147, 117, 325, 30);
		getContentPane().add(lblListaDeGeneros);

		JButton btnHome = new JButton("");
		btnHome.setToolTipText("Inicio");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefinicionesGenerales menuprincipal = new DefinicionesGenerales();
				menuprincipal.setVisible(true);
				dispose();
			}
		});
		btnHome.setIcon(new ImageIcon(VentanaBuscarPersona.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img5 = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg5 = img5.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg5));
		getContentPane().add(btnHome);

		btnNuevo = new JButton("");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaRegistroPersona registro = new VentanaRegistroPersona();
				registro.setVisible(true);
				dispose();
			}
		});
		btnNuevo.setToolTipText("Nuevo");
		btnNuevo.setOpaque(false);
		btnNuevo.setContentAreaFilled(false);
		btnNuevo.setBorderPainted(false);
		btnNuevo.setIcon(new ImageIcon(VentanaBuscarPersona.class
				.getResource("/imgs/add.png")));
		btnNuevo.setBounds(426, 52, 32, 32);
		Image img2 = ((ImageIcon) btnNuevo.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnNuevo.setIcon(new ImageIcon(newimg2));
		getContentPane().add(btnNuevo);
		
		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("Lista de Personas\r\n");
		scrollPane.setBounds(0, 158, 1146, 261);
		getContentPane().add(scrollPane);
		
		   
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"clickButtonescape");

		getRootPane().getActionMap().put("clickButtonescape",new AbstractAction(){
			        public void actionPerformed(ActionEvent ae)
			        {
			    botonCancelar.doClick();
			    System.out.println("button esc clicked");
			        }
			    });
		
		 
	
		
		
		table_1 = new JTable() {  
		      public boolean isCellEditable(int row, int column){  
			        return false;  
			      }  
			};
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			    Integer row = table_1.rowAtPoint(arg0.getPoint());
			    
			    
			    List<String> selectedData = new ArrayList<String>();
			    
			    int col = 0;
				while (col < table_1.getColumnCount()+1) {
			    String table_click0 = table_1.getModel().getValueAt(table_1.
		                          convertRowIndexToModel(row), col).toString();
			    selectedData.add(table_click0);
			    
				col++;
				}
			    codTemporal = selectedData.get(0);
			}
		});
			table_1.setToolTipText("");
			table_1.getTableHeader().setReorderingAllowed(false);

			table_1.setAutoCreateRowSorter(false);
			
			//getContentPane().add(table_1);
			
			scrollPane.setViewportView(table_1);
			
		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(57, 88, 432, 14);
		getContentPane().add(lblMensaje);

		btnModificar = new JButton();
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!codTemporal.equals("")) {
				    PersonaDAO personaDAO = new PersonaDAO();
				    UcsawsPersona personaAModificar = personaDAO.obtenerPersonaByIdPersona(codTemporal);

					VentanaModificarPersona modificar = new VentanaModificarPersona(personaAModificar);
					modificar.setVisible(true);
					dispose();
				} else {
					lblMensaje
							.setText("Por favor seleccione que Persona desea Modificar.");

					Timer t = new Timer(Login.timer, new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							lblMensaje.setText(null);
						}
					});
					t.setRepeats(false);
					t.start();
				}

			}
		});
		btnModificar.setIcon(new ImageIcon(VentanaBuscarPersona.class
				.getResource("/imgs/def.png")));
		btnModificar.setToolTipText("Modificar");
		btnModificar.setOpaque(false);
		btnModificar.setEnabled(true);
		btnModificar.setContentAreaFilled(false);
		btnModificar.setBorderPainted(false);
		btnModificar.setBounds(510, 53, 32, 32);

		Image img6 = ((ImageIcon) btnModificar.getIcon()).getImage();
		Image newimg6 = img6.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnModificar.setIcon(new ImageIcon(newimg6));

		getContentPane().add(btnModificar);
		
		btnImportar = new JButton();
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImportarPersona  persona = new ImportarPersona();
				persona.setVisible(true);
				dispose();
			}
		});
		
		
		
		
		btnImportar.setIcon(new ImageIcon(VentanaBuscarPersona.class.getResource("/imgs/caja.png")));
		btnImportar.setToolTipText("Importar XLS");
		btnImportar.setOpaque(false);
		btnImportar.setEnabled(true);
		btnImportar.setContentAreaFilled(false);
		btnImportar.setBorderPainted(false);
		btnImportar.setBounds(548, 52, 32, 32);
		Image img7 = ((ImageIcon) btnImportar.getIcon()).getImage();
		Image newimg7 = img7.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnImportar.setIcon(new ImageIcon(newimg7));
		
		
		getContentPane().add(btnImportar);
		

		
		//table_1 = new JTable();
		//scrollPane.setViewportView(table_1);
		
		//table_1 = new JTable();
		
		
	
		recuperarDatos();
		table_1.setModel(model);
		
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		//cellSelectionModel
		//		.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

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
		
		if(VentanaBuscarEvento.readOnly==true){
			btnNuevo.setEnabled(false);
			btnNuevo.setToolTipText("Ya No se puede cargar datos durante ni despues la votacion");
			btnEliminar.setEnabled(false);
			btnEliminar.setToolTipText("Ya No se puede eliminar datos durante ni despues la votacion");
			btnModificar.setEnabled(false);
			btnModificar.setToolTipText("Ya No se puede Modificar datos durante ni despues la votacion");
			btnImportar.setEnabled(false);
			btnImportar.setToolTipText("Ya No se puede Importar datos durante ni despues la votacion");
		}

	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}
	


	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnEliminar) {
			if (!codTemporal.equals("")) {
				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar la Persona?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION) {
					PersonaDAO personaDAO = new PersonaDAO();

					try {
						if (personaDAO.eliminarPersona(codTemporal) == false) {
							JOptionPane.showMessageDialog(null,
									"Error al intentar Borrar la Persona",
									"Error", JOptionPane.ERROR_MESSAGE);
						}

						else {
//							JOptionPane.showMessageDialog(null,
//									"Excelente, se ha eliminado la Persona ",
//									"Información",
//									JOptionPane.INFORMATION_MESSAGE);
							
							lblMensaje
							.setText("Se ha eliminado la persona.");

					Timer t = new Timer(Login.timer, new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							lblMensaje.setText(null);
						}
					});
							
							
							// modificarGenero(textCod.getText(),
							// codTemporal.getText());
							codTemporal = "";
							limpiar();

							model = new PersonaJTableModel();

							recuperarDatos();
							table_1.setModel(model);
							table_1.removeColumn(table_1.getColumnModel().getColumn(0));
						}

					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "sfdsfsfsdfs",
								"Información", JOptionPane.WARNING_MESSAGE);
					}

				}
			} else {
				lblMensaje
						.setText("Por favor seleccione que Persona desea Eliminar");

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
		// botonModificar.setEnabled(true);
		btnEliminar.setEnabled(bEliminar);
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

		
		query.setTipoQueryGenerico(44);

		query.setQueryGenerico(VentanaBuscarEvento.evento);

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();
		
		// json string to List<String>;
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();
		List<UcsawsPersona> lista = new ArrayList<UcsawsPersona>();
		try{
		lista = mapper.readValue(jsonInString,new TypeReference<List<UcsawsPersona>>(){});
		}
		catch(Exception e){
			System.out.println(e);
		}

		if (lista.isEmpty()) {
			//JOptionPane.showMessageDialog(null, "algo salio mal",
			//		"Advertencia", JOptionPane.WARNING_MESSAGE);
			//return lista;
		}

		else {
			obtenerModeloA(table_1,lista );
			
	    // return lista;
		}



	}

	void LimpiarCampos() {
		txtBuscar.setText("");
		// textFecha.setText("");
		// textUsu.setText("");
		codTemporal = "";
		// txtId.setText("");

	}

	public void filter(String query) {

		//TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);

		//tr.setRowFilter(RowFilter.regexFilter(query));
	    
	    //table_1.setModel(new KeywordDataModel());
	    TableRowSorter sorter = new TableRowSorter(table_1.getModel());
	    sorter.setRowFilter(RowFilter.regexFilter(query));
	    table_1.setRowSorter(sorter);

	}
	
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    
	public AbstractTableModel obtenerModeloA(JTable tabla, List<UcsawsPersona> evento){
		
		 
		Iterator<UcsawsPersona> ite = evento.iterator();

		//private String[] colNames = new String[] 
		//{"ID","Item", "CI.", "Nombre", "Apellido","Fch. Nac.", "Pais Origen", "Pais Actual","Genero","Linea Baja","Celular","E-Mail"}; 

		UcsawsPersona aux;
		Integer cont = 1;
		while (ite.hasNext()) {
			aux = ite.next();

			Object[] row = { aux.getIdPersona(), cont , 
				formatter.format(aux.getCi()) , 
				aux.getNombre(), aux.getApellido(), 
			new SimpleDateFormat("dd-MM-yyyy").format((aux.getFechaNacimiento())),aux.getUcsawsPaisByIdPaisOrigen().getNombre(),
			aux.getUcsawsPaisByIdPaisActual().getNombre(),aux.getUcsawsGenero().getDescripcion() ,aux.getTelLineaBaja(), aux.getTelCelular(), aux.getEmail() };
					


					//new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
					
					
			model.personas.add(row);
			cont++;
		
		
		


		
	}
		return model;
	}
}
