package src.main.java.admin.listas;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
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
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.validator.ListasValidator;
import src.main.java.dao.listas.ListasDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComboBox;

import java.util.Vector;

public class VentanaRegistroLista extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;
	private JTable table;

	private PaisJTableModel model = new PaisJTableModel();
	private JScrollPane scrollPane;

	private ListasValidator listasValidator = new ListasValidator();

	private String codTemporal = "";
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JLabel lblNro;
	private JTextField txtNro;
	private JTextField txtNombre;
	private JLabel lblAnho;
	private JTextField txtAnho;
	private JComboBox cmbTipoLista;
	private JTextField txtFiltrar;
	private DefaultTableModel dm;
	private ListasDAO listaDAO = new ListasDAO();

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroLista() {

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Registrar");
		botonGuardar.setIcon(new ImageIcon(VentanaRegistroLista.class
				.getResource("/imgs/save.png")));
		botonGuardar.setBounds(298, 48, 32, 32);
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
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroLista.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(774, 383, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		// Image newimg4 = img4.getScaledInstance(32, 32,
		// java.awt.Image.SCALE_SMOOTH);

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE LISTAS");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(812, 444);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("Lista de Candidatos");
		scrollPane.setBounds(0, 200, 806, 183);
		getContentPane().add(scrollPane);

		table = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				Component component = super.prepareRenderer(renderer, row,
						column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(Math.max(rendererWidth
						+ getIntercellSpacing().width,
						tableColumn.getPreferredWidth()));
				return component;
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
					txtNro.setText(selectedData.get(2));
					txtNombre.setText(selectedData.get(3));
					txtAnho.setText(selectedData.get(4));
					// textUsu.setText(selectedData.get(4));
					// codTemporal.setText(selectedData.get(1));
					codTemporal = selectedData.get(0);
					
				//	cmbTipoLista = new JComboBox(recuperarDatosComboBoxTipoLista());
					//cmbTipoLista.removeAllItems();
					
//					//cmbTipoLista = new JComboBox(recuperarDatosComboBoxTipoLista());
//					Item item = (Item) cmbTipoLista.getSelectedItem();
//					Integer tipoListaSelected = item.getId();
					
					//cmbTipoLista.setSelectedItem(3);
					System.out.println(cmbTipoLista.getSelectedItem());
					
				//	Item item = (Item) cmbTipoLista.getSelectedItem();
				//	Integer tipoListaSelected = item.getId();
					
					
					//cmbTipoLista.setSelectedItem(selectedData.get(5));
					//test datamodel to array
					DefaultComboBoxModel dtm = (DefaultComboBoxModel)  cmbTipoLista.getModel();
					System.out.println(dtm.getSize());
					int cont=0;
					while(cont < dtm.getSize()){
						if (dtm.getElementAt(cont).toString().compareToIgnoreCase(selectedData.get(5).toString())==0)
						{
							Item item = (Item) dtm.getElementAt(cont);
							Integer tipoListaSelected = item.getId();
							cmbTipoLista.setSelectedIndex(cont);
							break;
						}
						
							cont++;
						
						System.out.println(dtm.getElementAt(0));
					}
					
					selectedData.get(5);
				   // int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
				   // Object[][] tableData = new Object[nRow][nCol];
				  //  for (int i = 0 ; i < nRow ; i++)
				  //      for (int j = 0 ; j < nCol ; j++)
				          //  tableData[i][j] = dtm.getValueAt(i,j);
					
					
					
					
					
					//test
					//cmbTipoLista.getModel();
					
					
					//cmbTipoLista.setSelectedIndex(2);
					
					
				//	cmbTipoLista.repaint();
				//	//cmbTipoLista.updateUI();
				//	System.out.println(cmbTipoLista.getSelectedItem().toString());
				   
				        
				        
					
				//	cmbTipoLista.setSelectedItem(selectedData.get(2));
					//repaint();

				
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
		btnHome.setIcon(new ImageIcon(VentanaRegistroLista.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		lblNro = new JLabel();
		lblNro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNro.setText("Nro.:");
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

		txtNro.setBounds(213, 54, 75, 26);
		getContentPane().add(txtNro);
		txtNro.setColumns(10);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(213, 175, 454, 14);
		getContentPane().add(lblMensaje);

		JLabel lblNombre = new JLabel();
		lblNombre.setText("Nombre:");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(102, 82, 89, 25);
		getContentPane().add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(213, 85, 310, 26);
		getContentPane().add(txtNombre);

		lblAnho = new JLabel();
		lblAnho.setText("Año:");
		lblAnho.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAnho.setBounds(130, 113, 61, 25);
		getContentPane().add(lblAnho);

		txtAnho = new JTextField();
		txtAnho.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				if ((car < '0' || car > '9'))
					e.consume();
			}
		});
		txtAnho.setColumns(10);
		txtAnho.setBounds(213, 116, 75, 26);
		getContentPane().add(txtAnho);

		JLabel lblTipoLista = new JLabel();
		lblTipoLista.setText("Tipo Lista:");
		lblTipoLista.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoLista.setBounds(130, 144, 61, 25);
		getContentPane().add(lblTipoLista);

		cmbTipoLista = new JComboBox(recuperarDatosComboBoxTipoLista());
		cmbTipoLista.setSelectedIndex(-1);
		cmbTipoLista.setBounds(213, 146, 340, 20);
		getContentPane().add(cmbTipoLista);
		
		txtFiltrar = new JTextField();
		txtFiltrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String query =txtFiltrar.getText().toUpperCase(); 
				filter(query);
			}
		});
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
		txtFiltrar.setForeground(Color.LIGHT_GRAY);
		txtFiltrar.setText("Escriba para filtrar...");
		txtFiltrar.setEditable(true);
		txtFiltrar.setBounds(174, 383, 319, 26);
		getContentPane().add(txtFiltrar);

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
				Item item = (Item) cmbTipoLista.getSelectedItem();
				Integer tipoListaSelected = item.getId();

				if (!(txtNro.getText().length() == 0)
						&& !(txtAnho.getText().length() == 0)
						&& !(txtNombre.getText().length() == 0)
						&& !(txtNombre.getText().length() == 0)) {
					if (txtNro.getText().length() > 3) {
						lblMensaje
								.setText("El codigo debe ser de maximo 3 caracteres.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
					} else if

					(listasValidator.ValidarCodigo(txtNro.getText(),
							tipoListaSelected, txtNombre.getText(),
							txtAnho.getText()) == false) {
						// if
						// (candidatoValidator.ValidarPersona(personaSelected)
						// == false) {
						// Genero genero = new Genero();
						// genero.setDescripcion(textGenero.getText());

						
						if (codTemporal==""){
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
						query.setQueryGenerico("INSERT INTO ucsaws_listas"
								+ "( id_lista, nombre_lista, nro_lista, anho,id_tipo_lista, id_evento  ,usuario_ins,fch_ins, usuario_upd, fch_upd) "
								+ "VALUES (" + "nextval('ucsaws_listas_seq') ,"
								+ " upper('" + txtNombre.getText() + "'), "

								+ " " + txtNro.getText() + ", "
								+ txtAnho.getText() + "  , "
								+ tipoListaSelected + ","
								+ VentanaBuscarEvento.evento + ",'"
								+ Login.userLogeado + "' , now(), '"
								+ Login.userLogeado + "' , now())");

						QueryGenericoResponse response = weatherClient
								.getQueryGenericoResponse(query);
						weatherClient.printQueryGenericoResponse(response);

						model = new PaisJTableModel();
						recuperarDatos();
						table.setModel(dm);
						model.fireTableDataChanged();
						table.removeColumn(table.getColumnModel().getColumn(0));
						// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
						lblMensaje
								.setText("Excelente, se ha guardado la Lista.");
						Timer t = new Timer(Login.timer, new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								lblMensaje.setText(null);
							}
						});
						t.setRepeats(false);
						t.start();
						cmbTipoLista.setSelectedIndex(-1);
						txtNro.setText("");
						txtNombre.setText("");
						txtAnho.setText("");
						codTemporal = "";
						// this.dispose();
						// } else {
						// // JOptionPane.showMessageDialog(null,
						// // "Ya existe el genero " + txtDesc.getText(),
						// // "Información",JOptionPane.WARNING_MESSAGE);
						// lblMensaje
						// .setText("La Persona no puede tener mas de una candidatura");
						// Timer t = new Timer(Login.timer,
						// new ActionListener() {
						//
						// public void actionPerformed(
						// ActionEvent e) {
						// lblMensaje.setText(null);
						// }
						// });
						// t.setRepeats(false);
						// t.start();
						// }
					}
						else 
							if(codTemporal!= "")
							{
								listaDAO.actualizarLista(txtNro.getText(), txtNombre.getText(), txtAnho.getText(), tipoListaSelected.toString(), codTemporal);
								recuperarDatos();
								table.setModel(dm);
								model.fireTableDataChanged();
								table.removeColumn(table.getColumnModel().getColumn(0));
								// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
								lblMensaje
										.setText("Excelente, se ha modificado la Lista.");
								Timer t = new Timer(Login.timer, new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										lblMensaje.setText(null);
									}
								});
								t.setRepeats(false);
								t.start();

								txtNro.setText("");
								txtNombre.setText("");
								txtAnho.setText("");
								cmbTipoLista.setSelectedIndex(-1);
								codTemporal = "";
							}
						else {
						// JOptionPane.showMessageDialog(null,
						// "Ya existe el genero " + txtDesc.getText(),
						// "Información",JOptionPane.WARNING_MESSAGE);
						lblMensaje
								.setText("Ya existe una Lista con esos datos. ");
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
			VentanaBuscarLista candidato = new VentanaBuscarLista();
			candidato.setVisible(true);
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

		query.setQueryGenerico("SELECT  id_lista, nro_lista, nombre_lista, anho, tlis.descripcion "
				+ "from  ucsaws_listas lis join ucsaws_tipo_lista tlis on "
				+ "( lis.id_tipo_lista = tlis.id_tipo_lista) where lis.id_evento = " + VentanaBuscarEvento.evento
				+  " order by tlis.descripcion, nro_lista" + "");



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
					fil.get(2).toString(),fil.get(3).toString(),fil.get(4).toString()};

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
		
		String[] colNames = new String[] {"ID","Item", "Nro", "Nombre","Año","Tipo Lista"};
		
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = colNames.length;
	    for (int column = 0; column < columnCount; column++) {
	        columnNames.add(colNames[column]);
	    }
	    
	    dm = new DefaultTableModel(data, columnNames);

	}

	private Vector recuperarDatosComboBoxTipoLista() {
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

		query.setQueryGenerico("SELECT id_tipo_lista, descripcion"
				+ " from ucsaws_tipo_lista where id_evento = " + VentanaBuscarEvento.evento + " order by descripcion");

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
}