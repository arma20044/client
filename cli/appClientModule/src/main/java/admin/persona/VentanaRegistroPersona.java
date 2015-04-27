package src.main.java.admin.persona;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.Calendario;
import src.main.java.admin.validator.PersonaValidator;
import src.main.java.dao.persona.PersonaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaRegistroPersona extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar, btnEliminar, btnFecha;
	private JTable table;
	private VentanaRegistroPersona ventanaRegistroPersona;
	private PersonaJTableModel model = new PersonaJTableModel();
	private JScrollPane scrollPane;

	private PersonaValidator personaValidator = new PersonaValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();

	private JFormattedTextField txtFechaNac;

	private JLabel lblPaisActual;
	private JComboBox cmbPaisActual, cmbPaisOrigen;
	private JLabel lblGenero;
	private JComboBox cmbGenero;
	private JLabel lblCI;
	private JTextField txtCI;
	private JLabel lblNombreApellido;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JLabel lblFchNac;
	private JLabel lblCelular;
	private JLabel lblLineaBaja;
	private JTextField txtCelular;
	private JTextField txtLineaBaja;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroPersona() {

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Registrar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroPersona.class
				.getResource("/imgs/save.png")));
		botonGuardar.setBounds(339, 52, 32, 32);
		botonGuardar.setOpaque(false);
		botonGuardar.setContentAreaFilled(false);
		botonGuardar.setBorderPainted(false);
		Image img3 = ((ImageIcon) botonGuardar.getIcon()).getImage();
		Image newimg3 = img3.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonGuardar.setIcon(new ImageIcon(newimg3));

		botonCancelar = new JButton();
		botonCancelar.setBackground(Color.WHITE);
		botonCancelar.setToolTipText("Atrás");
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroPersona.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(1114, 415, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));

		btnEliminar = new JButton();
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setIcon(new ImageIcon(VentanaRegistroPersona.class
				.getResource("/imgs/borrar.png")));
		btnEliminar.setEnabled(true);
		btnEliminar.setBounds(381, 52, 32, 32);
		btnEliminar.setOpaque(false);
		btnEliminar.setContentAreaFilled(false);
		btnEliminar.setBorderPainted(false);
		Image img4 = ((ImageIcon) btnEliminar.getIcon()).getImage();
		Image newimg4 = img4.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnEliminar.setIcon(new ImageIcon(newimg4));

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE PERSONAS\r\n");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		btnEliminar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(btnEliminar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(1152, 476);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("Lista de Personas\r\n");
		scrollPane.setBounds(0, 261, 1146, 157);
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
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				List<String> selectedData = new ArrayList<String>();

				int[] selectedRow = table.getSelectedRows();
				// int[] selectedColumns = table_1.getSelectedColumns();

				for (int i = 0; i < selectedRow.length; i++) {
					int col = 0;
					while (table.getColumnCount() > col) {
						System.out.println(table
								.getValueAt(selectedRow[i], col));
						try {
							selectedData.add((String) table.getValueAt(
									selectedRow[i], col));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}

						col++;
					}
					// selectedData.ad table_1.getValueAt(selectedRow[i],
					// selectedColumns[0]);
					// txtId.setText(selectedData.get(0));
					// txtCod.setText(selectedData.get(0));
					// txtDesc.setText(selectedData.get(1));
					// textFecha.setText(selectedData.get(2));
					// textUsu.setText(selectedData.get(4));
					// codTemporal.setText(selectedData.get(1));
					codTemporal = (String) (table.getModel().getValueAt(
							selectedRow[i], 0));

				}
				System.out.println("Selected: " + selectedData);

			}
		});
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroPersona.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		cmbPaisOrigen = new JComboBox(recuperarDatosComboBoxPaisOrigen());
		cmbPaisOrigen.setBounds(213, 144, 340, 20);
		getContentPane().add(cmbPaisOrigen);

		JLabel lblPaisOrigen = new JLabel();
		lblPaisOrigen.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPaisOrigen.setText("Pais Origen:");
		lblPaisOrigen.setBounds(130, 142, 61, 25);
		getContentPane().add(lblPaisOrigen);

		lblPaisActual = new JLabel();
		lblPaisActual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPaisActual.setText("PaisActual:");
		lblPaisActual.setBounds(101, 175, 90, 25);
		getContentPane().add(lblPaisActual);

		cmbPaisActual = new JComboBox(recuperarDatosComboBoxPaisActual());
		cmbPaisActual.setBounds(213, 177, 340, 20);
		getContentPane().add(cmbPaisActual);

		lblGenero = new JLabel();
		lblGenero.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGenero.setText("Genero:");
		lblGenero.setBounds(130, 208, 61, 25);
		getContentPane().add(lblGenero);

		cmbGenero = new JComboBox(recuperarDatosComboBoxGenero());
		cmbGenero.setBounds(213, 210, 340, 20);
		getContentPane().add(cmbGenero);

		lblCI = new JLabel();
		lblCI.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCI.setText("C.I. N°:");
		lblCI.setBounds(130, 52, 61, 25);
		getContentPane().add(lblCI);

		txtCI = new JTextField();
		txtCI.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtCI.setBounds(213, 54, 108, 20);
		getContentPane().add(txtCI);
		txtCI.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(223, 236, 619, 14);
		getContentPane().add(lblMensaje);

		lblNombreApellido = new JLabel();
		lblNombreApellido.setText("Nombre(s) y Apellido(s):");
		lblNombreApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombreApellido.setBounds(75, 83, 116, 25);
		getContentPane().add(lblNombreApellido);

		txtNombres = new JTextField();
		txtNombres.setColumns(10);
		txtNombres.setBounds(213, 85, 158, 20);
		getContentPane().add(txtNombres);

		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(381, 85, 172, 20);
		getContentPane().add(txtApellidos);

		lblFchNac = new JLabel();
		lblFchNac.setText("Fch. Nac.:");
		lblFchNac.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFchNac.setBounds(75, 113, 116, 25);
		getContentPane().add(lblFchNac);

		lblCelular = new JLabel();
		lblCelular.setText("Tel. Celular:");
		lblCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCelular.setBounds(579, 83, 116, 25);
		getContentPane().add(lblCelular);

		lblLineaBaja = new JLabel();
		lblLineaBaja.setText("Linea Baja:");
		lblLineaBaja.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLineaBaja.setBounds(579, 142, 116, 25);
		getContentPane().add(lblLineaBaja);

		txtCelular = new JTextField();
		txtCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtCelular.setColumns(10);
		txtCelular.setBounds(705, 85, 108, 20);
		getContentPane().add(txtCelular);

		txtLineaBaja = new JTextField();
		txtLineaBaja.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtLineaBaja.setColumns(10);
		txtLineaBaja.setBounds(705, 144, 108, 20);
		getContentPane().add(txtLineaBaja);

		txtFechaNac = new JFormattedTextField();
		txtFechaNac.setText("23/04/2015");
		txtFechaNac.setBounds(213, 115, 69, 20);
		getContentPane().add(txtFechaNac);

		JButton btnFecha = new JButton("");
		btnFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendario cal = new Calendario(ventanaRegistroPersona);
				cal.displayDate();
				if (!Calendario.fechafinalSeleccionada.startsWith("/")) {
					txtFechaNac.setText(Calendario.fechafinalSeleccionada);
				}
			}
		});
		btnFecha.setToolTipText("Calendario");
		btnFecha.setBounds(292, 110, 30, 23);
		btnFecha.setOpaque(false);
		btnFecha.setContentAreaFilled(false);
		btnFecha.setBorderPainted(false);
		Image img5 = ((ImageIcon) botonGuardar.getIcon()).getImage();
		Image newimg5 = img5.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnFecha.setIcon(new ImageIcon(VentanaRegistroPersona.class
				.getResource("/imgs/cal.png")));
		getContentPane().add(btnFecha);

		table.removeColumn(table.getColumnModel().getColumn(0));
		recuperarDatos();

	}

	private void limpiar() {
		codTemporal = "";
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonGuardar) {
			try {
				Item item = (Item) cmbGenero.getSelectedItem();
				Integer generoSelected = item.getId();

				Item item2 = (Item) cmbPaisActual.getSelectedItem();
				Integer actualSelected = item2.getId();

				Item item3 = (Item) cmbPaisOrigen.getSelectedItem();
				Integer origenSelected = item3.getId();
				if (!(txtCI.getText().length() == 0)
						&& !(txtCelular.getText().length() == 0)
						&& !(txtFechaNac.getText().length() == 0)
						&& !(txtLineaBaja.getText().length() == 0)
						&& !(txtNombres.getText().length() == 0)
						&& !(txtApellidos.getText().length() == 0)) {
					if (txtCI.getText().length() > 7) {
						lblMensaje
								.setText("El codigo debe ser de maximo 7 caracteres.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					} else if

					(personaValidator.ValidarCodigo(txtCI.getText()) == false) {
					//	if (personaValidator.ValidarCodigo(txtCI.getText()) == false) {
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
							query.setTipoQueryGenerico(1);
							System.out.println(Login.userLogeado);
							query.setQueryGenerico("INSERT INTO ucsaws_persona"
									+ "( id_persona, nombre, apellido, fecha_nacimiento, id_pais_origen,id_pais_actual,id_genero,ci, tel_linea_baja,tel_celular,"
									+ " usuario_ins,fch_ins, usuario_upd, fch_upd) "
									+ "VALUES ("
									+ "nextval('ucsaws_persona_seq')" + " , "
									+ " upper('"
									+ txtNombres.getText()
									+ "'), "
									+ " upper('"
									+ txtApellidos.getText()
									+ "'), "
									+ " to_date('"
									+ txtFechaNac.getText()
									+ "', 'DD/MM/YYYY'), "

									+ origenSelected
									+ " , "
									+ actualSelected
									+ " , "
									+ generoSelected
									+ ",'"
									+ txtCI.getText()
									+ "', "
									+ "'"
									+ txtLineaBaja.getText()
									+ "',' "
									+ txtCelular.getText()
									+ "', '"
									+ Login.userLogeado
									+ "' , now(), '"
									+ Login.userLogeado + "' , now())");

							QueryGenericoResponse response = weatherClient
									.getQueryGenericoResponse(query);
							weatherClient.printQueryGenericoResponse(response);

							model = new PersonaJTableModel();
							recuperarDatos();
							table.setModel(model);
							model.fireTableDataChanged();
							table.removeColumn(table.getColumnModel()
									.getColumn(0));
							// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
							lblMensaje
									.setText("Excelente, se ha guardado el genero.");
							Timer t = new Timer(Login.timer,
									new ActionListener() {

										public void actionPerformed(
												ActionEvent e) {
											lblMensaje.setText(null);
										}
									});
							t.setRepeats(false);
							t.start();

							txtCI.setText("");
							txtNombres.setText("");
							txtApellidos.setText("");
							txtCelular.setText("");
							txtLineaBaja.setText("");
							DateFormat dateFormat;
							Date date;
							dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							date = new Date();
							   System.out.println(dateFormat.format(date));
							   txtFechaNac.setText(dateFormat.format(date));
							   
							
							// this.dispose();
						 
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje
								.setText("Ya existe una Persona con esta cedula Nro. : "
										+ txtCI.getText());
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					}

				}

				else {
					// JOptionPane.showMessageDialog(null, ,
					// "Información",JOptionPane.WARNING_MESSAGE);
					lblMensaje.setText("Debe ingresar todos los campos.");
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
		if (e.getSource() == btnEliminar) {

			if (!codTemporal.equals("")) {

				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar a la Persona?",
						"Confirmación", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)

				{
					PersonaDAO personaDAO = new PersonaDAO();

					try {
						personaDAO.eliminarPersona(codTemporal);

					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "sfdsfsfsdfs",
								"Información", JOptionPane.WARNING_MESSAGE);
					}
					if (personaDAO.eliminarPersona(codTemporal) == true) {

						// JOptionPane.showMessageDialog(null,"Excelente, se ha eliminado el genero "
						// + txtDesc.getText());
						// modificarGenero(textCod.getText(),
						// codTemporal.getText());
						// txtId.setText("");
						lblMensaje
								.setText("Excelente, se ha eliminado la Persona ");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
						limpiar();

						model = new PersonaJTableModel();

						recuperarDatos();
						table.setModel(model);

						model.fireTableDataChanged();
						table.removeColumn(table.getColumnModel().getColumn(0));
					}

					else {
						// JOptionPane.showMessageDialog(null,"Existen registros que apuntan al Genero que desea eliminar ","Error",JOptionPane.ERROR_MESSAGE);
						lblMensaje
								.setText("ERROR: Existen registros que apuntan a la Persona que desea eliminar ");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					}
				}

			} else {
				// JOptionPane.showMessageDialog(null,
				// "Por favor seleccione que Genero desea Eliminar",
				// "Información",JOptionPane.WARNING_MESSAGE);
				lblMensaje
						.setText("Por favor seleccione que Persona desea Eliminar");
				Timer t = new Timer(Login.timer, new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						lblMensaje.setText(null);
					}
				});
				t.setRepeats(false);
				t.start();
			}

		}
		if (e.getSource() == botonCancelar) {
			VentanaBuscarPersona persona = new VentanaBuscarPersona();
			persona.setVisible(true);
			this.dispose();

		}
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

		query.setQueryGenerico("select ci,id_persona, per.nombre, per.apellido, fecha_nacimiento, ori.nombre as PaisOrigen, act.nombre as PaisActual, gen.descripcion,  tel_linea_baja, tel_celular"

				+ " from ucsaws_persona per join ucsaws_pais ori on (per.id_pais_origen = ori.id_pais) join ucsaws_pais act on (per.id_pais_actual = act.id_pais) "
				+ "join ucsaws_genero gen on (per.id_genero = gen.id_genero)"
				+ "order by per.apellido , per.nombre");

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

			String[] fin = { fil.get(0).toString(), fil.get(1).toString(),
					fil.get(2).toString(), fil.get(3).toString(),
					fil.get(4).toString(), fil.get(5).toString(),
					fil.get(6).toString(), fil.get(7).toString(),
					fil.get(8).toString(), fil.get(9).toString() };

			model.ciudades.add(fin);
			ite++;
		}

	}

	private Vector recuperarDatosComboBoxPaisOrigen() {
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

		query.setQueryGenerico("SELECT id_pais, nombre" + " from ucsaws_pais "
				+ "order by nombre");

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

	private Vector recuperarDatosComboBoxPaisActual() {
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

		query.setQueryGenerico("SELECT id_pais, nombre" + " from ucsaws_pais "
				+ "order by nombre");

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

			tcandidato.add(fin);
			model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
			ite++;
		}
		return model;

	}

	private Vector recuperarDatosComboBoxGenero() {
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

		query.setQueryGenerico("SELECT id_genero, descripcion"
				+ " from ucsaws_genero " + "order by codigo");

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