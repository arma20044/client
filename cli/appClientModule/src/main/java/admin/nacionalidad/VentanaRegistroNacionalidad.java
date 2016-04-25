package src.main.java.admin.nacionalidad;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.validator.NacionalidadValidator;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaRegistroNacionalidad extends JFrame implements
		ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;
	private JTable table;

	private NacionalidadJTableModel model = new NacionalidadJTableModel();
	private JScrollPane scrollPane;

	private NacionalidadValidator nacionalidadValidator = new NacionalidadValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JComboBox cmbPais;
	private JLabel lblCod;
	private JTextField txtCod;
	private JTextField txtDescripcion;
	private JLabel lblDescripcion;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroNacionalidad() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtCod.requestFocus();
			}
		});
		

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Registrar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroNacionalidad.class
				.getResource("/imgs/save.png")));
		botonGuardar.setBounds(331, 45, 32, 32);
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
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroNacionalidad.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(774, 383, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		// Image newimg4 = img4.getScaledInstance(32, 32,
		// java.awt.Image.SCALE_SMOOTH);

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE NACIONALIDADES");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(812, 444);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("Lista de Nacionalidades");
		scrollPane.setBounds(0, 190, 806, 193);
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroNacionalidad.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		cmbPais = new JComboBox(recuperarDatosComboBoxPaisActual());
		cmbPais.setBounds(213, 119, 340, 20);
		getContentPane().add(cmbPais);

		JLabel lblPais = new JLabel();
		lblPais.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPais.setText("Pais:");
		lblPais.setBounds(130, 117, 61, 25);
		getContentPane().add(lblPais);

		lblCod = new JLabel();
		lblCod.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCod.setText("Codigo:");
		lblCod.setBounds(130, 52, 61, 25);
		getContentPane().add(lblCod);

		txtCod = new JTextField();
		txtCod.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z'))
					arg0.consume();
			}
		});
		txtCod.setBounds(213, 54, 108, 26);
		getContentPane().add(txtCod);
		txtCod.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(413, 176, 363, 14);
		getContentPane().add(lblMensaje);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(213, 86, 340, 26);
		getContentPane().add(txtDescripcion);

		lblDescripcion = new JLabel();
		lblDescripcion.setText("Descripcion:");
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcion.setBounds(130, 84, 61, 25);
		getContentPane().add(lblDescripcion);

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

				Item item3 = (Item) cmbPais.getSelectedItem();
				Integer paisSelected = item3.getId();
				if (!(txtCod.getText().length() == 0)) {
					if (txtCod.getText().length() > 3) {
						lblMensaje
								.setText("El codigo debe ser de maximo 3 caracteres.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					} else if

					(nacionalidadValidator.ValidarCodigo(txtCod.getText(),
							txtDescripcion.getText()) == false) {
						if (nacionalidadValidator.ValidarPais(paisSelected) == false) {
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
							query.setQueryGenerico("INSERT INTO ucsaws_nacionalidad"
									+ "( id_nacionalidad,id_pais, cod_nacionalidad, desc_nacionalidad , id_evento,usuario_ins,fch_ins, usuario_upd, fch_upd) "
									+ "VALUES ("
									+ "nextval('ucsaws_nacionalidad_seq')"
									+ " , "
									+ paisSelected

									+ ", upper('"
									+ txtCod.getText()
									+ "'), upper('"
									+ txtDescripcion.getText()
									+ "'),"

									+ VentanaBuscarEvento.evento
									+ ",'"
									+ Login.userLogeado
									+ "' , now(), '"
									+ Login.userLogeado + "' , now())");

							QueryGenericoResponse response = weatherClient
									.getQueryGenericoResponse(query);
							weatherClient.printQueryGenericoResponse(response);

							model = new NacionalidadJTableModel();
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

							txtCod.setText("");
							txtDescripcion.setText("");

							// this.dispose();
						} else {
							// JOptionPane.showMessageDialog(null,
							// "Ya existe el genero " + txtDesc.getText(),
							// "Información",JOptionPane.WARNING_MESSAGE);
							lblMensaje
									.setText("Ya existe nacionalidad para el Pais: "
											+ cmbPais.getSelectedItem());
							Timer t = new Timer(Login.timer,
									new ActionListener() {

										public void actionPerformed(
												ActionEvent e) {
											lblMensaje.setText(null);
										}
									});
							t.setRepeats(false);
							t.start();

							txtCod.setText("");
							txtDescripcion.setText("");

							// cmbPais.removeAllItems();

						}
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje
								.setText("Ya existe una Nacionalidad con esos datos.");
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
		if (e.getSource() == botonCancelar) {
			VentanaBuscarNacionalidad nacionalidad = new VentanaBuscarNacionalidad();
			nacionalidad.setVisible(true);
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

		query.setQueryGenerico("select id_nacionalidad, cod_nacionalidad, desc_nacionalidad, nombre"
				+ " from ucsaws_nacionalidad nac join ucsaws_pais pais on (nac.id_pais = pais.id_pais)"
				+ "where nac.id_evento = " + VentanaBuscarEvento.evento);

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
		int contador = 0;
		while (filas.size() > ite) {
			contador = contador + 1;
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), String.valueOf(contador), fil.get(1).toString(),
					fil.get(2).toString(), fil.get(3).toString() };

			model.ciudades.add(fin);
			ite++;
		}

	}

	public Vector recuperarDatosComboBoxPaisActual() {
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

		query.setQueryGenerico("SELECT id_pais, nombre"
				+ " from ucsaws_pais where id_evento = "
				+ VentanaBuscarEvento.evento + "order by nombre");

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
}