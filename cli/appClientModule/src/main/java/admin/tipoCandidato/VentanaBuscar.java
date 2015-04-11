package src.main.java.admin.tipoCandidato;

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
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.Logica;
import src.main.java.dao.tipoCandidato.TipoCandidatoDAO;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import javax.swing.ImageIcon;

import java.awt.Color;

public class VentanaBuscar  extends JFrame implements ActionListener {

	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo;
	private JTextField txtCod;
	private JLabel lblCod;
	private JButton botonGuardar,botonCancelar,botonBuscar,botonModificar,botonEliminar;
	private VentanaBuscar ventanaBuscar;
	
	JSONArray miPersona = null;
	DefaultTableModel modelo;
	private JTable table_1;
	private TipoCandidatoJTableModel model = new TipoCandidatoJTableModel();
	private JScrollPane scrollPane;
	private JLabel lblDesc;
	private JTextField txtDesc;
	
	private String codTemporal = "";
	private JLabel lblMensaje;

	
	
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de busqueda
	 */
	public VentanaBuscar() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		botonGuardar = new JButton();
		botonGuardar.setBounds(462, 88, 120, 25);
		botonGuardar.setText("Guardar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(607, 120, 120, 25);
		botonCancelar.setText("Cancelar");
		
		botonBuscar = new JButton();
		botonBuscar.setToolTipText("Buscar");
		botonBuscar.setIcon(new ImageIcon(VentanaBuscar.class.getResource("/imgs/search.png")));
		botonBuscar.setBounds(219, 52, 50, 25);
		
		botonEliminar = new JButton();
		botonEliminar.setBounds(462, 120, 120, 25);
		botonEliminar.setText("Eliminar");
		
		botonModificar = new JButton();
		botonModificar.setEnabled(false);
		botonModificar.setBounds(607, 88, 120, 25);
		botonModificar.setText("Modificar");

		labelTitulo = new JLabel();
		labelTitulo.setText("BUSQUEDA, MODIFICACION Y ELIMINACION DE TIPO DE CANDIDATOS");
		labelTitulo.setBounds(105, 11, 729, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblCod=new JLabel();
		lblCod.setText("Codigo");
		lblCod.setBounds(20, 52, 80, 25);
		getContentPane().add(lblCod);
		
		txtCod=new JTextField();
		txtCod.setBounds(140, 52, 59, 25);
		getContentPane().add(txtCod);
		
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
		
		scrollPane = new JScrollPane();
		scrollPane.setToolTipText("Listado de Tipos de Candidato.");
		scrollPane.setBounds(0, 197, 883, 248);
		getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		table_1.setToolTipText("Listado de Tipos de Candidato.");
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
			        	//txtId.setText(selectedData.get(0));
			        	txtCod.setText(selectedData.get(0));
			        	txtDesc.setText(selectedData.get(1));
//			        	textFecha.setText(selectedData.get(2));
//			        	textUsu.setText(selectedData.get(4));
			        	//codTemporal.setText(selectedData.get(1));
			        	codTemporal =(String) ( table_1.getModel().getValueAt(selectedRow[i], 0));
			          
			        }
			        System.out.println("Selected: " + selectedData);
				
			}
		});
		table_1.setModel(model);
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		
		lblDesc = new JLabel();
		lblDesc.setText("Descripcion:");
		lblDesc.setBounds(20, 88, 80, 25);
		getContentPane().add(lblDesc);
		
		txtDesc = new JTextField();
		txtDesc.setText("");
		txtDesc.setBounds(140, 88, 170, 25);
		getContentPane().add(txtDesc);
		
		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(294, 57, 363, 14);
		getContentPane().add(lblMensaje);
		
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
				TipoCandidatoDAO tipoCandidatoDAO = new TipoCandidatoDAO();
				
				tipoCandidatoDAO.modificarTipoCandidato(txtDesc.getText(), codTemporal);
				
				botonEliminar.setEnabled(true);
				botonModificar.setEnabled(false);
				
//				if (Logica.modificaPersona==true) {
//					habilita(true, false, false, false, false, true, false, true, true);	
//					//table.setEnabled(true);

					
					
				//}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null,"Error en el Ingreso de Datos","Error",JOptionPane.ERROR_MESSAGE);
			}
			//this.dispose();
			model = new TipoCandidatoJTableModel();
			recuperarDatos();
			table_1.setModel(model);
			table_1.removeColumn(table_1.getColumnModel().getColumn(0));
			//model.fireTableDataChanged();
			
			
			LimpiarCampos();
			
			botonGuardar.setEnabled(false);
			//botonModificar.setEnabled(true);
			botonBuscar.setEnabled(true);
			
			
			
			
		}
		
		if (e.getSource()==botonBuscar)
		{
			String ge = txtCod.getText();
			
			TipoCandidatoDAO tipoCandidatoDAO = new TipoCandidatoDAO();
			
			if(!(txtCod.getText().length() == 0)  || !(txtDesc.getText().length() == 0)){
				
			
				
					try {
						miPersona = tipoCandidatoDAO.buscarTipoCandidato(txtCod.getText(), txtDesc.getText());
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
			txtCod.setText((String) a.get(1));
			txtDesc.setText((String) a.get(2));
			botonModificar.setEnabled(true);
			
			model = new TipoCandidatoJTableModel();
			recuperarDatos();
			table_1.setModel(model);
			table_1.removeColumn(table_1.getColumnModel().getColumn(0));
			model.fireTableDataChanged();
			}
//			
			
			}
			else{
				lblMensaje.setText("Por favor complete los campos para realizar la busqueda");
				codTemporal="";
				txtCod.setText("");
				txtDesc.setText("");
				  Timer t = new Timer(Login.timer, new ActionListener() {

			            public void actionPerformed(ActionEvent e) {
			            	lblMensaje.setText(null);
			            }
			        });
			        t.setRepeats(false);
			        t.start();
			}
		}
		
		if (e.getSource()==botonModificar)
		{
			habilita(true, false, false, true, false, false, true, false, false);
			
		}
		
		if (e.getSource()==botonEliminar)
		{
			if (!codTemporal.equals(""))
			{
				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar el Tipo Candidato " + txtDesc.getText() + "?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)
				{
					TipoCandidatoDAO tipoCandidatoDAO = new TipoCandidatoDAO();
					
					try {
						tipoCandidatoDAO.eliminarTipoCandidato(codTemporal);
						
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "sfdsfsfsdfs", "Información",JOptionPane.WARNING_MESSAGE);
					}
					JOptionPane.showMessageDialog(null,"Excelente, se ha eliminado el Tipo Candidato " + txtDesc.getText());
					//modificarGenero(textCod.getText(), codTemporal.getText());
					codTemporal= "";
					txtDesc.setText("");
					limpiar();
					
					model = new TipoCandidatoJTableModel();
					
					recuperarDatos();
					table_1.setModel(model);
					table_1.removeColumn(table_1.getColumnModel().getColumn(0));
					//model.fireTableDataChanged();
					//table_1.repaint();
				}
			}
			else{
				//JOptionPane.showMessageDialog(null, "Por favor seleccione que Genero desea Eliminar", "Información",JOptionPane.WARNING_MESSAGE);
				lblMensaje.setText("Por favor seleccione que Tipo Candidato que desea Eliminar");
				  Timer t = new Timer(Login.timer, new ActionListener() {

			            public void actionPerformed(ActionEvent e) {
			            	lblMensaje.setText(null);
			            }
			        });
			        t.setRepeats(false);
			        t.start();
			}
			
			
			
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
	//	txtId.setText(Long.toString( (Long) a.get(0))   );
		txtCod.setText((String) a.get(1));
		txtDesc.setText((String) a.get(2));
		//textFecha.setText((String) a.get(2));
		//textUsu.setText((String) a.get(4));
		codTemporal=  a.get(0).toString();
		
		if (!(codTemporal == "")){
			botonModificar.setEnabled(true);
		}
		
		
			
		
		habilita(true, false, false, false, false, true, false, true, true);
	}


	/**
	 * Permite limpiar los componentes
	 */
	public void limpiar()
	{
		txtCod.setText("");
		
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
		botonBuscar.setEnabled(bBuscar);
		botonGuardar.setEnabled(bGuardar);
		//botonModificar.setEnabled(true);
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
			
			query.setQueryGenerico("SELECT id_tipo_candidato, codigo,descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
					+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_tipo_candidato ");
			
			
			
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
		txtCod.setText("");
		//textFecha.setText("");
		//textUsu.setText("");
		txtDesc.setText("");
		codTemporal = "";
	//	txtId.setText("");
		
		
	}
}
