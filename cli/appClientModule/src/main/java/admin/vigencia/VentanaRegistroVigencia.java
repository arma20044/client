package src.main.java.admin.vigencia;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import com.lowagie.toolbox.plugins.Txt2Pdf;

import src.main.java.admin.Coordinador;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.Calendario;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.validator.VigenciaValidator;
import src.main.java.dao.vigencia.VigenciaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFormattedTextField;

public class VentanaRegistroVigencia extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar, btnEliminar;
	private JTable table;

	private VigenciaJTableModel model = new VigenciaJTableModel();
	private JScrollPane scrollPane;
	
	private VentanaRegistroVigencia ventanaRegistroVigencia;
	
	private JFormattedTextField txtHasta, txtDesde;

	private VigenciaValidator vigenciaValidator = new VigenciaValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JComboBox cmbPais;
	private JTextField txtHoraDesde;
	private JTextField txtMinutoDesde;
	private JTextField txtHoraHasta;
	private JTextField txtMinutoHasta;
	private JLabel label;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroVigencia() {

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Registrar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroVigencia.class
				.getResource("/imgs/save.png")));
		botonGuardar.setBounds(410, 56, 32, 32);
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
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroVigencia.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(774, 383, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));

		btnEliminar = new JButton();
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setIcon(new ImageIcon(VentanaRegistroVigencia.class
				.getResource("/imgs/borrar.png")));
		btnEliminar.setEnabled(true);
		btnEliminar.setBounds(452, 56, 32, 32);
		btnEliminar.setOpaque(false);
		btnEliminar.setContentAreaFilled(false);
		btnEliminar.setBorderPainted(false);
		Image img4 = ((ImageIcon) btnEliminar.getIcon()).getImage();
		Image newimg4 = img4.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnEliminar.setIcon(new ImageIcon(newimg4));

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE VIGENCIA POR PAIS");
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroVigencia.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		cmbPais = new JComboBox(recuperarDatosComboBoxZona());
		cmbPais.setToolTipText("Nro. y Descripcion de Distrito");
		cmbPais.setBounds(150, 65, 241, 20);
		getContentPane().add(cmbPais);

		JLabel lblPais = new JLabel();
		lblPais.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPais.setText("Pais:");
		lblPais.setBounds(79, 63, 61, 25);
		getContentPane().add(lblPais);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(326, 165, 363, 14);
		getContentPane().add(lblMensaje);
		
		JLabel lblDesde = new JLabel();
		lblDesde.setText("Fch. Desde:");
		lblDesde.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesde.setBounds(26, 96, 116, 25);
		getContentPane().add(lblDesde);
		
		txtDesde = new JFormattedTextField();
		txtDesde.setText("23/04/2015");
		txtDesde.setBounds(164, 98, 103, 23);
		getContentPane().add(txtDesde);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendario cal = new Calendario(ventanaRegistroVigencia);
				cal.displayDate();
				if (!Calendario.fechafinalSeleccionada.startsWith("/")) {
					txtDesde.setText(Calendario.fechafinalSeleccionada);
				}
			}
		});
		button.setIcon(new ImageIcon(VentanaRegistroVigencia.class.getResource("/imgs/cal.png")));
		button.setToolTipText("Calendario");
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setBounds(277, 96, 30, 23);
		getContentPane().add(button);
		
		JLabel lblhasta = new JLabel();
		lblhasta.setText("Fch. Hasta:");
		lblhasta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblhasta.setBounds(26, 131, 116, 25);
		getContentPane().add(lblhasta);
		
		txtHasta = new JFormattedTextField();
		txtHasta.setText("23/04/2015");
		txtHasta.setBounds(164, 133, 103, 23);
		getContentPane().add(txtHasta);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendario cal = new Calendario(ventanaRegistroVigencia);
				cal.displayDate();
				if (!Calendario.fechafinalSeleccionada.startsWith("/")) {
					txtHasta.setText(Calendario.fechafinalSeleccionada);
				}
			}
		});
		button_1.setIcon(new ImageIcon(VentanaRegistroVigencia.class.getResource("/imgs/cal.png")));
		button_1.setToolTipText("Calendario");
		button_1.setOpaque(false);
		button_1.setContentAreaFilled(false);
		button_1.setBorderPainted(false);
		button_1.setBounds(277, 133, 30, 23);
		getContentPane().add(button_1);
		
		txtHoraDesde = new JTextField();
		txtHoraDesde.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtHoraDesde.getText().length() == 1)
				{
					txtHoraDesde.setText(0 + txtHoraDesde.getText() );
				}
			}
		});
		txtHoraDesde.setBounds(326, 98, 32, 23);
		getContentPane().add(txtHoraDesde);
		txtHoraDesde.setColumns(10);
		
		txtMinutoDesde = new JTextField();
		txtMinutoDesde.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtMinutoDesde.getText().length() == 1)
				{
					txtMinutoDesde.setText(0 + txtMinutoDesde.getText() );
				}
			}
		});
		txtMinutoDesde.setColumns(10);
		txtMinutoDesde.setBounds(368, 98, 32, 23);
		getContentPane().add(txtMinutoDesde);
		
		txtHoraHasta = new JTextField();
		txtHoraHasta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtHoraHasta.getText().length() == 1)
				{
					txtHoraHasta.setText(0 + txtHoraHasta.getText() );
				}
			}
		});
		txtHoraHasta.setColumns(10);
		txtHoraHasta.setBounds(326, 134, 32, 23);
		getContentPane().add(txtHoraHasta);
		
		txtMinutoHasta = new JTextField();
		txtMinutoHasta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtMinutoHasta.getText().length() == 1)
				{
					txtMinutoHasta.setText(0 + txtMinutoHasta.getText() );
				}
			}
		});
		txtMinutoHasta.setColumns(10);
		txtMinutoHasta.setBounds(368, 133, 32, 23);
		getContentPane().add(txtMinutoHasta);
		
		JLabel lblNewLabel = new JLabel(":");
		lblNewLabel.setBounds(360, 98, 18, 20);
		getContentPane().add(lblNewLabel);
		
		label = new JLabel(":");
		label.setBounds(360, 134, 18, 20);
		getContentPane().add(label);

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
				Integer distritoSelected = item3.getId();
				//if (!(txtNroZona.getText().length() == 0)) {
//					if (txtNroZona.getText().length() > 3) {
//						lblMensaje.setText("El codigo debe ser de maximo 3 caracteres.");
//						Timer t = new Timer(Login.timer, new ActionListener() {
//
//							public void actionPerformed(ActionEvent e) {
//								lblMensaje.setText(null);
//							}
//						});
//						t.setRepeats(false);
//						t.start();
//					} 
					//else
						if   (vigenciaValidator.ValidarCodigo( distritoSelected) == false) {
						//if (candidatoValidator.ValidarPersona(personaSelected) == false) {
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
							query.setQueryGenerico("INSERT INTO ucsaws_vigencia_horario_x_pais"
									+ "( id_vigencia, id_pais, fch_vigencia_desde, fch_vigencia_hasta,id_evento ,usuario_ins,fch_ins, usuario_upd, fch_upd) "
									+ "VALUES ("
									+ "nextval('ucsaws_vigencia_seq') "
									+ " ,'"
									+ distritoSelected + "' , '"+ txtDesde.getText()+ " " + txtHoraDesde.getText() + ":" + txtMinutoDesde.getText()+
									"','" + txtHasta.getText() + " " + txtHoraHasta.getText() + ":" + txtMinutoHasta.getText() + "'," + VentanaBuscarEvento.evento + " ,'"
									+ Login.userLogeado
									+ "' , now(), '"
									+ Login.userLogeado
									+ "' , now())");

							QueryGenericoResponse response = weatherClient
									.getQueryGenericoResponse(query);
							weatherClient.printQueryGenericoResponse(response);

							model = new VigenciaJTableModel();
							recuperarDatos();
							table.setModel(model);
							model.fireTableDataChanged();
							table.removeColumn(table.getColumnModel()
									.getColumn(0));
							// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
							lblMensaje
									.setText("Excelente, se ha guardado la vigencia.");
							Timer t = new Timer(Login.timer,
									new ActionListener() {

										public void actionPerformed(
												ActionEvent e) {
											lblMensaje.setText(null);
										}
									});
							t.setRepeats(false);
							t.start();

							

							// this.dispose();
//						} else {
//							// JOptionPane.showMessageDialog(null,
//							// "Ya existe el genero " + txtDesc.getText(),
//							// "Información",JOptionPane.WARNING_MESSAGE);
//							lblMensaje
//									.setText("La Persona no puede tener mas de una candidatura");
//							Timer t = new Timer(Login.timer,
//									new ActionListener() {
//
//										public void actionPerformed(
//												ActionEvent e) {
//											lblMensaje.setText(null);
//										}
//									});
//							t.setRepeats(false);
//							t.start();
//						}
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje.setText("Ya existe la vigencia");
						
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent ev) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					}
						

			
			

//				else {
//					// JOptionPane.showMessageDialog(null, ,
//					// "Información",JOptionPane.WARNING_MESSAGE);
//					lblMensaje.setText("Debe ingresar todos los campos.");
//					Timer t = new Timer(Login.timer, new ActionListener() {
//
//						public void actionPerformed(ActionEvent e) {
//							lblMensaje.setText(null);
//						}
//					});
//					t.setRepeats(false);
//					t.start();
//				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,
						"Error al intentar insertar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		if (e.getSource() == btnEliminar) {

			if (!codTemporal.equals("")) {

				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar la vigencia?",
						"Confirmación", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)

				{
					VigenciaDAO localDAO = new VigenciaDAO();

					try {
						localDAO.eliminarLocal(codTemporal);

					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "sfdsfsfsdfs",
								"Información", JOptionPane.WARNING_MESSAGE);
					}
					if (localDAO.eliminarLocal(codTemporal) == true) {

						// JOptionPane.showMessageDialog(null,"Excelente, se ha eliminado el genero "
						// + txtDesc.getText());
						// modificarGenero(textCod.getText(),
						// codTemporal.getText());
						// txtId.setText("");
						lblMensaje
								.setText("Excelente, se ha eliminado el Local ");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
						limpiar();
						
						model = new VigenciaJTableModel();

						recuperarDatos();
						table.setModel(model);

						model.fireTableDataChanged();
						table.removeColumn(table.getColumnModel().getColumn(0));
					}

					else {
						// JOptionPane.showMessageDialog(null,"Existen registros que apuntan al Genero que desea eliminar ","Error",JOptionPane.ERROR_MESSAGE);
						lblMensaje
								.setText("ERROR: Existen registros que apuntan al Local que desea eliminar ");
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
						.setText("Por favor seleccione que Local desea Eliminar");
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
			VentanaBuscarVigencia candidato = new VentanaBuscarVigencia();
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

		query.setQueryGenerico("SELECT  id_vigencia, codigo,nombre,fch_vigencia_desde, fch_vigencia_hasta "
				+ "from  ucsaws_vigencia_horario_x_pais v join ucsaws_pais p on (v.id_pais = p.id_pais)"
				+ "order by nombre" + "");

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
					fil.get(4).toString() };

			model.ciudades.add(fin);
			ite++;
		}

	}

	private Vector recuperarDatosComboBoxZona() {
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
}