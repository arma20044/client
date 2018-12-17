package src.main.java.login;

import hello.wsdl.AutenticarRequest;
import hello.wsdl.AutenticarResponse;
import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsRoles;
import entity.UcsawsUsers;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.Reportes;

import src.main.java.admin.utils.Close;
import src.main.java.admin.utils.StringEncrypter;
import src.main.java.admin.utils.Utilidades;
import src.main.java.dao.rol.RolDAO;
import src.main.java.hello.VentanaPrincipal;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

import java.awt.Color;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import java.awt.event.KeyAdapter;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Login extends javax.swing.JFrame implements KeyListener {

  public static String userLogeado;
  public static String nombreApellidoUserLogeado;
  public static String email;
  public static String rol;



  public static final Integer timer = 3000;

  public Login() {
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        System.out.println("holaaaaaa");
      }
    });

    setResizable(false);
    setIconImage(Toolkit.getDefaultToolkit()
        .getImage(Login.class.getResource("/imgs/paraguay.png")));
    initComponents();
    this.setBounds(0, 0, 417, 250);
    this.setLocationRelativeTo(null);
  }

  private UcsawsUsers autenticar() {
    boolean autenticado = false;
    try {
      String u = campoUsuario.getText();
      String p = campoContrasena.getText();

      UcsawsUsers logroAutenticarse = consultar(u, p);

      if (logroAutenticarse.getIdUser() != null) {
        System.out.println(u);
        System.out.println(p);

        // if(new
        // Usuarios().autenticar(campoUsuario.getText(),campoContrasena.getText()))

        // /
        // if(!campoUsuario.getText().equals(campoContrasena.getText()))
        // {
        autenticado = true;
        // JOptionPane.showMessageDialog(this,
        // "ADMIN - Inicio de sesion correcto",
        // "Correcto",
        // JOptionPane.INFORMATION_MESSAGE);

        // }
        // else {
        // cambioContrasena chgPwd = new
        // cambioContrasena(campoUsuario.getText());
        // chgPwd.setModal(true);
        // chgPwd.setVisible(true);
        return logroAutenticarse;
      } else if (logroAutenticarse.getIdUser() == null) {
        autenticado = true;
        // JOptionPane.showMessageDialog(this,
        // "VOTANTE - Inicio de sesion correcto",
        // "Correcto",
        // JOptionPane.INFORMATION_MESSAGE);
        // clases.utilidades.Global.usuario=campoUsuario.getText();

        return new UcsawsUsers();
      }

      // else
      // {
      // if(campoContrasena.getText().equals(clases.utilidades.MasterPwd.generarPassword()))
      // {
      // JOptionPane.showMessageDialog(null,
      // "Ingreso con una contraseña maestra");
      // autenticado = true;
      //
      // clases.utilidades.MasterPwd.bloquearPassword(clases.utilidades.MasterPwd.generarPassword());
      // }
      else {
        JOptionPane.showMessageDialog(this, "No se encuentra la combinación usuario y contraseña",
            "No se puede autenticar", JOptionPane.INFORMATION_MESSAGE);
      }

      // return "ERROR";
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "Se ha producido la siguiente excepción: " + e,
          "Se ha producido una excepción", JOptionPane.ERROR_MESSAGE);
    }
    return null;

    // return "ERROR2";
  }

  private void ingresar() {
    // clases.utilidades.Global.usuario=campoUsuario.getText();
    VentanaPrincipal miVentanaPrincipal;
    /* Instanciamos el objeto */
    miVentanaPrincipal = new VentanaPrincipal();
    /*
     * Enviamos el objeto como parametro para que sea unico en toda la aplicaci�n
     */
    miVentanaPrincipal.setVentanaPrincipal(miVentanaPrincipal);
    /* Hacemos que se cargue la ventana */
    miVentanaPrincipal.setVisible(true);
    this.dispose();

  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed"
  // desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel3 = new javax.swing.JLabel();
    labelUsuario = new javax.swing.JLabel();
    campoUsuario = new javax.swing.JTextField();

    campoUsuario.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent arg0) {
        if (campoUsuario.getText().length() == 0) {
          campoUsuario.setBackground(Color.decode("#FF0000"));
        } else if (campoUsuario.getText().length() > 0) {
          campoUsuario.setBackground(UIManager.getColor("info"));

        } else {
          campoUsuario.setBackground(Color.white);
        }
      }
    });
    labelContrasena = new javax.swing.JLabel();
    botonEntrar = new javax.swing.JButton();
    botonEntrar.setIcon(new ImageIcon(Login.class.getResource("/imgs/key.png")));
    btnAtras = new javax.swing.JButton();
    labelImagen = new javax.swing.JLabel();
    campoContrasena = new javax.swing.JPasswordField();
    campoContrasena.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent arg0) {
        if (campoContrasena.getText().length() == 0) {
          campoContrasena.setBackground(Color.decode("#FF0000"));
        } else {
          campoContrasena.setBackground(Color.white);
        }
      }

      @Override
      public void focusGained(FocusEvent arg0) {
        // char car = arg0.getKeyListeners().getKeyChar();

      }
    });
    labelTitulo = new javax.swing.JLabel();

    jLabel3.setText("jLabel3");

    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
        Close close = new Close();
        close.cerrarAplicacion(we);
      }
    });


    setTitle("Sistema E-vote: Paraguay Elecciones 2015");

    labelUsuario.setText("Usuario");

    campoUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        //campoUsuarioKeyPressed(evt);
       // loginDefinitivo();
      }
    });

    labelContrasena.setText("Contraseña");

    // botonEntrar.setIcon(new
    // ImageIcon(Login.class.getResource("/imgs/llaves.png"))); // NOI18N
    botonEntrar.setText("Entrar");
    botonEntrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
       /* try {
          if (campoUsuario.getText().length() > 0 && campoContrasena.getPassword().length > 0) {
            botonEntrarActionPerformed(evt);
          }
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }*/
        loginDefinitivo();
      }
    });

    btnAtras.setText("Atras");
    btnAtras.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        botonSalirActionPerformed(evt);
      }
    });

    labelImagen.setIcon(new ImageIcon(Login.class.getResource("/imgs/PadLock-128.png"))); // NOI18N

    campoContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        /*try {
          if (campoUsuario.getText().length() > 0 && campoContrasena.getPassword().length > 0) {

            campoContrasenaKeyPressed(evt);
          }
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }*/
       // loginDefinitivo();
      }
    });

    labelTitulo.setText("Ver. 1.0.0");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(
        layout
            .createSequentialGroup()
            .addContainerGap()
            .addComponent(labelImagen)
            .addGroup(
                layout
                    .createParallelGroup(Alignment.TRAILING)
                    .addGroup(
                        layout
                            .createSequentialGroup()
                            .addGap(18)
                            .addGroup(
                                layout
                                    .createParallelGroup(Alignment.LEADING)
                                    .addGroup(
                                        layout
                                            .createSequentialGroup()
                                            .addComponent(botonEntrar, GroupLayout.PREFERRED_SIZE,
                                                111, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(ComponentPlacement.RELATED)
                                            .addComponent(btnAtras, GroupLayout.PREFERRED_SIZE,
                                                102, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(
                                        Alignment.TRAILING,
                                        layout
                                            .createSequentialGroup()
                                            .addGroup(
                                                layout.createParallelGroup(Alignment.TRAILING)
                                                    .addComponent(labelContrasena)
                                                    .addComponent(labelUsuario))
                                            .addGap(18)
                                            .addGroup(
                                                layout
                                                    .createParallelGroup(Alignment.LEADING, false)
                                                    .addComponent(campoUsuario)
                                                    .addComponent(campoContrasena,
                                                        GroupLayout.DEFAULT_SIZE, 140,
                                                        Short.MAX_VALUE))))
                            .addContainerGap(26, GroupLayout.PREFERRED_SIZE))
                    .addGroup(
                        layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(labelTitulo).addContainerGap()))));
    layout.setVerticalGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(
        layout
            .createSequentialGroup()
            .addGap(36)
            .addGroup(
                layout
                    .createParallelGroup(Alignment.LEADING)
                    .addGroup(
                        layout
                            .createSequentialGroup()
                            .addGroup(
                                layout
                                    .createParallelGroup(Alignment.BASELINE)
                                    .addComponent(campoUsuario, GroupLayout.PREFERRED_SIZE, 25,
                                        GroupLayout.PREFERRED_SIZE).addComponent(labelUsuario))
                            .addGap(18)
                            .addGroup(
                                layout
                                    .createParallelGroup(Alignment.BASELINE)
                                    .addComponent(campoContrasena, GroupLayout.PREFERRED_SIZE, 25,
                                        GroupLayout.PREFERRED_SIZE).addComponent(labelContrasena))
                            .addGap(27)
                            .addGroup(
                                layout
                                    .createParallelGroup(Alignment.BASELINE)
                                    .addComponent(botonEntrar, GroupLayout.PREFERRED_SIZE, 37,
                                        GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAtras, GroupLayout.DEFAULT_SIZE, 37,
                                        Short.MAX_VALUE))
                            .addPreferredGap(ComponentPlacement.RELATED).addComponent(labelTitulo))
                    .addComponent(labelImagen, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 146,
                        GroupLayout.PREFERRED_SIZE)).addContainerGap()));
    getContentPane().setLayout(layout);

    pack();
    getRootPane().setDefaultButton(botonEntrar);
  }// </editor-fold>//GEN-END:initComponents

  private void botonEntrarActionPerformed(java.awt.event.ActionEvent evt) throws ParseException {// GEN-FIRST:event_botonEntrarActionPerformed

    UcsawsUsers autenticado = autenticar();
    // Boolean estaAutenticado = autenticar();

    if (autenticado.getIdUser() != null) { // admin

      userLogeado = campoUsuario.getText();



      nombreApellidoUserLogeado =
          autenticado.getUcsawsPersona().getNombre() + " "
              + autenticado.getUcsawsPersona().getApellido();
      email = autenticado.getUcsawsPersona().getEmail();
      /*
       * VentanaBuscar miVentanaPrincipal; Instanciamos el objeto miVentanaPrincipal= new
       * VentanaBuscar(); Enviamos el objeto como parametro para que sea unico en toda la aplicaci�n
       * //miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal); Hacemos que se cargue la ventana
       * miVentanaPrincipal.setVisible(true); this.dispose();
       */
      // VentanaRegistro miVentanaPrincipal;
      // /*Instanciamos el objeto*/
      // miVentanaPrincipal= new VentanaRegistro();
      // /*Enviamos el objeto como parametro para que sea unico
      // * en toda la aplicaci�n*/
      // //miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
      // /*Hacemos que se cargue la ventana*/
      // miVentanaPrincipal.setVisible(true);
      // this.dispose();
      MenuPrincipal menuPrincipal = new MenuPrincipal();
      /*
       * Enviamos el objeto como parametro para que sea unico en toda la aplicaci�n
       */
      // miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
      /* Hacemos que se cargue la ventana */
      menuPrincipal.setVisible(true);
      this.dispose();

    }

    else if (autenticado.getIdUser() == null) { // votante
      // VentanaPrincipal miVentanaPrincipal;
      // /*Instanciamos el objeto*/
      // miVentanaPrincipal= new VentanaPrincipal();
      // /*Enviamos el objeto como parametro para que sea unico
      // * en toda la aplicaci�n*/
      // miVentanaPrincipal.setVentanaPrincipal(miVentanaPrincipal);
      // /*Hacemos que se cargue la ventana*/
      // miVentanaPrincipal.setVisible(true);
      // this.dispose();
      JOptionPane.showMessageDialog(null, "Usuario o Contraseña erróneos.", "ERROR.",
          JOptionPane.INFORMATION_MESSAGE);

    }
    // )
    // {
    // ingresar();
    // }
  }// GEN-LAST:event_botonEntrarActionPerformed

  private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_botonSalirActionPerformed
    PreLogin pL = new PreLogin();
    pL.setVisible(true);
    this.dispose();
  }// GEN-LAST:event_botonSalirActionPerformed

  private void campoUsuarioKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_campoUsuarioKeyPressed
    if (evt.getKeyCode() == 10) {
      campoContrasena.requestFocus();
    }
  }// GEN-LAST:event_campoUsuarioKeyPressed

  private void campoContrasenaKeyPressed(java.awt.event.KeyEvent evt) throws ParseException {// GEN-FIRST:event_campoContrasenaKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) { 
      
      if (campoUsuario.getText().length() > 0 && campoContrasena.getPassword().length > 0) {
        
      
      UcsawsUsers autenticado = autenticar();

      if (autenticado.getIdUser() != null) { // admin

        userLogeado = campoUsuario.getText();



        nombreApellidoUserLogeado =
            autenticado.getUcsawsPersona().getNombre() + " "
                + autenticado.getUcsawsPersona().getApellido();
        email = autenticado.getUcsawsPersona().getEmail();

        MenuPrincipal menuPrincipal = new MenuPrincipal();
        /*
         * Enviamos el objeto como parametro para que sea unico en toda la aplicaci�n
         */
        // miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
        /* Hacemos que se cargue la ventana */
        menuPrincipal.setVisible(true);
        this.dispose();

      }

      else if (autenticado.getIdUser() == null) { // votante
        // VentanaPrincipal miVentanaPrincipal;
        // /*Instanciamos el objeto*/
        // miVentanaPrincipal= new VentanaPrincipal();
        // /*Enviamos el objeto como parametro para que sea unico
        // * en toda la aplicaci�n*/
        // miVentanaPrincipal.setVentanaPrincipal(miVentanaPrincipal);
        // /*Hacemos que se cargue la ventana*/
        // miVentanaPrincipal.setVisible(true);
        // this.dispose();
        JOptionPane.showMessageDialog(null, "Usuario o Contraseña erróneos.", "ERROR.",
            JOptionPane.INFORMATION_MESSAGE);

      }
      
    }
      else{
        JOptionPane.showMessageDialog(null,
            "Debe ingresar todos los campos.",
            "Error de Logeo",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }// GEN-LAST:event_campoContrasenaKeyPressed

  // public static void main(String args[]) {
  // try {
  // javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
  // } catch (ClassNotFoundException | InstantiationException |
  // IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
  // {
  // java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE,
  // null, ex);
  // }
  // java.awt.EventQueue.invokeLater(new Runnable() {
  // public void run() {
  // new Login().setVisible(true);
  // }
  // });
  // }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton botonEntrar;
  private javax.swing.JButton btnAtras;
  private javax.swing.JPasswordField campoContrasena;
  private javax.swing.JTextField campoUsuario;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel labelContrasena;
  private javax.swing.JLabel labelImagen;
  private javax.swing.JLabel labelTitulo;
  private javax.swing.JLabel labelUsuario;

  // End of variables declaration//GEN-END:variables

  public UcsawsUsers consultar(String usuario, String pass) throws Exception {

    try {
      List<String> usuarioPass = new ArrayList<String>();
      usuarioPass.add(usuario);
      usuarioPass.add(Utilidades.Encriptar(pass));

      // conver a Json inicio

      ObjectMapper mapperObj = new ObjectMapper();
      String jsonStr = "";
      try {
        // get Employee object as a json string
        jsonStr = mapperObj.writeValueAsString(usuarioPass);
        System.out.println(jsonStr);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      // convert a Json fin

      ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

      WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
      QueryGenericoRequest query = new QueryGenericoRequest();

      query.setTipoQueryGenerico(7);

      query.setQueryGenerico(jsonStr);

      QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
      weatherClient.printQueryGenericoResponse(response);

      ObjectMapper mapper = new ObjectMapper();
      String jsonInString = response.getQueryGenericoResponse();

      System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonInString));

      UcsawsUsers userDelWS = mapper.readValue(jsonInString, UcsawsUsers.class);

      if (userDelWS.getIdUser() != null) {
        // se autentica un admin
        return userDelWS;
      }

      else if (userDelWS.getIdUser() == null) {
        // se autentica un votante
        return userDelWS;
      }

      else {
        System.out.println("Connection Failed!");
        return null;

      }
    } catch (Exception e) {
      throw new Exception(e);
    }
    // return null;

  }

  @Override
  public void keyPressed(KeyEvent e) {
    System.out.println("keyPressed");

  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
      System.out.println("derecha");
    else if (e.getKeyCode() == KeyEvent.VK_LEFT)
      System.out.println("izq");
    else if (e.getKeyCode() == KeyEvent.VK_DOWN)
      System.out.println("abajo");
    else if (e.getKeyCode() == KeyEvent.VK_UP)
      System.out.println("arriba");

  }

  @Override
  public void keyTyped(KeyEvent e) {
    System.out.println("keyTyped");

  }


  void loginDefinitivo() {
     
      String result = "";
      if (campoUsuario.getText().length() > 0 && campoContrasena.getPassword().length > 0) {
        UcsawsUsers usuario =
            verificarCredenciales(campoUsuario.getText(), campoContrasena.getText());
        
        if(usuario.getIdUser() == null){
          JOptionPane.showMessageDialog(null,
              "Verifique el usuario y contraseña que ha ingresado.",
              "Error de Logeo",
              JOptionPane.ERROR_MESSAGE);
        }
        else{

        UcsawsRoles rol = verificarRoles(usuario);

        switch (rol.getCodigo()) {
          case "ADM":
            result = "ADM";
            break;

          case "COO":
            result = "COO";
            break;

          case "VOT":
            result = "VOT";
            break;

          case "CON":
            result = "CON";
            break;

          case "MIE":
            result = "MIE";
            break;
        }



        setearVariablesGlobales(usuario, result);
        
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        /*
         * Enviamos el objeto como parametro para que sea unico en toda la aplicaci�n
         */
        // miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
        /* Hacemos que se cargue la ventana */
        menuPrincipal.setVisible(true);
        this.dispose();
      
      }
      }
      else{
        JOptionPane.showMessageDialog(null,
            "Debe ingresar todos los campos.",
            "Error de Logeo",
            JOptionPane.ERROR_MESSAGE);
      }
  
  }

  UcsawsUsers verificarCredenciales(String u, String p) {
    UcsawsUsers result = new UcsawsUsers();
    try {
      result = consultar(u, p);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  UcsawsRoles verificarRoles(UcsawsUsers usuario) {

    RolDAO rolDAO = new RolDAO();
    UcsawsRoles rol = rolDAO.obtenerRolById(usuario.getIdRol().getIdRol().toString());

    return rol;

  }

  void setearVariablesGlobales(UcsawsUsers u, String r) {
    userLogeado = u.getUsuario();
    nombreApellidoUserLogeado = u.getUcsawsPersona().getDatosPersonales();
    email = u.getUcsawsPersona().getEmail();
    rol = r;
  }
}
