package src.main.java.admin.evento;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jdesktop.swingx.JXFindBar;
import org.jdesktop.swingx.JXTable;
import org.json.simple.JSONArray;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.Reportes;
import src.main.java.admin.utils.ArmarFecha;
import src.main.java.admin.utils.Close;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import src.main.java.login.PreLogin;
import entity.UcsawsEvento;

public class VentanaBuscarEvento extends JFrame implements ActionListener {
	
	ArmarFecha armarFecha = new ArmarFecha();
	
	JXFindBar txtBuscar;
	
	private static final DecimalFormat formatter = new DecimalFormat( "#,##0.00" );
	//public static Integer evento; // 0
	public static String nroEvento; // 1
	public static String descripcionEvento; // 2
	public static Date fechaDesde;
	public static Date fechaHasta;
	public static String tipoEventoDescripcon;
	//public static String idEveto;
	public static UcsawsEvento eventoClase;
	
	public static Date fechaDesdeDate;
	public static Date fechaHastaDate;
	
	public static String evento;
	
	public static Date date;
	
	private DefaultTableModel dm;

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo;
	private JButton botonCancelar, btnEliminar, btnNuevo, btnModificar;

	JSONArray miPersona = null;
	DefaultTableModel modelo;
	private JXTable table_1;
	private EventoJTableModel model = new EventoJTableModel();
	private JScrollPane scrollPane;

	private String codTemporal = "";

	private JLabel lblMensaje;
	
	boolean eliminado;
	
	public static boolean readOnly= false;
	
	UcsawsEvento e;
	
	EventoDAO eventoDAO = new EventoDAO();

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de busqueda
	 */
	public VentanaBuscarEvento() {
	  
	  this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent we)
          { 
              Close close = new Close();
              close.cerrarAplicacion(we);
          }
      });
	 
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtBuscar.requestFocus();
			}
		});
		
//		if(PreLogin.eventoVigenteConParametros(fechaDesde, fechaHasta, evento) == 0){
//			readOnly=false;
//		}
//		else{
//			readOnly=true;
//		}
		readOnly=false;
		
		e = new UcsawsEvento();
		
		setResizable(false);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		botonCancelar = new JButton();
		botonCancelar.setIcon(new ImageIcon(VentanaBuscarEvento.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setToolTipText("Atrás");
		botonCancelar.setBounds(1101, 422, 45, 25);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg));
	

		btnEliminar = new JButton();
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setIcon(new ImageIcon(VentanaBuscarEvento.class
				.getResource("/imgs/borrar.png")));
		btnEliminar.setBounds(554, 62, 32, 32);
		btnEliminar.setOpaque(false);
		btnEliminar.setContentAreaFilled(false);
		btnEliminar.setBorderPainted(false);
		Image img4 = ((ImageIcon) btnEliminar.getIcon()).getImage();
		Image newimg4 = img4.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnEliminar.setIcon(new ImageIcon(newimg4));
		if (MenuPrincipal.reporte){
			btnEliminar.setVisible(false);
		}
		
		else{		
			btnEliminar.setVisible(true);
		}

		labelTitulo = new JLabel();
		labelTitulo.setText("VER EVENTOS");
		labelTitulo.setBounds(248, 11, 270, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));
		btnEliminar.addActionListener(this);
		botonCancelar.addActionListener(this);

		getContentPane().add(botonCancelar);
		getContentPane().add(btnEliminar);
		getContentPane().add(labelTitulo);
		

		
		limpiar();
		
	      if (Login.rol.compareToIgnoreCase("ADM") == 0) {
	          btnEliminar.setEnabled(true);
	        } else {
	          btnEliminar.setEnabled(false);
	          btnEliminar.setToolTipText("Solo los Administradores pueden acceder a ésta Área");
	        }

		setSize(1152, 476);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
 
		
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"clickButtonescape");

		getRootPane().getActionMap().put("clickButtonescape",new AbstractAction(){
			        public void actionPerformed(ActionEvent ae)
			        {
			    botonCancelar.doClick();
			    System.out.println("button esc clicked");
			        }
			    });
		
		
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0),"clickButtondelete");

getRootPane().getActionMap().put("clickButtondelete",new AbstractAction(){
        public void actionPerformed(ActionEvent ae)
        {
     btnEliminar.doClick();
    System.out.println("button delete clicked");
        }
    });

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("Lista de Eventos");
		scrollPane.setBounds(0, 158, 1146, 265);
		getContentPane().add(scrollPane);

		table_1 = new JXTable() {  
		      public boolean isCellEditable(int row, int column){  
			        return false;  
			      }  
			};
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setToolTipText("Listado de Eventos.");
		//table_1.setAutoCreateRowSorter(false);
		table_1 .getTableHeader().setReorderingAllowed(false);
		table_1.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table_1);
		// String[] columnNames = {"Picture", "Description"};
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				 if (arg0.getClickCount() == 2) {
					    System.out.println("double clicked");
					  
					    List<String> selectedData = new ArrayList<String>();

						//int selectedRow = table_1.rowAtPoint(arg0.getPoint());
						
						
						//Object a = table_1.getModel().getValueAt(table_1.convertRowIndexToView(selectedRow[0]), 0);
						// int[] selectedColumns = table_1.getSelectedColumns();
						//System.out.println(a);

						//if (selectedRow >= 0) {
						int selectedRow = table_1.rowAtPoint(arg0.getPoint());
							System.out.println(selectedRow);
							int col = 0;
							while (col < table_1.getColumnCount()+1) {
								//System.out.println(table_1.getValueAt(selectedRow,
								//		col));
								try {
									int row = table_1.rowAtPoint(arg0.getPoint());
									 String table_click0 = table_1.getModel().getValueAt(table_1.
					                          convertRowIndexToModel(row), col).toString();
					                //System.out.println(table_click0);
					                
									selectedData.add(table_click0);
									System.out.println(selectedData);
								
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}

								col++;
							}
					//seteo del encabezado inicio
					System.out.println(selectedData);
					nroEvento = selectedData.get(2);
					descripcionEvento = selectedData.get(3);
					fechaDesde  = armarFecha.armarFecha( selectedData.get(4));
					fechaHasta  = armarFecha.armarFecha( selectedData.get(5));
					tipoEventoDescripcon = selectedData.get(6);
					
					//seteo del encabezado final 
					
					// selectedData.ad table_1.getValueAt(selectedRow[i],
					// selectedColumns[0]);
					// txtId.setText(selectedData.get(0));
					//txtBuscar.setText(selectedData.get(0));

					// textFecha.setText(selectedData.get(2));
					// textUsu.setText(selectedData.get(4));
					// codTemporal.setText(selectedData.get(1));
					codTemporal = selectedData.get(0);
					
					evento = codTemporal;
					
					System.out.println(evento);
					
					String fechaEvento = selectedData.get(4);
					
					
					DateFormat fechaEventoFormated = new SimpleDateFormat("dd-MM-yyyy");
					
					String str = fechaEvento.substring(0, 10);

				   
					try {
						date = fechaEventoFormated.parse(str);
						System.out.println(" Date: " + date.toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				    
					
					
					
					System.out.println();
					
					if(PreLogin.eventoVigenteConParametros(fechaDesde, fechaHasta,Integer.parseInt(evento)) == 0){
						readOnly=false;
					}
					else{
						readOnly=true;
					}
					
					eventoClase = eventoDAO.obtenerEventoById(codTemporal);
					
					if (MenuPrincipal.reporte){
						Reportes reportes = new Reportes();
						reportes.setVisible(true);
						dispose();
					}
					
					else{		
					DefinicionesGenerales main = new DefinicionesGenerales();
					main.setVisible(true);
					dispose();
					}

				}
				//System.out.println("Selected: " + selectedData);5
				
				

			
				 
					 if (arg0.getClickCount() == 1) {
						 
						 List<String> selectedData = new ArrayList<String>();

							//int selectedRow = table_1.rowAtPoint(arg0.getPoint());
							
							
							//Object a = table_1.getModel().getValueAt(table_1.convertRowIndexToView(selectedRow[0]), 0);
							// int[] selectedColumns = table_1.getSelectedColumns();
							//System.out.println(a);

							//if (selectedRow >= 0) {
							int selectedRow = table_1.rowAtPoint(arg0.getPoint());
								System.out.println(selectedRow);
								int col = 0;
								while (col < table_1.getColumnCount()+1) {
									//System.out.println(table_1.getValueAt(selectedRow,
									//		col));
									try {
										int row = table_1.rowAtPoint(arg0.getPoint());
										 String table_click0 = table_1.getModel().getValueAt(table_1.
						                          convertRowIndexToModel(row), col).toString();
						                //System.out.println(table_click0);
						                
										selectedData.add(table_click0);
										System.out.println(selectedData);
									
									} catch (Exception e) {
										System.out.println(e.getMessage());
									}

									col++;
								
						 codTemporal = selectedData.get(0);
						 }	 
								fechaDesde  = armarFecha.armarFecha( selectedData.get(4));
								fechaHasta  = armarFecha.armarFecha( selectedData.get(5));
								//fechaDesdeDate = new Date();
								
								
								
								
								
								
								
					// txtBuscar.setText(selectedData.get(3));
					// System.out.println(txtBuscar.get));
					 
					 
						
						/*e.setIdEvento(Integer.parseInt(selectedData.get(0)));
						e.setNroEvento((selectedData.get(2)));
						e.setDescripcion (selectedData.get(3));
						e.setFchDesde(armarFecha.armarFecha(selectedData.get(4)));
						e.setFchHasta(armarFecha.armarFecha(selectedData.get(5)));
						e.setId_tipo_evento(    selectedData.get(6));*/
						
								UcsawsEvento eventoVigente = eventoDAO.obtenerEventoById(codTemporal);
								
								if(eventoVigente.getIdEvento() != null){
								  btnModificar.setEnabled(false);
								  btnModificar.setToolTipText("Ya No se puede modificar datos durante ni despues la votacion");
								  btnEliminar.setEnabled(false);
								  btnEliminar.setToolTipText("Ya No se puede modificar datos durante ni despues la votacion");
								}
								else{
								  btnModificar.setEnabled(true);
								  btnModificar.setToolTipText("");
								  btnEliminar.setEnabled(true);
								  btnEliminar.setToolTipText("");
								}
						
				 }

			}
		});
		table_1.setAutoResizeMode(JXTable.AUTO_RESIZE_ALL_COLUMNS);
		recuperarDatos();
		table_1.setModel(model);
		setVisible(true); 
		table_1.removeColumn(table_1.getColumnModel().getColumn(0));
		JLabel lblListaDeGeneros = new JLabel();
		lblListaDeGeneros.setText("LISTA DE EVENTOS");
		lblListaDeGeneros.setFont(new Font("Verdana", Font.BOLD, 18));
		lblListaDeGeneros.setBounds(147, 117, 325, 30);
		getContentPane().add(lblListaDeGeneros);

		JButton btnHome = new JButton("");
		btnHome.setToolTipText("Inicio");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuPrincipal menuprincipal = new MenuPrincipal();
				menuprincipal.setVisible(true);
				dispose();
			}
		});
		btnHome.setIcon(new ImageIcon(VentanaBuscarEvento.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img5 = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg5 = img5.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg5));
		getContentPane().add(btnHome);

		btnNuevo = new JButton("");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaRegistroEvento registro = new VentanaRegistroEvento();
				registro.setVisible(true);
				dispose();
			}
		});
		btnNuevo.setToolTipText("Nuevo");
		btnNuevo.setOpaque(false);
		btnNuevo.setContentAreaFilled(false);
		btnNuevo.setBorderPainted(false);
		btnNuevo.setIcon(new ImageIcon(VentanaBuscarEvento.class
				.getResource("/imgs/add.png")));
		btnNuevo.setBounds(512, 62, 32, 32);
		Image img2 = ((ImageIcon) btnNuevo.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnNuevo.setIcon(new ImageIcon(newimg2));
		if (MenuPrincipal.reporte){
			btnNuevo.setVisible(false);
		}
		
		else{		
			btnNuevo.setVisible(true);
		}
		getContentPane().add(btnNuevo);
		
	    if (Login.rol.compareToIgnoreCase("ADM") == 0) {
	      btnNuevo.setEnabled(true);
	    } else {
	      btnNuevo.setEnabled(false);
	      btnNuevo.setToolTipText("Solo los Administradores pueden acceder a ésta Área");
	    }
		
		
		
		//btnModificarEvento = new JButton("");
				btnModificar = new JButton("");
				btnModificar.setVisible(false);
				btnModificar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if (!codTemporal.equals("")) {
							UcsawsEvento eventoModificar = eventoDAO.obtenerEventoById(codTemporal);
							
							VentanaModificarEvento modificar = new VentanaModificarEvento(eventoModificar);
							modificar.setVisible(true);
							dispose();
						}
						else{
							lblMensaje
							.setText("Por favor seleccione que Evento desea Modificar.");

					Timer t = new Timer(Login.timer, new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							lblMensaje.setText(null);
						}
					});
					t.setRepeats(false);
					t.start();
						}
					}
				});
				btnModificar.setToolTipText("Modificar");
				btnModificar.setOpaque(false);
				btnModificar.setContentAreaFilled(false);
				btnModificar.setBorderPainted(false);
				btnModificar.setIcon(new ImageIcon(VentanaBuscarEvento.class.getResource("/imgs/def.png")));
				//btnModificar.setToolTipText("Nuevo");
				btnModificar.setOpaque(false);
				btnModificar.setContentAreaFilled(false);
				btnModificar.setBorderPainted(false);
				btnModificar.setBounds(596, 63, 32, 32);
				getContentPane().add(btnModificar);
				Image img6 = ((ImageIcon) btnModificar.getIcon()).getImage();
				Image newimg6 = img6.getScaledInstance(32, 32,
						java.awt.Image.SCALE_SMOOTH);
				btnModificar.setIcon(new ImageIcon(newimg6));
				if (MenuPrincipal.reporte){
					btnModificar.setVisible(false);
				}
				
				else{		
					btnModificar.setVisible(true);
				}
				
		        if (Login.rol.compareToIgnoreCase("ADM") == 0) {
		          btnModificar.setEnabled(true);
		        } else {
		          btnModificar.setEnabled(false);
		          btnModificar.setToolTipText("Solo los Administradores pueden acceder a ésta Área");
		        }
		
		if(readOnly==true){
			btnNuevo.setEnabled(false);
			btnNuevo.setToolTipText("Ya No se puede cargar datos durante ni despues la votacion");
			btnEliminar.setEnabled(false);
			btnEliminar.setToolTipText("Ya No se puede eliminar datos durante ni despues la votacion");
			btnModificar.setEnabled(false);
			btnModificar.setToolTipText("Ya No se puede Modificar datos durante ni despues la votacion");
		}

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(86, 106, 432, 14);
		getContentPane().add(lblMensaje);
		
		txtBuscar = new JXFindBar(table_1.getSearchable());
		txtBuscar.setToolTipText("Ingrese texto para filtrar...");
		txtBuscar.setBounds(28, 61, 474, 33);
		getContentPane().add(txtBuscar);
		
		
		table_1.setColumnControlVisible(true);
		
		TableRowFilterSupport.forTable(table_1).searchable(true).apply();
		
		

		// table_1.getColumnModel().getColumn(0).setHeaderValue("Descripcion");

		ListSelectionModel cellSelectionModel = table_1.getSelectionModel();
		//recuperarDatos();
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

	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnEliminar) {
			
			if (!codTemporal.equals("")) {
				if(PreLogin.eventoVigenteConParametros(fechaDesde, fechaHasta,Integer.parseInt(codTemporal))==0){
				int respuesta = JOptionPane.showConfirmDialog(this,
						"¿Esta seguro de eliminar el Evento?", "Confirmación",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_NO_OPTION) {
					EventoDAO eventoDAO = new EventoDAO();

					try {
						eliminado = eventoDAO.eliminarEvento(Integer.parseInt(codTemporal));

					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "sfdsfsfsdfs",
								"Información", JOptionPane.WARNING_MESSAGE);
					}
					
					if(eliminado == false){
						
						lblMensaje.setText("No se ha podido Eliminar - Existen referencias a éste registro.");
						codTemporal = "";
						//txtBuscar.set
						
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
					}
					
					else{

					lblMensaje.setText("Excelente, se ha eliminado el Evento");
					codTemporal = "";
					//txtBuscar.setText("");

					Timer t = new Timer(Login.timer, new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							lblMensaje.setText(null);
						}
					});

					codTemporal = "";
					limpiar();

					model = new EventoJTableModel();

					recuperarDatos();
					table_1.setModel(model);
					table_1.removeColumn(table_1.getColumnModel().getColumn(0));
					// model.fireTableDataChanged();
					// table_1.repaint();
				}}
			} else {
				JOptionPane.showMessageDialog(null, "Ya No se puede Eliminar el Evento durante ni despues la votacion","Aviso", JOptionPane.WARNING_MESSAGE);
				
			}
		}
			else{
				lblMensaje
				.setText("Por favor seleccione que Evento desea Eliminar");

		Timer t = new Timer(Login.timer, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				lblMensaje.setText(null);
			}
		});
		t.setRepeats(false);
		t.start();

		// JOptionPane.showMessageDialog(null,
		// "Por favor seleccione que Genero desea Eliminar",
		// "Información",JOptionPane.WARNING_MESSAGE);
			}
		}
		if (e.getSource() == botonCancelar) {
			/*Administracion administracion = new Administracion();
			administracion.setVisible(true);
			this.dispose();*/
			
			
			if (MenuPrincipal.reporte){
				MenuPrincipal menu = new MenuPrincipal();
				menu.setVisible(true);
				dispose();
			}
			
			else{		
				MenuPrincipal menuPrincipal = new MenuPrincipal();
				menuPrincipal.setVisible(true);
				this.dispose();
			}
			
			
			
			
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
		// botonModificar.setEnabled(true);
		btnEliminar.setEnabled(bEliminar);
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

		
		query.setTipoQueryGenerico(30);

		query.setQueryGenerico("consultarEvento");

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();
		
		// json string to List<String>;
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = response.getQueryGenericoResponse();
		List<UcsawsEvento> lista = new ArrayList<UcsawsEvento>();
		try{
		lista = mapper.readValue(jsonInString,new TypeReference<List<UcsawsEvento>>(){});
		}
		catch(Exception e){
			System.out.println(e);
		}

		if (lista.isEmpty()) {
			JOptionPane.showMessageDialog(null, "algo salio mal",
					"Advertencia", JOptionPane.WARNING_MESSAGE);
			//return lista;
		}

		else {
			obtenerModeloA(table_1,lista );
			
	    // return lista;
		}


	}

	void LimpiarCampos() {
		//txtBuscar.sea
		// textFecha.setText("");
		// textUsu.setText("");
		codTemporal = "";
		// txtId.setText("");

	}
	
	public void filter(String query){
		
		
		
		/*TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
		
		
		
		table_1.setRowSorter(tr);
		
	tr.setRowFilter(RowFilter.regexFilter(query));*/
	    
	    TableRowSorter sorter = new TableRowSorter(table_1.getModel());
	    sorter.setRowFilter(RowFilter.regexFilter(query));
	    table_1.setRowSorter(sorter);
		
		
	}
	
	/*public DefaultTableModel obtenerModelo(JTable tabla, List<UcsawsEvento> evento){
		
		
		DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		Iterator<UcsawsEvento> ite = evento.iterator();

		String header[] = new String[] { "ID","Item","Nro.", "Desc. Evento","Inicio","Fin","Desc. Tipo Evento" };
		model.setColumnIdentifiers(header);

		UcsawsEvento aux;
		Integer cont = 1;
		while (ite.hasNext()) {
			aux = ite.next();

			Object[] row = { aux.getIdEvento(), cont , aux.getNroEvento() , aux.getDescripcion(), 
					new SimpleDateFormat("dd-MM-yyyy HH:mm").format((aux.getFchDesde())),
					new SimpleDateFormat("dd-MM-yyyy HH:mm").format((aux.getFchHasta())), aux.getUcsawsTipoEvento().getDescripcion() };
					

 
					//new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
					
					
			model.addRow(row);
			cont++;
		
		
		


		
	}
		return model;
	}*/
	
	public AbstractTableModel obtenerModeloA(JXTable tabla, List<UcsawsEvento> evento){
		
		
		//AbstractTableModel model = (DefaultTableModel) tabla.getModel();
		Iterator<UcsawsEvento> ite = evento.iterator();

		//String header[] = new String[] { "ID","Item","Nro.", "Desc. Evento","Inicio","Fin","Desc. Tipo Evento" };
		//model.setColumnIdentifiers(header);

		UcsawsEvento aux;
		Integer cont = 1;
		while (ite.hasNext()) {
			aux = ite.next();

			Object[] row = { aux.getIdEvento(), cont , aux.getNroEvento() , aux.getDescripcion(), 
					new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFchDesde())),
					new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFchHasta())), aux.getUcsawsTipoEvento().getDescripcion() };
					


					//new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
				model.eventos.add(row);
					
			
			cont++;
		
		
		
	}
		
		return model;
	}
	
	  
	    public String horadeDate(Date fecha) {
	        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
	        String time = localDateFormat.format(fecha);
	        System.out.println(time);
		return time;
	    }
}
