package src.main.java.admin.miembromesa;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.validator.CandidatoValidator;
import src.main.java.dao.candidato.CandidatoDAO;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.listas.ListasDAO;
import src.main.java.dao.persona.PersonaDAO;
import src.main.java.dao.tipoLista.TipoListaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import entity.UcsawsCandidatos;
import entity.UcsawsEvento;
import entity.UcsawsListas;
import entity.UcsawsPersona;
import entity.UcsawsTipoLista;

public class VentanaRegistroCandidato extends JFrame implements ActionListener {

  private Coordinador miCoordinador; // objeto miCoordinador que permite la
  // relacion entre esta clase y la clase
  // coordinador
  private JLabel labelTitulo, lblMensaje, lblq;
  private JButton botonGuardar, botonCancelar;

  private MiembroMesaJTableModel model = new MiembroMesaJTableModel();

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
  private DefaultTableModel dm;

  private JComboBox combo;

  private CandidatoDAO candidatoDAO = new CandidatoDAO();
  private JLabel lblObservacin;

  private String obs = "";

  /**
   * constructor de la clase donde se inicializan todos los componentes de la ventana de registro
   */
  public VentanaRegistroCandidato() {

    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e) {
        txtCod.requestFocus();
      }
    });

    botonGuardar = new JButton();
    botonGuardar.setToolTipText("Guardar");
    botonGuardar
        .setIcon(new ImageIcon(VentanaRegistroCandidato.class.getResource("/imgs/save.png")));
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
    botonCancelar.setIcon(new ImageIcon(VentanaRegistroCandidato.class
        .getResource("/imgs/back2.png")));
    botonCancelar.setBounds(710, 206, 32, 32);
    botonCancelar.setOpaque(false);
    botonCancelar.setContentAreaFilled(false);
    botonCancelar.setBorderPainted(false);
    Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
    Image newimg2 = img2.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonCancelar.setIcon(new ImageIcon(newimg2));

    labelTitulo = new JLabel();
    labelTitulo.setText("NUEVO CANDIDATO");
    labelTitulo.setBounds(269, 11, 380, 30);
    labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

    botonGuardar.addActionListener(this);
    botonCancelar.addActionListener(this);
    getContentPane().add(botonCancelar);
    getContentPane().add(botonGuardar);
    getContentPane().add(labelTitulo);
    limpiar();
    setSize(745, 265);
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
    btnHome.setIcon(new ImageIcon(VentanaRegistroCandidato.class.getResource("/imgs/home.png")));
    btnHome.setBounds(0, 0, 32, 32);
    Image img = ((ImageIcon) btnHome.getIcon()).getImage();
    Image newimg = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnHome.setIcon(new ImageIcon(newimg));
    getContentPane().add(btnHome);

    cmbPersona = new JComboBox(recuperarDatosComboBoxPersona());
    cmbPersona.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        if (cmbPersona.getSelectedIndex() != -1) {

          Item item = (Item) cmbPersona.getSelectedItem();
          String p = item.getDescription();

          String string = p;

          // String string = "004-034556";
          String[] parts = string.split(" ");
          String part1 = parts[0]; // 004
          String part2 = parts[1]; // 034556

          txtCod.setText(part1.substring(0, 1) + part2.substring(0, 2));
          System.out.println(txtCod.getText());
        }
      }
    });
    cmbPersona.setBounds(213, 90, 501, 20);
    cmbPersona.setSelectedIndex(-1);
    getContentPane().add(cmbPersona);
    AutoCompleteDecorator.decorate(cmbPersona);

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

    combo = new JComboBox();
    combo.setBounds(213, 150, 501, 20);
    combo.addItem("PRE");
    combo.addItem("VICE");
    combo.setSelectedIndex(0);
    getContentPane().add(combo);

    lblObservacin.setVisible(false);
    combo.setVisible(false);

    cmbLista = new JComboBox(recuperarDatosComboBoxLista());
    cmbLista.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (cmbLista.getSelectedIndex() != -1) {
          Item item = (Item) cmbLista.getSelectedItem();
          Integer idTipoListaSelected = item.getId();
          // "Hello".toLowerCase().contains("He".toLowercase());
          ListasDAO listasDAO = new ListasDAO();
          TipoListaDAO tipoListaDAO = new TipoListaDAO();
          UcsawsListas l = new UcsawsListas();
          List<UcsawsTipoLista> tl = new ArrayList<UcsawsTipoLista>();
          l = listasDAO.obtenerListaByIdIdLista(idTipoListaSelected);
          tl =
              tipoListaDAO.obtenerTipoListaByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

          Iterator i = tl.iterator();
          boolean result = false;
          while (i.hasNext()) {
            UcsawsTipoLista element = (UcsawsTipoLista) i.next();
            if (l.getUcsawsTipoLista().getCodigo().compareTo(element.getCodigo()) == 0) {
              if (element.getCodigo().compareTo("PRE") == 0) {
                result = true;
              }
            }
          }


          if (result == true) {

            lblObservacin.setVisible(true);
            combo.setSelectedIndex(0);
            combo.setVisible(true);

          } else {
            esPresidenteOVice = "-";
            lblObservacin.setVisible(false);
            combo.setSelectedIndex(-1);
            combo.setVisible(false);

          }



        }
      }
    });

    cmbLista.setBounds(213, 123, 501, 20);
    cmbLista.setSelectedIndex(-1);
    getContentPane().add(cmbLista);
    AutoCompleteDecorator.decorate(cmbLista);

    lblCod = new JLabel();
    lblCod.setHorizontalAlignment(SwingConstants.RIGHT);
    lblCod.setText("Codigo:");
    lblCod.setBounds(130, 52, 61, 25);
    getContentPane().add(lblCod);

    txtCod = new JTextField();
    txtCod.setBounds(213, 54, 108, 25);
    getContentPane().add(txtCod);
    txtCod.setColumns(10);
    txtCod.setEnabled(false);

    lblMensaje = new JLabel("");
    lblMensaje.setForeground(Color.RED);
    lblMensaje.setBounds(212, 181, 363, 14);
    getContentPane().add(lblMensaje);
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

        if (cmbLista.getSelectedIndex() != -1 && cmbPersona.getSelectedIndex() != -1 //&& combo.getSelectedIndex() != -1
            ) {
          Item item = (Item) cmbLista.getSelectedItem();
          listaSelected = item.getId();

          Item item3 = (Item) cmbPersona.getSelectedItem();
          personaSelected = item3.getId();
              if(  combo.getSelectedIndex() == -1 ){
                item4 = esPresidenteOVice;
              }
              else{
                item4 = (String) combo.getSelectedItem();
              }
          

          
          // obervacionSelected = Integer.parseInt(item4);
        }
        

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


          (candidatoValidator.ValidarCodigo(txtCod.getText(), personaSelected.toString(),
              listaSelected.toString(), item4, VentanaBuscarEvento.evento) == false) {


            if (codTemporal == "") {
              // if (candidatoValidator.ValidarPersona(personaSelected) == false) {
              EventoDAO eventoDAO = new EventoDAO();
              PersonaDAO personaDAO = new PersonaDAO();
              ListasDAO listaDAO = new ListasDAO();
              CandidatoDAO candidatoDAO = new CandidatoDAO();

              UcsawsCandidatos candidatoAGuardar = new UcsawsCandidatos();


              UcsawsEvento evento = eventoDAO.obtenerEventoById(VentanaBuscarEvento.evento);


              candidatoAGuardar.setIdPersona(personaDAO.obtenerPersonaByIdPersona(personaSelected
                  .toString()));
              candidatoAGuardar.setIdLista(listaDAO.obtenerListaByIdIdLista(listaSelected));
              candidatoAGuardar.setIdEvento(evento);
              candidatoAGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado);
              candidatoAGuardar.setFchIns(new Date());
              candidatoAGuardar.setCodigo(txtCod.getText());
              candidatoAGuardar.setDescripcion(item4);

              // candidatoAGuardar.setUcsawsPais(pais);



              candidatoDAO.guardarCandidato(candidatoAGuardar);


              VentanaBuscarMiembroMesa buscar = new VentanaBuscarMiembroMesa();
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
      VentanaBuscarMiembroMesa candidato = new VentanaBuscarMiembroMesa();
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
}
