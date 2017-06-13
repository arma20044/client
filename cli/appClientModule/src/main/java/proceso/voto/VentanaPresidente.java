package src.main.java.proceso.voto;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import src.main.java.admin.utils.FechaUtils;
import src.main.java.dao.candidato.CandidatoDAO;
import src.main.java.login.EleccionMesa;
import entity.UcsawsCandidatos;
import entity.UcsawsListas;

public class VentanaPresidente extends JFrame implements ActionListener {
  
  public static boolean votoBlanco = false; 

  List<UcsawsCandidatos> resultA = new ArrayList<UcsawsCandidatos>();
  List<UcsawsCandidatos> resultB = new ArrayList<UcsawsCandidatos>();
  List<UcsawsCandidatos> resultC = new ArrayList<UcsawsCandidatos>();

  public static String presidente;
  public static UcsawsListas listaPresidente;

  private Container contenedor;/* declaramos el contenedor */
  JButton botonCambiar;/* declaramos el objeto Boton */
  JLabel labelTitulo;/* declaramos el objeto Label */
  private VentanaPresidente miVentanaPrincipal;

  // datos a enviar
  Integer cedula;
  Integer candidato;
  String lista;


  public Integer getCedula() {
    return cedula;
  }

  public void setCedula(Integer cedula) {
    this.cedula = cedula;
  }

  public Integer getCandidato() {
    return candidato;
  }

  public void setCandidato(Integer candidato) {
    this.candidato = candidato;
  }


  // datos a enviar

  public String getLista() {
    return lista;
  }

  public void setLista(String lista) {
    this.lista = lista;
  }

  public VentanaPresidente() {



    // List<UcsawsCandidatos> listaCandidatosPresidente = candidatosPresidente();

    List<List<UcsawsCandidatos>> listas = filtrarCandidatos(candidatosPresidente());


    getContentPane().setForeground(Color.WHITE);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setResizable(false);

    /* permite iniciar las propiedades de los componentes */
    iniciarComponentes();
    setTitle("Sistema E-vote - 2014");
    setSize(1089, 621);
    getContentPane().setLayout(null);

    JLabel lblNewLabel_1 = new JLabel("");
    lblNewLabel_1.setIcon(new ImageIcon(VentanaPresidente.class
        .getResource("/imgs/justicia electoral logo.jpg")));
    lblNewLabel_1.setBounds(0, 0, 117, 115);

    Image img6 = ((ImageIcon) lblNewLabel_1.getIcon()).getImage();
    Image newimg6 =
        img6.getScaledInstance(lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(),
            java.awt.Image.SCALE_SMOOTH);
    lblNewLabel_1.setIcon(new ImageIcon(newimg6));


    getContentPane().add(lblNewLabel_1);

    JLabel lblBoletinDeVoto = new JLabel("BOLETIN DE VOTO OFICIAL");
    lblBoletinDeVoto.setHorizontalAlignment(SwingConstants.CENTER);
    lblBoletinDeVoto.setFont(new Font("Tahoma", Font.BOLD, 25));
    lblBoletinDeVoto.setBounds(171, 24, 850, 31);
    getContentPane().add(lblBoletinDeVoto);

    Integer d = FechaUtils.obtenerAnhoDeDate(resultA.get(0).getIdEvento().getFchDesde());
    Integer h = d + 5;
    JLabel lblPeriodo = new JLabel("Periodo: "+ d +" - " +  h);
    lblPeriodo.setForeground(Color.GRAY);
    lblPeriodo.setHorizontalAlignment(SwingConstants.CENTER);
    lblPeriodo.setFont(new Font("Tahoma", Font.BOLD, 25));
    lblPeriodo.setBounds(171, 108, 850, 31);
    getContentPane().add(lblPeriodo);

    JLabel lblS = new JLabel("SELECCIONE LA CANDIDATURA DE SU PREFERENCIA");
    lblS.setHorizontalAlignment(SwingConstants.CENTER);
    lblS.setForeground(Color.BLACK);
    lblS.setFont(new Font("Tahoma", Font.BOLD, 20));
    lblS.setBounds(171, 150, 850, 25);
    getContentPane().add(lblS);
    /* Asigna un titulo a la barra de titulo */
    // setTitle("CoDejaVu : JFrame VentanaPrincipal");
    /* tama�o de la ventana */
    // setSize(300,180);
    /* pone la ventana en el Centro de la pantalla */
    setLocationRelativeTo(null);
  }

  /**
   * @param miVentana Enviamos una instancia de la ventana principal
   */
  public void setVentanaPrincipal(VentanaPresidente miVentana) {
    miVentanaPrincipal = miVentana;
  }

  private void iniciarComponentes() {

    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    contenedor = getContentPane();/* instanciamos el contenedor */
    /*
     * con esto definmos nosotros mismos los tama�os y posicion de los componentes
     */
    contenedor.setLayout(null);
    final ButtonGroup buttonGroup = new ButtonGroup();
    // VentanaPrincipal frmSistemaEvote = new VentanaPrincipal();


    JLabel lblNewLabel = new JLabel("CANDIDATOS A PRESIDENTE Y VICEPRESIDENTE DE LA REPUBLICA ");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
    lblNewLabel.setBounds(171, 66, 850, 31);
    getContentPane().add(lblNewLabel);

    JPanel panel = new JPanel();
    panel.setBounds(211, 204, 211, 290);
    getContentPane().add(panel);
    panel.setLayout(null);
    panel.setBorder(BorderFactory.createLoweredBevelBorder());



    JLabel lblFotoPrimerCandidatoPresidente = new JLabel("PresidenteA");
    lblFotoPrimerCandidatoPresidente.setIcon(new ImageIcon(VentanaPresidente.class
        .getResource("/candidatosJpeg/" + resultA.get(0).getIdPersona().getNombre()
            + resultA.get(0).getIdPersona().getApellido() + ".jpg")));
    // lblFotoPrimerCandidatoPresidente.setIcon(new ImageIcon());
    // lblHC.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/hc.jpg")));
    lblFotoPrimerCandidatoPresidente.setBounds(0, 0, 100, 141);

    Image img = ((ImageIcon) lblFotoPrimerCandidatoPresidente.getIcon()).getImage();
    Image newimg =
        img.getScaledInstance(lblFotoPrimerCandidatoPresidente.getWidth(),
            lblFotoPrimerCandidatoPresidente.getHeight(), java.awt.Image.SCALE_SMOOTH);
    lblFotoPrimerCandidatoPresidente.setIcon(new ImageIcon(newimg));

    panel.add(lblFotoPrimerCandidatoPresidente);
    // lblHC.setIcon(new ImageIcon(Main.class.getResource("/img/hc.jpg")));

    JLabel lblFotoPrimerCandidatoVicePresidente = new JLabel("VicePresidenteA");
    lblFotoPrimerCandidatoVicePresidente.setIcon(new ImageIcon(VentanaPresidente.class
        .getResource("/candidatosJpeg/" + resultA.get(1).getIdPersona().getNombre()
            + resultA.get(1).getIdPersona().getApellido() + ".jpg")));
    lblFotoPrimerCandidatoVicePresidente.setBounds(110, 0, 100, 141);

    Image img2 = ((ImageIcon) lblFotoPrimerCandidatoVicePresidente.getIcon()).getImage();
    Image newimg2 =
        img2.getScaledInstance(lblFotoPrimerCandidatoVicePresidente.getWidth(),
            lblFotoPrimerCandidatoVicePresidente.getHeight(), java.awt.Image.SCALE_SMOOTH);
    lblFotoPrimerCandidatoVicePresidente.setIcon(new ImageIcon(newimg2));

    panel.add(lblFotoPrimerCandidatoVicePresidente);
    // lblAfara.setIcon(new ImageIcon(Main.class.getResource("/img/afara.gif")));

    JLabel lblNombrePrimerCandidatoVicePresidente = new JLabel("candidato a vice");
    lblNombrePrimerCandidatoVicePresidente.setHorizontalAlignment(SwingConstants.CENTER);
    lblNombrePrimerCandidatoVicePresidente.setBounds(110, 140, 100, 15);
    lblNombrePrimerCandidatoVicePresidente.setText(resultA.get(1).getIdPersona().getNombre() + " "
        + resultA.get(1).getIdPersona().getApellido());
    panel.add(lblNombrePrimerCandidatoVicePresidente);
    lblNombrePrimerCandidatoVicePresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
    decreaseFontSize(lblNombrePrimerCandidatoVicePresidente);

    JLabel lblNombrePrimerCandidatoPresidente = new JLabel("candidato a presidente");
    lblNombrePrimerCandidatoPresidente.setHorizontalAlignment(SwingConstants.CENTER);
    lblNombrePrimerCandidatoPresidente.setBounds(0, 140, 100, 15);
    lblNombrePrimerCandidatoPresidente.setText(resultA.get(0).getIdPersona().getNombre() + " "
        + resultA.get(0).getIdPersona().getApellido());
    panel.add(lblNombrePrimerCandidatoPresidente);
    decreaseFontSize(lblNombrePrimerCandidatoPresidente);


    lblNombrePrimerCandidatoPresidente.setFont(new Font("Tahoma", Font.BOLD, 12));

    final JRadioButton rdbLista1 =
        new JRadioButton("LISTA " + resultA.get(0).getIdLista().getNroLista().toString());
    rdbLista1.setHorizontalAlignment(SwingConstants.CENTER);
    // rdbLista1.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent arg0) {
    // presidente = rdbLista1 .getText().substring(rdbLista1.getText().length()-1,
    // rdbLista1.getText().length());
    // System.out.println("PRESIDENTE: " + presidente);
    // }
    // });
    buttonGroup.add(rdbLista1);
    rdbLista1.setFont(new Font("Tahoma", Font.BOLD, 25));
    rdbLista1.setBounds(31, 173, 147, 39);
    panel.add(rdbLista1);

    JLabel label = new JLabel("Presidente");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setFont(new Font("Tahoma", Font.PLAIN, 10));
    label.setBounds(0, 155, 100, 13);
    panel.add(label);

    JLabel label_1 = new JLabel("Vicepresidente");
    label_1.setHorizontalAlignment(SwingConstants.CENTER);
    label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
    label_1.setBounds(110, 155, 100, 13);
    panel.add(label_1);

    JTextPane textPaneA = new JTextPane();
    textPaneA.setText(resultA.get(0).getIdLista().getNombreLista());
    textPaneA.setFont(new Font("Tahoma", Font.BOLD, 16));
    textPaneA.setEnabled(false);
    textPaneA.setEditable(false);
    textPaneA.setDisabledTextColor(Color.BLACK);
    textPaneA.setBounds(2, 214, 207, 74);
    panel.add(textPaneA);

    StyledDocument docA = textPaneA.getStyledDocument();
    docA.setParagraphAttributes(0, docA.getLength(), center, false);


    JPanel panel_1 = new JPanel();
    panel_1.setLayout(null);
    panel_1.setBounds(461, 204, 211, 290);
    getContentPane().add(panel_1);
    panel_1.setBorder(BorderFactory.createLoweredBevelBorder());

    JLabel lblFotoSegundoCandidatoPresidente = new JLabel("PresidenteB");
    lblFotoSegundoCandidatoPresidente.setIcon(new ImageIcon(VentanaPresidente.class
        .getResource("/candidatosJpeg/" + resultB.get(0).getIdPersona().getNombre()
            + resultB.get(0).getIdPersona().getApellido() + ".jpg")));
    // label.setIcon(new ImageIcon(Main.class.getResource("/img/ferre.jpg")));
    lblFotoSegundoCandidatoPresidente.setBounds(0, 0, 100, 141);

    Image img3 = ((ImageIcon) lblFotoSegundoCandidatoPresidente.getIcon()).getImage();
    Image newimg3 =
        img3.getScaledInstance(lblFotoSegundoCandidatoPresidente.getWidth(),
            lblFotoSegundoCandidatoPresidente.getHeight(), java.awt.Image.SCALE_SMOOTH);
    lblFotoSegundoCandidatoPresidente.setIcon(new ImageIcon(newimg3));

    panel_1.add(lblFotoSegundoCandidatoPresidente);

    JLabel lblFotoSegundoCandidatoVicePresidente = new JLabel("VicePresidenteB");
    lblFotoSegundoCandidatoVicePresidente.setIcon(new ImageIcon(VentanaPresidente.class
        .getResource("/candidatosJpeg/" + resultB.get(1).getIdPersona().getNombre()
            + resultB.get(1).getIdPersona().getApellido() + ".jpg")));
    // label_1.setIcon(new
    // ImageIcon(Main.class.getResource("/img/imagen-cynthia-brizuela-speratti.jpg")));
    lblFotoSegundoCandidatoVicePresidente.setBounds(110, 0, 100, 141);

    Image img4 = ((ImageIcon) lblFotoSegundoCandidatoVicePresidente.getIcon()).getImage();
    Image newimg4 =
        img4.getScaledInstance(lblFotoSegundoCandidatoVicePresidente.getWidth(),
            lblFotoSegundoCandidatoVicePresidente.getHeight(), java.awt.Image.SCALE_SMOOTH);
    lblFotoSegundoCandidatoVicePresidente.setIcon(new ImageIcon(newimg4));

    panel_1.add(lblFotoSegundoCandidatoVicePresidente);

    JLabel lblNombreSegundoCandidatoVicePresidente = new JLabel("vice 2");
    lblNombreSegundoCandidatoVicePresidente.setHorizontalAlignment(SwingConstants.CENTER);
    lblNombreSegundoCandidatoVicePresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
    lblNombreSegundoCandidatoVicePresidente.setBounds(110, 140, 100, 15);
    lblNombreSegundoCandidatoVicePresidente.setText(resultB.get(1).getIdPersona().getNombre() + " "
        + resultB.get(1).getIdPersona().getApellido());
    panel_1.add(lblNombreSegundoCandidatoVicePresidente);
    decreaseFontSize(lblNombreSegundoCandidatoVicePresidente);

    JLabel lblNombreSegundoCandidatoPresidente = new JLabel("candidato 2");
    lblNombreSegundoCandidatoPresidente.setHorizontalAlignment(SwingConstants.CENTER);
    lblNombreSegundoCandidatoPresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
    lblNombreSegundoCandidatoPresidente.setBounds(0, 140, 100, 15);
    lblNombreSegundoCandidatoPresidente.setText(resultB.get(0).getIdPersona().getNombre() + " "
        + resultB.get(0).getIdPersona().getApellido());
    panel_1.add(lblNombreSegundoCandidatoPresidente);
    decreaseFontSize(lblNombreSegundoCandidatoPresidente);

    final JRadioButton rdbLista2 =
        new JRadioButton("LISTA " + resultB.get(0).getIdLista().getNroLista().toString());
    rdbLista2.setHorizontalAlignment(SwingConstants.CENTER);
    // rdbLista2.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent arg0) {
    // presidente = rdbLista2 .getText().substring(rdbLista2.getText().length()-1,
    // rdbLista2.getText().length());
    // System.out.println("PRESIDENTE: " + presidente);
    // }
    // });
    buttonGroup.add(rdbLista2);
    rdbLista2.setFont(new Font("Tahoma", Font.BOLD, 25));
    rdbLista2.setBounds(26, 172, 147, 39);
    panel_1.add(rdbLista2);

    JLabel lblPresidente1 = new JLabel("Presidente");
    lblPresidente1.setHorizontalAlignment(SwingConstants.CENTER);
    lblPresidente1.setBounds(0, 155, 100, 13);
    panel_1.add(lblPresidente1);
    lblPresidente1.setFont(new Font("Tahoma", Font.PLAIN, 10));

    JLabel lblVice1 = new JLabel("Vicepresidente");
    lblVice1.setHorizontalAlignment(SwingConstants.CENTER);
    lblVice1.setBounds(110, 155, 100, 13);
    panel_1.add(lblVice1);
    lblVice1.setFont(new Font("Tahoma", Font.PLAIN, 10));

    JTextPane textPaneB = new JTextPane();
    textPaneB.setText(resultB.get(0).getIdLista().getNombreLista());
    textPaneB.setFont(new Font("Tahoma", Font.BOLD, 16));
    textPaneB.setEnabled(false);
    textPaneB.setEditable(false);
    textPaneB.setDisabledTextColor(Color.BLACK);
    textPaneB.setBounds(2, 214, 207, 74);
    panel_1.add(textPaneB);

    StyledDocument docB = textPaneB.getStyledDocument();
    docB.setParagraphAttributes(0, docB.getLength(), center, false);

    JPanel panel_2 = new JPanel();
    panel_2.setLayout(null);
    panel_2.setBounds(712, 204, 211, 290);
    getContentPane().add(panel_2);
    panel_2.setBorder(BorderFactory.createLoweredBevelBorder());

    JLabel lblFotoTercerCandidatoPresidente = new JLabel("PresidenteC");
    lblFotoTercerCandidatoPresidente.setIcon(new ImageIcon(VentanaPresidente.class
        .getResource("/candidatosJpeg/" + resultC.get(0).getIdPersona().getNombre()
            + resultC.get(0).getIdPersona().getApellido() + ".jpg")));
    // label_2.setIcon(new ImageIcon(Main.class.getResource("/img/alegre.jpg")));
    lblFotoTercerCandidatoPresidente.setBounds(0, 0, 100, 141);

    Image img5 = ((ImageIcon) lblFotoTercerCandidatoPresidente.getIcon()).getImage();
    Image newimg5 =
        img5.getScaledInstance(lblFotoTercerCandidatoPresidente.getWidth(),
            lblFotoTercerCandidatoPresidente.getHeight(), java.awt.Image.SCALE_SMOOTH);
    lblFotoTercerCandidatoPresidente.setIcon(new ImageIcon(newimg5));

    panel_2.add(lblFotoTercerCandidatoPresidente);

    JLabel lblFotoTercerCandidatoVicePresidente = new JLabel("VicePresidenteC");
    lblFotoTercerCandidatoVicePresidente.setIcon(new ImageIcon(VentanaPresidente.class
        .getResource("/candidatosJpeg/" + resultC.get(1).getIdPersona().getNombre()
            + resultC.get(1).getIdPersona().getApellido() + ".jpeg")));
    // label_3.setIcon(new ImageIcon(Main.class.getResource("/img/fili.jpg")));
    lblFotoTercerCandidatoVicePresidente.setBounds(110, 0, 100, 141);

    Image img6 = ((ImageIcon) lblFotoTercerCandidatoVicePresidente.getIcon()).getImage();
    Image newimg6 =
        img6.getScaledInstance(lblFotoTercerCandidatoVicePresidente.getWidth(),
            lblFotoTercerCandidatoVicePresidente.getHeight(), java.awt.Image.SCALE_SMOOTH);
    lblFotoTercerCandidatoVicePresidente.setIcon(new ImageIcon(newimg6));


    panel_2.add(lblFotoTercerCandidatoVicePresidente);

    JLabel lblNombreTercerCandidatoVicePresidente = new JLabel("vice 3");
    lblNombreTercerCandidatoVicePresidente.setHorizontalAlignment(SwingConstants.CENTER);
    lblNombreTercerCandidatoVicePresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
    lblNombreTercerCandidatoVicePresidente.setBounds(110, 140, 100, 15);
    lblNombreTercerCandidatoVicePresidente.setText(resultC.get(1).getIdPersona().getNombre() + " "
        + resultC.get(1).getIdPersona().getApellido());
    panel_2.add(lblNombreTercerCandidatoVicePresidente);
    decreaseFontSize(lblNombreTercerCandidatoVicePresidente);

    JLabel lblNombreTercerCandidatoPresidente = new JLabel("candidato 3");
    lblNombreTercerCandidatoPresidente.setHorizontalAlignment(SwingConstants.CENTER);
    lblNombreTercerCandidatoPresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
    lblNombreTercerCandidatoPresidente.setBounds(0, 140, 100, 15);
    lblNombreTercerCandidatoPresidente.setText(resultC.get(0).getIdPersona().getNombre() + " "
        + resultC.get(0).getIdPersona().getApellido());
    panel_2.add(lblNombreTercerCandidatoPresidente);
    decreaseFontSize(lblNombreTercerCandidatoPresidente);

    final JRadioButton rdbLista3 =
        new JRadioButton("LISTA " + resultC.get(0).getIdLista().getNroLista().toString());
    rdbLista3.setHorizontalAlignment(SwingConstants.CENTER);
    // rdbLista3.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent arg0) {
    // presidente = rdbLista3 .getText().substring(rdbLista3.getText().length()-1,
    // rdbLista3.getText().length());
    // System.out.println("PRESIDENTE: " + presidente);
    // }
    // });
    buttonGroup.add(rdbLista3);
    rdbLista3.setFont(new Font("Tahoma", Font.BOLD, 25));
    rdbLista3.setBounds(20, 172, 147, 39);
    panel_2.add(rdbLista3);

    JLabel label_2 = new JLabel("Presidente");
    label_2.setHorizontalAlignment(SwingConstants.CENTER);
    label_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
    label_2.setBounds(0, 155, 100, 13);
    panel_2.add(label_2);

    JLabel label_3 = new JLabel("Vicepresidente");
    label_3.setHorizontalAlignment(SwingConstants.CENTER);
    label_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
    label_3.setBounds(110, 155, 100, 13);
    panel_2.add(label_3);

    JTextPane textPaneC = new JTextPane();
    textPaneC.setText(resultC.get(0).getIdLista().getNombreLista());
    textPaneC.setFont(new Font("Tahoma", Font.BOLD, 16));
    textPaneC.setEnabled(false);
    textPaneC.setEditable(false);
    textPaneC.setDisabledTextColor(Color.BLACK);
    textPaneC.setBounds(2, 214, 207, 74);
    panel_2.add(textPaneC);

    StyledDocument docC = textPaneC.getStyledDocument();
    docC.setParagraphAttributes(0, docC.getLength(), center, false);


    // ver www.camick.com select button group
    final JButton botonCambiar = new JButton();
    botonCambiar.setText("Confirmar Voto");
    botonCambiar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evento) {

        JButton source = (JButton) evento.getSource();
        Integer ban = 0;
        if (evento.getSource() == botonCambiar) {
          // para saber que rdb se selecciono//
          JRadioButton[] rdb = {rdbLista1, rdbLista2, rdbLista3};
          for (int i = 0; i < rdb.length; i++) {
            if (rdb[i].isSelected()) {


              lista = rdb[i].getText();

              if (!lista.isEmpty()) {

                if (rdbLista1.isSelected()) {
                   
                  //presidente =    rdbLista1.getText().substring(rdbLista1.getText().length() - 1,  rdbLista1.getText().length());
                  listaPresidente = resultA.get(0).getIdLista();
                  System.out.println("PRESIDENTE A ID Lista " +listaPresidente.getNroLista() + "-"  +listaPresidente.getNombreLista());
                }

                else if (rdbLista2.isSelected()) {
                  
                 // presidente = rdbLista2.getText().substring(rdbLista2.getText().length() - 1,  rdbLista2.getText().length());
                  listaPresidente = resultB.get(0).getIdLista();
                  System.out.println("PRESIDENTE A ID Lista " +listaPresidente.getNroLista() + "-"  +listaPresidente.getNombreLista());
                }


                else if (rdbLista3.isSelected()) {
                  
                  //presidente =     rdbLista3.getText().length());
                  listaPresidente = resultC.get(0).getIdLista();
                  System.out.println("PRESIDENTE A ID Lista " +listaPresidente.getNroLista() + "-"  +listaPresidente.getNombreLista());
                }

                dispose();
                VentanaConfirmacionPresidente miVentanaConfirmacion =
                    new VentanaConfirmacionPresidente(miVentanaPrincipal, true);
                // if (lista.compareTo("LISTA 1")== 0){
                // miVentanaPrincipal.setCandidato(1);
                // }

                // miVentanaPrincipal.setLista(VentanaPresidente.presidente);
                // miVentanaPrincipal.setCedula(VentanaPrincipalVotante.cedulaVotante);
                // setear voto para lista


                ban = 1;
                break;
              }

              else {
                JOptionPane.showMessageDialog(new JFrame(), "Ocurrió un error", "Dialog",
                    JOptionPane.ERROR_MESSAGE);
              }



              // dispose();
            }


            // /////



          }
          if (ban == 0) {
            JOptionPane
                .showMessageDialog(
                    source,
                    "Debe seleccionar la lista que desea, si quiere votar en blanco favor clic en el Boton Votar Blanco");
          }
        }
      }
    });
    // btnNewButton.addActionListener(new ActionListener() {
    // public void actionPerformed(ActionEvent arg0) {
    // JButton source = (JButton) arg0.getSource();
    // if (rdbLista1.isSelected()) {
    // //guardar
    // Jd confirmar = new Jd();
    // confirmar.setModal(true);
    // confirmar.show();
    // //confirmar.setVisible(true);
    // }
    // else if (rdbLista3.isSelected()){
    // //guardar
    // Jd confirmar = new Jd();
    // confirmar.setModal(true);
    // confirmar.show();
    // //confirmar.setVisible(true);
    // }
    //
    //
    // else if (rdbLista4.isSelected() ){
    // //guardar
    // Jd confirmar = new Jd();
    // confirmar.setModal(true);
    // confirmar.show();
    // //confirmar.setVisible(true);
    //
    //
    // }
    // else{
    // JOptionPane.showMessageDialog(source,"Debe seleccionar la lista que desea, si quiere votar en blanco favor clic en el Boton Votar Blanco");
    // }
    // }
    // });
    botonCambiar.setFont(new Font("Tahoma", Font.PLAIN, 23));
    botonCambiar.setBounds(294, 537, 185, 37);
    getContentPane().add(botonCambiar);

    JButton btnNewButton_1 = new JButton("Votar BLANCO");
    btnNewButton_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        votoBlanco = true;
        dispose();
        VentanaConfirmacionPresidente miVentanaConfirmacion =
            new VentanaConfirmacionPresidente(miVentanaPrincipal, true);
      }
    });
    btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
    btnNewButton_1.setBounds(639, 537, 185, 37);
    getContentPane().add(btnNewButton_1);


  }

  /* Agregamos el evento al momento de llamar la otra ventana */
  public void actionPerformed(ActionEvent evento) {
    if (evento.getSource() == botonCambiar) {
      VentanaConfirmacionPresidente miVentanaConfirmacion =
          new VentanaConfirmacionPresidente(miVentanaPrincipal, true);
      miVentanaConfirmacion.setVisible(true);
    }
  }

  private List<UcsawsCandidatos> candidatosPresidente() {

    CandidatoDAO candidatoDAO = new CandidatoDAO();
    List<UcsawsCandidatos> candidatosPresidente =
        candidatoDAO.obtenerCandidatosByIdEvento(EleccionMesa.evento);

    Iterator<UcsawsCandidatos> ite = candidatosPresidente.iterator();

    UcsawsCandidatos aux;
    List<UcsawsCandidatos> result = new ArrayList<UcsawsCandidatos>();
    while (ite.hasNext()) {
      aux = ite.next();
      if (aux.getIdLista().getUcsawsTipoLista().getCodigo().compareTo("PRE") == 0) {

        result.add(aux);

      }

    }

    return result;

  }

  private List<List<UcsawsCandidatos>> filtrarCandidatos(List<UcsawsCandidatos> candidatos) {

    List<List<UcsawsCandidatos>> result = new ArrayList<List<UcsawsCandidatos>>();
    try {
      Iterator<UcsawsCandidatos> ite = candidatos.iterator();

      UcsawsCandidatos aux;

      int i = 0;
      while (ite.hasNext()) {
        aux = ite.next();
        if (i == 0) {
          if (aux.getDescripcion().compareTo("PRE") == 0) {
            resultA.add(aux);

          } else {
            if (aux.getDescripcion().compareTo("VICE") == 0) {
              resultA.add(aux);
              i = 2;
            }
          }
        } else if (i == 2) {
          if (aux.getDescripcion().compareTo("PRE") == 0) {
            resultB.add(aux);
            // i++;
          } else {
            if (aux.getDescripcion().compareTo("VICE") == 0) {
              resultB.add(aux);
              i = 4;
            }
          }

        } else if (i == 4) {
          if (aux.getDescripcion().compareTo("PRE") == 0) {
            resultC.add(aux);
            // i++;
          } else {
            if (aux.getDescripcion().compareTo("VICE") == 0) {
              resultC.add(aux);
              i = 6;
            }
          }
        }



      }
    } catch (Exception e) {
      System.out.println(e);
    }
    result.add(resultA);
    result.add(resultB);
    result.add(resultC);


    return result;
  }

  /**
   * Save bytes array to image file
   * 
   * @param chunk
   */
  public void Bytes2Image(byte[] chunk) {

    BufferedImage image;
    String formatName = "jpg";
    try {
      ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(chunk));
      Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
      if (iter.hasNext()) {
        ImageReader reader = (ImageReader) iter.next();
        reader.setInput(iis);
        formatName = reader.getFormatName();
      }
      image = ImageIO.read(new ByteArrayInputStream(chunk));

    } catch (IOException ex) {
      System.err.println(ex.getMessage());
    }
  }


  protected void decreaseFontSize(JLabel comp) {

    Font font = comp.getFont();
    FontMetrics fm = comp.getFontMetrics(font);
    int width = comp.getWidth();
    int height = comp.getHeight();
    int textWidth = fm.stringWidth(comp.getText());
    int textHeight = fm.getHeight();

    int size = font.getSize();
    while (size > 0 && (textHeight > height || textWidth > width)) {
      size -= 2;
      font = font.deriveFont(font.getStyle(), size);
      fm = comp.getFontMetrics(font);
      textWidth = fm.stringWidth(comp.getText());
      textHeight = fm.getHeight();
    }

    comp.setFont(font);

  }
}
