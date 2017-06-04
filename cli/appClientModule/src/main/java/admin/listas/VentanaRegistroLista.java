package src.main.java.admin.listas;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.validator.ListasValidator;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.listas.ListasDAO;
import src.main.java.dao.tipoLista.TipoListaDAO;
import src.main.java.login.Login;
import entity.UcsawsListas;
import entity.UcsawsTipoLista;

public class VentanaRegistroLista extends JFrame implements ActionListener {

    private Coordinador miCoordinador; // objeto miCoordinador que permite la
				       // relacion entre esta clase y la clase
				       // coordinador
    private JLabel labelTitulo, lblMensaje;
    private JButton botonGuardar, botonCancelar;

    private ListasJTableModel model = new ListasJTableModel();

    private ListasValidator listasValidator = new ListasValidator();

    private String codTemporal = "";
    private JButton btnHome;

    List<Object[]> ciudades = new ArrayList<Object[]>();

    List<Object[]> listas = new ArrayList<Object[]>();

    List<Object[]> tcandidato = new ArrayList<Object[]>();
    private JLabel lblNro;
    private JTextField txtNro;
    private JTextField txtNombre;
    private JLabel lblAnho;
    private JTextField txtAnho;
    private JComboBox cmbTipoLista;
    private DefaultTableModel dm;
    private ListasDAO listaDAO = new ListasDAO();

    Item item;
    Integer tipoListaSelected;

    /**
     * constructor de la clase donde se inicializan todos los componentes de la
     * ventana de registro
     */
    public VentanaRegistroLista() {

	addWindowListener(new WindowAdapter() {
	    public void windowOpened(WindowEvent e) {
		txtNro.requestFocus();
	    }
	});

	botonGuardar = new JButton();
	botonGuardar.setToolTipText("Guardar");
	botonGuardar.setIcon(new ImageIcon(VentanaRegistroLista.class
		.getResource("/imgs/save.png")));
	botonGuardar.setBounds(298, 48, 32, 32);
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
	botonCancelar.setIcon(new ImageIcon(VentanaRegistroLista.class
		.getResource("/imgs/back2.png")));
	botonCancelar.setBounds(689, 197, 32, 32);
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
	labelTitulo.setText("NUEVA LISTA");
	labelTitulo.setBounds(269, 11, 380, 30);
	labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

	botonGuardar.addActionListener(this);
	botonCancelar.addActionListener(this);
	getContentPane().add(botonCancelar);
	getContentPane().add(botonGuardar);
	getContentPane().add(labelTitulo);
	limpiar();
	setSize(725, 256);
	setTitle("Sistema E-vote: Paraguay Elecciones 2015");
	setLocationRelativeTo(null);
	setResizable(false);
	getContentPane().setLayout(null);
	// recuperarDatos();

	btnHome = new JButton("");
	btnHome.setToolTipText("Inicio");
	btnHome.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		DefinicionesGenerales menuprincipal = new DefinicionesGenerales();
		menuprincipal.setVisible(true);
		dispose();
	    }
	});
	btnHome.setIcon(new ImageIcon(VentanaRegistroLista.class
		.getResource("/imgs/home.png")));
	btnHome.setBounds(0, 0, 32, 32);
	Image img = ((ImageIcon) btnHome.getIcon()).getImage();
	Image newimg = img.getScaledInstance(32, 32,
		java.awt.Image.SCALE_SMOOTH);
	btnHome.setIcon(new ImageIcon(newimg));
	getContentPane().add(btnHome);

	lblNro = new JLabel();
	lblNro.setHorizontalAlignment(SwingConstants.RIGHT);
	lblNro.setText("Nro.:");
	lblNro.setBounds(130, 52, 61, 25);
	getContentPane().add(lblNro);

	txtNro = new JTextField();

	txtNro.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyTyped(KeyEvent arg0) {
		char car = arg0.getKeyChar();
		if ((car < '0' || car > '9'))
		    arg0.consume();
	    }
	});

	txtNro.setBounds(213, 54, 75, 26);
	getContentPane().add(txtNro);
	txtNro.setColumns(10);

	lblMensaje = new JLabel("");
	lblMensaje.setForeground(Color.RED);
	lblMensaje.setBounds(213, 175, 454, 14);
	getContentPane().add(lblMensaje);

	JLabel lblNombre = new JLabel();
	lblNombre.setText("Nombre:");
	lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
	lblNombre.setBounds(102, 82, 89, 25);
	getContentPane().add(lblNombre);

	txtNombre = new JTextField();
	txtNombre.setColumns(10);
	txtNombre.setBounds(213, 85, 310, 26);
	getContentPane().add(txtNombre);

	lblAnho = new JLabel();
	lblAnho.setText("Año:");
	lblAnho.setHorizontalAlignment(SwingConstants.RIGHT);
	lblAnho.setBounds(130, 113, 61, 25);
	getContentPane().add(lblAnho);

	txtAnho = new JTextField();
	txtAnho.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyTyped(KeyEvent e) {
		char car = e.getKeyChar();
		if ((car < '0' || car > '9'))
		    e.consume();
	    }
	});
	txtAnho.setColumns(10);
	txtAnho.setBounds(213, 116, 75, 26);
	getContentPane().add(txtAnho);

	JLabel lblTipoLista = new JLabel();
	lblTipoLista.setText("Tipo Lista:");
	lblTipoLista.setHorizontalAlignment(SwingConstants.RIGHT);
	lblTipoLista.setBounds(130, 144, 61, 25);
	getContentPane().add(lblTipoLista);

	cmbTipoLista = new JComboBox(recuperarDatosComboBoxTipoLista());
	cmbTipoLista.setSelectedIndex(-1);
	cmbTipoLista.setBounds(213, 146, 340, 20);
	getContentPane().add(cmbTipoLista);
	// recuperarDatos();

	getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");

	getRootPane().getActionMap().put("clickButton", new AbstractAction() {
	    public void actionPerformed(ActionEvent ae) {
		botonGuardar.doClick();
		System.out.println("button clicked");
	    }
	});

	getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
		"clickButtonescape");

	getRootPane().getActionMap().put("clickButtonescape",
		new AbstractAction() {
		    public void actionPerformed(ActionEvent ae) {
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

		if (cmbTipoLista.getSelectedIndex() != -1) {
		    item = (Item) cmbTipoLista.getSelectedItem();
		    tipoListaSelected = item.getId();
		}

		if (!(txtNro.getText().length() == 0)
			&& !(txtAnho.getText().length() == 0)
			&& !(txtNombre.getText().length() == 0)
			&& cmbTipoLista.getSelectedIndex() != -1
			&& !(txtNombre.getText().length() == 0)) {
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

		    (listasValidator.ValidarCodigo(
			    Integer.parseInt(txtNro.getText()),
			    txtNombre.getText(),
			    Integer.parseInt(txtAnho.getText()),
			    tipoListaSelected, VentanaBuscarEvento.evento) == false) {

			EventoDAO eventoDAO = new EventoDAO();
			TipoListaDAO tipoListaDAO = new TipoListaDAO();

			UcsawsTipoLista tipoLista = tipoListaDAO
				.obtenerTipoListaById(tipoListaSelected);

			UcsawsListas listaAGuardar = new UcsawsListas();

			listaAGuardar.setNroLista(Integer.parseInt(txtNro
				.getText().toUpperCase()));
			listaAGuardar.setNombreLista(txtNombre.getText()
				.toUpperCase());
			listaAGuardar.setIdEvento(eventoDAO
				.obtenerEventoById(VentanaBuscarEvento.evento));
			listaAGuardar.setAnho(Integer.parseInt(txtAnho
				.getText()));
			listaAGuardar.setFchIns(new Date());
			listaAGuardar
				.setUsuarioIns(Login.nombreApellidoUserLogeado);
			listaAGuardar.setUcsawsTipoLista(tipoLista);

			ListasDAO listasDAO = new ListasDAO();
			listasDAO.guardarLista(listaAGuardar);

			VentanaBuscarLista buscar = new VentanaBuscarLista();
			buscar.setVisible(true);
			dispose();

		    } else {
			lblMensaje
				.setText("Ya existe Lista con el Codigo o Nombre. Favor Verificar.");
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
	    VentanaBuscarLista candidato = new VentanaBuscarLista();
	    candidato.setVisible(true);
	    this.dispose();

	}
    }

    private Vector recuperarDatosComboBoxTipoLista() {
	Vector model = new Vector();

	ListasDAO listaDAO = new ListasDAO();
	TipoListaDAO tipoListaDAO = new TipoListaDAO();

	List<UcsawsTipoLista> tipoLista = tipoListaDAO
		.obtenerTipoListaByIdEvento(Integer
			.parseInt(VentanaBuscarEvento.evento));
	// List<UcsawsListas> lista =
	// listaDAO.obtenerListaByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

	if (tipoLista.isEmpty()) {
	    // JOptionPane.showMessageDialog(null, "algo salio mal",
	    // "Advertencia", JOptionPane.WARNING_MESSAGE);
	    // return lista;
	}

	else {

	    // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
	    Iterator<UcsawsTipoLista> ite = tipoLista.iterator();

	    UcsawsTipoLista aux;

	    while (ite.hasNext()) {
		aux = ite.next();

		model.addElement(new Item(aux.getIdTipoLista(), aux
			.getDescripcion()));

	    }
	    // return model;
	}

	return model;

    }

}