package src.main.java.admin.evento;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.validator.DateValidator;
import src.main.java.admin.validator.EventoValidator;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;




public class VentanaRegistroEvento extends JFrame implements ActionListener{

	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo;
	private JLabel lblDescripcion, lblMensaje,lblErrorDesde;
	private JButton botonGuardar,botonCancelar,btnEliminar;
	private VentanaRegistroEvento ventanaRegistro;
	private EventoValidator eventoValidator = new EventoValidator();
	
	private EventoJTableModel model = new EventoJTableModel();
	private JTable table;
	
	private JScrollPane scrollPane;
	private JTextField txtNroEvento;
	private JTextArea txtAJTextArea;
	private JComboBox cmbTipoEvento;
	private JFormattedTextField txtDesde;
	private JFormattedTextField txtHasta;
	private Date date;
	private DateFormat dateFormat;
	

	
	List<Object[]> tipoEvento = new ArrayList<Object[]>();
	
	DateValidator dateValidator = new DateValidator();
	
	String SelectedCombo = "";
	private JTextField txtDecripcion;
	
	String codTemporal = "";
	 
	private JLabel lblErrorHasta;
	
	
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de registro
	 */
	public VentanaRegistroEvento() {

		botonGuardar = new JButton();
		botonGuardar.setBounds(608, 79, 120, 25);
		botonGuardar.setText("Registrar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(608, 144, 120, 25);
		botonCancelar.setText("Cancelar");
		
		btnEliminar = new JButton();
		btnEliminar.setText("Eliminar");
		btnEliminar.setBounds(608, 115, 120, 25);
		getContentPane().add(btnEliminar);

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE EVENTOS.");
		labelTitulo.setBounds(207, 28, 300, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblDescripcion=new JLabel();
		lblDescripcion.setText("Descripcion:");
		lblDescripcion.setBounds(36, 99, 87, 14);
		getContentPane().add(lblDescripcion);
		
		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		btnEliminar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(812, 595);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				 List<String> selectedData= new ArrayList<String>();

			        int[] selectedRow = table.getSelectedRows();
			        //int[] selectedColumns = table_1.getSelectedColumns();

			        for (int i = 0; i < selectedRow.length; i++) {
			            int col= 0;
			        	while (table.getColumnCount() > col){
			        		 //System.out.println(table_1.getValueAt(selectedRow[i], col));
			        		 try{
			        			 selectedData.add( (String) table.getValueAt(selectedRow[i], col));
			        		 }
			        		 catch (Exception e){
			        			 System.out.println(e.getMessage());
			        		 }
			        		
			        		
			        		col++;
			        	}
			           // selectedData.ad table_1.getValueAt(selectedRow[i], selectedColumns[0]);
			        	txtNroEvento.setText(selectedData.get(0));
			        	txtDecripcion.setText(selectedData.get(1));
			        	
			        	//txtUsuIns.setText((String) table_1.getModel().getValueAt(selectedRow[i], 5));
			        	//txtFchIns.setText((String) table_1.getModel().getValueAt(selectedRow[i], 4));
			        	
//			        	codTemporal.setText((String) table_1.getValueAt(0, 0));
//			        	System.out.println(codTemporal.getText());
			        	codTemporal = ((String) table.getModel().getValueAt(selectedRow[i], 0));
			        	//System.out.println(codTemporal.getText());
			        }
			        //System.out.println("Selected: " + selectedData);
				
			}
		});
		scrollPane.setBounds(0, 307, 806, 259);
		getContentPane().add(scrollPane);
		
		
		table.setBounds(74, 226, 665, 178);
		table.setToolTipText("Listado de Generos");
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(model);
		table.removeColumn(table.getColumnModel().getColumn(0));
		
		JLabel lblTipoEvento = new JLabel();
		lblTipoEvento.setText("Tipo Evento:");
		lblTipoEvento.setBounds(36, 195, 87, 14);
		getContentPane().add(lblTipoEvento);
		
		cmbTipoEvento = new JComboBox();
		cmbTipoEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object cmboitem = cmbTipoEvento.getSelectedItem();
				SelectedCombo = cmboitem.toString();
			}
		});
		cmbTipoEvento.setBounds(133, 192, 451, 20);
		getContentPane().add(cmbTipoEvento);
		recuperarDatosComboBox();
		int pos = 0;
		while (tipoEvento.size() > pos){
		Object elemento[] = tipoEvento.get(pos);
		cmbTipoEvento.addItem(elemento[1]);
		pos++;
		}	
		
		JLabel lblDesde = new JLabel();
		lblDesde.setText("Fecha desde:");
		lblDesde.setBounds(36, 240, 90, 14);
		getContentPane().add(lblDesde);
		
		JLabel lblHasta = new JLabel();
		lblHasta.setText("Fecha Hasta:");
		lblHasta.setBounds(40, 276, 86, 14);
		getContentPane().add(lblHasta);
		
		txtDesde = new JFormattedTextField();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		date = new Date();
		   System.out.println(dateFormat.format(date));
		   txtDesde.setText(dateFormat.format(date));
		   
		txtDesde.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(txtDesde.getText().length() ==0){
					Calendario cal = new Calendario(ventanaRegistro);
					cal.displayDate();
					if(!Calendario.fechafinalSeleccionada.startsWith("/"))
					txtDesde.setText(Calendario.fechafinalSeleccionada);
				}else
				if (dateValidator.isThisDateValid(txtDesde.getText(), "dd/MM/yyyy")== false){
					//txtDesde.requestFocus();
					//txtDesde.selectAll();
					lblErrorDesde.setText("Error: Por favor ingrese fecha desde valida.");
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblErrorDesde.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
				      
//					JOptionPane.showMessageDialog(null,
//					          "Error: Por favor ingrese fecha desde valida.", "Massage de Error",
//					          JOptionPane.ERROR_MESSAGE);
					
				}
				else{
					dateValidator.fechaHastaEsMenor(txtDesde.getText(), txtHasta.getText());
				}
				
			}
		});
	
		txtDesde.setBounds(133, 237, 69, 20);
		getContentPane().add(txtDesde);
		
		txtHasta = new JFormattedTextField();
		
		   System.out.println(dateFormat.format(date));
		   txtHasta.setText(dateFormat.format(date));
		txtHasta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (dateValidator.isThisDateValid(txtHasta.getText(), "dd/MM/yyyy")== false){
					//txtHasta.requestFocus();
					txtHasta.selectAll();
					lblErrorHasta.setText("Error: Por favor ingrese fecha hasta valida.");
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblErrorHasta.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
					
				}
				else{
					dateValidator.fechaHastaEsMenor(txtDesde.getText(), txtHasta.getText());
				}
			}
		});
		txtHasta.setBounds(133, 273, 69, 20);
		getContentPane().add(txtHasta);
		
		JLabel lblNroEvento = new JLabel();
		lblNroEvento.setText("Nro. Evento:");
		lblNroEvento.setBounds(36, 64, 87, 14);
		getContentPane().add(lblNroEvento);
		
		txtNroEvento = new JTextField();
		txtNroEvento.setToolTipText("Este campo es de maximo 5 caracteres");
		txtNroEvento.setBounds(131, 61, 152, 20);
		
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
		getContentPane().add(txtNroEvento);
		txtNroEvento.setColumns(10);
		
		txtDecripcion = new JTextField();
		txtDecripcion.setColumns(10);
		txtDecripcion.setBounds(133, 96, 451, 85);
		getContentPane().add(txtDecripcion);
		
		JButton btnDesde = new JButton("");
		btnDesde.setIcon(new ImageIcon(VentanaRegistroEvento.class.getResource("/imgs/cal.png")));
		btnDesde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendario cal = new Calendario(ventanaRegistro);
				cal.displayDate();
				if(!Calendario.fechafinalSeleccionada.startsWith("/")){
					txtDesde.setText(Calendario.fechafinalSeleccionada);
				}
			}
		});
		btnDesde.setBounds(207, 234, 30, 23);
		getContentPane().add(btnDesde);
		
		JButton btnHasta = new JButton("");
		btnHasta.setIcon(new ImageIcon(VentanaRegistroEvento.class.getResource("/imgs/cal.png")));
		btnHasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendario cal = new Calendario(ventanaRegistro);
				cal.displayDate();
				if(!Calendario.fechafinalSeleccionada.startsWith("/")){
					txtHasta.setText(Calendario.fechafinalSeleccionada);
				}
				
			}
		});
		btnHasta.setBounds(207, 273, 30, 23);
		getContentPane().add(btnHasta);
		
		
		
		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(276, 223, 452, 14);
		getContentPane().add(lblMensaje);
		
		lblErrorDesde = new JLabel("");
		lblErrorDesde.setForeground(Color.RED);
		lblErrorDesde.setBounds(247, 240, 439, 14);
		getContentPane().add(lblErrorDesde);
		
		lblErrorHasta = new JLabel("");
		lblErrorHasta.setForeground(Color.RED);
		lblErrorHasta.setBounds(247, 276, 439, 14);
		getContentPane().add(lblErrorHasta);
		
		
		recuperarDatos();
		
		
		

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
				
				if (!txtNroEvento.getText().equals("") && !txtDecripcion.getText().equals("") && !txtDesde.getText().equals("") && !txtHasta.getText().equals(""))
				{
					if (dateValidator.fechaHastaEsMenor(txtDesde.getText(), txtHasta.getText())== false){
						
					
					if(eventoValidator.ValidarEvento(txtNroEvento.getText()) == false){
						
					
					
					JSONArray filas = new JSONArray();
					JSONArray fil = new JSONArray();
					//Obtener el Departamento
					ApplicationContext ctxDept = SpringApplication.run(WeatherConfiguration.class);

					WeatherClient weatherClientDept = ctxDept.getBean(WeatherClient.class);
					QueryGenericoRequest queryDept = new QueryGenericoRequest();
					
					//para registrar se inserta el codigo es 1
					queryDept.setTipoQueryGenerico(2);
					
//					query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
//							+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");
					
					queryDept.setQueryGenerico("SELECT id_tipo_evento,  descripcion from ucsaws_tipo_evento  " +
		"where descripcion = '" + SelectedCombo + "'");
					
					QueryGenericoResponse responseDept = weatherClientDept.getQueryGenericoResponse(queryDept);
					weatherClientDept.printQueryGenericoResponse(responseDept);
					
					String res = responseDept.getQueryGenericoResponse();
					
					Object ob = null;
					JSONParser j = new JSONParser();
					//try {
						ob = j.parse(res);
					//} catch (org.json.simple.parser.ParseException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					//}
				
				filas = (JSONArray) ob;
				
				fil = (JSONArray) filas.get(0);
				
				Genero genero = new Genero();
				genero.setDescripcion(txtNroEvento.getText());
				
				
				ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

				WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
				QueryGenericoRequest query = new QueryGenericoRequest();
				
				//para registrar se inserta el codigo es 1
				query.setTipoQueryGenerico(1);
				System.out.println(Login.userLogeado);
				query.setQueryGenerico("INSERT INTO ucsaws_evento"
						+ "( id_evento, descripcion, id_tipo_evento, fch_desde, fch_hasta, nro_evento, usuario_ins,fch_ins, usuario_upd, fch_upd) "
						+ "VALUES ("
						+ "nextval('ucsaws_evento_seq')"
						+ ", '"
						+ txtDecripcion.getText()
						+ "','"
						+ fil.get(0) + "','"+ txtDesde.getText() + "','"+ txtHasta.getText()+ "','" + txtNroEvento.getText()  + "','" 
						+ Login.userLogeado + "' , now(), '"
						+ Login.userLogeado +"' , now())");
				
				
				
				QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
				weatherClient.printQueryGenericoResponse(response);
				
				if (response.getQueryGenericoResponse().compareTo("ERRORRRRRRR")== 0){
					//JOptionPane.showMessageDialog(null,"Error al intentar insertar","Error",JOptionPane.ERROR_MESSAGE);
					lblMensaje.setText("Ocurrio un error inesperado");
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblMensaje.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
					
				}
				else{
					//JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el Departamento.");
					lblMensaje.setText("Excelente, se ha guardado el Evento.");
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblMensaje.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
					txtNroEvento.setText("");
					//txtAJTextArea.setText("");
//					txtDesde.setText("");
//					txtHasta.setText("");
					txtDesde.setText(dateFormat.format(date));
					txtHasta.setText(dateFormat.format(date));
					txtDecripcion.setText("");

					model = new EventoJTableModel();
					recuperarDatos();
					table.setModel(model);
					model.fireTableDataChanged();
					table.removeColumn(table.getColumnModel().getColumn(0));
				}
				
				
				
				
				
				
				//this.dispose();
				}
					
					else{
						//JOptionPane.showMessageDialog(null, "El Evento ya Existe","Advertencia",JOptionPane.WARNING_MESSAGE);
						lblMensaje.setText("El Evento ya Existe.");
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
					
					}
				}
				else{
					//JOptionPane.showMessageDialog(null, "Debe ingresar todos los campos.", "Información",JOptionPane.WARNING_MESSAGE);
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
						"¿Esta seguro de eliminar el Evento?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION)
					
				{
					EventoDAO eventoDAO = new EventoDAO();
					
					try {
						eventoDAO.eliminarEvento(codTemporal);
						
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "sfdsfsfsdfs", "Información",JOptionPane.WARNING_MESSAGE);
					}
					if (eventoDAO.eliminarEvento(codTemporal) == true){
						
					
					//JOptionPane.showMessageDialog(null,"Excelente, se ha eliminado el genero " + txtDesc.getText());
					//modificarGenero(textCod.getText(), codTemporal.getText());
					//txtId.setText("");
					lblMensaje.setText("Excelente, se ha eliminado el Evento " + txtDecripcion.getText());
					  Timer t = new Timer(Login.timer, new ActionListener() {

				            public void actionPerformed(ActionEvent e) {
				            	lblMensaje.setText(null);
				            }
				        });
				        t.setRepeats(false);
				        t.start();
					limpiar();
					txtDecripcion.setText("");
					txtDesde.setText("");
					txtHasta.setText("");
					txtNroEvento.setText("");
					//cmbTipoEvento.)
					
					model = new EventoJTableModel();
					
					recuperarDatos();
					table.setModel(model);
					
					model.fireTableDataChanged();
					table.removeColumn(table.getColumnModel().getColumn(0));
					}
					
					else{
						//JOptionPane.showMessageDialog(null,"Existen registros que apuntan al Genero que desea eliminar ","Error",JOptionPane.ERROR_MESSAGE);
						lblMensaje.setText("ERROR: Existen registros que apuntan al Evento que desea eliminar ");
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
				lblMensaje.setText("Por favor seleccione que Evento desea Eliminar");
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


	public VentanaRegistroEvento getVentanaRegistro() {
		return ventanaRegistro;
	}


	public void setVentanaRegistro(VentanaRegistroEvento ventanaRegistro) {
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
			
//			query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
//					+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");
			
			query.setQueryGenerico("SELECT id_evento,nro_evento, ev.descripcion, tev.descripcion as tipo, to_char(ev.fch_ins, 'DD/MM/YYYY HH24:MI:SS'), "
					+ "ev.usuario_ins, to_char(ev.fch_upd, 'DD/MM/YYYY HH24:MI:SS') as fechaupd,ev.usuario_upd as usuupd "
					+ "from ucsaws_evento ev join ucsaws_tipo_evento tev on (ev.id_tipo_evento = tev.id_tipo_evento)order by nro_evento");

			
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
					 fil.get(6).toString(),fil.get(7).toString()
			 };
			 
			 model.ciudades.add(fin);
			ite++;
		}
		
	}
	
	private List<Object[]> recuperarDatosComboBox(){
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
			
//			query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
//					+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");
			
			query.setQueryGenerico("SELECT id_tipo_evento, descripcion"
				+ " from ucsaws_tipo_evento "
					+ "order by descripcion");
			
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
				 = {fil.get(0).toString(),  fil.get(1).toString(),  
			 };
			 
			 tipoEvento.add(fin);
			ite++;
		}
		return tipoEvento;
		
	}
}
