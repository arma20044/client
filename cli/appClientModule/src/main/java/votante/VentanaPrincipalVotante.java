package src.main.java.votante;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaMainEvento;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.EleccionMesa;
import src.main.java.login.Login;
import src.main.java.proceso.voto.VentanaPresidente;

public class VentanaPrincipalVotante extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo;
	private JButton botonCancelar, btnNewButton;

	public static String votante;
	
	public static Integer cedulaVotante;
	
	public static Integer idVotanteHabilitado;
	
	public static Integer idVotante;

	public final static int INTERVAL = 5000;
	
	public Timer timer;

	JSONArray miPersona = null;
	DefaultTableModel modelo;
	private JTable table_1;
	private VotanteJTableModel model = new VotanteJTableModel();
	private JScrollPane scrollPane;

	private String codTemporal = "";

	private JLabel lblMensaje;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de busqueda
	 */
	public VentanaPrincipalVotante() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		labelTitulo = new JLabel();
		labelTitulo.setText("BIENVENIDO - FAVOR SELECCIONE SU NOMBRE");
		labelTitulo.setBounds(66, 24, 518, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));
		// botonCancelar.addActionListener(this);

		// getContentPane().add(botonCancelar);
		getContentPane().add(labelTitulo);
		limpiar();

		setSize(640, 476);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("SELECCIONE SU NOMBRE");
		scrollPane.setBounds(0, 158, 634, 265);
		getContentPane().add(scrollPane);

		table_1 = new JTable() {
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
		table_1.setToolTipText("SELECCIONE SU NOMBRE");
		table_1.setAutoCreateRowSorter(true);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table_1);
		// String[] columnNames = {"Picture", "Description"};
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
				List<String> selectedData = new ArrayList<String>();

				int[] selectedRow = table_1.getSelectedRows();
				// int[] selectedColumns = table_1.getSelectedColumns();

				for (int i = 0; i < selectedRow.length; i++) {
					int col = 0;
					while (table_1.getColumnCount() > col) {
						System.out.println(table_1.getValueAt(selectedRow[i],
								col));
						VentanaPrincipalVotante.cedulaVotante = Integer.parseInt((String) (table_1.getValueAt(0, 0)));
						try {
							selectedData.add((String) table_1.getValueAt(
									selectedRow[i], col));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}

						col++;
					}
					// selectedData.ad table_1.getValueAt(selectedRow[i],
					// selectedColumns[0]);
					// txtId.setText(selectedData.get(0));
					// txtBuscar.setText(selectedData.get(0));

					// textFecha.setText(selectedData.get(2));
					// textUsu.setText(selectedData.get(4));
					// codTemporal.setText(selectedData.get(1));
					codTemporal = (String) (table_1.getModel().getValueAt(
							selectedRow[i], 0));

					votante = (String) (table_1.getModel().getValueAt(
							selectedRow[i], 0));

					System.out.println(votante);

					VentanaPresidente main = new VentanaPresidente();
					main.setVisible(true);
					
					dispose();

				}
				System.out.println("Selected: " + selectedData);
				
				stopTimer();

			}
		});
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table_1.setModel(model);
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(57, 88, 432, 14);
		getContentPane().add(lblMensaje);

		JLabel lblParaIniciarSu = new JLabel();
		lblParaIniciarSu.setText("PARA INICIAR SU VOTACIÓN");
		lblParaIniciarSu.setFont(new Font("Verdana", Font.BOLD, 18));
		lblParaIniciarSu.setBounds(139, 65, 314, 30);
		getContentPane().add(lblParaIniciarSu);

		// table_1.getColumnModel().getColumn(0).setHeaderValue("Descripcion");

		ListSelectionModel cellSelectionModel = table_1.getSelectionModel();
		recuperarDatos();
		cellSelectionModel
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

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

		timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				// recuperarDatos();
				// Refresh the panel

			

				recuperarDatos();
				//table_1.setModel(model);
			//	table_1.tableChanged(e);
				table_1.removeAll();
				table_1.revalidate();
			//	table_1.repaint();

				// revalidate();
				// repaint(table_1);
				// if (/* condition to terminate the thread. */) {
				// timer.stop();
				// }
			}
		});

		timer.start();

	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
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

	private void recuperarDatos() {
		
		Integer idMesa = obtenerMesa(EleccionMesa.Mesa, EleccionMesa.evento, Integer.parseInt(EleccionMesa.local));
		
		model.ciudades = new  ArrayList<Object[]>();
		
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

		query.setQueryGenerico("select id_votante, ci, nombre, apellido from ucsaws_votante vo "
								+ " join ucsaws_persona per on (vo.id_persona = per.id_persona)where  "
								+ "habilitado = 1 and sufrago = 0 and vo.id_evento = " + EleccionMesa.idEvento +
								" and id_mesa = " + idMesa);

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
			
			//idVotanteHabilitado = Integer.parseInt(fil.get(0).toString());
			
			idVotante = Integer.parseInt(fil.get(0).toString());

			String[] fin = { fil.get(0).toString(), fil.get(1).toString(),
					fil.get(2).toString(), fil.get(3).toString() };

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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	 
     public void stopTimer() {
         Toolkit.getDefaultToolkit().beep();
         if (timer.isRunning()){
        	 timer.stop();
        	          }
         
     }
     
 	//Metodo para obtener el ID de la Mesa en donde se voto
 	private Integer obtenerMesa(int nroMesa, Integer idEvento, Integer idLocal) {

 		JSONArray filas = new JSONArray();
 		JSONArray fil = new JSONArray();

 		Object ob = null;

 		ApplicationContext ctx = SpringApplication
 				.run(WeatherConfiguration.class);

 		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
 		QueryGenericoRequest query = new QueryGenericoRequest();

 		// para registrar se inserta el codigo es 1
 		query.setTipoQueryGenerico(2);
 		System.out.println(Login.userLogeado);
 		query.setQueryGenerico("select id_mesa, desc_mesa, id_local, id_evento"
 				+ " from ucsaws_mesa" + " where nro_mesa = " + nroMesa
 				+ " and id_evento = " + idEvento
 				+ " and  id_local = " + idLocal);

 		QueryGenericoResponse response = weatherClient
 				.getQueryGenericoResponse(query);
 		weatherClient.printQueryGenericoResponse(response);

 		JSONParser j = new JSONParser();

 		String generoAntesPartir = response.getQueryGenericoResponse();

 		try {
			ob = j.parse(generoAntesPartir);
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 		filas = (JSONArray) ob;

 		fil = (JSONArray) filas.get(0);
 		
 		idLocal =  Integer.parseInt(fil.get(2).toString());
 		
 		idEvento = Integer.parseInt(fil.get(3).toString());
 		
 		String result = fil.get(0).toString();

 		return Integer.parseInt(result);

 	}
     
     
}
