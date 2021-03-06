package src.main.java.admin.evento;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.EdgedBalloonStyle;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jdesktop.swingx.error.ErrorLevel;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.persona.Item;
import src.main.java.admin.utils.ArmarFecha;
import src.main.java.admin.utils.Close;
import src.main.java.admin.utils.Time24hFormatValidator;
import src.main.java.admin.validator.EventoValidator;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.tipoEvento.TipoEventoDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import entity.Generic;
import entity.UcsawsEvento;
import entity.UcsawsTipoEvento;


public class VentanaRegistroEvento extends JFrame implements ActionListener {
    
    MaskFormatter mf1;

    JDateChooser dateChooserDesde = new JDateChooser("dd/MM/yyyy","##/##/####", '_');
    JDateChooser dateChooserHasta = new JDateChooser("dd/MM/yyyy","##/##/####", '_');
    

    private EventoJTableModel model = new EventoJTableModel();

    ArmarFecha armarFecha = new ArmarFecha();

    private Coordinador miCoordinador; // objeto miCoordinador que permite la
				       // relacion entre esta clase y la clase
				       // coordinador
    private JLabel labelTitulo, lblMensaje;
    private JButton botonGuardar, botonCancelar, btnFecha;
    private VentanaRegistroEvento ventanaRegistroPersona;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();

    private EventoValidator eventoValidator = new EventoValidator();

    private String codTemporal = "";
    private JButton btnHome;

    List<Object[]> eventos = new ArrayList<Object[]>();

    List<Object[]> listas = new ArrayList<Object[]>();

    List<Object[]> tcandidato = new ArrayList<Object[]>();
    private JLabel lblTipoEvento;
    private JComboBox cmbTipoEvento;
    private JLabel lblNro;
    private JTextField txtNro;
    private JLabel lblDescripcion;
    private JTextField txtDescripcion;
    private JLabel lblFechaDesde;

    private DefaultTableModel dm;

    private EventoDAO eventoDAO = new EventoDAO();
    
    JFormattedTextField horaMinutoDesde;
    private JFormattedTextField horaMinutoHasta;
  
    BalloonTip tooltipBalloon ;
    BalloonTipStyle edgedLook = new EdgedBalloonStyle(Color.WHITE, Color.BLUE);
    /**
     * constructor de la clase donde se inicializan todos los componentes de la
     * ventana de registro
     */
    public VentanaRegistroEvento() {

	addWindowListener(new WindowAdapter() {
	    public void windowOpened(WindowEvent e) {
		txtNro.requestFocus();
	    }
	});

    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent we)
        { 
            Close close = new Close();
            close.cerrarAplicacion(we);
        }
    });
	 
	JTextFieldDateEditor editord = (JTextFieldDateEditor) dateChooserDesde.getDateEditor();
	    editord.setEditable(false);
	    
	    JTextFieldDateEditor editorh = (JTextFieldDateEditor) dateChooserHasta.getDateEditor();
	    editorh.setEditable(false);    
	    
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
	botonCancelar.setBounds(539, 259, 32, 32);
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
	setSize(578, 319);
	setTitle("Sistema E-vote: Paraguay Elecciones 2015");
	setLocationRelativeTo(null);
	setResizable(false);
	getContentPane().setLayout(null);
	
	getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"clickButton");

	getRootPane().getActionMap().put("clickButton",new AbstractAction(){
		        public void actionPerformed(ActionEvent ae)
		        {
		    botonGuardar.doClick();
		    System.out.println("button clicked");
		        }
		    });
	
	
	getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"clickButtonescape");

	getRootPane().getActionMap().put("clickButtonescape",new AbstractAction(){
		        public void actionPerformed(ActionEvent ae)
		        {
		    botonCancelar.doClick();
		    System.out.println("button esc clicked");
		        }
		    });
	//recuperarDatos();

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
	AutoCompleteDecorator.decorate(cmbTipoEvento);

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
	Image img5 = ((ImageIcon) botonGuardar.getIcon()).getImage();
	Image newimg5 = img5.getScaledInstance(32, 32,
		java.awt.Image.SCALE_SMOOTH);

	JLabel lblFchHasta = new JLabel();
	lblFchHasta.setText("Fch. Hasta:");
	lblFchHasta.setHorizontalAlignment(SwingConstants.RIGHT);
	lblFchHasta.setBounds(75, 170, 116, 25);
	getContentPane().add(lblFchHasta);
	// recuperarDatos();

	cmbTipoEvento.setSelectedIndex(-1);

	dateChooserDesde.setBounds(213, 139, 97, 20);
	getContentPane().add(dateChooserDesde);

	
	dateChooserHasta.setBounds(213, 170, 97, 20);
	getContentPane().add(dateChooserHasta);
	
	try{
	mf1 = new MaskFormatter("##:##");
	mf1.setPlaceholderCharacter('_');
	}
	catch(Exception e){
	    System.out.println(e);
	}
	horaMinutoDesde = new JFormattedTextField(mf1);
	horaMinutoDesde.addFocusListener(new FocusAdapter() {
		@Override
		public void focusLost(FocusEvent arg0) {
			if (horaMinutoDesde.getText().length()>0 && horaMinutoDesde.getText().compareTo("__:__")!=0){
		    		if (validarHora(horaMinutoDesde.getText())==true){
		    		/*txtHora.requestFocus();
		    		txtHora.selectAll();
	    			txtHora.revalidate();
	    			txtHora.repaint();*/
		    			//tooltipBalloon.setVisible(false);
		    			//tooltipBalloon.setVisible(false);
		    			if(tooltipBalloon != null){
		    			tooltipBalloon.setVisible(false);}
		    			//txtHora.setToolTipText("");
		    			//ToolTipUtils.balloonToToolTip(new BalloonTip(txtHora," "), 1, 1);
		    			//System.out.println(tooltipBalloon.getToolTipText());
		    			horaMinutoDesde.setToolTipText("");
		    		}
		    		else
		    		{
		    			
		    			/*tooltipBalloon = new BalloonTip(horaMinutoDesde, new JLabel("Ingrese hora y minuto válido!"), edgedLook, Orientation.RIGHT_ABOVE, AttachLocation.CENTER, 40, 20, false);
		    			// Now convert this balloon tip to a tooltip, such that the tooltip shows up after 500 milliseconds and stays visible for 3000 milliseconds
		    			ToolTipUtils.balloonToToolTip(tooltipBalloon, 1000, 1);
		    			getContentPane().add(tooltipBalloon);
		    			tooltipBalloon.setVisible(true);
		    			//txtHora.setToolTipText("Ingrese hora y minuto válido!");*/
		    			
		    			//tooltipBalloon.
		    		 JOptionPane.showMessageDialog(new JFrame(), "Es necesario que ingrese hora/minuto correcto", "Dialog",
		    		        JOptionPane.ERROR_MESSAGE);
		    			horaMinutoDesde.selectAll();
		    			
		    			//txtHora.repaint();
		    			horaMinutoDesde.requestFocus();
		    			horaMinutoDesde.validate();
		    		}
		    		}
		}
	});
	horaMinutoDesde.setToolTipText("hh:mm");
	horaMinutoDesde.setBounds(320, 139, 33, 20);
	getContentPane().add(horaMinutoDesde);
	
	horaMinutoHasta = new JFormattedTextField(mf1);
	horaMinutoHasta.addFocusListener(new FocusAdapter() {
		@Override
		public void focusLost(FocusEvent arg0) {
		    
			if (horaMinutoHasta.getText().length()>0 && horaMinutoHasta.getText().compareTo("__:__")!=0){
		    		if (validarHora(horaMinutoHasta.getText())==true){
		    		/*txtHora.requestFocus();
		    		txtHora.selectAll();
	    			txtHora.revalidate();
	    			txtHora.repaint();*/
		    			//tooltipBalloon.setVisible(false);
		    			//tooltipBalloon.setVisible(false);
		    			if(tooltipBalloon != null){
		    			tooltipBalloon.setVisible(false);}
		    			//txtHora.setToolTipText("");
		    			//ToolTipUtils.balloonToToolTip(new BalloonTip(txtHora," "), 1, 1);
		    			//System.out.println(tooltipBalloon.getToolTipText());
		    			horaMinutoHasta.setToolTipText("");
		    		}
		    		else
		    		{
		    			
		    			/*tooltipBalloon = new BalloonTip(horaMinutoDesde, new JLabel("Ingrese hora y minuto válido!"), edgedLook, Orientation.RIGHT_ABOVE, AttachLocation.CENTER, 40, 20, false);
		    			// Now convert this balloon tip to a tooltip, such that the tooltip shows up after 500 milliseconds and stays visible for 3000 milliseconds
		    			ToolTipUtils.balloonToToolTip(tooltipBalloon, 1000, 1);
		    			getContentPane().add(tooltipBalloon);
		    			tooltipBalloon.setVisible(true);
		    			//txtHora.setToolTipText("Ingrese hora y minuto válido!");*/
		    			
		    			//tooltipBalloon.
		    		 JOptionPane.showMessageDialog(new JFrame(), "Es necesario que ingrese hora/minuto correcto", "Dialog",
		    		        JOptionPane.ERROR_MESSAGE);
		    		horaMinutoHasta.selectAll();
		    			
		    			//txtHora.repaint();
		    			horaMinutoHasta.requestFocus();
		    			horaMinutoHasta.validate();
		    		}
		    		}
		}
	});
	horaMinutoHasta.setToolTipText("hh:mm");
	horaMinutoHasta.setBounds(320, 170, 33, 20);
	getContentPane().add(horaMinutoHasta);
	
	

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

		if (cmbTipoEvento.getSelectedIndex() != -1) {
		    item = (Item) cmbTipoEvento.getSelectedItem();
		    tipoEventoSelected = item.getId();
		}

		if (!(txtNro.getText().length() == 0)

		&& !(horaMinutoDesde.getText().length() == 0)

		&& !(horaMinutoDesde.getText().length() == 0)

		&& !(horaMinutoHasta.getText().length() == 0)

		&& !(horaMinutoHasta.getText().length() == 0)

		&& !(dateChooserDesde.getDate() == null)

		&& (cmbTipoEvento.getSelectedIndex() != -1)

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

		   /* if (codTemporal != "") {

			eventoDAO.actualizarEvento(
				e);

			recuperarDatos();
			table.setModel(model);
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

			// txt
			// .setText("");
			codTemporal = "";
			txtNro.setText("");
			txtDescripcion.setText("");
			dateChooserDesde.setDate(new Date());
			dateChooserHasta.setDate(new Date());
			horaMinutoDesde.setText("");
			horaMinutoHasta.setText("");
			cmbTipoEvento.setSelectedIndex(-1);

		    }*/

		    if (codTemporal == "") {
			// no deben haber dos eventos del mismo tipo dentro del
			// mismo rango
			Generic g = new Generic();
			g.setDesde(armarFecha.juntarFecha(
				dateChooserDesde.getDate(),horaMinutoDesde.getText()));
			g.setHasta(armarFecha.juntarFecha(
				dateChooserHasta.getDate(),horaMinutoHasta.getText()));
			g.setId(tipoEventoSelected);
			if (EventoValidator.eventoVigente(g) == false) {
			    if

			    (eventoValidator.ValidarCodigo(txtNro.getText()) == false) {
				// if
				// (personaValidator.ValidarCodigo(txtCI.getText())
				// == false) {
				// Genero genero = new Genero();
				// genero.setDescripcion(textGenero.getText());

				//String horaDesde = armarHoraMinuto(
				//	txtHoraDesde.getText(),
				//	txtMinDesde.getText());
				// String fechaDesdeFinal =
				// txtFchDesde.getText()
				// + " " + horaDesde;

				//String horaHasta = armarHoraMinuto(
				//	txtHoraHasta.getText(),
				//	txtMinHasta.getText());
				//String fechaHastaFinal = txtFchHasta.getText()
				//	+ " " + horaHasta;

				Calendar calendar = new GregorianCalendar();
				int year = calendar.get(Calendar.YEAR);

				ApplicationContext ctx = SpringApplication
					.run(WeatherConfiguration.class);

				WeatherClient weatherClient = ctx
					.getBean(WeatherClient.class);
				QueryGenericoRequest query = new QueryGenericoRequest();

				query.setTipoQueryGenerico(2);
				System.out.println(Login.userLogeado);

				ArmarFecha armarFecha = new ArmarFecha();

				UcsawsEvento eventoParaGuardar = new UcsawsEvento();
				eventoParaGuardar.setDescripcion(txtDescripcion
					.getText().toUpperCase());
				eventoParaGuardar.setFchDesde(g.getDesde());
				eventoParaGuardar.setFchHasta(g.getHasta());
				eventoParaGuardar
					.setNroEvento(txtNro.getText());

				TipoEventoDAO tipoEventoDAO = new TipoEventoDAO();

				eventoParaGuardar
					.setUcsawsTipoEvento(tipoEventoDAO
						.buscarTipoEventoById(tipoEventoSelected));

				// parseo json
				ObjectMapper mapperObj = new ObjectMapper();
				String jsonStr = "";
				try {
				    // get Employee object as a json string
				    jsonStr = mapperObj
					    .writeValueAsString(eventoParaGuardar);
				    System.out.println(jsonStr);
				} catch (IOException ex) {
				    // TODO Auto-generated catch block
				    ex.printStackTrace();
				}

				query.setQueryGenerico(jsonStr);

				QueryGenericoResponse response = weatherClient
					.getQueryGenericoResponse(query);
				weatherClient
					.printQueryGenericoResponse(response);


				if (response.getQueryGenericoResponse().compareTo("NO") ==0){
				   
				  showErrorMessage("Error Fatal.", "No se ha podido insertar el registro",  new RuntimeException ( "Ocurrio un error mientras se intentaba insertar." ) );
				}
				else{
				  
				
				lblMensaje
					.setText("Excelente, se ha guardado el Evento.");
				Timer t = new Timer(Login.timer,
					new ActionListener() {

					    public void actionPerformed(
						    ActionEvent e) {
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
				// txtFchDesde.setText(dateFormat.format(date));

				cmbTipoEvento.setSelectedIndex(-1);
				txtNro.setText("");
				txtDescripcion.setText("");
				dateChooserDesde.setDate(new Date());
				dateChooserHasta.setDate(new Date());
				horaMinutoDesde.setText("");
				horaMinutoDesde.setValue(null);
				
				horaMinutoHasta.setText("");
				horaMinutoHasta.setValue(null);
				//txtFiltrar.setText("Escriba para Filtrar");
				//txtFiltrar.setForeground(Color.LIGHT_GRAY);

				VentanaBuscarEvento evento = new VentanaBuscarEvento();
				evento.setVisible(true);
				this.dispose();
				}
			    } else {
				// JOptionPane.showMessageDialog(null,
				// "Ya existe el genero " + txtDesc.getText(),
				// "Información",JOptionPane.WARNING_MESSAGE);
				lblMensaje
					.setText("Ya existe un Evento con este Nro. : "
						+ txtNro.getText());
				Timer t = new Timer(Login.timer,
					new ActionListener() {

					    public void actionPerformed(
						    ActionEvent e) {
						lblMensaje.setText(null);
					    }
					});
				t.setRepeats(false);
				t.start();
			    }
			} else {
			    lblMensaje
				    .setText("Error. Ya existe un evento de ese tipo en ese rango de fecha. ");
			    Timer t = new Timer(Login.timer,
				    new ActionListener() {

					public void actionPerformed(
						ActionEvent e) {
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
		/*JOptionPane.showMessageDialog(null,
			"Error al intentar insertar", "Error",
			JOptionPane.ERROR_MESSAGE);*/
	      showErrorMessage("Error Fatal.", "No se ha podido insertar el registro", ex);
	    }

	}
	if (e.getSource() == botonCancelar) {
	    VentanaBuscarEvento persona = new VentanaBuscarEvento();
	    persona.setVisible(true);
	    this.dispose();

	}
    }

 /*   private void recuperarDatos() {
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
	try {
	    lista = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsEvento>>() {
		    });
	} catch (Exception e) {
	    System.out.println(e);
	}

	if (lista.isEmpty()) {
	    JOptionPane.showMessageDialog(null, "algo salio mal",
		    "Advertencia", JOptionPane.WARNING_MESSAGE);
	    // return lista;
	}

	else {
	    obtenerModeloA(table, lista);

	    // return lista;
	}

    }*/

    private Vector recuperarDatosComboBoxTipoEvento() {
	Vector model = new Vector();

	ApplicationContext ctx = SpringApplication
		.run(WeatherConfiguration.class);

	WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
	QueryGenericoRequest query = new QueryGenericoRequest();

	query.setTipoQueryGenerico(31);

	query.setQueryGenerico("consultarTipoEvento");

	QueryGenericoResponse response = weatherClient
		.getQueryGenericoResponse(query);
	weatherClient.printQueryGenericoResponse(response);

	String res = response.getQueryGenericoResponse();

	// json string to List<String>;
	ObjectMapper mapper = new ObjectMapper();
	String jsonInString = response.getQueryGenericoResponse();
	List<UcsawsTipoEvento> lista = new ArrayList<UcsawsTipoEvento>();
	try {
	    lista = mapper.readValue(jsonInString,
		    new TypeReference<List<UcsawsTipoEvento>>() {
		    });
	} catch (Exception e) {
	    System.out.println(e);
	}

	if (lista.isEmpty()) {
	    JOptionPane.showMessageDialog(null, "algo salio mal",
		    "Advertencia", JOptionPane.WARNING_MESSAGE);
	    // return lista;
	}

	else {

	    // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
	    Iterator<UcsawsTipoEvento> ite = lista.iterator();

	    UcsawsTipoEvento aux;

	    while (ite.hasNext()) {
		aux = ite.next();

		model.addElement(new Item(aux.getIdTipoEvento(), aux
			.getDescripcion()));

	    }
	    // return model;
	}

	return model;

    }

    public String armarHoraMinuto(String hora, String minuto) {

	String result = "";

	result = hora + ":" + minuto;

	return result;

    }

   /* public void filter(String query) {

	TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(
		dm);

	table.setRowSorter(tr);

	tr.setRowFilter(RowFilter.regexFilter(query));

    }*/

    public DefaultTableModel obtenerModelo(JTable tabla,
	    List<UcsawsEvento> evento) {

	DefaultTableModel model = (DefaultTableModel) tabla.getModel();
	Iterator<UcsawsEvento> ite = evento.iterator();

	String header[] = new String[] { "ID", "Item", "Nro.", "Desc. Evento",
		"Inicio", "Fin", "Desc. Tipo Evento" };
	model.setColumnIdentifiers(header);

	UcsawsEvento aux;
	Integer cont = 1;
	while (ite.hasNext()) {
	    aux = ite.next();

	    Object[] row = {
		    aux.getIdEvento(),
		    cont,
		    aux.getNroEvento(),
		    aux.getDescripcion(),
		    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux
			    .getFchDesde())),
		    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux
			    .getFchHasta())),
		    aux.getUcsawsTipoEvento().getDescripcion() };

	    // new
	    // SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};

	    model.addRow(row);
	    cont++;

	}
	return model;
    }

    public AbstractTableModel obtenerModeloA(JTable tabla,
	    List<UcsawsEvento> evento) {

	// AbstractTableModel model = (DefaultTableModel) tabla.getModel();
	Iterator<UcsawsEvento> ite = evento.iterator();

	// String header[] = new String[] { "ID","Item","Nro.",
	// "Desc. Evento","Inicio","Fin","Desc. Tipo Evento" };
	// model.setColumnIdentifiers(header);

	UcsawsEvento aux;
	Integer cont = 1;
	while (ite.hasNext()) {
	    aux = ite.next();

	    Object[] row = {
		    aux.getIdEvento(),
		    cont,
		    aux.getNroEvento(),
		    aux.getDescripcion(),
		    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux
			    .getFchDesde())),
		    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux
			    .getFchHasta())),
		    aux.getUcsawsTipoEvento().getDescripcion() };

	    // new
	    // SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
	    model.eventos.add(row);

	    cont++;

	}

	return model;
    }
    
	private boolean validarHora(String hora){
		
		Time24hFormatValidator t = new Time24hFormatValidator();
		
		if (t.validate(hora)){
		
			 //limpiar();
			
		    	
		    	 //return false;
			//if (txtHora.getText().length()>0 && txtHora.getText().compareTo("__:__")!=0){
		    	
		    	  //txtHora.requestFocus();
		          
		    return true;	
		    	
			//}
		}
		else
			//JOptionPane.showMessageDialog(null, "Por Favor Ingrese una Hora Válida.");
		return false;
		
		
		
	}
	
	public static void showErrorMessage(final String shortMessage, final String detailedMessage, final Throwable exception) {
	    JXErrorPane.setDefaultLocale(Locale.getDefault());
	    final JXErrorPane errorPane = new JXErrorPane();
	    final ErrorInfo info = new ErrorInfo(shortMessage, detailedMessage, null, "error", exception, ErrorLevel.SEVERE,
	        null);
	    errorPane.setErrorInfo(info);
	    final JDialog dialog = JXErrorPane.createDialog(null, errorPane);
	    //centerOnScreen(dialog);
	    dialog.setLocale(Locale.ENGLISH);
	    dialog.setModal(true);
	    dialog.setTitle(shortMessage);
	}
}