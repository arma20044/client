package src.main.java.admin.acta;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.validator.ActaValidator;
import src.main.java.dao.acta.ActaDAO;
import src.main.java.dao.departamento.DepartamentoDAO;
import src.main.java.dao.distrito.DistritoDAO;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.listas.ListasDAO;
import src.main.java.dao.local.LocalDAO;
import src.main.java.dao.mesa.MesaDAO;
import src.main.java.dao.persona.PersonaDAO;
import src.main.java.dao.tipoActa.TipoActaDAO;
import src.main.java.dao.zona.ZonaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import com.toedter.calendar.JDateChooser;

import entity.UcsawsActas;
import entity.UcsawsDepartamento;
import entity.UcsawsDistrito;
import entity.UcsawsEvento;
import entity.UcsawsListas;
import entity.UcsawsLocal;
import entity.UcsawsMesa;
import entity.UcsawsPersona;
import entity.UcsawsTipoActa;
import entity.UcsawsZona;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;

public class VentanaRegistroActaFin extends JFrame implements ActionListener {

  private Coordinador miCoordinador; // objeto miCoordinador que permite la
  // relacion entre esta clase y la clase
  // coordinador
  private JLabel labelTitulo, lblMensaje, lblq;
  private JButton botonGuardar, botonCancelar;

  private ActaJTableModel model = new ActaJTableModel();

  private ActaValidator actaValidator = new ActaValidator();

  private String codTemporal = "";
  private String esPresidenteOVice = "-";

  private JButton btnHome;

  List<Object[]> ciudades = new ArrayList<Object[]>();

  List<Object[]> listas = new ArrayList<Object[]>();

  List<Object[]> tcandidato = new ArrayList<Object[]>();
  private JLabel lblCod;
  private JTextField txtCod;
  private DefaultTableModel dm;
  JSpinner spinnerHora = new JSpinner();
  JSpinner spinnerMinuto = new JSpinner();

  private String obs = "";
  private JComboBox cmbTipoActa;
  private JLabel lblDescripcion;

  JLabel lblCantidad = new JLabel();
  JTextArea areaDesc = new JTextArea();
  JTextArea areaObs = new JTextArea();
  private JScrollPane scrollPane;
  private JScrollPane scrollPane_1;
  
  private UcsawsActas actaFin;

  /**
   * constructor de la clase donde se inicializan todos los componentes de la ventana de registro
   */
  public VentanaRegistroActaFin(String idActaFinalizar) {
    
    ActaDAO actaDAO = new ActaDAO();
    
    actaFin = actaDAO.obtenerActaById(Integer.parseInt(idActaFinalizar));
    
    

    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e) {
        txtCod.requestFocus();
      }
    });

    botonGuardar = new JButton();
    botonGuardar.setToolTipText("Guardar");
    botonGuardar.setIcon(new ImageIcon(VentanaRegistroActaFin.class.getResource("/imgs/save.png")));
    botonGuardar.setBounds(339, 52, 32, 32);
    botonGuardar.setOpaque(false);
    botonGuardar.setContentAreaFilled(false);
    botonGuardar.setBorderPainted(false);
    Image img3 = ((ImageIcon) botonGuardar.getIcon()).getImage();
    Image newimg3 = img3.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonGuardar.setIcon(new ImageIcon(newimg3));

    botonCancelar = new JButton();
    botonCancelar.setBackground(Color.WHITE);
    botonCancelar.setToolTipText("Atrás");
    botonCancelar.setIcon(new ImageIcon(VentanaRegistroActaFin.class.getResource("/imgs/back2.png")));
    botonCancelar.setBounds(707, 547, 32, 32);
    botonCancelar.setOpaque(false);
    botonCancelar.setContentAreaFilled(false);
    botonCancelar.setBorderPainted(false);
    Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
    Image newimg2 = img2.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonCancelar.setIcon(new ImageIcon(newimg2));

    labelTitulo = new JLabel();
    labelTitulo.setText("NUEVO FIN");
    labelTitulo.setBounds(269, 11, 380, 30);
    labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

    botonGuardar.addActionListener(this);
    botonCancelar.addActionListener(this);
    getContentPane().add(botonCancelar);
    getContentPane().add(botonGuardar);
    getContentPane().add(labelTitulo);
    limpiar();
    setSize(745, 608);
    setTitle("Sistema E-vote: Paraguay Elecciones 2015");
    setLocationRelativeTo(null);
    setResizable(false);
    getContentPane().setLayout(null);
    // recuperarDatos();

    btnHome = new JButton("");
    btnHome.setToolTipText("Inicio");
    btnHome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        DefinicionesGenerales menuprincipal = new DefinicionesGenerales();
        menuprincipal.setVisible(true);
        dispose();
      }
    });
    btnHome.setIcon(new ImageIcon(VentanaRegistroActaFin.class.getResource("/imgs/home.png")));
    btnHome.setBounds(0, 0, 32, 32);
    Image img = ((ImageIcon) btnHome.getIcon()).getImage();
    Image newimg = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnHome.setIcon(new ImageIcon(newimg));
    getContentPane().add(btnHome);

    lblCod = new JLabel();
    lblCod.setHorizontalAlignment(SwingConstants.RIGHT);
    lblCod.setText("Codigo:");
    lblCod.setBounds(130, 52, 61, 25);
    getContentPane().add(lblCod);

    txtCod = new JTextField();
    txtCod.setText("123");
    txtCod.setBounds(213, 54, 108, 25);
    getContentPane().add(txtCod);
    txtCod.setColumns(10);
    txtCod.setEnabled(false);

    lblMensaje = new JLabel("");
    lblMensaje.setForeground(Color.RED);
    lblMensaje.setBounds(213, 562, 363, 14);
    getContentPane().add(lblMensaje);

    lblDescripcion = new JLabel();
    lblDescripcion.setText("Descripción:");
    lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
    lblDescripcion.setBounds(130, 95, 73, 14);
    getContentPane().add(lblDescripcion);
    
    scrollPane = new JScrollPane();
    scrollPane.setBounds(213, 90, 482, 73);
    getContentPane().add(scrollPane);
    scrollPane.setViewportView(areaDesc);
    areaDesc.setLineWrap(true);
    areaDesc.setText(actaFin.getDescripcion());

    JLabel lblObservación = new JLabel();
    lblObservación.setText("Observación:");
    lblObservación.setHorizontalAlignment(SwingConstants.RIGHT);
    lblObservación.setBounds(130, 178, 73, 14);
    getContentPane().add(lblObservación);
    
    scrollPane_1 = new JScrollPane();
    scrollPane_1.setBounds(213, 178, 480, 73);
    getContentPane().add(scrollPane_1);
    areaObs.setLineWrap(true);
    scrollPane_1.setViewportView(areaObs);

    JLabel lblFecha = new JLabel();
    lblFecha.setText("Fecha:");
    lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
    lblFecha.setBounds(87, 275, 116, 25);
    getContentPane().add(lblFecha);

    JLabel lblNroVotantes = new JLabel();
    lblNroVotantes.setText("Nro. Votantes:");
    lblNroVotantes.setHorizontalAlignment(SwingConstants.RIGHT);
    lblNroVotantes.setBounds(87, 312, 116, 25);
    getContentPane().add(lblNroVotantes);
    lblCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
    lblCantidad.setBounds(213, 312, 66, 25);
    lblCantidad.setText(obtenerCantidadVotos());
    getContentPane().add(lblCantidad);

    JLabel lblTipoActa = new JLabel();
    lblTipoActa.setText("Tipo Acta:");
    lblTipoActa.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTipoActa.setBounds(87, 357, 116, 25);
    getContentPane().add(lblTipoActa);

    cmbTipoActa = new JComboBox(recuperarDatosComboBoxTipoActa());
    cmbTipoActa.setSelectedIndex(-1);
    cmbTipoActa.setBounds(213, 359, 289, 20);
    getContentPane().add(cmbTipoActa);
    
    JLabel label = new JLabel();
    label.setText(convertStringToDate(VentanaBuscarEvento.eventoClase.getFchDesde()));
    label.setHorizontalAlignment(SwingConstants.RIGHT);
    label.setBounds(213, 275, 116, 25);
    getContentPane().add(label);
    
    JLabel label_1 = new JLabel();
    label_1.setText("Hora:");
    label_1.setHorizontalAlignment(SwingConstants.RIGHT);
    label_1.setBounds(437, 270, 116, 25);
    getContentPane().add(label_1);
    
    
    spinnerHora.setBounds(569, 272, 41, 20);
    getContentPane().add(spinnerHora);
    
    JLabel label_2 = new JLabel();
    label_2.setText(":");
    label_2.setHorizontalAlignment(SwingConstants.RIGHT);
    label_2.setBounds(597, 270, 19, 25);
    getContentPane().add(label_2);
    
    
    spinnerMinuto.setBounds(629, 272, 41, 20);
    getContentPane().add(spinnerMinuto);
    // recuperarDatos();

    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");

    getRootPane().getActionMap().put("clickButton", new AbstractAction() {
      public void actionPerformed(ActionEvent ae) {
        botonGuardar.doClick();
        System.out.println("button clicked");
      }
    });


    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clickButtonescape");

    getRootPane().getActionMap().put("clickButtonescape", new AbstractAction() {
      public void actionPerformed(ActionEvent ae) {
        botonCancelar.doClick();
        System.out.println("button esc clicked");
      }
    });

  }

  private String obtenerCantidadVotos() {
    ActaDAO actaDAO = new ActaDAO();
    Integer c =
        actaDAO.obtenerCantidadVotosByMesayEvento(actaFin.getIdMesa().getIdMesa(),
            Integer.parseInt(VentanaBuscarEvento.evento));
   return c.toString();
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

        Integer listaSelected = 0, personaSelected = 0, obervacionSelected = 0;
        String item4 = "";

        /*
         * if(!(combo.isVisible())){ if(combo.getSelectedIndex() ==-1){
         * lblMensaje.setText("Ingrese todos los campos"); Timer t = new Timer(Login.timer, new
         * ActionListener() {
         * 
         * public void actionPerformed(ActionEvent e) { lblMensaje.setText(null); } });
         * t.setRepeats(false); t.start(); } } else{
         */

        /*
         * if (cmbLista.getSelectedIndex() != -1 && cmbPersona.getSelectedIndex() != -1 //&&
         * combo.getSelectedIndex() != -1 ) { Item item = (Item) cmbLista.getSelectedItem();
         * listaSelected = item.getId();
         * 
         * Item item3 = (Item) cmbPersona.getSelectedItem(); personaSelected = item3.getId(); if(
         * combo.getSelectedIndex() == -1 ){ item4 = esPresidenteOVice; } else{ item4 = (String)
         * combo.getSelectedItem(); }
         * 
         * 
         * 
         * // obervacionSelected = Integer.parseInt(item4); }
         */


        if (!(txtCod.getText().length() == 0)) {
          if (!(txtCod.getText().length() == 3)) {
            lblMensaje.setText("El codigo debe ser de 3 caracteres.");
            Timer t = new Timer(Login.timer, new ActionListener() {

              public void actionPerformed(ActionEvent e) {
                lblMensaje.setText(null);
              }
            });
            t.setRepeats(false);
            t.start();
          } else if


          (actaValidator.ValidarCodigo(
          // txtCod.getText(), personaSelected.toString(),
          // listaSelected.toString(), item4, VentanaBuscarEvento.evento
              ) == true) {
            
            
          
              
              Item item2 = (Item) cmbTipoActa.getSelectedItem();
              Integer tipoActaSelected = item2.getId();
              
              


            if (codTemporal == "") {
              // if (candidatoValidator.ValidarPersona(personaSelected) == false) {
              EventoDAO eventoDAO = new EventoDAO();
              MesaDAO mesaDAO = new MesaDAO();
              ActaDAO actaDAO = new ActaDAO();
              TipoActaDAO tipoActaDAO = new TipoActaDAO();

              UcsawsActas actaGuardar = new UcsawsActas();


              UcsawsEvento evento = eventoDAO.obtenerEventoById(VentanaBuscarEvento.evento);
              
              //  ***
              Calendar now = Calendar.getInstance();
              now.setTime(evento.getFchDesde());
              now.set(Calendar.HOUR, (Integer)  spinnerHora.getValue() );
              now.set(Calendar.MINUTE, (Integer)  spinnerMinuto.getValue());
              now.set(Calendar.SECOND, 0);
            //  ***

              actaGuardar.setIdMesa(actaFin.getIdMesa());
              actaGuardar.setIdEvento(evento);
              actaGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado);
              actaGuardar.setFchIns(new Date());
              actaGuardar.setDescripcion(item4);
              actaGuardar.setTipoActa(tipoActaDAO.obtenerTipoActaById(tipoActaSelected));
              actaGuardar.setDescripcion(areaDesc.getText());
              actaGuardar.setObservacion(areaObs.getText());
              actaGuardar.setFecha(now.getTime());
              actaGuardar.setNumeroVotantes(Integer.parseInt(lblCantidad.getText()));
              actaGuardar.setActaFinalizada(actaFin);

              // candidatoAGuardar.setUcsawsPais(pais);



              actaDAO.guardarActa(actaGuardar);


              VentanaBuscarActa buscar = new VentanaBuscarActa();
              buscar.setVisible(true);
              dispose();
              /*
               * }
               * 
               * else // JOptionPane.showMessageDialog(null, // "Ya existe el genero " +
               * txtDesc.getText(), // "Información",JOptionPane.WARNING_MESSAGE);
               * lblMensaje.setText("La Persona no puede tener mas de una candidatura"); Timer t =
               * new Timer(Login.timer, new ActionListener() {
               * 
               * public void actionPerformed(ActionEvent e) { lblMensaje.setText(null); } });
               * t.setRepeats(false); t.start();
               */
              // this.dispose();
            } else {

            }
          } else {
            // JOptionPane.showMessageDialog(null,
            // "Ya existe el genero " + txtDesc.getText(),
            // "Información",JOptionPane.WARNING_MESSAGE);
            lblMensaje
                .setText("Ya existe el candidato con alguno de los datos cargados. Favor verificar.");
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
        // }
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error al intentar insertar : " + ex, "Error",
            JOptionPane.ERROR_MESSAGE);
      }

    }
    if (e.getSource() == botonCancelar) {
      VentanaBuscarActa candidato = new VentanaBuscarActa();
      candidato.setVisible(true);
      this.dispose();

    }
  }

  /*
   * private void recuperarDatos() { JSONArray filas = new JSONArray(); JSONArray fil = new
   * JSONArray();
   * 
   * boolean existe = false;
   * 
   * // Statement estatuto = conex.getConnection().createStatement();
   * 
   * ApplicationContext ctx = SpringApplication .run(WeatherConfiguration.class);
   * 
   * WeatherClient weatherClient = ctx.getBean(WeatherClient.class); QueryGenericoRequest query =
   * new QueryGenericoRequest();
   * 
   * // para registrar se inserta el codigo es 1 query.setTipoQueryGenerico(2);
   * 
   * query.setQueryGenerico(
   * "SELECT ca.id_candidatos, ca.codigo, nombre, apellido , li.nro_lista || ' - ' ||  li.nombre_lista "
   * + " , tl.descripcion, ca.descripcion obs" +
   * " from ucsaws_candidatos  ca join ucsaws_persona per on (ca.id_persona = per.id_persona) " +
   * " join ucsaws_listas li on (ca.id_lista = li.id_lista)" +
   * " join ucsaws_tipo_lista tl on (li.id_tipo_lista = tl.id_tipo_lista)" +
   * " where ca.id_evento = " + VentanaBuscarEvento.evento );
   * 
   * QueryGenericoResponse response = weatherClient .getQueryGenericoResponse(query);
   * weatherClient.printQueryGenericoResponse(response);
   * 
   * String res = response.getQueryGenericoResponse();
   * 
   * if (res.compareTo("ERRORRRRRRR") == 0) { JOptionPane.showMessageDialog(null, "algo salio mal",
   * "Advertencia", JOptionPane.WARNING_MESSAGE);
   * 
   * }
   * 
   * else { existe = true;
   * 
   * String generoAntesPartir = response.getQueryGenericoResponse();
   * 
   * JSONParser j = new JSONParser(); Object ob = null; String part1, part2, part3;
   * 
   * try { ob = j.parse(generoAntesPartir); } catch (org.json.simple.parser.ParseException e) { //
   * TODO Auto-generated catch block e.printStackTrace(); }
   * 
   * filas = (JSONArray) ob;
   * 
   * }
   * 
   * Vector<Vector<Object>> data = new Vector<Vector<Object>>();
   * 
   * //Vector<Object> vector = new Vector<Object>();
   * 
   * 
   * int ite = 0; String campo4, campo5 = ""; int contador = 0; while (filas.size() > ite) {
   * contador = contador + 1; fil = (JSONArray) filas.get(ite);
   * 
   * String[] fin = { fil.get(0).toString(), String.valueOf(contador),fil.get(1).toString(),
   * fil.get(2).toString(),fil.get(3).toString(),fil.get(4).toString()
   * ,fil.get(5).toString(),fil.get(6).toString()};
   * 
   * //model.ciudades.add(fin); int pos = 0; Vector<Object> vector = new Vector<Object>(); while(pos
   * < fin.length){ vector.add(fin[pos]); pos++; } ite++; data.add(vector); }
   * 
   * 
   * 
   * 
   * // names of columns
   * 
   * String[] colNames = new String[] {"ID", "Item", "codigo", "nombre","apellido", "Lista", "Tipo",
   * "Observacion"};
   * 
   * Vector<String> columnNames = new Vector<String>(); int columnCount = colNames.length; for (int
   * column = 0; column < columnCount; column++) { columnNames.add(colNames[column]); }
   * 
   * dm = new DefaultTableModel(data, columnNames);
   * 
   * }
   */

  private Vector recuperarDatosComboBoxPersona() {
    Vector model = new Vector();

    PersonaDAO personaDAO = new PersonaDAO();
    // TipoListaDAO tipoListaDAO = new TipoListaDAO();

    JSONArray filas = new JSONArray();
    JSONArray fil = new JSONArray();

    boolean existe = false;

    // Statement estatuto = conex.getConnection().createStatement();

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(107);

    query.setQueryGenerico(VentanaBuscarEvento.evento);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    String res = response.getQueryGenericoResponse();

    // json string to List<String>;
    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();
    List<UcsawsPersona> lista = new ArrayList<UcsawsPersona>();
    try {
      lista = mapper.readValue(jsonInString, new TypeReference<List<UcsawsPersona>>() {});
    } catch (Exception e) {
      System.out.println(e);
    }
    List<UcsawsPersona> persona;
    if ((lista.isEmpty())) {
      // JOptionPane.showMessageDialog(null, "algo salio mal",
      // "Advertencia", JOptionPane.WARNING_MESSAGE);
      // return lista;
    }



    else {

      // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
      Iterator<UcsawsPersona> ite = lista.iterator();

      UcsawsPersona aux;

      while (ite.hasNext()) {
        aux = ite.next();

        model.addElement(new Item(aux.getIdPersona(), aux.getNombre() + " " + aux.getApellido()));

      }
      // return model;
    }

    return model;

  }

  private Vector recuperarDatosComboBoxTipoCandidato() {
    Vector model = new Vector();
    JSONArray filas = new JSONArray();
    JSONArray fil = new JSONArray();

    boolean existe = false;

    // Statement estatuto = conex.getConnection().createStatement();

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    // para registrar se inserta el codigo es 1
    query.setTipoQueryGenerico(2);

    // query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
    // +
    // "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");

    query.setQueryGenerico("SELECT id_tipo_candidato, descripcion" + " from ucsaws_tipo_candidato "
        + "order by descripcion");

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    String res = response.getQueryGenericoResponse();

    if (res.compareTo("ERRORRRRRRR") == 0) {
      JOptionPane.showMessageDialog(null, "algo salio mal", "Advertencia",
          JOptionPane.WARNING_MESSAGE);

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

      String[] fin = {fil.get(0).toString(), fil.get(1).toString(),};

      tcandidato.add(fin);
      model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
      ite++;
    }
    return model;

  }

  private Vector recuperarDatosComboBoxLista() {

    Vector model = new Vector();

    ListasDAO listaDAO = new ListasDAO();
    // TipoListaDAO tipoListaDAO = new TipoListaDAO();

    List<UcsawsListas> lista =
        listaDAO.obtenerListaByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));
    // List<UcsawsListas> lista =
    // listaDAO.obtenerListaByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

    if (lista.isEmpty()) {
      // JOptionPane.showMessageDialog(null, "algo salio mal",
      // "Advertencia", JOptionPane.WARNING_MESSAGE);
      // return lista;
    }

    else {

      // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
      Iterator<UcsawsListas> ite = lista.iterator();

      UcsawsListas aux;

      while (ite.hasNext()) {
        aux = ite.next();

        model.addElement(new Item(aux.getIdLista(), aux.getNombreLista()));

      }
      // return model;
    }

    return model;

  }

  public void filter(String query) {

    //
    // TableRowSorter<DefaultTableModel> tr = new
    // TableRowSorter<DefaultTableModel>(dm);
    //
    //
    //
    // table.setRowSorter(tr);
    //
    // tr.setRowFilter(RowFilter.regexFilter(query));

  }

  // public void borrarElementosComboPersona(){
  // cmbPersona.removeAllItems();
  // }
  //
  // public void refrescarComboPersona(){
  //
  //
  //
  //
  // cmbPersona = new JComboBox(recuperarDatosComboBoxPersona());
  //
  // //cmbPersona.revalidate();
  //
  // this.getContentPane().validate();
  // this.getContentPane().repaint();
  //
  //
  //
  // }
  private String patron(String s) {
    if (s.matches("\\d{4}/[a-zA-Z]{3}")) {
      System.out.println("Found good SSN: " + s);
    }
    return "El formato es ####/abc - 4 Digitos / 3 Letras.";
  }



  private Vector recuperarDatosComboBoxDepartamento() {
    Vector model = new Vector();

    DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    List<UcsawsDepartamento> lista =
        departamentoDAO.obtenerDepartamentoByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

    if (lista.isEmpty()) {
      // JOptionPane.showMessageDialog(null, "algo salio mal", "Advertencia",
      // JOptionPane.WARNING_MESSAGE);
      // return lista;
    }

    else {

      // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
      Iterator<UcsawsDepartamento> ite = lista.iterator();

      UcsawsDepartamento aux;

      while (ite.hasNext()) {
        aux = ite.next();

        model.addElement(new Item(aux.getIdDepartamento(), aux.getDescDepartamento()));

      }
      // return model;
    }

    return model;

  }

  private Vector recuperarDatosComboBoxDistrito(Integer idDepartamento) {
    Vector model = new Vector();

    DistritoDAO distritoDAO = new DistritoDAO();

    List<UcsawsDistrito> lista = distritoDAO.obtenerDistritoByIdDepartamento(idDepartamento);

    if (lista.isEmpty()) {
      // JOptionPane.showMessageDialog(null, "algo salio mal", "Advertencia",
      // JOptionPane.WARNING_MESSAGE);
      // return lista;
    }

    else {

      // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
      Iterator<UcsawsDistrito> ite = lista.iterator();

      UcsawsDistrito aux;

      while (ite.hasNext()) {
        aux = ite.next();

        model.addElement(new Item(aux.getIdDistrito(), aux.getDescDistrito()));

      }
      //
    }
    return model;
    // return model;

  }

  private Vector recuperarDatosComboBoxZona(Integer idZona) {
    Vector model = new Vector();


    ZonaDAO zonaDAO = new ZonaDAO();

    List<UcsawsZona> lista = zonaDAO.obtenerZonaByIdDistrito(idZona);

    if (lista.isEmpty()) {
      // JOptionPane.showMessageDialog(null, "algo salio mal", "Advertencia",
      // JOptionPane.WARNING_MESSAGE);
      // return lista;
    }

    else {

      // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
      Iterator<UcsawsZona> ite = lista.iterator();

      UcsawsZona aux;

      while (ite.hasNext()) {
        aux = ite.next();

        model.addElement(new Item(aux.getIdZona(), aux.getDescZona()));

      }
      //
    }
    return model;

  }

  private Vector recuperarDatosComboBoxLocal(Integer idZona) {
    Vector model = new Vector();

    LocalDAO localDAO = new LocalDAO();

    List<UcsawsLocal> lista = localDAO.obtenerLocalByIdZona(idZona);

    if (lista.isEmpty()) {
      // JOptionPane.showMessageDialog(null, "algo salio mal", "Advertencia",
      // JOptionPane.WARNING_MESSAGE);
      // return lista;
    }

    else {

      // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
      Iterator<UcsawsLocal> ite = lista.iterator();

      UcsawsLocal aux;

      while (ite.hasNext()) {
        aux = ite.next();

        model.addElement(new Item(aux.getIdLocal(), aux.getDescLocal()));

      }
      //
    }
    return model;



  }

  private Vector recuperarDatosComboBoxMesa(Integer idLocal) {
    Vector model = new Vector();

    MesaDAO mesaDAO = new MesaDAO();

    List<UcsawsMesa> lista = mesaDAO.obtenerMesaByIdLocal(idLocal);

    if (lista.isEmpty()) {
      // JOptionPane.showMessageDialog(null, "algo salio mal", "Advertencia",
      // JOptionPane.WARNING_MESSAGE);
      // return lista;
    }

    else {

      // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
      Iterator<UcsawsMesa> ite = lista.iterator();

      UcsawsMesa aux;

      while (ite.hasNext()) {
        aux = ite.next();

        model.addElement(new Item(aux.getIdMesa(), aux.getDescMesa()));

      }
      //
    }
    return model;

  }

  private Vector recuperarDatosComboBoxTipoActa() {
    Vector model = new Vector();

    TipoActaDAO tipoActaDAO = new TipoActaDAO();
    List<UcsawsTipoActa> lista =
        tipoActaDAO.obtenerTipoActaByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

    if (lista.isEmpty()) {
      // JOptionPane.showMessageDialog(null, "algo salio mal", "Advertencia",
      // JOptionPane.WARNING_MESSAGE);
      // return lista;
    }

    else {

      // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
      Iterator<UcsawsTipoActa> ite = lista.iterator();

      UcsawsTipoActa aux;

      while (ite.hasNext()) {
        
        aux = ite.next();
        if(aux.getCodigoActa().compareTo("CIE")==0){
        model.addElement(new Item(aux.getIdTipoActa(), aux.getDescripcion()));
      }
      }
      // return model;
    }

    return model;

  }

  void barrer() {

    // if (cmbDistrito.getSelectedIndex() != -1) {
  //  cmbDistrito.removeAllItems();
  //  cmbZona.removeAllItems();
  //  cmbLocal.removeAllItems();
 //  cmbMesa.removeAllItems();
    // }
  }

  void barrer2() {

    // if (cmbDistrito.getSelectedIndex() != -1) {
    // cmbDistrito.removeAllItems();
    // cmbZona.removeAllItems();
   // cmbLocal.removeAllItems();
   // cmbMesa.removeAllItems();
    // }
  }

  void barrer3() {

    // if(cmbDistrito.getSelectedIndex()!= -1){

    // cmbLocal.removeAllItems();
   // cmbMesa.removeAllItems();
    // }
  }

  void barrer4() {

    // if(cmbDistrito.getSelectedIndex()!= -1){

  //  cmbMesa.removeAllItems();
    // }
  }
  
  public String convertStringToDate(Date indate)
  {
     String dateString = null;
     SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
     /*you can also use DateFormat reference instead of SimpleDateFormat 
      * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
      */
     try{
      dateString = sdfr.format( indate );
     }catch (Exception ex ){
      System.out.println(ex);
     }
     return dateString;
  }
}
