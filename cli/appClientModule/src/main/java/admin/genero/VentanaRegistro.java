package src.main.java.admin.genero;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.validator.GeneroValidator;
import src.main.java.dao.genero.GeneroDAO;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;


public class VentanaRegistro extends JFrame implements ActionListener{

	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo;
	private JTextField txtDesc;
	private JLabel lblDesc, lblMensaje;
	private JButton botonGuardar,botonCancelar, btnEliminar;
	private VentanaRegistro ventanaRegistro;
	private JTable table;
	
	
	private CiudadesJTableModel model = new CiudadesJTableModel();
	private JScrollPane scrollPane;
	
	private GeneroValidator generoValidador = new GeneroValidator();
	private JTextField txtCod;
	
	private String codTemporal = "";
	
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de registro
	 */
	public VentanaRegistro() {

		botonGuardar = new JButton();
		botonGuardar.setBounds(239, 154, 120, 25);
		botonGuardar.setText("Registrar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(384, 154, 120, 25);
		botonCancelar.setText("Cancelar");
		

		btnEliminar = new JButton();
		btnEliminar.setText("Eliminar");
		btnEliminar.setEnabled(true);
		btnEliminar.setBounds(529, 155, 120, 25);
		//getContentPane().add(btnEliminar);

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE GENEROS");
		labelTitulo.setBounds(261, 28, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblDesc=new JLabel();
		lblDesc.setText("Descripcion");
		lblDesc.setBounds(261, 102, 80, 25);
		getContentPane().add(lblDesc);
		
		txtDesc=new JTextField();
		txtDesc.setBounds(384, 102, 175, 25);
		getContentPane().add(txtDesc);
		
		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		btnEliminar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(btnEliminar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(812, 444);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 211, 806, 204);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setToolTipText("Listado de Generos");
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
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
//			        	txtCod.setText(selectedData.get(0));
//			        	txtDesc.setText(selectedData.get(1));
//			        	textFecha.setText(selectedData.get(2));
//			        	textUsu.setText(selectedData.get(4));
			        	//codTemporal.setText(selectedData.get(1));
			        	codTemporal =(String) ( table.getModel().getValueAt(selectedRow[i], 0));
			          
			        }
			        System.out.println("Selected: " + selectedData);
				
			}
		});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(model);
		
		JLabel lblCod = new JLabel();
		lblCod.setText("Código:");
		lblCod.setBounds(261, 69, 80, 25);
		getContentPane().add(lblCod);
		
		txtCod = new JTextField();
		txtCod.setText("");
		txtCod.setBounds(384, 66, 61, 25);
		getContentPane().add(txtCod);
		
	    lblMensaje = new JLabel("");
	    lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(182, 190, 363, 14);
		getContentPane().add(lblMensaje);
		
		
		table.removeColumn(table.getColumnModel().getColumn(0));
		recuperarDatos();
		
		

	}


	private void limpiar() 
	{
		txtDesc.setText("");
		codTemporal = "";
	}


	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==botonGuardar)
		{
			try {
				
				if (!(txtCod.getText().length() == 0) && !(txtDesc.getText().length() == 0))
				{
					if(txtCod.getText().length() > 1){
						lblMensaje.setText("El codigo debe ser de una sola letra.");
						  Timer t = new Timer(Login.timer, new ActionListener() {

					            public void actionPerformed(ActionEvent e) {
					            	lblMensaje.setText(null);
					            }
					        });
					        t.setRepeats(false);
					        t.start();
					}
					else if
					
					(generoValidador.ValidarGenero(txtCod.getText()) == false){
						{
				//Genero genero = new Genero();
				//genero.setDescripcion(textGenero.getText());
				
				
				ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

				WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
				QueryGenericoRequest query = new QueryGenericoRequest();
				
				//para registrar se inserta el codigo es 1
				query.setTipoQueryGenerico(1);
				System.out.println(Login.userLogeado);
				query.setQueryGenerico("INSERT INTO ucsaws_genero"
						+ "( id_genero, descripcion, codigo, usuario_ins,fch_ins, usuario_upd, fch_upd) "
						+ "VALUES ("
						+ "nextval('ucsaws_genero_seq')"
						+ ", "
						+"upper('"
						+ txtDesc.getText() 
						+"')"
							+ ", "
						+"upper('"
						+ txtCod.getText() 
						+"'), '"
						+ Login.userLogeado + "' , now(), '"
						+ Login.userLogeado +"' , now())");
				
				
				
				QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
				weatherClient.printQueryGenericoResponse(response);
				
				model = new CiudadesJTableModel();
				recuperarDatos();
				table.setModel(model);
				model.fireTableDataChanged();
				table.removeColumn(table.getColumnModel().getColumn(0));
				//JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
				lblMensaje.setText("Excelente, se ha guardado el genero.");
				  Timer t = new Timer(Login.timer, new ActionListener() {

			            public void actionPerformed(ActionEvent e) {
			            	lblMensaje.setText(null);
			            }
			        });
			        t.setRepeats(false);
			        t.start();
				
				
				txtCod.setText("");
				txtDesc.setText("");
				
				
				
				
				
				//this.dispose();
				}
					}
				else{
					//JOptionPane.showMessageDialog(null, "Ya existe el genero " + txtDesc.getText(), "Información",JOptionPane.WARNING_MESSAGE);
					lblMensaje.setText("Ya existe el genero con el codigo " + txtCod.getText());
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblMensaje.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
				}
				
					
						
					}
				
				else{
					//JOptionPane.showMessageDialog(null, , "Información",JOptionPane.WARNING_MESSAGE);
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
				JOptionPane.showMessageDialog(null,"Error al intentar insertar","Error",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		if (e.getSource()==btnEliminar)
		{
			
			
			if (!codTemporal.equals(""))
			{
				
				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar el Genero?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)
					
				{
					GeneroDAO generoDAO = new GeneroDAO();
					
					try {
						generoDAO.eliminarGenero(codTemporal);
						
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "sfdsfsfsdfs", "Información",JOptionPane.WARNING_MESSAGE);
					}
					if (generoDAO.eliminarGenero(codTemporal) == true){
						
					
					//JOptionPane.showMessageDialog(null,"Excelente, se ha eliminado el genero " + txtDesc.getText());
					//modificarGenero(textCod.getText(), codTemporal.getText());
					//txtId.setText("");
					lblMensaje.setText("Excelente, se ha eliminado el genero " + txtDesc.getText());
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblMensaje.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
					limpiar();
					
					model = new CiudadesJTableModel();
					
					recuperarDatos();
					table.setModel(model);
					
					model.fireTableDataChanged();
					table.removeColumn(table.getColumnModel().getColumn(0));
					}
					
					else{
						//JOptionPane.showMessageDialog(null,"Existen registros que apuntan al Genero que desea eliminar ","Error",JOptionPane.ERROR_MESSAGE);
						lblMensaje.setText("ERROR: Existen registros que apuntan al Genero que desea eliminar ");
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
				lblMensaje.setText("Por favor seleccione que Genero desea Eliminar");
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


	public VentanaRegistro getVentanaRegistro() {
		return ventanaRegistro;
	}


	public void setVentanaRegistro(VentanaRegistro ventanaRegistro) {
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
			
			query.setQueryGenerico("SELECT id_genero, codigo, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
					+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_genero ");
			
			
			
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
