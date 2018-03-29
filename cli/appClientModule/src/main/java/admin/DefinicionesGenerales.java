package src.main.java.admin;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import scr.main.java.admin.distrito.VentanaBuscarDistrito;
import src.main.java.admin.acta.VentanaBuscarActa;
import src.main.java.admin.candidato.VentanaBuscarCandidato;
import src.main.java.admin.departamento.VentanaBuscarDepartamento;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.evento.VentanaMainEvento;
import src.main.java.admin.genero.VentanaBuscarGenero;
import src.main.java.admin.listas.VentanaBuscarLista;
import src.main.java.admin.local.VentanaBuscarLocal;
import src.main.java.admin.mesa.VentanaBuscarMesa;
import src.main.java.admin.miembromesa.VentanaBuscarMiembroMesa;
import src.main.java.admin.nacionalidad.VentanaBuscarNacionalidad;
import src.main.java.admin.pais.VentanaBuscarPais;
import src.main.java.admin.persona.VentanaBuscarPersona;
import src.main.java.admin.roles.VentanaBuscarRoles;
import src.main.java.admin.tipoActa.VentanaBuscarTipoActa;
import src.main.java.admin.tipoCandidato.VentanaBuscarTipoCandidato;
import src.main.java.admin.tipoEvento.VentanaBuscarTipoEvento;
import src.main.java.admin.tipoLista.VentanaBuscarTipoLista;
import src.main.java.admin.tipoMiembroMesa.VentanaBuscarTipoMiembroMesa;
import src.main.java.admin.tipoMiembroMesa.VentanaRegistroTipoMiembroMesa;
import src.main.java.admin.users.VentanaBuscarUsers;
import src.main.java.admin.utils.Close;
import src.main.java.admin.vigencia.VentanaBuscarVigencia;
import src.main.java.admin.votantesHabilitados.VentanaBuscarVotantesHabilitados;
import src.main.java.admin.zona.VentanaBuscarZona;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jdesktop.swingx.JXButton;

public class DefinicionesGenerales extends JFrame implements ActionListener {

  private Coordinador miCoordinador; // objeto miCoordinador que permite la
  // relacion entre esta clase y la clase
  // coordinador
  private JLabel labelTitulo, labelSeleccion;
  private JButton btnAtras;
  private JButton btnGenero, btnPersonas, btnVotantesHabilitados, btnVigenciaHorarioXPais,
      btnDepartamentos, btnNacionalidades, btnPais, btnTipoEvento, btnTipoLista, btnListas,
      btnCandidatos, btnEscrutinio, btnHome;

  /**
   * Establece la informacion que se presentara como introduccion del sistema
   */
  public String textoIntroduccion = "";
  private JButton btnRoles;

  public DefinicionesGenerales() {
    setResizable(false);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);



    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
        Close close = new Close();
        close.cerrarAplicacion(we);
      }
    });

    btnAtras = new JButton();
    btnAtras.setIcon(new ImageIcon(DefinicionesGenerales.class.getResource("/imgs/volver.png")));
    btnAtras.setToolTipText("Atras...");
    btnAtras.setBounds(683, 474, 83, 51);

    labelSeleccion = new JLabel();
    labelSeleccion.setFont(new Font("Tahoma", Font.BOLD, 19));
    labelSeleccion.setText("Escoja que operacion desea realizar.");
    labelSeleccion.setBounds(93, 433, 580, 61);

    textoIntroduccion =
        "Esta aplicaci�n presenta un ejemplo pr�ctico del patron "
            + "de dise�o MVC.\n\n"
            + "La aplicaci�n permite registrar, actualizar, buscar y eliminar registros de una tabla Persona."
            + "tambien son vinculados algunos conceptos de los Patrones Value Object y Data Access Objetc\n";

    btnAtras.addActionListener(this);
    getContentPane().add(btnAtras);
    getContentPane().add(labelSeleccion);

    setSize(772, 554);
    setTitle("Sistema E-vote: Paraguay Elecciones 2015");
    setLocationRelativeTo(null);
    getContentPane().setLayout(null);

    btnGenero = new JButton("Generos");
    btnGenero.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent arg0) {
        labelSeleccion.setText("Aquí puede Gestionar los Generos del Sistema.");

      }

      @Override
      public void mouseExited(MouseEvent e) {
        // labelSeleccion.setText("Escoja que operacion desea realizar");
        cambiarEtiqueta();
      }
    });
    btnGenero.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarGenero genero = new VentanaBuscarGenero();
        genero.setVisible(true);
        dispose();
      }
    });
    btnGenero.setBounds(105, 182, 176, 23);
    getContentPane().add(btnGenero);



//    if (Login.rol.compareToIgnoreCase("ADM") == 0 || Login.rol.compareToIgnoreCase("MIE") == 0 ) {
//      btnGenero.setEnabled(true);
//    } else {
//      btnGenero.setEnabled(false);
//      btnGenero.setToolTipText("Solo los Administradores o Miembros de Mesa pueden acceder a ésta Área");
//    }

    btnPais = new JButton("Paises");
    btnPais.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar los Paises del Sistema.");
      }
    });
    btnPais.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarPais pais = new VentanaBuscarPais();
        pais.setVisible(true);
        dispose();
      }
    });
    btnPais.setBounds(105, 250, 176, 23);
    getContentPane().add(btnPais);

    btnListas = new JButton("Listas");
    btnListas.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar las Listas del Sistema.");
      }
    });
    btnListas.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarLista lista = new VentanaBuscarLista();
        lista.setVisible(true);
        dispose();
      }
    });
    btnListas.setBounds(105, 284, 176, 23);
    getContentPane().add(btnListas);

    btnPersonas = new JButton("Personas");
    btnPersonas.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar las Personas del Sistema.");
      }
    });
    btnPersonas.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        VentanaBuscarPersona persona = new VentanaBuscarPersona();
        persona.setVisible(true);
        dispose();

      }
    });
    btnPersonas.setBounds(320, 182, 146, 23);
    getContentPane().add(btnPersonas);

//    if (Login.rol.compareToIgnoreCase("ADM") == 0) {
//      btnPersonas.setEnabled(true);
//    } else {
//      btnPersonas.setEnabled(false);
//      btnPersonas.setToolTipText("Solo los Administradores pueden acceder a ésta Área");
//    }

    btnDepartamentos = new JButton("Departamentos");
    btnDepartamentos.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar los Departamentos del Sistema.");
      }
    });
    btnDepartamentos.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarDepartamento departamento = new VentanaBuscarDepartamento();
        departamento.setVisible(true);
        dispose();
      }
    });
    btnDepartamentos.setBounds(320, 216, 146, 23);
    getContentPane().add(btnDepartamentos);

    labelTitulo = new JLabel();
    labelTitulo.setIcon(new ImageIcon(DefinicionesGenerales.class.getResource("/imgs/def.png")));
    labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    labelTitulo.setText("Definiciones Generales");
    labelTitulo.setBounds(10, 96, 712, 86);
    labelTitulo.setFont(new Font("Verdana", Font.BOLD, 46));
    getContentPane().add(labelTitulo);

    btnCandidatos = new JButton("Candidatos");
    btnCandidatos.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar los Candidatos del Sistema.");
      }
    });
    btnCandidatos.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarCandidato candidato;
        candidato = new VentanaBuscarCandidato();
        candidato.setVisible(true);
        dispose();

      }
    });
    btnCandidatos.setBounds(320, 284, 146, 23);
    getContentPane().add(btnCandidatos);

    btnVotantesHabilitados = new JButton("Votantes Habilitados");
    btnVotantesHabilitados.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar los Votantes Habilitados del Sistema.");
      }
    });
    btnVotantesHabilitados.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarVotantesHabilitados votantesHabilitados =
            new VentanaBuscarVotantesHabilitados();
        votantesHabilitados.setVisible(true);
        dispose();
      }
    });
    btnVotantesHabilitados.setBounds(497, 182, 176, 23);
    getContentPane().add(btnVotantesHabilitados);
    
//    if (Login.rol.compareToIgnoreCase("MIE") == 0) {
//      btnVotantesHabilitados.setEnabled(true);
//    } else {
//      btnVotantesHabilitados.setEnabled(false);
//      btnVotantesHabilitados.setToolTipText("Solo los Administradores pueden acceder a ésta Área");
//    }

    btnTipoEvento = new JButton("Tipo Evento");
    btnTipoEvento.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar los Tipos de Evento del Sistema.");
      }
    });
    btnTipoEvento.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarTipoEvento tevento;
        tevento = new VentanaBuscarTipoEvento();
        tevento.setVisible(true);
        dispose();
      }
    });
    btnTipoEvento.setBounds(320, 250, 146, 23);
    getContentPane().add(btnTipoEvento);

    btnVigenciaHorarioXPais = new JButton("Vigencia Horario por Pais");
    btnVigenciaHorarioXPais.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar los Horarios por Paises del Sistema.");
      }
    });
    btnVigenciaHorarioXPais.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarVigencia vigencia = new VentanaBuscarVigencia();
        vigencia.setVisible(true);
        dispose();
      }
    });
    btnVigenciaHorarioXPais.setBounds(105, 216, 176, 23);
    getContentPane().add(btnVigenciaHorarioXPais);

    btnNacionalidades = new JButton("Nacionalidades\r\n");
    btnNacionalidades.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar las Nacionalidades del Sistema.");
      }
    });
    btnNacionalidades.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarNacionalidad nacionalidad = new VentanaBuscarNacionalidad();
        nacionalidad.setVisible(true);
        dispose();
      }
    });
    btnNacionalidades.setBounds(497, 216, 176, 23);
    getContentPane().add(btnNacionalidades);

    btnHome = new JButton("");
    btnHome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        MenuPrincipal menuprincipal = new MenuPrincipal();
        menuprincipal.setVisible(true);
        dispose();
      }
    });
    btnHome.setSelectedIcon(null);
    btnHome.setToolTipText("Inicio");
    btnHome.setBounds(0, 0, 32, 32);
    getContentPane().add(btnHome);
    btnHome.setIcon(new ImageIcon(VentanaBuscarEvento.class.getResource("/imgs/home.png")));
    btnHome.setBounds(0, 0, 32, 32);
    Image img5 = ((ImageIcon) btnHome.getIcon()).getImage();
    Image newimg5 = img5.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnHome.setIcon(new ImageIcon(newimg5));
    getContentPane().add(btnHome);

    JPanel panel = new JPanel();
    panel.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    panel.setBounds(49, 0, 619, 85);
    getContentPane().add(panel);
    panel.setLayout(null);

    JLabel lblNombre = new JLabel("Usuario logueado:");
    lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblNombre.setBounds(0, 0, 111, 26);
    panel.add(lblNombre);

    JLabel lblNombreDescripcion = new JLabel(Login.nombreApellidoUserLogeado);
    lblNombreDescripcion.setBounds(143, 0, 405, 26);
    panel.add(lblNombreDescripcion);

    JLabel label = new JLabel("Nro. Evento:");
    label.setFont(new Font("Tahoma", Font.BOLD, 11));
    label.setBounds(0, 25, 79, 14);
    panel.add(label);

    JLabel label_1 = new JLabel(VentanaBuscarEvento.nroEvento);
    label_1.setBounds(77, 25, 60, 14);
    panel.add(label_1);
    label_1.setForeground(Color.BLACK);

    JLabel label_2 = new JLabel("Descripcion Evento:");
    label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
    label_2.setBounds(0, 37, 111, 14);
    panel.add(label_2);

    JLabel label_3 = new JLabel(VentanaBuscarEvento.descripcionEvento);
    label_3.setBounds(114, 37, 200, 14);
    panel.add(label_3);
    label_3.setForeground(Color.BLACK);

    JLabel label_4 = new JLabel("Fecha Desde:");
    label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
    label_4.setBounds(317, 25, 83, 14);
    panel.add(label_4);

    /*
     * date a string formateado
     */
    // **
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String reportDate = df.format(VentanaBuscarEvento.fechaDesde);
    // **

    JLabel label_5 = new JLabel(reportDate);
    label_5.setBounds(410, 25, 191, 14);
    panel.add(label_5);
    label_5.setForeground(Color.BLACK);



    /*
     * date a string formateado
     */
    // **
    DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String reportDate2 = df.format(VentanaBuscarEvento.fechaHasta);
    // **

    JLabel lblFechaHasta = new JLabel("Fecha Hasta:");
    lblFechaHasta.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblFechaHasta.setBounds(317, 37, 83, 14);
    panel.add(lblFechaHasta);

    JLabel label_7 = new JLabel(reportDate2);
    label_7.setBounds(410, 37, 191, 14);
    panel.add(label_7);
    label_7.setForeground(Color.BLACK);

    JLabel label_8 = new JLabel("Tipo Evento:");
    label_8.setFont(new Font("Tahoma", Font.BOLD, 11));
    label_8.setBounds(0, 50, 79, 14);
    panel.add(label_8);

    JLabel label_9 = new JLabel(VentanaBuscarEvento.tipoEventoDescripcon);
    label_9.setBounds(87, 50, 196, 14);
    panel.add(label_9);
    label_9.setForeground(Color.BLACK);

    JLabel lblRol = new JLabel("Rol:");
    lblRol.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblRol.setBounds(317, 50, 83, 14);
    panel.add(lblRol);
    
    String r = "";
    if (Login.rol.compareToIgnoreCase("ADM") == 0) {
      r = "ADMINISTRADOR";
      
    } else if (Login.rol.compareToIgnoreCase("COO") == 0) {
      r = "COORDINADOR";
    }

    else if (Login.rol.compareToIgnoreCase("VOT") == 0) {
      r = "VOTANTE";
    }

    else if (Login.rol.compareToIgnoreCase("CON") == 0) {
      r = "CONSULTA";
    }

    else if (Login.rol.compareToIgnoreCase("MIE") == 0) {
      r = "MIEMBRO DE MESA";
    }

    JLabel label_6 = new JLabel(r);
    label_6.setForeground(Color.BLACK);
    label_6.setBounds(410, 50, 191, 14);
    panel.add(label_6);

    btnEscrutinio = new JButton("Escrutinio");
    btnEscrutinio.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar el Escrutinio del Sistema.");
      }
    });
    btnEscrutinio.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Escrutinio escru = new Escrutinio();
        escru.setVisible(true);
        dispose();
      }
    });
    btnEscrutinio.setBounds(105, 363, 176, 23);
    getContentPane().add(btnEscrutinio);

   if (Login.rol.compareToIgnoreCase("COO") == 0) {
      btnEscrutinio.setEnabled(true);
    } else {
      btnEscrutinio.setEnabled(false);
     btnEscrutinio.setToolTipText("Solo los Coordinadores pueden acceder a ésta Área");
    }


    btnTipoLista = new JButton("Tipo Lista");
    btnTipoLista.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar los Tipo de Lista del Sistema.");
      }
    });
    btnTipoLista.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarTipoLista tLista = new VentanaBuscarTipoLista();
        tLista.setVisible(true);
        dispose();
      }
    });
    btnTipoLista.setBounds(497, 250, 176, 23);
    getContentPane().add(btnTipoLista);
    // lblNombreDescripcion.repaint();

    btnGenero.addKeyListener(new MKeyListener());
    btnPersonas.addKeyListener(new MKeyListener());
    btnVotantesHabilitados.addKeyListener(new MKeyListener());
    btnVigenciaHorarioXPais.addKeyListener(new MKeyListener());
    btnDepartamentos.addKeyListener(new MKeyListener());
    btnNacionalidades.addKeyListener(new MKeyListener());
    btnPais.addKeyListener(new MKeyListener());
    btnTipoEvento.addKeyListener(new MKeyListener());
    btnTipoLista.addKeyListener(new MKeyListener());
    btnListas.addKeyListener(new MKeyListener());
    btnCandidatos.addKeyListener(new MKeyListener());
    btnEscrutinio.addKeyListener(new MKeyListener());
    btnAtras.addKeyListener(new MKeyListener());
    btnHome.addKeyListener(new MKeyListener());


    // btnGenero.requestFocus();
    getRootPane().setDefaultButton(btnHome);

    JButton bntUsers = new JButton("Usuarios");
    bntUsers.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar los Usuarios del Sistema.");
      }
    });
    bntUsers.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        VentanaBuscarUsers u = new VentanaBuscarUsers();
        u.setVisible(true);
        dispose();
      }
    });
    bntUsers.setBounds(320, 363, 146, 23);
    getContentPane().add(bntUsers);

    btnRoles = new JButton("Roles");
    btnRoles.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        // labelSeleccion.setText("Escoja que operacion desea realizar");
        cambiarEtiqueta();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        labelSeleccion.setText("Aquí puede Gestionar los Roles del Sistema.");
      }
    });
    btnRoles.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        VentanaBuscarRoles roles = new VentanaBuscarRoles();
        roles.setVisible(true);
        dispose();
      }
    });
    btnRoles.setBounds(497, 363, 176, 23);
    getContentPane().add(btnRoles);

    JXButton btnTipoActa = new JXButton();
    btnTipoActa.setText("Tipo Acta");
    btnTipoActa.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent arg0) {
        VentanaBuscarTipoActa tipoActa = new VentanaBuscarTipoActa();
        tipoActa.setVisible(true);
        dispose();

      }

      @Override
      public void mouseEntered(MouseEvent arg0) {
        labelSeleccion.setText("Aquí puede Gestionar los Tipos de Acta del Sistema.");
      }

      @Override
      public void mouseExited(MouseEvent e) {
        cambiarEtiqueta();
      }
    });
    btnTipoActa.setBounds(497, 284, 176, 23);
    getContentPane().add(btnTipoActa);

    JXButton btnTipoMiembroMesa = new JXButton();
    btnTipoMiembroMesa.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent arg0) {
        VentanaBuscarTipoMiembroMesa consulta = new VentanaBuscarTipoMiembroMesa();
        consulta.setVisible(true);
        dispose();
      }
    });
    btnTipoMiembroMesa.setText("Tipo Miembro Mesa");
    btnTipoMiembroMesa.setBounds(497, 318, 176, 23);
    getContentPane().add(btnTipoMiembroMesa);

    JButton btnActa = new JButton("Actas");
    btnActa.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        VentanaBuscarActa acta = new VentanaBuscarActa();
        acta.setVisible(true);
        dispose();
      }
    });
    btnActa.setBounds(105, 318, 176, 23);
    getContentPane().add(btnActa);

  }

  public void setCoordinador(Coordinador miCoordinador) {
    this.miCoordinador = miCoordinador;
  }

  public void actionPerformed(ActionEvent arg0) {
    VentanaBuscarEvento mainEvento = new VentanaBuscarEvento();
    mainEvento.setVisible(true);
    dispose();
    // TODO Auto-generated method stub

  }

  class MKeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent event) {



      if (event.getKeyCode() == KeyEvent.VK_ENTER) {


        if (btnGenero.isFocusOwner() == true) {
          btnGenero.doClick();
          System.out.println("the button is: " + btnGenero.getText() + " btnGenero");
        }

        if (btnPersonas.isFocusOwner() == true) {
          btnPersonas.doClick();
          System.out.println("the button is: " + btnPersonas.getText() + " btnPersonas");
        }

        if (btnVotantesHabilitados.isFocusOwner() == true) {
          btnVotantesHabilitados.doClick();
          System.out.println("the button is: " + btnVotantesHabilitados.getText()
              + " btnVotantesHabilitados");
        }

        if (btnVigenciaHorarioXPais.isFocusOwner() == true) {
          btnVigenciaHorarioXPais.doClick();
          System.out.println("the button is: " + btnVigenciaHorarioXPais.getText()
              + " btnVigenciaHorarioXPais");
        }

        if (btnDepartamentos.isFocusOwner() == true) {
          btnDepartamentos.doClick();
          System.out.println("the button is: " + btnDepartamentos.getText() + " btnDepartamentos");
        }


        if (btnNacionalidades.isFocusOwner() == true) {
          btnNacionalidades.doClick();
          System.out
              .println("the button is: " + btnNacionalidades.getText() + " btnNacionalidades");
        }

        if (btnPais.isFocusOwner() == true) {
          btnPais.doClick();
          System.out.println("the button is: " + btnPais.getText() + " btnPais");
        }

        // ****

        if (btnTipoEvento.isFocusOwner() == true) {
          btnTipoEvento.doClick();
          System.out.println("the button is: " + btnTipoEvento.getText() + " btnTipoEvento");
        }


        if (btnTipoLista.isFocusOwner() == true) {
          btnTipoLista.doClick();
          System.out.println("the button is: " + btnTipoLista.getText() + " btnTipoLista");
        }


        if (btnListas.isFocusOwner() == true) {
          btnListas.doClick();
          System.out.println("the button is: " + btnListas.getText() + " btnListas");
        }


        if (btnCandidatos.isFocusOwner() == true) {
          btnCandidatos.doClick();
          System.out.println("the button is: " + btnCandidatos.getText() + " btnCandidatos");
        }

        if (btnEscrutinio.isFocusOwner() == true) {
          btnEscrutinio.doClick();
          System.out.println("the button is: " + btnEscrutinio.getText() + " btnEscrutinio");
        }


      }
      if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
        btnAtras.doClick();
      }

      if (event.getKeyCode() == KeyEvent.VK_HOME) {
        btnHome.doClick();
      }
    }
  }

  private void cambiarEtiqueta() {
    labelSeleccion.setText("Escoja que operacion desea realizar.");

  }
}
