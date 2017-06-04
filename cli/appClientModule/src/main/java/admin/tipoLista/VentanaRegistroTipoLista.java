package src.main.java.admin.tipoLista;

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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsTipoEvento;
import entity.UcsawsTipoLista;
import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.tipoEvento.TipoEventoJTableModel;
import src.main.java.admin.tipoEvento.VentanaBuscarTipoEvento;
import src.main.java.admin.validator.TipoListaValidator;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.tipoLista.TipoListaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaRegistroTipoLista extends JFrame implements
		ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;

	private TipoListaJTableModel model = new TipoListaJTableModel();

	private TipoListaValidator tipoListaValidator = new TipoListaValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JLabel lblNroZona;
	private JTextField txtCodigoTipoLista;
	private JTextField txtDescripcionTipoLista;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroTipoLista() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtCodigoTipoLista.requestFocus();
			}
		});

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroTipoLista.class
				.getResource("/imgs/save.png")));
		botonGuardar.setBounds(298, 45, 32, 32);
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
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroTipoLista.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(674, 142, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		

		labelTitulo = new JLabel();
		labelTitulo.setText("NUEVA TIPO LISTA");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(710, 208);
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroTipoLista.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		lblNroZona = new JLabel();
		lblNroZona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNroZona.setText("Codigo:");
		lblNroZona.setBounds(130, 52, 61, 25);
		getContentPane().add(lblNroZona);

		txtCodigoTipoLista = new JTextField();
		txtCodigoTipoLista.setToolTipText("Solo Letras");
		txtCodigoTipoLista.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z'))
					arg0.consume();
			}
		});

		txtCodigoTipoLista.setBounds(213, 45, 75, 26);
		getContentPane().add(txtCodigoTipoLista);
		txtCodigoTipoLista.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(213, 119, 454, 14);
		getContentPane().add(lblMensaje);

		JLabel lblDescripcionZona = new JLabel();
		lblDescripcionZona.setText("Descripcion:");
		lblDescripcionZona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcionZona.setBounds(102, 82, 89, 25);
		getContentPane().add(lblDescripcionZona);

		txtDescripcionTipoLista = new JTextField();
		txtDescripcionTipoLista.setColumns(10);
		txtDescripcionTipoLista.setBounds(213, 82, 310, 26);
		getContentPane().add(txtDescripcionTipoLista);
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

				if (!(txtCodigoTipoLista.getText().length() == 0)
						&& !(txtDescripcionTipoLista.getText().length() == 0)) {
					if (txtCodigoTipoLista.getText().length() != 3  ) {
						lblMensaje
								.setText("El codigo debe ser de 3 caracteres.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					} else if

					(tipoListaValidator.ValidarCodigo(txtCodigoTipoLista.getText(),txtDescripcionTipoLista.getText(), VentanaBuscarEvento.evento) == false) {
					    TipoListaDAO tipoListaDAO = new TipoListaDAO();
					    EventoDAO eventoDAO = new EventoDAO();

					      UcsawsTipoLista listaAGuardar = new UcsawsTipoLista();
					      listaAGuardar.setCodigo(txtCodigoTipoLista.getText().toUpperCase());
					      listaAGuardar.setDescripcion(txtDescripcionTipoLista.getText().toUpperCase());
					      listaAGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado.toUpperCase());
					      listaAGuardar.setFchIns(new Date());
					      listaAGuardar.setIdEvento(eventoDAO.obtenerEventoById(VentanaBuscarEvento.evento));
					      
					      tipoListaDAO.guardarTipoLista(listaAGuardar);

							model = new TipoListaJTableModel();
							//recuperarDatos();
							//table.setModel(model);
							model.fireTableDataChanged();
							//table.removeColumn(table.getColumnModel().getColumn(0));
							// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
							lblMensaje
									.setText("Excelente, se ha guardado el Tipo Lista.");
							Timer t = new Timer(Login.timer, new ActionListener() {

								public void actionPerformed(ActionEvent e) {
									lblMensaje.setText(null);
								}
							});
							t.setRepeats(false);
							t.start();

							//txtDescripcion.setText("");
							
							VentanaBuscarTipoLista tipoLista = new VentanaBuscarTipoLista();
							tipoLista.setVisible(true);
							this.dispose();
					    
					    
					    
					    
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje
								.setText("Ya existe el Tipo Lista con el codigo o descripcion ingresado. ");
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
			VentanaBuscarTipoLista tlista = new VentanaBuscarTipoLista();
			tlista.setVisible(true);
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

		query.setQueryGenerico("SELECT  id_tipo_lista, codigo, descripcion "
				+ "from  ucsaws_tipo_lista where id_evento = " + VentanaBuscarEvento.evento
				+ "order by codigo" + "");

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

			String[] fin = { fil.get(0).toString(),String.valueOf(contador), fil.get(1).toString(),
					fil.get(2).toString() };

			model.ciudades.add(fin);
			ite++;
		}

	}*/

}