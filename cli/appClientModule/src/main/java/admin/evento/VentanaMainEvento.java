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
	private JLabel label_4;
	private JButton btnDefinicionesGenerales;

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
		botonCancelar.setBounds(1267, 422, 45, 25);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);


		labelTitulo = new JLabel();
		labelTitulo.setText("MAIN EVENTO");
		labelTitulo.setBounds(369, 103, 270, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));
		botonCancelar.addActionListener(this);

		getContentPane().add(botonCancelar);
		getContentPane().add(labelTitulo);
		limpiar();

		setSize(1318, 476);
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

		lblNroEvento = new JLabel("Nro. Evento:");
		lblNroEvento.setBounds(21, 11, 94, 14);
		getContentPane().add(lblNroEvento);

		System.out.println(nroEvento);

		label = new JLabel(nroEvento);
		label.setForeground(Color.RED);
		label.setBounds(172, 11, 214, 14);
		getContentPane().add(label);
		
		lblDescripcionEvento = new JLabel("Descripcion Evento:");
		lblDescripcionEvento.setBounds(21, 36, 129, 14);
		getContentPane().add(lblDescripcionEvento);
		
		lblFechaDesde = new JLabel("Fecha Desde:");
		lblFechaDesde.setBounds(516, 8, 94, 14);
		getContentPane().add(lblFechaDesde);
		
		lblFechaHasta = new JLabel("Fecha Hasta");
		lblFechaHasta.setBounds(516, 39, 94, 14);
		getContentPane().add(lblFechaHasta);
		
		lblTipoEvento = new JLabel("Tipo Evento:");
		lblTipoEvento.setBounds(21, 72, 94, 14);
		getContentPane().add(lblTipoEvento);
		
		label_1 = new JLabel(descripcionEvento);
		label_1.setForeground(Color.RED);
		label_1.setBounds(172, 36, 214, 14);
		getContentPane().add(label_1);
		
		label_2 = new JLabel(fechaDesde);
		label_2.setForeground(Color.RED);
		label_2.setBounds(649, 8, 214, 14);
		getContentPane().add(label_2);
		
		label_3 = new JLabel(fechaHasta);
		label_3.setForeground(Color.RED);
		label_3.setBounds(649, 39, 214, 14);
		getContentPane().add(label_3);
		
		label_4 = new JLabel(tipoEventoDescripcon);
		label_4.setForeground(Color.RED);
		label_4.setBounds(107, 72, 214, 14);
		getContentPane().add(label_4);
		
		btnDefinicionesGenerales = new JButton("Definiciones Generales");
		btnDefinicionesGenerales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefinicionesGenerales definiciones = new DefinicionesGenerales();
				definiciones.setVisible(true);
				dispose();
			}
		});
		btnDefinicionesGenerales.setBounds(145, 156, 200, 50);
		getContentPane().add(btnDefinicionesGenerales);
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

		query.setQueryGenerico("select id_evento, nro_evento,ev.descripcion, fch_desde, fch_hasta , tev.descripcion as Tdescripcion "

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
			
			fechaDesde  = (String) fil.get(3);
			fechaHasta  = (String) fil.get(4);
			tipoEventoDescripcon =  (String) fil.get(5);

			model.ciudades.add(fin);
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
