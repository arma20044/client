package src.main.java.admin.departamento;

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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.validator.DepartamentoValidator;
import src.main.java.dao.departamento.DepartamentoDAO;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import entity.UcsawsDepartamento;

public class VentanaRegistroDepartamento extends JFrame implements
		ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;

	private DepartamentoJTableModel model = new DepartamentoJTableModel();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JLabel lblNroZona;
	private JTextField txtNroDepartamento;
	private JTextField txtDescripcion;

	private DepartamentoValidator departamentoValidator = new DepartamentoValidator();

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroDepartamento() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtNroDepartamento.requestFocus();
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroDepartamento.class
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
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroDepartamento.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(602, 123, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		

		labelTitulo = new JLabel();
		labelTitulo.setText("NUEVO DEPARTAMENTO");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(640, 181);
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroDepartamento.class
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

		txtNroDepartamento = new JTextField();
		txtNroDepartamento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				if ((car < '0' || car > '9'))
					e.consume();
			}
		});
		txtNroDepartamento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtNroDepartamento.getText().length() == 1) {
					txtNroDepartamento.setText(0 + txtNroDepartamento.getText());
				}
			}
		});
		txtNroDepartamento.setBounds(213, 54, 75, 26);
		getContentPane().add(txtNroDepartamento);
		txtNroDepartamento.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(213, 116, 363, 14);
		getContentPane().add(lblMensaje);

		JLabel lblDescripcionZona = new JLabel();
		lblDescripcionZona.setText("Descripcion:");
		lblDescripcionZona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcionZona.setBounds(114, 86, 89, 25);
		getContentPane().add(lblDescripcionZona);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(213, 85, 246, 26);
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

				if (!(txtNroDepartamento.getText().length() == 0) && !(txtDescripcion.getText().length() == 0)) {
					if (txtNroDepartamento.getText().length() > 3) {
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

					(departamentoValidator.ValidarCodigo(txtNroDepartamento.getText(),
							txtDescripcion.getText(), VentanaBuscarEvento.evento) == false) {
						 
					    EventoDAO evento = new EventoDAO();
					    DepartamentoDAO departamentoDAO = new DepartamentoDAO();
					    
					    UcsawsDepartamento departamentoAGuardar = new UcsawsDepartamento();
					    departamentoAGuardar.setNroDepartamento(txtNroDepartamento.getText().toUpperCase());
					    departamentoAGuardar.setDescDepartamento(txtDescripcion.getText().toUpperCase());
					    departamentoAGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado.toUpperCase());
					    departamentoAGuardar.setIdEvento(evento.obtenerEventoById(VentanaBuscarEvento.evento));
					    departamentoAGuardar.setFchIns(new Date());
					    
					    
					     
					    boolean a = departamentoDAO.guardarDepartamento(departamentoAGuardar);
					    
					    
					    VentanaBuscarDepartamento departamento = new VentanaBuscarDepartamento();
					    departamento.setVisible(true);
					    dispose();
					     
					    
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje
								.setText("Ya existe un Departamento con esos Datos.");
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
			VentanaBuscarDepartamento candidato = new VentanaBuscarDepartamento();
			candidato.setVisible(true);
			this.dispose();

		}
	}

	   /* private void recuperarDatos() {
			DepartamentoDAO departamentoDAO = new DepartamentoDAO();
			
			List<UcsawsDepartamento> listaDepartamentosByEvento = departamentoDAO
				.obtenerDepartamentoByIdEvento(Integer
					.parseInt(VentanaBuscarEvento.evento));

			List<UcsawsDepartamento> lista = new ArrayList<UcsawsDepartamento>();
			try {
			    lista = listaDepartamentosByEvento;
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
	    
	    
	    public AbstractTableModel obtenerModeloA(JTable tabla,
		    List<UcsawsDepartamento> departamento) {

		// AbstractTableModel model = (DefaultTableModel) tabla.getModel();
		Iterator<UcsawsDepartamento> ite = departamento.iterator();

		// String header[] = new String[] { "ID","Item","Nro.",
		// "Desc. Evento","Inicio","Fin","Desc. Tipo Evento" };
		// model.setColumnIdentifiers(header);

		UcsawsDepartamento aux;
		Integer cont = 1;
		while (ite.hasNext()) {
		    aux = ite.next();

		    Object[] row = { aux.getIdDepartamento(), cont,aux.getNroDepartamento(), aux.getDescDepartamento()};

		    // new
		    // SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
		    model.departamento.add(row);

		    cont++;

		}

		return model;
	    }


	private Vector recuperarDatosComboBoxDepartamento() {
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

		query.setQueryGenerico("SELECT id_departamento, nro_departamento || ' -  ' || desc_departamento"
				+ " from ucsaws_departamento " + "order by nro_departamento");

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