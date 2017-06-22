package src.main.java.admin.nacionalidad;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

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

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.validator.NacionalidadValidator;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.nacionalidades.NacionalidadesDAO;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import entity.UcsawsEvento;
import entity.UcsawsNacionalidad;
import entity.UcsawsPais;

public class VentanaRegistroNacionalidad extends JFrame implements
		ActionListener {
    
    	public PaisDAO paisDAO = new PaisDAO();
    	public EventoDAO eventoDAO = new EventoDAO();

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;

	private NacionalidadJTableModel model = new NacionalidadJTableModel();

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
		botonGuardar.setToolTipText("Guardar");
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
		botonCancelar.setBounds(669, 158, 32, 32);
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
		labelTitulo.setText("NUEVA NACIONALIDAD");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(703, 214);
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
		cmbPais.setSelectedIndex(-1);
		getContentPane().add(cmbPais);
		AutoCompleteDecorator.decorate(cmbPais);

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
		lblMensaje.setBounds(202, 150, 363, 14);
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
				Integer paisSelected =null;
				if (!(cmbPais.getSelectedIndex()== -1)){
				Item item3 = (Item) cmbPais.getSelectedItem();
				paisSelected = item3.getId();}
				
				if ((txtCod.getText().length() > 0) && (txtDescripcion.getText().length() > 0) && cmbPais.getSelectedIndex() != -1) {
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
							txtDescripcion.getText(), VentanaBuscarEvento.evento) == false) {
					    	
					    
						if (nacionalidadValidator.ValidarPais(paisSelected, VentanaBuscarEvento.evento) == false) {
						    // obtener Objeto Pais
						    UcsawsPais pais = paisDAO.obtenerPaisById(paisSelected);
						    UcsawsEvento evento = eventoDAO.obtenerEventoById(VentanaBuscarEvento.evento);
						    UcsawsNacionalidad nacionalidadAGuardar = new UcsawsNacionalidad();
						    
						    nacionalidadAGuardar.setCodNacionalidad(txtCod.getText().toUpperCase());
						    nacionalidadAGuardar.setDescNacionalidad(txtDescripcion.getText().toUpperCase());
						    nacionalidadAGuardar.setIdEvento(evento);
						    nacionalidadAGuardar.setUcsawsPais(pais);
						    nacionalidadAGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado);
						    
						    
						    NacionalidadesDAO nacionalidadesDAO = new NacionalidadesDAO();
						    nacionalidadesDAO.guardarNacionalidad(nacionalidadAGuardar);
						    

						    VentanaBuscarNacionalidad buscar = new VentanaBuscarNacionalidad();
						    buscar.setVisible(true);
						    dispose(); 

						 
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
			    
			    System.out.println(ex);
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

	/*private void recuperarDatos() {
	    JSONArray filas = new JSONArray();
		JSONArray fil = new JSONArray();

		boolean existe = false;

		// Statement estatuto = conex.getConnection().createStatement();

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		// para registrar se inserta el codigo es 1
		query.setTipoQueryGenerico(46);

		query.setQueryGenerico(VentanaBuscarEvento.evento);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();

		// json string to List<String>;
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();
		List<UcsawsNacionalidad> lista = new ArrayList<UcsawsNacionalidad>();
		try {
		    lista = mapper.readValue(jsonInString,
			    new TypeReference<List<UcsawsNacionalidad>>() {
			    });
		} catch (Exception e) {
		    System.out.println(e);
		}

		if (lista.isEmpty()) {
		    // JOptionPane.showMessageDialog(null, "algo salio mal",
		    // "Advertencia", JOptionPane.WARNING_MESSAGE);
		    // return lista;
		}

		else {
		    obtenerModeloA(table, lista);

		    // return lista;
		}



	}*/

	public Vector recuperarDatosComboBoxPaisActual() {
		Vector model = new Vector();

		ApplicationContext ctx = SpringApplication
			.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		//query.setTipoQueryGenerico(45);
		query.setTipoQueryGenerico(57);

		query.setQueryGenerico(VentanaBuscarEvento.evento);

		QueryGenericoResponse response = weatherClient
			.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();

		// json string to List<String>;
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();
		List<UcsawsPais> pais = new ArrayList<UcsawsPais>();
		try {
		    pais = mapper.readValue(jsonInString,
			    new TypeReference<List<UcsawsPais>>() {
			    });
		} catch (Exception e) {
		    System.out.println(e);
		}

		if (pais.isEmpty()) {
		    //JOptionPane.showMessageDialog(null, "algo salio mal",
			//    "Advertencia", JOptionPane.WARNING_MESSAGE);
		    // return lista;
		    System.out.println("Ya se agregaron todas las nacionalidades");
		}

		else {

		    // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		    Iterator<UcsawsPais> ite = pais.iterator();

		    UcsawsPais aux;

		    while (ite.hasNext()) {
			aux = ite.next();

			model.addElement(new Item(aux.getIdPais(), aux
				.getNombre()));

		    }
		    // return model;
		}

		return model;

	}
	
	 public AbstractTableModel obtenerModeloA(JTable tabla,
		    List<UcsawsNacionalidad> nacionalidad) {

		// AbstractTableModel model = (DefaultTableModel) tabla.getModel();
		Iterator<UcsawsNacionalidad> ite = nacionalidad.iterator();

		// String header[] = new String[] { "ID","Item","Nro.",
		// "Desc. Evento","Inicio","Fin","Desc. Tipo Evento" };
		// model.setColumnIdentifiers(header);

		UcsawsNacionalidad aux;
		Integer cont = 1;
		while (ite.hasNext()) {
		    aux = ite.next();
		   // "ID", "Itema", "Cod. Nacionalidad", "Desc. Nacionalidad","Pais"}

		    Object[] row = { aux.getIdNacionalidad(), cont, aux.getCodNacionalidad(),
			    aux.getDescNacionalidad(), aux.getUcsawsPais().getNombre() };

		    // new
		    // SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
		    model.nacionalidad.add(row);

		    cont++;

		}

		return model;
	    }
}