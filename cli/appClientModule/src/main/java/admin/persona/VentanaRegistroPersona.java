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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.DateValidator;
import src.main.java.admin.validator.PersonaValidator;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.genero.GeneroDAO;
import src.main.java.dao.nacionalidades.NacionalidadesDAO;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.dao.persona.PersonaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import entity.UcsawsGenero;
import entity.UcsawsNacionalidad;
import entity.UcsawsPais;
import entity.UcsawsPersona;

public class VentanaRegistroPersona extends JFrame implements ActionListener {

    	private static final DecimalFormat formatter = new DecimalFormat( "###,###,###" );
	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar, btnFecha;
	private VentanaRegistroPersona ventanaRegistroPersona;
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
	
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date date = new Date();
	
	private DateValidator dateValidator;
	
	private DefaultTableModel dm;
	private JTextField txtEmail;
	
	JDateChooser FechaNac = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroPersona() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtCI.requestFocus();
			}
		});

		JTextFieldDateEditor editor = (JTextFieldDateEditor) FechaNac.getDateEditor();
	    editor.setEditable(false);
	    FechaNac.setBounds(213, 114, 100, 26);
	    getContentPane().add(FechaNac);
	    
		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar");
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
		botonCancelar.setBounds(838, 264, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		//Image newimg4 = img4.getScaledInstance(32, 32,
			//	java.awt.Image.SCALE_SMOOTH);
		
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

		labelTitulo = new JLabel();
		labelTitulo.setText("NUEVA PERSONA");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(873, 325);
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroPersona.class
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
		getContentPane().add(cmbPaisOrigen);
		AutoCompleteDecorator.decorate(cmbPaisOrigen);

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
		cmbPaisActual.setSelectedIndex(-1);
		getContentPane().add(cmbPaisActual);
		AutoCompleteDecorator.decorate(cmbPaisActual);

		lblGenero = new JLabel();
		lblGenero.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGenero.setText("Genero:");
		lblGenero.setBounds(130, 215, 61, 25);
		getContentPane().add(lblGenero);

		cmbGenero = new JComboBox(recuperarDatosComboBoxGenero());
		cmbGenero.setBounds(213, 217, 340, 20);
		cmbGenero.setSelectedIndex(-1);
		getContentPane().add(cmbGenero);
		AutoCompleteDecorator.decorate(cmbGenero);
		
		cmbNacionalidad = new JComboBox(recuperarDatosComboBoxNacionalidad());
		cmbNacionalidad.setBounds(705, 195, 158, 20);
		cmbNacionalidad.setSelectedIndex(-1);
		getContentPane().add(cmbNacionalidad);
		AutoCompleteDecorator.decorate(cmbNacionalidad);

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
		getContentPane().add(txtNombres);

		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(381, 85, 172, 26);
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
		getContentPane().add(txtEmail);
		
		JLabel lblEmail = new JLabel();
		lblEmail.setText("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(579, 155, 116, 25);
		getContentPane().add(lblEmail);
		//recuperarDatos();

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
			    Integer generoSelected = 0;
			    	if(cmbGenero.getSelectedIndex()!= -1){
				Item item = (Item) cmbGenero.getSelectedItem();
				generoSelected = item.getId();
			    	}
			    	
			    	Integer actualSelected = 0 ;
			    	if(cmbPaisActual.getSelectedIndex()!= -1){
				Item item2 = (Item) cmbPaisActual.getSelectedItem();
				actualSelected = item2.getId();
			    	}
			    	
			    	Integer origenSelected = 0;
			    	if(cmbPaisOrigen.getSelectedIndex()!= -1){
				Item item3 = (Item) cmbPaisOrigen.getSelectedItem();
				origenSelected = item3.getId();
			    	}
			    	
			    	Integer nacionalidadSelected = 0;
			    	if(cmbNacionalidad.getSelectedIndex()!= -1){
				Item item4 = (Item) cmbNacionalidad.getSelectedItem();
				nacionalidadSelected = item4.getId();
			    	}
			    	
				if (!(txtCI.getText().length() == 0)
						&& !(txtCelular.getText().length() == 0)
						&& !(FechaNac.getDate() == null)
						&& !(txtLineaBaja.getText().length() == 0)
						&& !(txtNombres.getText().length() == 0)
						&& !(txtApellidos.getText().length() == 0)
				    		&& !(cmbGenero.getSelectedIndex()== -1)
				    		&& !(cmbPaisActual.getSelectedIndex()== -1)
				    		&& ! (cmbPaisOrigen.getSelectedIndex()== -1)
				    		&& ! (cmbNacionalidad.getSelectedIndex()== -1))
				    {
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

					(personaValidator.ValidarCedula(txtCI.getText(), Integer.parseInt(VentanaBuscarEvento.evento)) == false) {
					    EventoDAO eventoDAO = new EventoDAO();
					    GeneroDAO generoDAO = new GeneroDAO();
					    NacionalidadesDAO nacionalidadesDAO = new NacionalidadesDAO();
					    PaisDAO paisDAO = new PaisDAO();
					    PersonaDAO personaDAO = new  PersonaDAO();
					    
					    
					    UcsawsPersona personaGuardar = new UcsawsPersona();
					    personaGuardar.setNombre(txtNombres.getText().toUpperCase());
					    personaGuardar.setApellido(txtApellidos.getText().toUpperCase());
					    personaGuardar.setCi(new BigDecimal(txtCI.getText()));
					    personaGuardar.setEmail(txtEmail.getText().toLowerCase());
					    personaGuardar.setFechaNacimiento(FechaNac.getDate());
					    personaGuardar.setIdEvento(eventoDAO.obtenerEventoById(VentanaBuscarEvento.evento));
					    personaGuardar.setTelCelular(txtCelular.getText());
					    personaGuardar.setTelLineaBaja(txtLineaBaja.getText());
					    personaGuardar.setUcsawsGenero(generoDAO.buscarGeneroById(generoSelected.toString()));
					    personaGuardar.setUcsawsNacionalidad(nacionalidadesDAO.obtenerNacionalidadById(nacionalidadSelected.toString()));
					    personaGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado);
					    personaGuardar.setUcsawsPaisByIdPaisActual(paisDAO.obtenerPaisById(actualSelected));
					    personaGuardar.setUcsawsPaisByIdPaisOrigen(paisDAO.obtenerPaisById(origenSelected));
					    
					    personaDAO.guardarPersona(personaGuardar);
					    
					    
					    VentanaBuscarPersona lista = new VentanaBuscarPersona();
					    lista.setVisible(true);
					    this.dispose();
					    
					 

							/*model = new PersonaJTableModel();
							recuperarDatos();
							table.setModel(model);
							model.fireTableDataChanged();
							table.removeColumn(table.getColumnModel()
									.getColumn(0));
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
							   txtFechaNac.setText(dateFormat.format(date));
							   
							   
							   cmbGenero.setSelectedIndex(-1);
							   cmbPaisActual.setSelectedIndex(-1);
							   cmbPaisOrigen.setSelectedIndex(-1);
							   cmbNacionalidad.setSelectedIndex(-1);*/
							   
							
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
		if (e.getSource() == botonCancelar) {
			VentanaBuscarPersona persona = new VentanaBuscarPersona();
			persona.setVisible(true);
			this.dispose();

		}
	}

	/*private void recuperarDatos() {
	    JSONArray filas = new JSONArray();
		JSONArray fil = new JSONArray();

		boolean existe = false;

		// Statement estatuto = conex.getConnection().createStatement();

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		
		query.setTipoQueryGenerico(44);

		query.setQueryGenerico(VentanaBuscarEvento.evento);

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();
		
		// json string to List<String>;
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();
		List<UcsawsPersona> lista = new ArrayList<UcsawsPersona>();
		try{
		lista = mapper.readValue(jsonInString,new TypeReference<List<UcsawsPersona>>(){});
		}
		catch(Exception e){
			System.out.println(e);
		}

		if (lista.isEmpty()) {
			JOptionPane.showMessageDialog(null, "algo salio mal",
					"Advertencia", JOptionPane.WARNING_MESSAGE);
			//return lista;
		}

		else {
			obtenerModeloA(table,lista );
			
	    // return lista;
		}



	}*/


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
		   // JOptionPane.showMessageDialog(null, "algo salio mal",
			//    "Advertencia", JOptionPane.WARNING_MESSAGE);
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
		  //  JOptionPane.showMessageDialog(null, "algo salio mal",
			//    "Advertencia", JOptionPane.WARNING_MESSAGE);
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
		    //JOptionPane.showMessageDialog(null, "algo salio mal",
			//    "Advertencia", JOptionPane.WARNING_MESSAGE);
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
			   // JOptionPane.showMessageDialog(null, "algo salio mal",
				//    "Advertencia", JOptionPane.WARNING_MESSAGE);
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
	
	/*public void filter(String query){
	    
	    PersonaJTableModel am = (PersonaJTableModel) table.getModel();
		
	    //dm  = am;
		
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
		
		
		
		table.setRowSorter(tr);
		
	tr.setRowFilter(RowFilter.regexFilter(query));
		
		
	}*/
	
	
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
	
	public AbstractTableModel obtenerModeloA(JTable tabla, List<UcsawsPersona> evento){
		
		 
		Iterator<UcsawsPersona> ite = evento.iterator();

		//private String[] colNames = new String[] 
		//{"ID","Item", "CI.", "Nombre", "Apellido","Fch. Nac.", "Pais Origen", "Pais Actual","Genero","Linea Baja","Celular","E-Mail"}; 

		UcsawsPersona aux;
		Integer cont = 1;
		while (ite.hasNext()) {
			aux = ite.next();

			Object[] row = { aux.getIdPersona(), cont , 
				formatter.format(aux.getCi()) , 
				aux.getNombre(), aux.getApellido(), 
			new SimpleDateFormat("dd-MM-yyyy").format((aux.getFechaNacimiento())),aux.getUcsawsPaisByIdPaisOrigen().getNombre(),
			aux.getUcsawsPaisByIdPaisActual().getNombre(),aux.getUcsawsGenero().getDescripcion() ,aux.getTelLineaBaja(), aux.getTelCelular(), aux.getEmail() };
					


					//new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
					
					
			model.personas.add(row);
			cont++;
		
		
		


		
	}
		return model;
	}
}