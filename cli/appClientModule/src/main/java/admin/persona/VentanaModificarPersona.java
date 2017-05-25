package src.main.java.admin.persona;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.DateValidator;
import src.main.java.admin.validator.PersonaValidator;
import src.main.java.dao.genero.GeneroDAO;
import src.main.java.dao.nacionalidades.NacionalidadesDAO;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.dao.persona.PersonaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import com.toedter.calendar.JDateChooser;

import entity.UcsawsGenero;
import entity.UcsawsNacionalidad;
import entity.UcsawsPais;
import entity.UcsawsPersona;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaModificarPersona extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, borrar, btnFecha, botonCancelar;
	private VentanaModificarPersona ventanaRegistroPersona;
	private PersonaJTableModel model = new PersonaJTableModel();

	private PersonaValidator personaValidator = new PersonaValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();

	private JLabel lblPaisActual;
	private JComboBox cmbPaisActual, cmbPaisOrigen;
	private JLabel lblGenero;
	private JComboBox cmbGenero,cmbNacionalidad;
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
	private JLabel lblNacionalidad;
	
	private UcsawsPersona persona;
	
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date date = new Date();
	
	private DateValidator dateValidator;
	
	private DefaultTableModel dm;
	private JTextField txtEmail;
	private JDateChooser fechaNacimiento;
	 

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaModificarPersona(UcsawsPersona p) {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtCI.requestFocus();
			}
		});
		
		persona = p ;

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar Cambios");
		botonGuardar.setIcon(new ImageIcon(VentanaModificarPersona.class
				.getResource("/imgs/save.png")));
		botonGuardar.setBounds(339, 52, 32, 32);
		botonGuardar.setOpaque(false);
		botonGuardar.setContentAreaFilled(false);
		botonGuardar.setBorderPainted(false);
		Image img3 = ((ImageIcon) botonGuardar.getIcon()).getImage();
		Image newimg3 = img3.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonGuardar.setIcon(new ImageIcon(newimg3));
		//Image newimg2 = img2.getScaledInstance(32, 32,
				//java.awt.Image.SCALE_SMOOTH);
		//Image newimg4 = img4.getScaledInstance(32, 32,
			//	java.awt.Image.SCALE_SMOOTH);

		labelTitulo = new JLabel();
		labelTitulo.setText("MODIFICAR PERSONAS\r\n");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(876, 305);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		//recuperarDatos();
		
		
	 	 
		btnHome = new JButton("");
		btnHome.setToolTipText("Inicio");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefinicionesGenerales menuprincipal = new DefinicionesGenerales();
				menuprincipal.setVisible(true);
				dispose();
			}
		});
		btnHome.setIcon(new ImageIcon(VentanaModificarPersona.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		cmbPaisOrigen = new JComboBox(recuperarDatosComboBoxPaisOrigen());
		cmbPaisOrigen.setBounds(213, 151, 340, 20);
		cmbPaisOrigen.setSelectedIndex(-1);
		filtrarComboPaisOrigen(); 
		getContentPane().add(cmbPaisOrigen);

		JLabel lblPaisOrigen = new JLabel();
		lblPaisOrigen.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPaisOrigen.setText("Pais Origen:");
		lblPaisOrigen.setBounds(130, 149, 61, 25);
		getContentPane().add(lblPaisOrigen);

		lblPaisActual = new JLabel();
		lblPaisActual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPaisActual.setText("PaisActual:");
		lblPaisActual.setBounds(101, 182, 90, 25);
		getContentPane().add(lblPaisActual);

		cmbPaisActual = new JComboBox(recuperarDatosComboBoxPaisActual());
		cmbPaisActual.setBounds(213, 184, 340, 20);
		filtrarComboPaisActual();	 
		getContentPane().add(cmbPaisActual);

		lblGenero = new JLabel();
		lblGenero.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGenero.setText("Genero:");
		lblGenero.setBounds(130, 215, 61, 25);
		getContentPane().add(lblGenero);

		cmbGenero = new JComboBox(recuperarDatosComboBoxGenero());
		cmbGenero.setBounds(213, 217, 340, 20);
		filtrarComboGenero();		 
		getContentPane().add(cmbGenero);
		
		cmbNacionalidad = new JComboBox(recuperarDatosComboBoxNacionalidad());
		cmbNacionalidad.setBounds(705, 195, 158, 20);
		filtrarComboNacionalidad();	 
		getContentPane().add(cmbNacionalidad);

		lblCI = new JLabel();
		lblCI.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCI.setText("C.I. N°:");
		lblCI.setBounds(130, 52, 61, 25);
		getContentPane().add(lblCI);

		txtCI = new JTextField();
		txtCI.setEditable(false);
		txtCI.setText(persona.getCi().toString());
		txtCI.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtCI.setBounds(213, 54, 108, 26);
		getContentPane().add(txtCI);
		txtCI.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(263, 247, 619, 14);
		getContentPane().add(lblMensaje);

		lblNombreApellido = new JLabel();
		lblNombreApellido.setText("Nombre(s) y Apellido(s):");
		lblNombreApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombreApellido.setBounds(75, 83, 116, 25);
		getContentPane().add(lblNombreApellido);

		txtNombres = new JTextField();
		txtNombres.setColumns(10);
		txtNombres.setBounds(213, 85, 158, 26);
		txtNombres.setText(persona.getNombre());
		getContentPane().add(txtNombres);

		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(381, 85, 172, 26);
		txtApellidos.setText(persona.getApellido());
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
		lblLineaBaja.setBounds(579, 119, 116, 25);
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
		txtCelular.setBounds(705, 85, 158, 26);
		txtCelular.setText(persona.getTelCelular());
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
		txtLineaBaja.setBounds(705, 121, 158, 26);
		txtLineaBaja.setText(persona.getTelLineaBaja());
		getContentPane().add(txtLineaBaja);
		Image img5 = ((ImageIcon) botonGuardar.getIcon()).getImage();
		Image newimg5 = img5.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		
		lblNacionalidad = new JLabel();
		lblNacionalidad.setText("Nacionalidad:");
		lblNacionalidad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNacionalidad.setBounds(579, 193, 116, 25);
		getContentPane().add(lblNacionalidad);
		
		txtEmail = new JTextField();
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(!(txtEmail.getText().compareTo("-")==0)){
				if (isValidEmailAddress(txtEmail.getText())==false){
					lblMensaje
					.setText("Ingrese formato de Email correcto.");
			Timer t = new Timer(Login.timer,
					new ActionListener() {

						public void actionPerformed(
								ActionEvent e) {
							lblMensaje.setText(null);
						}
					});
			t.setRepeats(false);
			t.start();
			
			txtEmail.requestFocus();
			txtEmail.selectAll();
				}
				}
			}
		});
		txtEmail.setColumns(10);
		txtEmail.setBounds(705, 157, 158, 26);
		txtEmail.setText(persona.getEmail());
		getContentPane().add(txtEmail);
		
		JLabel lblEmail = new JLabel();
		lblEmail.setText("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(579, 155, 116, 25);
		getContentPane().add(lblEmail);
		
		fechaNacimiento = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		fechaNacimiento.setBounds(213, 117, 100, 26);
		fechaNacimiento.setDate(persona.getFechaNacimiento());
		getContentPane().add(fechaNacimiento);
		
		botonCancelar = new JButton("");
		botonCancelar.setIcon(new ImageIcon(VentanaModificarPersona.class.getResource("/imgs/back2.png")));
		botonCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			    VentanaBuscarPersona p = new VentanaBuscarPersona();
			    p.setVisible(true);
			    dispose();
			}
		});
		botonCancelar.setBounds(838, 244, 32, 32);
		getContentPane().add(botonCancelar);
		Image img4 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg4 = img4.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg4));
		botonCancelar.setBorderPainted(false);
		botonCancelar.setContentAreaFilled(false);
		
		
		 
		//recuperarDatos();
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"clickButton");

		getRootPane().getActionMap().put("clickButton",new AbstractAction(){
			        public void actionPerformed(ActionEvent ae)
			        {
			    botonGuardar.doClick();
			    System.out.println("button clicked");
			        }
			    });
		
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"clickButtonescape");

		getRootPane().getActionMap().put("clickButtonescape",new AbstractAction(){
			        public void actionPerformed(ActionEvent ae)
			        {
			    botonCancelar.doClick();
			    System.out.println("button esc clicked");
			        }
			    });

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
				
				Item item4 = (Item) cmbNacionalidad.getSelectedItem();
				Integer nacionalidadSelected = item4.getId();
				
				if (!(txtCI.getText().length() == 0)
						&& !(txtCelular.getText().length() == 0)
						&& !(fechaNacimiento.getDate()  == null)
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
					} else{
					    
					    //setear los campos a guardar update
					    GeneroDAO generoDAO = new GeneroDAO();
					    NacionalidadesDAO nacionalidadesDAO = new NacionalidadesDAO();
					    PaisDAO paisDAO = new PaisDAO();
					    PersonaDAO personaDAO = new  PersonaDAO();
					    
					    persona.setNombre(txtNombres.getText().toUpperCase());
					    persona.setApellido(txtApellidos.getText().toUpperCase());
					    persona.setCi(new BigDecimal(txtCI.getText()));
					    persona.setEmail(txtEmail.getText().toLowerCase());
					    persona.setFechaNacimiento(fechaNacimiento.getDate());
					    persona.setTelCelular(txtCelular.getText());
					    persona.setTelLineaBaja(txtLineaBaja.getText());
					    persona.setUcsawsGenero(generoDAO.buscarGeneroById(generoSelected.toString()));
					    persona.setUcsawsNacionalidad(nacionalidadesDAO.obtenerNacionalidadById(nacionalidadSelected.toString()));
					    persona.setUcsawsPaisByIdPaisActual(paisDAO.obtenerPaisById(actualSelected));
					    persona.setUcsawsPaisByIdPaisOrigen(paisDAO.obtenerPaisById(origenSelected));
					    persona.setFchUpd(new Date());
					    persona.setUsuarioUpd(Login.nombreApellidoUserLogeado.toUpperCase());
					    
					    personaDAO.actualizarPersona(persona);
	 

							//model = new PersonaJTableModel();
							//recuperarDatos();
							//table.setModel(dm);
							//model.fireTableDataChanged();
							//table.removeColumn(table.getColumnModel()
							//		.getColumn(0));
							// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
							lblMensaje
									.setText("Excelente, se ha guardado la Persona.");
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
							//   txtFechaNac.setText(dateFormat.format(date));
							   
							   
							   cmbGenero.setSelectedIndex(-1);
							   cmbPaisActual.setSelectedIndex(-1);
							   cmbPaisOrigen.setSelectedIndex(-1);
							   cmbNacionalidad.setSelectedIndex(-1);
							   
							   VentanaBuscarPersona mostrar = new VentanaBuscarPersona();
							   mostrar.setVisible(true);
							   dispose();
							   
							
							// this.dispose();
						 
					/*} else {
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
					}*/
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
		if (e.getSource() == borrar) {
			VentanaBuscarPersona persona = new VentanaBuscarPersona();
			persona.setVisible(true);
			this.dispose();

		}
	}

 

 

	private Vector recuperarDatosComboBoxPaisActual() {
	    Vector model = new Vector();

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(45);

		query.setQueryGenerico(VentanaBuscarEvento.evento);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();

		// json string to List<String>;
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();
		List<UcsawsPais> lista = new ArrayList<UcsawsPais>();
		try {
		    lista = mapper.readValue(jsonInString,
			    new TypeReference<List<UcsawsPais>>() {
			    });
		} catch (Exception e) {
		    System.out.println(e);
		}

		if (lista.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "algo salio mal",
			    "Advertencia", JOptionPane.WARNING_MESSAGE);
		    // return lista;
		}

		else {

		    // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		    Iterator<UcsawsPais> ite = lista.iterator();

		    UcsawsPais aux;

		    while (ite.hasNext()) {
			aux = ite.next();

			model.addElement(new Item(aux.getIdPais(), aux.getNombre()));

		    }
		    // return model;
		}

		return model;
	}

	private Vector recuperarDatosComboBoxGenero() {
	    Vector model = new Vector();

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();
		
		//parseo json
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		 
			try{
			jsonStr = mapperObj.writeValueAsString(VentanaBuscarEvento.evento);
			}
			catch(Exception e){
			    System.out.println(e);
			}

		query.setTipoQueryGenerico(39);

		query.setQueryGenerico(jsonStr);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();

		// json string to List<String>;
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();
		List<UcsawsGenero> genero = new ArrayList<UcsawsGenero>();
		try {
		    genero = mapper.readValue(jsonInString,
			    new TypeReference<List<UcsawsGenero>>() {
			    });
		} catch (Exception e) {
		    System.out.println(e);
		}

		if (genero.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "algo salio mal",
			    "Advertencia", JOptionPane.WARNING_MESSAGE);
		    // return lista;
		}

		else {

		    // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		    Iterator<UcsawsGenero> ite = genero.iterator();

		    UcsawsGenero aux;

		    while (ite.hasNext()) {
			aux = ite.next();

			model.addElement(new Item(aux.getIdGenero(), aux.getDescripcion()));

		    }
		    // return model;
		}

		return model;

	}
	
	private Vector recuperarDatosComboBoxNacionalidad() {
	    Vector model = new Vector();

			ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
		 

			query.setTipoQueryGenerico(46);

			query.setQueryGenerico(VentanaBuscarEvento.evento);

			QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);

			String res = response.getQueryGenericoResponse();

			// json string to List<String>;
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = response.getQueryGenericoResponse();
			List<UcsawsNacionalidad> nacionalidad = new ArrayList<UcsawsNacionalidad>();
			try {
			    nacionalidad = mapper.readValue(jsonInString,
				    new TypeReference<List<UcsawsNacionalidad>>() {
				    });
			} catch (Exception e) {
			    System.out.println(e);
			}

			if (nacionalidad.isEmpty()) {
			    JOptionPane.showMessageDialog(null, "algo salio mal",
				    "Advertencia", JOptionPane.WARNING_MESSAGE);
			    // return lista;
			}

			else {

			    // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
			    Iterator<UcsawsNacionalidad> ite = nacionalidad.iterator();

			    UcsawsNacionalidad aux;

			    while (ite.hasNext()) {
				aux = ite.next();

				model.addElement(new Item(aux.getIdNacionalidad(), aux.getDescNacionalidad()));

			    }
			    // return model;
			}

			return model;

	}
	
 
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
	
	private Vector recuperarDatosComboBoxPaisOrigen() {
		Vector model = new Vector();

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		query.setTipoQueryGenerico(45);

		query.setQueryGenerico(VentanaBuscarEvento.evento);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();

		// json string to List<String>;
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();
		List<UcsawsPais> lista = new ArrayList<UcsawsPais>();
		try {
		    lista = mapper.readValue(jsonInString,
			    new TypeReference<List<UcsawsPais>>() {
			    });
		} catch (Exception e) {
		    System.out.println(e);
		}

		if (lista.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "algo salio mal",
			    "Advertencia", JOptionPane.WARNING_MESSAGE);
		    // return lista;
		}

		else {

		    // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		    Iterator<UcsawsPais> ite = lista.iterator();

		    UcsawsPais aux;

		    while (ite.hasNext()) {
			aux = ite.next();

			model.addElement(new Item(aux.getIdPais(), aux.getNombre()));

		    }
		    // return model;
		}

		return model;

	}
	
 
	    // filtrar pais origen
	    public void filtrarComboPaisOrigen() {
		DefaultComboBoxModel dtm = (DefaultComboBoxModel) cmbPaisOrigen.getModel();

		// System.out.println(dtm.getSize());
		int cont = 0;
		Boolean finded = false;
		int tamanho = dtm.getSize();
		while (cont < dtm.getSize()) {
		    if (dtm.getElementAt(cont).toString()
			    .compareTo(persona.getUcsawsPaisByIdPaisOrigen().getNombre()) == 0) {
			// Item item = (Item) dtm.getElementAt(cont);
			// Integer tipoListaSelected = item.getId();
			cmbPaisOrigen.setSelectedIndex(cont);
			finded = true;
			break;
		    }

		    cont++;

		    // System.out.println(dtm.getElementAt(0));
		}
		if (finded == false) {
		    String a = persona.getUcsawsPaisByIdPaisOrigen().getNombre();
		    cmbPaisOrigen.addItem(a);
		    cmbPaisOrigen.setSelectedIndex(tamanho);
		}
	    }

	    
	    // filtrar pais actual
	    public void filtrarComboPaisActual() {
		DefaultComboBoxModel dtm = (DefaultComboBoxModel) cmbPaisActual.getModel();

		// System.out.println(dtm.getSize());
		int cont = 0;
		Boolean finded = false;
		int tamanho = dtm.getSize();
		while (cont < dtm.getSize()) {
		    if (dtm.getElementAt(cont).toString()
			    .compareTo(persona.getUcsawsPaisByIdPaisActual().getNombre()) == 0) {
			// Item item = (Item) dtm.getElementAt(cont);
			// Integer tipoListaSelected = item.getId();
			cmbPaisActual.setSelectedIndex(cont);
			finded = true;
			break;
		    }

		    cont++;

		    // System.out.println(dtm.getElementAt(0));
		}
		if (finded == false) {
		    String a = persona.getUcsawsPaisByIdPaisOrigen().getNombre();
		    cmbPaisActual.addItem(a);
		    cmbPaisActual.setSelectedIndex(tamanho);
		}
	    }
	    
	    // filtrar genero
	    public void filtrarComboGenero() {
		DefaultComboBoxModel dtm = (DefaultComboBoxModel) cmbGenero.getModel();

		// System.out.println(dtm.getSize());
		int cont = 0;
		Boolean finded = false;
		int tamanho = dtm.getSize();
		while (cont < dtm.getSize()) {
		    if (dtm.getElementAt(cont).toString()
			    .compareTo(persona.getUcsawsGenero().getDescripcion()) == 0) {
			// Item item = (Item) dtm.getElementAt(cont);
			// Integer tipoListaSelected = item.getId();
			cmbGenero.setSelectedIndex(cont);
			finded = true;
			break;
		    }

		    cont++;

		    // System.out.println(dtm.getElementAt(0));
		}
		if (finded == false) {
		    String a = persona.getUcsawsGenero().getDescripcion();
		    cmbGenero.addItem(a);
		    cmbGenero.setSelectedIndex(tamanho);
		}
	    }
	    
	    
	    // filtrar nacionalidad
	    public void filtrarComboNacionalidad() {
		DefaultComboBoxModel dtm = (DefaultComboBoxModel) cmbNacionalidad.getModel();

		// System.out.println(dtm.getSize());
		int cont = 0;
		Boolean finded = false;
		int tamanho = dtm.getSize();
		while (cont < dtm.getSize()) {
		    if (dtm.getElementAt(cont).toString()
			    .compareTo(persona.getUcsawsNacionalidad().getDescNacionalidad()) == 0) {
			// Item item = (Item) dtm.getElementAt(cont);
			// Integer tipoListaSelected = item.getId();
			cmbNacionalidad.setSelectedIndex(cont);
			finded = true;
			break;
		    }

		    cont++;

		    // System.out.println(dtm.getElementAt(0));
		}
		if (finded == false) {
		    String a = persona.getUcsawsNacionalidad().getDescNacionalidad();
		    cmbNacionalidad.addItem(a);
		    cmbNacionalidad.setSelectedIndex(tamanho);
		}
	    }
}