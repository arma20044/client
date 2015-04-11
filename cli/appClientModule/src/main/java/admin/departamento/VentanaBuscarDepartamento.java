package src.main.java.admin.departamento;

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
import src.main.java.dao.departamento.DepartamentoDAO;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class VentanaBuscarDepartamento  extends JFrame implements ActionListener {

	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo;
	private JTextField txtNroDpto,txtFchIns,txtUsuIns;
	private JLabel lblNroDpto,lblFchIns,lblUsuIns;
	private JButton botonGuardar,botonCancelar,botonBuscar,botonModificar,botonEliminar;
	private VentanaBuscarDepartamento ventanaBuscar;
	private JTextField codTemporal;
	
	JSONArray miPersona = null;
	DefaultTableModel modelo;
	private JTable table_1;
	private CiudadesJTableModel model = new CiudadesJTableModel();
	private JScrollPane scrollPane;
	private JLabel lblNombreDpto;
	private JTextField txtNombre;

	
	
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de busqueda
	 */
	public VentanaBuscarDepartamento() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		botonGuardar = new JButton();
		botonGuardar.setBounds(462, 88, 120, 25);
		botonGuardar.setText("Guardar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(607, 120, 120, 25);
		botonCancelar.setText("Cancelar");
		
		botonBuscar = new JButton();
		botonBuscar.setBounds(327, 52, 50, 25);
		botonBuscar.setText("Ok");
		
		botonEliminar = new JButton();
		botonEliminar.setBounds(462, 120, 120, 25);
		botonEliminar.setText("Eliminar");
		
		botonModificar = new JButton();
		botonModificar.setBounds(607, 88, 120, 25);
		botonModificar.setText("Modificar");

		labelTitulo = new JLabel();
		labelTitulo.setText("ADMINISTRAR DEPARTAMENTOS.");
		labelTitulo.setBounds(105, 11, 359, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblNroDpto=new JLabel();
		lblNroDpto.setText("Nro. Dpto.:");
		lblNroDpto.setBounds(20, 52, 80, 25);
		getContentPane().add(lblNroDpto);
		
		lblFchIns=new JLabel();
		lblFchIns.setText("Fch. Insercion:");
		lblFchIns.setBounds(20, 120, 113, 25);
		getContentPane().add(lblFchIns);
		
		lblUsuIns=new JLabel();
		lblUsuIns.setText("Usuario Insercion:");
		lblUsuIns.setBounds(20, 160, 113, 25);
		getContentPane().add(lblUsuIns);
		
		txtNroDpto=new JTextField();
		txtNroDpto.setBounds(140, 52, 170, 25);
		getContentPane().add(txtNroDpto);
		
		txtFchIns=new JTextField();
		txtFchIns.setBounds(143, 120, 167, 25);
		getContentPane().add(txtFchIns);
		
		txtUsuIns=new JTextField();
		txtUsuIns.setBounds(143, 160, 167, 25);
		getContentPane().add(txtUsuIns);
		
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
				
		setSize(889, 476);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		codTemporal = new JTextField();
		codTemporal.setText("");
		codTemporal.setEditable(true);
		codTemporal.setBounds(415, 40, 80, 25);
		codTemporal.hide();
		getContentPane().add(codTemporal);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 245, 883, 200);
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
			        	
			        	txtNombre.setText(selectedData.get(1));
			        	txtNroDpto.setText(selectedData.get(2));
			        	txtFchIns.setText(selectedData.get(3));
			        	txtUsuIns.setText(selectedData.get(4));
			        	codTemporal.setText(selectedData.get(2));
			          
			        }
			        System.out.println("Selected: " + selectedData);
				
			}
		});
		table_1.setModel(model);
		
		JLabel lblListaDeGeneros = new JLabel();
		lblListaDeGeneros.setText("LISTA DE DEPARTAMENTOS.");
		lblListaDeGeneros.setFont(new Font("Verdana", Font.BOLD, 18));
		lblListaDeGeneros.setBounds(232, 204, 308, 30);
		getContentPane().add(lblListaDeGeneros);
		
		lblNombreDpto = new JLabel();
		lblNombreDpto.setText("Nombre:");
		lblNombreDpto.setBounds(20, 88, 44, 25);
		getContentPane().add(lblNombreDpto);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setText("");
		txtNombre.setBounds(140, 84, 170, 25);
		getContentPane().add(txtNombre);
		
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
				DepartamentoDAO departamentoDAO = new DepartamentoDAO();
				
				departamentoDAO.modificarDepartamento(txtNroDpto.getText(),  codTemporal.getText() , txtNombre.getText());
				
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
			
			txtNombre.setEditable(false);
			
			
		}
		
		if (e.getSource()==botonBuscar)
		{
			String ge = txtNroDpto.getText();
			
			DepartamentoDAO departamentoDAO = new DepartamentoDAO();
			
			
			
				try {
					miPersona = departamentoDAO.buscarDepartamento(Integer.getInteger(txtNroDpto.getText())   );
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
			txtNombre.setEditable(true);
			
		}
		
		if (e.getSource()==botonEliminar)
		{
			if (!txtNroDpto.getText().equals(""))
			{
				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar la Persona?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)
				{
					DepartamentoDAO departamentoDAO = new DepartamentoDAO();
					
					departamentoDAO.eliminarDepartamento(txtNroDpto.getText());
					//modificarGenero(textCod.getText(), codTemporal.getText());
					txtNombre.setText("");
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
		txtNombre.setText(Long.toString( (Long) a.get(0))   );
		txtNroDpto.setText((String) a.get(1));
		txtFchIns.setText((String) a.get(2));
		txtUsuIns.setText((String) a.get(4));
		codTemporal.setText((String) a.get(1));
		
			
		
		habilita(true, false, false, false, false, true, false, true, true);
	}


	/**
	 * Permite limpiar los componentes
	 */
	public void limpiar()
	{
		txtNroDpto.setText("");
		txtFchIns.setText("");
		txtUsuIns.setText("");
		
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
		txtNroDpto.setEditable(codigo);
		
		txtFchIns.setEditable(nombre);
		txtUsuIns.setEditable(profesion);
		botonBuscar.setEnabled(bBuscar);
		botonGuardar.setEnabled(bGuardar);
		botonModificar.setEnabled(bModificar);
		botonEliminar.setEnabled(bEliminar);
	}


	public VentanaBuscarDepartamento getVentanaBuscar() {
		return ventanaBuscar;
	}


	public void setVentanaBuscar(VentanaBuscarDepartamento ventanaBuscar) {
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
			
			query.setQueryGenerico("SELECT id_departamento, desc_departamento, nro_departamento, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
					+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento "
					+ "order by nro_departamento");
			
			
			
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
							 fil.get(3).toString(),  fil.get(4).toString() , fil.get(5).toString(),
							 fil.get(6).toString()
					 };
					 
					 model.ciudades.add(fin);
					ite++;
		}
		
	}
	
	void LimpiarCampos(){
		txtNroDpto.setText("");
		txtFchIns.setText("");
		txtUsuIns.setText("");
		codTemporal.setText("");
		txtNombre.setText("");
		
		
	}
}
