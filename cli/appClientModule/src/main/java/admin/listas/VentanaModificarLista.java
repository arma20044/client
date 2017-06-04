package src.main.java.admin.listas;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
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

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.validator.ListasValidator;
import src.main.java.dao.listas.ListasDAO;
import src.main.java.dao.tipoLista.TipoListaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import entity.Listas;
import entity.UcsawsListas;
import entity.UcsawsPais;
import entity.UcsawsTipoLista;

public class VentanaModificarLista extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, lblMensaje;
	private JButton botonGuardar, botonCancelar;

	private ListasJTableModel model = new ListasJTableModel();

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
	private DefaultTableModel dm;
	private ListasDAO listaDAO = new ListasDAO();
	
	private Listas lista;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaModificarLista(Listas l) {
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				txtNro.requestFocus();
			}
		});
		
		lista = l;
		
		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar Cambios");
		botonGuardar.setIcon(new ImageIcon(VentanaModificarLista.class
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
		botonCancelar.setIcon(new ImageIcon(VentanaModificarLista.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setBounds(676, 198, 32, 32);
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
		labelTitulo.setText("MODIFICAR LISTAS");
		labelTitulo.setBounds(269, 11, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(719, 259);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
	//recuperarDatos();
		//table.setModel(dm);

		btnHome = new JButton("");
		btnHome.setToolTipText("Inicio");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefinicionesGenerales menuprincipal = new DefinicionesGenerales();
				menuprincipal.setVisible(true);
				dispose();
			}
		});
		btnHome.setIcon(new ImageIcon(VentanaModificarLista.class
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
		txtNro.setText(l.getNro_lista());
		txtNro.setEditable(false);
		txtNro.setEnabled(false);
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
		txtNombre.setText(l.getNombre_lista());
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
		txtAnho.setText(l.getAnho_lista());
		getContentPane().add(txtAnho);

		JLabel lblTipoLista = new JLabel();
		lblTipoLista.setText("Tipo Lista:");
		lblTipoLista.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoLista.setBounds(130, 144, 61, 25);
		getContentPane().add(lblTipoLista);

		cmbTipoLista = new JComboBox(recuperarDatosComboBoxTipoLista());
		cmbTipoLista.setSelectedIndex(-1);
		filtrarComboEventoTipo();
		cmbTipoLista.setBounds(213, 146, 340, 20);
		getContentPane().add(cmbTipoLista);
		//recuperarDatos();

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

					(listasValidator.ValidarCodigo(
		                Integer.parseInt(txtNro.getText()),
		                txtNombre.getText(),
		                Integer.parseInt(txtAnho.getText()),
		                tipoListaSelected, VentanaBuscarEvento.evento) == false) {
						// if
						// (candidatoValidator.ValidarPersona(personaSelected)
						// == false) {
						// Genero genero = new Genero();
						// genero.setDescripcion(textGenero.getText());

				
							if(codTemporal== "")
							{
								//listaDAO.actualizarLista(txtNro.getText(), txtNombre.getText(), txtAnho.getText(), tipoListaSelected.toString(), codTemporal);
							    
							    listaDAO.modificarLista(listaDAO.obtenerListaByIdIdLista(Integer.parseInt(codTemporal)));
							//	recuperarDatos();
								//table.setModel(dm);
								model.fireTableDataChanged();
							//	table.removeColumn(table.getColumnModel().getColumn(0));
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



	private Vector recuperarDatosComboBoxTipoLista() {
		Vector model = new Vector();

		TipoListaDAO tipoListaDAO = new TipoListaDAO();
		
		
		List<UcsawsTipoLista> tipoListas = tipoListaDAO.obtenerTipoListaByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

		  if (tipoListas.isEmpty()) {
		       // JOptionPane.showMessageDialog(null, "algo salio mal",
		        //    "Advertencia", JOptionPane.WARNING_MESSAGE);
		        // return lista;
		       // System.out.println("Ya se agregaron todas las nacionalidades");
		    }

		    else {

		        // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		        Iterator<UcsawsTipoLista> ite = tipoListas.iterator();

		        UcsawsTipoLista aux;

		        while (ite.hasNext()) {
		        aux = ite.next();

		        model.addElement(new Item(aux.getIdTipoLista(),aux.getDescripcion()));

		        }
		        // return model;
		    }

		    return model;
	}
	public void filter(String query){
		
		
		
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
		
		
		
		//table.setRowSorter(tr);
		
	tr.setRowFilter(RowFilter.regexFilter(query));
		
		
	}
	
	//filtrar lista	
	public void filtrarComboEventoTipo(){
	DefaultComboBoxModel dtm = (DefaultComboBoxModel)  cmbTipoLista.getModel();
		
		//System.out.println(dtm.getSize());
		int cont=0;
		Boolean finded=false;
		int tamanho = dtm.getSize();
		while(cont < dtm.getSize()){
			if (dtm.getElementAt(cont).toString().compareTo(lista.getId_tipo_lista().toString())==0)
			{
				//Item item = (Item) dtm.getElementAt(cont);
				//Integer tipoListaSelected = item.getId();
				cmbTipoLista. setSelectedIndex(cont);
				finded=true;
				break;
			}
			
				cont++;
			
			//System.out.println(dtm.getElementAt(0));
		}
		if(finded==false){
			String a = lista.getId_tipo_lista().toString();
			cmbTipoLista.addItem(a);
			cmbTipoLista.setSelectedIndex(tamanho);
		}}
		//filtrar lista
}