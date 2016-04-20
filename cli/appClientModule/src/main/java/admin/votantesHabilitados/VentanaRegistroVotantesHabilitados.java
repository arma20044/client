package src.main.java.admin.votantesHabilitados;

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

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.departamento.Item;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.evento.VentanaRegistroEvento;
import src.main.java.admin.local.VentanaBuscarLocal;
import src.main.java.admin.validator.VotantesHabilitadosValidator;
import src.main.java.dao.votantesHabilitados.VotantesHabilitadosDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.EleccionMesa;
import src.main.java.login.Login;
import src.main.java.login.PreLogin;
import src.main.java.proceso.voto.VentanaVotoFinal;
import src.main.java.votante.VentanaPrincipalVotante;
import sun.awt.RepaintArea;

import javax.swing.JCheckBox;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class VentanaRegistroVotantesHabilitados extends JFrame implements
		ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje, lblMesa;
	private JButton botonGuardar, botonCancelar;
	private JTable table;

	private VotantesHabilitadosJTableModel model = new VotantesHabilitadosJTableModel();
	private JScrollPane scrollPane;

	private VotantesHabilitadosValidator votantesHabilitadosValidator = new VotantesHabilitadosValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JComboBox cmbPersona, cmbDepartamento, cmbMesa, cmbZona;
	private JLabel lblDepartamento;
	private JLabel lblZona;
	private JComboBox comboBox;

	private JComboBox cmbDistrito;

	private static String nombrecmb;
	private JLabel lblLocal;
	private JComboBox cmbLocal;

	private DefaultComboBoxModel dm, dmz, dml, dmm;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroVotantesHabilitados() {

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Registrar");
		botonGuardar.setIcon(new ImageIcon(
				VentanaRegistroVotantesHabilitados.class
						.getResource("/imgs/save.png")));
		botonGuardar.setBounds(499, 36, 32, 32);
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
		botonCancelar.setIcon(new ImageIcon(
				VentanaRegistroVotantesHabilitados.class
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
		labelTitulo.setText("REGISTRO DE VOTANTES HABILITADOS");
		labelTitulo.setBounds(163, 11, 486, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroVotantesHabilitados.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		cmbPersona = new JComboBox(recuperarDatosComboBoxPersona());
		cmbPersona.setSelectedIndex(-1);
		cmbPersona.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				// cmbMesa.setVisible(false);
				// if(cmbMesa != null){
				// if(cmbMesa.getModel().getSize() == 0){
				//
				// }
				// }
				// else
				if (cmbZona != null) {
					cmbZona.removeAllItems();

					System.out.println(cmbZona.getItemCount());
					// cmbMesa = null;

					// cmbMesa.revalidate();
					// cmbMesa.repaint();

					Item item = (Item) cmbPersona.getSelectedItem();
					Integer personaSelected = item.getId();

					Integer pais = PaisDePersonaSeleccionada(personaSelected);

					// recuperarDatosComboBoxMesaPorPais(pais);
					Vector zonaXPais = recuperarDatosComboBoxZonaPorPais(pais);
					if (zonaXPais.toString().compareTo("[]") != 0) {
						cmbZona = new JComboBox(zonaXPais);
						cmbZona.setBounds(209, 112, 289, 20);
						cmbZona.revalidate();
						getContentPane().add(cmbZona);
						System.out.println(cmbZona.getItemCount());

						// cmbMesa.repaint();

						lblMesa.setVisible(true);
						lblMesa.revalidate();

						if (cmbZona.getItemCount() == 0) {
							cmbZona.removeAllItems();
							cmbZona.setVisible(false);
							cmbZona.revalidate();
						}
					}

				} else {
					Item item = (Item) cmbPersona.getSelectedItem();
					Integer personaSelected = item.getId();

					Integer pais = PaisDePersonaSeleccionada(personaSelected);

					// recuperarDatosComboBoxMesaPorPais(pais);
					// cmbMesa.setModel( );

					Vector zonaXPais = recuperarDatosComboBoxZonaPorPais(pais);

					if (zonaXPais.toString().compareTo("[]") != 0) {
						cmbZona = new JComboBox(zonaXPais);
						cmbZona.setBounds(209, 112, 289, 20);
						// cmbMesa.revalidate();
						cmbZona.repaint();
						getContentPane().add(cmbZona);

					}
					// lblMesa.setVisible(true);
					// lblMesa.revalidate();

					// pais = 0;

				}
			}
		});

		cmbPersona.setBounds(209, 48, 289, 20);
		getContentPane().add(cmbPersona);

		cmbDepartamento = new JComboBox(recuperarDatosComboBoxDepartamento());
		cmbDepartamento.setSelectedIndex(-1);

		cmbDepartamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				barrer();

				Item item = (Item) cmbDepartamento.getSelectedItem();
				Integer idDepartamentoSelected = item.getId();
				recuperarDatosComboBoxDistrito(idDepartamentoSelected);
				cmbDistrito.setModel(dm);
				cmbDistrito.setSelectedIndex(-1);
				// cmbDistrito.addActionListener(new ActionListener() {
				//
				// @Override
				// public void actionPerformed(ActionEvent arg0) {
				// // TODO Auto-generated method stub
				// if (cmbDistrito.getSelectedIndex()!=-1){
				//
				// Item item = (Item) cmbDistrito.getSelectedItem();
				// Integer idDistritoSelected = item.getId();
				//
				//
				// cmbZona.addActionListener(new ActionListener() {
				//
				// @Override
				// public void actionPerformed(ActionEvent arg0) {
				// // TODO Auto-generated method stub
				//
				// Item item = (Item) cmbZona.getSelectedItem();
				// Integer idDistritoSelected = item.getId();
				//
				//
				// cmbLocal.addActionListener(new ActionListener() {
				//
				// @Override
				// public void actionPerformed(ActionEvent arg0) {
				// // TODO Auto-generated method stub
				//
				// Item item = (Item) cmbLocal
				// .getSelectedItem();
				// Integer idDistritoSelected = item
				// .getId();
				//
				//
				// }
				// });
				//
				//
				//
				//
				//
				// }
				// });
				//
				// }
				// }
				// });

			}
		});

		// cmbDepartamento.addItemListener(new ItemListener() {
		// public void itemStateChanged(ItemEvent arg0) {
		//
		//
		//
		//
		//
		//
		// }
		// });

		cmbDepartamento.setBounds(209, 81, 289, 20);
		getContentPane().add(cmbDepartamento);

		cmbDistrito = new JComboBox();
		cmbDistrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbDistrito.getSelectedIndex() != -1) {
					
					

					Item item = (Item) cmbDistrito.getSelectedItem();
					Integer idDepartamentoSelected = item.getId();

					recuperarDatosComboBoxZona(idDepartamentoSelected);
					cmbZona.setModel(dmz);
					cmbZona.setSelectedIndex(-1);
					barrer2();
				}
				else
					barrer2();
			}
		});
		cmbDistrito.setBounds(209, 111, 289, 20);
		cmbDistrito.revalidate();

		cmbZona = new JComboBox();
		cmbZona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbZona.getSelectedIndex() != -1) {
					
					

					Item item = (Item) cmbZona.getSelectedItem();
					Integer idDepartamentoSelected = item.getId();

					recuperarDatosComboBoxLocal(idDepartamentoSelected);
					cmbLocal.setModel(dml);
					cmbLocal.setSelectedIndex(-1);
				}
				else
					barrer3();
			}
		});
		cmbZona.setBounds(209, 144, 289, 20);
		getContentPane().add(cmbZona);

		// repaint();
		getContentPane().add(cmbDistrito);

		cmbLocal = new JComboBox();
		cmbLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbLocal.getSelectedIndex() != -1) {
					
					

					Item item = (Item) cmbLocal.getSelectedItem();
					Integer idDepartamentoSelected = item.getId();

					recuperarDatosComboBoxMesa(idDepartamentoSelected);
					cmbMesa.setModel(dmm);
					cmbMesa.setSelectedIndex(-1);
				}
				
				else{
					barrer4();
				}
			}
		});
		cmbLocal.setBounds(209, 172, 289, 20);
		getContentPane().add(cmbLocal);

		cmbMesa = new JComboBox();
		cmbMesa.setBounds(209, 199, 169, 20);
		getContentPane().add(cmbMesa);

		// cmbDistrito.paint(cmbDistrito.getGraphics());

		JLabel lblPersona = new JLabel();
		lblPersona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPersona.setText("Persona:");
		lblPersona.setBounds(141, 46, 61, 25);
		getContentPane().add(lblPersona);

		lblDepartamento = new JLabel();
		lblDepartamento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDepartamento.setText("Departamento:");
		lblDepartamento.setBounds(129, 84, 73, 14);
		getContentPane().add(lblDepartamento);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(433, 240, 363, 14);
		getContentPane().add(lblMensaje);

		lblMesa = new JLabel();
		lblMesa.setText("Mesa:");
		lblMesa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMesa.setBounds(141, 197, 61, 25);
		lblMesa.setVisible(true);
		getContentPane().add(lblMesa);

		lblZona = new JLabel("Zona:");
		lblZona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblZona.setBounds(163, 144, 41, 20);
		getContentPane().add(lblZona);

		JLabel lblDistrito = new JLabel();
		lblDistrito.setText("Distrito");
		lblDistrito.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDistrito.setBounds(129, 114, 73, 14);
		getContentPane().add(lblDistrito);

		lblLocal = new JLabel("Local:");
		lblLocal.setBounds(174, 172, 28, 14);
		getContentPane().add(lblLocal);

		// cmbZona = new JComboBox();
		// cmbZona.addFocusListener(new FocusAdapter() {
		// @Override
		// public void focusLost(FocusEvent arg0) {
		// //cmbMesa.setVisible(false);
		// // if(cmbMesa != null){
		// // if(cmbMesa.getModel().getSize() == 0){
		// //
		// // }
		// // }
		// // else
		// if (cmbMesa != null){
		// cmbMesa.removeAllItems();
		//
		//
		// System.out.println(cmbMesa.getItemCount());
		// //cmbMesa = null;
		//
		// //cmbMesa.revalidate();
		// //cmbMesa.repaint();
		//
		//
		//
		// Item item = (Item) cmbPersona.getSelectedItem();
		// Integer personaSelected = item.getId();
		//
		// Integer pais = PaisDePersonaSeleccionada(personaSelected);
		//
		//
		// //recuperarDatosComboBoxMesaPorPais(pais);
		// Vector mesaXPais = recuperarDatosComboBoxMesaPorPais(pais);
		// if (mesaXPais.toString().compareTo("[]") != 0){
		// cmbMesa = new JComboBox(mesaXPais);
		// cmbMesa.setBounds(209, 143, 289, 20);
		// cmbMesa.revalidate();
		// getContentPane().add(cmbMesa);
		// System.out.println(cmbMesa.getItemCount());
		//
		// //cmbMesa.repaint();
		//
		// lblMesa.setVisible(true);
		// lblMesa.revalidate();
		//
		// if (cmbMesa.getItemCount() == 0){
		// cmbMesa.removeAllItems();
		// cmbMesa.setVisible(false);
		// cmbMesa.revalidate();
		// }
		// }
		//
		// }
		// else{
		// Item item = (Item) cmbPersona.getSelectedItem();
		// Integer personaSelected = item.getId();
		//
		// Integer pais = PaisDePersonaSeleccionada(personaSelected);
		//
		//
		// //recuperarDatosComboBoxMesaPorPais(pais);
		// //cmbMesa.setModel( );
		//
		// Vector mesaXPais = recuperarDatosComboBoxMesaPorPais(pais);
		//
		// if (mesaXPais.toString().compareTo("[]") != 0){
		// cmbMesa = new JComboBox(mesaXPais);
		// cmbMesa.setBounds(209, 143, 289, 20);
		// //cmbMesa.revalidate();
		// cmbMesa.repaint();
		// getContentPane().add(cmbMesa);
		//
		// }
		// // lblMesa.setVisible(true);
		// // lblMesa.revalidate();
		//
		// //pais = 0;
		//
		// }
		// }
		// });
		// cmbZona.setBounds(209, 112, 164, 20);
		// getContentPane().add(cmbZona);

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

				Item item = (Item) cmbPersona.getSelectedItem();
				Integer personaSelected = item.getId();

				Item item2 = (Item) cmbDepartamento.getSelectedItem();
				Integer habilitadoSelected = item2.getId();

				// if (!(txtCod.getText().length() == 0)) {

				if (votantesHabilitadosValidator.ValidarCedula(personaSelected) == false) {
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

					Item itemMesa = (Item) cmbMesa.getSelectedItem();
					Integer idMesa = itemMesa.getId();
					System.out.println(idMesa);

					query.setTipoQueryGenerico(1);
					System.out.println(Login.userLogeado);
					query.setQueryGenerico("INSERT INTO ucsaws_votante"
							+ "( id_votante, id_persona, habilitado,sufrago, id_mesa, id_evento,  usuario_ins,fch_ins, usuario_upd, fch_upd) "
							+ "VALUES (" + "nextval('ucsaws_votante_seq')"
							+ " , " + personaSelected + ", 2 ," + "0" + ", "
							+ idMesa + "," + VentanaBuscarEvento.evento + ", '"
							+ Login.userLogeado + "' , now(), '"
							+ Login.userLogeado + "' , now())");

					QueryGenericoResponse response = weatherClient
							.getQueryGenericoResponse(query);
					weatherClient.printQueryGenericoResponse(response);

					VentanaBuscarVotantesHabilitados buscar = new VentanaBuscarVotantesHabilitados();
					buscar.setVisible(true);
					dispose();

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
							.setText("La persona ya esta habilitada con esa cedula: "
									+ cmbPersona.getSelectedItem().toString());
					Timer t = new Timer(Login.timer, new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							lblMensaje.setText(null);
						}
					});
					t.setRepeats(false);
					t.start();
				}

				// }

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,
						"Error al intentar insertar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		if (e.getSource() == botonCancelar) {
			VentanaBuscarVotantesHabilitados votante = new VentanaBuscarVotantesHabilitados();
			votante.setVisible(true);
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

		query.setQueryGenerico("SELECT vo.id_votante,ci,per.nombre, apellido,  hab.cod_habilitado,sufrago , pActual.nombre as PaisActual, desc_mesa "

				+ "from ucsaws_votante vo join ucsaws_persona per on (vo.id_persona = per.id_persona) "
				// +
				// " join ucsaws_pais pOrigen on (pOrigen.id_pais = per.id_pais_origen) "
				+ " join ucsaws_pais pActual on (pActual.id_pais = per.id_pais_actual) "
				+ " join ucsaws_habilitado hab on (hab.id_habilitado = habilitado) "
				+ " join ucsaws_mesa m on (vo.id_mesa = m.id_mesa) where vo.id_evento= "
				+ VentanaBuscarEvento.evento + " and sufrago = 0");

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

			String[] fin = { fil.get(0).toString(), String.valueOf(contador),
					fil.get(1).toString(), fil.get(2).toString(),
					fil.get(3).toString(), fil.get(4).toString(),
					fil.get(5).toString(), fil.get(6).toString(),
					fil.get(7).toString() };

			model.ciudades.add(fin);
			ite++;
		}

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

	// private Vector recuperarDatosComboBoxMesa(Integer pais) {
	//
	// Item item = (Item) cmbPersona.getSelectedItem();
	// Integer personaSelected = item.getId();
	//
	// Vector model = new Vector();
	// JSONArray filas = new JSONArray();
	// JSONArray fil = new JSONArray();
	//
	// boolean existe = false;
	//
	// // Statement estatuto = conex.getConnection().createStatement();
	//
	// ApplicationContext ctx = SpringApplication
	// .run(WeatherConfiguration.class);
	//
	// WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	// QueryGenericoRequest query = new QueryGenericoRequest();
	//
	// // para registrar se inserta el codigo es 1
	// query.setTipoQueryGenerico(2);
	//
	// //
	// query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
	// // +
	// //
	// "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");
	//
	// query.setQueryGenerico("SELECT id_mesa, desc_mesa"
	// + " ucsaws_mesa m join ucsaws_local l on (m.id_local = l.id_local)"
	// + " join ucsaws_zona z on (l.id_zona = z.id_zona)"
	// + " join ucsaws_distrito d on (d.id_distrito = z.id_distrito)"
	// +
	// " join ucsaws_departamento de on (de.id_departamento = d.id_departamento)"
	// + " join ucsaws_votante vo on (vo.id_mesa = m.id_mesa)"
	// + " join ucsaws_persona per on (per.id_persona = vo.id_persona)"
	// + " where id_pais_actual = " + pais);
	//
	// QueryGenericoResponse response = weatherClient
	// .getQueryGenericoResponse(query);
	// weatherClient.printQueryGenericoResponse(response);
	//
	// String res = response.getQueryGenericoResponse();
	//
	// if (res.compareTo("ERRORRRRRRR") == 0) {
	// JOptionPane.showMessageDialog(null, "algo salio mal",
	// "Advertencia", JOptionPane.WARNING_MESSAGE);
	//
	// }
	//
	// else {
	// existe = true;
	//
	// String generoAntesPartir = response.getQueryGenericoResponse();
	//
	// JSONParser j = new JSONParser();
	// Object ob = null;
	// String part1, part2, part3;
	//
	// try {
	// ob = j.parse(generoAntesPartir);
	// } catch (org.json.simple.parser.ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// filas = (JSONArray) ob;
	//
	// }
	//
	// int ite = 0;
	// String campo4, campo5 = "";
	// while (filas.size() > ite) {
	// fil = (JSONArray) filas.get(ite);
	//
	// String[] fin = { fil.get(0).toString(), fil.get(1).toString(), };
	//
	// ciudades.add(fin);
	// model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
	// ite++;
	// }
	// return model;
	// }

	private Vector recuperarDatosComboBoxHabilitado() {
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

		query.setQueryGenerico("SELECT id_habilitado, cod_habilitado"
				+ " from ucsaws_habilitado " + "order by cod_habilitado");

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

	private Integer PaisDePersonaSeleccionada(Integer personaSelected) {

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

		query.setQueryGenerico("SELECT id_persona, id_pais_actual"
				+ " from ucsaws_persona where id_persona = " + personaSelected
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
		System.out.println(fil.get(1));
		Integer i = (int) (long) fil.get(1);
		return i;

	}

	private Vector recuperarDatosComboBoxMesaPorPais(Integer pais) {
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

		query.setQueryGenerico("SELECT  id_mesa, desc_mesa "
				+ "from ucsaws_mesa m join ucsaws_local l on (m.id_local = l.id_local)"
				+ "join ucsaws_zona z on (l.id_zona = z.id_zona)"
				+ " join ucsaws_distrito dis on (dis.id_distrito = z.id_distrito)"
				+ " join ucsaws_departamento dep on (dep.id_departamento = dis.id_departamento)"
				+ " where m.id_evento = " + VentanaBuscarEvento.evento
				+ " and dis.id_distrito = " + pais
				+ " order by nro_local , nro_mesa" + "");

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

	private Vector recuperarDatosComboBoxZonaPorPais(Integer pais) {
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

		query.setQueryGenerico("SELECT  z.id_zona, z.desc_zona "
				+ "from ucsaws_mesa m join ucsaws_local l on (m.id_local = l.id_local)"
				+ "join ucsaws_zona z on (l.id_zona = z.id_zona)"
				+ " join ucsaws_distrito dis on (dis.id_distrito = z.id_distrito)"
				+ " join ucsaws_departamento dep on (dep.id_departamento = dis.id_departamento)"
				+ " where m.id_evento = " + VentanaBuscarEvento.evento
				+ " and dis.id_distrito = " + pais
				+ " order by nro_local , nro_mesa" + "");

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

	private Vector recuperarDatosComboBoxDepartamento() {
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

		query.setQueryGenerico("SELECT id_departamento, nro_departamento || ' -  ' || desc_departamento"
				+ " from ucsaws_departamento where id_evento = "
				+ VentanaBuscarEvento.evento + " order by nro_departamento");

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

	private void recuperarDatosComboBoxDistrito(Integer idDepartamento) {
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

		query.setQueryGenerico("SELECT id_distrito, nro_distrito || ' -  ' || desc_distrito"
				+ " from ucsaws_distrito where id_departamento = "
				+ idDepartamento
				+ " and id_evento = "
				+ VentanaBuscarEvento.evento + " order by nro_distrito");

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

		Vector data = new Vector<Vector<Object>>();

		// Vector<Object> vector = new Vector<Object>();

		int ite = 0;
		String campo4, campo5 = "";
		int contador = 0;
		while (filas.size() > ite) {
			contador = contador + 1;
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), fil.get(1).toString() };

			data.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
			ite++;
		}

		dm = new DefaultComboBoxModel(data);
	}

	private void recuperarDatosComboBoxZona(Integer idZona) {
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

		query.setQueryGenerico("SELECT id_zona, nro_zona || ' -  ' || desc_zona"
				+ " from ucsaws_zona where id_distrito = "
				+ idZona
				+ " and id_evento = "
				+ VentanaBuscarEvento.evento
				+ " order by nro_zona");

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

		Vector data = new Vector<Vector<Object>>();

		// Vector<Object> vector = new Vector<Object>();

		int ite = 0;
		String campo4, campo5 = "";
		int contador = 0;
		while (filas.size() > ite) {
			contador = contador + 1;
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), fil.get(1).toString() };

			data.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
			ite++;
		}

		dmz = new DefaultComboBoxModel(data);
	}

	private void recuperarDatosComboBoxLocal(Integer idZona) {
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

		query.setQueryGenerico("SELECT id_local, nro_local || ' -  ' || desc_local"
				+ " from ucsaws_local where id_zona = "
				+ idZona
				+ " and id_evento = "
				+ VentanaBuscarEvento.evento
				+ " order by nro_local");

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

		Vector data = new Vector<Vector<Object>>();

		// Vector<Object> vector = new Vector<Object>();

		int ite = 0;
		String campo4, campo5 = "";
		int contador = 0;
		while (filas.size() > ite) {
			contador = contador + 1;
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), fil.get(1).toString() };

			data.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
			ite++;
		}

		dml = new DefaultComboBoxModel(data);
	}

	private void recuperarDatosComboBoxMesa(Integer idLocal) {
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

		query.setQueryGenerico("SELECT id_mesa, nro_mesa || ' -  ' || desc_mesa"
				+ " from ucsaws_mesa where id_local = "
				+ idLocal
				+ " and id_evento = "
				+ VentanaBuscarEvento.evento
				+ " order by nro_mesa");

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

		Vector data = new Vector<Vector<Object>>();

		// Vector<Object> vector = new Vector<Object>();

		int ite = 0;
		String campo4, campo5 = "";
		int contador = 0;
		while (filas.size() > ite) {
			contador = contador + 1;
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), fil.get(1).toString() };

			data.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
			ite++;
		}

		dmm = new DefaultComboBoxModel(data);
	}

	void barrer() {

	//	if (cmbDistrito.getSelectedIndex() != -1) {
			cmbDistrito.removeAllItems();
			cmbZona.removeAllItems();
			cmbLocal.removeAllItems();
			cmbMesa.removeAllItems();
		//}
	}

	void barrer2() {

	//	if (cmbDistrito.getSelectedIndex() != -1) {
			//cmbDistrito.removeAllItems();
			//cmbZona.removeAllItems();
			cmbLocal.removeAllItems();
			cmbMesa.removeAllItems();
		//}
	}
	
void barrer3(){
		
	//	if(cmbDistrito.getSelectedIndex()!= -1){
		
		
		//cmbLocal.removeAllItems();
		cmbMesa.removeAllItems();
		//}
	}

void barrer4(){
	
	//if(cmbDistrito.getSelectedIndex()!= -1){

	cmbMesa.removeAllItems();
	//}
}

}