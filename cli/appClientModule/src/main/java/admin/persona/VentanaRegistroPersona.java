package src.main.java.admin.persona;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
import javax.swing.table.TableRowSorter;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.Calendario;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.DateValidator;
import src.main.java.admin.validator.PersonaValidator;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaRegistroPersona extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar, btnFecha;
	private JTable table;
	private VentanaRegistroPersona ventanaRegistroPersona;
	private PersonaJTableModel model = new PersonaJTableModel();
	private JScrollPane scrollPane;

	private PersonaValidator personaValidator = new PersonaValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();

	private JFormattedTextField txtFechaNac;

	private JLabel lblPaisActual;
	private JComboBox cmbPaisActual, cmbPaisOrigen;
	private JLabel lblGenero;
	private JComboBox cmbGenero,cmbNacionalidad;
	private JLabel lblCI;
	private JTextField txtCI;
	private JLabel lblNombreApellido;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JLabel lblFchNac;
	private JLabel lblCelular;
	private JLabel lblLineaBaja;
	private JTextField txtCelular;
	private JTextField txtLineaBaja;
	private JLabel lblNacionalidad;
	
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date date = new Date();
	
	private DateValidator dateValidator;
	private JTextField txtFiltrar;
	
	private DefaultTableModel dm;
	private JTextField txtEmail;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroPersona() {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtCI.requestFocus();
			}
		});

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroPersona.class
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
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroPersona.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(1114, 415, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		//Image newimg4 = img4.getScaledInstance(32, 32,
			//	java.awt.Image.SCALE_SMOOTH);

		labelTitulo = new JLabel();
		labelTitulo.setText("NUEVA PERSONA");
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
		scrollPane.setToolTipText("Lista de Personas\r\n");
		scrollPane.setBounds(0, 261, 1146, 158);
		getContentPane().add(scrollPane);

		table = new JTable() {  
		      public boolean isCellEditable(int row, int column){  
			        return false;  
			      }  
			};
		table.setToolTipText("");
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				List<String> selectedData = new ArrayList<String>();

				int selectedRow = table.rowAtPoint(arg0.getPoint());
					System.out.println(selectedRow);
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
							System.out.println(selectedData);
						
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}

						col++;
					}
					// selectedData.ad table_1.getValueAt(selectedRow[i],
					// selectedColumns[0]);
					// txtId.setText(selectedData.get(0));
					// txtCod.setText(selectedData.get(0));
					// txtDesc.setText(selectedData.get(1));
					// textFecha.setText(selectedData.get(2));
					// textUsu.setText(selectedData.get(4));
					// codTemporal.setText(selectedData.get(1));
					codTemporal = selectedData.get(0);
					txtCI.setText(selectedData.get(2));
					txtNombres.setText(selectedData.get(3));
					txtApellidos.setText(selectedData.get(4));
					txtFechaNac.setText(selectedData.get(5));
					
					txtLineaBaja.setText(selectedData.get(9));
					txtCelular.setText(selectedData.get(10));
					
					
					// actualizar combo lista
					DefaultComboBoxModel dtmlista = (DefaultComboBoxModel)  cmbGenero.getModel();
					//System.out.println(dtm.getSize());
					int cont=0;
					Boolean findedlista=false;
					int tamanho = dtmlista.getSize();
					while(cont < dtmlista.getSize()){
						if (dtmlista.getElementAt(cont).toString().compareToIgnoreCase(selectedData.get(8).toString() )==0)
						{
							//Item item = (Item) dtm.getElementAt(cont);
							//Integer tipoListaSelected = item.getId();
							cmbGenero.setSelectedIndex(cont);
							findedlista=true;
							break;
						}
						
							cont++;
						
						//System.out.println(dtm.getElementAt(0));
					}
					if(findedlista==false){
						cmbGenero.addItem(selectedData.get(8));
						cmbGenero.setSelectedIndex(tamanho);
					}
					// actualizar combo lista
					
					// actualizar combo pais origen
					DefaultComboBoxModel dtmlista1 = (DefaultComboBoxModel)  cmbPaisOrigen .getModel();
					//System.out.println(dtm.getSize());
					int cont2=0;
					Boolean findedlista1=false;
					int tamanho2 = dtmlista1.getSize();
					while(cont2 < dtmlista1.getSize()){
						if (dtmlista1.getElementAt(cont2).toString().compareToIgnoreCase(selectedData.get(6).toString())==0)
						{
							//Item item = (Item) dtm.getElementAt(cont);
							//Integer tipoListaSelected = item.getId();
							cmbPaisOrigen.setSelectedIndex(cont2);
							findedlista=true;
							break;
						}
						
							cont2++;
						
						//System.out.println(dtm.getElementAt(0));
					}
					if(findedlista1==false){
						cmbPaisOrigen.addItem(selectedData.get(6));
						cmbPaisOrigen.setSelectedIndex(tamanho2);
					}
					// actualizar combo pais origen
					
					
					// actualizar combo pais origen
					DefaultComboBoxModel dtmlista3 = (DefaultComboBoxModel)  cmbPaisActual .getModel();
					//System.out.println(dtm.getSize());
					int cont3=0;
					Boolean findedlista3=false;
					int tamanho3 = dtmlista3.getSize();
					while(cont3 < dtmlista3.getSize()){
						if (dtmlista3.getElementAt(cont3).toString().compareToIgnoreCase(selectedData.get(7).toString())==0)
						{
							//Item item = (Item) dtm.getElementAt(cont);
							//Integer tipoListaSelected = item.getId();
							cmbPaisActual.setSelectedIndex(cont3);
							findedlista3=true;
							break;
						}
						
							cont3++;
						
						//System.out.println(dtm.getElementAt(0));
					}
					if(findedlista3==false){
						cmbPaisActual.addItem(selectedData.get(7));
						cmbPaisActual.setSelectedIndex(tamanho3);
					}
					// actualizar combo pais origen
					
					
					// actualizar combo pais origen
					DefaultComboBoxModel dtmlista4 = (DefaultComboBoxModel)  cmbNacionalidad .getModel();
					//System.out.println(dtm.getSize());
					int cont4=0;
					Boolean findedlista4=false;
					int tamanho4 = dtmlista4.getSize();
					while(cont4 < dtmlista4.getSize()){
						if (dtmlista4.getElementAt(cont4).toString().compareToIgnoreCase(selectedData.get(11).toString())==0)
						{
							//Item item = (Item) dtm.getElementAt(cont);
							//Integer tipoListaSelected = item.getId();
							cmbNacionalidad.setSelectedIndex(cont4);
							findedlista4=true;
							break;
						}
						
							cont4++;
						
						//System.out.println(dtm.getElementAt(0));
					}
					if(findedlista4==false){
						cmbNacionalidad.addItem(selectedData.get(11));
						cmbNacionalidad.setSelectedIndex(tamanho4);
					}
					// actualizar combo pais origen
					

				
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroPersona.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		cmbPaisOrigen = new JComboBox(recuperarDatosComboBoxPaisOrigen());
		cmbPaisOrigen.setBounds(213, 151, 340, 20);
		cmbPaisOrigen.setSelectedIndex(-1);
		getContentPane().add(cmbPaisOrigen);

		JLabel lblPaisOrigen = new JLabel();
		lblPaisOrigen.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPaisOrigen.setText("Pais Origen:");
		lblPaisOrigen.setBounds(130, 149, 61, 25);
		getContentPane().add(lblPaisOrigen);

		lblPaisActual = new JLabel();
		lblPaisActual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPaisActual.setText("PaisActual:");
		lblPaisActual.setBounds(101, 182, 90, 25);
		getContentPane().add(lblPaisActual);

		cmbPaisActual = new JComboBox(recuperarDatosComboBoxPaisActual());
		cmbPaisActual.setBounds(213, 184, 340, 20);
		cmbPaisActual.setSelectedIndex(-1);
		getContentPane().add(cmbPaisActual);

		lblGenero = new JLabel();
		lblGenero.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGenero.setText("Genero:");
		lblGenero.setBounds(130, 215, 61, 25);
		getContentPane().add(lblGenero);

		cmbGenero = new JComboBox(recuperarDatosComboBoxGenero());
		cmbGenero.setBounds(213, 217, 340, 20);
		cmbGenero.setSelectedIndex(-1);
		getContentPane().add(cmbGenero);
		
		cmbNacionalidad = new JComboBox(recuperarDatosComboBoxNacionalidad());
		cmbNacionalidad.setBounds(705, 195, 158, 20);
		cmbNacionalidad.setSelectedIndex(-1);
		getContentPane().add(cmbNacionalidad);

		lblCI = new JLabel();
		lblCI.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCI.setText("C.I. N°:");
		lblCI.setBounds(130, 52, 61, 25);
		getContentPane().add(lblCI);

		txtCI = new JTextField();
		txtCI.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtCI.setBounds(213, 54, 108, 26);
		getContentPane().add(txtCI);
		txtCI.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(263, 247, 619, 14);
		getContentPane().add(lblMensaje);

		lblNombreApellido = new JLabel();
		lblNombreApellido.setText("Nombre(s) y Apellido(s):");
		lblNombreApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombreApellido.setBounds(75, 83, 116, 25);
		getContentPane().add(lblNombreApellido);

		txtNombres = new JTextField();
		txtNombres.setColumns(10);
		txtNombres.setBounds(213, 85, 158, 26);
		getContentPane().add(txtNombres);

		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(381, 85, 172, 26);
		getContentPane().add(txtApellidos);

		lblFchNac = new JLabel();
		lblFchNac.setText("Fch. Nac.:");
		lblFchNac.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFchNac.setBounds(75, 113, 116, 25);
		getContentPane().add(lblFchNac);

		lblCelular = new JLabel();
		lblCelular.setText("Tel. Celular:");
		lblCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCelular.setBounds(579, 83, 116, 25);
		getContentPane().add(lblCelular);

		lblLineaBaja = new JLabel();
		lblLineaBaja.setText("Linea Baja:");
		lblLineaBaja.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLineaBaja.setBounds(579, 119, 116, 25);
		getContentPane().add(lblLineaBaja);

		txtCelular = new JTextField();
		txtCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtCelular.setColumns(10);
		txtCelular.setBounds(705, 85, 158, 26);
		getContentPane().add(txtCelular);

		txtLineaBaja = new JTextField();
		txtLineaBaja.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		txtLineaBaja.setColumns(10);
		txtLineaBaja.setBounds(705, 121, 158, 26);
		getContentPane().add(txtLineaBaja);

		txtFechaNac = new JFormattedTextField();
		txtFechaNac.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				boolean s= true;


				if(txtFechaNac.getText() == null){
					s = false;
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.setLenient(false);
				
				try {
					
					//if not valid, it will throw ParseException
					Date date = sdf.parse(txtFechaNac.getText());
					System.out.println(date);
				
				} catch (ParseException e) {
					
					e.printStackTrace();
					s =  false;
					
					lblMensaje
					.setText("Ingrese formato de fecha correcto.");
			Timer t = new Timer(Login.timer,
					new ActionListener() {

						public void actionPerformed(
								ActionEvent e) {
							lblMensaje.setText(null);
						}
					});
			t.setRepeats(false);
			t.start();
			
			txtFechaNac.requestFocus();
			txtFechaNac.selectAll();
			
					
				}
				
				//s = true;
			

				
			}
		});
		
		
		
		txtFechaNac.setText(dateFormat.format(date));
		txtFechaNac.setBounds(213, 115, 80, 26);
		getContentPane().add(txtFechaNac);

		JButton btnFecha = new JButton("");
		btnFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendario cal = new Calendario(ventanaRegistroPersona);
				cal.displayDate();
				if (!Calendario.fechafinalSeleccionada.startsWith("/")) {
					txtFechaNac.setText(Calendario.fechafinalSeleccionada);
				}
			}
		});
		btnFecha.setToolTipText("Calendario");
		btnFecha.setBounds(302, 117, 30, 23);
		btnFecha.setOpaque(false);
		btnFecha.setContentAreaFilled(false);
		btnFecha.setBorderPainted(false);
		Image img5 = ((ImageIcon) botonGuardar.getIcon()).getImage();
		Image newimg5 = img5.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnFecha.setIcon(new ImageIcon(VentanaRegistroPersona.class
				.getResource("/imgs/cal.png")));
		getContentPane().add(btnFecha);
		
		lblNacionalidad = new JLabel();
		lblNacionalidad.setText("Nacionalidad:");
		lblNacionalidad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNacionalidad.setBounds(579, 193, 116, 25);
		getContentPane().add(lblNacionalidad);
		
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
			public void keyReleased(KeyEvent arg0) {
				
				String query =txtFiltrar.getText().toUpperCase(); 
				filter(query);
				
			}
		});
		txtFiltrar.setText("Escriba para filtrar...");
		txtFiltrar.setForeground(Color.LIGHT_GRAY);
		txtFiltrar.setEditable(true);
		txtFiltrar.setBounds(209, 421, 319, 26);
		getContentPane().add(txtFiltrar);
		
		txtEmail = new JTextField();
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(!(txtEmail.getText().compareTo("-")==0)){
				if (isValidEmailAddress(txtEmail.getText())==false){
					lblMensaje
					.setText("Ingrese formato de Email correcto.");
			Timer t = new Timer(Login.timer,
					new ActionListener() {

						public void actionPerformed(
								ActionEvent e) {
							lblMensaje.setText(null);
						}
					});
			t.setRepeats(false);
			t.start();
			
			txtEmail.requestFocus();
			txtEmail.selectAll();
				}
				}
			}
		});
		txtEmail.setColumns(10);
		txtEmail.setBounds(705, 157, 158, 26);
		getContentPane().add(txtEmail);
		
		JLabel lblEmail = new JLabel();
		lblEmail.setText("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(579, 155, 116, 25);
		getContentPane().add(lblEmail);
		
	

		table.removeColumn(table.getColumnModel().getColumn(0));
		recuperarDatos();

	}

	private void limpiar() {
		codTemporal = "";
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonGuardar) {
			try {
				Item item = (Item) cmbGenero.getSelectedItem();
				Integer generoSelected = item.getId();

				Item item2 = (Item) cmbPaisActual.getSelectedItem();
				Integer actualSelected = item2.getId();

				Item item3 = (Item) cmbPaisOrigen.getSelectedItem();
				Integer origenSelected = item3.getId();
				
				Item item4 = (Item) cmbNacionalidad.getSelectedItem();
				Integer nacionalidadSelected = item4.getId();
				
				if (!(txtCI.getText().length() == 0)
						&& !(txtCelular.getText().length() == 0)
						&& !(txtFechaNac.getText().length() == 0)
						&& !(txtLineaBaja.getText().length() == 0)
						&& !(txtNombres.getText().length() == 0)
						&& !(txtApellidos.getText().length() == 0)) {
					if (txtCI.getText().length() > 7) {
						lblMensaje
								.setText("El codigo debe ser de maximo 7 caracteres.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					} else if

					(personaValidator.ValidarCodigo(txtCI.getText()) == false) {
					//	if (personaValidator.ValidarCodigo(txtCI.getText()) == false) {
							// Genero genero = new Genero();
							// genero.setDescripcion(textGenero.getText());

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
							query.setQueryGenerico("INSERT INTO ucsaws_persona"
									+ "( id_persona,email,id_evento, id_nacionalidad, nombre, apellido, fecha_nacimiento, id_pais_origen,id_pais_actual,id_genero,ci, tel_linea_baja,tel_celular,"
									+ " usuario_ins,fch_ins, usuario_upd, fch_upd) "
									+ "VALUES ("
									+ "nextval('ucsaws_persona_seq'),lower ('" + txtEmail.getText() + "'),"  + VentanaBuscarEvento.evento + "," + nacionalidadSelected +  " , "
									+ " upper('"
									+ txtNombres.getText()
									+ "'), "
									+ " upper('"
									+ txtApellidos.getText()
									+ "'), "
									+ " to_date('"
									+ txtFechaNac.getText()
									+ "', 'DD/MM/YYYY'), "

									+ origenSelected
									+ " , "
									+ actualSelected
									+ " , "
									+ generoSelected
									+ ",'"
									+ txtCI.getText()
									+ "', "
									+ "'"
									+ txtLineaBaja.getText()
									+ "',' "
									+ txtCelular.getText()
									+ "', '"
									+ Login.userLogeado
									+ "' , now(), '"
									+ Login.userLogeado + "' , now())");

							QueryGenericoResponse response = weatherClient
									.getQueryGenericoResponse(query);
							weatherClient.printQueryGenericoResponse(response);

							model = new PersonaJTableModel();
							recuperarDatos();
							table.setModel(dm);
							model.fireTableDataChanged();
							table.removeColumn(table.getColumnModel()
									.getColumn(0));
							// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
							lblMensaje
									.setText("Excelente, se ha guardado la Persona.");
							Timer t = new Timer(Login.timer,
									new ActionListener() {

										public void actionPerformed(
												ActionEvent e) {
											lblMensaje.setText(null);
										}
									});
							t.setRepeats(false);
							t.start();

							txtCI.setText("");
							txtNombres.setText("");
							txtApellidos.setText("");
							txtCelular.setText("");
							txtLineaBaja.setText("");
							DateFormat dateFormat;
							Date date;
							dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							date = new Date();
							   System.out.println(dateFormat.format(date));
							   txtFechaNac.setText(dateFormat.format(date));
							   
							   
							   cmbGenero.setSelectedIndex(-1);
							   cmbPaisActual.setSelectedIndex(-1);
							   cmbPaisOrigen.setSelectedIndex(-1);
							   cmbNacionalidad.setSelectedIndex(-1);
							   
							
							// this.dispose();
						 
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje
								.setText("Ya existe una Persona con esta cedula Nro. : "
										+ txtCI.getText());
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
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
			VentanaBuscarPersona persona = new VentanaBuscarPersona();
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

		query.setQueryGenerico("select ci,id_persona, per.nombre, per.apellido,  to_char(fecha_nacimiento, 'DD/MM/YYYY'), ori.nombre as PaisOrigen, act.nombre as PaisActual, gen.descripcion,  tel_linea_baja, tel_celular, n.desc_nacionalidad , email"

				+ " from ucsaws_persona per join ucsaws_pais ori on (per.id_pais_origen = ori.id_pais) join ucsaws_pais act on (per.id_pais_actual = act.id_pais) "
				+ "join ucsaws_genero gen on (per.id_genero = gen.id_genero) join ucsaws_nacionalidad n on (n.id_nacionalidad = per.id_nacionalidad)"
				+ "where per.id_evento = " + VentanaBuscarEvento.evento
				+ " order by per.apellido , per.nombre");

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
					,fil.get(5).toString(),fil.get(6).toString(),fil.get(7).toString()
					,fil.get(8).toString(),fil.get(9).toString(),fil.get(10).toString(),fil.get(11).toString()};

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
		
		String[] colNames = new String[] {"ID","Item", "CI.", "Nombre", "Apellido","Fch. Nac.", "Pais Origen", "Pais Actual","Genero","Linea Baja","Celular","Nacionalidad","E-mail"};
		
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = colNames.length;
	    for (int column = 0; column < columnCount; column++) {
	        columnNames.add(colNames[column]);
	    }
	    
	    dm = new DefaultTableModel(data, columnNames);

	}

	private Vector recuperarDatosComboBoxPaisOrigen() {
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

		query.setQueryGenerico("SELECT id_pais, nombre" + " from ucsaws_pais where id_evento =  " + VentanaBuscarEvento.evento
				+ " order by nombre");

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

			ciudades.add(fin);
			model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
			ite++;
		}
		return model;

	}

	private Vector recuperarDatosComboBoxPaisActual() {
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

		query.setQueryGenerico("SELECT id_pais, nombre" + " from ucsaws_pais where id_evento= " + VentanaBuscarEvento.evento
				+ " order by nombre");

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

			tcandidato.add(fin);
			model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
			ite++;
		}
		return model;

	}

	private Vector recuperarDatosComboBoxGenero() {
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

		query.setQueryGenerico("SELECT id_genero, descripcion"
				+ " from ucsaws_genero where id_evento = " + VentanaBuscarEvento.evento + "order by codigo");

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
	
	private Vector recuperarDatosComboBoxNacionalidad() {
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

		query.setQueryGenerico("SELECT id_nacionalidad, desc_nacionalidad"
				+ " from ucsaws_nacionalidad where id_evento =  "+ VentanaBuscarEvento.evento + " order by cod_nacionalidad");

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
	
	public void filter(String query){
		
		
		
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
		
		
		
		table.setRowSorter(tr);
		
	tr.setRowFilter(RowFilter.regexFilter(query));
		
		
	}
	
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
}