package src.main.java.admin.vigencia;

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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jdesktop.swingx.JXErrorPane;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsPais;
import entity.UcsawsTipoEvento;
import entity.UcsawsVigenciaHorarioXPais;
import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.validator.VigenciaValidator;
import src.main.java.dao.pais.PaisDAO;
import src.main.java.dao.vigencia.VigenciaDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaRegistroVigencia extends JFrame implements ActionListener {

  private Coordinador miCoordinador; // objeto miCoordinador que permite la
  // relacion entre esta clase y la clase
  // coordinador


  String fechaDesde, fechaHasta;



  private JLabel labelTitulo, lblMensaje;
  private JButton botonGuardar, botonCancelar;

  private VigenciaJTableModel model = new VigenciaJTableModel();

  private VentanaRegistroVigencia ventanaRegistroVigencia;

  private JFormattedTextField txtHasta, txtDesde;

  private VigenciaValidator vigenciaValidator = new VigenciaValidator();

  private String codTemporal = "";
  private JButton btnHome;

  List<Object[]> ciudades = new ArrayList<Object[]>();

  List<Object[]> listas = new ArrayList<Object[]>();

  List<Object[]> tcandidato = new ArrayList<Object[]>();
  private JComboBox cmbPais;
  private JTextField txtHoraDesde;
  private JTextField txtMinutoDesde;
  private JTextField txtHoraHasta;
  private JTextField txtMinutoHasta;
  private JLabel label;
  private JButton btnAtras;

  /**
   * constructor de la clase donde se inicializan todos los componentes de la ventana de registro
   */
  public VentanaRegistroVigencia() {

    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e) {
        cmbPais.requestFocus();
      }
    });



    botonGuardar = new JButton();
    botonGuardar.setToolTipText("Guardar");
    botonGuardar
        .setIcon(new ImageIcon(VentanaRegistroVigencia.class.getResource("/imgs/save.png")));
    botonGuardar.setBounds(410, 56, 32, 32);
    botonGuardar.setOpaque(false);
    botonGuardar.setContentAreaFilled(false);
    botonGuardar.setBorderPainted(false);
    Image img3 = ((ImageIcon) botonGuardar.getIcon()).getImage();
    Image newimg3 = img3.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonGuardar.setIcon(new ImageIcon(newimg3));

    botonCancelar = new JButton();
    botonCancelar.setBackground(Color.WHITE);
    botonCancelar.setToolTipText("Atrás");
    botonCancelar.setIcon(new ImageIcon(VentanaRegistroVigencia.class
        .getResource("/imgs/back2.png")));
    botonCancelar.setBounds(774, 383, 32, 32);
    botonCancelar.setOpaque(false);
    botonCancelar.setContentAreaFilled(false);
    botonCancelar.setBorderPainted(false);
    Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
    Image newimg2 = img2.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonCancelar.setIcon(new ImageIcon(newimg2));
    // Image newimg4 = img4.getScaledInstance(32, 32,
    // java.awt.Image.SCALE_SMOOTH);

    labelTitulo = new JLabel();
    labelTitulo.setText("NUEVA VIGENCIA");
    labelTitulo.setBounds(269, 11, 380, 30);
    labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

    botonGuardar.addActionListener(this);
    botonCancelar.addActionListener(this);
    getContentPane().add(botonCancelar);
    getContentPane().add(botonGuardar);
    getContentPane().add(labelTitulo);
    limpiar();
    setSize(573, 245);
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
    btnHome.setIcon(new ImageIcon(VentanaRegistroVigencia.class.getResource("/imgs/home.png")));
    btnHome.setBounds(0, 0, 32, 32);
    Image img = ((ImageIcon) btnHome.getIcon()).getImage();
    Image newimg = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnHome.setIcon(new ImageIcon(newimg));
    getContentPane().add(btnHome);

    cmbPais = new JComboBox(recuperarDatosComboBoxZona());
    cmbPais.setToolTipText("Nro. y Descripcion de Distrito");
    cmbPais.setBounds(150, 65, 241, 20);
    cmbPais.setSelectedIndex(-1);
    getContentPane().add(cmbPais);

    JLabel lblPais = new JLabel();
    lblPais.setHorizontalAlignment(SwingConstants.RIGHT);
    lblPais.setText("Pais:");
    lblPais.setBounds(79, 63, 61, 25);
    getContentPane().add(lblPais);

    lblMensaje = new JLabel("");
    lblMensaje.setForeground(Color.RED);
    lblMensaje.setBounds(159, 167, 363, 14);
    getContentPane().add(lblMensaje);

    JLabel lblDesde = new JLabel();
    lblDesde.setText("Fch. Desde:");
    lblDesde.setHorizontalAlignment(SwingConstants.RIGHT);
    lblDesde.setBounds(26, 96, 116, 25);
    getContentPane().add(lblDesde);

    java.util.Date date = VentanaBuscarEvento.eventoClase.getFchDesde();
    DateTime desde = new DateTime(date);

    java.util.Date date2 = VentanaBuscarEvento.eventoClase.getFchHasta();
    DateTime hasta = new DateTime(date2);

    fechaDesde = desde.getDayOfMonth() + "/" + desde.getMonthOfYear() + "/" + desde.getYear();
    fechaHasta = hasta.getDayOfMonth() + "/" + hasta.getMonthOfYear() + "/" + hasta.getYear();
    
    txtDesde = new JFormattedTextField();
    txtDesde.setText(fechaDesde);
    txtDesde.setBounds(150, 96, 103, 23);
    getContentPane().add(txtDesde);
    txtDesde.setEditable(false);

    JLabel lblhasta = new JLabel();
    lblhasta.setText("Fch. Hasta:");
    lblhasta.setHorizontalAlignment(SwingConstants.RIGHT);
    lblhasta.setBounds(26, 131, 116, 25);
    getContentPane().add(lblhasta);

    txtHasta = new JFormattedTextField();
    txtHasta.setText(fechaDesde);
    txtHasta.setBounds(150, 130, 103, 23);
    getContentPane().add(txtHasta);
    txtHasta.setEditable(false);

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
        if (hora > 23) {
          txtHoraDesde.setText("23");
        } else if (hora < 1) {
          txtHoraDesde.setText("01");
        }


        if (txtHoraDesde.getText().length() == 1) {
          txtHoraDesde.setText(0 + txtHoraDesde.getText());
        }
      }
    });
    txtHoraDesde.setBounds(277, 96, 32, 23);
    getContentPane().add(txtHoraDesde);
    txtHoraDesde.setColumns(10);

    txtMinutoDesde = new JTextField();
    txtMinutoDesde.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        char car = e.getKeyChar();
        if ((car < '0' || car > '9'))
          e.consume();
      }
    });
    txtMinutoDesde.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent arg0) {

        Integer min = Integer.parseInt(txtMinutoDesde.getText());
        if (min > 59) {
          txtMinutoDesde.setText("59");
        } else if (min < 0) {
          txtMinutoDesde.setText("00");
        }

        if (txtMinutoDesde.getText().length() == 1) {
          txtMinutoDesde.setText(0 + txtMinutoDesde.getText());
        }
      }
    });
    txtMinutoDesde.setColumns(10);
    txtMinutoDesde.setBounds(319, 96, 32, 23);
    getContentPane().add(txtMinutoDesde);

    txtHoraHasta = new JTextField();
    txtHoraHasta.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        char car = e.getKeyChar();
        if ((car < '0' || car > '9'))
          e.consume();
      }
    });
    txtHoraHasta.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent arg0) {

        Integer hora = Integer.parseInt(txtHoraHasta.getText());
        if (hora > 23) {
          txtHoraHasta.setText("23");
        } else if (hora < 1) {
          txtHoraHasta.setText("01");
        }

        if (txtHoraHasta.getText().length() == 1) {
          txtHoraHasta.setText(0 + txtHoraHasta.getText());
        }
      }
    });
    txtHoraHasta.setColumns(10);
    txtHoraHasta.setBounds(277, 132, 32, 23);
    getContentPane().add(txtHoraHasta);

    txtMinutoHasta = new JTextField();
    txtMinutoHasta.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        char car = e.getKeyChar();
        if ((car < '0' || car > '9'))
          e.consume();
      }
    });
    txtMinutoHasta.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent arg0) {

        Integer min = Integer.parseInt(txtMinutoHasta.getText());
        if (min > 59) {
          txtMinutoHasta.setText("59");
        } else if (min < 0) {
          txtMinutoHasta.setText("00");
        }

        if (txtMinutoHasta.getText().length() == 1) {
          txtMinutoHasta.setText(0 + txtMinutoHasta.getText());
        }
      }
    });
    txtMinutoHasta.setColumns(10);
    txtMinutoHasta.setBounds(319, 131, 32, 23);
    getContentPane().add(txtMinutoHasta);

    JLabel lblNewLabel = new JLabel(":");
    lblNewLabel.setBounds(311, 96, 18, 20);
    getContentPane().add(lblNewLabel);

    label = new JLabel(":");
    label.setBounds(311, 132, 18, 20);
    getContentPane().add(label);
    
    btnAtras = new JButton();
    btnAtras.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent arg0) {
        VentanaBuscarVigencia vigencia = new VentanaBuscarVigencia();
        vigencia.setVisible(true);
        dispose();
      }
    });
    btnAtras.setIcon(new ImageIcon(VentanaRegistroVigencia.class.getResource("/imgs/back2.png")));
    btnAtras.setToolTipText("Atrás");
    btnAtras.setOpaque(false);
    btnAtras.setContentAreaFilled(false);
    btnAtras.setBorderPainted(false);
    btnAtras.setBounds(522, 191, 45, 25);
    getContentPane().add(btnAtras);
    Image img4 = ((ImageIcon) btnAtras.getIcon()).getImage();
    Image newimg4 = img4.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnAtras.setIcon(new ImageIcon(newimg4));
    // recuperarDatos();
    
    
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

        if(cmbPais.getSelectedIndex() != -1 && txtHoraDesde.getText().length() >0 && 
            txtMinutoDesde.getText().length() >0 && txtHoraHasta.getText().length() >0 && txtMinutoHasta.getText().length() >0){

        Item item3 = (Item) cmbPais.getSelectedItem();
        Integer distritoSelected = item3.getId();
        // if (!(txtNroZona.getText().length() == 0)) {
        // if (txtNroZona.getText().length() > 3) {
        // lblMensaje.setText("El codigo debe ser de maximo 3 caracteres.");
        // Timer t = new Timer(Login.timer, new ActionListener() {
        //
        // public void actionPerformed(ActionEvent e) {
        // lblMensaje.setText(null);
        // }
        // });
        // t.setRepeats(false);
        // t.start();
        // }
        // else
        if (vigenciaValidator.ValidarCodigo(distritoSelected) == false) {

          // Genero genero = new Genero();
          // genero.setDescripcion(textGenero.getText());

          String horaDesde = armarHoraMinuto(txtHoraDesde.getText(), txtMinutoDesde.getText());
          String fechaDesdeFinal = txtDesde.getText() + " " + horaDesde;

          String horaHasta = armarHoraMinuto(txtHoraHasta.getText(), txtMinutoHasta.getText());
          String fechaHastaFinal = txtHasta.getText() + " " + horaHasta;

          Calendar calendar = new GregorianCalendar();
          int year = calendar.get(Calendar.YEAR);

          if (vigenciaValidator.ValidarRango(fechaDesdeFinal, fechaHastaFinal) == false) {

            PaisDAO paisDAO = new PaisDAO();
            
             UcsawsVigenciaHorarioXPais vigenciaAguardar = new UcsawsVigenciaHorarioXPais();
             vigenciaAguardar.setIdEvento(VentanaBuscarEvento.eventoClase);
             vigenciaAguardar.setFchIns(new Date());
             vigenciaAguardar.setUsuarioIns(Login.nombreApellidoUserLogeado.toUpperCase());
             
             java.util.Date date = new Date(fechaDesdeFinal);
             DateTime desde = new DateTime(date);

             java.util.Date date2 = new Date(fechaHastaFinal);
             DateTime hasta = new DateTime(date2);
             
             vigenciaAguardar.setFchVigenciaDesde(date);
             vigenciaAguardar.setFchVigenciaHasta(date2);
             vigenciaAguardar.setIdPais(paisDAO.obtenerPaisById(distritoSelected));
             
             VigenciaDAO vigenciaDAO = new VigenciaDAO();
             if (vigenciaDAO.guardarVigencia(vigenciaAguardar)){

               // JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el genero.");
               lblMensaje.setText("Excelente, se ha guardado la vigencia.");
               Timer t = new Timer(Login.timer, new ActionListener() {

                 public void actionPerformed(ActionEvent e) {
                   lblMensaje.setText(null);
                 }
               });
               t.setRepeats(false);
               t.start();
               
               VentanaBuscarVigencia vigencia = new VentanaBuscarVigencia();
               vigencia.setVisible(true);
               dispose();
             }
             else{
               lblMensaje.setText("ERROR, No se ha podido Guardar.");
               Timer t = new Timer(Login.timer, new ActionListener() {

                 public void actionPerformed(ActionEvent e) {
                   lblMensaje.setText(null);
                 }
               });
               t.setRepeats(false);
               t.start();
             }




            // this.dispose();
          } else {
            // JOptionPane.showMessageDialog(null,
            // "Ya existe el genero " + txtDesc.getText(),
            // "Información",JOptionPane.WARNING_MESSAGE);
            lblMensaje.setText("Fuera de Rango.");
            Timer t = new Timer(Login.timer, new ActionListener() {

              public void actionPerformed(ActionEvent e) {
                lblMensaje.setText(null);
              }
            });
            t.setRepeats(false);
            t.start();
          }
        } else {
          // JOptionPane.showMessageDialog(null,
          // "Ya existe el genero " + txtDesc.getText(),
          // "Información",JOptionPane.WARNING_MESSAGE);
          lblMensaje.setText("Ya existe la vigencia para: " + cmbPais.getSelectedItem().toString());

          Timer t = new Timer(Login.timer, new ActionListener() {

            public void actionPerformed(ActionEvent ev) {
              lblMensaje.setText(null);
            }
          });
          t.setRepeats(false);
          t.start();
        }

        }
        else{
          lblMensaje.setText("Por Favor ingrese todos los campos.");

          Timer t = new Timer(Login.timer, new ActionListener() {

            public void actionPerformed(ActionEvent ev) {
              lblMensaje.setText(null);
            }
          });
          t.setRepeats(false);
          t.start();
        }

        // else {
        // // JOptionPane.showMessageDialog(null, ,
        // // "Información",JOptionPane.WARNING_MESSAGE);
        // lblMensaje.setText("Debe ingresar todos los campos.");
        // Timer t = new Timer(Login.timer, new ActionListener() {
        //
        // public void actionPerformed(ActionEvent e) {
        // lblMensaje.setText(null);
        // }
        // });
        // t.setRepeats(false);
        // t.start();
        // }
      } catch (Exception ex) {
        JXErrorPane.showDialog(ex);
        //JOptionPane.showMessageDialog(null, "Error al intentar insertar", "Error",
          //  JOptionPane.ERROR_MESSAGE);
      }

    }
    if (e.getSource() == botonCancelar) {
      VentanaBuscarVigencia candidato = new VentanaBuscarVigencia();
      candidato.setVisible(true);
      this.dispose();

    }
  }



  private Vector recuperarDatosComboBoxZona() {
    Vector model = new Vector();
    PaisDAO paisDAO = new PaisDAO();



    List<UcsawsPais> lista = new ArrayList<UcsawsPais>();
    try {
      lista = paisDAO.obtenerPaisByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));
    } catch (Exception e) {
      System.out.println(e);
    }

    if (lista.isEmpty()) {
      JOptionPane.showMessageDialog(null, "algo salio mal", "Advertencia",
          JOptionPane.WARNING_MESSAGE);
      // return lista;
    }

    else {

      // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
      Iterator<UcsawsPais> ite = lista.iterator();

      UcsawsPais aux;

      while (ite.hasNext()) {
        aux = ite.next();

        model.addElement(new Item(aux.getIdPais(), aux.getNombre()));

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
}
