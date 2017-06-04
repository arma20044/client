package src.main.java.admin.roles;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.validator.GeneroValidator;
import src.main.java.admin.validator.RolValidator;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.rol.RolDAO;
import src.main.java.login.Login;
import entity.UcsawsRoles;

public class VentanaRegistroRoles extends JFrame implements ActionListener {

  private Coordinador miCoordinador; // objeto miCoordinador que permite la
  // relacion entre esta clase y la clase
  // coordinador
  private JLabel labelTitulo, lblMensaje;
  private JButton botonGuardar, botonCancelar;

  private RolesJTableModel model = new RolesJTableModel();

  private GeneroValidator generoValidator = new GeneroValidator();

  private String codTemporal = "";
  private JButton btnHome;

  List<Object[]> ciudades = new ArrayList<Object[]>();

  List<Object[]> listas = new ArrayList<Object[]>();

  List<Object[]> tcandidato = new ArrayList<Object[]>();
  private JLabel lblCodigo;
  private JTextField txtCodigo;
  private JTextField txtDescripcion;

  /**
   * constructor de la clase donde se inicializan todos los componentes de la ventana de registro
   */
  public VentanaRegistroRoles() {

    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e) {
        txtCodigo.requestFocus();
      }
    });

    botonGuardar = new JButton();
    botonGuardar.setToolTipText("Guardar");
    botonGuardar.setIcon(new ImageIcon(VentanaRegistroRoles.class.getResource("/imgs/save.png")));
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
    botonCancelar.setIcon(new ImageIcon(VentanaRegistroRoles.class.getResource("/imgs/back2.png")));
    botonCancelar.setBounds(674, 160, 32, 32);
    botonCancelar.setOpaque(false);
    botonCancelar.setContentAreaFilled(false);
    botonCancelar.setBorderPainted(false);
    Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
    Image newimg2 = img2.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonCancelar.setIcon(new ImageIcon(newimg2));


    labelTitulo = new JLabel();
    labelTitulo.setText("NUEVO ROL");
    labelTitulo.setBounds(269, 11, 380, 30);
    labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

    botonGuardar.addActionListener(this);
    botonCancelar.addActionListener(this);
    getContentPane().add(botonCancelar);
    getContentPane().add(botonGuardar);
    getContentPane().add(labelTitulo);
    limpiar();
    setSize(716, 223);
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
    btnHome.setIcon(new ImageIcon(VentanaRegistroRoles.class.getResource("/imgs/home.png")));
    btnHome.setBounds(0, 0, 32, 32);
    Image img = ((ImageIcon) btnHome.getIcon()).getImage();
    Image newimg = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnHome.setIcon(new ImageIcon(newimg));
    getContentPane().add(btnHome);

    lblCodigo = new JLabel();
    lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
    lblCodigo.setText("Codigo:");
    lblCodigo.setBounds(130, 52, 61, 25);
    getContentPane().add(lblCodigo);

    txtCodigo = new JTextField();
    txtCodigo.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent arg0) {
        char car = arg0.getKeyChar();
        if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z'))
          arg0.consume();
      }
    });

    txtCodigo.setBounds(213, 52, 75, 26);
    getContentPane().add(txtCodigo);
    txtCodigo.setColumns(10);

    lblMensaje = new JLabel("");
    lblMensaje.setForeground(Color.RED);
    lblMensaje.setBounds(213, 128, 454, 14);
    getContentPane().add(lblMensaje);

    JLabel lblDescripcion = new JLabel();
    lblDescripcion.setText("Descripcion:");
    lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
    lblDescripcion.setBounds(102, 82, 89, 25);
    getContentPane().add(lblDescripcion);

    txtDescripcion = new JTextField();
    txtDescripcion.setColumns(10);
    txtDescripcion.setBounds(213, 87, 310, 26);
    getContentPane().add(txtDescripcion);
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
        RolValidator rolValidator = new RolValidator();

        if (!(txtCodigo.getText().length() == 0) && !(txtDescripcion.getText().length() == 0)) {
          if (txtCodigo.getText().length() > 3) {
            lblMensaje.setText("El codigo debe ser de maximo 3 caracteres.");
            Timer t = new Timer(Login.timer, new ActionListener() {

              public void actionPerformed(ActionEvent e) {
                lblMensaje.setText(null);
              }
            });
            t.setRepeats(false);
            t.start();
          } else if

          (rolValidator.ValidarCodigo(txtCodigo.getText(), txtDescripcion.getText(),
              VentanaBuscarEvento.evento) == false) {

            EventoDAO eventoDAO = new EventoDAO();
            RolDAO rolDAO = new RolDAO();

            UcsawsRoles rolAGuardar = new UcsawsRoles();
            rolAGuardar.setCodigo(txtCodigo.getText().toUpperCase());
            rolAGuardar.setDescripcion(txtDescripcion.getText().toUpperCase());
            rolAGuardar.setFchIns(new Date());
            rolAGuardar.setUsuarioIns(Login.nombreApellidoUserLogeado.toUpperCase());
            rolAGuardar.setUcsawsEvento(eventoDAO.obtenerEventoById(VentanaBuscarEvento.evento));
            rolDAO.guardarRol(rolAGuardar);

            VentanaBuscarRoles roles = new VentanaBuscarRoles();
            roles.setVisible(true);
            dispose();



          } else {
            // JOptionPane.showMessageDialog(null,
            // "Ya existe el genero " + txtDesc.getText(),
            // "Información",JOptionPane.WARNING_MESSAGE);
            lblMensaje.setText("Ya existe el Rol con ese codigo. " + txtCodigo.getText());
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
        JOptionPane.showMessageDialog(null, "Error al intentar insertar", "Error",
            JOptionPane.ERROR_MESSAGE);
      }

    }
    if (e.getSource() == botonCancelar) {
      VentanaBuscarRoles candidato = new VentanaBuscarRoles();
      candidato.setVisible(true);
      this.dispose();

    }
  }



}
