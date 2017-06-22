package src.main.java.admin.votantesHabilitados;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.buscadores.VentanaBuscarPerson;
import src.main.java.admin.departamento.Item;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.validator.VotantesHabilitadosValidator;
import src.main.java.dao.departamento.DepartamentoDAO;
import src.main.java.dao.distrito.DistritoDAO;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.local.LocalDAO;
import src.main.java.dao.mesa.MesaDAO;
import src.main.java.dao.persona.PersonaDAO;
import src.main.java.dao.votantesHabilitados.VotantesHabilitadosDAO;
import src.main.java.dao.zona.ZonaDAO;
import src.main.java.login.Login;
import entity.UcsawsDepartamento;
import entity.UcsawsDistrito;
import entity.UcsawsLocal;
import entity.UcsawsMesa;
import entity.UcsawsPersona;
import entity.UcsawsVotante;
import entity.UcsawsZona;

public class VentanaRegistroVotantesHabilitados extends JFrame implements ActionListener {

  private VentanaRegistroVotantesHabilitados a;

  private Coordinador miCoordinador; // objeto miCoordinador que permite la
  // relacion entre esta clase y la clase
  // coordinador
  private JLabel labelTitulo, lblMensaje, lblMesa;
  private JButton botonGuardar, botonCancelar;

  private VotantesHabilitadosJTableModel model = new VotantesHabilitadosJTableModel();

  private VotantesHabilitadosValidator votantesHabilitadosValidator =
      new VotantesHabilitadosValidator();

  public static String codTemporal = "";
  private JButton btnHome;

  List<Object[]> ciudades = new ArrayList<Object[]>();

  List<Object[]> listas = new ArrayList<Object[]>();

  List<Object[]> tcandidato = new ArrayList<Object[]>();
  private JComboBox cmbDepartamento, cmbMesa, cmbZona;
  private JLabel lblDepartamento;
  private JLabel lblZona;
  private JComboBox comboBox;

  private JComboBox cmbDistrito;

  private static String nombrecmb;
  private JLabel lblLocal;
  private JComboBox cmbLocal;

  private DefaultComboBoxModel dm, dmz, dml, dmm;
  private JButton btnSeleccionarPersona;

  public static JLabel lblNombrePersona;

  public static String personaSeleccionada = "";

  /**
   * constructor de la clase donde se inicializan todos los componentes de la ventana de registro
   */
  public VentanaRegistroVotantesHabilitados() {

    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e) {
        btnSeleccionarPersona.requestFocus();
      }
    });

    botonGuardar = new JButton();
    botonGuardar.setToolTipText("Guardar");
    botonGuardar.setIcon(new ImageIcon(VentanaRegistroVotantesHabilitados.class
        .getResource("/imgs/save.png")));
    botonGuardar.setBounds(550, 46, 32, 32);
    botonGuardar.setOpaque(false);
    botonGuardar.setContentAreaFilled(false);
    botonGuardar.setBorderPainted(false);
    Image img3 = ((ImageIcon) botonGuardar.getIcon()).getImage();
    Image newimg3 = img3.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonGuardar.setIcon(new ImageIcon(newimg3));

    botonCancelar = new JButton();
    botonCancelar.setBackground(Color.WHITE);
    botonCancelar.setToolTipText("Atrás");
    botonCancelar.setIcon(new ImageIcon(VentanaRegistroVotantesHabilitados.class
        .getResource("/imgs/back2.png")));
    botonCancelar.setBounds(700, 262, 32, 32);
    botonCancelar.setOpaque(false);
    botonCancelar.setContentAreaFilled(false);
    botonCancelar.setBorderPainted(false);
    Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
    Image newimg2 = img2.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonCancelar.setIcon(new ImageIcon(newimg2));

    labelTitulo = new JLabel();
    labelTitulo.setText("NUEVO VOTANTE HABILITADO");
    labelTitulo.setBounds(163, 11, 486, 30);
    labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

    botonGuardar.addActionListener(this);
    botonCancelar.addActionListener(this);
    getContentPane().add(botonCancelar);
    getContentPane().add(botonGuardar);
    getContentPane().add(labelTitulo);
    limpiar();
    setSize(740, 320);
    setTitle("Sistema E-vote: Paraguay Elecciones 2015");
    setLocationRelativeTo(null);
    setResizable(false);
    getContentPane().setLayout(null);

    btnHome = new JButton("");
    btnHome.setToolTipText("Inicio");
    btnHome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        DefinicionesGenerales menuprincipal = new DefinicionesGenerales();
        menuprincipal.setVisible(true);
        dispose();
      }
    });
    btnHome.setIcon(new ImageIcon(VentanaRegistroVotantesHabilitados.class
        .getResource("/imgs/home.png")));
    btnHome.setBounds(0, 0, 32, 32);
    Image img = ((ImageIcon) btnHome.getIcon()).getImage();
    Image newimg = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnHome.setIcon(new ImageIcon(newimg));
    getContentPane().add(btnHome);

    cmbDepartamento = new JComboBox(recuperarDatosComboBoxDepartamento());
    cmbDepartamento.setSelectedIndex(-1);

    cmbDepartamento.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        if (cmbDepartamento.getSelectedIndex() != -1) {

          barrer();

          Item item = (Item) cmbDepartamento.getSelectedItem();
          Integer idDepartamentoSelected = item.getId();

          cmbDistrito.setModel(new DefaultComboBoxModel<Vector>(
              recuperarDatosComboBoxDistrito(idDepartamentoSelected)));
          cmbDistrito.setSelectedIndex(-1);
          // cmbDistrito.addActionListener(new ActionListener() {
          //
          // @Override
          // public void actionPerformed(ActionEvent arg0) {
          // // TODO Auto-generated method stub
          // if (cmbDistrito.getSelectedIndex()!=-1){
          //
          // Item item = (Item) cmbDistrito.getSelectedItem();
          // Integer idDistritoSelected = item.getId();
          //
          //
          // cmbZona.addActionListener(new ActionListener() {
          //
          // @Override
          // public void actionPerformed(ActionEvent arg0) {
          // // TODO Auto-generated method stub
          //
          // Item item = (Item) cmbZona.getSelectedItem();
          // Integer idDistritoSelected = item.getId();
          //
          //
          // cmbLocal.addActionListener(new ActionListener() {
          //
          // @Override
          // public void actionPerformed(ActionEvent arg0) {
          // // TODO Auto-generated method stub
          //
          // Item item = (Item) cmbLocal
          // .getSelectedItem();
          // Integer idDistritoSelected = item
          // .getId();
          //
          //
          // }
          // });
          //
          //
          //
          //
          //
          // }
          // });
          //
          // }
          // }
          // });
        }
      }
    });

    // cmbDepartamento.addItemListener(new ItemListener() {
    // public void itemStateChanged(ItemEvent arg0) {
    //
    //
    //
    //
    //
    //
    // }
    // });

    cmbDepartamento.setBounds(209, 81, 289, 20);
    getContentPane().add(cmbDepartamento);
    AutoCompleteDecorator.decorate(cmbDepartamento);

    cmbDistrito = new JComboBox();
    cmbDistrito.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (cmbDistrito.getSelectedIndex() != -1) {

          Item item = (Item) cmbDistrito.getSelectedItem();
          Integer idDepartamentoSelected = item.getId();


          cmbZona.setModel(new DefaultComboBoxModel<Vector>(
              recuperarDatosComboBoxZona(idDepartamentoSelected)));
          cmbZona.setSelectedIndex(-1);
          barrer2();
        } else
          barrer2();
      }
    });
    cmbDistrito.setBounds(209, 111, 289, 20);
    cmbDistrito.revalidate();
    AutoCompleteDecorator.decorate(cmbDistrito);

    cmbZona = new JComboBox();
    cmbZona.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (cmbZona.getSelectedIndex() != -1) {

          Item item = (Item) cmbZona.getSelectedItem();
          Integer idDepartamentoSelected = item.getId();


          cmbLocal.setModel(new DefaultComboBoxModel<Vector>(
              recuperarDatosComboBoxLocal(idDepartamentoSelected)));
          cmbLocal.setSelectedIndex(-1);
        } else
          barrer3();
      }
    });
    cmbZona.setBounds(209, 144, 289, 20);
    getContentPane().add(cmbZona);
    AutoCompleteDecorator.decorate(cmbZona);

    // repaint();
    getContentPane().add(cmbDistrito);

    cmbLocal = new JComboBox();
    cmbLocal.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (cmbLocal.getSelectedIndex() != -1) {

          Item item = (Item) cmbLocal.getSelectedItem();
          Integer idDepartamentoSelected = item.getId();


          cmbMesa.setModel(new DefaultComboBoxModel<Vector>(
              recuperarDatosComboBoxMesa(idDepartamentoSelected)));
          cmbMesa.setSelectedIndex(-1);
        }

        else {
          barrer4();
        }
      }
    });
    cmbLocal.setBounds(209, 172, 289, 20);
    getContentPane().add(cmbLocal);
    AutoCompleteDecorator.decorate(cmbLocal);

    cmbMesa = new JComboBox();
    cmbMesa.setBounds(209, 199, 169, 20);
    getContentPane().add(cmbMesa);
    AutoCompleteDecorator.decorate(cmbMesa);

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

    // cmbDistrito.paint(cmbDistrito.getGraphics());

    JLabel lblPersona = new JLabel();
    lblPersona.setHorizontalAlignment(SwingConstants.RIGHT);
    lblPersona.setText("Persona:");
    lblPersona.setBounds(141, 46, 61, 25);
    getContentPane().add(lblPersona);

    lblDepartamento = new JLabel();
    lblDepartamento.setHorizontalAlignment(SwingConstants.RIGHT);
    lblDepartamento.setText("Departamento:");
    lblDepartamento.setBounds(129, 84, 73, 14);
    getContentPane().add(lblDepartamento);

    lblMensaje = new JLabel("");
    lblMensaje.setForeground(Color.RED);
    lblMensaje.setBounds(209, 230, 363, 14);
    getContentPane().add(lblMensaje);

    lblMesa = new JLabel();
    lblMesa.setText("Mesa:");
    lblMesa.setHorizontalAlignment(SwingConstants.RIGHT);
    lblMesa.setBounds(141, 197, 61, 25);
    lblMesa.setVisible(true);
    getContentPane().add(lblMesa);

    lblZona = new JLabel("Zona:");
    lblZona.setHorizontalAlignment(SwingConstants.RIGHT);
    lblZona.setBounds(163, 144, 41, 20);
    getContentPane().add(lblZona);

    JLabel lblDistrito = new JLabel();
    lblDistrito.setText("Distrito");
    lblDistrito.setHorizontalAlignment(SwingConstants.RIGHT);
    lblDistrito.setBounds(129, 114, 73, 14);
    getContentPane().add(lblDistrito);

    lblLocal = new JLabel("Local:");
    lblLocal.setBounds(174, 172, 28, 14);
    getContentPane().add(lblLocal);

    btnSeleccionarPersona = new JButton("");
    btnSeleccionarPersona.setIcon(new ImageIcon(VentanaRegistroVotantesHabilitados.class
        .getResource("/imgs/hojas.png")));
    btnSeleccionarPersona.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        VentanaBuscarPerson buscar = new VentanaBuscarPerson(a, true);
        buscar.setVisible(true);

      }
    });
    btnSeleccionarPersona.setToolTipText("Seleccionar Persona...");
    btnSeleccionarPersona.setBounds(508, 42, 32, 32);
    getContentPane().add(btnSeleccionarPersona);

    Border border = LineBorder.createGrayLineBorder();

    personaSeleccionada = "";

    lblNombrePersona = new JLabel(personaSeleccionada);
    lblNombrePersona.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent arg0) {
        VentanaBuscarPerson buscar = new VentanaBuscarPerson(a, true);
        buscar.setVisible(true);
      }
    });
    lblNombrePersona.setBounds(209, 46, 289, 25);
    lblNombrePersona.setBorder(border);
    getContentPane().add(lblNombrePersona);
    // recuperarDatos();



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
        Integer personaSelected = 0;
        // Item item = (Item) cmbPersona.getSelectedItem();
        if (codTemporal.compareTo("") != 0) {
          personaSelected = Integer.parseInt(codTemporal);
        }


        if (lblNombrePersona.getText().length() > 0 && cmbDepartamento.getSelectedIndex() != -1
            && (codTemporal != null || codTemporal.length() > 0)
            && cmbDistrito.getSelectedIndex() != -1 && cmbZona.getSelectedIndex() != -1
            && cmbLocal.getSelectedIndex() != -1 && cmbMesa.getSelectedIndex() != -1) {

          Item item2 = (Item) cmbDepartamento.getSelectedItem();
          Integer habilitadoSelected = item2.getId();

          PersonaDAO personaDAO = new PersonaDAO();
          UcsawsPersona p = personaDAO.obtenerPersonaByIdPersona(codTemporal);

          if (votantesHabilitadosValidator.ValidarCedula(p.getCi()) == false) {
            // Genero genero = new Genero();
            // genero.setDescripcion(textGenero.getText());

            Calendar calendar = new GregorianCalendar();
            int year = calendar.get(Calendar.YEAR);



            Item itemMesa = (Item) cmbMesa.getSelectedItem();
            Integer idMesa = itemMesa.getId();
            System.out.println(idMesa);

            EventoDAO eventoDAO = new EventoDAO();
            // PersonaDAO personaDAO = new PersonaDAO();
            MesaDAO mesaDAO = new MesaDAO();


            UcsawsVotante votanteAGuardar = new UcsawsVotante();
            votanteAGuardar.setFchIns(new Date());
            votanteAGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado.toUpperCase());
            votanteAGuardar.setHabilitado(2);
            votanteAGuardar.setIdEvento(eventoDAO.obtenerEventoById(VentanaBuscarEvento.evento));
            votanteAGuardar.setIdPersona(personaDAO.obtenerPersonaByIdPersona(personaSelected
                .toString()));
            votanteAGuardar.setSufrago(0);
            votanteAGuardar.setUcsawsMesa(mesaDAO.obtenerMesaByID(idMesa));

            VotantesHabilitadosDAO votantesDAO = new VotantesHabilitadosDAO();
            votantesDAO.guardarVotante(votanteAGuardar);

            VentanaBuscarVotantesHabilitados votantes = new VentanaBuscarVotantesHabilitados();
            votantes.setVisible(true);
            dispose();



          } else {
            // JOptionPane.showMessageDialog(null,
            // "Ya existe el genero " + txtDesc.getText(),
            // "Información",JOptionPane.WARNING_MESSAGE);
            lblMensaje.setText("Una persona solo puede estar registrada una sola vez por evento.");
            Timer t = new Timer(Login.timer, new ActionListener() {

              public void actionPerformed(ActionEvent e) {
                lblMensaje.setText(null);
              }
            });
            t.setRepeats(false);
            t.start();
          }

        } else {
          lblMensaje.setText("Por favor, ingrese todos los campos.");
          Timer t = new Timer(Login.timer, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
              lblMensaje.setText(null);
            }
          });
          t.setRepeats(false);
          t.start();
        }

      } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error al intentar insertar", "Error",
            JOptionPane.ERROR_MESSAGE);
      }

    }
    if (e.getSource() == botonCancelar) {
      VentanaBuscarVotantesHabilitados votante = new VentanaBuscarVotantesHabilitados();
      votante.setVisible(true);
      personaSeleccionada = "";
      this.dispose();

    }
  }



  // private Vector recuperarDatosComboBoxMesa(Integer pais) {
  //
  // Item item = (Item) cmbPersona.getSelectedItem();
  // Integer personaSelected = item.getId();
  //
  // Vector model = new Vector();
  // JSONArray filas = new JSONArray();
  // JSONArray fil = new JSONArray();
  //
  // boolean existe = false;
  //
  // // Statement estatuto = conex.getConnection().createStatement();
  //
  // ApplicationContext ctx = SpringApplication
  // .run(WeatherConfiguration.class);
  //
  // WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
  // QueryGenericoRequest query = new QueryGenericoRequest();
  //
  // // para registrar se inserta el codigo es 1
  // query.setTipoQueryGenerico(2);
  //
  // //
  // query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
  // // +
  // //
  // "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");
  //
  // query.setQueryGenerico("SELECT id_mesa, desc_mesa"
  // + " ucsaws_mesa m join ucsaws_local l on (m.id_local = l.id_local)"
  // + " join ucsaws_zona z on (l.id_zona = z.id_zona)"
  // + " join ucsaws_distrito d on (d.id_distrito = z.id_distrito)"
  // +
  // " join ucsaws_departamento de on (de.id_departamento = d.id_departamento)"
  // + " join ucsaws_votante vo on (vo.id_mesa = m.id_mesa)"
  // + " join ucsaws_persona per on (per.id_persona = vo.id_persona)"
  // + " where id_pais_actual = " + pais);
  //
  // QueryGenericoResponse response = weatherClient
  // .getQueryGenericoResponse(query);
  // weatherClient.printQueryGenericoResponse(response);
  //
  // String res = response.getQueryGenericoResponse();
  //
  // if (res.compareTo("ERRORRRRRRR") == 0) {
  // JOptionPane.showMessageDialog(null, "algo salio mal",
  // "Advertencia", JOptionPane.WARNING_MESSAGE);
  //
  // }
  //
  // else {
  // existe = true;
  //
  // String generoAntesPartir = response.getQueryGenericoResponse();
  //
  // JSONParser j = new JSONParser();
  // Object ob = null;
  // String part1, part2, part3;
  //
  // try {
  // ob = j.parse(generoAntesPartir);
  // } catch (org.json.simple.parser.ParseException e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // }
  //
  // filas = (JSONArray) ob;
  //
  // }
  //
  // int ite = 0;
  // String campo4, campo5 = "";
  // while (filas.size() > ite) {
  // fil = (JSONArray) filas.get(ite);
  //
  // String[] fin = { fil.get(0).toString(), fil.get(1).toString(), };
  //
  // ciudades.add(fin);
  // model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
  // ite++;
  // }
  // return model;
  // }



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

  void barrerTodo() {

    lblNombrePersona.setText("");
    // cmbDepartamento.get // removeAllItems();
    cmbDistrito.removeAllItems();
    cmbZona.removeAllItems();
    cmbLocal.removeAllItems();
    cmbMesa.removeAllItems();
    cmbDepartamento.setSelectedIndex(-1);

  }

  void barrer() {

    // if (cmbDistrito.getSelectedIndex() != -1) {
    cmbDistrito.removeAllItems();
    cmbZona.removeAllItems();
    cmbLocal.removeAllItems();
    cmbMesa.removeAllItems();
    // }
  }

  void barrer2() {

    // if (cmbDistrito.getSelectedIndex() != -1) {
    // cmbDistrito.removeAllItems();
    // cmbZona.removeAllItems();
    cmbLocal.removeAllItems();
    cmbMesa.removeAllItems();
    // }
  }

  void barrer3() {

    // if(cmbDistrito.getSelectedIndex()!= -1){

    // cmbLocal.removeAllItems();
    cmbMesa.removeAllItems();
    // }
  }

  void barrer4() {

    // if(cmbDistrito.getSelectedIndex()!= -1){

    cmbMesa.removeAllItems();
    // }
  }
}
