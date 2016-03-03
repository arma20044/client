package src.main.java.admin.escrutinio;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.Escrutinio;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.Dhondt;
import src.main.java.dao.listas.ListasDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaEscrutinioPresidente extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo;
	private JButton botonCancelar, botonBuscar;

	JSONArray miPersona = null;
	DefaultTableModel modelo;
	//private PaisJTableModel model = new PaisJTableModel();

	private String codTemporal = "";

	private JLabel lblMensaje;
	JLabel lblCandidato1;
	JLabel lblCandidato2,lblCandidato3;
	JLabel lblCandidato4;
	JButton btnComenzar;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de busqueda
	 */
	public VentanaEscrutinioPresidente() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		

		botonCancelar = new JButton();
		botonCancelar.setIcon(new ImageIcon(VentanaEscrutinioPresidente.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setToolTipText("Atrás");
		botonCancelar.setBounds(589, 422, 45, 25);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg));

		botonBuscar = new JButton();
		botonBuscar.setToolTipText("Buscar");
		botonBuscar.setIcon(new ImageIcon(VentanaEscrutinioPresidente.class
				.getResource("/imgs/search.png")));
		botonBuscar.setBounds(415, 52, 32, 32);
		botonBuscar.setOpaque(false);
		botonBuscar.setContentAreaFilled(false);
		botonBuscar.setBorderPainted(false);
		Image img3 = ((ImageIcon) botonBuscar.getIcon()).getImage();
		Image newimg3 = img3.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonBuscar.setIcon(new ImageIcon(newimg3));
		

		labelTitulo = new JLabel();
		labelTitulo.setText("ABM DE LISTA");
		labelTitulo.setBounds(248, 11, 270, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));
		botonBuscar.addActionListener(this);
		botonCancelar.addActionListener(this);

		getContentPane().add(botonCancelar);
		getContentPane().add(botonBuscar);
		getContentPane().add(labelTitulo);
		limpiar();

		setSize(640, 476);
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
		btnHome.setIcon(new ImageIcon(VentanaEscrutinioPresidente.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img5 = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg5 = img5.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg5));
		getContentPane().add(btnHome);
	

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(51, 95, 432, 14);
		getContentPane().add(lblMensaje);
		
		btnComenzar = new JButton("Comenzar");
		btnComenzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Dhondt d = 	new Dhondt();
				
				
				
				
			       System.out.println("Hello, D'Hondt");
			   	int chairs =80;
			   	double []votes1997 =  new double[]  {28, 20, 24, 18, 6, 5,3,2,2};
			   	String[] party1997 = {"UUP","DUP","SDLP", "SF","Alliance", "IndU", "UUUC", "WC", "PUP"};
			   //	double []votes =  new double[]  {16, 38, 14, 29, 8, 1, 1, 1};
//			   	double[] votes =  new double[]{12.9, 29.3, 13.9, 26.3, 7.7, 2.4, 0.9, 6.6};
			   	//String[] party = {"UUP","DUP","SDLP", "SF","Alliance", "IndU", "Green", "Ind"};
			   	List<String> CandidatoVotos =  ObtenerListaCandidatosYVotos();
			   	int cont= 0;
			   	int cont2=0;
			   	List<String> party = new ArrayList<String>();
			   	while (CandidatoVotos.size() > cont){
			   		
			   		String a = CandidatoVotos.get(cont);
			   		String[] parts = a.split("-");
			   		String part1 = parts[0]; // 004
			   		//String part2 = parts[1]; // 034556
			   		
			   		party.add(part1);
			   		
			   		cont++;
			   	}
			   	

			   	
			   //	double []votes =  new double[]  {16, 38};
			   	
			   	
			   	double[] votes = new double[CandidatoVotos.size()];
			   	
			   	while (CandidatoVotos.size() > cont2) {
			   		
			   		String a = CandidatoVotos.get(cont2);
			   		String[] parts = a.split("-");
			   		//String part1 = parts[0]; // 004
			   		String part2 = parts[1]; // 034556
			   		
			   		votes[cont2] = Double.parseDouble(part2);
			   	   // double val = Double.parseDouble(i);
			   	   // nums[i] = val;  
			   		cont2++;
			   	}
			 //  	db_results.add(nums);

			   	int [] allocated = new int[votes.length];
			   	double [][] dhondtTable = new double [chairs][votes.length];
			   	System.out.println("Dhondt Table chairs="+chairs+ " parties= " +votes.length+ "\n");
			   // Build D'Hondt Table
			   	for (int m = 0; m < chairs; m++) {
			   		for (int n = 0; n < votes.length; n++) {
			   			if (m == 0)
			   				dhondtTable[m][n] = votes[n];
			   			else
			   				dhondtTable[m][n] = dhondtTable[0][n]/(m+1);
			   		}
			   	}
			   //Print Table
			           System.out.println("Table for D'Hondt");
			   	d.DrawDhondtTable(dhondtTable,votes.length, chairs);

			   /*Allocate Ministers using D'Hondt
			    * iterate through the number of cabinet posts to allocate
			    * find the highest value in the D'Hondt Table 
			    */
			   	int o = 0;
			   	for (int c= 1; c < chairs+1; c++) {
			   		o = d.getMaxElement(dhondtTable,votes.length, chairs);
			   		d.DrawDhondtTable(dhondtTable,votes.length, chairs);
			   	 	System.out.println(" Seat " + c + " for " + party.get(o));
			   		allocated[o] = allocated[o] + 1;
			   	}

			   	d.DrawDhondtTable(dhondtTable,votes.length, chairs);
			   	int ban= 0;
			   //Output Results
			   	for (int p=0; p < votes.length; p++) {
			   		
			   		if(ban == 0){
			   		System.out.println(party.get(p) + " obtuvo " + allocated [p] + " Escaño(s)");
			   		lblCandidato1.setText(party.get(p) + " obtuvo " + allocated [p] + " Escaño(s)");
			   		ban++;
			   		}
			   		else
			   		if(ban == 1){
				   		System.out.println(party.get(p) + " obtuvo " + allocated [p] + " Escaño(s)");
				   		lblCandidato2.setText(party.get(p) + " obtuvo " + allocated [p] + " Escaño(s)");
				   		ban++;
				   		}
			   		else
			   		if(ban == 3){
				   		System.out.println(party.get(p) + " obtuvo " + allocated [p] + " Escaño(s)");
				   		lblCandidato3.setText(party.get(p) + " obtuvo " + allocated [p] + " Escaño(s)");
				   		ban++;
				   		}
			   		else
			   		if(ban == 4){
			   			System.out.println(party.get(p) + " obtuvo " + allocated [p] + " Escaño(s)");
				   		lblCandidato4.setText(party.get(p) + " obtuvo " + allocated [p] + " Escaño(s)");
				   		ban++;
				   		}
			   	}
			   	btnComenzar.setText("Procesar de Nuevo?.");
			   	btnComenzar.setSize(145, 23);
			   	
			}
		});
		btnComenzar.setBounds(397, 375, 89, 23);
		getContentPane().add(btnComenzar);
		
		lblCandidato1 = new JLabel("");
		lblCandidato1.setBounds(51, 169, 517, 32);
		getContentPane().add(lblCandidato1);
		
		lblCandidato2 = new JLabel("");
		lblCandidato2.setBounds(51, 212, 517, 32);
		getContentPane().add(lblCandidato2);
		
		lblCandidato3 = new JLabel("");
		lblCandidato3.setBounds(51, 255, 517, 32);
		getContentPane().add(lblCandidato3);
		
		lblCandidato4 = new JLabel("");
		lblCandidato4.setBounds(51, 289, 517, 32);
		getContentPane().add(lblCandidato4);
		recuperarDatos();


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

		if (e.getSource() == botonBuscar) {
			//String ge = txtBuscar.getText();

			ListasDAO listasDAO = new ListasDAO();

			
		}
		if (e.getSource() == botonCancelar) {
			Escrutinio escr = new Escrutinio();
			escr.setVisible(true);
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
		//txtBuscar.setText((String) a.get(1));
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
		botonBuscar.setEnabled(bBuscar);
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
		query.setTipoQueryGenerico(2);

		query.setQueryGenerico("SELECT  id_lista, nro_lista, nombre_lista, anho, tlis.descripcion "
				+ "from  ucsaws_listas lis join ucsaws_tipo_lista tlis on "
				+ "( lis.id_tipo_lista = tlis.id_tipo_lista)" + "order by tlis.descripcion, nro_lista" + "");

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
		int contador = 0;


	}

	void LimpiarCampos() {
		//txtBuscar.setText("");
		// textFecha.setText("");
		// textUsu.setText("");
		codTemporal = "";
		// txtId.setText("");

	}
	List<String> ObtenerListaCandidatosYVotos(){
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

		query.setQueryGenerico("select 'Lista N° ' || nro_lista || ' ' || nombre_lista as a, count(id_voto)  from ucsaws_votos vo join ucsaws_listas li on (vo.id_lista = li.id_lista) " 
				+ " join ucsaws_tipo_lista tli on (li.id_tipo_lista = tli.id_tipo_lista) where descripcion = 'DIPUTADO' and id_evento = " +
				VentanaBuscarEvento.evento + " group by a" );

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
		List<String> result = new ArrayList<String>();
		int contador = 0;
		
		List<String> fin = new ArrayList<String>();
		
		while (filas.size() > ite) {
			
			contador = contador + 1  ;
			
			fil = (JSONArray) filas.get(ite);

			
		//	Arrays.toString()
			
			
			
			fin.add(fil.get(0).toString() + "-" + fil.get(1).toString() );
			//= { fil.get(1).toString() };

			//result.
			//model.ciudades.add(fin);
			ite++;
			System.out.println(fin);
		}
		return fin;
	}
	
	static <T> T[] append(T[] arr, T element) {
	    final int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}
}
