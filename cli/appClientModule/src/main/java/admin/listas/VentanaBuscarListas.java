package src.main.java.admin.listas;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import src.main.java.admin.evento.EventoJTableModel;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;


public class VentanaBuscarListas  extends JFrame implements ActionListener {

	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel lblTitulo,lblMensaje;
	private JTextField txtNroEvento,txtFchIns,txtUsuIns;
	private JLabel lblNroEvento,lblFchIns,lblUsuIns;
	private JButton botonGuardar,botonCancelar,botonBuscar,botonModificar,botonEliminar;
	private VentanaBuscarListas ventanaBuscar;
	String codTemporal = "";
	
	JSONArray miPersona = null;
	DefaultTableModel modelo;
	private JTable table_1;
	private ListasJTableModel model = new ListasJTableModel();
	private JScrollPane scrollPane;
	private JLabel lblDescripcion;
	private JTextField txtDescripcion;

	
	
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de busqueda
	 */
	public VentanaBuscarListas() {
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

		lblTitulo = new JLabel();
		lblTitulo.setText("ADMINISTRAR EVENTOS.");
		lblTitulo.setBounds(105, 11, 359, 30);
		lblTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblNroEvento=new JLabel();
		lblNroEvento.setText("Nro. Evento.:");
		lblNroEvento.setBounds(20, 52, 80, 25);
		getContentPane().add(lblNroEvento);
		
		lblFchIns=new JLabel();
		lblFchIns.setText("Fch. Insercion:");
		lblFchIns.setBounds(20, 120, 113, 25);
		getContentPane().add(lblFchIns);
		
		lblUsuIns=new JLabel();
		lblUsuIns.setText("Usuario Insercion:");
		lblUsuIns.setBounds(20, 160, 113, 25);
		getContentPane().add(lblUsuIns);
		
		txtNroEvento=new JTextField();
		txtNroEvento.setBounds(140, 52, 170, 25);
		getContentPane().add(txtNroEvento);
		txtNroEvento.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                
                if(txtNroEvento.getText().length() == 5){
                	e.consume();
                }
                else
                if (((caracter < '0') || (caracter > '9'))
                        && (caracter != '\b')) {
                    e.consume();
                }
            }
        });
		
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
		getContentPane().add(lblTitulo);
		limpiar();
				
		setSize(889, 476);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		
		
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
			        	
			        	txtDescripcion.setText(selectedData.get(1));
			        	txtNroEvento.setText(selectedData.get(0));
			        	txtFchIns.setText(selectedData.get(3));
			        	txtUsuIns.setText(selectedData.get(4));
			        	codTemporal =(String) ( table_1.getModel().getValueAt(selectedRow[i], 0));
			        	botonEliminar.isEnabled();
			          
			        }
			        System.out.println("Selected: " + selectedData);
				
			}
		});
		table_1.setModel(model);
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		JLabel lblListaDeGeneros = new JLabel();
		lblListaDeGeneros.setText("LISTA DE EVENTOS.");
		lblListaDeGeneros.setFont(new Font("Verdana", Font.BOLD, 18));
		lblListaDeGeneros.setBounds(232, 204, 308, 30);
		getContentPane().add(lblListaDeGeneros);
		
		lblDescripcion = new JLabel();
		lblDescripcion.setText("Descripcion:");
		lblDescripcion.setBounds(20, 88, 110, 25);
		getContentPane().add(lblDescripcion);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setText("");
		txtDescripcion.setBounds(140, 84, 170, 25);
		getContentPane().add(txtDescripcion);
		
		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(57, 192, 613, 14);
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
				EventoDAO eventoDAO = new EventoDAO();
				
				if (txtDescripcion.getText().length() > 0) {
				
				eventoDAO.modificarEvento(   txtDescripcion.getText(),codTemporal);
				model = new ListasJTableModel();
				recuperarDatos();
				table_1.setModel(model);
				model.fireTableDataChanged();
				table_1.removeColumn(table_1.getColumnModel().getColumn(0));
				
				LimpiarCampos();
				
				botonGuardar.setEnabled(false);
				botonModificar.setEnabled(true);
				botonBuscar.setEnabled(true);
				
				txtDescripcion.setEditable(false);
				txtNroEvento.setEditable(true);
				codTemporal = "";
				
				lblMensaje.setText("Excelente, se ha modificado el evento");
				  Timer t = new Timer(Login.timer, new ActionListener() {

			          public void actionPerformed(ActionEvent e) {
			          	lblMensaje.setText(null);
			          }
			      });
			      t.setRepeats(false);
			      t.start();
				
				if (Logica.modificaPersona==true) {
					habilita(true, false, false, false, false, true, false, true, true);	
					//table.setEnabled(true);
					
					
					
					
				}
			}else{
				lblMensaje.setText("Por favor complete todos los campos para realizar la actualización");
				//codTemporal="";
				//txtNroEvento.setText("");
				//txtDescripcion.setText("");
				  Timer t = new Timer(Login.timer, new ActionListener() {

			            public void actionPerformed(ActionEvent e) {
			            	lblMensaje.setText(null);
			            }
			        });
			        t.setRepeats(false);
			        t.start();
			}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null,"Error en el Ingreso de Datos","Error",JOptionPane.ERROR_MESSAGE);
			}
			//this.dispose();
		
			
		}
		
		if (e.getSource()==botonBuscar)
		{
			String ge = txtNroEvento.getText();
			
			EventoDAO eventoDAO = new EventoDAO();
			
			if(!(txtNroEvento.getText().length() == 0)  || !(txtDescripcion.getText().length() == 0)){
			
				try {
					miPersona = eventoDAO.buscarEvento(txtNroEvento.getText(), txtDescripcion.getText());
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
			
			model = new ListasJTableModel();
			recuperarDatos();
			table_1.setModel(model);
			model.fireTableDataChanged();
			table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		
			}
			
	
			
			}
			else{
				lblMensaje.setText("Por favor complete los campos para realizar la busqueda");
				codTemporal="";
				txtNroEvento.setText("");
				txtDescripcion.setText("");
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
			if (codTemporal.length() > 0){
				habilita(true, false, false, true, false, false, true, false, false);
				txtDescripcion.setEditable(true);
				txtNroEvento.setEditable(false);
			}
			
			
		}
		
		if (e.getSource()==botonEliminar)
		{
			
				if (!codTemporal.equals(""))
				{
				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar el Evento?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)
				{
					EventoDAO eventoDAO = new EventoDAO();
					
					eventoDAO.eliminarEvento(codTemporal);
					//modificarGenero(textCod.getText(), codTemporal.getText());
					txtDescripcion.setText("");
					limpiar();
					
				}
				}
				else{
					//JOptionPane.showMessageDialog(null, "Por favor seleccione que Genero desea Eliminar", "Información",JOptionPane.WARNING_MESSAGE);
					lblMensaje.setText("Por favor seleccione que Evento desea Eliminar");
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblMensaje.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
				}
			
			
			model = new ListasJTableModel();
			recuperarDatos();
			table_1.setModel(model);
			model.fireTableDataChanged();
			table_1.removeColumn(table_1.getColumnModel().getColumn(0));
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
		txtDescripcion.setText((String) a.get(2) );
		txtNroEvento.setText((String) a.get(1));
		txtFchIns.setText((String) a.get(4));
		txtUsuIns.setText((String) a.get(5));
		codTemporal = (String) a.get(1);
		
			
		
		habilita(true, false, false, false, false, true, false, true, true);
	}


	/**
	 * Permite limpiar los componentes
	 */
	public void limpiar()
	{
		txtNroEvento.setText("");
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
		txtNroEvento.setEditable(codigo);
		
		txtFchIns.setEditable(nombre);
		txtUsuIns.setEditable(profesion);
		botonBuscar.setEnabled(bBuscar);
		botonGuardar.setEnabled(bGuardar);
		botonModificar.setEnabled(bModificar);
		botonEliminar.setEnabled(bEliminar);
	}


	public VentanaBuscarListas getVentanaBuscar() {
		return ventanaBuscar;
	}


	public void setVentanaListas(VentanaBuscarListas ventanaBuscar) {
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
			
			query.setQueryGenerico("SELECT id_lista,nro_lista, nombre_lista,  to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS'), "
					+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as fchupd,usuario_upd as usuupd "
					+ "from ucsaws_listas");
			
			
			
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
		txtNroEvento.setText("");
		txtFchIns.setText("");
		txtUsuIns.setText("");
		codTemporal= "";
		txtDescripcion.setText("");
		
		
	}
}
