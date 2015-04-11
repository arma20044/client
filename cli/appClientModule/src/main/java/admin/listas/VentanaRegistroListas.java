package src.main.java.admin.listas;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.validator.ListasValidator;
import src.main.java.dao.listas.ListasDAO;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class VentanaRegistroListas extends JFrame implements ActionListener{

	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo;
	private JButton botonGuardar,botonCancelar, btnEliminar;
	private VentanaRegistroListas ventanaRegistro;
	private JLabel lblNro,lblMensaje;
	private JTextField txtNro;
	private JTextField txtAnho;
	private JLabel lblAnho;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JTable table;
	private String codTemporal = "";
	
	private ListasJTableModel model = new ListasJTableModel();
	
	private ListasValidator listasValidator = new ListasValidator();
	
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de registro
	 */
	public VentanaRegistroListas() {
		setResizable(false);

		botonGuardar = new JButton();
		botonGuardar.setBounds(180, 229, 120, 25);
		botonGuardar.setText("Registrar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(342, 229, 120, 25);
		botonCancelar.setText("Cancelar");
		
		btnEliminar = new JButton();
		btnEliminar.setBounds(342, 229, 120, 25);
		btnEliminar.setText("Eliminar");
		
		

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE LISTAS.");
		labelTitulo.setBounds(120, 20, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));
		
		lblMensaje = new JLabel("");
	    lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(182, 190, 363, 14);
		getContentPane().add(lblMensaje);
		
		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		btnEliminar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(759, 492);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		lblNro = new JLabel();
		lblNro.setText("Nro. Lista:");
		lblNro.setBounds(51, 82, 80, 25);
		getContentPane().add(lblNro);
		
		txtNro = new JTextField();
		txtNro.setText("");
		txtNro.setEditable(true);
		txtNro.setBounds(160, 82, 106, 25);
		getContentPane().add(txtNro);
		
		txtAnho = new JTextField();
		txtAnho.setText("");
		txtAnho.setEditable(true);
		txtAnho.setBounds(160, 121, 62, 25);
		getContentPane().add(txtAnho);
		
		lblAnho = new JLabel();
		lblAnho.setText("Anho Lista:");
		lblAnho.setBounds(51, 121, 80, 25);
		getContentPane().add(lblAnho);
		
		lblNombre = new JLabel();
		lblNombre.setText("Nombre Lista:");
		lblNombre.setBounds(51, 157, 80, 25);
		getContentPane().add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setText("");
		txtNombre.setEditable(true);
		txtNombre.setBounds(160, 157, 237, 25);
		getContentPane().add(txtNombre);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 277, 679, 175);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0)  {
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
//			        	txtCod.setText(selectedData.get(0));
			        	txtNro.setText(selectedData.get(0));
			        	txtNombre.setText(selectedData.get(1));
			        	txtAnho.setText(selectedData.get(2));
//			        	textUsu.setText(selectedData.get(4));
			        	//codTemporal.setText(selectedData.get(1));
			        	codTemporal =(String) ( table.getModel().getValueAt(selectedRow[i], 0));
			          
			        }
			        System.out.println("Selected: " + selectedData);
				
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(model);
		table.removeColumn(table.getColumnModel().getColumn(0));
		recuperarDatos();
		table.setAutoCreateRowSorter(true);
		
		
		btnEliminar.setBounds(499, 230, 89, 23);
		getContentPane().add(btnEliminar);
		

	}


	private void limpiar() 
	{
	}


	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==botonGuardar)
		{
			try {
				
				if (!(txtNombre.getText().length() == 0) && !(txtNro.getText().length() == 0) && !(txtAnho.getText().length() == 0))
				{

					
					if (listasValidator.ValidarLista(txtNro.getText()) == false){
						
				String numeroLista,nombreLista,anho,descripcion;
				
				numeroLista = txtNro.getText();
				nombreLista = txtNombre.getText();
				anho = txtAnho.getText();
				
				
			
				
				
				
				ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

				WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
				QueryGenericoRequest query = new QueryGenericoRequest();
				
				//para registrar se inserta el codigo es 1
				query.setTipoQueryGenerico(1);
				
				query.setQueryGenerico("INSERT INTO ucsaws_listas"
						+ "( id_lista, nombre_lista, anho,nro_lista, usuario_ins,fch_ins, usuario_upd, fch_upd) "
						+ "VALUES ("
						+ "nextval('ucsaws_listas_seq')"
						+ ", "
						+"upper('"
						+ txtNombre.getText() 
						+"')"
							+ ", "
						
						+ txtAnho.getText() 
						+", "
						+ txtNro.getText()
						+", '"
						+ Login.userLogeado + "' , now(), '"
						+ Login.userLogeado +"' , now())");
				
				
				
				QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
				weatherClient.printQueryGenericoResponse(response);
				//JOptionPane.showMessageDialog(null,"Excelente, se ha guardado la Lista.");
				
				//this.dispose();
				lblMensaje.setText("Excelente, se ha guardado la Lista.");
				  Timer t = new Timer(Login.timer, new ActionListener() {

			            public void actionPerformed(ActionEvent e) {
			            	lblMensaje.setText(null);
			            }
			        });
			        t.setRepeats(false);
			        t.start();
			        
			        model = new ListasJTableModel();
					recuperarDatos();
					table.setModel(model);
					model.fireTableDataChanged();
					table.removeColumn(table.getColumnModel().getColumn(0));
					
					txtAnho.setText("");
					txtNombre.setText("");
					txtNro.setText("");
					codTemporal = "";
				}
					else{
						{
							//JOptionPane.showMessageDialog(null, "Ya existe el genero " + txtDesc.getText(), "Información",JOptionPane.WARNING_MESSAGE);
							lblMensaje.setText("Ya existe la Lista con el numero " + txtNro.getText());
							  Timer t = new Timer(Login.timer, new ActionListener() {

						            public void actionPerformed(ActionEvent e) {
						            	lblMensaje.setText(null);
						            }
						        });
						        t.setRepeats(false);
						        t.start();
						}
					}
			}
				else{
					//JOptionPane.showMessageDialog(null, "Debe ingresar el nombre.", "Información",JOptionPane.WARNING_MESSAGE);
					lblMensaje.setText("Debe Ingresar todos los campos");
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblMensaje.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
				}
				
			} catch (Exception ex) {
				//JOptionPane.showMessageDialog(null,"Error al intentar insertar","Error",JOptionPane.ERROR_MESSAGE);
				lblMensaje.setText("Error al intentar insertar.");
				  Timer t = new Timer(Login.timer, new ActionListener() {

			            public void actionPerformed(ActionEvent e) {
			            	lblMensaje.setText(null);
			            }
			        });
			        t.setRepeats(false);
			        t.start();
			}
			
		}
		if (e.getSource()==btnEliminar)
		{
			
			
			if (!codTemporal.equals(""))
			{
				
				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar la Lista " +txtNombre.getText() + "?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)
					
				{
					ListasDAO listasDAO = new ListasDAO();
					
					try {
						listasDAO.eliminarLista(codTemporal);
						
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "sfdsfsfsdfs", "Información",JOptionPane.WARNING_MESSAGE);
					}
					if (listasDAO.eliminarLista(codTemporal) == true){
						
					
					//JOptionPane.showMessageDialog(null,"Excelente, se ha eliminado el genero " + txtDesc.getText());
					//modificarGenero(textCod.getText(), codTemporal.getText());
					//txtId.setText("");
					lblMensaje.setText("Excelente, se ha eliminado la lista " + txtNombre.getText());
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblMensaje.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
					limpiar();
					
					model = new ListasJTableModel();
					
					recuperarDatos();
					table.setModel(model);
					
					model.fireTableDataChanged();
					table.removeColumn(table.getColumnModel().getColumn(0));
					
					txtAnho.setText("");
					txtNombre.setText("");
					txtNro.setText("");
					codTemporal = "";
					}
					
					else{
						//JOptionPane.showMessageDialog(null,"Existen registros que apuntan al Genero que desea eliminar ","Error",JOptionPane.ERROR_MESSAGE);
						lblMensaje.setText("ERROR: Existen registros que apuntan a la Lista que desea eliminar ");
						  Timer t = new Timer(Login.timer, new ActionListener() {

					            public void actionPerformed(ActionEvent e) {
					            	lblMensaje.setText(null);
					            }
					        });
					        t.setRepeats(false);
					        t.start();
					}
				}
				
			}
			else{
				//JOptionPane.showMessageDialog(null, "Por favor seleccione que Genero desea Eliminar", "Información",JOptionPane.WARNING_MESSAGE);
				lblMensaje.setText("Por favor seleccione que Lista desea Eliminar");
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


	public VentanaRegistroListas getVentanaRegistro() {
		return ventanaRegistro;
	}


	public void setVentanaRegistro(VentanaRegistroListas ventanaRegistro) {
		this.ventanaRegistro = ventanaRegistro;
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
			
			query.setQueryGenerico("SELECT id_lista, nro_lista, nombre_lista, anho, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
					+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_listas order by nro_lista");
			
			
			
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
					 fil.get(6).toString(), fil.get(7).toString()
			 };
			 
			 model.ciudades.add(fin);
			ite++;
		}
		
	}
}
