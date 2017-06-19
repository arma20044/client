package src.main.java.admin.escrutinio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import org.json.simple.JSONArray;

import com.lowagie.toolbox.plugins.Txt2Pdf;

import src.main.java.admin.Coordinador;
import src.main.java.admin.Escrutinio;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.tipoLista.TipoListaDAO;
import src.main.java.dao.voto.VotoDAO;
import entity.UcsawsTipoLista;

import javax.swing.JScrollPane;

public class VentanaEscrutinioPresidente extends JFrame implements ActionListener {

  private Coordinador miCoordinador; // objeto miCoordinador que permite la
  private JButton botonCancelar;

  JSONArray miPersona = null;
  DefaultTableModel modelo;
  // private PaisJTableModel model = new PaisJTableModel();

  private String codTemporal = "";

  private JLabel lblMensaje;
  JButton btnComenzar;
  private JLabel lblEscrutinioPresidentes;
  JTextPane textPane = new JTextPane();
  private JScrollPane scrollPane;

  /**
   * constructor de la clase donde se inicializan todos los componentes de la ventana de busqueda
   */
  public VentanaEscrutinioPresidente() {
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



    botonCancelar = new JButton();
    botonCancelar.setIcon(new ImageIcon(VentanaEscrutinioPresidente.class
        .getResource("/imgs/back2.png")));
    botonCancelar.setToolTipText("Atrás");
    botonCancelar.setBounds(589, 422, 45, 25);
    botonCancelar.setOpaque(false);
    botonCancelar.setContentAreaFilled(false);
    botonCancelar.setBorderPainted(false);
    Image img = ((ImageIcon) botonCancelar.getIcon()).getImage();
    Image newimg = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonCancelar.setIcon(new ImageIcon(newimg));
    botonCancelar.addActionListener(this);

    getContentPane().add(botonCancelar);
    limpiar();

    setSize(632, 476);
    setTitle("Sistema E-vote: Paraguay Elecciones 2015");
    setLocationRelativeTo(null);
    getContentPane().setLayout(null);

    JButton btnHome = new JButton("");
    btnHome.setToolTipText("Inicio");
    btnHome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        MenuPrincipal menuprincipal = new MenuPrincipal();
        menuprincipal.setVisible(true);
        dispose();
      }
    });
    btnHome.setIcon(new ImageIcon(VentanaEscrutinioPresidente.class.getResource("/imgs/home.png")));
    btnHome.setBounds(0, 0, 32, 32);
    Image img5 = ((ImageIcon) btnHome.getIcon()).getImage();
    Image newimg5 = img5.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnHome.setIcon(new ImageIcon(newimg5));
    getContentPane().add(btnHome);


    lblMensaje = new JLabel("");
    lblMensaje.setForeground(Color.RED);
    lblMensaje.setBounds(51, 95, 432, 14);
    getContentPane().add(lblMensaje);

    btnComenzar = new JButton("Comenzar");
    btnComenzar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {


        // ****** NEW
        VotoDAO votoDAO = new VotoDAO();
        TipoListaDAO tipoListaDAO = new TipoListaDAO();
        List<UcsawsTipoLista> listas =
            tipoListaDAO.obtenerTipoListaByIdEvento(VentanaBuscarEvento.eventoClase.getIdEvento());
        UcsawsTipoLista listaPresidente = new UcsawsTipoLista();

        Iterator<UcsawsTipoLista> ite = listas.iterator();

        UcsawsTipoLista aux;
        while (ite.hasNext()) {
          aux = ite.next();
          if (aux.getCodigo().compareToIgnoreCase("PRE") == 0) {
            listaPresidente = aux;
            break;
          }
        }



        List<Object> votosParaDiputados = votoDAO.obteneConteoVotoByEvento(listaPresidente);
        System.out.println(votosParaDiputados);


        Iterator<Object> ite2 = votosParaDiputados.iterator();
        List<String> aux2;
        List<String> CandidatoVotos = new ArrayList<String>();

        while (ite2.hasNext()) {
          aux2 = (List<String>) ite2.next();
          CandidatoVotos.add(aux2.get(0) + "-" + String.valueOf(aux2.get(1)));
        }


        int cont = 0;
        List<String> party = new ArrayList<String>();
        List<Integer> votos = new ArrayList<Integer>();
        while (CandidatoVotos.size() > cont) {

          String a = CandidatoVotos.get(cont);
          String[] parts = a.split("-");
          String part1 = parts[0]; // 004
          Integer part2 = Integer.parseInt(parts[1]); // 034556


          party.add(part1);
          votos.add(part2);
          cont++;
        }


        // ******
        List<Integer> votes = votos;
        int ban = 0;
        // Output Results
        for (int p = 0; p < votes.size(); p++) {
          textPane.setDisabledTextColor(Color.BLACK); 
          StyledDocument doc = textPane.getStyledDocument();

          // Define a keyword attribute

          SimpleAttributeSet keyWord = new SimpleAttributeSet();

          try {
            Integer i = votes.get(p);
            //textPane.setText();
           // doc.insertString(0, "Start of text\n", null);
            doc.insertString(doc.getLength(), "• " +party.get(p) + " obtuvo " + i + " Voto(s) \n", keyWord);
          } catch (Exception ex) {
            System.out.println(ex);
          }

          
          /*
           * if (ban == 0) {
           * 
           * Integer i = votes.get(p); // i becomes 5
           * 
           * System.out.println(party.get(p) + " obtuvo " + i + " Voto(s)");
           * lblCandidato1.setText(party.get(p) + " obtuvo " + i + " Voto(s)");
           * lblCandidato1.setFont(new Font("Verdana", Font.BOLD, 15)); ban++;
           * textArea.setText(party.get(p) + " obtuvo " + i + " Voto(s) \n"); } else if (ban == 1) {
           * Integer i = votes.get(p); System.out.println(party.get(p) + " obtuvo " + i +
           * " Voto(s)"); lblCandidato2.setText(party.get(p) + " obtuvo " + i + " Voto(s)"); ban++;
           * textArea.append(party.get(p) + " obtuvo " + i + " Voto(s)"); } else if (ban == 2) {
           * Integer i = votes.get(p); System.out.println(party.get(p) + " obtuvo " + i +
           * " Voto(s)"); lblCandidato3.setText(party.get(p) + " obtuvo " + i + " Voto(s)"); ban++;
           * textArea.append(party.get(p) + " obtuvo " + i + " Voto(s)"); } else if (ban == 3) {
           * Integer i = votes.get(p); System.out.println(party.get(p) + " obtuvo " + i +
           * " Voto(s)"); lblCandidato4.setText(party.get(p) + " obtuvo " + i + " Voto(s)"); ban++;
           * textArea.append(party.get(p) + " obtuvo " + i + " Voto(s)");
           * 
           * } else if (ban == 4) { Integer i = votes.get(p); System.out.println(party.get(p) +
           * " obtuvo " + i + " Voto(s)"); lblCandidato4.setText(party.get(p) + " obtuvo " + i +
           * " Voto(s)"); ban++; textArea.append(party.get(p) + " obtuvo " + i + " Voto(s)"); }
           */
        }
        btnComenzar.setText("Procesar de Nuevo?.");
        btnComenzar.setSize(145, 23);


        // lblListaCandidatosA.setText("Resultado del Escrutinio de Presidentes.");
      }
    });
    btnComenzar.setBounds(416, 422, 89, 23);
    getContentPane().add(btnComenzar);

    JLabel label = new JLabel("New label");
    label
        .setIcon(new ImageIcon(VentanaEscrutinioPresidente.class.getResource("/imgs/logoTSJE.png")));
    label.setBounds(34, 0, 182, 188);
    getContentPane().add(label);
    Image img6 = ((ImageIcon) label.getIcon()).getImage();
    Image newimg6 =
        img6.getScaledInstance(label.getWidth(), label.getHeight(), java.awt.Image.SCALE_SMOOTH);
    label.setIcon(new ImageIcon(newimg6));

    lblEscrutinioPresidentes = new JLabel();
    lblEscrutinioPresidentes.setText("ESCRUTINIO PRESIDENTES.");
    lblEscrutinioPresidentes.setFont(new Font("Verdana", Font.BOLD, 18));
    lblEscrutinioPresidentes.setBounds(263, 95, 324, 30);
    getContentPane().add(lblEscrutinioPresidentes);
    
    scrollPane = new JScrollPane();
    scrollPane.setBounds(34, 197, 586, 214);
    getContentPane().add(scrollPane);
    textPane.setEnabled(false);
    textPane.setFont(new Font("Tahoma", Font.BOLD, 22));
    textPane.setEditable(false);
    scrollPane.setViewportView(textPane);



    // cellSelectionModel.addListSelectionListener(new
    // ListSelectionListener() {
    // public void valueChanged(ListSelectionEvent e) {
    // String selectedData = null;
    //
    // int[] selectedRow = table_1.getSelectedRows();
    // int[] selectedColumns = table_1.getSelectedColumns();
    //
    // for (int i = 0; i < selectedRow.length; i++) {
    // for (int j = 0; j < selectedColumns.length; j++) {
    // selectedData = (String) table_1.getValueAt(selectedRow[i],
    // selectedColumns[j]);
    // }
    // }
    // System.out.println("Selected: " + selectedData);
    // }
    //
    // });


  }

  public void setCoordinador(Coordinador miCoordinador) {
    this.miCoordinador = miCoordinador;
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == botonCancelar) {
      Escrutinio escr = new Escrutinio();
      escr.setVisible(true);
      this.dispose();
    }

  }

  /**
   * permite cargar los datos de la persona consultada
   * 
   * @param miPersona
   */
  private void muestraPersona(JSONArray genero) {
    JSONArray a = (JSONArray) genero.get(0);
    // txtId.setText(Long.toString( (Long) a.get(0)) );
    // txtBuscar.setText((String) a.get(1));
    // textFecha.setText((String) a.get(2));
    // textUsu.setText((String) a.get(4));
    codTemporal = a.get(0).toString();

    habilita(true, false, false, false, false, true, false, true, true);
  }

  /**
   * Permite limpiar los componentes
   */
  public void limpiar() {

    // codTemporal.setText("");
    habilita(true, false, false, false, false, true, false, true, true);
  }

  /**
   * Permite habilitar los componentes para establecer una modificacion
   * 
   * @param codigo
   * @param nombre
   * @param edad
   * @param tel
   * @param profesion
   * @param cargo
   * @param bBuscar
   * @param bGuardar
   * @param bModificar
   * @param bEliminar
   */
  public void habilita(boolean codigo, boolean nombre, boolean edad, boolean tel,
      boolean profesion, boolean bBuscar, boolean bGuardar, boolean bModificar, boolean bEliminar) {}



  void LimpiarCampos() {
    // txtBuscar.setText("");
    // textFecha.setText("");
    // textUsu.setText("");
    codTemporal = "";
    // txtId.setText("");

  }



  static <T> T[] append(T[] arr, T element) {
    final int N = arr.length;
    arr = Arrays.copyOf(arr, N + 1);
    arr[N] = element;
    return arr;
  }
}
