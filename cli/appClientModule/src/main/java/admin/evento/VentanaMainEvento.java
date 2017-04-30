package src.main.java.admin.evento;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Administracion;
import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.MenuPrincipal;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import java.awt.Font;
import javax.swing.JLayeredPane;

public class VentanaMainEvento extends JFrame implements ActionListener {

    public static Integer evento; // 0
    public static String nroEvento; // 1
    public static String descripcionEvento; // 2
    public static String fechaDesde;
    public static String fechaHasta;
    public static String tipoEventoDescripcon;

    private Coordinador miCoordinador; // objeto miCoordinador que permite la
				       // relacion entre esta clase y la clase
				       // coordinador
    private JLabel labelTitulo;
    private JButton botonCancelar;

    JSONArray miPersona = null;
    DefaultTableModel modelo;
    private EventoJTableModel model = new EventoJTableModel();

    private String codTemporal = "";
    private JLabel lblNroEvento;
    private JLabel label;
    private JLabel lblDescripcionEvento;
    private JLabel lblFechaDesde;
    private JLabel lblFechaHasta;
    private JLabel lblTipoEvento;
    private JLabel label_1;
    private JLabel label_2;
    private JLabel label_3;
    private JButton btnDefinicionesGenerales;
    private JLabel label_5;
    private JLabel label_6;
    private JLabel label_7;

    /**
     * constructor de la clase donde se inicializan todos los componentes de la
     * ventana de busqueda
     */
    public VentanaMainEvento() {

	recuperarDatosEvento();

	setResizable(false);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	botonCancelar = new JButton();
	botonCancelar.setIcon(new ImageIcon(VentanaMainEvento.class
		.getResource("/imgs/back2.png")));
	botonCancelar.setToolTipText("Atrás");
	botonCancelar.setBounds(768, 422, 45, 25);
	botonCancelar.setOpaque(false);
	botonCancelar.setContentAreaFilled(false);
	botonCancelar.setBorderPainted(false);
	Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
	Image newimg2 = img2.getScaledInstance(32, 32,
		java.awt.Image.SCALE_SMOOTH);
	botonCancelar.setIcon(new ImageIcon(newimg2));

	labelTitulo = new JLabel();
	labelTitulo.setText("MAIN EVENTO");
	labelTitulo.setBounds(369, 103, 270, 30);
	labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));
	botonCancelar.addActionListener(this);

	getContentPane().add(botonCancelar);
	getContentPane().add(labelTitulo);
	limpiar();

	setSize(819, 476);
	setTitle("Sistema E-vote: Paraguay Elecciones 2015");
	setLocationRelativeTo(null);
	getContentPane().setLayout(null);

	JButton btnHome = new JButton("");
	btnHome.setToolTipText("Inicio");
	btnHome.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		MenuPrincipal menuprincipal = new MenuPrincipal();
		menuprincipal.setVisible(true);
		dispose();
	    }
	});
	btnHome.setIcon(new ImageIcon(VentanaMainEvento.class
		.getResource("/imgs/home.png")));
	btnHome.setBounds(0, 0, 32, 32);

	System.out.println(nroEvento);

	btnDefinicionesGenerales = new JButton("Definiciones Generales");
	btnDefinicionesGenerales.setIcon(new ImageIcon(VentanaMainEvento.class
		.getResource("/imgs/def.png")));
	btnDefinicionesGenerales.setFont(new Font("Tahoma", Font.PLAIN, 21));
	btnDefinicionesGenerales.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		DefinicionesGenerales definiciones = new DefinicionesGenerales();
		definiciones.setVisible(true);
		dispose();
	    }
	});
	btnDefinicionesGenerales.setBounds(262, 158, 338, 73);
	getContentPane().add(btnDefinicionesGenerales);

	JLayeredPane layeredPane = new JLayeredPane();
	layeredPane.setBounds(22, 11, 781, 83);
	getContentPane().add(layeredPane);

	lblTipoEvento = new JLabel("Tipo Evento:");
	lblTipoEvento.setBounds(10, 61, 94, 14);
	layeredPane.add(lblTipoEvento);

	lblNroEvento = new JLabel("Nro. Evento:");
	lblNroEvento.setBounds(10, 36, 94, 14);
	layeredPane.add(lblNroEvento);

	label = new JLabel(nroEvento);
	label.setBounds(161, 36, 214, 14);
	layeredPane.add(label);
	label.setFont(new Font("Tahoma", Font.BOLD, 11));
	label.setForeground(Color.BLACK);

	lblDescripcionEvento = new JLabel("Descripcion Evento:");
	lblDescripcionEvento.setBounds(10, 46, 129, 14);
	layeredPane.add(lblDescripcionEvento);

	label_1 = new JLabel(descripcionEvento);
	label_1.setBounds(161, 46, 214, 14);
	layeredPane.add(label_1);
	label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
	label_1.setForeground(Color.BLACK);

	lblFechaDesde = new JLabel("Fecha Desde:");
	lblFechaDesde.setBounds(385, 46, 94, 14);
	layeredPane.add(lblFechaDesde);

	label_2 = new JLabel(fechaDesde);
	label_2.setBounds(518, 46, 214, 14);
	layeredPane.add(label_2);
	label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
	label_2.setForeground(Color.BLACK);

	lblFechaHasta = new JLabel("Fecha Hasta");
	lblFechaHasta.setBounds(385, 61, 94, 14);
	layeredPane.add(lblFechaHasta);

	label_3 = new JLabel(fechaHasta);
	label_3.setBounds(518, 61, 214, 14);
	layeredPane.add(label_3);
	label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
	label_3.setForeground(Color.BLACK);

	JLabel label_4 = new JLabel(tipoEventoDescripcon);
	label_4.setBounds(161, 61, 214, 14);
	layeredPane.add(label_4);
	label_4.setForeground(Color.BLACK);
	label_4.setFont(new Font("Tahoma", Font.BOLD, 11));

	label_5 = new JLabel("Administrador");
	label_5.setBounds(0, 0, 89, 14);
	layeredPane.add(label_5);

	label_6 = new JLabel("Nombre:");
	label_6.setBounds(0, 11, 54, 14);
	layeredPane.add(label_6);

	label_7 = new JLabel(Login.nombreApellidoUserLogeado);
	label_7.setBounds(68, 11, 278, 14);
	layeredPane.add(label_7);
	// recuperarDatos();

	// cellSelectionModel.addListSelectionListener(new
	// ListSelectionListener() {
	// public void valueChanged(ListSelectionEvent e) {
	// String selectedData = null;
	//
	// int[] selectedRow = table_1.getSelectedRows();
	// int[] selectedColumns = table_1.getSelectedColumns();
	//
	// for (int i = 0; i < selectedRow.length; i++) {
	// for (int j = 0; j < selectedColumns.length; j++) {
	// selectedData = (String) table_1.getValueAt(selectedRow[i],
	// selectedColumns[j]);
	// }
	// }
	// System.out.println("Selected: " + selectedData);
	// }
	//
	// });

	DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm:ss");

    }

    public void setCoordinador(Coordinador miCoordinador) {
	this.miCoordinador = miCoordinador;
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == botonCancelar) {
	    VentanaBuscarEvento buscar = new VentanaBuscarEvento();
	    buscar.setVisible(true);
	    this.dispose();
	}

    }

    /**
     * permite cargar los datos de la persona consultada
     * 
     * @param miPersona
     */
    private void muestraPersona(JSONArray genero) {
	JSONArray a = (JSONArray) genero.get(0);
	// txtId.setText(Long.toString( (Long) a.get(0)) );
	// txtBuscar.setText((String) a.get(1));
	// textFecha.setText((String) a.get(2));
	// textUsu.setText((String) a.get(4));
	codTemporal = a.get(0).toString();

	habilita(true, false, false, false, false, true, false, true, true);
    }

    /**
     * Permite limpiar los componentes
     */
    public void limpiar() {

	// codTemporal.setText("");
	habilita(true, false, false, false, false, true, false, true, true);
    }

    /**
     * Permite habilitar los componentes para establecer una modificacion
     * 
     * @param codigo
     * @param nombre
     * @param edad
     * @param tel
     * @param profesion
     * @param cargo
     * @param bBuscar
     * @param bGuardar
     * @param bModificar
     * @param bEliminar
     */
    public void habilita(boolean codigo, boolean nombre, boolean edad,
	    boolean tel, boolean profesion, boolean bBuscar, boolean bGuardar,
	    boolean bModificar, boolean bEliminar) {
    }

    private void recuperarDatosEvento() {
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

	query.setQueryGenerico("select ev.id_evento, nro_evento,ev.descripcion, fch_desde, fch_hasta , tev.descripcion as Tdescripcion "

		+ " from ucsaws_evento ev join ucsaws_tipo_evento tev on (ev.id_tipo_evento = tev.id_tipo_evento)"
		+ "where id_evento = "
		+ Integer.parseInt(VentanaBuscarEvento.evento)
		+ "order by nro_evento");

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

	    String[] fin = { fil.get(0).toString(), fil.get(1).toString(),
		    fil.get(2).toString(), fil.get(3).toString(),
		    fil.get(4).toString(), fil.get(5).toString() };

	    nroEvento = (String) fil.get(1);

	    descripcionEvento = (String) fil.get(2);

	    fechaDesde = (String) fil.get(3);
	    fechaHasta = (String) fil.get(4);
	    tipoEventoDescripcon = (String) fil.get(5);

	    model.eventos.add(fin);
	    ite++;
	}

    }

    void LimpiarCampos() {
	// txtBuscar.setText("");
	// textFecha.setText("");
	// textUsu.setText("");
	codTemporal = "";
	// txtId.setText("");

    }
}
