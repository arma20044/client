package src.main.java.admin.persona;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.Logica;
import src.main.java.dao.genero.GeneroDAO;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class VentanaBuscar  extends JFrame implements ActionListener {

	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo;
	private JTextField txtCod,textFecha,textUsu;
	private JLabel lblCod,nombre,profesion;
	private JButton botonGuardar,botonCancelar,botonBuscar,botonModificar,botonEliminar;
	private VentanaBuscar ventanaBuscar;
	private JTextField codTemporal;
	
	JSONArray miPersona = null;
	DefaultTableModel modelo;
	private JTable table_1;
	private CiudadesJTableModel model = new CiudadesJTableModel();
	private JScrollPane scrollPane;
	private JLabel lblID;
	private JTextField txtId;

	
	
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de busqueda
	 */
	public VentanaBuscar() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		botonGuardar = new JButton();
		botonGuardar.setBounds(50, 220, 120, 25);
		botonGuardar.setText("Guardar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(190, 250, 120, 25);
		botonCancelar.setText("Cancelar");
		
		botonBuscar = new JButton();
		botonBuscar.setBounds(327, 52, 50, 25);
		botonBuscar.setText("Ok");
		
		botonEliminar = new JButton();
		botonEliminar.setBounds(330, 220, 120, 25);
		botonEliminar.setText("Eliminar");
		
		botonModificar = new JButton();
		botonModificar.setBounds(190, 220, 120, 25);
		botonModificar.setText("Modificar");

		labelTitulo = new JLabel();
		labelTitulo.setText("ADMINISTRAR GENEROS");
		labelTitulo.setBounds(105, 11, 272, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblCod=new JLabel();
		lblCod.setText("Codigo");
		lblCod.setBounds(20, 52, 80, 25);
		getContentPane().add(lblCod);
		
		nombre=new JLabel();
		nombre.setText("Fch. Insercion:");
		nombre.setBounds(20, 120, 113, 25);
		getContentPane().add(nombre);
		
		profesion=new JLabel();
		profesion.setText("Usuario Insercion:");
		profesion.setBounds(20, 160, 113, 25);
		getContentPane().add(profesion);
		
		txtCod=new JTextField();
		txtCod.setBounds(140, 52, 170, 25);
		getContentPane().add(txtCod);
		
		textFecha=new JTextField();
		textFecha.setBounds(143, 120, 167, 25);
		getContentPane().add(textFecha);
		
		textUsu=new JTextField();
		textUsu.setBounds(143, 160, 167, 25);
		getContentPane().add(textUsu);
		
		botonModificar.addActionListener(this);
		botonEliminar.addActionListener(this);
		botonBuscar.addActionListener(this);
		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);

		getContentPane().add(botonCancelar);
		getContentPane().add(botonBuscar);
		getContentPane().add(botonModificar);
		getContentPane().add(botonEliminar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
				
		setSize(991, 320);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		codTemporal = new JTextField();
		codTemporal.setText("");
		codTemporal.setEditable(true);
		codTemporal.setBounds(346, 80, 80, 25);
		codTemporal.hide();
		getContentPane().add(codTemporal);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(453, 32, 532, 259);
		getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		table_1.setToolTipText("Listado de Generos.");
		table_1.setAutoCreateRowSorter(true);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPane.setViewportView(table_1);
		//String[] columnNames = {"Picture", "Description"};
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				 List<String> selectedData= new ArrayList<String>();

			        int[] selectedRow = table_1.getSelectedRows();
			        //int[] selectedColumns = table_1.getSelectedColumns();

			        for (int i = 0; i < selectedRow.length; i++) {
			            int col= 0;
			        	while (table_1.getColumnCount() > col){
			        		 System.out.println(table_1.getValueAt(selectedRow[i], col));
			        		 try{
			        			 selectedData.add( (String) table_1.getValueAt(selectedRow[i], col));
			        		 }
			        		 catch (Exception e){
			        			 System.out.println(e.getMessage());
			        		 }
			        		
			        		
			        		col++;
			        	}
			           // selectedData.ad table_1.getValueAt(selectedRow[i], selectedColumns[0]);
			        	txtId.setText(selectedData.get(0));
			        	txtCod.setText(selectedData.get(1));
			        	textFecha.setText(selectedData.get(2));
			        	textUsu.setText(selectedData.get(4));
			        	codTemporal.setText(selectedData.get(1));
			          
			        }
			        System.out.println("Selected: " + selectedData);
				
			}
		});
		table_1.setModel(model);
		
		JLabel lblListaDeGeneros = new JLabel();
		lblListaDeGeneros.setText("LISTA DE GENEROS");
		lblListaDeGeneros.setFont(new Font("Verdana", Font.BOLD, 18));
		lblListaDeGeneros.setBounds(487, 0, 213, 30);
		getContentPane().add(lblListaDeGeneros);
		
		lblID = new JLabel();
		lblID.setText("ID:");
		lblID.setBounds(20, 88, 44, 25);
		getContentPane().add(lblID);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setText("");
		txtId.setBounds(140, 84, 170, 25);
		getContentPane().add(txtId);
		
		//table_1.getColumnModel().getColumn(0).setHeaderValue("Descripcion");
		
		
	    ListSelectionModel cellSelectionModel = table_1.getSelectionModel();
		recuperarDatos();
	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    
	    
	    
//	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
//	      public void valueChanged(ListSelectionEvent e) {
//	        String selectedData = null;
//
//	        int[] selectedRow = table_1.getSelectedRows();
//	        int[] selectedColumns = table_1.getSelectedColumns();
//
//	        for (int i = 0; i < selectedRow.length; i++) {
//	          for (int j = 0; j < selectedColumns.length; j++) {
//	            selectedData = (String) table_1.getValueAt(selectedRow[i], selectedColumns[j]);
//	          }
//	        }
//	        System.out.println("Selected: " + selectedData);
//	      }
//
//	    });


		
		
		DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm:ss");

	}


	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==botonGuardar)
		{
			try {
//				PersonaVo miPersona=new PersonaVo();
//				miPersona.setIdPersona(Integer.parseInt(textCod.getText()));
//				miPersona.setNombrePersona(textNombre.getText());
//				miPersona.setTelefonoPersona(Integer.parseInt(textTelefono.getText()));
//				miPersona.setEdadPersona(Integer.parseInt(textEdad.getText()));
//				miPersona.setProfesionPersona(textProfesion.getText());
//
				GeneroDAO generoDAO = new GeneroDAO();
				
				generoDAO.modificarGenero(txtCod.getText(), codTemporal.getText());
				
				if (Logica.modificaPersona==true) {
					habilita(true, false, false, false, false, true, false, true, true);	
					//table.setEnabled(true);

					
					
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null,"Error en el Ingreso de Datos","Error",JOptionPane.ERROR_MESSAGE);
			}
			//this.dispose();
			model = new CiudadesJTableModel();
			recuperarDatos();
			table_1.setModel(model);
			model.fireTableDataChanged();
			
			
			LimpiarCampos();
			
			botonGuardar.setEnabled(false);
			botonModificar.setEnabled(true);
			botonBuscar.setEnabled(true);
			
			
			
			
		}
		
		if (e.getSource()==botonBuscar)
		{
			String ge = txtCod.getText();
			
			GeneroDAO generoDAO = new GeneroDAO();
			
			
			
				try {
					miPersona = generoDAO.buscarGenero(txtCod.getText());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (org.json.simple.parser.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
			if (miPersona.size() > 0)
			{
				
				
			muestraPersona(miPersona);
			
			try {
//				VentanaVer ver = new VentanaVer(miPersona);
//				ver.setVisible(true);
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.print(e2);
			}
			
			
			
			modelo = new DefaultTableModel();
			JSONArray a =  (JSONArray) miPersona.get(0);
			
			
		
			}
			
			model = new CiudadesJTableModel();
			recuperarDatos();
			table_1.setModel(model);
			model.fireTableDataChanged();
		}
		
		if (e.getSource()==botonModificar)
		{
			habilita(true, false, false, true, false, false, true, false, false);
			
		}
		
		if (e.getSource()==botonEliminar)
		{
			if (!txtCod.getText().equals(""))
			{
				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar la Persona?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)
				{
					GeneroDAO generoDAO = new GeneroDAO();
					
					generoDAO.eliminarGenero(txtCod.getText());
					//modificarGenero(textCod.getText(), codTemporal.getText());
					limpiar();
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Ingrese un numero de Documento", "Información",JOptionPane.WARNING_MESSAGE);
			}
			
			model = new CiudadesJTableModel();
			recuperarDatos();
			table_1.setModel(model);
			model.fireTableDataChanged();
			
		}
		if (e.getSource()==botonCancelar)
		{
			this.dispose();
		}

	}



	/**
	 * permite cargar los datos de la persona consultada
	 * @param miPersona
	 */
	private void muestraPersona(JSONArray genero) {
		JSONArray a = (JSONArray) genero.get(0);
		txtId.setText((String) a.get(0));
		txtCod.setText((String) a.get(1));
		textFecha.setText((String) a.get(2));
		textUsu.setText((String) a.get(4));
		codTemporal.setText((String) a.get(1));
		
			
		
		habilita(true, false, false, false, false, true, false, true, true);
	}


	/**
	 * Permite limpiar los componentes
	 */
	public void limpiar()
	{
		txtCod.setText("");
		textFecha.setText("");
		textUsu.setText("");
		//codTemporal.setText("");
		habilita(true, false, false, false, false, true, false, true, true);
	}


	/**
	 * Permite habilitar los componentes para establecer una modificacion
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
	public void habilita(boolean codigo, boolean nombre, boolean edad, boolean tel, boolean profesion,	 boolean bBuscar, boolean bGuardar, boolean bModificar, boolean bEliminar)
	{
		txtCod.setEditable(codigo);
		textFecha.setEditable(nombre);
		textUsu.setEditable(profesion);
		botonBuscar.setEnabled(bBuscar);
		botonGuardar.setEnabled(bGuardar);
		botonModificar.setEnabled(bModificar);
		botonEliminar.setEnabled(bEliminar);
	}


	public VentanaBuscar getVentanaBuscar() {
		return ventanaBuscar;
	}


	public void setVentanaBuscar(VentanaBuscar ventanaBuscar) {
		this.ventanaBuscar = ventanaBuscar;
	}
	
	private void recuperarDatos(){
		JSONArray filas = new JSONArray();
		JSONArray fil = new JSONArray();
		
		Genero gen = new Genero();
		
		
		boolean existe=false;
		
		
			//Statement estatuto = conex.getConnection().createStatement();
		
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			//para registrar se inserta el codigo es 1
			query.setTipoQueryGenerico(2);
			
			query.setQueryGenerico("SELECT id_persona, nombre, apellido, fecha_nacimiento, id_pais_origen, "
       + "id_pais_actual, fch_ins, fch_upd, usuario_ins, usuario_upd, id_genero, "
       +"ci, tel_linea_baja, tel_celular FROM ucsaws_persona");
			
			
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("ERRORRRRRRR")==0){
					JOptionPane.showMessageDialog(null, "algo salio mal","Advertencia",JOptionPane.WARNING_MESSAGE);
					
				}
				
				else
			{
				existe=true;
				
				
				
				String generoAntesPartir = response.getQueryGenericoResponse();
				
				JSONParser j = new JSONParser();
				Object ob = null;
				String part1,part2,part3;
				
					
						try {
							ob = j.parse(generoAntesPartir);
						} catch (org.json.simple.parser.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					filas = (JSONArray) ob;
					
					
		
				
			}
			
			
				int ite = 0; 
				String campo4,campo5 = "";
				while(filas.size() >  ite ){
					 fil = (JSONArray) filas.get(ite);
					 
					 
					 
					 String[] fin
						 = {fil.get(0).toString(),  fil.get(1).toString(),  fil.get(2).toString()
							 ,
							 fil.get(3).toString(),  fil.get(4).toString() , fil.get(5).toString()
					 };
					 
					 model.ciudades.add(fin);
					ite++;
		}
		
	}
	
	void LimpiarCampos(){
		txtCod.setText("");
		textFecha.setText("");
		textUsu.setText("");
		codTemporal.setText("");
		txtId.setText("");
		
		
	}
}
