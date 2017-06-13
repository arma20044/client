package src.main.java.proceso.voto;

import hello.wsdl.ConsultarRequest;
import hello.wsdl.ConsultarResponse;
import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;
import hello.wsdl.VotarRequest;
import hello.wsdl.VotarResponse;
import hello.wsdl.Voto;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import scr.main.java.admin.mail.SendEmailGenerico;
import src.main.java.dao.voto.VotoDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.EleccionMesa;
import src.main.java.login.Login;
import src.main.java.votante.VentanaPrincipalVotante;
import entity.UcsawsListas;
import entity.UcsawsTipoLista;
import entity.UcsawsVotante;
import entity.UcsawsVotos;

@Transactional(readOnly = true)
public class VentanaConfirmacionDiputados extends JDialog {
  private Container contenedor;
  JLabel labelTitulo;
  private JLabel lblListaPresidente, lblListaSenador, lblListaDiputado, lblMensaje, lblCargar;

  // public static Integer idLocal;

  public static Integer idEvento;

  public static Integer idMesa;

  public VentanaConfirmacionDiputados(final VentanaDiputados miVentanaPrincipal, boolean modal) {
    getContentPane().setLayout(null);

    JLabel label = new JLabel("Está seguro?");
    label.setFont(new Font("Tahoma", Font.PLAIN, 40));
    label.setBounds(80, 24, 225, 49);
    getContentPane().add(label);

    JButton button = new JButton("SI");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        final SwingWorker worker = new SwingWorker() {

          @Override
          protected Object doInBackground() throws Exception {

            if (procesoVotacion()) {
              return null;
            } else {
              return null;

            }

          }
        };
        worker.execute();
      }
    });
    button.setBounds(127, 227, 43, 23);
    getContentPane().add(button);

    JButton button_1 = new JButton("NO");
    button_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaDiputados diputados = new VentanaDiputados();
        diputados.setVisible(true);
        dispose();
      }
    });
    button_1.setBounds(233, 227, 47, 23);
    getContentPane().add(button_1);

    lblListaPresidente = new JLabel();

    lblListaPresidente.setBounds(29, 95, 276, 14);
    lblListaPresidente.setText("Presidente Lista: " + VentanaPresidente.listaPresidente.getNroLista() + " - "+ VentanaPresidente.listaPresidente.getNombreLista());
    getContentPane().add(lblListaPresidente);

    lblListaSenador = new JLabel();

    lblListaSenador.setBounds(29, 120, 276, 14);
    lblListaSenador.setText("Senador Lista: " + VentanaSenadores.listaSenador.getNroLista() + " - " + VentanaSenadores.listaSenador.getNombreLista());
    getContentPane().add(lblListaSenador);

    lblListaDiputado = new JLabel();

    lblListaDiputado.setBounds(29, 147, 264, 14);
    lblListaDiputado.setText("Diputado Lista: " + VentanaDiputados.listaDiputado.getNroLista() + " - " + VentanaDiputados.listaDiputado.getNombreLista());
    getContentPane().add(lblListaDiputado);
    /*
     * Al llamar al constructor super(), le enviamos el JFrame Padre y la propiedad booleana que
     * determina que es hija
     */
    // super(miVentanaPrincipal, modal);
    setAlwaysOnTop(true);
    setResizable(false);
    setModal(true);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    getContentPane().setLayout(null);
    // contenedor=getContentPane();
    // contenedor.setLayout(null);
    // Asigna un titulo a la barra de titulo
    setTitle("Confirmar?");

    // labelTitulo= new JLabel();
    // labelTitulo.setText("VENTANA DE CONFIRMACION");
    // labelTitulo.setBounds(20, 20, 180, 23);

    // contenedor.add(labelTitulo);
    // tama�o de la ventana
    setSize(446, 305);
    // pone la ventana en el Centro de la pantalla
    // setLocationRelativeTo(null);
    // setLocationRelativeTo(null);

    setBounds(100, 100, 335, 305);

    JLabel lblEstSeguro = new JLabel("Está seguro?");
    lblEstSeguro.setFont(new Font("Tahoma", Font.PLAIN, 40));

    JButton btnNewButton = new JButton("SI");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }
    });

    JButton btnNewButton_1 = new JButton("NO");
    btnNewButton_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        dispose();
        VentanaDiputados diputados = new VentanaDiputados();
        diputados.setVisible(true);
      }
    });
    getContentPane().setLayout(null);
    getContentPane().add(label);
    getContentPane().add(button);
    getContentPane().add(button_1);
    getContentPane().add(lblListaPresidente);
    getContentPane().add(lblListaSenador);
    getContentPane().add(lblListaDiputado);

    lblMensaje = new JLabel("");
    lblMensaje.setForeground(Color.RED);
    lblMensaje.setFont(UIManager.getFont("Label.font"));
    lblMensaje.setBounds(80, 172, 199, 32);
    getContentPane().add(lblMensaje);

    lblCargar = new JLabel("");
    lblCargar.setIcon(new ImageIcon(VentanaConfirmacionDiputados.class
        .getResource("/imgs/hourglass.gif")));
    lblCargar.setBounds(29, 68, 264, 147);
    getContentPane().add(lblCargar);
    lblCargar.setVisible(false);

    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void guardar(Voto voto) throws Exception {
    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    ConsultarRequest consulta = new ConsultarRequest();

    consulta.setCedula(voto.getNombre());
    consulta.setOrigenPeticion(1);

    ConsultarResponse response = weatherClient.getConsultarResponse(consulta);
    weatherClient.printResponse(response);
  }

  private void votar(Voto voto) throws Exception {
    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    VotarRequest votar = new VotarRequest();

    votar.setCedula(voto.getNombre());
    votar.setOrigenPeticion(1);
    votar.setCandidato(voto.getCanditato().toString());

    VotarResponse response = weatherClient.getVotarResponse(votar);
    weatherClient.printVotoResponse(response);
  }

  // Metodo para obtener el ID de lista que fue seleccionado
  private Integer obtenerLista(int tipoLista, Integer lista) {
    String result = "";
    List<Integer> l = new ArrayList();
    l.add(tipoLista);
    l.add(lista);
    l.add(EleccionMesa.evento);

    // parseo json
    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(l);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    JSONArray filas = new JSONArray();
    JSONArray fil = new JSONArray();

    Object ob = null;

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    // para registrar se inserta el codigo es 1
    query.setTipoQueryGenerico(13);
    System.out.println(Login.userLogeado);

    // query.setQueryGenerico("select id_lista, nombre_lista"
    // + " from ucsaws_listas" + " where nro_lista = " + lista
    // + " and id_tipo_lista = " + tipoLista);

    query.setQueryGenerico(jsonStr);

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    JSONParser j = new JSONParser();

    String generoAntesPartir = response.getQueryGenericoResponse();

    if (generoAntesPartir.compareTo("NO") == 0) {

      // JOptionPane.showMessageDialog(null,
      // "LA consulta arrojo vacio!!!.");;
      lblMensaje.setText("Lista: LA consulta arrojo vacio!!!.");
    }

    else {
      // json string to java object;
      ObjectMapper mapper = new ObjectMapper();
      String jsonInString = generoAntesPartir;
      // UcsawsListas listar = new UcsawsListas();
      try {
        UcsawsListas listar = mapper.readValue(jsonInString, UcsawsListas.class);
        result = listar.getIdLista().toString();
      } catch (JsonParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (JsonMappingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      // return result;

    }
    return Integer.parseInt(result);

  }

  // Metodo para obtener el ID de la Mesa en donde se voto
  private Integer obtenerMesa(int nroMesa, Integer idEvento, Integer idLocal) {

    JSONArray filas = new JSONArray();
    JSONArray fil = new JSONArray();

    Object ob = null;

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    // para registrar se inserta el codigo es 1
    query.setTipoQueryGenerico(2);
    System.out.println(Login.userLogeado);
    query.setQueryGenerico("select id_mesa, desc_mesa, id_local, id_evento" + " from ucsaws_mesa"
        + " where nro_mesa = " + nroMesa + " and id_evento = " + idEvento + " and  id_local = "
        + idLocal);

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

    idLocal = Integer.parseInt(fil.get(2).toString());

    idEvento = Integer.parseInt(fil.get(3).toString());

    String result = fil.get(0).toString();

    return Integer.parseInt(result);

  }

  private void votar(UcsawsListas lista, UcsawsVotante votante) {

 
    
    Date today = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    try {
      today = sdf.parse(sdf.format(today));
    } catch (java.text.ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    UcsawsVotos votoAGuardar = new UcsawsVotos();
    votoAGuardar.setFchIns(today);
    votoAGuardar.setUsuarioIns("sistema");
    votoAGuardar.setIdLista(lista);
    votoAGuardar.setIdMesa(votante.getUcsawsMesa());
    
    
     VotoDAO votoDAO = new VotoDAO();
     votoDAO.guardarVoto(votoAGuardar);

  }

  private void votarBlanco(Integer idTipoLista, Integer idMesa) {

    List<Integer> l = new ArrayList<Integer>();

    l.add(idTipoLista);
    l.add(idMesa);
    l.add(EleccionMesa.evento);

    // parseo json
    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(l);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // insertar voto inicioS
    Calendar calendar = new GregorianCalendar();
    int year = calendar.get(Calendar.YEAR);

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    // para registrar se inserta el codigo es 1
    query.setTipoQueryGenerico(1);
    System.out.println(Login.userLogeado);

    query.setQueryGenerico(jsonStr);

    /*
     * query.setQueryGenerico(
     * "INSERT INTO ucsaws_votos_blanco ( id_voto_blanco, id_tipo_lista, id_mesa,usuario_ins,fch_ins, usuario_upd, fch_upd, id_evento) "
     * + "VALUES (nextval('ucsaws_voto_blanco') , " + idTipoLista + "," + idMesa + " , '" +
     * Login.userLogeado + "' , now(), '" + Login.userLogeado + "' , now()," + EleccionMesa.idEvento
     * + ")");
     */

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    // insertar votoS fin
    if (response.getQueryGenericoResponse().compareToIgnoreCase("NO") == 0) {

    } else if (response.getQueryGenericoResponse().compareToIgnoreCase("SI") == 0) {

    }

  }

  private void actualizarVotante(Integer idVotante) {

    // parseo json
    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(idVotante);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // actualizar situacion votante inicio
    Calendar calendar = new GregorianCalendar();
    int year = calendar.get(Calendar.YEAR);

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    // para registrar se inserta el codigo es 1
    // by default sufrago es 0 cuando no votó, al votar cambia a 1
    query.setTipoQueryGenerico(29);
    System.out.println(Login.userLogeado);

    query.setQueryGenerico(jsonStr);

    /*
     * query.setQueryGenerico("UPDATE ucsaws_votante" +
     * " SET sufrago = 1,  fch_upd = now() ,  usuario_upd= 'ucsavoto' " + " WHERE  id_votante = " +
     * VentanaPrincipalVotante.idVotante + " and id_mesa = " + idMesa);
     */

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    if (response.getQueryGenericoResponse().compareToIgnoreCase("SI") == 0) {

    } else if (response.getQueryGenericoResponse().compareToIgnoreCase("SI") == 0) {

    }

    // actualizar situacion votante fin

  }

  private UcsawsTipoLista obtenerTipoListaPorCodigo(String codigo, String nroListaSeleccionada) {

    List<String> l = new ArrayList<String>();
    l.add(codigo);
    l.add(nroListaSeleccionada);
    l.add(EleccionMesa.evento.toString());

    // parseo json
    ObjectMapper mapperObj = new ObjectMapper();
    String jsonStr = "";
    try {
      // get Employee object as a json string
      jsonStr = mapperObj.writeValueAsString(l);
      System.out.println(jsonStr);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
    QueryGenericoRequest query = new QueryGenericoRequest();

    query.setTipoQueryGenerico(21);
    System.out.println(Login.userLogeado);

    query.setQueryGenerico(jsonStr);

    /*
     * query.setQueryGenerico("UPDATE ucsaws_votante" +
     * " SET sufrago = 1,  fch_upd = now() ,  usuario_upd= 'ucsavoto' " + " WHERE  id_votante = " +
     * VentanaPrincipalVotante.idVotante + " and id_mesa = " + idMesa);
     */

    QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
    weatherClient.printQueryGenericoResponse(response);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = response.getQueryGenericoResponse();
    UcsawsTipoLista tipoLista = new UcsawsTipoLista();
    try {
      tipoLista = mapper.readValue(jsonInString, UcsawsTipoLista.class);
    } catch (JsonParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return tipoLista;

  }

  @Transactional
  private Boolean procesoVotacion() {
    int ite = 0;
    // JFrame frame = new JFrame("Test");
    // new JLabel("Aguarde... ", new
    // ImageIcon(VentanaConfirmacionDiputados.class.getResource("/imgs/hourglass.gif")),
    // JLabel.CENTER);
    // JLabel lblCargar = new JLabel();
    lblCargar.setVisible(true);
    // lblCargar.setBounds(80, 120, 141, 14);
    // lblCargar.setText("Senador Lista: " +
    // VentanaSenadores.senadores);
    // getContentPane().add(lblCargar);
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // frame.setSize(200, 200);
    // frame.setLocationRelativeTo(getParent());
    // frame.setLocationRelativeTo(null);
    // frame.setLocation(x, y);
    // frame.setUndecorated(true);
    // while (ite < 10000){
    // ite = ite + 1;
    // ejemploFrame.getTextField().setText("" + ite);

    // frame.setVisible(true);
    // }

    // idMesa = obtenerMesa(EleccionMesa.Mesa,
    // EleccionMesa.evento,
    // Integer.parseInt(EleccionMesa.local));

    idMesa = EleccionMesa.Mesa;

    System.out.println("Presidente Seleccionado Lista N° : " + VentanaPresidente.listaPresidente.getNombreLista());
    System.out.println("Senador Seleccionado Lista N° : " + VentanaSenadores.listaSenador.getNombreLista());
    System.out.println("Presidente Seleccionado Lista N° : " + VentanaDiputados.listaDiputado.getNombreLista());

    Integer idTipoListaPresidente = VentanaPresidente.listaPresidente.getUcsawsTipoLista().getIdTipoLista(); 
  //Integer idTipoListaPresidente = obtenerTipoListaPorCodigo("PRE", VentanaPresidente.presidente).getIdTipoLista();
    
    Integer idTipoListaSenador = VentanaSenadores.listaSenador.getUcsawsTipoLista().getIdTipoLista();
  //Integer idTipoListaSenador =  obtenerTipoListaPorCodigo("SEN", VentanaSenadores.senadores).getIdTipoLista();
    
    Integer idTipoListaDiputado = VentanaDiputados.listaDiputado.getUcsawsTipoLista().getIdTipoLista();
  //Integer idTipoListaDiputado =  obtenerTipoListaPorCodigo("DIP", VentanaDiputados.diputados).getIdTipoLista();

    if ((idTipoListaPresidente == null || idTipoListaPresidente == 0)
        || (idTipoListaSenador == null || idTipoListaSenador == 0)
        || (idTipoListaDiputado == null || idTipoListaDiputado == 0)) {

      lblMensaje.setText("ERROR");
     // JOptionPane.showMessageDialog(null, "ERROR", "InfoBox: " + " Ocurrio un error, revisar log.",
       //   JOptionPane.ERROR_MESSAGE);
      return false;
    } else {

      if (VentanaPresidente.votoBlanco == false  )  {
        // Integer idListaPresidete = obtenerLista(1,
        // Integer
        // .parseInt(VentanaPresidente.presidente));
        try {
          votar(VentanaPresidente.listaPresidente, EleccionMesa.votante);
          System.out.println("Se voto presidente");
        } catch (Exception e) {
          System.out.println(e);
        }
      } else {

        // Integer idTipoLista =
        // obtenerTipoListaPorCodigo("PRE").getIdTipoLista();
        // // para presidente
        try {
          votarBlanco(idTipoListaPresidente, idMesa);
          System.out.println("Se voto presidente en blanco");
        } catch (Exception e) {
          System.out.println(e);
        }
      }

      if (VentanaSenadores.votoBlanco == false ) {
        // Integer idListaSenador = obtenerLista(3, Integer
        // .parseInt(VentanaSenadores.senadores));
        try {
          votar(VentanaSenadores.listaSenador, EleccionMesa.votante);
          System.out.println("Se voto Senador");
        } catch (Exception e) {
          System.out.println(e);
        }
      } else {
        // Integer idTipoLista =
        // obtenerTipoListaPorCodigo("SEN").getIdTipoLista();
        // // para senador
        try {
          votarBlanco(idTipoListaSenador, idMesa);
          System.out.println("Se voto senador en blanco");
        } catch (Exception e) {
          System.out.println(e);
        }
      }

      if (VentanaDiputados.votoBlanco == false ) {
        // Integer idListaDiputado = obtenerLista(2, Integer
        // .parseInt(VentanaDiputados.diputados));
        try {
          votar(VentanaDiputados.listaDiputado, EleccionMesa.votante);
          System.out.println("Se voto Diputado");
        } catch (Exception e) {
          System.out.println(e);
        }
      } else {
        // Integer idTipoLista =
        // obtenerTipoListaPorCodigo("DIP").getIdTipoLista();
        // // para diputado
        try {
          votarBlanco(idTipoListaDiputado, idMesa);
          System.out.println("Se voto diputado en blanco");
        } catch (Exception e) {
          System.out.println(e);
        }
      }

      try {
        actualizarVotante(VentanaPrincipalVotante.idVotante);
        // eliminarVotanteHabilitado();

        // enviar notifiacion
        // SendEmailGenerico enviarNotificacion = new
        // SendEmailGenerico();
        // enviarNotificacion.enviarNotificacion(Login.email);
        SendEmailGenerico.NewEnviar(Login.email);
      } catch (Exception e) {
        System.out.println(e);
      }
      VentanaVotoFinal end = new VentanaVotoFinal();
      end.setVisible(true);
      dispose();
      // frame.setVisible(false);
      lblCargar.setVisible(false);
      return true;
    }
  }
}
