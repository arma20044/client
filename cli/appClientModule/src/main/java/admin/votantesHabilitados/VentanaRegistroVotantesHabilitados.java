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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.local.VentanaBuscarLocal;
import src.main.java.admin.validator.VotantesHabilitadosValidator;
import src.main.java.dao.votantesHabilitados.VotantesHabilitadosDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import sun.awt.RepaintArea;

import javax.swing.JCheckBox;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentanaRegistroVotantesHabilitados extends JFrame implements
		ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje, lblMesa;
	private JButton botonGuardar, botonCancelar, btnEliminar;
	private JTable table;

	private VotantesHabilitadosJTableModel model = new VotantesHabilitadosJTableModel();
	private JScrollPane scrollPane;

	private VotantesHabilitadosValidator votantesHabilitadosValidator = new VotantesHabilitadosValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JComboBox cmbPersona, cmbHabilitado ,cmbMesa;
	private JLabel lblHabilitado;
	private JLabel lblZona;
	private JComboBox cmbZona;

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
		btnEliminar.setIcon(new ImageIcon(
				VentanaRegistroVotantesHabilitados.class
						.getResource("/imgs/borrar.png")));
		btnEliminar.setEnabled(true);
		btnEliminar.setBounds(541, 36, 32, 32);
		btnEliminar.setOpaque(false);
		btnEliminar.setContentAreaFilled(false);
		btnEliminar.setBorderPainted(false);
		Image img4 = ((ImageIcon) btnEliminar.getIcon()).getImage();
		Image newimg4 = img4.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnEliminar.setIcon(new ImageIcon(newimg4));

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE VOTANTES HABILITADOS");
		labelTitulo.setBounds(163, 11, 486, 30);
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
		scrollPane.setToolTipText("Lista de Votantes Habilitados");
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroVotantesHabilitados.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		cmbPersona = new JComboBox(recuperarDatosComboBoxPersona());
		cmbPersona.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				//cmbMesa.setVisible(false);
//				if(cmbMesa != null){
//					if(cmbMesa.getModel().getSize() == 0){
//						
//					}
//				}
//				else
				if (cmbZona != null){
					cmbZona.removeAllItems();
					
					
					System.out.println(cmbZona.getItemCount());
					//cmbMesa = null;
					
					//cmbMesa.revalidate();
					//cmbMesa.repaint();
					
					
					
					Item item = (Item) cmbPersona.getSelectedItem();
					Integer personaSelected = item.getId();
					
					Integer pais = PaisDePersonaSeleccionada(personaSelected);
					
					
					//recuperarDatosComboBoxMesaPorPais(pais);
					Vector zonaXPais = recuperarDatosComboBoxZonaPorPais(pais);
					if (zonaXPais.toString().compareTo("[]") != 0){
						cmbZona = new JComboBox(zonaXPais);
						cmbZona.setBounds(209, 112, 289, 20);
						cmbZona.revalidate();
					getContentPane().add(cmbZona);
					System.out.println(cmbZona.getItemCount());
					
					//cmbMesa.repaint();
					
					lblMesa.setVisible(true);
					lblMesa.revalidate();
					
					if (cmbZona.getItemCount() == 0){
						cmbZona.removeAllItems();
						cmbZona.setVisible(false);
						cmbZona.revalidate();
					}
					}
					
				}
				else{
				Item item = (Item) cmbPersona.getSelectedItem();
				Integer personaSelected = item.getId();
				
				Integer pais = PaisDePersonaSeleccionada(personaSelected);
				
				
				//recuperarDatosComboBoxMesaPorPais(pais);
				//cmbMesa.setModel( );
				
				Vector zonaXPais = recuperarDatosComboBoxZonaPorPais(pais);
				
				if (zonaXPais.toString().compareTo("[]") != 0){
					cmbZona = new JComboBox(zonaXPais);
					cmbZona.setBounds(209, 112, 289, 20);
				//cmbMesa.revalidate();
					cmbZona.repaint();
				getContentPane().add(cmbZona);
				
				}
//				lblMesa.setVisible(true);
//				lblMesa.revalidate();
				
				//pais = 0;
				
			}
			}
		});
		
		cmbPersona.setBounds(209, 48, 289, 20);
		getContentPane().add(cmbPersona);

		cmbHabilitado = new JComboBox(recuperarDatosComboBoxHabilitado());
		cmbHabilitado.setBounds(209, 81, 69, 20);
		getContentPane().add(cmbHabilitado);

		JLabel lblPersona = new JLabel();
		lblPersona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPersona.setText("Persona:");
		lblPersona.setBounds(141, 46, 61, 25);
		getContentPane().add(lblPersona);

		lblHabilitado = new JLabel();
		lblHabilitado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHabilitado.setText("Habilitado:");
		lblHabilitado.setBounds(141, 79, 61, 25);
		getContentPane().add(lblHabilitado);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(140, 175, 363, 14);
		getContentPane().add(lblMensaje);
		
		lblMesa = new JLabel();
		lblMesa.setText("Mesa:");
		lblMesa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMesa.setBounds(141, 143, 61, 25);
		lblMesa.setVisible(true);
		getContentPane().add(lblMesa);
		
		lblZona = new JLabel("Zona:");
		lblZona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblZona.setBounds(161, 112, 41, 20);
		getContentPane().add(lblZona);
		
		//cmbZona = new JComboBox();
//		cmbZona.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusLost(FocusEvent arg0) {
//				//cmbMesa.setVisible(false);
////				if(cmbMesa != null){
////					if(cmbMesa.getModel().getSize() == 0){
////						
////					}
////				}
////				else
//				if (cmbMesa != null){
//					cmbMesa.removeAllItems();
//					
//					
//					System.out.println(cmbMesa.getItemCount());
//					//cmbMesa = null;
//					
//					//cmbMesa.revalidate();
//					//cmbMesa.repaint();
//					
//					
//					
//					Item item = (Item) cmbPersona.getSelectedItem();
//					Integer personaSelected = item.getId();
//					
//					Integer pais = PaisDePersonaSeleccionada(personaSelected);
//					
//					
//					//recuperarDatosComboBoxMesaPorPais(pais);
//					Vector mesaXPais = recuperarDatosComboBoxMesaPorPais(pais);
//					if (mesaXPais.toString().compareTo("[]") != 0){
//					cmbMesa = new JComboBox(mesaXPais);
//					cmbMesa.setBounds(209, 143, 289, 20);
//					cmbMesa.revalidate();
//					getContentPane().add(cmbMesa);
//					System.out.println(cmbMesa.getItemCount());
//					
//					//cmbMesa.repaint();
//					
//					lblMesa.setVisible(true);
//					lblMesa.revalidate();
//					
//					if (cmbMesa.getItemCount() == 0){
//						cmbMesa.removeAllItems();
//						cmbMesa.setVisible(false);
//						cmbMesa.revalidate();
//					}
//					}
//					
//				}
//				else{
//				Item item = (Item) cmbPersona.getSelectedItem();
//				Integer personaSelected = item.getId();
//				
//				Integer pais = PaisDePersonaSeleccionada(personaSelected);
//				
//				
//				//recuperarDatosComboBoxMesaPorPais(pais);
//				//cmbMesa.setModel( );
//				
//				Vector mesaXPais = recuperarDatosComboBoxMesaPorPais(pais);
//				
//				if (mesaXPais.toString().compareTo("[]") != 0){
//				cmbMesa = new JComboBox(mesaXPais);
//				cmbMesa.setBounds(209, 143, 289, 20);
//				//cmbMesa.revalidate();
//				cmbMesa.repaint();
//				getContentPane().add(cmbMesa);
//				
//				}
////				lblMesa.setVisible(true);
////				lblMesa.revalidate();
//				
//				//pais = 0;
//				
//			}
//			}
//		});
		//cmbZona.setBounds(209, 112, 164, 20);
		//getContentPane().add(cmbZona);
		
		

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
				
				Item item2 = (Item) cmbHabilitado.getSelectedItem();
				Integer habilitadoSelected = item2.getId();
				
				//if (!(txtCod.getText().length() == 0)) {
					
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
							query.setTipoQueryGenerico(1);
							System.out.println(Login.userLogeado);
							query.setQueryGenerico("INSERT INTO ucsaws_votante"
									+ "( id_votante, id_persona, habilitado,sufrago,  usuario_ins,fch_ins, usuario_upd, fch_upd) "
									+ "VALUES ("
									+ "nextval('ucsaws_votante_seq')"
									+ " , "
									+ personaSelected
									+ ", "
									+ habilitadoSelected
									+ ","
									+ "2"
									+ ", '"
									+ Login.userLogeado
									+ "' , now(), '"
									+ Login.userLogeado
									+ "' , now())");

							QueryGenericoResponse response = weatherClient
									.getQueryGenericoResponse(query);
							weatherClient.printQueryGenericoResponse(response);

							model = new VotantesHabilitadosJTableModel();
							recuperarDatos();
							table.setModel(model);
							model.fireTableDataChanged();
							table.removeColumn(table.getColumnModel()
									.getColumn(0));
							// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
							lblMensaje
									.setText("Excelente, se ha guardado al Votante.");
							Timer t = new Timer(Login.timer,
									new ActionListener() {

										public void actionPerformed(
												ActionEvent e) {
											lblMensaje.setText(null);
										}
									});
							t.setRepeats(false);
							t.start();

							//txtCod.setText("");

							// this.dispose();
						} else {
							// JOptionPane.showMessageDialog(null,
							// "Ya existe el genero " + txtDesc.getText(),
							// "Información",JOptionPane.WARNING_MESSAGE);
							lblMensaje
									.setText("La Persona no puede tener mas de una candidatura");
							Timer t = new Timer(Login.timer,
									new ActionListener() {

										public void actionPerformed(
												ActionEvent e) {
											lblMensaje.setText(null);
										}
									});
							t.setRepeats(false);
							t.start();
						}
				

				//}
			
			
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,
						"Error al intentar insertar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		if (e.getSource() == btnEliminar) {

			if (!codTemporal.equals("")) {

				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar al Votante?",
						"Confirmación", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)

				{
					VotantesHabilitadosDAO votantesHabilitadosDAO = new VotantesHabilitadosDAO();

					try {
						votantesHabilitadosDAO.eliminarVotante(codTemporal);

					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "sfdsfsfsdfs",
								"Información", JOptionPane.WARNING_MESSAGE);
					}
					if (votantesHabilitadosDAO.eliminarVotante(codTemporal) == true) {

						// JOptionPane.showMessageDialog(null,"Excelente, se ha eliminado el genero "
						// + txtDesc.getText());
						// modificarGenero(textCod.getText(),
						// codTemporal.getText());
						// txtId.setText("");
						lblMensaje
								.setText("Excelente, se ha eliminado al Votante ");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
						limpiar();

						model = new VotantesHabilitadosJTableModel();

						recuperarDatos();
						table.setModel(model);

						model.fireTableDataChanged();
						table.removeColumn(table.getColumnModel().getColumn(0));
					}

					else {
						// JOptionPane.showMessageDialog(null,"Existen registros que apuntan al Genero que desea eliminar ","Error",JOptionPane.ERROR_MESSAGE);
						lblMensaje
								.setText("ERROR: Existen registros que apuntan al Votante que desea eliminar ");
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
						.setText("Por favor seleccione que Votante desea Eliminar");
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

		query.setQueryGenerico("SELECT vo.id_votante,ci,per.nombre, apellido,  pActual.nombre as PaisActual, desc_mesa, "
				+ "hab.cod_habilitado habilitado, suf.cod_habilitado sufrago"
				+ " from ucsaws_votante vo join ucsaws_votante_habilitado vh on (vo.id_votante = vh.id_votante)"
				+ "join ucsaws_persona per on (vo.id_persona = per.id_persona)"
				+ "join ucsaws_pais pOrigen on (pOrigen.id_pais = per.id_pais_origen)"
				+ "join ucsaws_pais pActual on (pActual.id_pais = per.id_pais_actual)"
				+ "join ucsaws_habilitado hab on (hab.id_habilitado = habilitado)"
				+ "join ucsaws_habilitado suf on (suf.id_habilitado = sufrago)"
				+ "join ucsaws_mesa m on (vo.id_mesa = m.id_mesa)"
				+ " where per.id_evento= " + VentanaBuscarEvento.evento);


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
					fil.get(6).toString(),fil.get(7).toString() };

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

		query.setQueryGenerico("SELECT id_persona, nombre || ' ' || apellido"
				+ " from ucsaws_persona " + "order by apellido");

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

	private Vector recuperarDatosComboBoxMesa(Integer pais) {
		
		Item item = (Item) cmbPersona.getSelectedItem();
		Integer personaSelected = item.getId();
		
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

		query.setQueryGenerico("SELECT id_mesa, desc_mesa" 
				+ " ucsaws_mesa m join ucsaws_local l on (m.id_local = l.id_local)"
				+ " join ucsaws_zona z on (l.id_zona = z.id_zona)"
				+ " join ucsaws_distrito d on (d.id_distrito = z.id_distrito)"
				+ " join ucsaws_departamento de on (de.id_departamento = d.id_departamento)"
				+ " join ucsaws_votante vo on (vo.id_mesa = m.id_mesa)"
				+ " join ucsaws_persona per on (per.id_persona = vo.id_persona)"
				+ " where id_pais_actual = " + pais );

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
		
		
	 private Integer PaisDePersonaSeleccionada(Integer personaSelected){
		 
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
					+ " from ucsaws_persona where id_persona = " + personaSelected + "order by nombre");

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
}