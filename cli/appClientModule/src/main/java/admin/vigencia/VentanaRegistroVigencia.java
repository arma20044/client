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
	
	
	String fechaDesde, fechaHasta;
	
	
	
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;
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
		
		recuperarDatosFechaRangoDelEvento();

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
		//Image newimg4 = img4.getScaledInstance(32, 32,
			//	java.awt.Image.SCALE_SMOOTH);

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE VIGENCIA POR PAIS");
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
		txtDesde.setText(fechaDesde);
		txtDesde.setBounds(164, 98, 103, 23);
		getContentPane().add(txtDesde);
		txtDesde.setEditable(false);
		
		JLabel lblhasta = new JLabel();
		lblhasta.setText("Fch. Hasta:");
		lblhasta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblhasta.setBounds(26, 131, 116, 25);
		getContentPane().add(lblhasta);
		
		txtHasta = new JFormattedTextField();
		txtHasta.setText(fechaDesde);
		txtHasta.setBounds(164, 133, 103, 23);
		getContentPane().add(txtHasta);
		txtHasta.setEditable(false);
		
		txtHoraDesde = new JTextField();
		txtHoraDesde.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtHoraDesde.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Integer hora = Integer.parseInt(txtHoraDesde.getText());
				if ( hora > 23){
					txtHoraDesde.setText("23");
				}
				else
					if (hora < 1){
						txtHoraDesde.setText("01");
					}
				
				
				if (txtHoraDesde.getText().length() == 1)
				{
					txtHoraDesde.setText(0 + txtHoraDesde.getText() );
				}
			}
		});
		txtHoraDesde.setBounds(277, 96, 32, 23);
		getContentPane().add(txtHoraDesde);
		txtHoraDesde.setColumns(10);
		
		txtMinutoDesde = new JTextField();
		txtMinutoDesde.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				if ((car < '0' || car > '9'))
					e.consume();
			}
		});
		txtMinutoDesde.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				Integer min = Integer.parseInt(txtMinutoDesde.getText());
				if ( min > 59){
					txtMinutoDesde.setText("59");
				}
				else
					if (min < 0 ){
						txtMinutoDesde.setText("00");
					}
				
				if (txtMinutoDesde.getText().length() == 1)
				{
					txtMinutoDesde.setText(0 + txtMinutoDesde.getText() );
				}
			}
		});
		txtMinutoDesde.setColumns(10);
		txtMinutoDesde.setBounds(319, 96, 32, 23);
		getContentPane().add(txtMinutoDesde);
		
		txtHoraHasta = new JTextField();
		txtHoraHasta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				if ((car < '0' || car > '9'))
					e.consume();
			}
		});
		txtHoraHasta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				Integer hora = Integer.parseInt(txtHoraHasta.getText());
				if ( hora > 23){
					txtHoraHasta.setText("23");
				}
				else
					if (hora < 1){
						txtHoraHasta.setText("01");
					}
				
				if (txtHoraHasta.getText().length() == 1)
				{
					txtHoraHasta.setText(0 + txtHoraHasta.getText() );
				}
			}
		});
		txtHoraHasta.setColumns(10);
		txtHoraHasta.setBounds(277, 132, 32, 23);
		getContentPane().add(txtHoraHasta);
		
		txtMinutoHasta = new JTextField();
		txtMinutoHasta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				if ((car < '0' || car > '9'))
					e.consume();
			}
		});
		txtMinutoHasta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				Integer min = Integer.parseInt(txtMinutoHasta.getText());
				if ( min > 59){
					txtMinutoHasta.setText("59");
				}
				else
					if (min < 0 ){
						txtMinutoHasta.setText("00");
					}
				
				if (txtMinutoHasta.getText().length() == 1)
				{
					txtMinutoHasta.setText(0 + txtMinutoHasta.getText() );
				}
			}
		});
		txtMinutoHasta.setColumns(10);
		txtMinutoHasta.setBounds(319, 131, 32, 23);
		getContentPane().add(txtMinutoHasta);
		
		JLabel lblNewLabel = new JLabel(":");
		lblNewLabel.setBounds(311, 96, 18, 20);
		getContentPane().add(lblNewLabel);
		
		label = new JLabel(":");
		label.setBounds(311, 132, 18, 20);
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
							
							String horaDesde = armarHoraMinuto(txtHoraDesde.getText(), txtMinutoDesde.getText());
							String fechaDesdeFinal = txtDesde.getText() + " " + horaDesde;
							
							String horaHasta = armarHoraMinuto(txtHoraHasta.getText(), txtMinutoHasta.getText());
							String fechaHastaFinal = txtHasta.getText() + " " + horaHasta; 

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
									+ distritoSelected + "' , '"+ fechaDesdeFinal+
									"','" + fechaHastaFinal + "'," + VentanaBuscarEvento.evento + " ,'"
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
						lblMensaje.setText("Ya existe la vigencia para: " + cmbPais.getSelectedItem().toString());
						
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

		query.setQueryGenerico("SELECT  id_vigencia, codigo,nombre,to_char(fch_vigencia_desde, 'DD/MM/YYYY HH24:MI') as desde"
				+ ", to_char(fch_vigencia_hasta, 'DD/MM/YYYY HH24:MI') as hasta "
				+ "from  ucsaws_vigencia_horario_x_pais v join ucsaws_pais p on (v.id_pais = p.id_pais)"
				+ " where v.id_evento = " + VentanaBuscarEvento.evento
				+ " order by nombre" + "");

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

		query.setQueryGenerico("SELECT id_pais, nombre" + " from ucsaws_pais where id_evento = " 
				+ VentanaBuscarEvento.evento
				+ " order by nombre");

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
	
	private void recuperarDatosFechaRangoDelEvento() {
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

		query.setQueryGenerico("SELECT to_char(fch_desde, 'dd/MM/yyyy') as desde,  to_char(fch_hasta, 'dd/MM/yyyy') as hasta" + " from ucsaws_evento "
				+ " where id_evento = " + VentanaBuscarEvento.evento
				);

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

			fechaDesde =  fil.get(0).toString();
			
			fechaHasta = fil.get(1).toString();
			
			ite++;
		}
		//return model;

	}
	public String armarHoraMinuto(String hora, String minuto){
		
		
		String result = "";
		
		result = hora + ":" + minuto;
		
		
		
		return result;
		
	}
	
}