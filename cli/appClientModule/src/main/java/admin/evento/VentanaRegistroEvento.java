package src.main.java.admin.evento;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.Calendario;
import src.main.java.admin.validator.EventoValidator;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import src.main.java.login.PreLogin;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentanaRegistroEvento extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar, btnFecha;
	private JTable table;
	private VentanaRegistroEvento ventanaRegistroPersona;
	private EventoJTableModel model = new EventoJTableModel();
	private JScrollPane scrollPane;
	
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date date = new Date();
	
	
	
	private EventoValidator eventoValidator = new EventoValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();

	private JFormattedTextField txtFchDesde, txtFchHasta;
	private JLabel lblTipoEvento;
	private JComboBox cmbTipoEvento;
	private JLabel lblNro;
	private JTextField txtNro;
	private JLabel lblDescripcion;
	private JTextField txtDescripcion;
	private JLabel lblFechaDesde;
	private JTextField txtHoraDesde;
	private JTextField txtMinDesde;
	private JTextField txtHoraHasta;
	private JTextField txtMinHasta;
	private JTextField txtFiltrar;
	
	private DefaultTableModel dm;
	
	private EventoDAO eventoDAO = new EventoDAO();
	

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroEvento() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtNro.requestFocus();
			}
		});

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroEvento.class
				.getResource("/imgs/save.png")));
		botonGuardar.setBounds(339, 52, 32, 32);
		botonGuardar.setOpaque(false);
		botonGuardar.setContentAreaFilled(false);
		botonGuardar.setBorderPainted(false);
		Image img3 = ((ImageIcon) botonGuardar.getIcon()).getImage();
		Image newimg3 = img3.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonGuardar.setIcon(new ImageIcon(newimg3));

		botonCancelar = new JButton();
		botonCancelar.setBackground(Color.WHITE);
		botonCancelar.setToolTipText("Atrás");
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroEvento.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(1114, 415, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		

		labelTitulo = new JLabel();
		labelTitulo.setText("NUEVO EVENTO");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(1152, 476);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("Lista de Eventos\r\n");
		scrollPane.setBounds(0, 257, 1146, 160);
		getContentPane().add(scrollPane);

		table = new JTable()  {  
		      public boolean isCellEditable(int row, int column){  
			        return false;  
			      }  
			};
		table.setToolTipText("Lista de Eventos");
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
List<String> selectedData = new ArrayList<String>();

				
				int selectedRow = table.rowAtPoint(arg0.getPoint());
					//System.out.println(selectedRow);
					int col = 0;
					while (col < table.getColumnCount()+1) {
						//System.out.println(table_1.getValueAt(selectedRow,
						//		col));
						try {
							int row = table.rowAtPoint(arg0.getPoint());
							 String table_click0 = table.getModel().getValueAt(table.
			                          convertRowIndexToModel(row), col).toString();
			                //System.out.println(table_click0);
			                
							selectedData.add(table_click0);
							//System.out.println(selectedData);
						
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}

						col++;
					}
					
					//Selected: [1, 345343, TEST ANDE, 01/09/2015 07:00:00, 11/04/2016 23:30:00, ELECCIONES GENERALES]
					// selectedData.ad table_1.getValueAt(selectedRow[i],
					// selectedColumns[0]);
					// txtId.setText(selectedData.get(0));
					txtNro.setText(selectedData.get(2));
					 txtDescripcion.setText(selectedData.get(3));
					 txtFchDesde.setText(selectedData.get(4).substring(0, 10));
					 txtHoraDesde.setText(selectedData.get(4).substring(11, 13));
					 txtMinDesde.setText(selectedData.get(4).substring(14, 16));
					 
					 txtFchHasta.setText( selectedData.get(5).substring(0, 10));
					 txtHoraHasta.setText(selectedData.get(5).substring(11, 13));
					 txtMinHasta.setText(selectedData.get(5).substring(14, 16));
					// textUsu.setText(selectedData.get(4));
					// codTemporal.setText(selectedData.get(1));
					codTemporal =selectedData.get(0);
					
					// actualizar combo persona
					DefaultComboBoxModel dtm = (DefaultComboBoxModel)  cmbTipoEvento.getModel();
					
					//System.out.println(dtm.getSize());
					int cont=0;
					Boolean finded=false;
					int tamanho = dtm.getSize();
					while(cont < dtm.getSize()){
						if (dtm.getElementAt(cont).toString().compareTo(selectedData.get(6).toString())==0)
						{
							//Item item = (Item) dtm.getElementAt(cont);
							//Integer tipoListaSelected = item.getId();
							cmbTipoEvento.setSelectedIndex(cont);
							finded=true;
							break;
						}
						
							cont++;
						
						//System.out.println(dtm.getElementAt(0));
					}
					if(finded==false){
						String a = selectedData.get(6).toString();
						cmbTipoEvento.addItem(a);
						cmbTipoEvento.setSelectedIndex(tamanho);
					}
					// actualizar combo persona

				
				System.out.println("Selected: " + selectedData);

			}
		});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		recuperarDatos();
		table.setModel(dm);

		btnHome = new JButton("");
		btnHome.setToolTipText("Inicio");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuPrincipal menuprincipal = new MenuPrincipal();
				menuprincipal.setVisible(true);
				dispose();
			}
		});
		btnHome.setIcon(new ImageIcon(VentanaRegistroEvento.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		lblTipoEvento = new JLabel();
		lblTipoEvento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoEvento.setText("Tipo Evento:");
		lblTipoEvento.setBounds(96, 206, 95, 25);
		getContentPane().add(lblTipoEvento);

		cmbTipoEvento = new JComboBox(recuperarDatosComboBoxTipoEvento());
		cmbTipoEvento.setBounds(213, 208, 340, 25);
		getContentPane().add(cmbTipoEvento);

		lblNro = new JLabel();
		lblNro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNro.setText("N°:");
		lblNro.setBounds(130, 52, 61, 25);
		getContentPane().add(lblNro);

		txtNro = new JTextField();
		txtNro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtNro.setBounds(213, 54, 108, 25);
		getContentPane().add(txtNro);
		txtNro.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(188, 242, 619, 14);
		getContentPane().add(lblMensaje);

		lblDescripcion = new JLabel();
		lblDescripcion.setText("Descripcion:");
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcion.setBounds(75, 95, 116, 25);
		getContentPane().add(lblDescripcion);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(213, 95, 158, 25);
		getContentPane().add(txtDescripcion);

		lblFechaDesde = new JLabel();
		lblFechaDesde.setText("Fch. Desde:");
		lblFechaDesde.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFechaDesde.setBounds(75, 134, 116, 25);
		getContentPane().add(lblFechaDesde);

		txtFchDesde = new JFormattedTextField();
		txtFchDesde.setText(dateFormat.format(date));
		txtFchDesde.setBounds(213, 134, 89, 25);
		getContentPane().add(txtFchDesde);

		JButton btnFchDesde = new JButton("");
		btnFchDesde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendario cal = new Calendario(ventanaRegistroPersona);
				cal.displayDate();
				if (!Calendario.fechafinalSeleccionada.startsWith("/")) {
					txtFchDesde.setText(Calendario.fechafinalSeleccionada);
				}
			}
		});
		btnFchDesde.setToolTipText("Calendario");
		btnFchDesde.setBounds(312, 140, 30, 23);
		btnFchDesde.setOpaque(false);
		btnFchDesde.setContentAreaFilled(false);
		btnFchDesde.setBorderPainted(false);
		Image img5 = ((ImageIcon) botonGuardar.getIcon()).getImage();
		Image newimg5 = img5.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnFchDesde.setIcon(new ImageIcon(VentanaRegistroEvento.class
				.getResource("/imgs/cal.png")));
		getContentPane().add(btnFchDesde);

		JLabel lblFchHasta = new JLabel();
		lblFchHasta.setText("Fch. Hasta:");
		lblFchHasta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFchHasta.setBounds(75, 170, 116, 25);
		getContentPane().add(lblFchHasta);

		txtFchHasta = new JFormattedTextField();
		txtFchHasta.setText(dateFormat.format(date));
		txtFchHasta.setBounds(213, 170, 89, 25);
		getContentPane().add(txtFchHasta);
		
		JButton btnFechaHasta;
		btnFechaHasta = new JButton("");
		btnFechaHasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendario cal = new Calendario(ventanaRegistroPersona);
				cal.displayDate();
				if (!Calendario.fechafinalSeleccionada.startsWith("/")) {
					txtFchHasta.setText(Calendario.fechafinalSeleccionada);
				}
			}
		});
		btnFechaHasta.setIcon(new ImageIcon(VentanaRegistroEvento.class.getResource("/imgs/cal.png")));
		btnFechaHasta.setToolTipText("Calendario");
		btnFechaHasta.setOpaque(false);
		btnFechaHasta.setContentAreaFilled(false);
		btnFechaHasta.setBorderPainted(false);
		btnFechaHasta.setBounds(312, 174, 30, 23);
		getContentPane().add(btnFechaHasta);
		
		txtHoraDesde = new JTextField();
		txtHoraDesde.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtHoraDesde.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Integer hora = Integer.parseInt(txtHoraDesde.getText());
				if ( hora > 23){
					txtHoraDesde.setText("23");
				}
				else
					if (hora < 1){
						txtHoraDesde.setText("01");
					}
				
				
			}
			
		});
		txtHoraDesde.setBounds(347, 138, 30, 25);
		getContentPane().add(txtHoraDesde);
		txtHoraDesde.setColumns(10);
		
		txtMinDesde = new JTextField();
		txtMinDesde.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtMinDesde.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				Integer min = Integer.parseInt(txtMinDesde.getText());
				if ( min > 59){
					txtMinDesde.setText("59");
				}
				else
					if (min < 0 ){
						txtMinDesde.setText("00");
					}
			}
			
		});
		txtMinDesde.setColumns(10);
		txtMinDesde.setBounds(401, 138, 30, 25);
		getContentPane().add(txtMinDesde);
		
		txtHoraHasta = new JTextField();
		txtHoraHasta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtHoraHasta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				Integer hora = Integer.parseInt(txtHoraHasta.getText());
				if ( hora > 23){
					txtHoraHasta.setText("23");
				}
				else
					if (hora < 1){
						txtHoraHasta.setText("01");
					}
				
				
			}
			
		});
		txtHoraHasta.setColumns(10);
		txtHoraHasta.setBounds(347, 169, 30, 25);
		getContentPane().add(txtHoraHasta);
		
		txtMinHasta = new JTextField();
		txtMinHasta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtMinHasta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				Integer min = Integer.parseInt(txtMinHasta.getText());
				if ( min > 59){
					txtMinHasta.setText("59");
				}
				else
					if (min < 0 ){
						txtMinHasta.setText("00");
					}
				
			}
			
		});
		txtMinHasta.setColumns(10);
		txtMinHasta.setBounds(401, 169, 30, 25);
		getContentPane().add(txtMinHasta);
		
		JLabel lblNewLabel = new JLabel(":");
		lblNewLabel.setBounds(387, 143, 4, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel(":");
		label.setBounds(387, 174, 4, 19);
		getContentPane().add(label);

		table.removeColumn(table.getColumnModel().getColumn(0));
		recuperarDatos();
		
		cmbTipoEvento.setSelectedIndex(-1);
		
		txtFiltrar = new JTextField();
		txtFiltrar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtFiltrar.setText("");
				txtFiltrar.setForeground(Color.BLACK);
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtFiltrar.getText().length()== 0){
					String query =txtFiltrar.getText().toUpperCase(); 
					filter(query);
					txtFiltrar.setText("Escriba para Filtrar");
					txtFiltrar.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		txtFiltrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String query =txtFiltrar.getText().toUpperCase(); 
				filter(query);
			}
		});
		txtFiltrar.setText("");
		txtFiltrar.setForeground(Color.LIGHT_GRAY);
		txtFiltrar.setEditable(true);
		txtFiltrar.setBounds(234, 415, 319, 25);
		
		getContentPane().add(txtFiltrar);

	}

	private void limpiar() {
		codTemporal = "";
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	public void actionPerformed(ActionEvent e) {
		Item item;
		Integer tipoEventoSelected = null;
		if (e.getSource() == botonGuardar) {
			try {
				
				if(cmbTipoEvento.getSelectedIndex()!= -1){
				item = (Item) cmbTipoEvento.getSelectedItem();
				tipoEventoSelected = item.getId();
				}

				if (!(txtNro.getText().length() == 0) 
						
				&&  !(txtHoraDesde.getText().length() == 0)
				
				&&  !(txtMinDesde.getText().length() == 0)
				
				&&  !(txtHoraHasta.getText().length() == 0)
				
				&&  !(txtMinHasta.getText().length() == 0)

				&& !(txtFchDesde.getText().length() == 0)
				
				&& (cmbTipoEvento.getSelectedIndex()!= -1)

				&& !(txtDescripcion.getText().length() == 0)) {
					if (txtNro.getText().length() > 7) {
						lblMensaje
								.setText("El codigo debe ser de maximo 7 caracteres.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					} else
						
						if(codTemporal!=""){
						
						eventoDAO.actualizarEvento(txtDescripcion.getText(),tipoEventoSelected.toString(), 
								txtFchDesde.getText() + " " + txtHoraDesde.getText() + ":" + txtMinDesde.getText(),
								txtFchHasta.getText() + " " + txtHoraHasta.getText() + ":" + txtMinHasta.getText(),
								txtNro.getText(),codTemporal);
						
						recuperarDatos();
						table.setModel(dm);
						model.fireTableDataChanged();
						table.removeColumn(table.getColumnModel().getColumn(0));
						// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
						lblMensaje
								.setText("Excelente, se ha modificado el Evento.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();

					//txt
					//.setText("");
						codTemporal="";
						txtNro.setText("");
						txtDescripcion.setText("");
						txtFchDesde.setText(dateFormat.format(date));
						txtFchHasta.setText(dateFormat.format(date));
						txtHoraDesde.setText("");
						txtHoraHasta.setText("");
						txtMinDesde.setText("");
						txtMinHasta.setText("");
						cmbTipoEvento.setSelectedIndex(-1);
					
						
					}
					
					
					
					if(codTemporal==""){
						if (EventoValidator.eventoVigente( txtFchDesde.getText().substring(6, 10)  , txtFchHasta.getText()
								+  " 23:59", tipoEventoSelected) == false){
					if

					(eventoValidator.ValidarCodigo(txtNro.getText()) == false) {
						// if (personaValidator.ValidarCodigo(txtCI.getText())
						// == false) {
						// Genero genero = new Genero();
						// genero.setDescripcion(textGenero.getText());
						
						
						String horaDesde = armarHoraMinuto(txtHoraDesde.getText(), txtMinDesde.getText());
						String fechaDesdeFinal = txtFchDesde.getText() + " " + horaDesde;
						
						String horaHasta = armarHoraMinuto(txtHoraHasta.getText(), txtMinHasta.getText());
						String fechaHastaFinal = txtFchHasta.getText() + " " + horaHasta; 
						

						Calendar calendar = new GregorianCalendar();
						int year = calendar.get(Calendar.YEAR);

						ApplicationContext ctx = SpringApplication
								.run(WeatherConfiguration.class);

						WeatherClient weatherClient = ctx
								.getBean(WeatherClient.class);
						QueryGenericoRequest query = new QueryGenericoRequest();

						// para registrar se inserta el codigo es 1
						query.setTipoQueryGenerico(1);
						System.out.println(Login.userLogeado);
						query.setQueryGenerico("INSERT INTO ucsaws_evento"
								+ "( id_evento, nro_evento, descripcion, id_tipo_evento, fch_desde,fch_hasta, "
								+ " usuario_ins,fch_ins, usuario_upd, fch_upd) "
								+ "VALUES ("
								+ "nextval('ucsaws_tipo_evento_seq')" + " , "
								+ " upper('"
								+ txtNro.getText()
								+ "'), "

								+ " upper('"
								+ txtDescripcion.getText()
								+ "'), "

								+ tipoEventoSelected
								+ ", "

								+ " TO_TIMESTAMP('"
								+ fechaDesdeFinal
								+ "', 'DD/MM/YYYY HH24:MI:SS'), "

								+ " TO_TIMESTAMP('"
								+ fechaHastaFinal
								+ "', 'DD/MM/YYYY HH24:MI:SS'), '"
								
								+ Login.userLogeado
								+ "' , now(), '"
								+ Login.userLogeado + "' , now())");

						QueryGenericoResponse response = weatherClient
								.getQueryGenericoResponse(query);
						weatherClient.printQueryGenericoResponse(response);

						model = new EventoJTableModel();
						recuperarDatos();
						table.setModel(dm);
						model.fireTableDataChanged();
						table.removeColumn(table.getColumnModel().getColumn(0));
						// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
						lblMensaje
								.setText("Excelente, se ha guardado el Evento.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();

						
						
						DateFormat dateFormat;
						Date date;
						dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						date = new Date();
						System.out.println(dateFormat.format(date));
						txtFchDesde.setText(dateFormat.format(date));
						
						cmbTipoEvento.setSelectedIndex(-1);
						txtNro.setText("");
						txtDescripcion.setText("");
						txtFchDesde.setText(dateFormat.format(date));
						txtFchHasta.setText(dateFormat.format(date));
						txtHoraDesde.setText("");
						txtHoraHasta.setText("");
						txtMinDesde.setText("");
						txtMinHasta.setText("");
						txtFiltrar.setText("Escriba para Filtrar");
						txtFiltrar.setForeground(Color.LIGHT_GRAY);
						

						// this.dispose();

					
					}else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje
								.setText("Ya existe un Evento con este Nro. : "
										+ txtNro.getText());
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					}
						}else{
							lblMensaje
							.setText("Error. Ya existe un evento de ese tipo para ese año. ");
					Timer t = new Timer(Login.timer, new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							lblMensaje.setText(null);
						}
					});
						}

				}
				}

				else {
					// JOptionPane.showMessageDialog(null, ,
					// "Información",JOptionPane.WARNING_MESSAGE);
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
				JOptionPane.showMessageDialog(null,
						"Error al intentar insertar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		if (e.getSource() == botonCancelar) {
			VentanaBuscarEvento persona = new VentanaBuscarEvento();
			persona.setVisible(true);
			this.dispose();

		}
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

		query.setQueryGenerico("select ev.id_evento, nro_evento,ev.descripcion, to_char(fch_desde, 'DD/MM/YYYY HH24:MI:SS') as desde, to_char(fch_hasta, 'DD/MM/YYYY HH24:MI:SS') as hasta , tev.descripcion as Tdescripcion "

				+ " from ucsaws_evento ev join ucsaws_tipo_evento tev on (ev.id_tipo_evento = tev.id_tipo_evento)"
				+ "order by nro_evento");

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

Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		//Vector<Object> vector = new Vector<Object>();
		

		int ite = 0;
		String campo4, campo5 = "";
		int contador = 0;
		while (filas.size() > ite) {
			contador = contador + 1;
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), String.valueOf(contador),fil.get(1).toString(),
					fil.get(2).toString(),fil.get(3).toString(),fil.get(4).toString()
					,fil.get(5).toString()};

			//model.ciudades.add(fin);
			int pos = 0;
			 Vector<Object> vector = new Vector<Object>();
			while(pos < fin.length){
			vector.add(fin[pos]);
			pos++;
			}
			ite++;
			data.add(vector);
		}
		 
		
		
		
		  // names of columns
		
		String[] colNames = new String[] {"ID","Item","Nro.", "Desc. Evento","Inicio","Fin","Desc. Tipo Evento"};
		
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = colNames.length;
	    for (int column = 0; column < columnCount; column++) {
	        columnNames.add(colNames[column]);
	    }
	    
	    dm = new DefaultTableModel(data, columnNames);

	}

	private Vector recuperarDatosComboBoxTipoEvento() {
		Vector model = new Vector();
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

		// query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
		// +
		// "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");

		query.setQueryGenerico("SELECT id_tipo_evento, descripcion"
				+ " from ucsaws_tipo_evento " + "order by descripcion");

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
		while (filas.size() > ite) {
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), fil.get(1).toString(), };

			listas.add(fin);
			model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
			ite++;
		}
		return model;

	}
	
	public String armarHoraMinuto(String hora, String minuto){
		
		
		String result = "";
		
		result = hora + ":" + minuto;
		
		
		
		return result;
		
	}
	public void filter(String query){
		
		
		
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
		
		
		
		table.setRowSorter(tr);
		
	tr.setRowFilter(RowFilter.regexFilter(query));
		
		
	}
}