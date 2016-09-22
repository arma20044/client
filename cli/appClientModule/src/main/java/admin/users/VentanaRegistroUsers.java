package src.main.java.admin.users;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.sun.org.apache.xerces.internal.impl.dtd.models.CMBinOp;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.buscadores.VentanaBuscarPersonaDesdeUsers;
import src.main.java.admin.departamento.Item;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.validator.UsuariosValidator;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentanaRegistroUsers extends JFrame implements
		ActionListener {
	
	private DefaultTableModel dm2;
	
	private VentanaRegistroUsers a;

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton btnGuardar, botonCancelar;
	private JTable table;

	private UsersJTableModel model = new UsersJTableModel();
	private JScrollPane scrollPane;

	private UsuariosValidator usuariosValidator = new UsuariosValidator();

	public static String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JComboBox comboBox;

	private static String nombrecmb;

	private DefaultComboBoxModel dm, dmz, dml, dmm;
	private JButton btnSeleccionarPersona;

	public static JLabel lblNombrePersona;

	public static String personaSeleccionada = "";
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblUsuario;
	private JLabel lblPass;
	private JTextField txtUser;
	private JPasswordField txtPass;
	private JLabel lblRepetirPass;
	private JPasswordField txtRepPass;
	
	private JComboBox cmbRol;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroUsers() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				btnSeleccionarPersona.requestFocus();
			}
		});

		btnGuardar = new JButton();
		btnGuardar.setToolTipText("Registrar");
		btnGuardar.setIcon(new ImageIcon(
				VentanaRegistroUsers.class
						.getResource("/imgs/save.png")));
		btnGuardar.setBounds(550, 46, 32, 32);
		btnGuardar.setOpaque(false);
		btnGuardar.setContentAreaFilled(false);
		btnGuardar.setBorderPainted(false);
		Image img3 = ((ImageIcon) btnGuardar.getIcon()).getImage();
		Image newimg3 = img3.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnGuardar.setIcon(new ImageIcon(newimg3));

		botonCancelar = new JButton();
		botonCancelar.setBackground(Color.WHITE);
		botonCancelar.setToolTipText("Atrás");
		botonCancelar.setIcon(new ImageIcon(
				VentanaRegistroUsers.class
						.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(774, 429, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));

		labelTitulo = new JLabel();
		labelTitulo.setText("AMB USERS");
		labelTitulo.setBounds(163, 11, 486, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		btnGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(btnGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(812, 490);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("Lista de Votantes Habilitados");
		scrollPane.setBounds(0, 265, 806, 169);
		getContentPane().add(scrollPane);

		table = new JTable() {
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
		table.setToolTipText("");
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				List<String> selectedData = new ArrayList<String>();
//
//				int[] selectedRow = table.getSelectedRows();
//				// int[] selectedColumns = table_1.getSelectedColumns();
//
//				for (int i = 0; i < selectedRow.length; i++) {
//					int col = 0;
//					while (table.getColumnCount() > col) {
//						System.out.println(table
//								.getValueAt(selectedRow[i], col));
//						try {
//							selectedData.add((String) table.getValueAt(
//									selectedRow[i], col));
//						} catch (Exception e) {
//							System.out.println(e.getMessage());
//						}
//
//						col++;
//					}
//					// selectedData.ad table_1.getValueAt(selectedRow[i],
//					// selectedColumns[0]);
//					// txtId.setText(selectedData.get(0));
//					// txtCod.setText(selectedData.get(0));
//					// txtDesc.setText(selectedData.get(1));
//					// textFecha.setText(selectedData.get(2));
//					// textUsu.setText(selectedData.get(4));
//					// codTemporal.setText(selectedData.get(1));
//					codTemporal = (String) (table.getModel().getValueAt(
//							selectedRow[i], 0));
//
//				}
//				System.out.println("Selected: " + selectedData);
//
//			}
//		});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(model);

		btnHome = new JButton("");
		btnHome.setToolTipText("Inicio");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuPrincipal menuprincipal = new MenuPrincipal();
				menuprincipal.setVisible(true);
				dispose();
			}
		});
		btnHome.setIcon(new ImageIcon(VentanaRegistroUsers.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		// cmbDistrito.paint(cmbDistrito.getGraphics());

		JLabel lblPersona = new JLabel();
		lblPersona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPersona.setText("Persona:");
		lblPersona.setBounds(141, 46, 61, 25);
		getContentPane().add(lblPersona);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(433, 240, 363, 14);
		getContentPane().add(lblMensaje);

		btnSeleccionarPersona = new JButton("");
		btnSeleccionarPersona.setIcon(new ImageIcon(VentanaRegistroUsers.class.getResource("/imgs/hojas.png")));
		btnSeleccionarPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaBuscarPersonaDesdeUsers buscar = new VentanaBuscarPersonaDesdeUsers(a, true);
				buscar.setVisible(true);

			}
		});
		btnSeleccionarPersona.setToolTipText("Seleccionar Persona...");
		btnSeleccionarPersona.setBounds(508, 42, 32, 32);
		getContentPane().add(btnSeleccionarPersona);
		
		Border border = LineBorder.createGrayLineBorder();

		lblNombrePersona = new JLabel(personaSeleccionada);
		lblNombrePersona.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				VentanaBuscarPersonaDesdeUsers buscar = new VentanaBuscarPersonaDesdeUsers(a, true);
				buscar.setVisible(true);
			}
		});
		lblNombrePersona.setBounds(209, 46, 289, 25);
		lblNombrePersona.setBorder(border);
		getContentPane().add(lblNombrePersona);


		table.removeColumn(table.getColumnModel().getColumn(0));
		recuperarDatos();
		table.setModel(dm2);
		
		JLabel lblRol = new JLabel();
		lblRol.setText("Rol:");
		lblRol.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRol.setBounds(93, 99, 109, 25);
		getContentPane().add(lblRol);
		
		lblUsuario = new JLabel();
		lblUsuario.setText("Usuario:");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setBounds(93, 135, 109, 25);
		getContentPane().add(lblUsuario);
		
		lblPass = new JLabel();
		lblPass.setText("Contraeña:");
		lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPass.setBounds(93, 171, 109, 25);
		getContentPane().add(lblPass);
		
		txtUser = new JTextField();
		txtUser.setBounds(211, 137, 150, 23);
		getContentPane().add(txtUser);
		txtUser.setColumns(10);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(211, 173, 150, 23);
		getContentPane().add(txtPass);
		
		lblRepetirPass = new JLabel();
		lblRepetirPass.setText("Repetir Contraeña:");
		lblRepetirPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepetirPass.setBounds(93, 207, 109, 25);
		getContentPane().add(lblRepetirPass);
		
		txtRepPass = new JPasswordField();
		txtRepPass.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(!(String.valueOf(txtPass.getPassword()).compareTo(String.valueOf(txtRepPass.getPassword()))==0)){
					lblMensaje
					.setText("Las contraseñas no coinciden. "
							);
			Timer t = new Timer(Login.timer, new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					lblMensaje.setText(null);
				}
			});
			t.setRepeats(false);
			t.start();
					
					txtPass.setText("");
					txtRepPass.setText("");
					
					txtPass.requestFocus();

				}
			}
		});
		txtRepPass.setBounds(211, 207, 150, 23);
		getContentPane().add(txtRepPass);
		
		cmbRol = new JComboBox(recuperarDatosComboBoxRol());
		cmbRol.setSelectedIndex(-1);
		cmbRol.setBounds(212, 101, 217, 20);
		cmbRol.setSelectedIndex(-1);
		getContentPane().add(cmbRol);
		
		
		
		
	}

	private void limpiar() {
		codTemporal = "";
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == botonGuardar) {
//			try {
//				Integer personaSelected =0;
//				//Item item = (Item) cmbPersona.getSelectedItem();
//				if(codTemporal.compareTo("")!=0){
//				personaSelected = Integer.parseInt(codTemporal);
//				}
//				
//
//				 if (lblNombrePersona.getText().length() > 0// && 
//						// cmbDepartamento.getSelectedIndex()!=-1 &&
//						// cmbDistrito.getSelectedIndex()!=-1 &&
//						// cmbZona.getSelectedIndex()!=-1 && 
//						// cmbLocal.getSelectedIndex()!=-1 &&
//					//	 cmbMesa.getSelectedIndex()!=-1) {
//					 
//					 //Item item2 = (Item) cmbDepartamento.getSelectedItem();
//					//	Integer habilitadoSelected = item2.getId();
//
//				if (votantesHabilitadosValidator.ValidarCedula(personaSelected) == false) {
//					// Genero genero = new Genero();
//					// genero.setDescripcion(textGenero.getText());
//
//					Calendar calendar = new GregorianCalendar();
//					int year = calendar.get(Calendar.YEAR);
//
//					ApplicationContext ctx = SpringApplication
//							.run(WeatherConfiguration.class);
//
//					WeatherClient weatherClient = ctx
//							.getBean(WeatherClient.class);
//					QueryGenericoRequest query = new QueryGenericoRequest();
//
//					// para registrar se inserta el codigo es 1
//
//					//Item itemMesa = (Item) cmbMesa.getSelectedItem();
//					//Integer idMesa = itemMesa.getId();
//					//System.out.println(idMesa);
//
//					query.setTipoQueryGenerico(1);
//					System.out.println(Login.userLogeado);
//					query.setQueryGenerico("INSERT INTO ucsaws_votante"
//							+ "( id_votante, id_persona, habilitado,sufrago, id_mesa, id_evento,  usuario_ins,fch_ins, usuario_upd, fch_upd) "
//							+ "VALUES (" + "nextval('ucsaws_votante_seq')"
//							+ " , " + personaSelected + ", 2 ," + "0" + ", "
//							+ idMesa + "," + VentanaBuscarEvento.evento + ", '"
//							+ Login.userLogeado + "' , now(), '"
//							+ Login.userLogeado + "' , now())");
//
//					QueryGenericoResponse response = weatherClient
//							.getQueryGenericoResponse(query);
//					weatherClient.printQueryGenericoResponse(response);
//
//					//VentanaBuscarVotantesHabilitados buscar = new VentanaBuscarVotantesHabilitados();
//					//buscar.setVisible(true);
//					model = new UsersJTableModel();
//					recuperarDatos();
//					table.setModel(model);
//					model.fireTableDataChanged();
//					table.removeColumn(table.getColumnModel().getColumn(0));
//					
//					barrerTodo();
//					// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
//					lblMensaje
//							.setText("Excelente, se ha guardado el Votante.");
//					Timer t = new Timer(Login.timer, new ActionListener() {
//
//						public void actionPerformed(ActionEvent e) {
//							lblMensaje.setText(null);
//						}
//					});
//					t.setRepeats(false);
//					t.start();
//					
//					
//					
//					personaSeleccionada="";
//					//dispose();
//
//					/*
//					 * model = new VotantesHabilitadosJTableModel();
//					 * recuperarDatos(); table.setModel(model);
//					 * model.fireTableDataChanged();
//					 * table.removeColumn(table.getColumnModel().getColumn(0));
//					 * // JOptionPane.showMessageDialog(null,
//					 * "Excelente, se ha guardado el genero.");
//					 * lblMensaje.setText
//					 * ("Excelente, se ha guardado al Votante."); Timer t = new
//					 * Timer(Login.timer, new ActionListener() {
//					 * 
//					 * public void actionPerformed(ActionEvent e) {
//					 * lblMensaje.setText(null); } }); t.setRepeats(false);
//					 * t.start();
//					 */
//
//					// txtCod.setText("");
//
//					// this.dispose();
//				} else {
//					// JOptionPane.showMessageDialog(null,
//					// "Ya existe el genero " + txtDesc.getText(),
//					// "Información",JOptionPane.WARNING_MESSAGE);
//					lblMensaje
//							.setText("La persona ya esta habilitada con esa cedula: "
//									+ personaSelected);
//					Timer t = new Timer(Login.timer, new ActionListener() {
//
//						public void actionPerformed(ActionEvent e) {
//							lblMensaje.setText(null);
//						}
//					});
//					t.setRepeats(false);
//					t.start();
//				}
//
//				}
//				 else{
//					 lblMensaje
//						.setText("Por favor, ingrese todos los campos.");
//				Timer t = new Timer(Login.timer, new ActionListener() {
//
//					public void actionPerformed(ActionEvent e) {
//						lblMensaje.setText(null);
//					}
//				});
//				t.setRepeats(false);
//				t.start();
//				 }
//
//			} catch (Exception ex) {
//				JOptionPane.showMessageDialog(null,
//						"Error al intentar insertar", "Error",
//						JOptionPane.ERROR_MESSAGE);
//			}
//
//		}
//		if (e.getSource() == botonCancelar) {
//			VentanaBuscarUsers votante = new VentanaBuscarUsers();
//			votante.setVisible(true);
//			personaSeleccionada="";
//			this.dispose();
//
//		}}
	

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
		
		
		
		
	

		query.setQueryGenerico("SELECT Id_user, usuario, NOMBRE, APELLIDO, r.descripcion "
				
				 + " FROM ucsaws_users U JOIN ucsaws_persona P ON (U.ID_PERSONA = P.ID_PERSONA) "
				 + " join ucsaws_roles r on (r.id_rol = u.id_rol)"
				 + "   where U.id_evento= " + VentanaBuscarEvento.evento
				 + "  ");

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
			contador = contador + 1;
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(),String.valueOf(contador), fil.get(1).toString(),
					fil.get(2).toString(),fil.get(3).toString(),fil.get(4).toString()
					};

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
		
				String[] colNames = new String[] {"ID","Item", "User","Nombre", "Apellido", "Rol"};
				
			    Vector<String> columnNames = new Vector<String>();
			    int columnCount = colNames.length;
			    for (int column = 0; column < columnCount; column++) {
			        columnNames.add(colNames[column]);
			    }
			    
			    dm2 = new DefaultTableModel(data, columnNames);

	}


	private Vector recuperarDatosComboBoxPersona() {
		Vector model = new Vector();
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

		// query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
		// +
		// "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");

		query.setQueryGenerico("SELECT per.id_persona, nombre || ' ' || apellido "
				+ "from ucsaws_persona per left join ucsaws_votante vot "
				+ "on (per.id_persona = vot.id_persona) "
				+ " where per.id_evento = "
				+ VentanaBuscarEvento.evento
				+ " and per.id_persona not in "
				+ "(select id_persona from ucsaws_votante where sufrago = 0 and (habilitado = 1 or habilitado = 2) ) order by nombre");

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

		int ite = 0;
		String campo4, campo5 = "";
		while (filas.size() > ite) {
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), fil.get(1).toString(), };

			ciudades.add(fin);
			model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
			ite++;
		}
		return model;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnGuardar) {
		try {
			Integer personaSelected =0;
			Item item = null;
			Integer rolSelected = null;
			if (cmbRol.getSelectedIndex()!= -1){
			item = (Item) cmbRol.getSelectedItem();
			 rolSelected = item.getId();
			}
			
			//Item item = (Item) cmbPersona.getSelectedItem();
			if(codTemporal.compareTo("")!=0){
			personaSelected = Integer.parseInt(codTemporal);
			}
			

			 if (lblNombrePersona.getText().length() > 0
					 && String.valueOf(txtPass.getPassword()).length()  >0
					 && txtUser.getText().length() >0
					 &&  String.valueOf(txtRepPass.getPassword()).length() > 0
					 && cmbRol.getSelectedIndex()!=-1
					 
					 ){// && 
					// cmbDepartamento.getSelectedIndex()!=-1 &&
					// cmbDistrito.getSelectedIndex()!=-1 &&
					// cmbZona.getSelectedIndex()!=-1 && 
					// cmbLocal.getSelectedIndex()!=-1 &&
				//	 cmbMesa.getSelectedIndex()!=-1) {
				 
				 //Item item2 = (Item) cmbDepartamento.getSelectedItem();
				//	Integer habilitadoSelected = item2.getId();

			if (usuariosValidator.ValidarUser(txtUser.getText()) == false) {
				if (usuariosValidator.ValidarPersona(Integer.parseInt(codTemporal)) == false) {
				// Genero genero = new Genero();
				// genero.setDescripcion(textGenero.getText());

				Calendar calendar = new GregorianCalendar();
				int year = calendar.get(Calendar.YEAR);

				ApplicationContext ctx = SpringApplication
						.run(WeatherConfiguration.class);

				WeatherClient weatherClient = ctx
						.getBean(WeatherClient.class);
				QueryGenericoRequest query = new QueryGenericoRequest();

				// para registrar se inserta el codigo es 1

				//Item itemMesa = (Item) cmbMesa.getSelectedItem();
				//Integer idMesa = itemMesa.getId();
				//System.out.println(idMesa);
				
				boolean esAdmin = false;
				/*if (rdbtNo.isSelected()){
					esAdmin = false;
				}
				else{
					esAdmin = true;
				}*/
				query.setTipoQueryGenerico(1);
				System.out.println(Login.userLogeado);
				query.setQueryGenerico("INSERT INTO ucsaws_users"
	+ "( id_user, id_persona, id_rol,usuario, pass, id_evento,  usuario_ins,fch_ins, usuario_upd, fch_upd) "
+ "VALUES (" + "nextval('ucsaws_users_seq')" + " , " + codTemporal + ", "+ rolSelected +" ,'" + txtUser.getText() + "', '"
						+ String.valueOf(txtPass.getPassword()) + "'," + VentanaBuscarEvento.evento + ", '"
						+ Login.userLogeado + "' , now(), '"
						+ Login.userLogeado + "' , now())");

				QueryGenericoResponse response = weatherClient
						.getQueryGenericoResponse(query);
				weatherClient.printQueryGenericoResponse(response);

				//VentanaBuscarVotantesHabilitados buscar = new VentanaBuscarVotantesHabilitados();
				//buscar.setVisible(true);
				model = new UsersJTableModel();
				recuperarDatos();
				table.setModel(dm2);
				model.fireTableDataChanged();
				table.removeColumn(table.getColumnModel().getColumn(0));
				
				barrerTodo();
				// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
				lblMensaje
						.setText("Excelente, se ha guardado el Usuario.");
				Timer t = new Timer(Login.timer, new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						lblMensaje.setText(null);
					}
				});
				t.setRepeats(false);
				t.start();
				
				
				
				personaSeleccionada="";
				codTemporal="";
				barrerTodo();
				//rdbtNo.setSelected(true);
				//dispose();

				/*
				 * model = new VotantesHabilitadosJTableModel();
				 * recuperarDatos(); table.setModel(model);
				 * model.fireTableDataChanged();
				 * table.removeColumn(table.getColumnModel().getColumn(0));
				 * // JOptionPane.showMessageDialog(null,
				 * "Excelente, se ha guardado el genero.");
				 * lblMensaje.setText
				 * ("Excelente, se ha guardado al Votante."); Timer t = new
				 * Timer(Login.timer, new ActionListener() {
				 * 
				 * public void actionPerformed(ActionEvent e) {
				 * lblMensaje.setText(null); } }); t.setRepeats(false);
				 * t.start();
				 */

				// txtCod.setText("");

				// this.dispose();
			} else {
				// JOptionPane.showMessageDialog(null,
				// "Ya existe el genero " + txtDesc.getText(),
				// "Información",JOptionPane.WARNING_MESSAGE);
				lblMensaje
						.setText("Esta persona ya cuenta con un usuario. "
								);
				Timer t = new Timer(Login.timer, new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						lblMensaje.setText(null);
					}
				});
				t.setRepeats(false);
				t.start();
			}

			}
			 else{
				 lblMensaje
					.setText("El usuario ya existe.");
			Timer t = new Timer(Login.timer, new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					lblMensaje.setText(null);
				}
			});
			t.setRepeats(false);
			t.start();
			 }
			
			 }else{
				 lblMensaje
					.setText("Por favor, ingrese todos los campos.");
			Timer t = new Timer(Login.timer, new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					lblMensaje.setText(null);
				}
			});
			t.setRepeats(false);
			t.start();
			 }

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Error al intentar insertar", "Error",
					JOptionPane.ERROR_MESSAGE);
		
		}
		}
		if (arg0.getSource() == botonCancelar) {
			personaSeleccionada = "";
			VentanaBuscarUsers user = new VentanaBuscarUsers();
			user.setVisible(true);
			this.dispose();
		}
		
	}
		
		
		void barrerTodo() {
			
			lblNombrePersona.setText("");
			txtPass.setText("");
			txtUser.setText("");
			txtRepPass.setText("");
			
			
			
		}
		
		private Vector recuperarDatosComboBoxRol() {
			Vector model = new Vector();
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

			// query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
			// +
			// "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");

			query.setQueryGenerico("SELECT id_rol, descripcion"
					+ " from ucsaws_roles where id_evento = " + VentanaBuscarEvento.evento + "order by codigo");

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

			int ite = 0;
			String campo4, campo5 = "";
			while (filas.size() > ite) {
				fil = (JSONArray) filas.get(ite);

				String[] fin = { fil.get(0).toString(), fil.get(1).toString(), };

				listas.add(fin);
				model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
				ite++;
			}
			return model;

		}
		
}