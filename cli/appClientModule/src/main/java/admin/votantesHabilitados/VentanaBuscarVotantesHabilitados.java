package src.main.java.admin.votantesHabilitados;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
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
import javax.swing.table.TableRowSorter;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.Votante;
import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.evento.VentanaModificarEvento;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.votantesHabilitados.VotantesHabilitadosDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaBuscarVotantesHabilitados extends JFrame implements
		ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador

	String tempNombre, tempApellido, tempIdMesa, mesa, tempHabilitado, sufrago;
	Integer tempCI;

	static Integer ciVotante;

	static Integer idVotante;

	private JLabel labelTitulo;
	private JTextField txtBuscar;
	private JLabel lblBuscar;
	private JButton botonCancelar, btnNuevo, btnEliminar;

	JSONArray miPersona = null;
	DefaultTableModel modelo;
	private JTable table_1;
	private VotantesHabilitadosJTableModel model = new VotantesHabilitadosJTableModel();
	private JScrollPane scrollPane;

	private String codTemporal = "";

	private JLabel lblMensaje;

	private DefaultTableModel dm;

	boolean eliminado;

	Votante votante;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de busqueda
	 */
	public VentanaBuscarVotantesHabilitados() {

		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				txtBuscar.requestFocus();
			}
		});

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		botonCancelar = new JButton();
		botonCancelar.setIcon(new ImageIcon(
				VentanaBuscarVotantesHabilitados.class
						.getResource("/imgs/back2.png")));
		botonCancelar.setToolTipText("Atrás");
		botonCancelar.setBounds(589, 422, 45, 25);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg));

		labelTitulo = new JLabel();
		labelTitulo.setText("ABM DE VOTANTES HABILITADOS");
		labelTitulo.setBounds(131, 11, 387, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblBuscar = new JLabel();
		lblBuscar.setText("Buscar:");
		lblBuscar.setBounds(20, 52, 64, 25);
		getContentPane().add(lblBuscar);

		txtBuscar = new JTextField();
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String query = txtBuscar.getText().toUpperCase();
				filter(query);
			}
		});
		txtBuscar.setBounds(86, 52, 319, 25);
		getContentPane().add(txtBuscar);
		botonCancelar.addActionListener(this);

		getContentPane().add(botonCancelar);
		getContentPane().add(labelTitulo);
		limpiar();

		setSize(640, 476);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("Lista de Votantes Habilitados");
		scrollPane.setBounds(0, 158, 634, 265);
		getContentPane().add(scrollPane);

		table_1 = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table_1.setToolTipText("Listado de Votantes Habilitados.");
		table_1.setAutoCreateRowSorter(true);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table_1);
		// String[] columnNames = {"Picture", "Description"};
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(VentanaBuscarEvento.readOnly==false){

				List<String> selectedData = new ArrayList<String>();

				// int selectedRow = table_1.rowAtPoint(arg0.getPoint());

				// Object a =
				// table_1.getModel().getValueAt(table_1.convertRowIndexToView(selectedRow[0]),
				// 0);
				// int[] selectedColumns = table_1.getSelectedColumns();
				// System.out.println(a);

				// if (selectedRow >= 0) {
				int selectedRow = table_1.rowAtPoint(arg0.getPoint());
				System.out.println(selectedRow);
				int col = 0;
				while (col < table_1.getColumnCount() + 1) {
					// System.out.println(table_1.getValueAt(selectedRow,
					// col));
					try {
						int row = table_1.rowAtPoint(arg0.getPoint());
						String table_click0 = table_1
								.getModel()
								.getValueAt(
										table_1.convertRowIndexToModel(row),
										col).toString();
						// System.out.println(table_click0);

						selectedData.add(table_click0);
						System.out.println(selectedData);

						// comentar despues
						// int row1 = table_1.rowAtPoint(arg0.getPoint());
						// String table_click01 =
						// table_1.getModel().getValueAt(table_1.
						// convertRowIndexToModel(row1), 0).toString();
						// System.out.println(table_click01);
						// comentar despues

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

					col++;
				}
				// selectedData.ad table_1.getValueAt(selectedRow[i],
				// selectedColumns[0]);
				// txtId.setText(selectedData.get(0));
				txtBuscar.setText(selectedData.get(0));

				// textFecha.setText(selectedData.get(2));
				// textUsu.setText(selectedData.get(4));
				// codTemporal.setText(selectedData.get(1));
				codTemporal = selectedData.get(0);

				idVotante = Integer.parseInt(codTemporal);

				ciVotante = Integer.parseInt(selectedData.get(2));

				tempCI = Integer.parseInt(selectedData.get(2));

				tempNombre = selectedData.get(3);

				tempApellido = selectedData.get(4);

				tempIdMesa = selectedData.get(8);
				;

				mesa = tempIdMesa.substring(tempIdMesa.length() - 1);

				tempHabilitado = selectedData.get(5);

				sufrago = selectedData.get(6);

				votante = new Votante();
				votante.setNombre(tempNombre);
				votante.setApellido(tempApellido);
				votante.setCi(tempCI.toString());
				votante.setHabilitado(tempHabilitado);
				votante.setSufrago(sufrago);
				votante.setPaisActual(selectedData.get(7));
				votante.setId_mesa(tempIdMesa);

				// }
				System.out.println("Selected: " + selectedData);

				if (arg0.getClickCount() == 2) {

					if (tempHabilitado.compareTo("SI") == 0) {
						JOptionPane.showMessageDialog(null,
								"Ya esta habilitado", "Información",
								JOptionPane.WARNING_MESSAGE);
					}

					else {
						if (tempHabilitado.compareTo("NO") == 0) {
							VentanaHabilitarVotante habilitar = new VentanaHabilitarVotante(
									tempCI, tempNombre, tempApellido, Integer
											.parseInt(mesa));
							habilitar.setVisible(true);
							dispose();
						}
					}

				}

				else {
					if (arg0.getClickCount() == 1) {

					}

				}
			}
				else{
					JOptionPane.showMessageDialog(null, "Ya No se puede habilitar votantes durante ni despues la votacion","Aviso", JOptionPane.WARNING_MESSAGE);
					
				}
			}
		});
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		recuperarDatos();
		table_1.setModel(dm);
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		JLabel lblListaDeGeneros = new JLabel();
		lblListaDeGeneros.setText("LISTA DE VOTANTES HABILITADOS\r\n");
		lblListaDeGeneros.setFont(new Font("Verdana", Font.BOLD, 18));
		lblListaDeGeneros.setBounds(117, 117, 372, 30);
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
		btnHome.setIcon(new ImageIcon(VentanaBuscarVotantesHabilitados.class
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
				VentanaRegistroVotantesHabilitados registro = new VentanaRegistroVotantesHabilitados();
				registro.setVisible(true);
				dispose();
			}
		});
		btnNuevo.setToolTipText("Nuevo");
		btnNuevo.setOpaque(false);
		btnNuevo.setContentAreaFilled(false);
		btnNuevo.setBorderPainted(false);
		btnNuevo.setIcon(new ImageIcon(VentanaBuscarVotantesHabilitados.class
				.getResource("/imgs/add.png")));
		btnNuevo.setBounds(415, 52, 32, 32);
		Image img2 = ((ImageIcon) btnNuevo.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnNuevo.setIcon(new ImageIcon(newimg2));
		getContentPane().add(btnNuevo);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(57, 88, 432, 14);
		getContentPane().add(lblMensaje);

		btnEliminar = new JButton();
		btnEliminar.setIcon(new ImageIcon(
				VentanaBuscarVotantesHabilitados.class
						.getResource("/imgs/borrar.png")));
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setOpaque(false);
		btnEliminar.setEnabled(true);
		btnEliminar.setContentAreaFilled(false);
		btnEliminar.setBorderPainted(false);
		btnEliminar.setBounds(457, 53, 32, 32);
		getContentPane().add(btnEliminar);

		btnEliminar.addActionListener(this);
		//Image newimg3 = img3.getScaledInstance(32, 32,
			//	java.awt.Image.SCALE_SMOOTH);

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
		
		if(VentanaBuscarEvento.readOnly==true){
			btnNuevo.setEnabled(false);
			btnNuevo.setToolTipText("Ya No se puede cargar datos durante ni despues la votacion");
			btnEliminar.setEnabled(false);
			btnEliminar.setToolTipText("Ya No se puede eliminar datos durante ni despues la votacion");
			//btnModificar.setEnabled(false);
			//btnModificar.setToolTipText("Ya No se puede Modificar datos durante ni despues la votacion");
		}

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

		if (e.getSource() == btnEliminar) {
			if (!codTemporal.equals("")) {
				if (sufrago.compareTo("0") == 0) {
					int respuesta = JOptionPane.showConfirmDialog(this,
							"¿Esta seguro de eliminar el Votante?",
							"Confirmación", JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_NO_OPTION) {
						VotantesHabilitadosDAO votanteDAO = new VotantesHabilitadosDAO();

						try {
							eliminado = votanteDAO.eliminarVotante(codTemporal);

						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "sfdsfsfsdfs",
									"Información", JOptionPane.WARNING_MESSAGE);
						}

						if (eliminado == false) {

							lblMensaje
									.setText("No se ha podido Eliminar - Existen referencias a éste registro.");
							codTemporal = "";
							txtBuscar.setText("");

							Timer t = new Timer(Login.timer,
									new ActionListener() {

										public void actionPerformed(
												ActionEvent e) {
											lblMensaje.setText(null);
										}
									});
						}

						else {

							lblMensaje
									.setText("Excelente, se ha eliminado el Votante.");
							codTemporal = "";
							txtBuscar.setText("");

							Timer t = new Timer(Login.timer,
									new ActionListener() {

										public void actionPerformed(
												ActionEvent e) {
											lblMensaje.setText(null);
										}
									});

							codTemporal = "";
							limpiar();

							model = new VotantesHabilitadosJTableModel();

							recuperarDatos();
							table_1.setModel(dm);
							table_1.removeColumn(table_1.getColumnModel()
									.getColumn(0));
							// model.fireTableDataChanged();
							// table_1.repaint();
						}
					}
				} else {
					lblMensaje
							.setText("Ésta persona ha votado por ende no puede ser eliminada del Sistema.");

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
				lblMensaje
						.setText("Por favor seleccione que Evento desea Eliminar");

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

		query.setQueryGenerico("SELECT vo.id_votante,ci,per.nombre, apellido, hab.cod_habilitado ,sufrago, pActual.nombre as PaisActual, desc_mesa, m.id_mesa "

				+ "from ucsaws_votante vo join ucsaws_persona per on (vo.id_persona = per.id_persona) "
				// +
				// " join ucsaws_pais pOrigen on (pOrigen.id_pais = per.id_pais_origen) "
				+ " join ucsaws_pais pActual on (pActual.id_pais = per.id_pais_actual) "
				+ " join ucsaws_habilitado hab on (hab.id_habilitado = habilitado) "
				+ " join ucsaws_mesa m on (vo.id_mesa = m.id_mesa) where vo.id_evento= "
				+ VentanaBuscarEvento.evento);
		// + " and sufrago = 0");

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

			String[] fin = { fil.get(0).toString(), String.valueOf(contador),
					fil.get(1).toString(), fil.get(2).toString(),
					fil.get(3).toString(), fil.get(4).toString(),
					fil.get(5).toString(), fil.get(6).toString(),
					fil.get(7).toString(), fil.get(8).toString() };

			// model.ciudades.add(fin);
			int pos = 0;
			Vector<Object> vector = new Vector<Object>();
			while (pos < fin.length) {
				vector.add(fin[pos]);
				pos++;
			}
			ite++;
			data.add(vector);
		}

		// names of columns

		String[] colNames = new String[] { "ID", "Item", "CI", "Nombre",
				"Apellido", "Habilitado?", "Sufrago?", "Lugar de Votación",
				"Mesa N°" };

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

	public void filter(String query) {

		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(
				dm);

		table_1.setRowSorter(tr);

		tr.setRowFilter(RowFilter.regexFilter(query));

	}
}
