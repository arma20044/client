package src.main.java.admin.genero;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsGenero;
import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.EventoJTableModel;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.evento.VentanaMainEvento;
import src.main.java.admin.validator.GeneroValidator;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.genero.GeneroDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaRegistroGenero extends JFrame implements ActionListener {

    private Coordinador miCoordinador; // objeto miCoordinador que permite la
				       // relacion entre esta clase y la clase
				       // coordinador
    private JLabel labelTitulo, lblMensaje;
    private JButton botonGuardar, botonCancelar;
    private JTable table;

    private GeneroJTableModel model = new GeneroJTableModel();
    private JScrollPane scrollPane;

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
	botonCancelar.setBounds(774, 383, 32, 32);
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
	setSize(812, 444);
	setTitle("Sistema E-vote: Paraguay Elecciones 2015");
	setLocationRelativeTo(null);
	setResizable(false);
	getContentPane().setLayout(null);

	scrollPane = new JScrollPane();
	scrollPane.setAutoscrolls(true);
	scrollPane.setToolTipText("Lista de Generos");
	scrollPane.setBounds(0, 153, 806, 230);
	getContentPane().add(scrollPane);

	table = new JTable() {
	    @Override
	    public Component prepareRenderer(TableCellRenderer renderer,
		    int row, int column) {
		Component component = super.prepareRenderer(renderer, row,
			column);
		int rendererWidth = component.getPreferredSize().width;
		TableColumn tableColumn = getColumnModel().getColumn(column);
		tableColumn.setPreferredWidth(Math.max(rendererWidth
			+ getIntercellSpacing().width,
			tableColumn.getPreferredWidth()));
		return component;
	    }
	};
	table.getTableHeader().setReorderingAllowed(false);
	table.setToolTipText("");
	//table.setAutoCreateRowSorter(false);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	scrollPane.setViewportView(table);
	table.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		List<String> selectedData = new ArrayList<String>();

		// int selectedRow = table_1.rowAtPoint(arg0.getPoint());

		// Object a =
		// table_1.getModel().getValueAt(table_1.convertRowIndexToView(selectedRow[0]),
		// 0);
		// int[] selectedColumns = table_1.getSelectedColumns();
		// System.out.println(a);

		// if (selectedRow >= 0) {
		int selectedRow = table.rowAtPoint(arg0.getPoint());
		System.out.println(selectedRow);
		int col = 0;
		while (col < table.getColumnCount() + 1) {
		    // System.out.println(table_1.getValueAt(selectedRow,
		    // col));
		    try {
			int row = table.rowAtPoint(arg0.getPoint());
			String table_click0 = table
				.getModel()
				.getValueAt(table.convertRowIndexToModel(row),
					col).toString();
			// System.out.println(table_click0);

			selectedData.add(table_click0);
			System.out.println(selectedData);

		    } catch (Exception e) {
			System.out.println(e.getMessage());
		    }

		    col++;
		}
		// selectedData.ad table_1.getValueAt(selectedRow[i],
		// selectedColumns[0]);
		// txtId.setText(selectedData.get(0));
		//txtCodigo.setText(selectedData.get(2));
		//txtDescripcion.setText(selectedData.get(3));
		// textFecha.setText(selectedData.get(2));
		// textUsu.setText(selectedData.get(4));
		// codTemporal.setText(selectedData.get(1));
		//codTemporal = (selectedData.get(0));

		// System.out.println("Selected: " + selectedData);

	    }
	});
	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	table.setModel(model);

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

	table.removeColumn(table.getColumnModel().getColumn(0));
	recuperarDatos();

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

			model = new GeneroJTableModel();
			recuperarDatos();
			table.setModel(model);
			model.fireTableDataChanged();
			table.removeColumn(table.getColumnModel().getColumn(0));
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
			txtDescripcion.setText("");

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

    private void recuperarDatos() {

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

    }

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