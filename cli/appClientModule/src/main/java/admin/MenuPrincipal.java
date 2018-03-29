package src.main.java.admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import src.main.java.admin.acta.VentanaBuscarActa;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.Close;
import src.main.java.login.Login;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MenuPrincipal extends JFrame implements ActionListener {

  private Coordinador miCoordinador; // objeto miCoordinador que permite la relacion entre esta
                                     // clase y la clase coordinador

  public static boolean reporte;

  private JButton btnAdministracion, btnReportes;

  /**
   * Establece la informacion que se presentara como introduccion del sistema
   */
  public String textoIntroduccion = "";



  public MenuPrincipal() {

    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
        Close close = new Close();
        close.cerrarAplicacion(we);
      }
    });
    setIconImage(Toolkit.getDefaultToolkit().getImage(
        MenuPrincipal.class.getResource("/imgs/paraguay.png")));

    reporte = false;

    VentanaBuscarEvento.readOnly = false;

    setResizable(false);
    // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    textoIntroduccion =
        "Esta aplicaci�n presenta un ejemplo pr�ctico del patron "
            + "de dise�o MVC.\n\n"
            + "La aplicaci�n permite registrar, actualizar, buscar y eliminar registros de una tabla Persona."
            + "tambien son vinculados algunos conceptos de los Patrones Value Object y Data Access Objetc\n";

    setSize(670, 460);
    setTitle("Sistema E-vote: Paraguay Elecciones 2015");
    setLocationRelativeTo(null);
    getContentPane().setLayout(null);


    btnAdministracion = new JButton("Administracion");
    btnAdministracion.setHorizontalAlignment(SwingConstants.LEFT);
    btnAdministracion.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        VentanaBuscarEvento.evento = "";
        VentanaBuscarEvento ventanaBuscarEvento = new VentanaBuscarEvento();
        ventanaBuscarEvento.setVisible(true);
        dispose();
      }
    });
    btnAdministracion.setIcon(new ImageIcon(MenuPrincipal.class
        .getResource("/imgs/administracion.png")));
    btnAdministracion.setFont(new Font("Tahoma", Font.PLAIN, 21));
    btnAdministracion.setBounds(122, 169, 338, 73);
    getContentPane().add(btnAdministracion);

    JLabel lblTitulo = new JLabel();
    lblTitulo.setText("Menú Principal");
    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    lblTitulo.setFont(new Font("Verdana", Font.BOLD, 46));
    lblTitulo.setBounds(52, 87, 503, 86);
    getContentPane().add(lblTitulo);

    btnReportes = new JButton("Reportes");
    btnReportes.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaBuscarEvento.evento = "";

        reporte = true;
        VentanaBuscarEvento ventanaBuscarEvento = new VentanaBuscarEvento();
        ventanaBuscarEvento.setVisible(true);
        dispose();

      }
    });
    btnReportes.setHorizontalAlignment(SwingConstants.LEFT);
    btnReportes.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imgs/pfd64.png")));
    btnReportes.setFont(new Font("Tahoma", Font.PLAIN, 21));
    btnReportes.setBounds(122, 282, 338, 73);
    getContentPane().add(btnReportes);
    
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    panel.setBounds(10, 11, 619, 57);
    getContentPane().add(panel);
    
    JLabel label = new JLabel("Usuario logueado:");
    label.setFont(new Font("Tahoma", Font.BOLD, 11));
    label.setBounds(0, 0, 111, 26);
    panel.add(label);
    
    JLabel label_1 = new JLabel(Login.nombreApellidoUserLogeado);
    label_1.setBounds(143, 0, 405, 26);
    panel.add(label_1);
    

    /*
     * date a string formateado
     */
    // **
    String reportDate = "";
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    if (VentanaBuscarEvento.fechaDesde != null){
   
    reportDate = df.format(VentanaBuscarEvento.fechaDesde);
    // **
    }
    
    /*
     * date a string formateado
     */
    // **
    String reportDate2 = "";
    if (VentanaBuscarEvento.fechaHasta != null){
    DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    reportDate2 = df2.format(VentanaBuscarEvento.fechaHasta);
    // **
    }
    
    JLabel label_12 = new JLabel("Rol:");
    label_12.setFont(new Font("Tahoma", Font.BOLD, 11));
    label_12.setBounds(0, 33, 83, 14);
    panel.add(label_12);
    
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
    
    JLabel label_13 = new JLabel(r);
    label_13.setForeground(Color.BLACK);
    label_13.setBounds(40, 33, 191, 14);
    panel.add(label_13);
    // lblNombreDescripcion.repaint();


    btnAdministracion.addKeyListener(new MKeyListener());
    btnReportes.addKeyListener(new MKeyListener());



  }

  public void setCoordinador(Coordinador miCoordinador) {
    this.miCoordinador = miCoordinador;
  }


  public void actionPerformed(ActionEvent arg0) {
    // TODO Auto-generated method stub

  }

  class MKeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent event) {



      if (event.getKeyCode() == KeyEvent.VK_ENTER) {


        if (btnAdministracion.isFocusOwner() == true) {
          btnAdministracion.doClick();
          System.out
              .println("the button is: " + btnAdministracion.getText() + " btnAdministracion");
        }

        if (btnReportes.isFocusOwner() == true) {
          btnReportes.doClick();
          System.out.println("the button is: " + btnReportes.getText() + " btnReportes");
        }
      }
    }
  }
}
