package src.main.java.admin.zona;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import scr.main.java.admin.distrito.VentanaBuscarDistrito;
import src.main.java.admin.Coordinador;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.validator.ZonaValidator;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaRegistroZona extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;
	private JTable table;

	private ZonaJTableModel model = new ZonaJTableModel();
	private JScrollPane scrollPane;

	private ZonaValidator zonaValidator = new ZonaValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JLabel lblNroZona;
	private JTextField txtNroZona;
	private JTextField txtDescripcion;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroZona() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtNroZona.requestFocus();
			}
		});
		

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Registrar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroZona.class
				.getResource("/imgs/save.png")));
		botonGuardar.setBounds(298, 52, 32, 32);
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
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroZona.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(774, 383, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE ZONAS");
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
		scrollPane.setToolTipText("Lista de Candidatos");
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroZona.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		lblNroZona = new JLabel();
		lblNroZona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNroZona.setText("Nro:");
		lblNroZona.setBounds(141, 59, 61, 25);
		getContentPane().add(lblNroZona);

		txtNroZona = new JTextField();
		txtNroZona.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				if((car<'0' || car>'9')) e.consume();
			}
		});
		txtNroZona.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtNroZona.getText().length() == 1) {
					txtNroZona.setText(0 + txtNroZona.getText());
				}
			}
		});
		txtNroZona.setBounds(213, 54, 75, 26);
		getContentPane().add(txtNroZona);
		txtNroZona.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(326, 165, 363, 14);
		getContentPane().add(lblMensaje);

		JLabel lblDescripcionZona = new JLabel();
		lblDescripcionZona.setText("Descripcion:");
		lblDescripcionZona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcionZona.setBounds(114, 86, 89, 25);
		getContentPane().add(lblDescripcionZona);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(213, 85, 337, 26);
		getContentPane().add(txtDescripcion);

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

				
				if (!(txtNroZona.getText().length() == 0)) {
					if (txtNroZona.getText().length() > 3) {
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

					(zonaValidator.ValidarCodigo(txtNroZona.getText(),
							txtDescripcion.getText(), VentanaBuscarDistrito.distritoSeleccionado) == false) {
						// if
						// (candidatoValidator.ValidarPersona(personaSelected)
						// == false) {
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
						query.setQueryGenerico("INSERT INTO ucsaws_zona"
								+ "( id_zona, id_evento, desc_zona, nro_zona, id_distrito ,usuario_ins,fch_ins, usuario_upd, fch_upd) "
								+ "VALUES (" + "nextval('ucsaws_zona_seq') , " + VentanaBuscarEvento.evento + ","
								+ " upper('" + txtDescripcion.getText()
								+ "'), '"

								+ txtNroZona.getText() + "' ,'"
								+ VentanaBuscarDistrito.distritoSeleccionado  + "','" + Login.userLogeado
								+ "' , now(), '" + Login.userLogeado
								+ "' , now())");

						QueryGenericoResponse response = weatherClient
								.getQueryGenericoResponse(query);
						weatherClient.printQueryGenericoResponse(response);

						model = new ZonaJTableModel();
						recuperarDatos();
						table.setModel(model);
						model.fireTableDataChanged();
						table.removeColumn(table.getColumnModel().getColumn(0));
						// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
						lblMensaje
								.setText("Excelente, se ha guardado la Zona.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();

						txtNroZona.setText("");
						txtDescripcion.setText("");

						// this.dispose();
						// } else {
						// // JOptionPane.showMessageDialog(null,
						// // "Ya existe el genero " + txtDesc.getText(),
						// // "Información",JOptionPane.WARNING_MESSAGE);
						// lblMensaje
						// .setText("La Persona no puede tener mas de una candidatura");
						// Timer t = new Timer(Login.timer,
						// new ActionListener() {
						//
						// public void actionPerformed(
						// ActionEvent e) {
						// lblMensaje.setText(null);
						// }
						// });
						// t.setRepeats(false);
						// t.start();
						// }
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje.setText("Ya existe la Zona. "
								+ "Verifique que no ingrese un numero o nombre ya existente."
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
			VentanaBuscarZona candidato = new VentanaBuscarZona();
			candidato.setVisible(true);
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

		query.setQueryGenerico("SELECT id_zona, nro_zona,desc_zona,nro_distrito, desc_distrito  "
				+ " from ucsaws_zona zona join ucsaws_distrito dis on (zona.id_distrito = dis.id_distrito)"
				+ " join ucsaws_departamento dep on (dis.id_departamento = dep.id_departamento )"
				+ " where zona.id_evento = " + VentanaBuscarEvento.evento
				+ " and zona.id_distrito = " + VentanaBuscarDistrito.distritoSeleccionado
				+ "order by nro_distrito, nro_zona" + "");


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

			String[] fin = { fil.get(0).toString(), String.valueOf(contador),  fil.get(1).toString(),
					fil.get(2).toString(), fil.get(3).toString(),
					fil.get(4).toString() };

			model.ciudades.add(fin);
			ite++;
		}

	}

	

}