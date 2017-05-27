package src.main.java.admin.local;

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
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.validator.LocalValidator;
import src.main.java.admin.zona.VentanaBuscarZona;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.local.LocalDAO;
import src.main.java.dao.zona.ZonaDAO;
import src.main.java.login.Login;
import entity.UcsawsLocal;

public class VentanaRegistroLocal extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;

	private LocalJTableModel model = new LocalJTableModel();

	private LocalValidator localValidator = new LocalValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JLabel lblNroLocal;
	private JTextField txtNroLocal;
	private JTextField txtDescripcionLocal;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroLocal() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtNroLocal.requestFocus();
			}
		});
		

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroLocal.class
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
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroLocal.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(589, 150, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		

		labelTitulo = new JLabel();
		labelTitulo.setText("NUEVO LOCAL");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(624, 208);
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroLocal.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		lblNroLocal = new JLabel();
		lblNroLocal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNroLocal.setText("Nro:");
		lblNroLocal.setBounds(142, 59, 61, 25);
		getContentPane().add(lblNroLocal);

		txtNroLocal = new JTextField();
		txtNroLocal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				if((car<'0' || car>'9')) e.consume();
			}
		});
		txtNroLocal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtNroLocal.getText().length() == 1)
				{
					txtNroLocal.setText(0 + txtNroLocal.getText() );
				}
			}
		});
		txtNroLocal.setBounds(213, 54, 75, 26);
		getContentPane().add(txtNroLocal);
		txtNroLocal.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(213, 122, 363, 14);
		getContentPane().add(lblMensaje);
		
		JLabel lblDescripcionLocal = new JLabel();
		lblDescripcionLocal.setText("Descripcion:");
		lblDescripcionLocal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcionLocal.setBounds(114, 86, 89, 25);
		getContentPane().add(lblDescripcionLocal);
		
		txtDescripcionLocal = new JTextField();
		txtDescripcionLocal.setColumns(10);
		txtDescripcionLocal.setBounds(213, 85, 298, 26);
		getContentPane().add(txtDescripcionLocal);
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
				

				
				if (!(txtNroLocal.getText().length() == 0) && !(txtDescripcionLocal.getText().length() == 0)  ) {
					if (txtNroLocal.getText().length() > 3) {
						lblMensaje.setText("El codigo debe ser de maximo 3 caracteres.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					} else if

					(localValidator.ValidarCodigo(txtNroLocal.getText() , txtDescripcionLocal.getText(), VentanaBuscarEvento.evento
						, VentanaBuscarZona.zonaSeleccionada) == false) {

					    EventoDAO evento = new EventoDAO();
					    ZonaDAO zonaDAO = new ZonaDAO();
					    LocalDAO localDAO = new LocalDAO();
					    
					    UcsawsLocal localAGuardar = new UcsawsLocal();
					    localAGuardar.setNroLocal(txtNroLocal.getText());
					    localAGuardar.setDescLocal(txtDescripcionLocal.getText().toUpperCase());
					    localAGuardar.setFchIns(new Date());
					    localAGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado.toUpperCase());
					    localAGuardar.setIdEvento(evento.obtenerEventoById(VentanaBuscarEvento.evento));
					    localAGuardar.setUcsawsZona(zonaDAO.obtenerZonaByID(Integer.parseInt(VentanaBuscarZona.zonaSeleccionada)));
					    
					    try{
						localDAO.guardarLocal(localAGuardar);
					    }
					    catch(Exception ex){
						System.out
							.println(ex);
					    }
					    
					    finally{
						VentanaBuscarLocal local = new VentanaBuscarLocal();
						local.setVisible(true);
						dispose();
					    }
					    
					    
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje
								.setText("Ya existe el Local. "
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
			VentanaBuscarLocal candidato = new VentanaBuscarLocal();
			candidato.setVisible(true);
			this.dispose();

		}
	}




	




}