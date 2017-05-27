package src.main.java.admin.mesa;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsLocal;
import entity.UcsawsMesa;
import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.local.VentanaBuscarLocal;
import src.main.java.admin.validator.MesaValidator;
import src.main.java.admin.zona.VentanaBuscarZona;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.local.LocalDAO;
import src.main.java.dao.mesa.MesaDAO;
import src.main.java.dao.zona.ZonaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaRegistroMesa extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;

	private MesaJTableModel model = new MesaJTableModel();

	private MesaValidator mesaValidator = new MesaValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JLabel lblNroMesa;
	private JTextField txtNroMesa;
	private JTextField txtDescripcion;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroMesa() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtNroMesa.requestFocus();
			}
		});

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroMesa.class
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
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroMesa.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(660, 167, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		

		labelTitulo = new JLabel();
		labelTitulo.setText("NUEVA MESA");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(694, 231);
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroMesa.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		lblNroMesa = new JLabel();
		lblNroMesa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNroMesa.setText("Nro:");
		lblNroMesa.setBounds(142, 52, 61, 25);
		getContentPane().add(lblNroMesa);

		txtNroMesa = new JTextField();
		txtNroMesa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				if((car<'0' || car>'9')) e.consume();
			}
		});
		txtNroMesa.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtNroMesa.getText().length() == 1)
				{
					txtNroMesa.setText(0 + txtNroMesa.getText() );
				}
			}
		});
		txtNroMesa.setBounds(213, 54, 75, 26);
		getContentPane().add(txtNroMesa);
		txtNroMesa.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(213, 122, 363, 14);
		getContentPane().add(lblMensaje);
		
		JLabel lblDescripcionMesa = new JLabel();
		lblDescripcionMesa.setText("Descripcion:");
		lblDescripcionMesa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcionMesa.setBounds(114, 86, 89, 25);
		getContentPane().add(lblDescripcionMesa);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(213, 85, 320, 26);
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
				

				
				if (!(txtNroMesa.getText().length() == 0) && !(txtDescripcion.getText().length() == 0)) {
					if (txtNroMesa.getText().length() > 3) {
						lblMensaje.setText("El codigo debe ser de maximo 3 caracteres.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					} else if

					(mesaValidator.ValidarCodigo(txtNroMesa.getText() , txtDescripcion.getText(),VentanaBuscarEvento.evento , VentanaBuscarLocal.localSeleccionado) == false) {



					    EventoDAO evento = new EventoDAO();
					    MesaDAO mesaDAO = new MesaDAO();
					    LocalDAO localDAO = new LocalDAO();
					    
					    UcsawsMesa mesaAGuardar = new UcsawsMesa();
					    mesaAGuardar.setNroMesa(Integer.parseInt(txtNroMesa.getText()));
					    mesaAGuardar.setDescMesa(txtDescripcion.getText().toUpperCase());
					    mesaAGuardar.setFchIns(new Date());
					    mesaAGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado.toUpperCase());
					    mesaAGuardar.setIdEvento(evento.obtenerEventoById(VentanaBuscarEvento.evento));
					    mesaAGuardar.setUcsawsLocal(localDAO.obtenerLocalByID(Integer.parseInt(VentanaBuscarLocal.localSeleccionado)));
					    
					    try{
						mesaDAO.guardarMesa(mesaAGuardar);
					    }
					    catch(Exception ex){
						System.out
							.println(ex);
					    }
					    
					    finally{
						VentanaBuscarMesa mesa = new VentanaBuscarMesa();
						mesa.setVisible(true);
						dispose();
					    }
					    
					    
					    
					    
					    
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje
								.setText("Ya existe la Mesa "
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
			VentanaBuscarMesa candidato = new VentanaBuscarMesa();
			candidato.setVisible(true);
			this.dispose();

		}
	}










}