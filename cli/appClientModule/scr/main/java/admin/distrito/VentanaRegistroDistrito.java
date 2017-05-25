package scr.main.java.admin.distrito;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.departamento.VentanaBuscarDepartamento;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.validator.DistritoValidator;
import src.main.java.dao.departamento.DepartamentoDAO;
import src.main.java.dao.distrito.DistritoDAO;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.login.Login;
import entity.UcsawsDistrito;

public class VentanaRegistroDistrito extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;

	private DistritoJTableModel model = new DistritoJTableModel();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JLabel lblNroZona;
	private JTextField txtNro;
	private JTextField txtDescripcion;

	private DistritoValidator distritoValidator = new DistritoValidator();

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroDistrito() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtNro.requestFocus();
			}
		});

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroDistrito.class
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
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroDistrito.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(579, 134, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		//Image newimg4 = img4.getScaledInstance(32, 32,
		//		java.awt.Image.SCALE_SMOOTH);

		labelTitulo = new JLabel();
		labelTitulo.setText("NUEVO DISTRITO");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(614, 192);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		btnHome = new JButton("");
		btnHome.setToolTipText("Inicio");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefinicionesGenerales menuprincipal = new DefinicionesGenerales();
				menuprincipal.setVisible(true);
				dispose();
			}
		});
		btnHome.setIcon(new ImageIcon(VentanaRegistroDistrito.class
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
		lblNroZona.setBounds(142, 59, 61, 25);
		getContentPane().add(lblNroZona);

		txtNro = new JTextField();
		txtNro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				if((car<'0' || car>'9')) e.consume();
			}
		});
		txtNro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtNro.getText().length() == 1)
				{
					txtNro.setText(0 + txtNro.getText() );
				}
			}
		});
		txtNro.setBounds(213, 54, 75, 26);
		getContentPane().add(txtNro);
		txtNro.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(23, 122, 575, 14);
		getContentPane().add(lblMensaje);

		JLabel lblDescripcionZona = new JLabel();
		lblDescripcionZona.setText("Descripcion:");
		lblDescripcionZona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcionZona.setBounds(114, 86, 89, 25);
		getContentPane().add(lblDescripcionZona);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(213, 85, 309, 26);
		getContentPane().add(txtDescripcion);
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

				
				if (!(txtNro.getText().length() == 0)) {
					if (txtNro.getText().length() > 3) {
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

					(distritoValidator.ValidarCodigo(txtNro.getText(),
							txtDescripcion.getText(), VentanaBuscarDepartamento.departamentoSeleccionado) == false) {
						 
					    EventoDAO evento = new EventoDAO();
					    DepartamentoDAO departamentoDAO = new DepartamentoDAO();
					    DistritoDAO distritoDAO = new DistritoDAO();
					    
					    UcsawsDistrito distritoAGuardar = new UcsawsDistrito();
					    distritoAGuardar.setNroDistrito(txtNro.getText());
					    distritoAGuardar.setDescDistrito(txtDescripcion.getText().toUpperCase());
					    distritoAGuardar.setFchIns(new Date());
					    distritoAGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado.toUpperCase());
					    distritoAGuardar.setIdEvento(evento.obtenerEventoById(VentanaBuscarEvento.evento));
					    distritoAGuardar.setUcsawsDepartamento(departamentoDAO.obtenerDepartamentoByID(Integer.parseInt(VentanaBuscarDepartamento.departamentoSeleccionado)));
					    
					    try{
					    distritoDAO.guardarDistrito(distritoAGuardar);
					    }
					    catch(Exception ex){
						System.out
							.println(ex);
					    }
					    
					    finally{
						VentanaBuscarDistrito distrito = new VentanaBuscarDistrito();
						distrito.setVisible(true);
						dispose();
					    }
					    
					    
					    
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje.setText("Ya existe el Distrito. "
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
			VentanaBuscarDistrito candidato = new VentanaBuscarDistrito();
			candidato.setVisible(true);
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
		query.setTipoQueryGenerico(2);

		query.setQueryGenerico("SELECT id_distrito, nro_distrito,desc_distrito,nro_departamento, desc_departamento  "
				+ " from ucsaws_distrito di join ucsaws_departamento de on (di.id_departamento = de.id_departamento)"
				+ " where di.id_evento =" + VentanaBuscarEvento.evento
				+ " and di.id_departamento =" + VentanaBuscarDepartamento.departamentoSeleccionado
				+ " order by nro_departamento, nro_distrito" + "");

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
			contador = contador + 1  ;
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), String.valueOf(contador),fil.get(1).toString(),
					fil.get(2).toString(), fil.get(3).toString(),
					fil.get(4).toString() };

			model.ciudades.add(fin);
			ite++;
		}

	}*/



}