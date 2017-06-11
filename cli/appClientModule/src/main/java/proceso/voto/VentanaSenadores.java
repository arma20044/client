package src.main.java.proceso.voto;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import src.main.java.dao.candidato.CandidatoDAO;
import src.main.java.login.EleccionMesa;
import entity.UcsawsCandidatos;

public class VentanaSenadores extends JFrame implements ActionListener {

  public static String senadores;
  
  List<UcsawsCandidatos> listaSenadores;

  private Container contenedor;/* declaramos el contenedor */
  JButton botonCambiar;/* declaramos el objeto Boton */
  JLabel labelTitulo;/* declaramos el objeto Label */
  private VentanaSenadores miVentanaPrincipal;
  final JRadioButton rdbList1, rdbList2, rdbList3, rdbList4, rdbList5, rdbList6, rdbList7,
      rdbList8;
  ButtonGroup buttonGroup;
  // datos a enviar
  Integer cedula;
  Integer candidato;
  String lista;
  private ButtonGroup buttonGroup_1;


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

  public VentanaSenadores() {
    
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    
    listaSenadores = candidatosSenadores();
    
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setResizable(false);

    /* permite iniciar las propiedades de los componentes */
    iniciarComponentes();
    setTitle("Sistema E-vote - 2014");
    setSize(1201, 763);
    getContentPane().setLayout(null);

    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBounds(900, 192, 203, 234);
    getContentPane().add(panel);

    JLabel lblAlanTuring = new JLabel("SenadorD");
    lblAlanTuring.setIcon(new ImageIcon(VentanaSenadores.class
        .getResource("/candidatosJpeg/"+listaSenadores.get(3).getIdPersona().getNombre()+listaSenadores.get(3).getIdPersona().getApellido() +".jpg")));
    lblAlanTuring.setBounds(0, 0, 100, 141);

    Image img = ((ImageIcon) lblAlanTuring.getIcon()).getImage();
    Image newimg =
        img.getScaledInstance(lblAlanTuring.getWidth(), lblAlanTuring.getHeight(),
            java.awt.Image.SCALE_SMOOTH);
    lblAlanTuring.setIcon(new ImageIcon(newimg));

    panel.add(lblAlanTuring);

    JLabel label_3 = new JLabel(listaSenadores.get(3).getIdPersona().getNombre() + " "+ listaSenadores.get(3).getIdPersona().getApellido());
    label_3.setFont(new Font("Tahoma", Font.BOLD, 12));
    label_3.setBounds(0, 140, 203, 15);
    panel.add(label_3);
    decreaseFontSize(label_3 );

    rdbList4 = new JRadioButton(listaSenadores.get(3).getIdLista().getNroLista().toString());
    buttonGroup_1.add(rdbList4);
    rdbList4.setBounds(106, 36, 75, 105);
    panel.add(rdbList4);
    // rdbList4.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent arg0) {
    // capturarSenador((JRadioButton) arg0.getSource());
    // }
    // });
    rdbList4.setFont(new Font("Tahoma", Font.BOLD, 79));
    
    JTextPane textPaneD = new JTextPane();
    textPaneD.setText(listaSenadores.get(3).getIdLista().getNombreLista());
    textPaneD.setFont(new Font("Tahoma", Font.BOLD, 16));
    textPaneD.setEnabled(false);
    textPaneD.setEditable(false);
    textPaneD.setDisabledTextColor(Color.BLACK);
    textPaneD.setBounds(0, 158, 203, 76);
    panel.add(textPaneD);
    StyledDocument docD = textPaneD.getStyledDocument();
    docD.setParagraphAttributes(0, docD.getLength(), center, false);

    JLabel label_8 = new JLabel("");
    label_8.setIcon(new ImageIcon(VentanaSenadores.class
        .getResource("/imgs/justicia electoral logo.jpg")));
    label_8.setBounds(0, 0, 117, 115);

    Image img7 = ((ImageIcon) label_8.getIcon()).getImage();
    Image newimg7 =
        img7.getScaledInstance(label_8.getWidth(), label_8.getHeight(), java.awt.Image.SCALE_SMOOTH);
    label_8.setIcon(new ImageIcon(newimg7));

    getContentPane().add(label_8);

    JLabel label = new JLabel("BOLETIN DE VOTO OFICIAL");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setFont(new Font("Tahoma", Font.BOLD, 25));
    label.setBounds(444, 24, 342, 31);
    getContentPane().add(label);

    JLabel label_1 = new JLabel("PERIODO 2016-2021");
    label_1.setHorizontalAlignment(SwingConstants.CENTER);
    label_1.setForeground(Color.GRAY);
    label_1.setFont(new Font("Tahoma", Font.BOLD, 25));
    label_1.setBounds(351, 108, 536, 31);
    getContentPane().add(label_1);

    JLabel label_2 = new JLabel("SELECCIONE LA CANDIDATURA DE SU PREFERENCIA");
    label_2.setHorizontalAlignment(SwingConstants.CENTER);
    label_2.setForeground(Color.BLACK);
    label_2.setFont(new Font("Tahoma", Font.BOLD, 20));
    label_2.setBounds(302, 150, 631, 31);
    getContentPane().add(label_2);

    JPanel panel_1 = new JPanel();
    panel_1.setLayout(null);
    panel_1.setBounds(129, 192, 203, 234);
    getContentPane().add(panel_1);

    JLabel lblStevenBallmer = new JLabel("SenadorA");
    lblStevenBallmer.setIcon(new ImageIcon(VentanaSenadores.class
        .getResource("/candidatosJpeg/"+listaSenadores.get(0).getIdPersona().getNombre()+listaSenadores.get(0).getIdPersona().getApellido() +".jpg")));
    lblStevenBallmer.setBounds(0, 0, 100, 141);

    Image img4 = ((ImageIcon) lblStevenBallmer.getIcon()).getImage();
    Image newimg4 =
        img4.getScaledInstance(lblStevenBallmer.getWidth(), lblStevenBallmer.getHeight(),
            java.awt.Image.SCALE_SMOOTH);
    lblStevenBallmer.setIcon(new ImageIcon(newimg4));

    panel_1.add(lblStevenBallmer);

    JLabel label_7 = new JLabel(listaSenadores.get(0).getIdPersona().getNombre() + " "+ listaSenadores.get(0).getIdPersona().getApellido() );
    label_7.setFont(new Font("Tahoma", Font.BOLD, 12));
    label_7.setBounds(0, 141, 203, 15);
    panel_1.add(label_7);
    decreaseFontSize(label_7);

    rdbList1 = new JRadioButton(listaSenadores.get(0).getIdLista().getNroLista().toString());
    // rdbList1.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent arg0) {
    // capturarSenador((JRadioButton) arg0.getSource());
    // }
    // });
    buttonGroup_1.add(rdbList1);
    rdbList1.setFont(new Font("Tahoma", Font.BOLD, 79));
    rdbList1.setBounds(106, 36, 75, 105);
    panel_1.add(rdbList1);
    
    JTextPane textPaneA = new JTextPane();
    textPaneA.setText(listaSenadores.get(0).getIdLista().getNombreLista());
    textPaneA.setFont(new Font("Tahoma", Font.BOLD, 16));
    textPaneA.setEnabled(false);
    textPaneA.setEditable(false);
    textPaneA.setDisabledTextColor(Color.BLACK);
    textPaneA.setBounds(0, 158, 203, 76);
    panel_1.add(textPaneA);
    
    StyledDocument docA = textPaneA.getStyledDocument();
    docA.setParagraphAttributes(0, docA.getLength(), center, false);

    JPanel panel_2 = new JPanel();
    panel_2.setLayout(null);
    panel_2.setBounds(377, 192, 203, 234);
    getContentPane().add(panel_2);

    JLabel lblRolandWayne = new JLabel("SenadorB");
    lblRolandWayne.setIcon(new ImageIcon(VentanaSenadores.class
        .getResource("/candidatosJpeg/"+listaSenadores.get(1).getIdPersona().getNombre()+listaSenadores.get(1).getIdPersona().getApellido() +".jpg")));
    lblRolandWayne.setBounds(0, 0, 100, 141);

    Image img5 = ((ImageIcon) lblRolandWayne.getIcon()).getImage();
    Image newimg5 =
        img5.getScaledInstance(lblRolandWayne.getWidth(), lblRolandWayne.getHeight(),
            java.awt.Image.SCALE_SMOOTH);
    lblRolandWayne.setIcon(new ImageIcon(newimg5));

    panel_2.add(lblRolandWayne);

    JLabel label_6 = new JLabel(listaSenadores.get(1).getIdPersona().getNombre() + " "+ listaSenadores.get(1).getIdPersona().getApellido());
    label_6.setFont(new Font("Tahoma", Font.BOLD, 12));
    label_6.setBounds(0, 141, 203, 15);
    panel_2.add(label_6);
    decreaseFontSize(label_6 );

    rdbList2 = new JRadioButton(listaSenadores.get(1).getIdLista().getNroLista().toString());
    // rdbList2.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent e) {
    // capturarSenador((JRadioButton) e.getSource());
    // }
    // });
    buttonGroup_1.add(rdbList2);
    rdbList2.setFont(new Font("Tahoma", Font.BOLD, 79));
    rdbList2.setBounds(106, 36, 75, 105);
    panel_2.add(rdbList2);
    
    JTextPane textPaneB = new JTextPane();
    textPaneB.setText(listaSenadores.get(1).getIdLista().getNombreLista());
    textPaneB.setFont(new Font("Tahoma", Font.BOLD, 16));
    textPaneB.setEnabled(false);
    textPaneB.setEditable(false);
    textPaneB.setDisabledTextColor(Color.BLACK);
    textPaneB.setBounds(0, 158, 203, 76);
    panel_2.add(textPaneB);
    StyledDocument docB = textPaneB.getStyledDocument();
    docB.setParagraphAttributes(0, docB.getLength(), center, false);

    JPanel panel_3 = new JPanel();
    panel_3.setLayout(null);
    panel_3.setBounds(639, 192, 203, 234);
    getContentPane().add(panel_3);

    JLabel lblShawnFanning = new JLabel("SenadorC");
    lblShawnFanning.setIcon(new ImageIcon(VentanaSenadores.class
        .getResource("/candidatosJpeg/"+listaSenadores.get(2).getIdPersona().getNombre()+listaSenadores.get(2).getIdPersona().getApellido() +".jpg")));
    lblShawnFanning.setBounds(0, 0, 100, 141);

    Image img8 = ((ImageIcon) lblShawnFanning.getIcon()).getImage();
    Image newimg8 =
        img8.getScaledInstance(lblShawnFanning.getWidth(), lblShawnFanning.getHeight(),
            java.awt.Image.SCALE_SMOOTH);
    lblShawnFanning.setIcon(new ImageIcon(newimg8));

    panel_3.add(lblShawnFanning);

    JLabel label_11 = new JLabel(listaSenadores.get(2).getIdPersona().getNombre() + " "+ listaSenadores.get(2).getIdPersona().getApellido());
    label_11.setFont(new Font("Tahoma", Font.BOLD, 12));
    label_11.setBounds(0, 141, 203, 15);
    panel_3.add(label_11);
    decreaseFontSize(label_11 );
    rdbList3 = new JRadioButton(listaSenadores.get(2).getIdLista().getNroLista().toString());
    // rdbList3.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent e) {
    // capturarSenador((JRadioButton) e.getSource());
    // }
    // });
    buttonGroup_1.add(rdbList3);
    rdbList3.setFont(new Font("Tahoma", Font.BOLD, 79));
    rdbList3.setBounds(106, 36, 75, 105);
    panel_3.add(rdbList3);
    
    JTextPane textPane_C = new JTextPane();
    textPane_C.setText(listaSenadores.get(2).getIdLista().getNombreLista());
    textPane_C.setFont(new Font("Tahoma", Font.BOLD, 16));
    textPane_C.setEnabled(false);
    textPane_C.setEditable(false);
    textPane_C.setDisabledTextColor(Color.BLACK);
    textPane_C.setBounds(0, 158, 203, 76);
    panel_3.add(textPane_C);
    StyledDocument docC = textPane_C.getStyledDocument();
    docC.setParagraphAttributes(0, docC.getLength(), center, false);

    JPanel panel_4 = new JPanel();
    panel_4.setLayout(null);
    panel_4.setBounds(129, 437, 203, 234);
    getContentPane().add(panel_4);

    JLabel lblVintonCerf = new JLabel("SenadorE");
    lblVintonCerf.setIcon(new ImageIcon(VentanaSenadores.class
        .getResource("/candidatosJpeg/"+listaSenadores.get(4).getIdPersona().getNombre()+listaSenadores.get(4).getIdPersona().getApellido() +".jpg")));
    lblVintonCerf.setBounds(0, 0, 100, 141);

    Image img9 = ((ImageIcon) lblVintonCerf.getIcon()).getImage();
    Image newimg9 =
        img9.getScaledInstance(lblVintonCerf.getWidth(), lblVintonCerf.getHeight(),
            java.awt.Image.SCALE_SMOOTH);
    lblVintonCerf.setIcon(new ImageIcon(newimg9));

    panel_4.add(lblVintonCerf);

    JLabel label_5 = new JLabel(listaSenadores.get(4).getIdPersona().getNombre() + " "+ listaSenadores.get(4).getIdPersona().getApellido());
    label_5.setFont(new Font("Tahoma", Font.BOLD, 12));
    label_5.setBounds(0, 141, 203, 15);
    panel_4.add(label_5);
    decreaseFontSize(label_5 );

    rdbList5 = new JRadioButton(listaSenadores.get(4).getIdLista().getNroLista().toString());
    // rdbList5.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent e) {
    // capturarSenador((JRadioButton) e.getSource());
    // }
    // });
    buttonGroup_1.add(rdbList5);
    rdbList5.setFont(new Font("Tahoma", Font.BOLD, 79));
    rdbList5.setBounds(106, 36, 75, 105);
    panel_4.add(rdbList5);
    
    JTextPane textPaneE = new JTextPane();
    textPaneE.setEnabled(false);
    textPaneE.setEditable(false);
    textPaneE.setText(listaSenadores.get(4).getIdLista().getNombreLista());
    textPaneE.setBounds(0, 158, 203, 76);
    textPaneE.setFont(new Font("Tahoma", Font.BOLD, 16));
    panel_4.add(textPaneE);
    StyledDocument docE = textPaneE.getStyledDocument();
    
    docE.setParagraphAttributes(0, docE.getLength(), center, false);
    textPaneE.setDisabledTextColor(Color.BLACK);

    
    JPanel panel_5 = new JPanel();
    panel_5.setLayout(null);
    panel_5.setBounds(377, 437, 203, 234);
    getContentPane().add(panel_5);

    JLabel lblJackSKilby = new JLabel("SenadorF");
    lblJackSKilby.setIcon(new ImageIcon(VentanaSenadores.class
        .getResource("/candidatosJpeg/"+listaSenadores.get(5).getIdPersona().getNombre()+listaSenadores.get(5).getIdPersona().getApellido() +".jpg")));
    lblJackSKilby.setBounds(0, 0, 100, 141);

    Image img10 = ((ImageIcon) lblJackSKilby.getIcon()).getImage();
    Image newimg10 =
        img10.getScaledInstance(lblJackSKilby.getWidth(), lblJackSKilby.getHeight(),
            java.awt.Image.SCALE_SMOOTH);
    lblJackSKilby.setIcon(new ImageIcon(newimg10));


    panel_5.add(lblJackSKilby);

    JLabel label_15 = new JLabel(listaSenadores.get(5).getIdPersona().getNombre() + " "+ listaSenadores.get(5).getIdPersona().getApellido());
    label_15.setFont(new Font("Tahoma", Font.BOLD, 12));
    label_15.setBounds(0, 141, 203, 15);
    panel_5.add(label_15);
    decreaseFontSize( label_15);

    rdbList6 = new JRadioButton(listaSenadores.get(5).getIdLista().getNroLista().toString());
    // rdbList6.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent e) {
    // capturarSenador((JRadioButton) e.getSource());
    // }
    // });
    buttonGroup_1.add(rdbList6);
    rdbList6.setFont(new Font("Tahoma", Font.BOLD, 79));
    rdbList6.setBounds(106, 36, 75, 105);
    panel_5.add(rdbList6);
    
    JTextPane textPaneF = new JTextPane();
    textPaneF.setText(listaSenadores.get(5).getIdLista().getNombreLista());
    textPaneF.setFont(new Font("Tahoma", Font.BOLD, 16));
    textPaneF.setEnabled(false);
    textPaneF.setEditable(false);
    textPaneF.setDisabledTextColor(Color.BLACK);
    textPaneF.setBounds(0, 158, 203, 76);
    StyledDocument docf = textPaneF.getStyledDocument();
    docf.setParagraphAttributes(0, docf.getLength(), center, false);
    panel_5.add(textPaneF);

    JPanel panel_6 = new JPanel();
    panel_6.setLayout(null);
    panel_6.setBounds(639, 437, 203, 234);
    getContentPane().add(panel_6);

    JLabel lblGuglielmoMarconi = new JLabel("SenadorG");
    lblGuglielmoMarconi.setIcon(new ImageIcon(VentanaSenadores.class
        .getResource("/candidatosJpeg/"+listaSenadores.get(6).getIdPersona().getNombre()+listaSenadores.get(6).getIdPersona().getApellido() +".jpg")));
    lblGuglielmoMarconi.setBounds(0, 0, 100, 141);

    Image img11 = ((ImageIcon) lblGuglielmoMarconi.getIcon()).getImage();
    Image newimg11 =
        img11.getScaledInstance(lblGuglielmoMarconi.getWidth(), lblGuglielmoMarconi.getHeight(),
            java.awt.Image.SCALE_SMOOTH);
    lblGuglielmoMarconi.setIcon(new ImageIcon(newimg11));

    panel_6.add(lblGuglielmoMarconi);

    JLabel label_19 = new JLabel(listaSenadores.get(6).getIdPersona().getNombre() + " "+ listaSenadores.get(6).getIdPersona().getApellido());
    label_19.setFont(new Font("Tahoma", Font.BOLD, 12));
    label_19.setBounds(0, 141, 203, 15);
    panel_6.add(label_19);
    decreaseFontSize(label_19 );

    rdbList7 = new JRadioButton(listaSenadores.get(6).getIdLista().getNroLista().toString());
    // rdbList7.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent e) {
    // capturarSenador((JRadioButton) e.getSource());
    // }
    // });
    buttonGroup_1.add(rdbList7);
    rdbList7.setFont(new Font("Tahoma", Font.BOLD, 79));
    rdbList7.setBounds(106, 36, 75, 105);
    panel_6.add(rdbList7);
    
    JTextPane textPaneG = new JTextPane();
    textPaneG.setText(listaSenadores.get(6).getIdLista().getNombreLista());
    textPaneG.setFont(new Font("Tahoma", Font.BOLD, 16));
    textPaneG.setEnabled(false);
    textPaneG.setEditable(false);
    textPaneG.setDisabledTextColor(Color.BLACK);
    textPaneG.setBounds(0, 158, 203, 76);
    panel_6.add(textPaneG);
    StyledDocument docG = textPaneG.getStyledDocument();
    docG.setParagraphAttributes(0, docG.getLength(), center, false);
     

    JPanel panel_7 = new JPanel();
    panel_7.setLayout(null);
    panel_7.setBounds(900, 437, 203, 234);
    getContentPane().add(panel_7);

    JLabel lblLawrenceJEllison = new JLabel("SenadorH");
    lblLawrenceJEllison.setIcon(new ImageIcon(VentanaSenadores.class
        .getResource("/candidatosJpeg/"+listaSenadores.get(7).getIdPersona().getNombre()+listaSenadores.get(7).getIdPersona().getApellido() +".jpg")));
    lblLawrenceJEllison.setBounds(0, 0, 100, 141);

    Image img12 = ((ImageIcon) lblLawrenceJEllison.getIcon()).getImage();
    Image newimg12 =
        img12.getScaledInstance(lblLawrenceJEllison.getWidth(), lblLawrenceJEllison.getHeight(),
            java.awt.Image.SCALE_SMOOTH);
    lblLawrenceJEllison.setIcon(new ImageIcon(newimg12));


    panel_7.add(lblLawrenceJEllison);

    JLabel label_23 = new JLabel(listaSenadores.get(7).getIdPersona().getNombre() + " "+ listaSenadores.get(7).getIdPersona().getApellido());
    label_23.setFont(new Font("Tahoma", Font.BOLD, 12));
    label_23.setBounds(0, 142, 203, 15);
    panel_7.add(label_23);
    decreaseFontSize(label_23 );

    rdbList8 = new JRadioButton(listaSenadores.get(7).getIdLista().getNroLista().toString());
    // rdbList8.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseClicked(MouseEvent e) {
    // capturarSenador((JRadioButton) e.getSource());
    // }
    // });
    buttonGroup_1.add(rdbList8);
    rdbList8.setFont(new Font("Tahoma", Font.BOLD, 79));
    rdbList8.setBounds(106, 36, 75, 105);
    panel_7.add(rdbList8);
    
    JTextPane textPaneH = new JTextPane();
    textPaneH.setText(listaSenadores.get(7).getIdLista().getNombreLista());
    textPaneH.setFont(new Font("Tahoma", Font.BOLD, 16));
    textPaneH.setEnabled(false);
    textPaneH.setEditable(false);
    textPaneH.setDisabledTextColor(Color.BLACK);
    textPaneH.setBounds(0, 158, 203, 76);
    panel_7.add(textPaneH);

    StyledDocument docH = textPaneH.getStyledDocument();
    docH.setParagraphAttributes(0, docH.getLength(), center, false);
    
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
  public void setVentanaPrincipal(VentanaSenadores miVentana) {
    miVentanaPrincipal = miVentana;
  }

  private void iniciarComponentes() {
    contenedor = getContentPane();/* instanciamos el contenedor */
    /*
     * con esto definmos nosotros mismos los tama�os y posicion de los componentes
     */
    contenedor.setLayout(null);
    buttonGroup_1 = new ButtonGroup();
    // VentanaPrincipal frmSistemaEvote = new VentanaPrincipal();


    JLabel lblNewLabel = new JLabel("CANDIDATOS A SENADORES DE LA NACIÓN");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
    lblNewLabel.setBounds(341, 66, 544, 31);
    getContentPane().add(lblNewLabel);


    // ver www.camick.com select button group
    final JButton botonCambiar = new JButton();
    botonCambiar.setText("Confirmar Voto");
    botonCambiar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evento) {
        JButton source = (JButton) evento.getSource();
        Integer ban = 0;
        if (evento.getSource() == botonCambiar) {
          // para saber que rdb se selecciono//
          JRadioButton[] rdb =
              {rdbList1, rdbList2, rdbList3, rdbList4, rdbList5, rdbList6, rdbList7, rdbList8}; // agregar
                                                                                                // cada
                                                                                                // RDB
          for (int i = 0; i < rdb.length; i++) {
            if (rdb[i].isSelected()) {
              lista = rdb[i].getText();
              if (!lista.isEmpty()) {


                senadores = lista.substring(lista.length() - 1, lista.length());
                System.out.println("Senador: " + senadores);

                dispose();
                VentanaConfirmacionSenadores miVentanaConfirmacion =
                    new VentanaConfirmacionSenadores(miVentanaPrincipal, true);
                // if (lista.compareTo("LISTA 1")== 0){
                // miVentanaPrincipal.setCandidato(1);
                // }
                // miVentanaPrincipal.setCedula("3619250");
                // setear voto para lista

                // miVentanaPrincipal.setLista(VentanaSenadores.senadores);
                // miVentanaPrincipal.setCedula(VentanaPrincipalVotante.cedulaVotante);
                ban = 1;
                break;
              }


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
    botonCambiar.setBounds(395, 686, 185, 37);
    getContentPane().add(botonCambiar);

    JButton btnNewButton_1 = new JButton("Votar BLANCO");
    btnNewButton_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        senadores = "BLANCO";

        dispose();
        VentanaConfirmacionSenadores miVentanaConfirmacion =
            new VentanaConfirmacionSenadores(miVentanaPrincipal, true);

      }
    });
    btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
    btnNewButton_1.setBounds(639, 686, 179, 37);
    getContentPane().add(btnNewButton_1);


  }

  public static String getSenadores() {
    return senadores;
  }

  public static void setSenadores(String senadores) {
    VentanaSenadores.senadores = senadores;
  }

  public String getLista() {
    return lista;
  }

  public void setLista(String lista) {
    this.lista = lista;
  }

  /* Agregamos el evento al momento de llamar la otra ventana */
  public void actionPerformed(ActionEvent evento) {
    if (evento.getSource() == botonCambiar) {
      // VentanaConfirmacion miVentanaConfirmacion=new VentanaConfirmacion(miVentanaPrincipal,true);
      // miVentanaConfirmacion.setVisible(true);
    }
  }

  public void capturarSenador(JRadioButton rdb) {
    senadores = rdb.getText().substring(rdb.getText().length() - 1, rdb.getText().length());
    System.out.println("Senador: " + senadores);
  }


  private List<UcsawsCandidatos> candidatosSenadores() {

    CandidatoDAO candidatoDAO = new CandidatoDAO();
    List<UcsawsCandidatos> candidatosSenadores = candidatoDAO.obtenerCandidatosByIdEvento(EleccionMesa.evento);

    Iterator<UcsawsCandidatos> ite = candidatosSenadores.iterator();

    UcsawsCandidatos aux;
    List<UcsawsCandidatos> result = new ArrayList<UcsawsCandidatos>();
    while (ite.hasNext()) {
      aux = ite.next();
      if (aux.getIdLista().getUcsawsTipoLista().getCodigo().compareTo("SEN") == 0) {

        result.add(aux);

      }

    }

    return result;

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
