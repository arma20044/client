package src.main.java.login;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsEvento;
import entity.UcsawsPersona;
import entity.UcsawsUsers;
import entity.UcsawsVotante;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.utils.Utilidades;
import src.main.java.admin.zona.VentanaBuscarZona;
import src.main.java.dao.votantesHabilitados.VotantesHabilitadosDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.proceso.voto.VentanaPresidente;
import src.main.java.votante.VentanaPrincipalVotante;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPasswordField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;

public class EleccionMesa extends JFrame {
  
  public static UcsawsPersona personaEntity = new UcsawsPersona();
  
  public static UcsawsVotante votante;

  private JPanel contentPane;

  public static Integer evento = // obtenerEventoVigente();
      Integer.parseInt(VentanaBuscarEvento.evento);

  public static Integer Mesa = 0;

  public static Integer habilitado;

  public static String local;

  public static Integer idEvento;
  private JLabel lblCIN;

  public static Integer idPersona = 0;

  List<Object[]> ciudades = new ArrayList<Object[]>();
  private JPasswordField pfPass;
  private JTextField txtUser;

  /**
   * Launch the application.
   */


  /**
   * Create the frame.
   */
  public EleccionMesa() {

    setUndecorated(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 354, 301);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(null);
    setContentPane(contentPane);

    this.setLocationRelativeTo(null);

    JButton btnAceptar = new JButton("Aceptar");
    btnAceptar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        if (!(txtUser.getText().isEmpty()) && !(pfPass.getText().isEmpty())) {

          if (verificarUsuPass(txtUser.getText(), pfPass.getText()) == true) { // verificar usuario
              if(idEvento == personaEntity.getIdEvento().getIdEvento()){                                                                 // y contraseña
            if (verificarDatos(idPersona) == false) { // verificar si ya voto

              if (verificarDatos2() == true) {


                if (habilitado == 1) {
                  
                   


                  VentanaPresidente main = new VentanaPresidente();
                  main.setVisible(true);

                  dispose();
                } else {
                  JOptionPane.showMessageDialog(null,
                      "VOTANTE - Ud. No está habilitado para votar.", "error",
                      JOptionPane.INFORMATION_MESSAGE);
                }
              }
            } else {
              JOptionPane.showMessageDialog(null, "revisar", "error",
                  JOptionPane.INFORMATION_MESSAGE);
            }
          } else {
            JOptionPane.showMessageDialog(null, "revisar", "Esta persona no está registrada en el evento.",
                JOptionPane.INFORMATION_MESSAGE);
          }
          }

          else {


          }

          // obtenerMesa

          // Mesa = Integer.parseInt((txtUser.getText()));

          // System.out.println("Mesa Seleccionada es: " + Mesa);

          // local = obtenerLocal(Mesa.toString());

          // idEvento = obtenerEvento(Integer.parseInt(local));


          // SE OMITE
          /*
           * VentanaPrincipalVotante votantePrincipal = new VentanaPrincipalVotante();
           * votantePrincipal.setVisible(true); dispose();
           */
        } else {
          txtUser.setBackground(Color.RED);

          JOptionPane.showMessageDialog(null, "VOTANTE - Ingrese usuario y contraseña",
              "Campos Vacíos", JOptionPane.INFORMATION_MESSAGE);

          txtUser.requestFocus();

        }
      }
    });
    btnAceptar.setBounds(89, 247, 89, 23);
    contentPane.add(btnAceptar);

    JLabel lblContrasenha = new JLabel("Contraseña:");
    lblContrasenha.setFont(new Font("Tahoma", Font.PLAIN, 18));
    lblContrasenha.setBounds(47, 128, 101, 37);
    contentPane.add(lblContrasenha);

    lblCIN = new JLabel("C.I.N° / DNI:");
    lblCIN.setFont(new Font("Tahoma", Font.PLAIN, 18));
    lblCIN.setBounds(49, 68, 117, 37);
    contentPane.add(lblCIN);

    pfPass = new JPasswordField();
    pfPass.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        if (pfPass.getText().trim().length() == 0) {
          pfPass.setBackground(Color.RED);
        } else {
          pfPass.setBackground(Color.WHITE);
        }
      }
    });
    pfPass.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent arg0) {
        if (pfPass.getText().trim().length() == 0) {
          pfPass.setBackground(Color.RED);
        } else {
          pfPass.setBackground(Color.WHITE);
        }
      }
    });
    pfPass.setBounds(178, 138, 137, 25);
    contentPane.add(pfPass);

    txtUser = new JTextField();
    txtUser.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent arg0) {
        if (txtUser.getText().trim().length() == 0) {
          txtUser.setBackground(Color.RED);
        } else {
          txtUser.setBackground(Color.WHITE);
        }
      }
    });
    txtUser.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent e) {

      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (txtUser.getText().trim().length() == 0) {
          txtUser.setBackground(Color.RED);
        } else {
          txtUser.setBackground(Color.WHITE);
        }
      }
    });
    txtUser.setBounds(178, 79, 137, 25);
    contentPane.add(txtUser);
    txtUser.setColumns(10);

    JButton btnVolver = new JButton("Volver");
    btnVolver.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        PreLogin pre = new PreLogin();
        pre.setVisible(true);
        dispose();
      }
    });
    btnVolver.setBounds(191, 247, 89, 23);
    contentPane.add(btnVolver);

    getRootPane().setDefaultButton(btnAceptar);
  }

  // Metodo para obtener el ID del evento
  private static Integer obtenerEventoVigente() {

    JSONArray filas = new JSONArray();
    JSONArray fil = new JSONArray();

    Object ob = null;

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    // para registrar se inserta el codigo es 1
    query.setTipoQueryGenerico(2);
    System.out.println(Login.userLogeado);
    query
        .setQueryGenerico("select id_evento, descripcion"
            + " from ucsaws_evento where to_char(current_timestamp , 'dd/mm/yyyy' ) between to_char(fch_desde, 'dd/mm/yyyy') "
            + " and to_char(fch_hasta, 'dd/mm/yyyy')");



    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    JSONParser j = new JSONParser();

    String generoAntesPartir = response.getQueryGenericoResponse();

    try {
      ob = j.parse(generoAntesPartir);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    filas = (JSONArray) ob;

    fil = (JSONArray) filas.get(0);

    String result = fil.get(0).toString();

    return Integer.parseInt(result);

  }

  // Metodo para obtener el ID del local
  private static String obtenerLocal(String nroMesa) {

    JSONArray filas = new JSONArray();
    JSONArray fil = new JSONArray();

    Object ob = null;

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    // para registrar se inserta el codigo es 1
    query.setTipoQueryGenerico(2);
    System.out.println(Login.userLogeado);
    query.setQueryGenerico("SELECT  l.id_local, nro_local,desc_local,nro_zona, desc_zona "
        + "from  ucsaws_local l join ucsaws_zona z on (l.id_zona = z.id_zona)"
        + " join ucsaws_distrito dis on (dis.id_distrito = z.id_distrito)"
        + " join ucsaws_departamento dep on (dep.id_departamento = dis.id_departamento)"
        + " join ucsaws_mesa mesa on (mesa.id_local = l.id_local)" + " where mesa.id_evento = "
        + evento + " and nro_mesa = " + nroMesa + " order by nro_zona , nro_local" + "");



    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    JSONParser j = new JSONParser();

    String generoAntesPartir = response.getQueryGenericoResponse();

    try {
      ob = j.parse(generoAntesPartir);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    filas = (JSONArray) ob;

    fil = (JSONArray) filas.get(0);

    String result = fil.get(0).toString();

    return result;

  }

  // Metodo para obtener el ID del evento
  private static Integer obtenerEvento(Integer idLocal) {

    JSONArray filas = new JSONArray();
    JSONArray fil = new JSONArray();

    Object ob = null;

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    // para registrar se inserta el codigo es 1
    query.setTipoQueryGenerico(2);
    System.out.println(Login.userLogeado);
    query.setQueryGenerico("SELECT  mesa.id_evento, desc_local "
        + "from  ucsaws_local l join ucsaws_mesa mesa on (mesa.id_local = l.id_local)"
        + " where l.id_local = " + idLocal);



    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    JSONParser j = new JSONParser();

    String generoAntesPartir = response.getQueryGenericoResponse();

    try {
      ob = j.parse(generoAntesPartir);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    filas = (JSONArray) ob;

    fil = (JSONArray) filas.get(0);

    String result = fil.get(0).toString();

    return Integer.parseInt(result);

  }

  private Vector recuperarDatosComboBoxPaisOrigen() {
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

    query.setQueryGenerico("SELECT id_pais, nombre" + " from ucsaws_pais where id_evento =  "
        + VentanaBuscarEvento.evento + " order by nombre");

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

      ciudades.add(fin);
      model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
      ite++;
    }
    return model;

  }


  private static Boolean verificarDatos(Integer idPersona) {

    Boolean result = false;



    JSONArray filas = new JSONArray();
    JSONArray fil = new JSONArray();

    Object ob = null;

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    // para registrar se inserta el codigo es 1
    query.setTipoQueryGenerico(6);
    System.out.println(Login.userLogeado);

    // query.setQueryGenerico("select id_votante, id_evento from ucsaws_votante v  "
    // + "where habilitado = 1 and sufrago = 1 and v.id_persona = " + idPersona + " " );

    query.setQueryGenerico(idPersona.toString());



    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    JSONParser j = new JSONParser();

    String generoAntesPartir = response.getQueryGenericoResponse();

    if ((generoAntesPartir.compareTo("SI") == 0)) {
      JOptionPane.showMessageDialog(null, "El usuario ya ha votado.", "ERROR.",
          JOptionPane.INFORMATION_MESSAGE);
      result = true;
      return result;
    } else {
      // try {
      // ob = j.parse(generoAntesPartir);
      // } catch (ParseException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      //
      // filas = (JSONArray) ob;
      //
      // fil = (JSONArray) filas.get(0);
      //
      // //Mesa = (Integer) fil.get(1);
      //
      // Mesa = (int) (long) fil.get(1);
      //
      // habilitado = (int) (long) fil.get(2);
      //
      // VentanaPrincipalVotante.idVotante = (int) (long) fil.get(3);

      // result = true;

      return result;

    }
  }

  private static Boolean verificarUsuPass(String user, String pass) {

    UcsawsUsers users = new UcsawsUsers();
    users.setUsuario(user);
    users.setPass(Utilidades.Encriptar(pass));
    // parseo json
    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(users);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }



    Boolean result = false;

    JSONArray filas = new JSONArray();
    JSONArray fil = new JSONArray();

    Object ob = null;

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    // para registrar se inserta el codigo es 1
    query.setTipoQueryGenerico(5);
    System.out.println(Login.userLogeado);
    // query.setQueryGenerico("select id_user, id_mesa, sufrago, v.id_votante  from ucsaws_users u join ucsaws_persona p on (u.id_persona = p.id_persona) join ucsaws_votante v on (v.id_persona = p.id_persona) "
    // + "where habilitado = 1 and sufrago = 0 and usuario = '" + user + "' and pass = '" + pass +
    // "'" );

    // query.setQueryGenerico("select id_user, id_persona, id_evento  from ucsaws_users u "
    // + "where usuario = '" + user + "' and pass = '" + pass + "'" );

    query.setQueryGenerico(jsonStr);



    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    JSONParser j = new JSONParser();

    String generoAntesPartir = response.getQueryGenericoResponse();

    if (generoAntesPartir.compareTo("NO") == 0) {
      JOptionPane.showMessageDialog(null,
          "El usuario y la constraseña no coinciden, revise sus datos.", "ERROR.",
          JOptionPane.INFORMATION_MESSAGE);
      return result;
    } else {
      // json string to obj
      ObjectMapper mapper = new ObjectMapper();
      String jsonInString = response.getQueryGenericoResponse();

      try {
        System.out
            .println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonInString));
      } catch (JsonGenerationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (JsonMappingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      try {
        UcsawsUsers u = mapper.readValue(jsonInString, UcsawsUsers.class);
        // result = u.getIdEvento().toString();
        personaEntity = u.getUcsawsPersona();
        idPersona = u.getUcsawsPersona().getIdPersona();
        idEvento = u.getIdEvento().getIdEvento();
        result = true;
      } catch (JsonParseException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } catch (JsonMappingException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }


      // idPersona = (int) (long) fil.get(1);

      // idEvento = (int) (long) fil.get(2);



      return result;

    }
  }

  private static Boolean verificarDatos2() {
    Boolean result = false;

    VotantesHabilitadosDAO votanteDAO = new VotantesHabilitadosDAO();
    votante =
        votanteDAO.obtenerVotanteByIdPersonaYEvento(idPersona.toString(), idEvento.toString());



    if (votante.getIdVotante() != null) {
      if(votante.getUcsawsMesa().getUcsawsLocal().getUcsawsZona().getUcsawsDistrito().getDescDistrito().toUpperCase().compareTo(PreLogin.MiPais.toUpperCase())!=0){
        JOptionPane.showMessageDialog(null, "El Votante no esta habilitado para Votar en éste País. " +  PreLogin.MiPais, "ERROR.",
            JOptionPane.INFORMATION_MESSAGE);
      }
      else{
      if (votante.getSufrago() == 1) {
        JOptionPane.showMessageDialog(null, "El Votante ya ha Votado.", "ERROR.",
            JOptionPane.INFORMATION_MESSAGE);
        return result;
      } else {



        Mesa = votante.getUcsawsMesa().getIdMesa();
        habilitado = votante.getHabilitado();
        Login.email = votante.getIdPersona().getEmail();
        VentanaPrincipalVotante.idVotante = votante.getIdVotante();
        result = true;



        // result = true;



      }
    } }else {
      JOptionPane.showMessageDialog(null, "El Votante No Existe. ", "ERROR.",
          JOptionPane.INFORMATION_MESSAGE);
    }
  
    return result;


  }

}
