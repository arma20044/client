package src.main.java.admin.evento;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.Evento;
import entity.UcsawsEvento;
import entity.UcsawsTipoEvento;
import src.main.java.admin.Coordinador;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.Calendario;
import src.main.java.admin.persona.Item;
import src.main.java.admin.utils.ArmarFecha;
import src.main.java.admin.validator.EventoValidator;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.tipoEvento.TipoEventoDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;










import com.toedter.calendar.JDateChooser;

public class VentanaModificarEvento extends JFrame implements ActionListener {
    
    ArmarFecha armarFecha = new ArmarFecha();

    MaskFormatter mf1;
    
    private Coordinador miCoordinador; // objeto miCoordinador que permite la
				       // relacion entre esta clase y la clase
				       // coordinador
    private JLabel labelTitulo, lblMensaje;
    private JButton btnGuardar, botonCancelar, btnFecha;
    private VentanaModificarEvento ventanaRegistroPersona;
    private EventoJTableModel model = new EventoJTableModel();

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();

    private EventoValidator eventoValidator = new EventoValidator();

    private String codTemporal = "";
    private JButton btnHome;

    List<Object[]> ciudades = new ArrayList<Object[]>();

    List<Object[]> listas = new ArrayList<Object[]>();

    List<Object[]> tcandidato = new ArrayList<Object[]>();
    private JLabel lblTipoEvento;
    private JComboBox cmbTipoEvento;
    private JLabel lblNro;
    private JTextField txtNro;
    private JLabel lblDescripcion;
    private JTextField txtDescripcion;
    private JLabel lblFechaDesde;

    private DefaultTableModel dm;

    private EventoDAO eventoDAO = new EventoDAO();
    private TipoEventoDAO tipoEventoDAO = new TipoEventoDAO();

  
    private JFormattedTextField horaMinutoDesde;
    private JDateChooser dateChooserDesde;
    private JDateChooser dateChooserHasta;
    private JFormattedTextField horaMinutoHasta;
    
    UcsawsEvento evento;

    /**
     * constructor de la clase donde se inicializan todos los componentes de la
     * ventana de registro
     */
    public VentanaModificarEvento(UcsawsEvento e) {

	addWindowListener(new WindowAdapter() {
	    public void windowOpened(WindowEvent e) {
		txtNro.requestFocus();
	    }
	});
	
	evento = e;

	getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"clickButton");

	getRootPane().getActionMap().put("clickButton",new AbstractAction(){
		        public void actionPerformed(ActionEvent ae)
		        {
		    btnGuardar.doClick();
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
	

	btnGuardar = new JButton();
	btnGuardar.setToolTipText("Guardar Cambios");
	btnGuardar.setIcon(new ImageIcon(VentanaModificarEvento.class
		.getResource("/imgs/save.png")));
	btnGuardar.setBounds(339, 52, 32, 32);
	btnGuardar.setOpaque(false);
	btnGuardar.setContentAreaFilled(false);
	btnGuardar.setBorderPainted(false);
	Image img3 = ((ImageIcon) btnGuardar.getIcon()).getImage();
	Image newimg3 = img3.getScaledInstance(32, 32,
		java.awt.Image.SCALE_SMOOTH);
	btnGuardar.setIcon(new ImageIcon(newimg3));

	botonCancelar = new JButton();
	botonCancelar.setBackground(Color.WHITE);
	botonCancelar.setToolTipText("Atrás");
	botonCancelar.setIcon(new ImageIcon(VentanaModificarEvento.class
		.getResource("/imgs/back2.png")));
	botonCancelar.setBounds(526, 250, 32, 32);
	botonCancelar.setOpaque(false);
	botonCancelar.setContentAreaFilled(false);
	botonCancelar.setBorderPainted(false);
	Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
	Image newimg2 = img2.getScaledInstance(32, 32,
		java.awt.Image.SCALE_SMOOTH);
	botonCancelar.setIcon(new ImageIcon(newimg2));

	labelTitulo = new JLabel();
	labelTitulo.setText("MODIFICAR EVENTO\r\n");
	labelTitulo.setBounds(269, 11, 380, 30);
	labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

	btnGuardar.addActionListener(this);
	botonCancelar.addActionListener(this);
	getContentPane().add(botonCancelar);
	getContentPane().add(btnGuardar);
	getContentPane().add(labelTitulo);
	limpiar();
	setSize(593, 319);
	setTitle("Sistema E-vote: Paraguay Elecciones 2015");
	setLocationRelativeTo(null);
	setResizable(false);
	getContentPane().setLayout(null);
	//recuperarDatos();

	btnHome = new JButton("");
	btnHome.setToolTipText("Inicio");
	btnHome.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		MenuPrincipal menuprincipal = new MenuPrincipal();
		menuprincipal.setVisible(true);
		dispose();
	    }
	});
	btnHome.setIcon(new ImageIcon(VentanaModificarEvento.class
		.getResource("/imgs/home.png")));
	btnHome.setBounds(0, 0, 32, 32);
	Image img = ((ImageIcon) btnHome.getIcon()).getImage();
	Image newimg = img.getScaledInstance(32, 32,
		java.awt.Image.SCALE_SMOOTH);
	btnHome.setIcon(new ImageIcon(newimg));
	getContentPane().add(btnHome);

	lblTipoEvento = new JLabel();
	lblTipoEvento.setHorizontalAlignment(SwingConstants.RIGHT);
	lblTipoEvento.setText("Tipo Evento:");
	lblTipoEvento.setBounds(96, 206, 95, 25);
	getContentPane().add(lblTipoEvento);

	cmbTipoEvento = new JComboBox(recuperarDatosComboBoxTipoEvento());
	cmbTipoEvento.setBounds(213, 208, 340, 25);
	System.out.println("select item: " + evento.getUcsawsTipoEvento().getDescripcion());
	//cmbTipoEvento.setSelectedItem(evento.getUcsawsTipoEvento().getDescripcion());
	// cmbTipoEvento.setSelectedItem(e.getId_tipo_evento());
	filtrarComboEventoTipo();
	getContentPane().add(cmbTipoEvento);
	AutoCompleteDecorator.decorate(cmbTipoEvento);

	lblNro = new JLabel();
	lblNro.setHorizontalAlignment(SwingConstants.RIGHT);
	lblNro.setText("N°:");
	lblNro.setBounds(130, 52, 61, 25);
	getContentPane().add(lblNro);

	txtNro = new JTextField();
	txtNro.setEditable(false);
	txtNro.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyTyped(KeyEvent arg0) {
		char car = arg0.getKeyChar();
		if ((car < '0' || car > '9'))
		    arg0.consume();
	    }
	});
	txtNro.setBounds(213, 54, 108, 25);
	getContentPane().add(txtNro);
	txtNro.setColumns(10);
	txtNro.setText(evento.getNroEvento());

	lblMensaje = new JLabel("");
	lblMensaje.setForeground(Color.RED);
	lblMensaje.setBounds(188, 242, 619, 14);
	getContentPane().add(lblMensaje);

	lblDescripcion = new JLabel();
	lblDescripcion.setText("Descripcion:");
	lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
	lblDescripcion.setBounds(75, 95, 116, 25);
	getContentPane().add(lblDescripcion);

	txtDescripcion = new JTextField();
	txtDescripcion.setColumns(10);
	txtDescripcion.setBounds(213, 95, 158, 25);
	txtDescripcion.setText(evento.getDescripcion());
	getContentPane().add(txtDescripcion);

	lblFechaDesde = new JLabel();
	lblFechaDesde.setText("Fch. Desde:");
	lblFechaDesde.setHorizontalAlignment(SwingConstants.RIGHT);
	lblFechaDesde.setBounds(75, 134, 116, 25);
	getContentPane().add(lblFechaDesde);
	Image img5 = ((ImageIcon) btnGuardar.getIcon()).getImage();
	Image newimg5 = img5.getScaledInstance(32, 32,
		java.awt.Image.SCALE_SMOOTH);

	JLabel lblFchHasta = new JLabel();
	lblFchHasta.setText("Fch. Hasta:");
	lblFchHasta.setHorizontalAlignment(SwingConstants.RIGHT);
	lblFchHasta.setBounds(75, 170, 116, 25);
	getContentPane().add(lblFchHasta);
	
	try{
		mf1 = new MaskFormatter("##:##");
		mf1.setPlaceholderCharacter('_');
		}
		catch(Exception ex){
		    System.out.println(ex);
		}
	
	horaMinutoDesde = new JFormattedTextField(mf1);
	horaMinutoDesde.setToolTipText("hh:mm");
	horaMinutoDesde.setBounds(320, 134, 33, 20);
	horaMinutoDesde.setText(horadeDate(e.getFchDesde()));
	getContentPane().add(horaMinutoDesde);
	
	dateChooserDesde = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
	dateChooserDesde.setBounds(213, 134, 97, 20);
	dateChooserDesde.setDate(evento.getFchDesde());
	getContentPane().add(dateChooserDesde);
	
	dateChooserHasta = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
	dateChooserHasta.setBounds(213, 165, 97, 20);
	dateChooserHasta.setDate(evento.getFchHasta());
	getContentPane().add(dateChooserHasta);
	
	horaMinutoHasta = new JFormattedTextField(mf1);
	horaMinutoHasta.setToolTipText("hh:mm");
	horaMinutoHasta.setBounds(320, 165, 33, 20);
	horaMinutoHasta.setText(horadeDate(e.getFchHasta()));
	getContentPane().add(horaMinutoHasta);

	if (VentanaBuscarEvento.readOnly == true) {
	    btnGuardar.setEnabled(false);
	    btnGuardar
		    .setToolTipText("Ya No se puede Guardar datos durante ni despues la votacion");
	    /*
	     * btnEliminar.setEnabled(false); btnEliminar.setToolTipText(
	     * "Ya No se puede eliminar datos durante ni despues la votacion");
	     * btnModificar.setEnabled(false); btnModificar.setToolTipText(
	     * "Ya No se puede Modificar datos durante ni despues la votacion");
	     */
	}

    }

    private void limpiar() {
	codTemporal = "";
    }

    public void setCoordinador(Coordinador miCoordinador) {
	this.miCoordinador = miCoordinador;
    }

    public void actionPerformed(ActionEvent e) {
	Item item;
	Integer tipoEventoSelected = null;
	if (e.getSource() == btnGuardar) {
	    try {

		if (cmbTipoEvento.getSelectedIndex() != -1) {
		    item = (Item) cmbTipoEvento.getSelectedItem();
		    tipoEventoSelected = item.getId();
		}

		if (!(txtNro.getText().length() == 0)

		&& !(horaMinutoDesde.getText().length() == 0)

		&& !(horaMinutoDesde.getText().length() == 0)

		&& !(horaMinutoHasta.getText().length() == 0)

		&& !(horaMinutoHasta.getText().length() == 0)

		&& !(dateChooserDesde.getDate() == null)

		&& !(txtDescripcion.getText().length() == 0)) {
		    if (txtNro.getText().length() > 7) {
			lblMensaje
				.setText("El codigo debe ser de maximo 7 caracteres.");
			Timer t = new Timer(Login.timer, new ActionListener() {

			    public void actionPerformed(ActionEvent e) {
				lblMensaje.setText(null);
			    }
			});
			t.setRepeats(false);
			t.start();
		    } else

		    if (codTemporal == "") {

			UcsawsTipoEvento tipoEvento = tipoEventoDAO.obtenerTipoEventoById(tipoEventoSelected);
			evento.setUcsawsTipoEvento(tipoEvento);
			evento.setNroEvento(txtNro.getText());
			evento.setDescripcion(txtDescripcion.getText());
			evento.setFchDesde(armarFecha.juntarFecha(dateChooserDesde.getDate(),horaMinutoDesde.getText()));
			evento.setFchHasta(armarFecha.juntarFecha(dateChooserHasta.getDate(),horaMinutoHasta.getText()));
			eventoDAO.actualizarEvento(evento);

			/*recuperarDatos();
			table.setModel(model);
			model.fireTableDataChanged();
			table.removeColumn(table.getColumnModel().getColumn(0));*/
			// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
			lblMensaje
				.setText("Excelente, se ha modificado el Evento.");
			Timer t = new Timer(Login.timer, new ActionListener() {

			    public void actionPerformed(ActionEvent e) {
				lblMensaje.setText(null);
			    }
			});
			t.setRepeats(false);
			t.start();

			// txt
			// .setText("");
			/*codTemporal = "";
			txtNro.setText("");
			txtDescripcion.setText("");
			dateChooserDesde.setDate(new Date());
			dateChooserHasta.setDate(new Date());
			horaMinutoDesde.setText("");
			horaMinutoHasta.setText("");
			cmbTipoEvento.setSelectedIndex(-1);*/
			
			 VentanaBuscarEvento evento = new VentanaBuscarEvento();
			 evento.setVisible(true);
			    this.dispose();

		    }

		    /*if (codTemporal == "") {
			 
			Calendar calendar = new GregorianCalendar();
			int year = calendar.get(Calendar.YEAR);

			ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx
				.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();

			// para update se inserta el codigo es 3
			query.setTipoQueryGenerico(3);
			System.out.println(Login.userLogeado);
			query.setQueryGenerico("UPDATE ucsaws_evento SET "
				+ " nro_evento = upper('" + txtNro.getText()
				+ "'), " + " descripcion = upper('"
				+ txtDescripcion.getText() + "'),"
				+ " id_tipo_evento = " + tipoEventoSelected
				+ "," + " fch_desde =  TO_TIMESTAMP('"
				+ fechaDesdeFinal
				+ "', 'DD/MM/YYYY HH24:MI:SS'),"
				+ " fch_hasta = TO_TIMESTAMP('"
				+ fechaHastaFinal
				+ "', 'DD/MM/YYYY HH24:MI:SS') ,"
				+ " usuario_upd = '" + Login.userLogeado + "',"
				+ " fch_upd = now() " + " WHERE ID_EVENTO = "
				+ ev.getId_evento());

			QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);

			model = new EventoJTableModel();
			recuperarDatos();
			table.setModel(dm);
			model.fireTableDataChanged();
			table.removeColumn(table.getColumnModel().getColumn(0));
			 
			lblMensaje
				.setText("Excelente, se ha Modificado el Evento.");
			Timer t = new Timer(Login.timer, new ActionListener() {

			    public void actionPerformed(ActionEvent e) {
				lblMensaje.setText(null);
			    }
			});
			t.setRepeats(false);
			t.start();

			DateFormat dateFormat;
			Date date;
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			date = new Date();
			System.out.println(dateFormat.format(date));
			txtFchDesde.setText(dateFormat.format(date));

			cmbTipoEvento.setSelectedIndex(-1);
			txtNro.setText("");
			txtDescripcion.setText("");
			txtFchDesde.setText(dateFormat.format(date));
			txtFchHasta.setText(dateFormat.format(date));
			txtHoraDesde.setText("");
			txtHoraHasta.setText("");
			txtMinDesde.setText("");
			txtMinHasta.setText("");
			txtFiltrar.setText("Escriba para Filtrar");
			txtFiltrar.setForeground(Color.LIGHT_GRAY);

			VentanaBuscarEvento be = new VentanaBuscarEvento();
			be.setVisible(true);
			dispose();
		 

		    }*/
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
	    VentanaBuscarEvento persona = new VentanaBuscarEvento();
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

	query.setTipoQueryGenerico(30);

	query.setQueryGenerico("consultarEvento");

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	String res = response.getQueryGenericoResponse();

	// json string to List<String>;
	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();
	List<UcsawsEvento> lista = new ArrayList<UcsawsEvento>();
	try {
	    lista = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsEvento>>() {
		    });
	} catch (Exception e) {
	    System.out.println(e);
	}

	if (lista.isEmpty()) {
	    JOptionPane.showMessageDialog(null, "algo salio mal",
		    "Advertencia", JOptionPane.WARNING_MESSAGE);
	    // return lista;
	}

	else {
	    obtenerModeloA(table, lista);

	    // return lista;
	}

    }*/

    private Vector recuperarDatosComboBoxTipoEvento() {
	Vector model = new Vector();

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(31);

	query.setQueryGenerico("consultarTipoEvento");

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	String res = response.getQueryGenericoResponse();

	// json string to List<String>;
	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();
	List<UcsawsTipoEvento> lista = new ArrayList<UcsawsTipoEvento>();
	try {
	    lista = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsTipoEvento>>() {
		    });
	} catch (Exception e) {
	    System.out.println(e);
	}

	if (lista.isEmpty()) {
	    JOptionPane.showMessageDialog(null, "algo salio mal",
		    "Advertencia", JOptionPane.WARNING_MESSAGE);
	    // return lista;
	}

	else {

	    // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
	    Iterator<UcsawsTipoEvento> ite = lista.iterator();

	    UcsawsTipoEvento aux;

	    while (ite.hasNext()) {
		aux = ite.next();

		model.addElement(new Item(aux.getIdTipoEvento(), aux
			.getDescripcion()));

	    }
	    // return model;
	}

	return model;


    }

    public String armarHoraMinuto(String hora, String minuto) {

	String result = "";

	result = hora + ":" + minuto;

	return result;

    }

    /*public void filter(String query) {

	TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(
		dm);

	table.setRowSorter(tr);

	tr.setRowFilter(RowFilter.regexFilter(query));

    }/*/

    // filtrar tipo evento
    public void filtrarComboEventoTipo() {
	DefaultComboBoxModel dtm = (DefaultComboBoxModel) cmbTipoEvento
		.getModel();

	// System.out.println(dtm.getSize());
	int cont = 0;
	Boolean finded = false;
	int tamanho = dtm.getSize();
	while (cont < dtm.getSize()) {
	    if (dtm.getElementAt(cont).toString()
		    .compareTo(evento.getUcsawsTipoEvento().getDescripcion()) == 0) {
		// Item item = (Item) dtm.getElementAt(cont);
		// Integer tipoListaSelected = item.getId();
		cmbTipoEvento.setSelectedIndex(cont);
		finded = true;
		break;
	    }

	    cont++;

	    // System.out.println(dtm.getElementAt(0));
	}
	if (finded == false) {
	    String a = evento.getUcsawsTipoEvento().getDescripcion();
	    cmbTipoEvento.addItem(a);
	    cmbTipoEvento.setSelectedIndex(tamanho);
	}
    }

    // filtrar tipo evento

    public AbstractTableModel obtenerModeloA(JTable tabla,
	    List<UcsawsEvento> evento) {

	// AbstractTableModel model = (DefaultTableModel) tabla.getModel();
	Iterator<UcsawsEvento> ite = evento.iterator();

	// String header[] = new String[] { "ID","Item","Nro.",
	// "Desc. Evento","Inicio","Fin","Desc. Tipo Evento" };
	// model.setColumnIdentifiers(header);

	UcsawsEvento aux;
	Integer cont = 1;
	while (ite.hasNext()) {
	    aux = ite.next();

	    Object[] row = {
		    aux.getIdEvento(),
		    cont,
		    aux.getNroEvento(),
		    aux.getDescripcion(),
		    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux
			    .getFchDesde())),
		    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux
			    .getFchHasta())),
		    aux.getUcsawsTipoEvento().getDescripcion() };

	    // new
	    // SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
	    model.eventos.add(row);

	    cont++;

	}

	return model;
    }
    
    
    
    public String horadeDate(Date fecha) {
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        String time = localDateFormat.format(fecha);
        System.out.println(time);
	return time;
    }
    
    
}