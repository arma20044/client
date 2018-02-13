package src.main.java.admin.acta;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXFindBar;
import org.jdesktop.swingx.JXTable;
import org.json.simple.JSONArray;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;

import entity.UcsawsActas;
import entity.UcsawsMiembroMesa;
import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.miembromesa.VentanaBuscarMiembroMesa;
import src.main.java.admin.reportes.Acta;
import src.main.java.admin.reportes.CantidadVotosElegir;
import src.main.java.admin.reportes.CantidadVotosSenadorDiputado;
import src.main.java.dao.acta.ActaDAO;
import src.main.java.login.Login;


public class VentanaBuscarActa extends JFrame implements ActionListener {

  private Coordinador miCoordinador; // objeto miCoordinador que permite la
  // relacion entre esta clase y la clase
  // coordinador
  private JLabel labelTitulo;
  private JXFindBar txtBuscar;
  private JButton botonCancelar, btnEliminar, btnNuevo;

  JSONArray miPersona = null;
  DefaultTableModel modelo;
  private JXTable table_1;
  private ActaJTableModel model = new ActaJTableModel();
  private JScrollPane scrollPane;

  public static String codTemporal = "";

  private String tipoActa = "";

  private JLabel lblMensaje;

  private DefaultTableModel dm;



  /**
   * constructor de la clase donde se inicializan todos los componentes de la ventana de busqueda
   */
  public VentanaBuscarActa() {

    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e) {
        txtBuscar.requestFocus();
      }
    });

    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    botonCancelar = new JButton();
    botonCancelar.setIcon(new ImageIcon(VentanaBuscarActa.class.getResource("/imgs/back2.png")));
    botonCancelar.setToolTipText("Atrás");
    botonCancelar.setBounds(813, 422, 45, 25);
    botonCancelar.setOpaque(false);
    botonCancelar.setContentAreaFilled(false);
    botonCancelar.setBorderPainted(false);
    Image img = ((ImageIcon) botonCancelar.getIcon()).getImage();
    Image newimg = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonCancelar.setIcon(new ImageIcon(newimg));

    btnEliminar = new JButton();
    btnEliminar.setToolTipText("Eliminar");
    btnEliminar.setIcon(new ImageIcon(VentanaBuscarActa.class.getResource("/imgs/borrar.png")));
    btnEliminar.setBounds(528, 55, 32, 32);
    btnEliminar.setOpaque(false);
    btnEliminar.setContentAreaFilled(false);
    btnEliminar.setBorderPainted(false);
    Image img4 = ((ImageIcon) btnEliminar.getIcon()).getImage();
    Image newimg4 = img4.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnEliminar.setIcon(new ImageIcon(newimg4));

    labelTitulo = new JLabel();
    labelTitulo.setText("NUEVA ACTA");
    labelTitulo.setBounds(248, 11, 270, 30);
    labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));



    btnEliminar.addActionListener(this);
    botonCancelar.addActionListener(this);

    getContentPane().add(botonCancelar);
    getContentPane().add(btnEliminar);
    getContentPane().add(labelTitulo);
    limpiar();

    setSize(864, 476);
    setTitle("Sistema E-vote: Paraguay Elecciones 2015");
    setLocationRelativeTo(null);
    getContentPane().setLayout(null);

    scrollPane = new JScrollPane();
    scrollPane.setAutoscrolls(true);
    scrollPane.setToolTipText("Lista de Candidatos");
    scrollPane.setBounds(0, 158, 858, 265);
    getContentPane().add(scrollPane);

    table_1 = new JXTable() {
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table_1.setToolTipText("Listado de Generos.");
    // table_1.setAutoCreateRowSorter(true);
    table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    scrollPane.setViewportView(table_1);

    txtBuscar = new JXFindBar(table_1.getSearchable());

    txtBuscar.setBounds(10, 54, 474, 33);
    getContentPane().add(txtBuscar);
    table_1.setColumnControlVisible(true);

    TableRowFilterSupport.forTable(table_1).searchable(true).apply();
    // String[] columnNames = {"Picture", "Description"};
    table_1.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent arg0) {
        
   if(SwingUtilities.isRightMouseButton(arg0)){
          System.out.println("derecho");
          List<String> selectedData = new ArrayList<String>();

          int selectedRow = table_1.rowAtPoint(arg0.getPoint());
          // System.out.println(selectedRow);
          int col = 0;
          while (col < table_1.getColumnCount() + 1) {
            // System.out.println(table_1.getValueAt(selectedRow,
            // col));
            try {
              int row = table_1.rowAtPoint(arg0.getPoint());
              String table_click0 =
                  table_1.getModel().getValueAt(table_1.convertRowIndexToModel(row), col)
                      .toString();
              // System.out.println(table_click0);

              selectedData.add(table_click0);
              // System.out.println(selectedData);

            } catch (Exception e) {
              System.out.println(e.getMessage());
            }

            col++;
          }
          // selectedData.ad table_1.getValueAt(selectedRow[i],
          // selectedColumns[0]);
          // txtId.setText(selectedData.get(0));
          // txtBuscar.setText(selectedData.get(2) + " " + selectedData.get(3) + " Lista "
          // + selectedData.get(4));

          // textFecha.setText(selectedData.get(2));
          // textUsu.setText(selectedData.get(4));
          // codTemporal.setText(selectedData.get(1));
          codTemporal = selectedData.get(0);
          tipoActa = selectedData.get(7);
          
          
          Acta acta = new Acta();
         // System.out.println(idTipo);
          acta.start();
        }
   else
        {
        if (arg0.getClickCount() == 2) {
          System.out.println("Doble Click");

          System.out.println("Entrar en Miembro Mesa.");
          List<String> selectedData = new ArrayList<String>();

          int selectedRow = table_1.rowAtPoint(arg0.getPoint());
          // System.out.println(selectedRow);
          int col = 0;
          while (col < table_1.getColumnCount() + 1) {
            // System.out.println(table_1.getValueAt(selectedRow,
            // col));
            try {
              int row = table_1.rowAtPoint(arg0.getPoint());
              String table_click0 =
                  table_1.getModel().getValueAt(table_1.convertRowIndexToModel(row), col)
                      .toString();
              // System.out.println(table_click0);

              selectedData.add(table_click0);
              // System.out.println(selectedData);

            } catch (Exception e) {
              System.out.println(e.getMessage());
            }

            col++;
          }
          // selectedData.ad table_1.getValueAt(selectedRow[i],
          // selectedColumns[0]);
          // txtId.setText(selectedData.get(0));
          // txtBuscar.setText(selectedData.get(2) + " " + selectedData.get(3) + " Lista "
          // + selectedData.get(4));

          // textFecha.setText(selectedData.get(2));
          // textUsu.setText(selectedData.get(4));
          // codTemporal.setText(selectedData.get(1));
          codTemporal = selectedData.get(0);
          tipoActa = selectedData.get(7);

          System.out.println("Selected: " + selectedData);
          String a = tipoActaString();
          if (soloActaInicio() == false && a.compareToIgnoreCase("CIERRE") != 0) {


            VentanaBuscarMiembroMesa miembroMesa = new VentanaBuscarMiembroMesa(codTemporal);
            miembroMesa.setVisible(true);
            dispose();

          } else {
            JOptionPane.showMessageDialog(null,
                "El Acta ya ha sido Cerrado. Por ende ya no se pueden agregar Miembros.");
          }



        }
        if (arg0.getClickCount() == 1) {
          System.out.println("1 click");
          List<String> selectedData = new ArrayList<String>();

          int col = 0;
          while (col < table_1.getColumnCount() + 1) {
            // System.out.println(table_1.getValueAt(selectedRow,
            // col));
            try {
              int row = table_1.rowAtPoint(arg0.getPoint());
              String table_click0 =
                  table_1.getModel().getValueAt(table_1.convertRowIndexToModel(row), col)
                      .toString();
              // System.out.println(table_click0);

              selectedData.add(table_click0);
              // System.out.println(selectedData);

            } catch (Exception e) {
              System.out.println(e.getMessage());
            }

            col++;
          }
          codTemporal = selectedData.get(0);
          tipoActa = selectedData.get(7);
          
          
          //obtenemos acta
          ActaDAO actaDAO = new ActaDAO();
          UcsawsActas acta = actaDAO.obtenerActaById(Integer.parseInt(codTemporal));
          //obtenemos acta


          //preguntamos que tipo de acta es
          if (acta.getTipoActa().getDescripcion().contains("inicio".toUpperCase())){
            
            List<UcsawsActas> lista = actaDAO.obtenerActaByIdEvento(acta.getIdEvento().getIdEvento());
            
            Iterator<UcsawsActas> ite = lista.iterator();
            boolean finalizado = false;
            UcsawsActas aux;
            while (ite.hasNext()) {
              aux = ite.next();
              if(aux.getActaFinalizada() != null){
              if (aux.getActaFinalizada().getIdActa() == Integer.parseInt(codTemporal)) {
                finalizado = true;
                break;
              }
            }
            }
            
            if (!finalizado){
              
              //preguntar si queremos agregar miembros o finalizar el acta
              int seleccion = JOptionPane.showOptionDialog(
                  getContentPane(),
                  "Seleccione opcion", 
                  "Selector de opciones",
                  JOptionPane.YES_NO_CANCEL_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,    // null para icono por defecto.
                  new Object[] { "Agregar Miembros de Mesa", "Finalizar Acta" }, null   // null para YES, NO y CANCEL
                 );

               if (seleccion == 0){
                 VentanaBuscarMiembroMesa miembroMesa = new VentanaBuscarMiembroMesa(codTemporal);
                 miembroMesa.setVisible(true);
                 dispose();
               }
               else if (seleccion == 1){
                 VentanaRegistroActaFin ventanaRegistroFin = new VentanaRegistroActaFin(codTemporal);
                 ventanaRegistroFin.setVisible(true);
                 dispose();
               }
                  System.out.println("seleccionada opcion " + (seleccion + 1));
              
            }
            else if (finalizado){
              JOptionPane.showMessageDialog(null,
                  "El Acta ya ha sido Cerrado. Por ende ya no se pueden agregar Miembros.");
            }
          }
          
          else if(acta.getTipoActa().getDescripcion().contains("cierre".toUpperCase())){
            VentanaBuscarMiembroMesa miembroMesa = new VentanaBuscarMiembroMesa(codTemporal);
            miembroMesa.setVisible(true);
            dispose();
          }
          
          else{
            VentanaBuscarMiembroMesa miembroMesa = new VentanaBuscarMiembroMesa(codTemporal);
            miembroMesa.setVisible(true);
            dispose();
          }

          
          //preguntamos que tipo de acta es
       


        }


      }
      }

      private boolean soloActaInicio() {
        boolean existe = false;

        ActaDAO actaDAO = new ActaDAO();

        List<UcsawsActas> lista = new ArrayList<UcsawsActas>();

        lista = actaDAO.obtenerActaByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

        Iterator<UcsawsActas> ite = lista.iterator();

        UcsawsActas aux;
        while (ite.hasNext()) {
          aux = ite.next();
          if (aux.getActaFinalizada() != null) {
            if (aux.getActaFinalizada().getIdActa() == Integer.parseInt(codTemporal)) {
              existe = true;
            }
          }
        }

        // TODO Auto-generated method stub
        return existe;
      }

      private boolean actaInicioYaFinalizada() {
        boolean finalizo = false;

        ActaDAO actaDAO = new ActaDAO();

        List<UcsawsActas> lista = new ArrayList<UcsawsActas>();

        lista = actaDAO.obtenerActaByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

        Iterator<UcsawsActas> ite = lista.iterator();

        UcsawsActas aux;
        while (ite.hasNext()) {
          aux = ite.next();

          if (aux.getActaFinalizada().getIdActa() == Integer.parseInt(codTemporal)) {
            if (aux.getActaFinalizada() != null) {
              finalizo = true;
            }
          }

        }

        // TODO Auto-generated method stub
        return finalizo;
      }


      private String tipoActaString() {
        String result = "";
        if (tipoActa.contains("CIERRE")) {
          result = "CIERRE";
        } else if (tipoActa.contains("INICIO")) {
          result = "INICIO";
        }

        return result;

      }


    });
    table_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    recuperarDatos();
    table_1.setModel(model);
    table_1.removeColumn(table_1.getColumnModel().getColumn(0));
    JLabel lblListaDeGeneros = new JLabel();
    lblListaDeGeneros.setText("LISTA DE ACTAS");
    lblListaDeGeneros.setFont(new Font("Verdana", Font.BOLD, 18));
    lblListaDeGeneros.setBounds(147, 117, 325, 30);
    getContentPane().add(lblListaDeGeneros);

    JButton btnHome = new JButton("");
    btnHome.setToolTipText("Inicio");
    btnHome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        DefinicionesGenerales menuprincipal = new DefinicionesGenerales();
        menuprincipal.setVisible(true);
        dispose();
      }
    });
    btnHome.setIcon(new ImageIcon(VentanaBuscarActa.class.getResource("/imgs/home.png")));
    btnHome.setBounds(0, 0, 32, 32);
    Image img5 = ((ImageIcon) btnHome.getIcon()).getImage();
    Image newimg5 = img5.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnHome.setIcon(new ImageIcon(newimg5));
    getContentPane().add(btnHome);

    btnNuevo = new JButton("");
    btnNuevo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaRegistroActa registro = new VentanaRegistroActa();
        registro.setVisible(true);
        dispose();
      }
    });
    btnNuevo.setToolTipText("Nuevo");
    btnNuevo.setOpaque(false);
    btnNuevo.setContentAreaFilled(false);
    btnNuevo.setBorderPainted(false);
    btnNuevo.setIcon(new ImageIcon(VentanaBuscarActa.class.getResource("/imgs/add.png")));
    btnNuevo.setBounds(486, 55, 32, 32);
    Image img2 = ((ImageIcon) btnNuevo.getIcon()).getImage();
    Image newimg2 = img2.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnNuevo.setIcon(new ImageIcon(newimg2));
    getContentPane().add(btnNuevo);

    lblMensaje = new JLabel("");
    lblMensaje.setForeground(Color.RED);
    lblMensaje.setBounds(57, 88, 432, 14);
    getContentPane().add(lblMensaje);
    // Image newimg6 = img6.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);

    // table_1.getColumnModel().getColumn(0).setHeaderValue("Descripcion");

    ListSelectionModel cellSelectionModel = table_1.getSelectionModel();
    // recuperarDatos();
    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

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


    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clickButtonescape");

    getRootPane().getActionMap().put("clickButtonescape", new AbstractAction() {
      public void actionPerformed(ActionEvent ae) {
        botonCancelar.doClick();
        System.out.println("button esc clicked");
      }
    });


    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "clickButtondelete");

    getRootPane().getActionMap().put("clickButtondelete", new AbstractAction() {
      public void actionPerformed(ActionEvent ae) {
        btnEliminar.doClick();
        System.out.println("button delete clicked");
      }
    });

    DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm:ss");

    if (VentanaBuscarEvento.readOnly == true) {
      btnNuevo.setEnabled(false);
      btnNuevo.setToolTipText("Ya No se puede cargar datos durante ni despues la votacion");
      btnEliminar.setEnabled(false);
      btnEliminar.setToolTipText("Ya No se puede eliminar datos durante ni despues la votacion");
      // btnModificar.setEnabled(false);
      // btnModificar.setToolTipText("Ya No se puede Modificar datos durante ni despues la votacion");
    }

  }

  public void setCoordinador(Coordinador miCoordinador) {
    this.miCoordinador = miCoordinador;
  }

  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == btnEliminar) {
      if (!codTemporal.equals("")) {
        int respuesta =
            JOptionPane.showConfirmDialog(this, "¿Esta seguro de eliminar el Acta?",
                "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_NO_OPTION) {
          ActaDAO actaDAO = new ActaDAO();

          try {
            UcsawsActas actaEliminar = actaDAO.obtenerActaById(Integer.parseInt(codTemporal));

            if (actaDAO.eliminarActa(codTemporal) == false) {
              JOptionPane.showMessageDialog(null, "Error al intentar Borrar el Candidato", "Error",
                  JOptionPane.ERROR_MESSAGE);
            } else {
              codTemporal = "";

              lblMensaje.setText("Excelente, se ha eliminado el Acta ");

              Timer t = new Timer(Login.timer, new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                  lblMensaje.setText(null);
                }
              });
              t.setRepeats(false);
              t.start();
              codTemporal = "";
              limpiar();

              model = new ActaJTableModel();

              recuperarDatos();
              table_1.setModel(model);
              table_1.removeColumn(table_1.getColumnModel().getColumn(0));


            }


          } catch (Exception e2) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "sfdsfsfsdfs", "Información",
                JOptionPane.WARNING_MESSAGE);
          }
          // JOptionPane.showMessageDialog(null, "Excelente, se ha eliminado el candidato ");
          // modificarGenero(textCod.getText(),
          // codTemporal.getText());

          // limpiar();


          // model.fireTableDataChanged();
          // table_1.repaint();
        }
      } else {
        lblMensaje.setText("Por favor seleccione que Acta desea Eliminar");

        Timer t = new Timer(Login.timer, new ActionListener() {

          public void actionPerformed(ActionEvent e) {
            lblMensaje.setText(null);
          }
        });
        t.setRepeats(false);
        t.start();

        // JOptionPane.showMessageDialog(null,
        // "Por favor seleccione que Genero desea Eliminar",
        // "Información",JOptionPane.WARNING_MESSAGE);
      }

    }
    if (e.getSource() == botonCancelar) {
      DefinicionesGenerales definiciones = new DefinicionesGenerales();
      definiciones.setVisible(true);
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
    // xtBuscar.setText("");

    codTemporal = "";
    tipoActa = "";

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
      boolean profesion, boolean bBuscar, boolean bGuardar, boolean bModificar, boolean bEliminar) {
    // txtBuscar.setEditable(codigo);
    // botonModificar.setEnabled(true);
    btnEliminar.setEnabled(bEliminar);
  }

  private void recuperarDatos() {

    ActaDAO actaDAO = new ActaDAO();

    List<UcsawsActas> lista =
        actaDAO.obtenerActaByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

    if (lista.isEmpty()) {
      // JOptionPane.showMessageDialog(null, "algo salio mal",
      // "Advertencia", JOptionPane.WARNING_MESSAGE);
      // return lista;
    }

    else {
      obtenerModeloA(table_1, lista);

      // return lista;
    }

  }

  public AbstractTableModel obtenerModeloA(JTable tabla, List<UcsawsActas> candidatos) {

    // AbstractTableModel model = (DefaultTableModel) tabla.getModel();
    Iterator<UcsawsActas> ite = candidatos.iterator();

    // String header[] = new String[] { "ID","Item","Nro.",
    // "Desc. Evento","Inicio","Fin","Desc. Tipo Evento" };
    // model.setColumnIdentifiers(header);

    UcsawsActas aux;
    Integer cont = 1;
    while (ite.hasNext()) {
      aux = ite.next();
      // {"ID", "Item", "codigo", "nombre","apellido", "Lista", "Tipo",
      // "Observacion"};

      Object[] row =
          {aux.getIdActa(), cont, aux.getIdMesa().getDescMesa(), aux.getObservacion(),
              aux.getDescripcion(), new SimpleDateFormat("dd-MM-yyyy").format((aux.getFecha())),
              aux.getNumeroVotantes(), aux.getTipoActa().getDescripcion()};

      // new
      // SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
      model.candidato.add(row);

      cont++;

    }

    return model;
  }

  void LimpiarCampos() {
    // txtBuscar.setText("");
    // textFecha.setText("");
    // textUsu.setText("");
    codTemporal = "";
    // txtId.setText("");

  }

  public void filter(String query) {

    /*
     * TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>( dm);
     * 
     * table_1.setRowSorter(tr);
     * 
     * tr.setRowFilter(RowFilter.regexFilter(query));
     */

    TableRowSorter sorter = new TableRowSorter(table_1.getModel());
    sorter.setRowFilter(RowFilter.regexFilter(query));
    table_1.setRowSorter(sorter);

  }
}
