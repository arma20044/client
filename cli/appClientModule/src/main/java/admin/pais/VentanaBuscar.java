package src.main.java.admin.pais;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
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
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.Logica;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import javax.swing.ImageIcon;

public class VentanaBuscar  extends JFrame implements ActionListener {

	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo;
	private JTextField txtCod;
	private JLabel lblCodigo;
	private JButton botonGuardar,botonCancelar,botonBuscar,botonModificar,botonEliminar;
	private VentanaBuscar ventanaBuscar;
	private JTextField txtNombre;
	private JTable table;
	private JScrollPane scrollPane;
	private PaisJTableModel model = new PaisJTableModel();
	private String codTemporal = "";
	private JLabel lblMensaje;
	JSONArray miPersona = null;
	DefaultTableModel modelo;
	
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de busqueda
	 */
	public VentanaBuscar() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		botonGuardar = new JButton();
		botonGuardar.setBounds(143, 189, 120, 25);
		botonGuardar.setText("Guardar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(283, 219, 120, 25);
		botonCancelar.setText("Cancelar");
		
		botonBuscar = new JButton();
		botonBuscar.setToolTipText("Buscar");
		botonBuscar.setIcon(new ImageIcon(VentanaBuscar.class.getResource("/imgs/search.png")));
		botonBuscar.setBounds(267, 80, 50, 25);
		
		botonEliminar = new JButton();
		botonEliminar.setBounds(423, 189, 120, 25);
		botonEliminar.setText("Eliminar");
		
		botonModificar = new JButton();
		botonModificar.setBounds(283, 189, 120, 25);
		botonModificar.setText("Modificar");

		labelTitulo = new JLabel();
		labelTitulo.setText("ADMINISTRAR PAISES");
		labelTitulo.setBounds(120, 20, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblCodigo=new JLabel();
		lblCodigo.setText("Codigo:");
		lblCodigo.setBounds(20, 80, 80, 25);
		getContentPane().add(lblCodigo);
		
		txtCod=new JTextField();
		txtCod.setBounds(143, 80, 114, 25);
		getContentPane().add(txtCod);
		
		botonModificar.addActionListener(this);
		botonEliminar.addActionListener(this);
		botonBuscar.addActionListener(this);
		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		
		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(137, 164, 601, 14);
		getContentPane().add(lblMensaje);

		getContentPane().add(botonCancelar);
		getContentPane().add(botonBuscar);
		getContentPane().add(botonModificar);
		getContentPane().add(botonEliminar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
				
		setSize(754, 492);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		
		
		JLabel lblNombre = new JLabel();
		lblNombre.setText("Nombre:");
		lblNombre.setBounds(20, 133, 80, 25);
		getContentPane().add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setText("");
		txtNombre.setEditable(true);
		txtNombre.setBounds(143, 133, 167, 25);
		getContentPane().add(txtNombre);
		
		scrollPane = new JScrollPane();
		
		scrollPane.setBounds(0, 255, 738, 197);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
table.setModel(model);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				 List<String> selectedData= new ArrayList<String>();

			        int[] selectedRow = table.getSelectedRows();
			        //int[] selectedColumns = table_1.getSelectedColumns();

			        for (int i = 0; i < selectedRow.length; i++) {
			            int col= 0;
			        	while (table.getColumnCount() > col){
			        		 System.out.println(table.getValueAt(selectedRow[i], col));
			        		 try{
			        			 selectedData.add( (String) table.getValueAt(selectedRow[i], col));
			        		 }
			        		 catch (Exception e){
			        			 System.out.println(e.getMessage());
			        		 }
			        		
			        		
			        		col++;
			        	}
			           // selectedData.ad table_1.getValueAt(selectedRow[i], selectedColumns[0]);
			        	//txtId.setText(selectedData.get(0));
			        	txtCod.setText(selectedData.get(0));
			        	txtNombre.setText(selectedData.get(1));
//			        	textFecha.setText(selectedData.get(2));
//			        	textUsu.setText(selectedData.get(4));
			        	//codTemporal.setText(selectedData.get(1));
			        	codTemporal =(String) ( table.getModel().getValueAt(selectedRow[i], 0));
			          
			        }
			        System.out.println("Selected: " + selectedData);
				
			
			}
		});
		table.removeColumn(table.getColumnModel().getColumn(0));
		recuperarDatos();
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
				PaisDAO paisDAO = new PaisDAO();
				
				paisDAO.modificarPais(codTemporal, txtNombre.getText());
				
				
				if (Logica.modificaPersona==true) {
					habilita(true, false, false, false, false, true, false, true, true);	
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null,"Error en el Ingreso de Datos","Error",JOptionPane.ERROR_MESSAGE);
			}
			lblMensaje.setText("Excelente, se ha modificado el Pais " + txtNombre.getText());
			
			  Timer t = new Timer(Login.timer, new ActionListener() {

		            public void actionPerformed(ActionEvent e) {
		            	lblMensaje.setText(null);
		            }
		        });
		        t.setRepeats(false);
		        t.start();
		        
		        codTemporal = "";
		        txtCod.setText("");
		        txtNombre.setText("");
		        
		        model = new PaisJTableModel();
				recuperarDatos();
				table.setModel(model);
				table.removeColumn(table.getColumnModel().getColumn(0));
				model.fireTableDataChanged();
			
		}
		
		if (e.getSource()==botonBuscar)
		{
			String ge = txtCod.getText();
			
			PaisDAO paisDAO = new PaisDAO();
			
			if(!(txtCod.getText().length() == 0)  || !(txtNombre.getText().length() == 0)){
				
			
				
					try {
						miPersona = paisDAO.buscarPais(txtCod.getText(), txtNombre.getText());
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
			
			
			model = new PaisJTableModel();
			recuperarDatos();
			table.setModel(model);
			table.removeColumn(table.getColumnModel().getColumn(0));
			model.fireTableDataChanged();
			}
//			
			
			}
			else{
				lblMensaje.setText("Por favor complete los campos para realizar la busqueda");
				codTemporal="";
				txtCod.setText("");
				txtNombre.setText("");
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
			if (!txtCod.getText().equals(""))
			{
				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar el Pais " + txtCod.getText() + "?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)
				{
					PaisDAO paisDAO = new PaisDAO();
					
					paisDAO.eliminarPais(codTemporal);
					//modificarGenero(textCod.getText(), codTemporal.getText());
					if (paisDAO.eliminarPais(codTemporal) == false){
						lblMensaje.setText("No Se pudo eliminar existen registros que hacen referencia a éste pais: " + txtNombre.getText());
						
						  Timer t = new Timer(Login.timer, new ActionListener() {

					            public void actionPerformed(ActionEvent e) {
					            	lblMensaje.setText(null);
					            }
					        });
					        t.setRepeats(false);
					        t.start();
					        
					        txtCod.setText("");
					        txtNombre.setText("");
					        codTemporal = "";
					        
					        
					}else{
						
					
					limpiar();
					txtCod.setText("");
					txtNombre.setText("");
					codTemporal = "";
					
					
					
					lblMensaje.setText("Se elimino correctamente.");
					
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblMensaje.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
					}
					model = new PaisJTableModel();
					
					recuperarDatos();
					table.setModel(model);
					table.removeColumn(table.getColumnModel().getColumn(0));
				}
			}
			else{
				//JOptionPane.showMessageDialog(null, "Ingrese un numero de Documento", "Información",JOptionPane.WARNING_MESSAGE);
				lblMensaje.setText("Por favor seleccione que pais desea eliminar");
				
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
	private void muestraPersona(JSONArray pais) {
		JSONArray a = (JSONArray) pais.get(0);
		//	txtId.setText(Long.toString( (Long) a.get(0))   );
			txtCod.setText((String) a.get(1));
			txtNombre.setText((String) a.get(2));
			//textFecha.setText((String) a.get(2));
			//textUsu.setText((String) a.get(4));
			codTemporal=  a.get(0).toString();
		
			
		
		habilita(true, false, false, false, false, true, false, true, true);
	}


	/**
	 * Permite limpiar los componentes
	 */
	public void limpiar()
	{
		
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
			
			query.setQueryGenerico("SELECT id_pais, codigo, nombre, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
					+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_pais ");
			
			
			
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
}
