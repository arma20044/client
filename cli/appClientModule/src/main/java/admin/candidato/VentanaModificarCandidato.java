package src.main.java.admin.candidato;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Component;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import entity.Candidato;
import src.main.java.admin.Coordinador;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.validator.CandidatoValidator;
import src.main.java.dao.candidato.CandidatoDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaModificarCandidato extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje,lblq;
	private JButton botonGuardar, botonCancelar;
	private JTable table;

	private CandidatoJTableModel model = new CandidatoJTableModel();
	private JScrollPane scrollPane;

	private CandidatoValidator candidatoValidator = new CandidatoValidator();

	private String codTemporal = "";
	private String esPresidenteOVice = "-";
	
	private JButton btnHome;

	List<Object[]> ciudades = new ArrayList<Object[]>();

	List<Object[]> listas = new ArrayList<Object[]>();

	List<Object[]> tcandidato = new ArrayList<Object[]>();
	private JComboBox cmbPersona;
	private JLabel lblLista;
	private JComboBox cmbLista;
	private JLabel lblCod;
	private JTextField txtCod;
	private JTextField txtFiltrar;
	private DefaultTableModel dm;
	
	private JComboBox cmbObs;
	
	private CandidatoDAO candidatoDAO = new CandidatoDAO();
	private JLabel lblObservacin;
	
	private String obs = "";
	
	private Candidato c;
	

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaModificarCandidato(Candidato can) {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtCod.requestFocus();
			}
		});
		
		c = can;

		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Modificar");
		botonGuardar.setIcon(new ImageIcon(VentanaModificarCandidato.class
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
		botonCancelar.setIcon(new ImageIcon(VentanaModificarCandidato.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(774, 383, 32, 32);
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
		

		labelTitulo = new JLabel();
		labelTitulo.setText("MODIFICAR CANDIDATOS");
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
		scrollPane.setBounds(0, 208, 806, 175);
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
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				List<String> selectedData = new ArrayList<String>();
//
//				
//				int selectedRow = table.rowAtPoint(arg0.getPoint());
//					//System.out.println(selectedRow);
//					int col = 0;
//					while (col < table.getColumnCount()+1) {
//						//System.out.println(table_1.getValueAt(selectedRow,
//						//		col));
//						try {
//							int row = table.rowAtPoint(arg0.getPoint());
//							 String table_click0 = table.getModel().getValueAt(table.
//			                          convertRowIndexToModel(row), col).toString();
//			                //System.out.println(table_click0);
//			                
//							selectedData.add(table_click0);
//							//System.out.println(selectedData);
//						
//						} catch (Exception e) {
//							System.out.println(e.getMessage());
//						}
//
//						col++;
//					}
//					// selectedData.ad table_1.getValueAt(selectedRow[i],
//					// selectedColumns[0]);
//					// txtId.setText(selectedData.get(0));
//					 txtCod.setText(selectedData.get(2));
//					// txtDesc.setText(selectedData.get(1));
//					// textFecha.setText(selectedData.get(2));
//					// textUsu.setText(selectedData.get(4));
//					// codTemporal.setText(selectedData.get(1));
//					codTemporal = selectedData.get(0);
//					
//					
//					// actualizar combo persona
//					DefaultComboBoxModel dtm = (DefaultComboBoxModel)  cmbPersona.getModel();
//					
//					//System.out.println(dtm.getSize());
//					int cont=0;
//					Boolean finded=false;
//					int tamanho = dtm.getSize();
//					while(cont < dtm.getSize()){
//						if (dtm.getElementAt(cont).toString().compareTo(selectedData.get(3).toString() + " " +selectedData.get(4).toString())==0)
//						{
//							//Item item = (Item) dtm.getElementAt(cont);
//							//Integer tipoListaSelected = item.getId();
//							cmbPersona.setSelectedIndex(cont);
//							finded=true;
//							break;
//						}
//						
//							cont++;
//						
//						//System.out.println(dtm.getElementAt(0));
//					}
//					if(finded==false){
//						String a = selectedData.get(3).toString() + " " +selectedData.get(4).toString();
//						cmbPersona.addItem(a);
//						cmbPersona.setSelectedIndex(tamanho);
//					}
//					// actualizar combo persona
//					
//					
//					// actualizar combo lista
//					DefaultComboBoxModel dtmlista = (DefaultComboBoxModel)  cmbLista.getModel();
//					//System.out.println(dtm.getSize());
//					int cont2=0;
//					Boolean findedlista=false;
//					int tamanho2 = dtmlista.getSize();
//					while(cont2 < dtmlista.getSize()){
//						if (dtmlista.getElementAt(cont2).toString().compareToIgnoreCase(selectedData.get(5).toString() + " - " +selectedData.get(6).toString())==0)
//						{
//							//Item item = (Item) dtm.getElementAt(cont);
//							//Integer tipoListaSelected = item.getId();
//							cmbLista.setSelectedIndex(cont2);
//							findedlista=true;
//							break;
//						}
//						
//							cont2++;
//						
//						//System.out.println(dtm.getElementAt(0));
//					}
//					if(findedlista==false){
//						cmbLista.addItem(selectedData.get(5).toString() + " - " +selectedData.get(6).toString());
//						cmbLista.setSelectedIndex(tamanho2);
//					}
//					// actualizar combo lista
//
//				
//				//System.out.println("Selected: " + selectedData);
//
//			}
//		});
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
		btnHome.setIcon(new ImageIcon(VentanaModificarCandidato.class
				.getResource("/imgs/home.png")));
		btnHome.setBounds(0, 0, 32, 32);
		Image img = ((ImageIcon) btnHome.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnHome.setIcon(new ImageIcon(newimg));
		getContentPane().add(btnHome);

		cmbPersona = new JComboBox(recuperarDatosComboBoxPersona());
//		cmbPersona.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				if (cmbPersona.getSelectedIndex() != -1){
//					
//					Item item = (Item) cmbPersona.getSelectedItem();
//					String p = item.getDescription();
//					
//					String string = p; 
//					
//					//String string = "004-034556";
//					String[] parts = string.split(" ");
//					String part1 = parts[0]; // 004
//					String part2 = parts[1]; // 034556
//					
//					txtCod.setText(part1.substring(0, 1)+part2.substring(0,2));
//					System.out.println(txtCod.getText());
//				}
//			}
//		});
		cmbPersona.setBounds(213, 90, 501, 20);
		//cmbPersona.setSelectedIndex(-1);
		filtrarComboEventoTipo();
		getContentPane().add(cmbPersona);

		JLabel lblPersona = new JLabel();
		lblPersona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPersona.setText("Persona:");
		lblPersona.setBounds(130, 88, 61, 25);
		getContentPane().add(lblPersona);

		lblLista = new JLabel();
		lblLista.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLista.setText("Lista:");
		lblLista.setBounds(130, 121, 61, 25);
		getContentPane().add(lblLista);
		
		lblObservacin = new JLabel();
		lblObservacin.setText("Observación:");
		lblObservacin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblObservacin.setBounds(103, 148, 88, 25);
		getContentPane().add(lblObservacin);
		
		cmbObs = new JComboBox();
		cmbObs.setBounds(213, 150, 501, 20);
		cmbObs.addItem("PRE");
		cmbObs.addItem("VICE");
		cmbObs.setSelectedIndex(-1);
		
		getContentPane().add(cmbObs);
		
		lblObservacin.setVisible(false);
		cmbObs.setVisible(false);

		cmbLista = new JComboBox(recuperarDatosComboBoxLista());
		cmbLista.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(cmbLista.getSelectedIndex()!= -1){
		    	//Item item = (Item) cmbLista.getSelectedItem();
			//	String tipoListaSelected = item.getDescription();
				//"Hello".toLowerCase().contains("He".toLowercase());
				if (cmbLista.getModel().getSelectedItem().toString().contains("PRESIDENTE - VICEPRESIDENTE".toUpperCase())){
					
					lblObservacin.setVisible(true);
					cmbObs.setSelectedIndex(-1);
					filtrarComboObs();
					cmbObs.setVisible(true);
					
					
				
				}
				else{
					esPresidenteOVice="-";
					lblObservacin.setVisible(false);
					cmbObs.setSelectedIndex(-1);
					cmbObs.setVisible(false);
					
				}
				}
		    }
		});
		
		cmbLista.setBounds(213, 123, 501, 20);
		//cmbLista.setSelectedIndex(-1);
		filtrarComboLista();
		getContentPane().add(cmbLista);

		lblCod = new JLabel();
		lblCod.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCod.setText("Codigo:");
		lblCod.setBounds(130, 52, 61, 25);
		getContentPane().add(lblCod);

		txtCod = new JTextField();
		txtCod.setBounds(213, 54, 108, 25);
		txtCod.setText(can.getCodigo());
		getContentPane().add(txtCod);
		txtCod.setColumns(10);
		txtCod.setEnabled(false);

		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setBounds(365, 183, 363, 14);
		getContentPane().add(lblMensaje);
		
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
		txtFiltrar.setText("");
		txtFiltrar.setEditable(true);
		txtFiltrar.setBounds(269, 390, 319, 25);
		txtFiltrar.setForeground(Color.LIGHT_GRAY);
		txtFiltrar.setText("Escriba para filtrar...");
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
				
				if (!(txtCod.getText().length() == 0)) {
//					if (!(txtCod.getText().length() == 3)) {
//						lblMensaje.setText("El codigo debe ser de 3 caracteres.");
//						Timer t = new Timer(Login.timer, new ActionListener() {
//
//							public void actionPerformed(ActionEvent e) {
//								lblMensaje.setText(null);
//							}
//						});
//						t.setRepeats(false);
//						t.start();
//					} 
					//else 
						//if

					//(candidatoValidator.ValidarCodigo(txtCod.getText()) == false) {
						Item item = (Item) cmbLista.getSelectedItem();
						Integer listaSelected = item.getId();

						Item item3 = (Item) cmbPersona.getSelectedItem();
						Integer personaSelected = item3.getId();
						
						if (codTemporal==""){
						//if (candidatoValidator.ValidarPersona(personaSelected) == false) {
							// Genero genero = new Genero();
							// genero.setDescripcion(textGenero.getText());
							
							

							Calendar calendar = new GregorianCalendar();
							int year = calendar.get(Calendar.YEAR);

							ApplicationContext ctx = SpringApplication
									.run(WeatherConfiguration.class);

							WeatherClient weatherClient = ctx
									.getBean(WeatherClient.class);
							QueryGenericoRequest query = new QueryGenericoRequest();
							
							if(cmbObs.getSelectedIndex() == -1){
								obs = "-";
							}
							else
							{
								obs = cmbObs.getSelectedItem().toString();
							}

							// para UPDATE se inserta el codigo es 3
							query.setTipoQueryGenerico(1);
							System.out.println(Login.userLogeado);
							query.setQueryGenerico("UPDATE ucsaws_candidatos"
									+ " set id_persona = " + personaSelected + ","
									+ " descripcion = '" + obs + "',"
									+ " id_lista = " + listaSelected + ","
									+ " usuario_upd = " + Login.userLogeado + ","
									+ " fch_upd = now() "
									+ " WHERE ID_CANDIDATOS = " + c.getId_candidato());

							QueryGenericoResponse response = weatherClient
									.getQueryGenericoResponse(query);
							weatherClient.printQueryGenericoResponse(response);

							model = new CandidatoJTableModel();
							recuperarDatos();
							table.setModel(dm);
							model.fireTableDataChanged();
							table.removeColumn(table.getColumnModel()
									.getColumn(0));
							
							
							// JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
							lblMensaje
									.setText("Excelente, se ha Modificado el Candidato.");
							Timer t = new Timer(Login.timer,
									new ActionListener() {

										public void actionPerformed(
												ActionEvent e) {
											lblMensaje.setText(null);
										}
									});
							t.setRepeats(false);
							t.start();

							txtCod.setText("");
						
							
						VentanaBuscarCandidato can = new VentanaBuscarCandidato();
						can.setVisible(true);
						dispose();
//							}
//							
//							else 
//								// JOptionPane.showMessageDialog(null,
//								// "Ya existe el genero " + txtDesc.getText(),
//								// "Información",JOptionPane.WARNING_MESSAGE);
//								lblMensaje
//										.setText("La Persona no puede tener mas de una candidatura");
//								Timer t = new Timer(Login.timer,
//										new ActionListener() {
//
//											public void actionPerformed(
//													ActionEvent e) {
//												lblMensaje.setText(null);
//											}
//										});
//								t.setRepeats(false);
//								t.start();

							// this.dispose();
						} else {
							if(codTemporal!= "")
							{
								
								
								candidatoDAO.actualizarCandidato(txtCod.getText(),personaSelected.toString(), listaSelected.toString(), codTemporal);
								
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

							txtCod.setText("");
							
							}
						}
//					} else {
//						// JOptionPane.showMessageDialog(null,
//						// "Ya existe el genero " + txtDesc.getText(),
//						// "Información",JOptionPane.WARNING_MESSAGE);
//						lblMensaje
//								.setText("Ya existe el candidato con el codigo "
//										+ txtCod.getText());
//						Timer t = new Timer(Login.timer, new ActionListener() {
//
//							public void actionPerformed(ActionEvent e) {
//								lblMensaje.setText(null);
//							}
//						});
//						t.setRepeats(false);
//						t.start();
//					}

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
						"Error al intentar insertar : "+ ex, "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		if (e.getSource() == botonCancelar) {
			VentanaBuscarCandidato candidato = new VentanaBuscarCandidato();
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

		query.setQueryGenerico("SELECT ca.id_candidatos, ca.codigo, nombre, apellido , li.nro_lista || ' - ' ||  li.nombre_lista "
				+ " , tl.descripcion, ca.descripcion obs" +
								" from ucsaws_candidatos  ca join ucsaws_persona per on (ca.id_persona = per.id_persona) " +  
								" join ucsaws_listas li on (ca.id_lista = li.id_lista)"
								+ " join ucsaws_tipo_lista tl on (li.id_tipo_lista = tl.id_tipo_lista)"
								+ " where ca.id_evento = " + VentanaBuscarEvento.evento );

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
					,fil.get(5).toString(),fil.get(6).toString()};

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
		
		String[] colNames = new String[] {"ID", "Item",  "codigo", "nombre","apellido", "Lista", "Tipo", "Observacion"};
		
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = colNames.length;
	    for (int column = 0; column < columnCount; column++) {
	        columnNames.add(colNames[column]);
	    }
	    
	    dm = new DefaultTableModel(data, columnNames);

	}

	private Vector recuperarDatosComboBoxPersona() {
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

		query.setQueryGenerico("SELECT id_persona, nombre || ' ' || apellido as nomap  from ucsaws_persona "
								+ "where  "
								+ "   id_evento = " + VentanaBuscarEvento.evento + " order by apellido");

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

	private Vector recuperarDatosComboBoxTipoCandidato() {
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

		query.setQueryGenerico("SELECT id_tipo_candidato, descripcion"
				+ " from ucsaws_tipo_candidato " + "order by descripcion");

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

	private Vector recuperarDatosComboBoxLista() {
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

		query.setQueryGenerico("SELECT id_lista, nro_lista || ' - ' || nombre_lista || ' - ' || tlis.descripcion " +
								" from ucsaws_listas lis join ucsaws_tipo_lista tlis on (lis.id_tipo_lista = tlis.id_tipo_lista) "+
								 " where lis.id_evento = " + VentanaBuscarEvento.evento +
								" order by nro_lista");

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
	
//	public void borrarElementosComboPersona(){
//		cmbPersona.removeAllItems();
//	}
//	
//	public void refrescarComboPersona(){
//		
//		
//		
//		
//		cmbPersona = new JComboBox(recuperarDatosComboBoxPersona());
//		
//		//cmbPersona.revalidate();
//		
//		this.getContentPane().validate();
//		this.getContentPane().repaint();
//		
//		
//		
//	}
	private String patron(String s){
		if (s.matches("\\d{4}/[a-zA-Z]{3}")) {
			System.out.println("Found good SSN: " + s);
		}
		return "El formato es ####/abc - 4 Digitos / 3 Letras.";
	}
	
	//filtrar persona	
	public void filtrarComboEventoTipo(){
	DefaultComboBoxModel dtm = (DefaultComboBoxModel)  cmbPersona.getModel();
		
		//System.out.println(dtm.getSize());
		int cont=0;
		Boolean finded=false;
		int tamanho = dtm.getSize();
		while(cont < dtm.getSize()){
			if (dtm.getElementAt(cont).toString().compareTo(c.getId_persona().toString())==0)
			{
				//Item item = (Item) dtm.getElementAt(cont);
				//Integer tipoListaSelected = item.getId();
				cmbPersona. setSelectedIndex(cont);
				finded=true;
				break;
			}
			
				cont++;
			
			//System.out.println(dtm.getElementAt(0));
		}
		if(finded==false){
			String a = c.getId_persona().toString();
			cmbPersona.addItem(a);
			cmbPersona.setSelectedIndex(tamanho);
		}}
		//filtrar persona
	
	//filtrar lista	
		public void filtrarComboLista(){
		DefaultComboBoxModel dtm = (DefaultComboBoxModel)  cmbLista.getModel();
			
			//System.out.println(dtm.getSize());
			int cont=0;
			Boolean finded=false;
			int tamanho = dtm.getSize();
			while(cont < dtm.getSize()){
				if (dtm.getElementAt(cont).toString().compareTo(c.getId_lista().toString())==0)
				{
					//Item item = (Item) dtm.getElementAt(cont);
					//Integer tipoListaSelected = item.getId();
					cmbLista. setSelectedIndex(cont);
					finded=true;
					break;
				}
				
					cont++;
				
				//System.out.println(dtm.getElementAt(0));
			}
			if(finded==false){
				String a = c.getId_lista().toString();
				cmbLista.addItem(a);
				cmbLista.setSelectedIndex(tamanho);
			}}
			//filtrar lista
		
		//filtrar obs	
				public void filtrarComboObs(){
				DefaultComboBoxModel dtm = (DefaultComboBoxModel)  cmbObs.getModel();
					
					//System.out.println(dtm.getSize());
					int cont=0;
					Boolean finded=false;
					int tamanho = dtm.getSize();
					while(cont < dtm.getSize()){
						if (dtm.getElementAt(cont).toString().compareTo(c.getDescripcion() .toString())==0)
						{
							//Item item = (Item) dtm.getElementAt(cont);
							//Integer tipoListaSelected = item.getId();
							cmbObs. setSelectedIndex(cont);
							finded=true;
							break;
						}
						
							cont++;
						
						//System.out.println(dtm.getElementAt(0));
					}
					if(finded==false){
						String a = c.getDescripcion().toString();
						cmbObs.addItem(a);
						cmbObs.setSelectedIndex(tamanho);
					}}
					//filtrar obs
}