package src.main.java.admin.genero;

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

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.validator.GeneroValidator;
import src.main.java.dao.genero.GeneroDAO;
import src.main.java.login.Login;
import entity.UcsawsGenero;

public class VentanaRegistroGenero extends JFrame implements ActionListener {

    private Coordinador miCoordinador; // objeto miCoordinador que permite la
				       // relacion entre esta clase y la clase
				       // coordinador
    private JLabel labelTitulo, lblMensaje;
    private JButton botonGuardar, botonCancelar;

    private GeneroJTableModel model = new GeneroJTableModel();

    private GeneroValidator generoValidator = new GeneroValidator();

    private String codTemporal = "";
    private JButton btnHome;

    List<Object[]> ciudades = new ArrayList<Object[]>();

    List<Object[]> listas = new ArrayList<Object[]>();

    List<Object[]> tcandidato = new ArrayList<Object[]>();
    private JLabel lblNroZona;
    private JTextField txtCodigo;
    private JTextField txtDescripcion;

    /**
     * constructor de la clase donde se inicializan todos los componentes de la
     * ventana de registro
     */
    public VentanaRegistroGenero() {

	addWindowListener(new WindowAdapter() {
	    public void windowOpened(WindowEvent e) {
		txtCodigo.requestFocus();
	    }
	});

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
	
	botonGuardar = new JButton();
	botonGuardar.setToolTipText("Guardar");
	botonGuardar.setIcon(new ImageIcon(VentanaRegistroGenero.class
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
	botonCancelar.setIcon(new ImageIcon(VentanaRegistroGenero.class
		.getResource("/imgs/back2.png")));
	botonCancelar.setBounds(511, 138, 32, 32);
	botonCancelar.setOpaque(false);
	botonCancelar.setContentAreaFilled(false);
	botonCancelar.setBorderPainted(false);
	Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
	Image newimg2 = img2.getScaledInstance(32, 32,
		java.awt.Image.SCALE_SMOOTH);
	botonCancelar.setIcon(new ImageIcon(newimg2));

	labelTitulo = new JLabel();
	labelTitulo.setText("NUEVO GENERO");
	labelTitulo.setBounds(269, 11, 380, 30);
	labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

	botonGuardar.addActionListener(this);
	botonCancelar.addActionListener(this);
	getContentPane().add(botonCancelar);
	getContentPane().add(botonGuardar);
	getContentPane().add(labelTitulo);
	limpiar();
	setSize(546, 195);
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
	btnHome.setIcon(new ImageIcon(VentanaRegistroGenero.class
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

	txtCodigo = new JTextField();
	txtCodigo.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyTyped(KeyEvent arg0) {
		char car = arg0.getKeyChar();
		if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z'))
		    arg0.consume();
	    }
	});

	txtCodigo.setBounds(213, 52, 75, 26);
	getContentPane().add(txtCodigo);
	txtCodigo.setColumns(10);

	lblMensaje = new JLabel("");
	lblMensaje.setForeground(Color.RED);
	lblMensaje.setBounds(213, 128, 454, 14);
	getContentPane().add(lblMensaje);

	JLabel lblDescripcionZona = new JLabel();
	lblDescripcionZona.setText("Descripcion:");
	lblDescripcionZona.setHorizontalAlignment(SwingConstants.RIGHT);
	lblDescripcionZona.setBounds(102, 82, 89, 25);
	getContentPane().add(lblDescripcionZona);

	txtDescripcion = new JTextField();
	txtDescripcion.setColumns(10);
	txtDescripcion.setBounds(213, 87, 310, 26);
	getContentPane().add(txtDescripcion);
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

		if (!(txtCodigo.getText().length() == 0)
			&& !(txtDescripcion.getText().length() == 0)) {
		    if (txtCodigo.getText().length() > 1) {
			lblMensaje
				.setText("El codigo debe ser de maximo 1 caracter.");
			Timer t = new Timer(Login.timer, new ActionListener() {

			    public void actionPerformed(ActionEvent e) {
				lblMensaje.setText(null);
			    }
			});
			t.setRepeats(false);
			t.start();
		    } else if

		    (generoValidator.ValidarCodigo(txtCodigo.getText(),
			    VentanaBuscarEvento.evento) == false) {

			UcsawsGenero generoAGuardar = new UcsawsGenero();
			generoAGuardar
				.setUsuarioIns(Login.nombreApellidoUserLogeado);
			generoAGuardar.setCodigo(txtCodigo.getText().toUpperCase());
			generoAGuardar.setDescripcion(txtDescripcion.getText().toUpperCase());
			generoAGuardar.setIdEvento(Integer
				.parseInt(VentanaBuscarEvento.evento));

			GeneroDAO generoDAO = new GeneroDAO();
			generoDAO.guardarGenero(generoAGuardar);
			
			VentanaBuscarGenero genero = new VentanaBuscarGenero();
			genero.setVisible(true);
			dispose();

			/*model = new GeneroJTableModel();
			//recuperarDatos();
			//table.setModel(model);
			model.fireTableDataChanged();
			//table.removeColumn(table.getColumnModel().getColumn(0));
			// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
			lblMensaje
				.setText("Excelente, se ha guardado el Genero.");
			Timer t = new Timer(Login.timer, new ActionListener() {

			    public void actionPerformed(ActionEvent e) {
				lblMensaje.setText(null);
			    }
			});
			t.setRepeats(false);
			t.start();

			txtCodigo.setText("");
			txtDescripcion.setText("");*/

			// this.dispose();
			// } else {
			// // JOptionPane.showMessageDialog(null,
			// // "Ya existe el genero " + txtDesc.getText(),
			// // "Información",JOptionPane.WARNING_MESSAGE);
			// lblMensaje
			// .setText("La Persona no puede tener mas de una candidatura");
			// Timer t = new Timer(Login.timer,
			// new ActionListener() {
			//
			// public void actionPerformed(
			// ActionEvent e) {
			// lblMensaje.setText(null);
			// }
			// });
			// t.setRepeats(false);
			// t.start();
			// }
		    } else {
			// JOptionPane.showMessageDialog(null,
			// "Ya existe el genero " + txtDesc.getText(),
			// "Información",JOptionPane.WARNING_MESSAGE);
			lblMensaje
				.setText("Ya existe el Genero con ese codigo. "
					+ txtCodigo.getText());
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
	    VentanaBuscarGenero candidato = new VentanaBuscarGenero();
	    candidato.setVisible(true);
	    this.dispose();

	}
    }

   /* private void recuperarDatos() {

	JSONArray filas = new JSONArray();
	JSONArray fil = new JSONArray();

	boolean existe = false;

	// Statement estatuto = conex.getConnection().createStatement();

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	// para registrar se inserta el codigo es 1
	query.setTipoQueryGenerico(39);

	query.setQueryGenerico(VentanaBuscarEvento.evento);

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	String res = response.getQueryGenericoResponse();

	// json string to List<String>;
	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();
	List<UcsawsGenero> lista = new ArrayList<UcsawsGenero>();
	try {
	    lista = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsGenero>>() {
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

    public AbstractTableModel obtenerModeloA(JTable tabla,
	    List<UcsawsGenero> genero) {

	// AbstractTableModel model = (DefaultTableModel) tabla.getModel();
	Iterator<UcsawsGenero> ite = genero.iterator();

	// String header[] = new String[] { "ID","Item","Nro.",
	// "Desc. Evento","Inicio","Fin","Desc. Tipo Evento" };
	// model.setColumnIdentifiers(header);

	UcsawsGenero aux;
	Integer cont = 1;
	while (ite.hasNext()) {
	    aux = ite.next();

	    Object[] row = { aux.getIdGenero(), cont, aux.getCodigo(),
		    aux.getDescripcion() };

	    // new
	    // SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
	    model.genero.add(row);

	    cont++;

	}

	return model;
    }

}